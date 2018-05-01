package org.flowr.framework.core.event.pipeline;

import java.util.List;

import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface EventBus {

	public List<EventPipeline> getAllPipelines();
	
	public void addEventPipeline(EventPipeline eventPipeline);

	public void removeEventPipeline(EventPipeline eventPipeline);

	public EventPipeline lookup(String pipelineName);

	public List<EventPipeline> lookup(PipelineType pipelineType);
	
	public List<EventPipeline> lookup(PipelineFunctionType pipelineFunctionType);
	
	public EventPipeline lookup(String pipelineName,PipelineType pipelineType, PipelineFunctionType 
			pipelineFunctionType);
	
	public PipelineType getEventBusType();
	
	public String getEventBusName();

}