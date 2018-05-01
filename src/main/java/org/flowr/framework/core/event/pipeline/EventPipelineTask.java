package org.flowr.framework.core.event.pipeline;

import java.util.List;

import org.flowr.framework.core.notification.NotificationBufferQueue;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class EventPipelineTask implements PipelineTask {

	private EventBus eventBus			= null;
	
	public EventPipelineTask(EventBus eventBus) {
		this.eventBus = eventBus; 
	}
	
	@Override
	public NotificationBufferQueue call() throws Exception {

		return process();
	}

	@Override
	public NotificationBufferQueue process() throws InterruptedException {
		
		NotificationBufferQueue notificationBufferQueue = new NotificationBufferQueue();
		
		List<EventPipeline> pipelineList = eventBus.getAllPipelines();
		
		pipelineList.forEach(
				
			(p) -> {
				try {
					
					if(!p.isEmpty()) {
						
						if( p.getPipelineConfiguration().isBatchMode()) {						
							
							if(p.size() > p.getPipelineConfiguration().getBatchSize()) {
								notificationBufferQueue.addAll(p.getBatch());
							}			
							
							System.out.println("EventPipelineTask : notificationBufferQueue : "+notificationBufferQueue);
						}else {
							
							notificationBufferQueue.add(p.take());
							System.out.println("EventPipelineTask : notificationBufferQueue : "+notificationBufferQueue);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		);
		
		return notificationBufferQueue;
	}

}
