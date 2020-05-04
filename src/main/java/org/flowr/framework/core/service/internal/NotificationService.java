
/**
 * Provides service definition for Event notification. Concrete implementation should be provided by the adapter 
 * classes. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.internal;

import java.util.List;
import java.util.Set;

import org.flowr.framework.core.context.Context.RouteContext;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.exception.ClientException;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.ServerException;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.NotificationRoute;
import org.flowr.framework.core.notification.dispatcher.NotificationBufferQueue;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter;
import org.flowr.framework.core.service.FrameworkService;

public interface NotificationService extends FrameworkService{
    
    /**
     * CENTRAL  : Uses single thread model for notification which may involve usage of out of the box notification 
     * propagator using Queue, Topic etc.
     * DELEGATE : Uses chached thread model for notification which may involve distinct delegates to address different
     * types of notification propagator. 
     */
    public enum NotificationServiceMode{
        CENTRAL,
        DELEGATE
    }
    
    /**
     * QUEUED       : Notification is queued for execution
     * EXECUTED     : Notification is executed
     * ERROR        : Notification Error
     * TIMEOUT      : Notification Timeout
     * UNREACHABLE  : Notification EndPoint is Unreachable
     * HEALTHCHECK  : EndPoint HealthCheck Event
     */
    public enum NotificationServiceStatus{
        QUEUED,
        EXECUTED,
        ERROR,
        TIMEOUT,
        UNREACHABLE,
        HEALTHCHECK
    }
    
    void publishEvent(NotificationBufferQueue notificationBufferQueue) throws ClientException;

    void setNotificationRouteContext(RouteContext routeContext) throws ConfigurationException;

    List<NotificationServiceAdapter> getNotificationRoute(NotificationProtocolType notificationProtocolType,
            EventType eventType) throws ServerException;

    Set<NotificationRoute> getNotificationRoutes();
    
    boolean isEnabled();

    void setEnabled(boolean isEnabled);
    
    void notify(NotificationBufferQueue notificationBufferQueue) throws ClientException;

    static NotificationService getInstance() {
        
        return DefaultNotificationService.getInstance();
    }
    
    public final class DefaultNotificationService{
        
        private static NotificationService notificationService;
        
        private DefaultNotificationService() {
            
        }
        
        public static NotificationService getInstance() {
            
            if(notificationService == null) {
                notificationService = new NotificationServiceImpl();
            }
            
            return notificationService;
        }
        
    }

}
