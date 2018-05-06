package org.flowr.framework.core.notification;

import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.flow.BatchEventPublisher;
import org.flowr.framework.core.service.ServiceLifecycle;

/**
 * Extends EventPublisher to provide Notification Adapter configuration  required for notification.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NotificationServiceAdapter extends BatchEventPublisher, ServiceLifecycle{

	/*
	 * Provides Event handling integration capability based on the Event type qualified as CLIENT or SERVER
	 * CLIENT : Defined for discrete implementation of Notification servicing client side notification
	 * SERVER : Defined for discrete implementation of Notification servicing server side notification
	 */
	public enum NotificationServiceAdapterType{
		CLIENT,
		SERVER;
		
		public boolean equals(EventType eventType){
				
			boolean isEqual = false;
			
			if(this.name().equals(eventType.toString())){
				isEqual = true;
			}
			
			return isEqual;
		}
	}
	
	public void setNotificationServiceAdapterType(NotificationServiceAdapterType notificationServiceAdapterType);
	
	public NotificationServiceAdapterType getNotificationServiceAdapterType();
	
	/**
	 * Weaves NotificationTask to the adapter to address different NotificationProtocolTypes
	 * @param notificationTask
	 */
	public void setNotificationTask(NotificationTask notificationTask);
	
	public void setNotificationServiceAdapterName(String notificationServiceAdapterName);
	
	public String getNotificationServiceAdapterName();
	
}
