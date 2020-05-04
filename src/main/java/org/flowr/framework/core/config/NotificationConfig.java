
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config;

import java.util.Map;

import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.NotificationTask;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;

public class  NotificationConfig{
    
    private Map<NotificationProtocolType,NotificationSubscription> subscriptionMap;
    private NotificationServiceAdapter notificationServiceAdapter;
    private NotificationTask notificationTask;
    
    public NotificationConfig() {
        
    }
    
    public NotificationConfig(Map<NotificationProtocolType, NotificationSubscription> subscriptionMap,
            NotificationServiceAdapter notificationServiceAdapter, NotificationTask notificationTask) {
        this.subscriptionMap = subscriptionMap;
        this.notificationServiceAdapter = notificationServiceAdapter;
        this.notificationTask = notificationTask;
    }
    
    public boolean isValidConfig() {
    
        boolean isValidConfig = false;
        
        if( 
            subscriptionMap != null && !subscriptionMap.isEmpty() && 
            notificationServiceAdapter != null && notificationTask != null 
        ){  
            
            isValidConfig = true;
        }
        
        return isValidConfig;
    }

    public Map<NotificationProtocolType, NotificationSubscription> getSubscriptionMap() {
        return subscriptionMap;
    }

    public void setSubscriptionMap(Map<NotificationProtocolType, NotificationSubscription> subscriptionMap) {
        this.subscriptionMap = subscriptionMap;
    }

    public NotificationServiceAdapter getNotificationServiceAdapter() {
        return notificationServiceAdapter;
    }

    public void setNotificationServiceAdapter(NotificationServiceAdapter notificationServiceAdapter) {
        this.notificationServiceAdapter = notificationServiceAdapter;
    }

    public NotificationTask getNotificationTask() {
        return notificationTask;
    }

    
    public String toString(){
        
        StringBuilder builder = new StringBuilder(); 
                
        subscriptionMap.forEach(
                
                ( NotificationProtocolType k, NotificationSubscription v) -> 
                    builder.append("\n\t | "+k+" : \t"+v)                
        );

        
        return "\n\t NotificationConfig{"+
                "\n\t | notificationServiceAdapter : "+notificationServiceAdapter+   
                "\n\t | notificationTask : "+notificationTask+
                 builder.toString()+ 
                "\n\t}\n";
    }



}
