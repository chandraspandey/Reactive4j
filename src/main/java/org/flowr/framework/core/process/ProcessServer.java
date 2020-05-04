
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
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
import org.flowr.framework.core.notification.NotificationRoute;
import org.flowr.framework.core.notification.NotificationTask;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.notification.subscription.NotificationSubscription.SubscriptionStatus;
import org.flowr.framework.core.notification.subscription.NotificationSubscription.SubscriptionType;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.ServiceProvider;

public class ProcessServer{
    
    // Server Process configuration
    private String serverSubscriptionId; 
    private ServiceProvider processProvider;
    
    ProcessServer(ServiceProvider processProvider){     
        this.processProvider    = processProvider;
    }
    
    public ProcessServer build(RouteContext serverRouteContext) throws ConfigurationException{
        
        Logger.getRootLogger().info("ProcessServer : serverRouteContext : "+serverRouteContext);
        
        ((ServiceFramework)processProvider).getCatalog().getNotificationService().setNotificationRouteContext(
                serverRouteContext); 
        return this;
    }
    
    public NotificationContext withServerNotificationSubscription(
        Map<NotificationProtocolType,NotificationSubscription> notificationMap) throws ConfigurationException{
    
        NotificationContext serverNotificationContext = new NotificationContext();

        ArrayList<SubscriptionContext> subscriptionContextList = new ArrayList<>();
        
        if(notificationMap!= null && !notificationMap.isEmpty()){           
            
            serverNotificationContext.setNotificationSubscriptionMap(notificationMap);
            
            Iterator<Entry<NotificationProtocolType, NotificationSubscription>> iter = 
                    serverNotificationContext.getNotificationSubscriptionMap().entrySet().iterator();
            
            while(iter.hasNext()){
                
               Entry<NotificationProtocolType, NotificationSubscription> entry = iter.next();
                
               NotificationProtocolType notificationProtocolType = entry.getKey();
                
               NotificationSubscription notificationSubscription = entry.getValue();
                     
               if(
                       verifyAndProcessServerNotification(subscriptionContextList, 
                               notificationProtocolType, notificationSubscription)
               ) {
                   break;
               }else {
                  
                   throw new ConfigurationException(
                           ErrorMap.ERR_CONFIG,
                           "Server configuration mismatch for NotificationProtocolType in NotificationSubscription."
                   );
               }
            }
            
        }else{
            
            throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,"NotificationSubscription List can not be empty.");
        }
        
        Logger.getRootLogger().info(" notificationSubscriptionList : "+subscriptionContextList);
        
        return serverNotificationContext;
    }

    private boolean verifyAndProcessServerNotification(ArrayList<SubscriptionContext> subscriptionContextList,
            NotificationProtocolType notificationProtocolType, NotificationSubscription notificationSubscription)
            throws ConfigurationException {
        
        boolean isProcessed = false;
        
        if(notificationProtocolType == notificationSubscription.getNotificationProtocolType()){
            
            if(notificationProtocolType instanceof ServerNotificationProtocolType
                    && notificationSubscription.getSubscriptionType() == SubscriptionType.SERVER && 
                    notificationSubscription.getNotificationProtocolType() == 
                    ServerNotificationProtocolType.SERVER_ALL){
                
                SubscriptionContext serverSubscriptionContext = new SubscriptionContext();      
                
                serverSubscriptionContext.setSubscriptionStatus(SubscriptionStatus.REQUESTED);
                serverSubscriptionContext.setNotificationSubscription(notificationSubscription);
                
                serverSubscriptionContext = ((ServiceFramework)processProvider).getCatalog().getSubscriptionService()
                        .subscribeNotification(serverSubscriptionContext);
                
                serverSubscriptionId = serverSubscriptionContext.getSubcriptionId();
                
                notificationSubscription.setSubscriptionId(serverSubscriptionId);
                
                serverSubscriptionContext.setNotificationSubscription(notificationSubscription);
                
                subscriptionContextList.add(serverSubscriptionContext);
                
                // Only one subscription with ALL is sufficient
                isProcessed = true;

            }else{
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Server configuration can only subscribe for SubscriptionType.SERVER with "
                        + "NotificationProtocolType of Notification.ServerNotificationProtocolType as ALL : "+
                        notificationProtocolType);
   
            }
        }else{
            isProcessed = false;            
        }
        
        return isProcessed;
    }
    
    public RouteContext withServerNotificationTask(NotificationTask notificationTask, NotificationServiceAdapter 
        notificationServiceAdapter,NotificationContext serverNotificationContext) throws ConfigurationException{

        RouteContext serverRouteContext = new RouteContext();
        
        if(serverNotificationContext != null && serverNotificationContext.getNotificationSubscriptionMap() != null
                && !serverNotificationContext.getNotificationSubscriptionMap().isEmpty()){
        
            Iterator<Entry<NotificationProtocolType, NotificationSubscription>> notificationSubscriptionIter = 
                    serverNotificationContext.getNotificationSubscriptionMap().entrySet().iterator();
            
            NotificationRoute route = null;
            HashSet<NotificationRoute> routeSet = new HashSet<>();
            
            while(notificationSubscriptionIter.hasNext()){
                
                Entry<NotificationProtocolType, NotificationSubscription> entry = notificationSubscriptionIter.next();
                    
                route = new NotificationRoute();
                
                route.set(notificationServiceAdapter, entry.getKey());
                routeSet.add(route);
            }
            
            serverRouteContext.setRouteSet(routeSet);
            
            notificationServiceAdapter.configure(notificationTask);
        }else{
            throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,
                    "Invalid NotificationContext for Notification Routing : "+serverNotificationContext);

        }
        
        return serverRouteContext;      
    }

    public String getServerSubscriptionId() {
        return serverSubscriptionId;
    }
    
}
