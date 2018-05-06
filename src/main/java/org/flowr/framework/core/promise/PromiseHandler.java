package org.flowr.framework.core.promise;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Flow.Subscriber;

import org.flowr.framework.core.constants.ExceptionConstants;
import org.flowr.framework.core.constants.ExceptionMessages;
import org.flowr.framework.core.context.Context;
import org.flowr.framework.core.context.EventContext;
import org.flowr.framework.core.event.ChangeEvent;
import org.flowr.framework.core.event.ChangeEventEntity;
import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.flow.SingleEventPublisher;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.node.Autonomic.ResponseCode;
import org.flowr.framework.core.node.BackPressureExecutorService;
import org.flowr.framework.core.node.BackPressureExecutors;
import org.flowr.framework.core.process.management.ProcessHandler;
import org.flowr.framework.core.promise.deferred.ProgressScale;
import org.flowr.framework.core.promise.deferred.PromiseTask;
import org.flowr.framework.core.service.extension.PromiseService.PromiseServerState;
import org.flowr.framework.core.service.extension.PromiseService.PromiseServerStatus;
import org.flowr.framework.core.target.ReactiveTarget;

/**
 * Provides concrete implementation for Promise handling as defined by Promise
 * interface.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class PromiseHandler<REQUEST,RESPONSE> implements Promise<REQUEST,RESPONSE>,
	SingleEventPublisher{

	private String flowName 							= PromiseHandler.class.getSimpleName();
	private boolean isEnabled							= true;
	private FlowPublisherType flowPublisherType			= FlowPublisherType.FLOW_PUBLISHER_CLIENT;
	private FlowFunctionType flowFunctionType 			= FlowFunctionType.EVENT;  
	private FlowType flowType							= FlowType.ENDPOINT;
	
	private ReactiveTarget<REQUEST,RESPONSE> reactiveTarget;
	private String serverIdentifier						= PromiseTypeServer.ServerIdentifier();
	private String processIdentifier					= PromiseTypeServer.ProcessIdentifier();
	
	// Cached Thread pool for executing async tasks 
	private BackPressureExecutorService executorService = BackPressureExecutors.newCachedBackPressureThreadPool();
	private Subscriber<? super Event<EventModel>> subscriber;
	private ProcessHandler processHandler;
	
	@Override
	public void associateProcessHandler(ProcessHandler processHandler) {
		this.processHandler = processHandler;
	}
	
	public ProcessHandler getAssociatedProcessHandler() {
				
		return this.processHandler;
	}
	
	@Override
	public void subscribe(Subscriber<? super Event<EventModel>> subscriber) {
		this.subscriber = subscriber;
	}
	
	@Override
	public PromiseResponse<RESPONSE> handle(PromiseRequest<REQUEST,RESPONSE>
		promiseRequest) throws PromiseException{
		
		PromiseResponse<RESPONSE> promiseResponse = null;
				
		if(ifValid(promiseRequest)){
			
			startup();
			promiseResponse = then(promiseRequest); 			
		}else{
			
			PromiseException promiseException = new PromiseException(
					ExceptionConstants.ERR_REQUEST_INVALID,
					ExceptionMessages.MSG_REQUEST_INVALID,
					"Either RequestScale or DeferableRequest not set for PromiseRequest : "+promiseRequest);
			throw promiseException;
		}
		
		return promiseResponse;
	}
	
	
	
	@Override
	public boolean ifValid(PromiseRequest<REQUEST,RESPONSE> promiseRequest)  {
			
		boolean isValid = false;
				
		if(
				promiseRequest.getPromiseContext().getRequestScale() != null && 
				promiseRequest.getPromiseContext().getDeferableRequest() != null
		){
			isValid = true;			
		}
		
		return isValid;
	}

	@Override
	public PromiseResponse<RESPONSE> then(PromiseRequest<REQUEST,RESPONSE> promiseRequest) throws 
		PromiseException {

		PromiseResponse<RESPONSE> promiseResponse = null;
		
		boolean hasProcessingError = false;
			
		try{
		
			promiseResponse = what(promiseRequest);
			
			if(
				(
						promiseResponse.getProgressScale().getPromiseState() == PromiseState.ERROR ||
						promiseResponse.getProgressScale().getPromiseState() == PromiseState.TIMEOUT
				)
				&&
				promiseResponse.getProgressScale().getPromiseStatus() == PromiseStatus.COMPLETED						
			){
				hasProcessingError = true;
			}else{
				hasProcessingError = false;
			}
			
		}catch(PromiseException promiseException){				
			hasProcessingError = true;
		}
		
		if(hasProcessingError){

			promiseResponse.getProgressScale().setPromiseStatus(PromiseStatus.COMPLETED);				
			publishClientEvent(EventType.CLIENT,promiseResponse.getProgressScale());
		}
		
		return promiseResponse;
	}

	@Override
	public PromiseResponse<RESPONSE> what(PromiseRequest<REQUEST,RESPONSE> promiseRequest) throws PromiseException {
		
		PromiseResponse<RESPONSE> promiseResponse = null;

		ProgressScale snapshotScale = new ProgressScale();		
				
		Timeline timeline = new Timeline(snapshotScale);
	
		try {
			
			promiseResponse = iterate(promiseRequest);

			while(
					snapshotScale.getNow() < 100 &&
					snapshotScale.getPromiseState() != PromiseState.FULFILLED  && 
					snapshotScale.getPromiseStatus() != PromiseStatus.COMPLETED					
			){
					
				if(!snapshotScale.equals(promiseResponse.getProgressScale())){
					
					snapshotScale.clone((ProgressScale)promiseResponse.getProgressScale());
					publishClientEvent(EventType.CLIENT,snapshotScale);
					timeline = processTimeline( timeline, snapshotScale) ;	
					
				}else{
					
					promiseResponse = iterate(promiseRequest);	
					Thread.sleep(snapshotScale.getINTERVAL());
				}

			}
						
		} catch (InterruptedException | ExecutionException e) {
			
			// Update notification on error
			promiseResponse.getProgressScale().setPromiseState(PromiseState.ERROR);		
			promiseResponse.getProgressScale().setPromiseState(PromiseState.FULFILLED);
			promiseResponse.getProgressScale().setPromiseStatus(PromiseStatus.COMPLETED);
			publishClientEvent(EventType.CLIENT,promiseResponse.getProgressScale());
			
			e.printStackTrace();
			PromiseException promiseException = new PromiseException(
					ExceptionConstants.ERR_PROCESS_INTERUPPTED,
					ExceptionMessages.MSG_PROCESS_INTERUPPTED,
					"Process Interrupted.");
			throw promiseException;
		}
		
		timeline.setCompletionTime(new Timestamp(new Date().getTime()));
	
		processHandler.publishProcessEvent(EventType.SERVER,Context.ProcessLifecycleContext(serverIdentifier, 
				PromiseServerState.COMPLETE,PromiseServerStatus.RUNNING, snapshotScale.getSubscriptionClientId(),
				this.getClass().getName()));
		
		if(promiseRequest.isTimelineRequired()){		
			
			promiseResponse.setTimeline(timeline);
		}		
		
		return promiseResponse;
	}

	public PromiseResponse<RESPONSE> iterate(PromiseRequest<REQUEST,RESPONSE> 
		promiseRequest) throws PromiseException, InterruptedException, ExecutionException {
		
		PromiseResponse<RESPONSE> promiseResponse = new PromiseResponse<RESPONSE>();
		
		PromiseTask<REQUEST,RESPONSE> promiseTask = 
				new PromiseTask<REQUEST,RESPONSE>(promiseRequest,reactiveTarget);	
		
		executorService.configureBackPressure(promiseRequest.getPromiseContext().getRequestScale());
		
		if(executorService.isConfigured()){
		
			promiseResponse = (PromiseResponse<RESPONSE>) executorService.fallforward(promiseTask);	
			
			if( promiseResponse != null && promiseResponse.getStatus() == ResponseCode.OK.getResponseStatus()){
								
				promiseRequest.setAcknowledgmentIdentifier(
						promiseResponse.getProgressScale().getAcknowledgmentIdentifier());					
			}else if( promiseResponse != null && 
				(	
						promiseResponse.getStatus() == ResponseCode.SERVER_ERROR.getResponseStatus() ||
						promiseResponse.getStatus() == ResponseCode.TYPE_ERROR.getResponseStatus()
				)
			){
				
				promiseResponse = (PromiseResponse<RESPONSE>) executorService.fallback(
						promiseTask,ResponseCode.valueOf(promiseResponse.getStatus()));							
			}else{
				
				promiseResponse = (PromiseResponse<RESPONSE>) executorService.fallback(
						promiseTask,ResponseCode.TIMEOUT);	
			}
			
			//System.out.println("PhasedPromiseHandler : promiseResponse = "+promiseResponse);
			
		}else{
			
			PromiseException promiseException = new PromiseException(
					ExceptionConstants.ERR_CONFIG,
					ExceptionMessages.MSG_CONFIG,
					"BackPressure configuration not set for PromiseRequest : "+promiseRequest);
			throw promiseException;
		}
	
		return promiseResponse;
	}
	
	public void publishClientEvent(EventType eventType, Scale scale) {
		
		ChangeEvent<EventModel> changeEvent = new ChangeEventEntity();
		changeEvent.setEventTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		
		EventContext eventContext = new EventContext();
		eventContext.setPromiseState(scale.getPromiseState());
		eventContext.setPromiseStatus(scale.getPromiseStatus());
		eventContext.setChange(""+scale.getNow());
		
		eventContext.setNotificationDeliveryType(scale.getNotificationDeliveryType());	
	
		/*
		 * Can be part of client dispatch logic as augmentation at the point of delivery 
		 * 
		 NotificationSubscription notificationSubscription = 
				lookupNotificationSubscription(scale.getSubscriptionClientId());
		
		if(notificationSubscription != null){
			
			NotificationProtocolType notificationProtocolType = notificationSubscription.getNotificationProtocolType();
			eventContext.setNotificationProtocolType(notificationProtocolType);
		}else{
			throw new ServerException(ERR_NOTIFICATION_SUBSCRIPTION,MSG_NOTIFICATION_SUBSCRIPTION, 
					"NotificationSubscription does not exists for SubscriptionClientId : "+progressScale.getSubscriptionClientId());
		}*/
		
		EventModel eventModel = new EventModel();
		eventModel.setContext(eventContext);
		eventModel.setReactiveMetaData(scale);
		changeEvent.setChangedModel(eventModel);
		changeEvent.setEventType(eventType);
		
		publishEvent(changeEvent);
		//System.out.println("PromiseHandler : scale : "+scale);

	}
	
	@Override
	public Timeline processTimeline(Timeline timeline, Scale progressScale) {
				
		if(	 (new Date().getTime() - timeline.getStartTime().getTime() )
				>= progressScale.getINTERVAL() 
		){
			
			timeline.addToTimeline(progressScale.getNow()); 					
		}
		
		return timeline;
	}

	@Override
	public void startup(){

		processHandler.publishProcessEvent(EventType.SERVER,Context.ProcessLifecycleContext(serverIdentifier, 
				PromiseServerState.REGISTERED,PromiseServerStatus.STARTED, processIdentifier,this.getClass().getName()));
	}

	@Override
	public void shutdown() {
		executorService.shutdown();		
		
		if(!executorService.isTerminated()){
			executorService.shutdownNow();			
		}
		processHandler.publishProcessEvent(EventType.SERVER,Context.ProcessLifecycleContext(serverIdentifier, 
				PromiseServerState.UNREGISTERED,PromiseServerStatus.SHUTDOWN, processIdentifier,this.getClass().getName()));

	}
	
	public ReactiveTarget<REQUEST, RESPONSE> getReactiveTarget() {
		return reactiveTarget;
	}

	public void setReactiveTarget(ReactiveTarget<REQUEST, RESPONSE> reactiveTarget) {
		this.reactiveTarget = reactiveTarget;
		
		if(reactiveTarget.getServerIdentifier() != null){
			serverIdentifier = reactiveTarget.getServerIdentifier();
		}		
		
	}
	
	@Override
	public void setFlowName(String listenerName) {
		this.flowName = listenerName;
	}

	@Override
	public String getFlowName() {
		return this.flowName;
	}

		
	@Override
	public void setFlowType(FlowType listenerType) {
		this.flowType = listenerType;
	}

	@Override
	public FlowType getFlowType() {
		return this.flowType;
	}

	@Override
	public void setFlowFunctionType( FlowFunctionType 
		listenerFunctionType) {
		this.flowFunctionType = listenerFunctionType;
	}

	@Override
	public FlowFunctionType getFlowFunctionType() {
		return this.flowFunctionType;
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled=isEnabled;
	}

	@Override
	public boolean isEnabled() {
		return this.isEnabled;
	}

	@Override
	public void publishEvent(Event<EventModel> event) {
		
		System.out.println("PromiseHandler : publishEvent = "+event);
		this.subscriber.onNext(event);
	}

	@Override
	public void setFlowPublisherType(FlowPublisherType flowPublisherType) {
		this.flowPublisherType = flowPublisherType;
	}

	@Override
	public FlowPublisherType getFlowPublisherType() {
		return this.flowPublisherType;
	}

	@Override
	public boolean isSubscribed() {
		
		return (this.subscriber != null);
	}
}
