package org.flowr.framework.core.notification;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_NOTIFICATION_SUBSCRIPTION;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_NOTIFICATION_SUBSCRIPTION;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;

import org.flowr.framework.core.constants.ExceptionConstants;
import org.flowr.framework.core.constants.ExceptionMessages;
import org.flowr.framework.core.context.Context;
import org.flowr.framework.core.context.EventContext;
import org.flowr.framework.core.context.ProcessContext;
import org.flowr.framework.core.context.ServerContext;
import org.flowr.framework.core.event.ChangeEvent;
import org.flowr.framework.core.event.ChangeEventEntity;
import org.flowr.framework.core.event.ReactiveMetaData;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.ServerException;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.notification.subscription.SubscriptionHelper;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.deferred.ProgressScale;
import org.flowr.framework.core.promise.phase.PhasedProgressScale;
import org.flowr.framework.core.promise.scheduled.ScheduledProgressScale;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class NotificationEventHelper extends SubscriptionHelper implements NotificationHelper{

	private static NotificationAdapterMapping<NotificationServiceAdapter,NotificationProtocolType> 
		notificationAdapterMapping = new NotificationAdapterMapping<NotificationServiceAdapter,NotificationProtocolType>();


	@Override
	public void bindRoutes(HashSet<NotificationRoute<NotificationServiceAdapter,NotificationProtocolType>> routeSet) 
			throws ConfigurationException {
	
		if(!routeSet.isEmpty()){
				
			Iterator<NotificationRoute<NotificationServiceAdapter,NotificationProtocolType>> routeIter =  
					routeSet.iterator();
	
			if(routeIter.hasNext()){	
				
				NotificationRoute<NotificationServiceAdapter,NotificationProtocolType> notificationRoute =
						routeIter.next();
				
				NotificationServiceAdapter notificationServiceAdapter = notificationRoute.getKey();
				
				NotificationProtocolType notificationProtocolType = notificationRoute.getValues();
									
				notificationAdapterMapping.add(notificationServiceAdapter,notificationProtocolType);
				
			}
			
		}
		
		//System.out.println("NotificationEventHelper : bindRoutes :  "+routeSet);
		
	}
	
	@Override
	public void bindNotificationRoute(NotificationProtocolType notificationProtocolType, NotificationServiceAdapter 
			notificationServiceAdapter) throws ConfigurationException{
				
		notificationAdapterMapping.add(notificationServiceAdapter,notificationProtocolType);
	}
	
	@Override
	public ArrayList<NotificationServiceAdapter> getNotificationRoute(ChangeEvent<EventModel> event) 
			throws ServerException{
		
		ArrayList<NotificationServiceAdapter> routeList 	= null;
		
		NotificationProtocolType notificationProtocolType = null; 
		
		Context context = event.getChangedModel().getContext();
		
		if(context instanceof ProcessContext){
			notificationProtocolType = ((ProcessContext)event.getChangedModel().getContext()).getNotificationProtocolType();
		}else if(context instanceof EventContext){
			notificationProtocolType = ((EventContext)event.getChangedModel().getContext()).getNotificationProtocolType();
		}else if(context instanceof ServerContext){
			notificationProtocolType = ((ServerContext)event.getChangedModel().getContext()).getNotificationProtocolType();			
		}else{
			
			ServerException serverException = new ServerException(
					ExceptionConstants.ERR_SERVER_PROCESSING,
					ExceptionMessages.MSG_SERVER_PROCESSING,
					"Undefined Context for Server Processing : "+context);
			throw serverException;			
		}
		
		EventType eventType = event.getEventType();
		
		
		routeList = notificationAdapterMapping.getRoute(notificationProtocolType,eventType);
		
			
		//System.out.println("notificationAdapterMapping : "+routeList);
		
		return routeList;
	}
	
	
	// Need to confirm to pre defined object types
	@Override
	public ChangeEvent<EventModel> convert(EventContext eventContext) throws ServerException{
		
		ChangeEvent<EventModel> event = null;
		
		Object change = eventContext.getChange();
		
		//System.out.println("EventContext : "+eventContext);
		
		// Reactive MetaData not addressed
		ReactiveMetaData changeMetaData = eventContext.getReactiveMetaData();
		
		String subscriptionClientId = eventContext.getSubscriptionClientId();
		
		if(change != null){
			
			if(change instanceof ProgressScale || change instanceof PhasedProgressScale || change instanceof ScheduledProgressScale){
				event = onPromiseChange(subscriptionClientId, (Scale)change, changeMetaData);
			}else if(change instanceof ProcessContext){
				event = onProcessChange(subscriptionClientId,(ProcessContext) change, changeMetaData);
			}else if(change instanceof ServerContext){
				event = onServiceChange(subscriptionClientId,(ServerContext) change, changeMetaData);
			}else{
				ServerException serverException = new ServerException(
						ExceptionConstants.ERR_SERVER_PROCESSING,
						ExceptionMessages.MSG_SERVER_PROCESSING,
						"Undefined Change for Server Processing : "+change);
				throw serverException;		
			}
			
		}else{
						
			ServerException serverException = new ServerException(
					ExceptionConstants.ERR_SERVER_PROCESSING,
					ExceptionMessages.MSG_SERVER_PROCESSING,
					"Undefined Change for Server Processing : "+change);
			throw serverException;		
		}
		
		//System.out.println("NotificationEventAdapter : Event<EventModel> :"+event);
		
		return event;
	}
	
	@Override
	public ChangeEvent<EventModel> onProcessChange(String subscriptionClientId, ProcessContext processContext, 
		ReactiveMetaData changeMetaData) throws ServerException{
		
		//System.out.println("NotificationEventAdapter : onProcessChange :"+processContext);
		
		ChangeEvent<EventModel> changeEvent = new ChangeEventEntity();
		
		processContext.setServerIdentifier(subscriptionClientId);
		
		NotificationSubscription notificationSubscription = 
				lookupNotificationSubscription(processContext.getServerIdentifier());
		
		if(notificationSubscription != null){
		
			NotificationProtocolType notificationProtocolType = notificationSubscription.getNotificationProtocolType();
		
			processContext.setNotificationProtocolType(notificationProtocolType); 	
		}else{
			throw new ServerException(ERR_NOTIFICATION_SUBSCRIPTION,MSG_NOTIFICATION_SUBSCRIPTION, 
					"NotificationSubscription does not exists for processContext : "+processContext);
		}
		
		changeEvent.setSubscriptionClientId(processContext.getServerIdentifier());
		
		changeEvent.setEventTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		
		
		
		EventModel eventModel = new EventModel();
		eventModel.setReactiveMetaData(changeMetaData);
		eventModel.setContext(processContext);	
	
		changeEvent.setChangedModel(eventModel);
		changeEvent.setEventType(EventType.SERVER);
	
		return changeEvent;
	}
	
	@Override
	public ChangeEvent<EventModel> onServiceChange(String subscriptionClientId,
		ServerContext serverContext, ReactiveMetaData changeMetaData) throws ServerException{
		
		//System.out.println("NotificationEventAdapter : onProcessChange :"+change);
		
		ChangeEvent<EventModel> changeEvent = new ChangeEventEntity();
		
		NotificationSubscription notificationSubscription = 
				lookupNotificationSubscription(serverContext.getSubscriptionClientId());
		
		if(notificationSubscription != null){
		
			NotificationProtocolType notificationProtocolType = notificationSubscription.getNotificationProtocolType();
		
			serverContext.setNotificationProtocolType(notificationProtocolType);		
		}else{
			throw new ServerException(ERR_NOTIFICATION_SUBSCRIPTION,MSG_NOTIFICATION_SUBSCRIPTION, 
					"NotificationSubscription does not exists for serverContext : "+serverContext);
		}
		
		changeEvent.setSubscriptionClientId(serverContext.getSubscriptionClientId());
		
		changeEvent.setEventTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		
		EventModel eventModel = new EventModel();
		eventModel.setReactiveMetaData(changeMetaData);
		
		eventModel.setContext(serverContext);
	
		changeEvent.setChangedModel(eventModel);
		changeEvent.setEventType(EventType.SERVER);
	
		return changeEvent;
	}
	
	@Override
	public ChangeEvent<EventModel> onPromiseChange(String subscriptionClientId,
		Scale progressScale, ReactiveMetaData changeMetaData) throws ServerException{
		
		//System.out.println("NotificationEventAdapter : onPromiseChange :"+change);
		
		ChangeEvent<EventModel> changeEvent = new ChangeEventEntity();
		
		
		changeEvent.setSubscriptionClientId(progressScale.getSubscriptionClientId());
		
		changeEvent.setEventTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));		

		EventContext eventContext = new EventContext();
		eventContext.setPromiseState(progressScale.getPromiseState());
		eventContext.setPromiseStatus(progressScale.getPromiseStatus());
		eventContext.setChange(""+progressScale.getNow());
		
		if(progressScale instanceof ScheduledProgressScale){			
			eventContext.setScheduleStatus(((ScheduledProgressScale) progressScale).getScheduleStatus());
		}
		
		eventContext.setNotificationDeliveryType(progressScale.getNotificationDeliveryType());	
		
		NotificationSubscription notificationSubscription = 
				lookupNotificationSubscription(progressScale.getSubscriptionClientId());
		
		if(notificationSubscription != null){
			
			NotificationProtocolType notificationProtocolType = notificationSubscription.getNotificationProtocolType();
			eventContext.setNotificationProtocolType(notificationProtocolType);
		}else{
			throw new ServerException(ERR_NOTIFICATION_SUBSCRIPTION,MSG_NOTIFICATION_SUBSCRIPTION, 
					"NotificationSubscription does not exists for SubscriptionClientId : "+progressScale.getSubscriptionClientId());
		}
		
		EventModel eventModel =  new EventModel();
		eventModel.setReactiveMetaData(changeMetaData);		
		eventModel.setContext(eventContext);
		
		changeEvent.setChangedModel(eventModel);
		changeEvent.setEventType(EventType.CLIENT);
		
		return changeEvent;
	}

	@Override
	public HashSet<NotificationRoute<NotificationServiceAdapter, NotificationProtocolType>> getNotificationRoutes() {
		return notificationAdapterMapping.getRouteList();
	}
}
