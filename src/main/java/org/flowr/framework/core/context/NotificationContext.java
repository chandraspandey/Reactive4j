package org.flowr.framework.core.context;

import java.util.HashMap;
import java.util.Properties;

import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.NotificationBufferQueue;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.service.Service.ServiceType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class NotificationContext implements Context {

	private ServiceType serviceType = ServiceType.NOTIFICATION;
	//private Event<EventModel> event;
	private NotificationBufferQueue notificationBufferQueue;
	private ServiceConfiguration serviceConfiguration;
	private Properties notificationProperties;	
	private HashMap<NotificationProtocolType,NotificationSubscription> notificationSubscriptionMap;
	
	/*public Event<EventModel> getEvent() {
		return event;
	}
	public void setEvent(Event<EventModel> event) {
		this.event = event;
	}*/

	public ServiceConfiguration getServiceConfiguration() {
		return serviceConfiguration;
	}
	public void setServiceConfiguration(ServiceConfiguration serviceConfiguration) {
		this.serviceConfiguration = serviceConfiguration;
	}
	public Properties getNotificationProperties() {
		return notificationProperties;
	}
	public void setNotificationProperties(Properties notificationProperties) {
		this.notificationProperties = notificationProperties;
	}

	public HashMap<NotificationProtocolType,NotificationSubscription> getNotificationSubscriptionMap() {
		return notificationSubscriptionMap;
	}
	public void setNotificationSubscriptionMap(HashMap<NotificationProtocolType,NotificationSubscription> notificationSubscriptionMap) {
		this.notificationSubscriptionMap = notificationSubscriptionMap;
	}
	public ServiceType getServiceType(){
		return serviceType;
	}
	public NotificationBufferQueue getNotificationBufferQueue() {
		return notificationBufferQueue;
	}
	public void setNotificationBufferQueue(NotificationBufferQueue notificationBufferQueue) {
		this.notificationBufferQueue = notificationBufferQueue;
	}
	
	public String toString(){
		
		return "NotificationContext{"+
			" serviceType : "+serviceType+
			//" | event : "+event+
			" | notificationBufferQueue : "+notificationBufferQueue+
			" | serviceConfiguration : "+serviceConfiguration+
			" | notificationProperties : "+notificationProperties+
			" | notificationSubscriptionList : "+notificationSubscriptionMap+
			" | }\n";
	}




}
