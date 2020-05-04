
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.event.pipeline;

import java.util.List;

import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;

public interface EventBus {

    List<EventPipeline> getAllPipelines();
    
    void addEventPipeline(EventPipeline eventPipeline);

    void removeEventPipeline(EventPipeline eventPipeline);

    EventPipeline lookup(String pipelineName);

    List<EventPipeline> lookup(PipelineType pipelineType);
    
    List<EventPipeline> lookup(PipelineFunctionType pipelineFunctionType);
    
    EventPipeline lookup(String pipelineName,PipelineType pipelineType, PipelineFunctionType 
            pipelineFunctionType);
    
    PipelineType getEventBusType();
    
    String getEventBusName();

}
