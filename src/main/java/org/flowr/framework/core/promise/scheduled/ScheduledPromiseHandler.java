
/**
 * Provides concrete implementation of ScheduledPromise. Overrides the what
 * implementation of PromiseHandler for scheduler implementation.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.promise.scheduled;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.Flow.Subscriber;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.context.Context;
import org.flowr.framework.core.context.Context.EventContext;
import org.flowr.framework.core.context.ContextBuilder.EventContextBuilder;
import org.flowr.framework.core.event.ChangeEvent;
import org.flowr.framework.core.event.ChangeEventEntity;
import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.node.Autonomic.ResponseCode;
import org.flowr.framework.core.node.ha.BackPressureExecutorService;
import org.flowr.framework.core.node.ha.BackPressureExecutors;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter.AdapterFlowConfig;
import org.flowr.framework.core.process.management.ProcessHandler;
import org.flowr.framework.core.promise.PromiseRequest;
import org.flowr.framework.core.promise.PromiseResponse;
import org.flowr.framework.core.promise.PromiseTypeServer;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.Timeline;
import org.flowr.framework.core.service.extension.PromiseService.PromiseServerState;
import org.flowr.framework.core.service.extension.PromiseService.PromiseServerStatus;
import org.flowr.framework.core.target.ReactiveTarget;

public class ScheduledPromiseHandler implements ScheduledPromise{

    private boolean isEnabled                           = true;
     
    private ReactiveTarget reactiveTarget;
    private long scheduledDelay;
    private String serverIdentifier                     = PromiseTypeServer.serverIdentifier();
    private String processIdentifier                    = PromiseTypeServer.processIdentifier();
    // Chached Thread pool for executing async tasks 
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
    public PromiseResponse handle(PromiseRequest promiseRequest) throws PromiseException{
        
        PromiseResponse promiseResponse = null;
                
        if(ifValid(promiseRequest)){
            
            startup();
            promiseResponse = then(promiseRequest);             
        }else{
            
            throw new PromiseException(
                    ErrorMap.ERR_REQUEST_INVALID,
                    "Either RequestScale or DeferableRequest not set for PromiseRequest : "+promiseRequest);
        }
        
        return promiseResponse;
    }
    
    
    
    @Override
    public boolean ifValid(PromiseRequest promiseRequest)  {
            
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
    public PromiseResponse then(PromiseRequest promiseRequest) throws PromiseException {

        PromiseResponse promiseResponse = null;
        
        boolean hasProcessingError = false;
        
        try{
        
            promiseResponse = what(promiseRequest);
            
            if(
                    (
                            promiseResponse.getProgressScale().getPromiseState() == PromiseState.ERROR ||
                            promiseResponse.getProgressScale().getPromiseState() == PromiseState.TIMEOUT
                    ) &&
                promiseResponse.getProgressScale().getPromiseStatus() == PromiseStatus.COMPLETED                        
            ){
                hasProcessingError = true;

            }else{
                hasProcessingError = false;
            }
            
        }catch(PromiseException promiseException){              
            hasProcessingError = true;
            Logger.getRootLogger().error(promiseException);
        }
        
        
        if(hasProcessingError){
            
            Logger.getRootLogger().info("ScheduledPromiseHandler : hasProcessingError = "+hasProcessingError);
            
            promiseResponse = new PromiseResponse();
            promiseResponse.getProgressScale().setPromiseStatus(PromiseStatus.COMPLETED);               
            publishClientEvent(EventType.CLIENT,promiseResponse.getProgressScale());
        }
        
        return promiseResponse;
    }
    
    
    @Override
    public long determineSchedule(ScheduledProgressScale snapshotScale){
        
                
        Timestamp current = new Timestamp(Instant.now().toEpochMilli());
        
        if(snapshotScale.getScheduleStatus() == ScheduleStatus.REGISTERED && 
                snapshotScale.getScheduledTimestamp() !=null){
            
            long scheduledTime = snapshotScale.getScheduledTimestamp().getTime();
            
            scheduledDelay = scheduledTime - current.getTime();
            
            Logger.getRootLogger().info("\t ScheduledPromiseHandler : scheduledDelay : "+scheduledDelay+
                    " | scheduledTime "+scheduledTime+" | current : "
                    +current.getTime());
        }else if(snapshotScale.getScheduleStatus() == ScheduleStatus.DEFFERED && 
                snapshotScale.getDeferredTimestamp() != null){
            
            long deferredTime = snapshotScale.getDeferredTimestamp().getTime();
            
            scheduledDelay = deferredTime - current.getTime();
            
            Logger.getRootLogger().info("\t ScheduledPromiseHandler : scheduledDelay : "+scheduledDelay+
                    " | deferredTime "+deferredTime+" | current : "
                    +current.getTime());
        }else{
            scheduledDelay = 0;
        }
        
        return scheduledDelay;
    }
    
    @Override
    public PromiseResponse what(PromiseRequest promiseRequest) throws PromiseException {
        
        PromiseResponse promiseResponse = null;

        ScheduledProgressScale snapshotScale = new ScheduledProgressScale();
        snapshotScale.setScaleOption(promiseRequest.getPromiseContext().getRequestScale().getScaleOption());
        
        Timeline timeline = new Timeline(promiseRequest.getPromiseContext().getRequestScale().getScaleOption());
    
        try {
                        
            promiseResponse = iterate(promiseRequest);
            snapshotScale   = (ScheduledProgressScale) promiseResponse.getProgressScale();  
            
            while(
                    snapshotScale.getScaleOption().getNow() < 100 &&
                    snapshotScale.getPromiseState()   != PromiseState.FULFILLED  && 
                    snapshotScale.getPromiseStatus()  != PromiseStatus.COMPLETED    && 
                    snapshotScale.getScheduleStatus() != ScheduleStatus.OVER
            ){
                        
                // Determine schedule
                scheduledDelay = determineSchedule(snapshotScale);
                                
                if(!snapshotScale.equals(promiseResponse.getProgressScale())){
                    
                    snapshotScale.clone(promiseResponse.getProgressScale());    
                    publishClientEvent(EventType.CLIENT,snapshotScale);
                    timeline = processTimeline( timeline, promiseResponse.getProgressScale()) ; 
                }else{
                    
                    promiseResponse = iterate(promiseRequest);      
                    
                    waitForScheduledDelay(promiseRequest, scheduledDelay);
                }               
            }
                        
        } catch (InterruptedException | PromiseException e) {
            
            Thread.currentThread().interrupt();
            
            promiseResponse = new PromiseResponse();
            promiseResponse.getProgressScale().setPromiseState(PromiseState.ERROR);     
            promiseResponse.getProgressScale().setPromiseState(PromiseState.FULFILLED);
            promiseResponse.getProgressScale().setPromiseStatus(PromiseStatus.COMPLETED);
            publishClientEvent(EventType.CLIENT,promiseResponse.getProgressScale());
            
            Logger.getRootLogger().error(e);

            throw new PromiseException(
                    ErrorMap.ERR_PROCESS_INTERUPPTED,
                    "Process Interrupted.",
                    e
                );
        }
        
        timeline.setCompletionTime(new Timestamp(Instant.now().toEpochMilli()));
        
        processHandler.publishProcessEvent(EventType.SERVER,Context.getProcessLifecycleContext(serverIdentifier, 
                PromiseServerState.COMPLETE,PromiseServerStatus.RUNNING, snapshotScale.getSubscriptionClientId(),
                this.getClass().getName()));
        
        if(promiseRequest.isTimelineRequired()){        
            
            promiseResponse.setTimeline(timeline);
        }       
        
        return promiseResponse;
    }

    private static void waitForScheduledDelay(PromiseRequest promiseRequest, long scheduledDelay) 
        throws InterruptedException {
       
        // sleep till the schedule arrives before times out             
        if(scheduledDelay > 0 &&  scheduledDelay < promiseRequest.getPromiseContext().getRequestScale().getTimeout()){
            
           Thread.sleep(scheduledDelay);                   
 
        }
    }
    
    @Override
    public PromiseResponse iterate(PromiseRequest promiseRequest) throws PromiseException{
        
        PromiseResponse promiseResponse = null;
        
        ScheduledPromiseTask promiseTask = new ScheduledPromiseTask(promiseRequest,reactiveTarget);
        
        RequestScale requestScale = promiseRequest.getPromiseContext().getRequestScale();
        
        requestScale.setRetryInterval(Math.abs(scheduledDelay));
                
        executorService.configureBackPressure(promiseRequest.getPromiseContext().getRequestScale());
        
        if(executorService.isConfigured()){
        
            try {
                
                promiseResponse = executorService.fallforward(promiseTask); 
                
                if( promiseResponse != null && promiseResponse.getStatus() == ResponseCode.OK.getResponseStatus()){
                                    
                    promiseRequest.setAcknowledgmentIdentifier(
                            promiseResponse.getProgressScale().getAcknowledgmentIdentifier());                  
                }else if( promiseResponse != null && 
                        (   
                            promiseResponse.getStatus() == ResponseCode.SERVER_ERROR.getResponseStatus() ||
                            promiseResponse.getStatus() == ResponseCode.TYPE_ERROR.getResponseStatus()
                        )
                ){
                    
                    promiseResponse = executorService.fallback(promiseTask,promiseRequest,
                                           ResponseCode.valueOf(promiseResponse.getStatus()));                          
                }else{
                    
                    promiseResponse = executorService.fallback(promiseTask,promiseRequest,ResponseCode.TIMEOUT);   
                }
                
            } catch (ConfigurationException e) {
                
                Logger.getRootLogger().error(e);
                Thread.currentThread().interrupt();
                throw new PromiseException(
                        ErrorMap.ERR_CONFIG,
                        e.getMessage(),
                        e);
            }  

            
        }else{

            throw new PromiseException(
                    ErrorMap.ERR_CONFIG,
                    "BackPressure configuration not set for PromiseRequest : "+promiseRequest);
        }
        
        return promiseResponse;
    }
    
    @Override
    public Timeline processTimeline(Timeline timeline, Scale progressScale) {
                
        if(  (Instant.now().toEpochMilli() - timeline.getStartTime().getTime() )
                >= progressScale.getScaleOption().getInterval() 
        ){
            
            timeline.addToTimeline(progressScale.getScaleOption().getNow());                     
        }
        
        return timeline;
    }

    public void publishClientEvent(EventType eventType, Scale scale) {
        
        ChangeEvent<EventModel> changeEvent = new ChangeEventEntity();
        changeEvent.setEventTimestamp(new Timestamp(Instant.now().toEpochMilli()));
        changeEvent.setSubscriptionClientId(scale.getSubscriptionClientId());
        
        EventContext eventContext = null;  
        
        try {
            eventContext =   new EventContextBuilder()
                                .withIdentifierAs(scale.getSubscriptionClientId())
                                .withPromiseStateAs(scale.getPromiseState())
                                .withPromiseStatusAs(scale.getPromiseStatus())
                                .withNotificationDeliveryTypeAs(scale.getNotificationDeliveryType())
                                .withChangeAs(""+scale.getScaleOption().getNow())
                                .withScheduleStatusAs(((ScheduledProgressScale) scale).getScheduleStatus())
                                .build();
            
        } catch (ConfigurationException e) {
            Logger.getRootLogger().error(e);
        }
        
        EventModel eventModel = new EventModel();
        eventModel.setContext(eventContext);
        eventModel.setReactiveMetaData(scale);
        changeEvent.setChangedModel(eventModel);
        changeEvent.setEventType(eventType);
        
        Logger.getRootLogger().info("ScheduledPromiseHandler : changeEvent : "+changeEvent);
        publishEvent(changeEvent);
    }

    @Override
    public void startup(){
        
        processHandler.publishProcessEvent(EventType.SERVER,Context.getProcessLifecycleContext(serverIdentifier, 
                PromiseServerState.REGISTERED,PromiseServerStatus.STARTED, processIdentifier,
                this.getClass().getName()));
    }

    @Override
    public void shutdown() {
        executorService.shutdown();     
        
        if(!executorService.isTerminated()){
            executorService.shutdownNow();          
        }
        
        processHandler.publishProcessEvent(EventType.SERVER,Context.getProcessLifecycleContext(serverIdentifier, 
                PromiseServerState.UNREGISTERED,PromiseServerStatus.SHUTDOWN, processIdentifier,
                this.getClass().getName()));
    }
    
    public ReactiveTarget getReactiveTarget() {
        return reactiveTarget;
    }

    public void setReactiveTarget(ReactiveTarget reactiveTarget) {
        
        this.reactiveTarget = reactiveTarget;
        
        if(reactiveTarget.getServerIdentifier() != null){
            serverIdentifier = reactiveTarget.getServerIdentifier();
        }       
        
    }
    
    
    @Override
    public AdapterFlowConfig getAdapterFlowConfig() {
        
        return new AdapterFlowConfig(
                this.getClass().getSimpleName(),
                FlowPublisherType.FLOW_PUBLISHER_CLIENT,
                FlowFunctionType.EVENT,
                FlowType.ENDPOINT
            );
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
        subscriber.onNext(event);
    }
    
    @Override
    public boolean isSubscribed() {
        
        return (this.subscriber != null);
    }
}

