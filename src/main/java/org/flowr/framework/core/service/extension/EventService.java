package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;
import org.flowr.framework.core.exception.ClientException;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.service.ServiceFrameworkComponent;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface EventService extends ServiceFrameworkComponent, Runnable{

	public enum EventRegistrationStatus{
		REGISTERED,
		UNREGISTERED
	}
	
	public EventRegistrationStatus registerEventPipeline(String pipelineName,PipelineType pipelineType, PipelineFunctionType 
			pipelineFunctionType,EventPublisher eventPublisher);
	
	public void process() throws ClientException;
	
	public static EventService getInstance() {
		
		return DefaultEventService.getInstance();
	}
	
	public class DefaultEventService{
		
		private static EventService eventService = null;
		

		public static EventService getInstance() {
			
			if(eventService == null) {
				eventService = new EventServiceImpl();
			}
			
			return eventService;
		}
		
	}

}