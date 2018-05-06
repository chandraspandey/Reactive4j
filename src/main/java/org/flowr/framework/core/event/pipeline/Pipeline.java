package org.flowr.framework.core.event.pipeline;

import org.flowr.framework.core.flow.EventSubscriber;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Pipeline<Event> extends EventSubscriber{
	
	public enum PipelineType{
		PRIORITY,
		LINKED,
		SYNCHRONOUS,
		TRANSFER,
		BUS
	}
	
	/**
	 * ALL : is reserved for client side usage for option to process all the function types as a proxy at the edge points.
	 * 
	 *
	 */
	public enum PipelineFunctionType{
		ALL,
		PIPELINE_PROMISE_EVENT,
		PIPELINE_PROMISE_DEFFERED_EVENT,
		PIPELINE_PROMISE_PHASED_EVENT,
		PIPELINE_PROMISE_SCHEDULED_EVENT,
		PIPELINE_PROMISE_STAGED_EVENT,
		PIPELINE_PROMISE_STREAM_EVENT,
		PIPELINE_MANAGEMENT_EVENT,
		PIPELINE_NOTIFICATION_EVENT
	}
	
	public void setPipelineFunctionType(PipelineFunctionType pipelineFunctionType);
	
	public PipelineFunctionType getPipelineFunctionType();
	
	public void setPipelineType(PipelineType pipelineType);
	
	public PipelineType getPipelineType();
	
	public String getPipelineName();

	public void setPipelineName(String pipelineName);
}
