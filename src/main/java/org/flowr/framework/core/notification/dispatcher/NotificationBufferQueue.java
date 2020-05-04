
/**
 * Concrete implementation & extension of DelayQueue for storing in process
 * listener I/O capabilities.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.notification.dispatcher;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.event.ChangeEventEntity;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.notification.NotificationQueue;

public class NotificationBufferQueue extends DelayQueue<ChangeEventEntity> implements NotificationQueue {

    private QueueType queueType                 = QueueType.STAGING;
    private QueueStagingType queueStagingType   = QueueStagingType.SEVERITY;
    private EventType eventType; 

    public ChangeEventEntity addMetaData(ChangeEventEntity changeEventEntity) {        
        changeEventEntity.setQueueType(queueType);
        changeEventEntity.setQueueStagingType(queueStagingType);        
        return changeEventEntity;
    }
    
    @Override
    public boolean add(ChangeEventEntity e) {

        return super.add(addMetaData(e));
    }
        
    @Override
    public boolean offer(ChangeEventEntity e) {
   
        return super.offer(addMetaData(e));
    }
    
    @Override
    public void put(ChangeEventEntity e) {
          super.put(addMetaData(e));
    }
    
    @Override
    public boolean offer(ChangeEventEntity e, long timeout, TimeUnit unit) {

        return super.offer(addMetaData(e),timeout,unit);
    }
    
    @Override
    public void setQueueType(QueueType queueType) {
        this.queueType = queueType;
    }

    @Override
    public QueueType getQueueType() {

        return this.queueType;
    }
    
    @Override
    public EventType getEventType() {
        return this.eventType;
    }

    @Override
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
    
    @Override
    public void setQueueStagingType(QueueStagingType queueStagingType) {
        this.queueStagingType = queueStagingType;
    }
    
    @Override
    public QueueStagingType getQueueStagingType() {
        
        return this.queueStagingType;
    }
    
    @Override
    public String toString() {
        
        return "NotificationBufferQueue { "+queueType+" | "+queueStagingType+" | "+eventType+" | "
        +super.toString()+" } ";
    }
    
}

