
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.event.pipeline;

public interface Pipeline{
    
    public enum PipelineType{
        PRIORITY,
        LINKED,
        SYNCHRONOUS,
        TRANSFER,
        BUS
    }
    
    /**
     * ALL : is reserved for client side usage for option to process all the function types as a 
     * proxy at the edge points.
     * 
     *
     */
    public enum PipelineFunctionType{
        ALL,
        PIPELINE_PROMISE_EVENT,
        PIPELINE_PROMISE_DEFFERED_EVENT,
        PIPELINE_PROMISE_MAP_EVENT,
        PIPELINE_PROMISE_PHASED_EVENT,
        PIPELINE_PROMISE_SCHEDULED_EVENT,
        PIPELINE_PROMISE_STAGE_EVENT,
        PIPELINE_PROMISE_STREAM_EVENT,
        PIPELINE_MANAGEMENT_EVENT,
        PIPELINE_NOTIFICATION_EVENT,
        NOTIFICATION,
        HEALTH,
        USECASE,
        PIPELINE_SERVER_INTEGRATION,
        PIPELINE_SERVER_SERVICE,
        PIPELINE_CLIENT_SERVICE,
        PIPELINE_NETWORK_SERVER,
        PIPELINE_NETWORK_CLIENT
    }
    
    void setPipelineFunctionType(PipelineFunctionType pipelineFunctionType);
    
    PipelineFunctionType getPipelineFunctionType();
    
    void setPipelineType(PipelineType pipelineType);
    
    PipelineType getPipelineType();
    
    String getPipelineName();

    void setPipelineName(String pipelineName);
}
