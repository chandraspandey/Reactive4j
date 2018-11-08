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

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NetworkBus extends ProcessRegistry<NetworkGroup, SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler>>{
	
	public static NetworkBus getInstance() {
		
		return DefaultNetworkBus.getInstance();
	}
	
	public class DefaultNetworkBus{
		
		private static NetworkBus networkBus = null;
		

		public static NetworkBus getInstance() {
			
			if(networkBus == null) {
				networkBus = new NetworkPipelineBus();
			}
			
			return networkBus;
		}
		
	}
	
	public Map<String,IntegrationPipelineHandler> asHandlerMap();

	public List<NetworkPipeline> getAllPipelines();
	
	public NetworkGroupStatus addNetworkGroup(NetworkGroup networkGroup,SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> 
		handlerSet);

	public NetworkGroupStatus removeNetworkGroup(NetworkGroup networkGroup,SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> 
		handlerSet);
	
	public void execute();

	public List<NetworkPipeline> lookup(PipelineType pipelineType);
	
	public List<NetworkPipeline> lookup(PipelineFunctionType pipelineFunctionType);
	
	public NetworkPipeline lookup(String pipelineName,PipelineType pipelineType, PipelineFunctionType 
			pipelineFunctionType);
	
	public PipelineType getEventBusType();
	
	public String getNetworkBusName();
	
}