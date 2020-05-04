
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.internal;

import java.util.Collection;
import java.util.Optional;
import java.util.Properties;

import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.context.Context.SubscriptionContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.notification.subscription.NotificationSubscription.SubscriptionType;
import org.flowr.framework.core.notification.subscription.Subscription;
import org.flowr.framework.core.notification.subscription.SubscriptionHelper;
import org.flowr.framework.core.service.AbstractService;
import org.flowr.framework.core.service.ServiceProvider;
import org.flowr.framework.core.service.dependency.Dependency.DependencyType;

public class SubscriptionServiceImpl extends AbstractService implements SubscriptionService{

    private Subscription subscriptionHelper                 = new SubscriptionHelper();
    private ServiceConfig serviceConfig                      = new ServiceConfig(
                                                                true,
                                                                ServiceUnit.POOL,
                                                                FrameworkConstants.FRAMEWORK_SERVICE_SUBSCRIPTION,
                                                                ServiceType.SUBSCRIPTION,
                                                                ServiceStatus.UNUSED,
                                                                this.getClass().getSimpleName(),
                                                                DependencyType.MANDATORY
                                                            );

    @Override
    public ServiceConfig getServiceConfig() {    
        return this.serviceConfig;
    }
     
    @Override
    public SubscriptionContext changeNotificationSubscription(SubscriptionContext subscriptionContext) 
            throws ConfigurationException{
                
        SubscriptionContext subContext  = subscriptionHelper.onSubscriptionChange(subscriptionContext);
        
        if(subscriptionContext.getNotificationSubscription().getSubscriptionType() == SubscriptionType.SERVER){
            
            ((ServiceProvider)this.getServiceFramework()).setServerSubscriptionIdentifier(
                    subContext.getSubcriptionId());
        }   
        
        return subContext;  
    }
    
    @Override
    public SubscriptionContext subscribeNotification(SubscriptionContext subscriptionContext) 
            throws ConfigurationException{

        SubscriptionContext subContext  = subscriptionHelper.subscribe(subscriptionContext);
        
        if(subscriptionContext.getNotificationSubscription().getSubscriptionType() == SubscriptionType.SERVER){
            ((ServiceProvider)this.getServiceFramework()).setServerSubscriptionIdentifier(
                    subContext.getSubcriptionId());
        }
        
        return subContext;
    }
    
    @Override
    public SubscriptionContext unsubscribeNotification(SubscriptionContext subscriptionContext) 
            throws ConfigurationException{
        
        return subscriptionHelper.unsubscribe(subscriptionContext);
    }

    @Override
    public Collection<NotificationSubscription> getNotificationProtocolTypelist(
            NotificationProtocolType notificationProtocolType) {
        
        return subscriptionHelper.getNotificationProtocolTypelist(notificationProtocolType);
    }
    
    @Override
    public NotificationSubscription lookup(String subscriptionId) {
        return subscriptionHelper.lookup(subscriptionId);
    }
    
    @Override
    public ServiceStatus startup(Optional<Properties> configProperties) {
        return ServiceStatus.STARTED;
    }

    @Override
    public ServiceStatus shutdown(Optional<Properties> configProperties) {
        return ServiceStatus.STOPPED;
    }
    
    @Override
    public String toString(){
        
        return "SubscriptionService{"+
                " | serviceConfig : "+serviceConfig+    
                " | \n subscriptionHelper : "+subscriptionHelper+
                super.toString()+  
                "}\n";
    }

}
