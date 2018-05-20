package org.flowr.framework.core.notification.subscription;

import java.util.Collection;

import org.flowr.framework.core.context.SubscriptionContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Subscription {

	public NotificationSubscription lookupNotificationSubscription(String subscriptionId);
	
	public SubscriptionContext subscribe(SubscriptionContext subscriptionContext) throws ConfigurationException;
	
	public SubscriptionContext unsubscribe(SubscriptionContext subscriptionContext) throws ConfigurationException;
	
	public SubscriptionContext onSubscriptionChange(SubscriptionContext subscriptionContext) 
			throws ConfigurationException;

	public Collection<NotificationSubscription> getNotificationProtocolTypelist(
			NotificationProtocolType notificationProtocolType);

	public NotificationSubscription lookup(String subscriptionId);
	
}
