package org.flowr.framework.core.notification;

import java.util.ArrayList;
import java.util.HashSet;

import org.flowr.framework.core.context.EventContext;
import org.flowr.framework.core.context.ProcessContext;
import org.flowr.framework.core.context.ServerContext;
import org.flowr.framework.core.event.ChangeEvent;
import org.flowr.framework.core.event.ReactiveMetaData;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.ServerException;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.subscription.Subscription;
import org.flowr.framework.core.promise.Scale;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NotificationHelper extends Subscription{

	
	public void bindRoutes(HashSet<NotificationRoute<NotificationServiceAdapter,NotificationProtocolType>> routeSet) throws ConfigurationException;
	
	public void bindNotificationRoute(NotificationProtocolType notificationProtocolType,
		NotificationServiceAdapter responseServiceAdapter) throws ConfigurationException;

	public ArrayList<NotificationServiceAdapter> getNotificationRoute(ChangeEvent<EventModel> event) 
		throws ServerException;
	
	public HashSet<NotificationRoute<NotificationServiceAdapter,NotificationProtocolType>> getNotificationRoutes();
	
	public ChangeEvent<EventModel> convert(EventContext eventContext) throws ServerException;
	
	public ChangeEvent<EventModel> onPromiseChange(String subscriptionClientId,Scale progressScale, ReactiveMetaData changeMetaData) 
			throws ServerException;
	
	public ChangeEvent<EventModel> onProcessChange(String subscriptionClientId,ProcessContext processContext, ReactiveMetaData changeMetaData) 
			throws ServerException;

	public ChangeEvent<EventModel> onServiceChange(String subscriptionClientId,ServerContext serverContext, ReactiveMetaData changeMetaData)
			throws ServerException;
}
