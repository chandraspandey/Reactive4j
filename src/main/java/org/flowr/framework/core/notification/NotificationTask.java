
/**
 * Defines notification as discrete task executable as thread and provides integration with dispatcher capability. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.notification;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;

import org.flowr.framework.core.event.ChangeEvent;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.node.EndPointDispatcher;
import org.flowr.framework.core.node.io.network.ServiceMesh.ServiceTopologyMode;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.dispatcher.NotificationBufferQueue;
import org.flowr.framework.core.notification.dispatcher.NotificationDispatcher;
import org.flowr.framework.core.service.internal.NotificationService.NotificationServiceStatus;

public interface NotificationTask extends Callable<Map<NotificationProtocolType, NotificationServiceStatus>>,
    NotificationDispatcher {
        
    void setNotificationBufferQueue(NotificationBufferQueue notificationBufferQueue) ;
    
    void configure(Map<NotificationProtocolType,EndPointDispatcher> dispatcherMap);

    void configureTopology(ServiceTopologyMode serviceTopologyMode, Properties topologyProperties);

    String deriveSubscriptionContext(ChangeEvent<EventModel> event);

    SimpleEntry<NotificationProtocolType, NotificationServiceStatus> dispatchNotification(ChangeEvent<EventModel> event,
            String subscriptionId); 
}
