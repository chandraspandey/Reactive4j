
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.internal;

import java.util.Collection;

import org.flowr.framework.core.context.Context.SubscriptionContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.service.FrameworkService;

public interface SubscriptionService extends FrameworkService{
    
    SubscriptionContext subscribeNotification(SubscriptionContext subscriptionContext) 
            throws ConfigurationException;
    
    SubscriptionContext unsubscribeNotification(SubscriptionContext subscriptionContext)
            throws ConfigurationException;

    SubscriptionContext changeNotificationSubscription(SubscriptionContext subscriptionContext)
            throws ConfigurationException;
    
    Collection<NotificationSubscription> getNotificationProtocolTypelist(
            NotificationProtocolType notificationProtocolType);
    
    NotificationSubscription lookup(String subscriptionId);
    
    static SubscriptionService getInstance() {
        
        return DefaultSubscriptionService.getInstance();
    }
    
    public final class DefaultSubscriptionService{
        
        private static SubscriptionService subscriptionService;
        
        private DefaultSubscriptionService() {
            
        }
        
        public static SubscriptionService getInstance() {
            
            if(subscriptionService == null) {
                subscriptionService = new SubscriptionServiceImpl();
            }
            
            return subscriptionService;
        }
        
    }

    


}
