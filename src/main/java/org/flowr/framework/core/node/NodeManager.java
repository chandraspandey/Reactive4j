package org.flowr.framework.core.node;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.flowr.framework.core.config.NodeServiceConfiguration;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.io.endpoint.NodePipelineClient;
import org.flowr.framework.core.node.io.endpoint.NodePipelineServer;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessRequest;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessResponse;
import org.flowr.framework.core.node.io.flow.handler.MetricHandler;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric;
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupType;
import org.flowr.framework.core.node.io.pipeline.NetworkBus;
import org.flowr.framework.core.service.Service.ServiceStatus;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface NodeManager extends MetricHandler{

	public NodePipelineServer createInboundNetworkPipeline(	NodeServiceConfiguration inboundConfiguration,
			NodeServiceConfiguration outboundConfiguration,	NetworkGroupType inboundNetworkGroupType,
			NetworkMetric pipelineMetricSubscriber,boolean reuseNetworkGroup,
			SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> pipelineHandler,
			ProtocolPublishType protocolPublishType,
			Optional<IntegrationProcessRequest> handleFor,
			Optional<IntegrationProcessResponse> handleAs
	) throws IOException, ConfigurationException;
	
	public List<NodePipelineClient> createOutboundNetworkPipeline(NodeServiceConfiguration inboundConfiguration,
			NodeServiceConfiguration outboundConfiguration,	NetworkGroupType outboundNetworkGroupType,
			NetworkMetric pipelineMetricSubscriber,boolean reuseNetworkGroup,
			SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> pipelineHandler,
			ProtocolPublishType protocolPublishType,
			Optional<IntegrationProcessRequest> handleFor,
			Optional<IntegrationProcessResponse> handleAs
	) throws IOException, ConfigurationException;
	
	public SimpleEntry<Thread, Thread> startInboundNetworkPipeline(NodePipelineServer toServer,
			NodePipelineClient fromClient) throws IOException;
	
	public SimpleEntry<Thread, Thread> startOutboundNetworkPipeline(NodePipelineClient fromClient,NodePipelineServer toServer)
			throws IOException;
	
	public byte[] clientToServer(byte[] in,NodePipelineClient fromClient, NodePipelineServer toServer);
	
	public byte[] serverToClient(byte[] in,NodePipelineServer fromServer,NodePipelineClient toClient );
	
	public void configure() throws IOException, ConfigurationException;
	
	public NetworkBus getNetworkBus();
	
	public ServiceStatus startup() throws ConfigurationException;
	
	public ServiceStatus shutdown() throws ConfigurationException;

	public ServiceStatus initNetworkBusExecutor(Optional<Properties> configProperties);

	public ServiceStatus closeNetworkBusExecutor(Optional<Properties> configProperties);
	
}
