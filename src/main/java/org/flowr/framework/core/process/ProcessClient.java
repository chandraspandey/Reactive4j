
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.process;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.context.Context.NotificationContext;
import org.flowr.framework.core.context.Context.RouteContext;
import org.flowr.framework.core.context.Context.SubscriptionContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.notification.Notification.ClientNotificationProtocolType;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.NotificationRoute;
import org.flowr.framework.core.notification.NotificationTask;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.notification.subscription.NotificationSubscription.SubscriptionStatus;
import org.flowr.framework.core.notification.subscription.NotificationSubscription.SubscriptionType;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.ServiceProvider;

public class ProcessClient{
    
    // Client configuration
    private static String clientSubscriptionId              = ""+Math.random(); 
    private ServiceProvider processProvider;

    ProcessClient(ServiceProvider processProvider){
        
        this.processProvider    = processProvider;
    }
    
    private static void setSubscriptionId(String subscriptionId) {
        clientSubscriptionId = subscriptionId;
    }
    
    public static String subscriptionId() {
        
        return clientSubscriptionId;
    }
    
    public ProcessClient build(RouteContext clientRouteContext) throws ConfigurationException{

        ((ServiceFramework)processProvider).getCatalog().getNotificationService().setNotificationRouteContext(
                clientRouteContext); 
        return this;
    }
    
    public SubscriptionContext withClientNotificationSubscription(NotificationSubscription 
        notificationSubscription) throws ConfigurationException{    
        
        SubscriptionContext clientSubscriptionContext = new SubscriptionContext();  
        
        if(notificationSubscription!= null ){           
            
            clientSubscriptionContext.setSubscriptionStatus(SubscriptionStatus.REQUESTED);
            clientSubscriptionContext.setNotificationSubscription(notificationSubscription);
            
            clientSubscriptionContext = ((ServiceFramework)processProvider).getCatalog().getSubscriptionService().
                    subscribeNotification(clientSubscriptionContext);
            
            setSubscriptionId(clientSubscriptionContext.getSubcriptionId());
            
            notificationSubscription.setSubscriptionId(clientSubscriptionId);
            
            clientSubscriptionContext.setNotificationSubscription(notificationSubscription);
            
        }else{
            
            throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG, "NotificationSubscription List can not be empty.");
        }
            
        return clientSubscriptionContext;
    }
    
    public NotificationContext andClientNotificationSubscription(Map<NotificationProtocolType,
            NotificationSubscription> notificationMap) throws ConfigurationException{
    
        NotificationContext clientNotificationContext = new NotificationContext();      
        
        ArrayList<SubscriptionContext> subscriptionContextList = new ArrayList<>();
        
        if(notificationMap!= null && !notificationMap.isEmpty()){           
            
            clientNotificationContext.setNotificationSubscriptionMap(notificationMap);
            
            Iterator<Entry<NotificationProtocolType, NotificationSubscription>> notificationSubscriptionIter = 
                    clientNotificationContext.getNotificationSubscriptionMap().entrySet().iterator();
            
            while(notificationSubscriptionIter.hasNext()){
                
                Entry<NotificationProtocolType, NotificationSubscription> entry = 
                        notificationSubscriptionIter.next();
                
                NotificationProtocolType notificationProtocolType = entry.getKey();
                
                NotificationSubscription notificationSubscription = entry.getValue();

                
                if(!verifyAndProcessClientNotification(subscriptionContextList, notificationProtocolType,
                        notificationSubscription)){
                    
                    throw new ConfigurationException(
                            ErrorMap.ERR_CONFIG,
                            "Client configuration mismatch for NotificationProtocolType in NotificationSubscription."
                    );
                }
                
            }
            
        }else{
            
            throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,"NotificationSubscription List can not be empty.");
        }
        
        return clientNotificationContext;
    }

    private boolean verifyAndProcessClientNotification(ArrayList<SubscriptionContext> subscriptionContextList,
            NotificationProtocolType notificationProtocolType, NotificationSubscription notificationSubscription)
            throws ConfigurationException {
        
        boolean isProcessed = false;
        
        if(notificationProtocolType == notificationSubscription.getNotificationProtocolType()){
        
            if(notificationProtocolType instanceof ClientNotificationProtocolType
                    && notificationSubscription.getSubscriptionType() == SubscriptionType.CLIENT){
            
                SubscriptionContext clientSubscriptionContext = new SubscriptionContext();      
                
                clientSubscriptionContext.setSubscriptionStatus(SubscriptionStatus.REQUESTED);
                clientSubscriptionContext.setNotificationSubscription(notificationSubscription);
                
                clientSubscriptionContext = ((ServiceFramework)processProvider).getCatalog().getSubscriptionService()
                        .subscribeNotification(clientSubscriptionContext);                      
      
                setSubscriptionId(clientSubscriptionContext.getSubcriptionId());
                
                notificationSubscription.setSubscriptionId(clientSubscriptionId);
                
                clientSubscriptionContext.setNotificationSubscription(notificationSubscription);
                
                subscriptionContextList.add(clientSubscriptionContext);

                isProcessed = true;
            }else{
                
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Client configuration can only subscribe for NotificationProtocolType of "
                            + "Notification.ClientNotificationProtocolType : "+notificationProtocolType);
            }
        }else{
            
            isProcessed = false;
        }
        
        return isProcessed;
    }
    
    public RouteContext andClientNotificationTask(NotificationTask notificationTask,
            NotificationServiceAdapter notificationServiceAdapter,
            NotificationContext clientNotificationContext) throws ConfigurationException{
        
        RouteContext clientRouteContext = new RouteContext();
        
        if(clientNotificationContext != null && clientNotificationContext.getNotificationSubscriptionMap() != null &&
                !clientNotificationContext.getNotificationSubscriptionMap().isEmpty()){
            
            Iterator<Entry<NotificationProtocolType, NotificationSubscription>> notificationSubscriptionIter = 
                    clientNotificationContext.getNotificationSubscriptionMap().entrySet().iterator();
            
            NotificationRoute route = null;
            HashSet<NotificationRoute> routeSet = new HashSet<>();
            
            while(notificationSubscriptionIter.hasNext()){
                
                Entry<NotificationProtocolType, NotificationSubscription> entry = notificationSubscriptionIter.next();

                route = new NotificationRoute();
                
                route.set(notificationServiceAdapter, entry.getKey());
                
                Logger.getRootLogger().info("ProcessClient : andClientNotificationTask : route : "+route);
                
                routeSet.add(route);
            }           
            
            clientRouteContext.setRouteSet(routeSet);
            
            notificationServiceAdapter.configure(notificationTask);
        }else{
            
            throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,
                    "Invalid NotificationContext for Notification Routing : "+clientNotificationContext);
        }
        
        return clientRouteContext;      
    }
}
