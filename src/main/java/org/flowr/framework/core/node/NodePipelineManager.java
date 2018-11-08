package org.flowr.framework.core.node;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.Flow.Subscriber;

import org.flowr.framework.core.config.NodeServiceConfiguration;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.node.io.NetworkByteBuffer;
import org.flowr.framework.core.node.io.endpoint.NodeEndPointConfiguration;
import org.flowr.framework.core.node.io.endpoint.NodePipelineClient;
import org.flowr.framework.core.node.io.endpoint.NodePipelineClientEndPoint;
import org.flowr.framework.core.node.io.endpoint.NodePipelineServer;
import org.flowr.framework.core.node.io.endpoint.NodePipelineServerEndPoint;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessContext;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessHandler;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessRequest;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessResponse;
import org.flowr.framework.core.node.io.flow.metric.ChannelMetric;
import org.flowr.framework.core.node.io.flow.metric.MetricContext;
import org.flowr.framework.core.node.io.flow.metric.MetricHandlerMap;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric;
import org.flowr.framework.core.node.io.flow.protocol.NetworkSubscriptionContext;
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupType;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelFlowType;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelStatus;
import org.flowr.framework.core.node.io.pipeline.NetworkBus;
import org.flowr.framework.core.node.io.pipeline.NetworkBusExecutor;
import org.flowr.framework.core.node.io.pipeline.NetworkPipelineBus;
import org.flowr.framework.core.node.io.pipeline.NetworkPipelineBusExecutor;
import org.flowr.framework.core.service.Service.ServiceStatus;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public abstract class NodePipelineManager implements  NodeManager{
	
	protected NetworkBusExecutor networkBusExecutor = null;
	private NetworkBus	networkBus					= new NetworkPipelineBus();
	private MetricHandlerMap metricHandlerMap 		= new MetricHandlerMap();
	private Subscriber<? super ProtocolPublishType> networkMetricSubscriber;		
	
	@Override
	public void registerMetricMapperHandler() {
		
		Arrays.asList(MetricType.values()).forEach(
				
			(m) -> {
				
				IntegrationProcessHandler internalProcessHandler = new IntegrationProcessHandler.DefaultIntegrationProcessHandler();
				internalProcessHandler.setProtocolPublishType(ProtocolPublishType.INTERNAL);				
				metricHandlerMap.put( internalProcessHandler,m);
				
				IntegrationProcessHandler externalProcessHandler = new IntegrationProcessHandler.DefaultIntegrationProcessHandler();
				externalProcessHandler.setProtocolPublishType(ProtocolPublishType.EXTERNAL);				
				metricHandlerMap.put(externalProcessHandler,m);
				
				
			}
		);	
	}	
	
	@Override
	public Optional<IntegrationProcessResponse> handleMetricType(MetricContext metricContext) {
		
		System.out.println("NodePipelineManagerTest : "+metricContext);

		
		Optional<IntegrationProcessRequest> handleFor = metricContext.getHandleFor();
		
		Optional<IntegrationProcessResponse> handleAs = metricContext.getHandleAs();
		
			
		
		switch(metricContext.getMetricType()) {
			
			case CANCEL:{
				
				NetworkSubscriptionContext networkSubscriptionContext = 
					new NetworkSubscriptionContext(
						MetricType.CANCEL,
						NetworkSubscriptionContext.DEFAULT_TIMEOUT,
						NetworkSubscriptionContext.DEFAULT_TIMEUNIT,
						metricContext.getProtocolPublishType(),
						metricContext.getHandleFor(),
						metricContext.getHandleAs()			
					);						
				
				IntegrationProcessContext networkActuatorContext = new IntegrationProcessContext(
						ProtocolPublishType.INTERNAL,MetricType.CANCEL,  Optional.empty(), 
						Optional.of(networkSubscriptionContext),handleFor,handleAs);				
								
				handleAs = metricHandlerMap.delegate(networkActuatorContext);

				break;
			}case COMPLETION:{
				if(metricContext.getChannelMetric().isPresent()) {
					
					IntegrationProcessContext networkActuatorContext = new IntegrationProcessContext(
							ProtocolPublishType.INTERNAL,MetricType.COMPLETION, Optional.of(metricContext), 
							Optional.empty(), handleFor,handleAs);
					handleAs = metricHandlerMap.delegate(networkActuatorContext);
				}
				break;
			}case ERROR:{
				
				if(metricContext.getThrowable().isPresent() ) {					

					IntegrationProcessContext networkActuatorContext = new IntegrationProcessContext(
							ProtocolPublishType.INTERNAL,MetricType.COMPLETION, Optional.of(metricContext), 
							Optional.empty(), handleFor,handleAs);
					handleAs = metricHandlerMap.delegate(networkActuatorContext);
				}
				break;
			}case SUBSCRIPTION:{
				
				if(metricContext.getSubscription().isPresent()) {
					
					IntegrationProcessContext networkActuatorContext = new IntegrationProcessContext(
							ProtocolPublishType.INTERNAL,MetricType.SUBSCRIPTION, Optional.of(metricContext), 
							Optional.empty(), handleFor,handleAs);
					handleAs = metricHandlerMap.delegate(networkActuatorContext);
				}
				break;
			}case TRANSFER:{
				
				if(metricContext.getChannelMetric().isPresent()) {
	
					IntegrationProcessContext networkActuatorContext = new IntegrationProcessContext(
							ProtocolPublishType.INTERNAL,MetricType.TRANSFER, Optional.of(metricContext), 
							Optional.empty(), handleFor,handleAs);
					handleAs = metricHandlerMap.delegate(networkActuatorContext);
				}
				break;
			}case SEEK:{
				
				NetworkSubscriptionContext networkSubscriptionContext = 
					new NetworkSubscriptionContext(
						MetricType.SEEK,
						NetworkSubscriptionContext.DEFAULT_TIMEOUT,
						NetworkSubscriptionContext.DEFAULT_TIMEUNIT,
						metricContext.getProtocolPublishType(),
						metricContext.getHandleFor(),
						metricContext.getHandleAs()			
					);							
						
				IntegrationProcessContext networkActuatorContext = new IntegrationProcessContext(
						ProtocolPublishType.INTERNAL,MetricType.SEEK,  Optional.empty(), 
						Optional.of(networkSubscriptionContext), handleFor,handleAs);

				handleAs = metricHandlerMap.delegate(networkActuatorContext);
				break;
			}default:{
				break;
			}
		
		}
		
		return handleAs;
		
	}
	
	@Override
	public void onSeek(NetworkSubscriptionContext networkSubscriptionContext) {
		
		MetricContext metricContext = new MetricContext(
											MetricType.SEEK,
											Optional.of(ChannelMetric.seekMetric(null)),
											Optional.empty(),
											Optional.empty(),
											networkSubscriptionContext.getProtocolPublishType(),
											networkSubscriptionContext.getHandleFor(),
											networkSubscriptionContext.getHandleAs()
										);
		this.handleMetricType(metricContext);
		
	}
	
	@Override
	public void onCancel(NetworkSubscriptionContext networkSubscriptionContext) {
			
		MetricContext metricContext = new MetricContext(
											MetricType.CANCEL,
											Optional.of(ChannelMetric.cancelMetric(null)),
											Optional.empty(),
											Optional.empty(),
											networkSubscriptionContext.getProtocolPublishType(),
											networkSubscriptionContext.getHandleFor(),
											networkSubscriptionContext.getHandleAs()
										);
		this.handleMetricType(metricContext);
	}

	@Override
	public void setNetworkMetricSubscriber(Subscriber<? super ProtocolPublishType> networkMetricSubscriber){
		
		this.networkMetricSubscriber = networkMetricSubscriber;
	}
	
	@Override
	public Subscriber<? super ProtocolPublishType> getNetworkMetricSubscriber(){
		
		return this.networkMetricSubscriber;
	}
	
	@Override
	public String getNetworkMetricSubscriberName() {
		
		return this.getClass().getSimpleName();
	}
	
	@Override
	public ServiceStatus initNetworkBusExecutor(Optional<Properties> configProperties) {
		
		networkBusExecutor = new NetworkPipelineBusExecutor(networkBus);
		
		return networkBusExecutor.startup(configProperties);
	}
	
	@Override
	public ServiceStatus closeNetworkBusExecutor(Optional<Properties> configProperties) {
		
		networkBusExecutor = new NetworkPipelineBusExecutor(networkBus);
		
		return networkBusExecutor.shutdown(configProperties);
	}

	@Override	
	public NodePipelineServer createInboundNetworkPipeline(	NodeServiceConfiguration inboundConfiguration,
			NodeServiceConfiguration outboundConfiguration,	NetworkGroupType inboundNetworkGroupType,
			NetworkMetric pipelineMetricSubscriber,boolean reuseNetworkGroup,
			SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> pipelineHandlerSet,
			ProtocolPublishType protocolPublishType,
			Optional<IntegrationProcessRequest> handleFor,
			Optional<IntegrationProcessResponse> handleAs
	) throws IOException, ConfigurationException {
	
		NodePipelineServer nodePipelineServer = null;
		
		NodeEndPointConfiguration nodeEndPointServerConfiguration = new NodeEndPointConfiguration(
				inboundConfiguration.getNodePipelineName(), 
				inboundConfiguration.getHostName(),	
				inboundConfiguration.getHostPort(), 
				outboundConfiguration.getNodePipelineName(), 
				outboundConfiguration.getHostName(), 
				outboundConfiguration.getHostPort(), 
				pipelineMetricSubscriber, 
				inboundConfiguration.getNodeChannelName(),
				inboundNetworkGroupType,
				reuseNetworkGroup,
				pipelineHandlerSet,
				protocolPublishType,
				handleFor,
				handleAs
		);
		
		NodePipelineServerEndPoint nodePipelineServerEndPoint = new NodePipelineServerEndPoint();
		
		nodePipelineServer = (NodePipelineServer) nodePipelineServerEndPoint.configureAsPipeline(nodeEndPointServerConfiguration);
		
		nodePipelineServerEndPoint.setEndPointStatus(EndPointStatus.REACHABLE);
		nodePipelineServerEndPoint.setLastUpdated(Timestamp.from(Instant.now()));
		nodePipelineServerEndPoint.setNotificationProtocolType(inboundConfiguration.getNotificationProtocolType());
		nodePipelineServerEndPoint.setPipelineFunctionType(inboundConfiguration.getPipelineFunctionType());
		
		return nodePipelineServer;
	}
	
	@Override
	public List<NodePipelineClient> createOutboundNetworkPipeline(NodeServiceConfiguration inboundConfiguration,
			NodeServiceConfiguration outboundConfiguration,	NetworkGroupType outboundNetworkGroupType,
			NetworkMetric pipelineMetricSubscriber,boolean reuseNetworkGroup,
			SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> pipelineHandlerSet,
			ProtocolPublishType protocolPublishType,
			Optional<IntegrationProcessRequest> handleFor,
			Optional<IntegrationProcessResponse> handleAs
	) throws IOException, ConfigurationException {
				
		List<NodePipelineClient> nodePipelineClientList = new ArrayList<NodePipelineClient>();
		
		Map<SimpleEntry<String, Integer>, SimpleEntry<String, Integer>> pipelineHostPortMap = 
				new HashMap<SimpleEntry<String, Integer>, SimpleEntry<String, Integer>>();			
			
		Iterator<SimpleEntry<String, Integer>> inboundIter = inboundConfiguration.getClientEndPointList().iterator();
		
		Iterator<SimpleEntry<String, Integer>> outboundIter = outboundConfiguration.getClientEndPointList().iterator();
		
		while(inboundIter.hasNext() && outboundIter.hasNext()) {

			pipelineHostPortMap.put(inboundIter.next(), outboundIter.next());							
		}		
		
		Iterator<Entry<SimpleEntry<String, Integer>, SimpleEntry<String, Integer>>> pipelineHostPortMapIter =
				pipelineHostPortMap.entrySet().iterator();
		
		while(pipelineHostPortMapIter.hasNext()) {
				
			Entry<SimpleEntry<String, Integer>, SimpleEntry<String, Integer>> entry = pipelineHostPortMapIter.next();
			
			SimpleEntry<String, Integer> inboundHostPortConfig  = entry.getKey();
			SimpleEntry<String, Integer> outboundHostPortConfig = entry.getValue();			
			
			NodeEndPointConfiguration nodeEndPointClientConfiguration = new NodeEndPointConfiguration(
					inboundConfiguration.getNodePipelineName(), 
					inboundHostPortConfig.getKey(),	
					inboundHostPortConfig.getValue(), 
					outboundConfiguration.getNodePipelineName(), 
					outboundHostPortConfig.getKey(), 
					outboundHostPortConfig.getValue(), 
					pipelineMetricSubscriber, 
					outboundConfiguration.getNodeChannelName(),
					outboundNetworkGroupType,
					reuseNetworkGroup,
					pipelineHandlerSet,
					protocolPublishType,
					handleFor,
					handleAs
			);
			
			NodePipelineClientEndPoint nodePipelineClientEndPoint = new NodePipelineClientEndPoint();
						
			NodePipelineClient nodePipelineClient = (NodePipelineClient) 
					nodePipelineClientEndPoint.configureAsPipeline(nodeEndPointClientConfiguration); 
			
		
			nodePipelineClientEndPoint.setEndPointStatus(EndPointStatus.REACHABLE);
			nodePipelineClientEndPoint.setLastUpdated(Timestamp.from(Instant.now()));
			nodePipelineClientEndPoint.setNotificationProtocolType(outboundConfiguration.getNotificationProtocolType());
			nodePipelineClientEndPoint.setPipelineFunctionType(outboundConfiguration.getPipelineFunctionType());
			
			nodePipelineClientList.add(nodePipelineClient);
			
		}	
		return nodePipelineClientList; 	
	}
	
	@Override
	public SimpleEntry<Thread, Thread> startInboundNetworkPipeline(NodePipelineServer toServer,
		NodePipelineClient fromClient) throws IOException{
		
		Thread serverThread = new Thread(toServer);
		serverThread.start();	
		
		ChannelStatus inboundServerChannelStatus = toServer.connect(null,ChannelFlowType.INBOUND);
		System.out.println("NodePipelineManager : SERVER : inboundServerChannelStatus : "+inboundServerChannelStatus);
	
		Thread clientThread = new Thread(fromClient);
		clientThread.start();
		
		ChannelStatus outboundClientChannelStatus = fromClient.connect(toServer.getSocketAddress(ChannelFlowType.INBOUND),
				ChannelFlowType.OUTBOUND);
		
		System.out.println("NodePipelineManager : CLIENT : outboundClientChannelStatus : "+outboundClientChannelStatus);
		
		while(
				(toServer.getChannelStatus(ChannelFlowType.INBOUND) != ChannelStatus.CONNECTED ) &&
				(fromClient.getChannelStatus(ChannelFlowType.OUTBOUND) != ChannelStatus.CONNECTED )
			){
				
				System.out.println("NodePipelineManager : Waiting for sync ");
			
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}

	
		return new SimpleEntry<Thread, Thread>(serverThread,clientThread);
	}
	
	@Override
	public SimpleEntry<Thread, Thread> startOutboundNetworkPipeline(NodePipelineClient fromClient,NodePipelineServer toServer)
		throws IOException{
			
		Thread serverThread = new Thread(toServer);
		serverThread.start();	
	
		ChannelStatus outboundServerChannelStatus = toServer.connect(null,ChannelFlowType.OUTBOUND);
		System.out.println("NodePipelineManager : SERVER : outboundServerChannelStatus : "+outboundServerChannelStatus);
	
		Thread clientThread = new Thread(fromClient);
		clientThread.start();
		
		ChannelStatus outboundClientChannelStatus = fromClient.connect(toServer.getSocketAddress(ChannelFlowType.OUTBOUND),
				ChannelFlowType.INBOUND);
		System.out.println("NodePipelineManager : CLIENT : outboundClientChannelStatus : "+outboundClientChannelStatus);
		
		while(
				(toServer.getChannelStatus(ChannelFlowType.OUTBOUND) != ChannelStatus.CONNECTED ) &&
				(fromClient.getChannelStatus(ChannelFlowType.INBOUND) != ChannelStatus.CONNECTED )
			){
				
				System.out.println("NodePipelineManager : Waiting for sync ");
			
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	
		return new SimpleEntry<Thread, Thread>(serverThread,clientThread);
	}
	
	@Override
	public byte[] clientToServer(byte[] in,NodePipelineClient fromClient, NodePipelineServer toServer) {
		
		byte[] out = null;
		
		System.out.println("NodePipelineManager : clienttoServer : readFromNetwork : "+
				toServer.getChannelStatus(ChannelFlowType.INBOUND) +" : "+
				fromClient.getChannelStatus(ChannelFlowType.OUTBOUND)
		);
		
		if(
				toServer.getChannelStatus(ChannelFlowType.INBOUND) == ChannelStatus.CONNECTED && 
				fromClient.getChannelStatus(ChannelFlowType.OUTBOUND) == ChannelStatus.CONNECTED 
		) {
		
			fromClient.getNodeConfig().getOut().add(new NetworkByteBuffer(ByteBuffer.wrap("ECHO".getBytes())));
			fromClient.writeToNetwork();			
			ByteBuffer buffer = toServer.readFromNetwork();		
			
			out = new byte[buffer.limit()];
			
			buffer.get(out);
		}

		return out;
	}
	
	@Override
	public byte[] serverToClient(byte[] in,NodePipelineServer fromServer,NodePipelineClient toClient ) {
		
		byte[] out = null;
		
		System.out.println("NodePipelineManager : serverToClient : readFromNetwork : "+
				fromServer.getChannelStatus(ChannelFlowType.OUTBOUND) +" : "+
				toClient.getChannelStatus(ChannelFlowType.INBOUND)
		);			
		
		if(
				fromServer.getChannelStatus(ChannelFlowType.OUTBOUND) == ChannelStatus.CONNECTED && 
				toClient.getChannelStatus(ChannelFlowType.INBOUND) == ChannelStatus.CONNECTED 
		) {
			
			fromServer.getNodeConfig().getIn().add(new NetworkByteBuffer(ByteBuffer.wrap("ECHO".getBytes())));
			
			ByteBuffer buffer = toClient.readFromNetwork();			
	
			out = new byte[buffer.limit()];
			
			buffer.get(out);			
			
		}
		
		return out;
	}

	@Override
	public NetworkBus getNetworkBus() {
		return networkBus;
	}


}
