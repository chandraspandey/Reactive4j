package org.flowr.framework.core.node.io.pipeline;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.flowr.framework.core.constants.ServerConstants;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler.HandlerType;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandlerContext;
import org.flowr.framework.core.node.io.network.NetworkGroup;
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupStatus;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class NetworkPipelineBus implements NetworkBus {

	private static HashMap<NetworkGroup, SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler>> networkMap 	= 
			new HashMap<NetworkGroup, SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler>>();
	private RegistryType registryType							= RegistryType.LOCAL;
	private PipelineType pipelineType 							= PipelineType.BUS;	
	private String networkBusName								= ServerConstants.CONFIG_SERVER_NETWORK_BUS_NAME;
	
	@Override
	public Map<String,IntegrationPipelineHandler> asHandlerMap(){
		
		Map<String,IntegrationPipelineHandler> handlerMap = new HashMap<String,IntegrationPipelineHandler>();
		
		networkMap.values().forEach(
				
			(h) -> {
				
				handlerMap.put(h.getKey().getHandlerName(), h.getKey());
				handlerMap.put(h.getValue().getHandlerName(), h.getValue());				
			}
		);	
		
		return handlerMap;
	}
	
	@Override
	public NetworkGroupStatus addNetworkGroup(NetworkGroup networkGroup, SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> 
		handlerSet) {
		
		NetworkGroupStatus networkGroupStatus = NetworkGroupStatus.UNKNOWN;
		
		if(networkMap.containsKey(networkGroup)) {
		
			networkGroupStatus = NetworkGroupStatus.IGNORED;
		}else {
			bind(networkGroup, handlerSet);
			networkGroupStatus = NetworkGroupStatus.ASSOCIATED;
		}
		
		return networkGroupStatus;
	}
	

	@Override
	public NetworkGroupStatus removeNetworkGroup(NetworkGroup networkGroup, SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> 
		handlerSet) {		
		
		NetworkGroupStatus networkGroupStatus = NetworkGroupStatus.UNKNOWN;
		
		if(networkMap.containsKey(networkGroup)) {
		
			unbind(networkGroup, handlerSet);
			networkGroupStatus = NetworkGroupStatus.DISASSOCIATED;
		}else {			

			networkGroupStatus = NetworkGroupStatus.INVALID;
		}
		
		return networkGroupStatus;
	}

	@Override
	public List<NetworkPipeline> lookup(PipelineType pipelineType){
		
		List<NetworkPipeline> networkPipelineList = new ArrayList<NetworkPipeline>();
		
		networkMap.keySet().forEach(
				
			(g) -> {
				
				if(g.getIn().getPipelineType().equals(pipelineType)) {
					networkPipelineList.add(g.getIn());
				}
				
				if(g.getOut().getPipelineType().equals(pipelineType)) {
					networkPipelineList.add(g.getOut());
				}
			}
		);		
		
		return networkPipelineList;
	}
	
	@Override
	public void execute(){
		
		networkMap.entrySet().forEach(
				
			(entry) -> {
				
				IntegrationPipelineHandlerContext handlerContext = new IntegrationPipelineHandlerContext(
						HandlerType.IO,entry.getKey().getIn().asNetworkBufferQueue());
				
				entry.getValue().getKey().handle(handlerContext);
			}
		);
		
	}
	
	@Override
	public List<NetworkPipeline> lookup(PipelineFunctionType pipelineFunctionType){
		
		List<NetworkPipeline> networkPipelineList = new ArrayList<NetworkPipeline>();
		
		networkMap.keySet().forEach(
				
			(g) -> {
				
				if(g.getIn().getPipelineFunctionType().equals(pipelineFunctionType)) {
					networkPipelineList.add(g.getIn());
				}
				
				if(g.getOut().getPipelineFunctionType().equals(pipelineFunctionType)) {
					networkPipelineList.add(g.getOut());
				}
			}
		);
		
		return networkPipelineList;
	}
	
	@Override
	public NetworkPipeline lookup(String pipelineName,PipelineType pipelineType, PipelineFunctionType 
		pipelineFunctionType){
		
		NetworkPipeline networkPipeline =null;
								
		Iterator<NetworkGroup> iter = networkMap.keySet().iterator();
		
		while(iter.hasNext()) {
			
			NetworkGroup g = iter.next();
			
			if(
					g.getIn().getPipelineName().equals(pipelineName) &&
					g.getIn().getPipelineType().equals(pipelineType) &&
					g.getIn().getPipelineFunctionType().equals(pipelineFunctionType)
			) {
				networkPipeline = g.getIn();
				break;
			}
			
			if(
					g.getOut().getPipelineName().equals(pipelineName) &&
					g.getOut().getPipelineType().equals(pipelineType) &&
					g.getOut().getPipelineFunctionType().equals(pipelineFunctionType)
			) {
				networkPipeline = g.getOut();
				break;
			}
		}		
		System.out.println("NetworkPipelineBus : networkPipeline : "+networkPipeline);
		
		return networkPipeline;
	}

	@Override
	public PipelineType getEventBusType() {

		return this.pipelineType;
	}


	@Override
	public String getNetworkBusName() {
		return this.networkBusName;
	}


	@Override
	public List<NetworkPipeline> getAllPipelines() {
		
		List<NetworkPipeline> networkPipelineList	= new ArrayList<NetworkPipeline>();
		
		networkMap.keySet().forEach(
				
			(g) -> {
				
					networkPipelineList.add(g.getIn());
					networkPipelineList.add(g.getOut());
			}
		);
		
		return networkPipelineList;
	}
	
	@Override
	public void bind(NetworkGroup networkGroup, SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> handlerSet) {
		networkMap.put(networkGroup,handlerSet);
	}

	@Override
	public void unbind(NetworkGroup networkGroup, SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> handlerSet) {
		networkMap.remove(networkGroup);
	}

	@Override
	public void rebind(NetworkGroup networkGroup, SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> handlerSet) {
		unbind(networkGroup, handlerSet);
		bind(networkGroup, handlerSet);
	}

	@Override
	public Collection<SimpleEntry<IntegrationPipelineHandler, IntegrationPipelineHandler>> list() {
		return  networkMap.values();
	}

	@Override
	public SimpleEntry<IntegrationPipelineHandler, IntegrationPipelineHandler> lookup(NetworkGroup networkGroup) {
				
		return networkMap.get(networkGroup);
	}

	@Override
	public void persist() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		networkMap.clear();
	}

	@Override
	public HashMap<NetworkGroup, SimpleEntry<IntegrationPipelineHandler, IntegrationPipelineHandler>> restore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegistryType getRegistryType() {
		return this.registryType;
	}

	@Override
	public void setRegistryType(RegistryType registryType) {
		this.registryType = registryType;
	}

	public String toString() {
		
		return "NetworkPipelineBus { "+networkBusName+" | "+pipelineType+" | "+registryType+" | "+networkMap+" } ";
	}


}
