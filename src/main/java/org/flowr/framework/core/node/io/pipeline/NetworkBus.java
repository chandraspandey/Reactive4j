
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io.pipeline;

import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler;
import org.flowr.framework.core.node.io.network.NetworkGroup;
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupStatus;
import org.flowr.framework.core.process.ProcessRegistry;

public interface NetworkBus extends ProcessRegistry<NetworkGroup, 
    SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler>>{
    
    static NetworkBus getInstance() {
        
        return DefaultNetworkBus.getInstance();
    }
    
    public final class DefaultNetworkBus{
        
        private static NetworkBus networkBus;
        
        private DefaultNetworkBus() {
            
        }

        public static NetworkBus getInstance() {
            
            if(networkBus == null) {
                networkBus = new NetworkPipelineBus();
            }
            
            return networkBus;
        }
        
    }
    
    Map<String,IntegrationPipelineHandler> asHandlerMap();

    List<NetworkPipeline> getAllPipelines();
    
    NetworkGroupStatus addNetworkGroup(NetworkGroup networkGroup,
            SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> 
        handlerSet);

    NetworkGroupStatus removeNetworkGroup(NetworkGroup networkGroup,
            SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> 
        handlerSet);
    
    void execute();

    List<NetworkPipeline> lookup(PipelineType pipelineType);
    
    List<NetworkPipeline> lookup(PipelineFunctionType pipelineFunctionType);
    
    NetworkPipeline lookup(String pipelineName,PipelineType pipelineType, PipelineFunctionType 
            pipelineFunctionType);
    
    PipelineType getEventBusType();
    
    String getNetworkBusName();
    
}
