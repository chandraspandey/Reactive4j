package org.flowr.framework.core.node.io.endpoint;

import java.util.Optional;
import java.util.AbstractMap.SimpleEntry;

import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessRequest;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessResponse;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.ProtocolPublishType;
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class NodeEndPointConfiguration {

	private String inboundPipelineName; 
	private String inboundPipelineHostName; 
	private int inboundPipelinePortNumber;
	private String outboundPipelineName; 
	private String outboundPipelineHostName; 
	private int outboundPipelinePortNumber;
	private NetworkMetric pipelineMetricSubscriber; 
	private String channelName;
	private NetworkGroupType networkGroupType;
	private boolean reuseNetworkGroup;
	private SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> pipelineHandler;
	private Optional<IntegrationProcessRequest> handleFor 	= null;
	private Optional<IntegrationProcessResponse> handleAs	= null;
	private ProtocolPublishType protocolPublishType			= null;
	
	public NodeEndPointConfiguration(
			String inboundPipelineName, String inboundPipelineHostName,	int inboundPipelinePortNumber, 
			String outboundPipelineName, String outboundPipelineHostName, int outboundPipelinePortNumber, 
			NetworkMetric pipelineMetricSubscriber, String channelName,
			NetworkGroupType networkGroupType,boolean reuseNetworkGroup,
			SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> pipelineHandler,
			ProtocolPublishType protocolPublishType,
			Optional<IntegrationProcessRequest> handleFor,
			Optional<IntegrationProcessResponse> handleAs
			) {

		this.inboundPipelineName 		= inboundPipelineName;
		this.inboundPipelineHostName 	= inboundPipelineHostName;
		this.inboundPipelinePortNumber 	= inboundPipelinePortNumber;
		this.outboundPipelineName 		= outboundPipelineName;
		this.outboundPipelineHostName 	= outboundPipelineHostName;
		this.outboundPipelinePortNumber = outboundPipelinePortNumber;
		this.pipelineMetricSubscriber 	= pipelineMetricSubscriber;
		this.channelName 				= channelName;
		this.networkGroupType			= networkGroupType;
		this.reuseNetworkGroup			= reuseNetworkGroup;
		this.pipelineHandler			= pipelineHandler;
		this.protocolPublishType		= protocolPublishType;
		this.handleFor					= handleFor;
		this.handleAs					= handleAs;
	}
	
	public String getInboundPipelineName() {
		return inboundPipelineName;
	}

	public String getInboundPipelineHostName() {
		return inboundPipelineHostName;
	}

	public int getInboundPipelinePortNumber() {
		return inboundPipelinePortNumber;
	}

	public String getOutboundPipelineName() {
		return outboundPipelineName;
	}

	public String getOutboundPipelineHostName() {
		return outboundPipelineHostName;
	}

	public int getOutboundPipelinePortNumber() {
		return outboundPipelinePortNumber;
	}

	public NetworkMetric getPipelineMetricSubscriber() {
		return pipelineMetricSubscriber;
	}

	public String getChannelName() {
		return channelName;
	}

	public NetworkGroupType getNetworkGroupType() {
		return networkGroupType;
	}

	public boolean isReuseNetworkGroup() {
		return reuseNetworkGroup;
	}
	
	public Optional<IntegrationProcessRequest> getHandleFor() {
		return handleFor;
	}

	public Optional<IntegrationProcessResponse> getHandleAs() {
		return handleAs;
	}

	public ProtocolPublishType getProtocolPublishType() {
		return protocolPublishType;
	}
	
	public SimpleEntry<IntegrationPipelineHandler, IntegrationPipelineHandler> getPipelineHandler() {
		return pipelineHandler;
	}

	public String toString(){
		
		return "NodeEndPointConfiguration{"+
				" networkGroupType : "+networkGroupType+	
				" | reuseNetworkGroup : "+reuseNetworkGroup+	
				" | channelName : "+channelName+	
				" | inboundPipelineName : "+inboundPipelineName+
				" | inboundPipelineHostName : "+inboundPipelineHostName+
				" | inboundPipelinePortNumber : "+inboundPipelinePortNumber+
				" | outboundPipelineName : "+outboundPipelineName+
				" | outboundPipelineHostName : "+outboundPipelineHostName+
				" | outboundPipelinePortNumber : "+outboundPipelinePortNumber+
				" |\n handleFor : "+handleFor+
				" |\n handleAs : "+handleAs+				
				" |\n pipelineMetricSubscriber : "+pipelineMetricSubscriber+
				" |\n pipelineHandler : "+pipelineHandler+
				"\n}";

	}

	
}
