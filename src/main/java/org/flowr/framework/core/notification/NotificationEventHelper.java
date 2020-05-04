
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.notification;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.ServerException;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter;

public class NotificationEventHelper implements NotificationHelper{

    private static NotificationAdapterMapping notificationAdapterMapping = new NotificationAdapterMapping();


    @Override
    public void bindRoutes(Set<NotificationRoute> routeSet) throws ConfigurationException {
    
        if(!routeSet.isEmpty()){
                
            Iterator<NotificationRoute> routeIter =  routeSet.iterator();
    
            if(routeIter.hasNext()){    
                
                NotificationRoute notificationRoute = routeIter.next();
                
                NotificationServiceAdapter notificationServiceAdapter = notificationRoute.getKey();
                
                NotificationProtocolType notificationProtocolType = notificationRoute.getValues();
                                    
                notificationAdapterMapping.add(notificationServiceAdapter,notificationProtocolType);
                
            }           
        }
    }
    
    @Override
    public void bindNotificationRoute(NotificationProtocolType notificationProtocolType, NotificationServiceAdapter 
            notificationServiceAdapter) throws ConfigurationException{
                
        notificationAdapterMapping.add(notificationServiceAdapter,notificationProtocolType);
    }
    
    @Override
    public List<NotificationServiceAdapter> getNotificationRoute(NotificationProtocolType notificationProtocolType,
            EventType eventType) throws ServerException{
            
        return notificationAdapterMapping.getRoute(notificationProtocolType,eventType);
    }
    
    public List<NotificationServiceAdapter> getNotificationRoute(EventType eventType) throws ServerException{
        
        return notificationAdapterMapping.getRoute( eventType);
    }

    @Override
    public Set<NotificationRoute> getNotificationRoutes() {
        return notificationAdapterMapping.getRouteList();
    }
}
