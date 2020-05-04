
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.event.pipeline;

import org.apache.log4j.Logger;
import org.flowr.framework.core.event.ChangeEventEntity;
import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.notification.dispatcher.NotificationBufferQueue;

public class EventPipelineTask implements PipelineTask {

    private EventPipeline eventPipeline;
    
    public EventPipelineTask(EventPipeline eventPipeline) {
        this.eventPipeline = eventPipeline; 
    }
    
    @Override
    public NotificationBufferQueue call() throws Exception {

        return process();
    }

    @Override
    public NotificationBufferQueue process() throws InterruptedException {
        
        NotificationBufferQueue notificationBufferQueue = new NotificationBufferQueue();

        try {
                    
            if( eventPipeline.getPipelineConfiguration().isBatchMode()) {                       
                
                if(eventPipeline.size() > eventPipeline.getPipelineConfiguration().getBatchSize()) {
                     
                    eventPipeline.getBatch().forEach(
                          
                       (Event<EventModel>  e) -> notificationBufferQueue.add((ChangeEventEntity)e)
                           
                    );
                    
                } 
                
            }else {
                
                notificationBufferQueue.add((ChangeEventEntity) eventPipeline.take());
            }

        } catch (InterruptedException e) {
            Logger.getRootLogger().error(e);
            Thread.currentThread().interrupt();
        }           
        
        return notificationBufferQueue;
    }

}
