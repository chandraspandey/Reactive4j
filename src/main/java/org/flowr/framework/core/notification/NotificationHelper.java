package org.flowr.framework.core.notification;

import java.util.ArrayList;
import java.util.HashSet;

import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.ServerException;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NotificationHelper {

	
	public void bindRoutes(HashSet<NotificationRoute<NotificationServiceAdapter,NotificationProtocolType>> routeSet) throws ConfigurationException;
	
	public void bindNotificationRoute(NotificationProtocolType notificationProtocolType,
		NotificationServiceAdapter responseServiceAdapter) throws ConfigurationException;

	public ArrayList<NotificationServiceAdapter> getNotificationRoute(NotificationProtocolType notificationProtocolType,
		EventType eventType) throws ServerException;
	
	public ArrayList<NotificationServiceAdapter> getNotificationRoute(EventType eventType) throws ServerException;
	
	public HashSet<NotificationRoute<NotificationServiceAdapter,NotificationProtocolType>> getNotificationRoutes();
	
}
