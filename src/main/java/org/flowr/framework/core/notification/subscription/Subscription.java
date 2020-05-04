
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

public interface Subscription {

    NotificationSubscription lookupNotificationSubscription(String subscriptionId);
    
    SubscriptionContext subscribe(SubscriptionContext subscriptionContext) throws ConfigurationException;
    
    SubscriptionContext unsubscribe(SubscriptionContext subscriptionContext) throws ConfigurationException;
    
    SubscriptionContext onSubscriptionChange(SubscriptionContext subscriptionContext) 
            throws ConfigurationException;

    Collection<NotificationSubscription> getNotificationProtocolTypelist(
            NotificationProtocolType notificationProtocolType);

    NotificationSubscription lookup(String subscriptionId);
    
}
