
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.framework.fixture.notification;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.event.pipeline.EventPipeline;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.node.EndPointDispatcher;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.NotificationQueue.QueueStagingType;
import org.flowr.framework.core.notification.NotificationQueue.QueueType;
import org.flowr.framework.core.notification.dispatcher.NotificationBufferQueue;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.service.ServiceEndPoint;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.internal.NotificationService.NotificationServiceStatus;


public class BusinessNotificationProtocolDispatcher implements EndPointDispatcher {

    private NotificationProtocolType notificationProtocolType; 
    private Collection<ServiceEndPoint> serviceEndPointList;
    private EventPipeline eventPipeline;
    private QueueStagingType queueStagingType = QueueStagingType.SEVERITY;
    private NotificationBufferQueue notificationBufferQueue = new NotificationBufferQueue();
    
    @Override
    public void configure(NotificationProtocolType notificationProtocolType, 
            Collection<ServiceEndPoint> serviceEndPointList, EventPipeline eventPipeline) {
        
        this.notificationProtocolType   = notificationProtocolType;
        this.serviceEndPointList        = serviceEndPointList;
        this.eventPipeline              = eventPipeline;
    }
    
    public void handle(QueueStagingType queueStagingType) {
        
        this.queueStagingType = queueStagingType;
        notificationBufferQueue.setQueueStagingType(queueStagingType);
        notificationBufferQueue.setEventType(eventPipeline.getEventType());
        notificationBufferQueue.setQueueType(QueueType.STAGING);
    }

    @Override
    public NotificationServiceStatus dispatch(Event<EventModel> event) {
        
        Logger.getRootLogger().info("NotificationProtocolDispatcher : event : "+event);
                
        eventPipeline.add(event);
        
        // RoundRobin
        return NotificationServiceStatus.EXECUTED;
    }

    @Override
    public Collection<NotificationSubscription> getNotificationProtocolTypelist(NotificationProtocolType 
        notificationProtocolType){
        
        return ServiceFramework.getInstance().getCatalog().getSubscriptionService().getNotificationProtocolTypelist(
                notificationProtocolType);
    }
    
    @Override
    public Collection<ServiceEndPoint> getEndPointList() {
        return this.serviceEndPointList;
    }

    public QueueStagingType getQueueStagingType() {
        return queueStagingType;
    }

    public NotificationProtocolType getNotificationProtocolType() {
        return notificationProtocolType;
    }

}
