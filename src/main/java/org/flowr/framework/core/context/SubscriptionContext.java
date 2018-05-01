package org.flowr.framework.core.context;

import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.notification.subscription.NotificationSubscription.SubscriptionStatus;
import org.flowr.framework.core.service.Service.ServiceType;

/**
 * 
 * Provides mechanism to handle subscription change
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class SubscriptionContext implements Context {

	private ServiceType serviceType = ServiceType.SUBSCRIPTION;
	private String subcriptionId;
	private SubscriptionStatus subscriptionStatus;
	private NotificationSubscription notificationSubscription;
	
	public String getSubcriptionId() {
		return subcriptionId;
	}
	public void setSubcriptionId(String subcriptionId) {
		this.subcriptionId = subcriptionId;
	}
	public SubscriptionStatus getSubscriptionStatus() {
		return subscriptionStatus;
	}
	public void setSubscriptionStatus(SubscriptionStatus subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}
	public NotificationSubscription getNotificationSubscription() {
		return notificationSubscription;
	}
	public void setNotificationSubscription(NotificationSubscription notificationSubscription) {
		this.notificationSubscription = notificationSubscription;
	}
	
	public ServiceType getServiceType(){
		return serviceType;
	}
	
	public String toString(){
		
		return "SubscriptionContext{"+
			" serviceType : "+serviceType+
			" | subcriptionId : "+subcriptionId+
			" | subscriptionStatus : "+subscriptionStatus+
			" | notificationSubscription : "+notificationSubscription+
			" | }\n";
	}

}
