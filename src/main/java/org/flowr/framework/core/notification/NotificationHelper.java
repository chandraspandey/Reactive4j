
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.notification;

import java.util.List;
import java.util.Set;

import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.ServerException;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter;

public interface NotificationHelper {
    
    void bindRoutes(Set<NotificationRoute> routeSet) throws ConfigurationException;
    
    void bindNotificationRoute(NotificationProtocolType notificationProtocolType,
        NotificationServiceAdapter responseServiceAdapter) throws ConfigurationException;

    List<NotificationServiceAdapter> getNotificationRoute(NotificationProtocolType notificationProtocolType,
        EventType eventType) throws ServerException;
    
    List<NotificationServiceAdapter> getNotificationRoute(EventType eventType) throws ServerException;
    
    Set<NotificationRoute> getNotificationRoutes();
    
}
