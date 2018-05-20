package org.flowr.framework.core.event.pipeline;

import org.flowr.framework.core.notification.NotificationBufferQueue;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class EventPipelineTask implements PipelineTask {

	private EventPipeline eventPipeline	= null;
	
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
							notificationBufferQueue.addAll(eventPipeline.getBatch());
						}			
						
						//System.out.println("EventPipelineTask : notificationBufferQueue : "+notificationBufferQueue);
					}else {
						
						notificationBufferQueue.add(eventPipeline.take());
						//System.out.println("EventPipelineTask : notificationBufferQueue : "+notificationBufferQueue);
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
		
		return notificationBufferQueue;
	}

}
