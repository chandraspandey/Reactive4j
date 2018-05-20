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

public interface SubscriptionLifecycle {

	public SubscriptionContext register(SubscriptionContext subscriptionContext) throws ConfigurationException;
	
	public SubscriptionContext deregister(SubscriptionContext subscriptionContext) throws ConfigurationException;
	
	public SubscriptionContext change(SubscriptionContext subscriptionContext) throws ConfigurationException;

	public Collection<NotificationSubscription> getNotificationProtocolTypeList(
			NotificationProtocolType notificationProtocolType);
}
