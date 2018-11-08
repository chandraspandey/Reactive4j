package org.flowr.framework.core.node.io.endpoint;

import java.util.Optional;

import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessRequest;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessResponse;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.ProtocolPublishType;
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupType;
import org.flowr.framework.core.node.io.pipeline.NetworkPipeline;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class NodeConfig {

	private NetworkGroupType networkGroupType 				= NetworkGroupType.LOCAL;
	private NetworkPipeline in								= null;
	private NetworkPipeline out								= null;
	private String channelName								= null;
	private boolean reuseNetworkGroup						= true;
	private NetworkMetric pipelineMetricSubscriber			= null;
	private Optional<IntegrationProcessRequest> handleFor 	= null;
	private Optional<IntegrationProcessResponse> handleAs	= null;
	private ProtocolPublishType protocolPublishType			= null;
	
	
	public NodeConfig(NetworkPipeline in, NetworkPipeline out, String channelName, NetworkGroupType networkGroupType,
		boolean reuseNetworkGroup, NetworkMetric pipelineMetricSubscriber,ProtocolPublishType protocolPublishType,
		Optional<IntegrationProcessRequest> 
		handleFor,Optional<IntegrationProcessResponse> handleAs) {
		
		this.in							= in;
		this.out						= out;
		this.networkGroupType 			= networkGroupType;
		this.channelName 				= channelName;
		this.reuseNetworkGroup 			= reuseNetworkGroup;
		this.pipelineMetricSubscriber 	= pipelineMetricSubscriber;	
		this.protocolPublishType 		= protocolPublishType;
		this.handleFor					= handleFor;
		this.handleAs					= handleAs;
	}

	public NetworkGroupType getNetworkGroupType() {
		return networkGroupType;
	}

	public String getChannelName() {
		return channelName;
	}

	public NetworkPipeline getIn() {
		return in;
	}

	public NetworkPipeline getOut() {
		return out;
	}
	
	public boolean isReuseNetworkGroup() {
		return reuseNetworkGroup;
	}
	
	public NetworkMetric getMetricHandler() {
		return pipelineMetricSubscriber;
	}
	
	public Optional<IntegrationProcessRequest> getHandleFor() {
		return handleFor;
	}

	public void setHandleFor(Optional<IntegrationProcessRequest> handleFor) {
		this.handleFor = handleFor;
	}

	public Optional<IntegrationProcessResponse> getHandleAs() {
		return handleAs;
	}

	public void setHandleAs(Optional<IntegrationProcessResponse> handleAs) {
		this.handleAs = handleAs;
	}

	public ProtocolPublishType getProtocolPublishType() {
		return protocolPublishType;
	}

	public void setProtocolPublishType(ProtocolPublishType protocolPublishType) {
		this.protocolPublishType = protocolPublishType;
	}
	

	public String toString(){
		
		return "NodeConfig{"+
				" networkGroupType : "+networkGroupType+	
				" | reuseNetworkGroup : "+reuseNetworkGroup+	
				" | channelName : "+channelName+	
				" | in : "+in.getPipelineName()+
				" | out : "+out.getPipelineName()+
				" | pipelineMetricSubscriber : "+pipelineMetricSubscriber+
				" | protocolPublishType : "+protocolPublishType+
				" | handleFor : "+handleFor+
				" | handleAs : "+handleAs+
				"}";

	}
}
