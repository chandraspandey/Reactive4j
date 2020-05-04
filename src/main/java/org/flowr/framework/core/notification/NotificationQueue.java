
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.notification;

import org.flowr.framework.core.event.Event.EventType;

public interface NotificationQueue{
    
    public enum QueueType{
        LIFO,
        FIFO,
        STAGING
    }
    
    public enum QueueStagingType{
        PRIORITY,
        SEVERITY
    }
    
    EventType getEventType();

    void setEventType(EventType eventType);
    
    void setQueueStagingType(QueueStagingType queueStagingType);
    
    QueueStagingType getQueueStagingType();
    
    void setQueueType(QueueType queueType);
    
    QueueType getQueueType();
}
