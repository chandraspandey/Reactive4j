package org.flowr.framework.core.node.io.endpoint;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.time.Instant;
import java.util.Set;

import org.flowr.framework.core.constants.NodeConstants;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;
import org.flowr.framework.core.node.io.flow.IOFlow.IOFlowType;
import org.flowr.framework.core.node.io.flow.protocol.NetworkProcessor;
import org.flowr.framework.core.node.io.network.NetworkGroup;
import org.flowr.framework.core.node.io.network.NodeChannel;
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupStatus;
import org.flowr.framework.core.node.io.network.NetworkGroup.NodeGroup;
import org.flowr.framework.core.node.io.pipeline.NetworkPipeline;

/**
 * Models 1:N relationship between local Server & remote/local Clients.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public class NodePipelineClient implements Runnable,NodeChannel{

	private NetworkGroup networkGroup							= null;
	private NetworkProcessor clientPublisher 					= null;
	private boolean keepRunning									= false;
	private NodeConfig nodeConfig								= null;
	private NodeEndPointConfiguration nodeEndPointConfiguration = null;
	
	private NetworkPipeline in									= null;
	private SocketAddress	inSocketAddress						= null; 		
	private NetworkClientSocketChannel inSocketChannel  		= null;
	
	private NetworkPipeline out									= null;	
	private SocketAddress	outSocketAddress					= null;	
	private NetworkClientSocketChannel outSocketChannel 		= null;
	
	public NodePipelineClient(NodeEndPointConfiguration nodeEndPointConfiguration) throws IOException {	
		
		this.nodeEndPointConfiguration = nodeEndPointConfiguration;
		
		this.in 	= new NetworkPipeline();
		this.in.setPipelineName(nodeEndPointConfiguration.getInboundPipelineName());
		this.in.setEventType(EventType.NETWORK);
		this.in.setPipelineFunctionType(PipelineFunctionType.PIPELINE_NETWORK_CLIENT);
		this.in.setIOFlowType(IOFlowType.INBOUND);
		this.in.setPipelineType(PipelineType.BUS);
		this.in.setHostName(nodeEndPointConfiguration.getInboundPipelineHostName());
		this.in.setPortNumber(nodeEndPointConfiguration.getInboundPipelinePortNumber());		
		
		this.out = new NetworkPipeline();
		this.out.setPipelineName(nodeEndPointConfiguration.getOutboundPipelineName());
		this.out.setEventType(EventType.NETWORK);
		this.out.setPipelineFunctionType(PipelineFunctionType.PIPELINE_NETWORK_CLIENT);
		this.out.setIOFlowType(IOFlowType.OUTBOUND);
		this.out.setPipelineType(PipelineType.BUS);
		this.out.setHostName(nodeEndPointConfiguration.getInboundPipelineHostName());
		this.out.setPortNumber(nodeEndPointConfiguration.getOutboundPipelinePortNumber());
				
		this.nodeConfig = new NodeConfig(
							in,
							out,
							nodeEndPointConfiguration.getChannelName(),
							nodeEndPointConfiguration.getNetworkGroupType(),
							nodeEndPointConfiguration.isReuseNetworkGroup(),
							nodeEndPointConfiguration.getPipelineMetricSubscriber(),
							nodeEndPointConfiguration.getProtocolPublishType(),
							nodeEndPointConfiguration.getHandleFor(),
							nodeEndPointConfiguration.getHandleAs()
		);
		
		this.clientPublisher = new NetworkProcessor(nodeConfig);
		NetworkGroupStatus networkGroupStatus = init();
		System.out.println("NodePipelineClient : networkGroupStatus : "+networkGroupStatus);;

	}
	
	@Override
	public void run() {
		
		while(keepRunning) {
			
			System.out.println(" NodePipelineClient : "+Instant.now().toString()+ " | < IN : "+in.getQueue().size()+" : "+
					inSocketAddress+" > | < OUT : "+out.getQueue().size()+" : "+outSocketAddress+" >");	
			
			try {
				Thread.sleep(NodeConstants.NODE_PIPELINE_CLIENT_SLEEP_INTERVAL);
			} catch (InterruptedException e) {
					e.printStackTrace();
			}
		}
	}
	
	@Override
	public NetworkGroupStatus init() throws IOException {
		
		NetworkGroupStatus networkGroupStatus = NetworkGroupStatus.UNKNOWN;
		
		this.networkGroup			= NodeGroup.instance(nodeConfig);
		this.networkGroup.init(in,out,nodeEndPointConfiguration.getPipelineHandler());		
		
		this.inSocketChannel 		= new NetworkClientSocketChannel(this.networkGroup,ChannelFlowType.INBOUND,clientPublisher);
		this.outSocketChannel 		= new NetworkClientSocketChannel(this.networkGroup,ChannelFlowType.OUTBOUND,clientPublisher);

		this.inSocketAddress  = getSocketAddress(ChannelFlowType.INBOUND);
		this.outSocketAddress = getSocketAddress(ChannelFlowType.OUTBOUND);
	
		keepRunning	  				= true;
		networkGroupStatus = NetworkGroupStatus.ASSOCIATED;
		
		return networkGroupStatus;
	}

	@Override
	public SocketAddress getSocketAddress(ChannelFlowType channelFlowType) {
		
		SocketAddress socketAddress = null;
		
		switch(channelFlowType) {
		
			case INBOUND:{
				
				socketAddress = inSocketChannel.getSocketAddress();
				break;
			}case OUTBOUND:{
				
				socketAddress = outSocketChannel.getSocketAddress();
				break;
			}default:{
				break;
			}
		}
		
		return socketAddress;
	}
	
	@Override
	public ByteBuffer readFromNetwork() {
		return this.inSocketChannel.readFromPipeline();
	}
	
	
	
	@Override
	public void writeToNetwork() {
			
		this.outSocketChannel.writeToPipeline();	
	}
	
	@Override
	public ChannelStatus connect(SocketAddress serverSocketAddress,ChannelFlowType channelFlowType) throws IOException {
				
		ChannelStatus channelStatus = ChannelStatus.DISCONNECTED;
		
		switch(channelFlowType) {
		
			case INBOUND:{
			
				if(
						this.inSocketChannel.connect(serverSocketAddress)	== ChannelStatus.CONNECTED 
				) {
					channelStatus = ChannelStatus.CONNECTED;
				}
				System.out.println("NodePipelineClient : connect : inServerSocketChannel : "+inSocketChannel.getChannelStatus());
				break;
			}case OUTBOUND:{
				if(
						this.outSocketChannel.connect(serverSocketAddress) == ChannelStatus.CONNECTED 
				) {
					channelStatus = ChannelStatus.CONNECTED;
				}
				System.out.println("NodePipelineClient : connect : outServerSocketChannel : "+outSocketChannel.getChannelStatus());
	
				break;
			}default:{
				break;
			}
		}					

		return channelStatus;		
		
	}
	
	@Override
	public Set<SocketOption<?>> getConfiguredOption() {
		
		return  NodeChannel.getNodeChannelSocketOption().getConfiguredOptions();		
	}
	
	@Override
	public Set<SocketOption<?>> getDefaultSupportedOption(ChannelFlowType channelFlowType) {
		
		Set<SocketOption<?>> socketOption = null;
		
		switch(channelFlowType) {
		
			case INBOUND:{
			
				socketOption = this.inSocketChannel.getDefaultSupportedOption();
				break;
			}case OUTBOUND:{
				
				socketOption = this.outSocketChannel.getDefaultSupportedOption();
				break;
			}default:{
				break;
			}
		}
		
		return socketOption;
	}
	
	@Override
	public void configureSocketOption(ChannelFlowType channelFlowType) {
		
		switch(channelFlowType) {
		
		case INBOUND:{
			
				NodeChannel.getNodeChannelSocketOption().configure((Channel) this.inSocketChannel, 
					this.inSocketChannel.getDefaultSupportedOption());
				break;
			}case OUTBOUND:{
				
				NodeChannel.getNodeChannelSocketOption().configure((Channel) this.outSocketChannel, 
						this.outSocketChannel.getDefaultSupportedOption());				
				break;
			}default:{
				break;
			}
		}
	}
			
	@Override	
	public ChannelStatus getChannelStatus(ChannelFlowType channelFlowType) {

		ChannelStatus channelStatus = ChannelStatus.DEFAULT;
		
		switch(channelFlowType) {
		
			case INBOUND:{
				
				channelStatus = inSocketChannel.getChannelStatus();
				break;
			}case OUTBOUND:{
				
				channelStatus = outSocketChannel.getChannelStatus();
				break;
			}default:{
				break;
			}
		}
		return channelStatus;
	}
	
	@Override
	public ChannelState getChannelState(ChannelFlowType channelFlowType) {

		ChannelState channelState = ChannelState.DEFAULT;
		
		switch(channelFlowType) {
		
			case INBOUND:{
				
				channelState = inSocketChannel.getChannelState();
				break;
			}case OUTBOUND:{
				
				channelState = outSocketChannel.getChannelState();
				break;
			}default:{
				break;
			}
		}
		return channelState;
	}
	
	@Override
	public String getChannelName() {
		return this.nodeConfig.getChannelName();
	}
	
	@Override
	public NetworkGroupStatus close()  {
		
		NetworkGroupStatus networkGroupStatus = NetworkGroupStatus.UNKNOWN;
		
		if(
				inSocketChannel.close() 	== ChannelStatus.DISCONNECTED &&
				outSocketChannel.close() 	== ChannelStatus.DISCONNECTED &&
				networkGroup.close(nodeEndPointConfiguration.getPipelineHandler()) 	
											== NetworkGroupStatus.DISASSOCIATED
		) {
			
			networkGroupStatus = NetworkGroupStatus.DISASSOCIATED;
		}
		
		
		keepRunning	  = false;
		return networkGroupStatus;
	}
	
	@Override
	public NodeConfig getNodeConfig() {
		return nodeConfig;
	}

	public NodeEndPointConfiguration getNodeEndPointConfiguration() {
		return nodeEndPointConfiguration;
	}
	
	@Override
	public NetworkGroup getNetworkGroup() {
		return networkGroup;
	}
}
