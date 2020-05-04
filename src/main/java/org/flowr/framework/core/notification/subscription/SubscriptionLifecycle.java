
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.notification.subscription;

import java.util.Collection;

import org.flowr.framework.core.context.Context.SubscriptionContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;

public interface SubscriptionLifecycle {

    SubscriptionContext register(SubscriptionContext subscriptionContext) throws ConfigurationException;
    
    SubscriptionContext deregister(SubscriptionContext subscriptionContext) throws ConfigurationException;
    
    SubscriptionContext change(SubscriptionContext subscriptionContext) throws ConfigurationException;

    Collection<NotificationSubscription> getNotificationProtocolTypeList(
            NotificationProtocolType notificationProtocolType);
}
