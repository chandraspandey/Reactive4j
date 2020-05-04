
/**
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io.flow.handler;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.node.io.NetworkByteBuffer;
import org.flowr.framework.core.notification.NotificationQueue;

public class NetworkBufferQueue implements NotificationQueue {

    private static LinkedTransferQueue<NetworkByteBuffer> bufferQueue   = new LinkedTransferQueue<>();
    private QueueType queueType                                         = QueueType.STAGING;
    private QueueStagingType queueStagingType                           = QueueStagingType.PRIORITY;
    private EventType eventType                                         = EventType.NETWORK; 

    public boolean add(NetworkByteBuffer e) {

        return bufferQueue.add(e);
    }
    
    public boolean offer(NetworkByteBuffer e) {
    
        return bufferQueue.offer(e);
    }
    
    public void put(NetworkByteBuffer e) {
    
        bufferQueue.put(e);
    }
    
    public boolean offer(NetworkByteBuffer e, long timeout, TimeUnit unit) {

        return bufferQueue.offer(e,timeout,unit);
    }
    
    public LinkedTransferQueue<NetworkByteBuffer> asBufferQueue(){
        
        return bufferQueue;
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
    
    public String toString() {
        
        return "NotificationBufferQueue { "+queueType+" | "+queueStagingType+" | "+eventType
                +" | "+bufferQueue.size()+" } ";
    }

  
    
}

