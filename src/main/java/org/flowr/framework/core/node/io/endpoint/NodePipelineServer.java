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
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupStatus;
import org.flowr.framework.core.node.io.network.NetworkGroup.NodeGroup;
import org.flowr.framework.core.node.io.network.NodeChannel;
import org.flowr.framework.core.node.io.pipeline.NetworkPipeline;

/**
 * Models 1:N relationship between local Server & remote/local Clients.
 * @author Chandra Shekhar Pandey
 *
 */
public class NodePipelineServer implements Runnable, NodeChannel{

	private NetworkGroup networkGroup							= null;
	private NetworkProcessor serverPublisher 					= null;
	private boolean keepRunning									= false;
	private NodeEndPointConfiguration nodeEndPointConfiguration = null;	
	private NodeConfig nodeConfig								= null;
	
	private NetworkPipeline in									= null;
	private SocketAddress	inSocketAddress						= null;	
	private NetworkServerSocketChannel inServerSocketChannel	= null; 
	
	private NetworkPipeline out									= null;
	private SocketAddress	outSocketAddress					= null;	
	private NetworkServerSocketChannel outServerSocketChannel	= null;

	
	public NodePipelineServer(NodeEndPointConfiguration nodeEndPointConfiguration) throws IOException {
		
		this.nodeEndPointConfiguration = nodeEndPointConfiguration;
		
		this.in 	= new NetworkPipeline();
		this.in.setPipelineName(nodeEndPointConfiguration.getInboundPipelineName());
		this.in.setEventType(EventType.NETWORK);
		this.in.setPipelineFunctionType(PipelineFunctionType.PIPELINE_NETWORK_SERVER);
		this.in.setIOFlowType(IOFlowType.INBOUND);
		this.in.setPipelineType(PipelineType.BUS);
		this.in.setHostName(nodeEndPointConfiguration.getInboundPipelineHostName());
		this.in.setPortNumber(nodeEndPointConfiguration.getInboundPipelinePortNumber());
		
		this.out = new NetworkPipeline();
		this.out.setPipelineName(nodeEndPointConfiguration.getOutboundPipelineName());
		this.out.setEventType(EventType.NETWORK);
		this.out.setPipelineFunctionType(PipelineFunctionType.PIPELINE_NETWORK_SERVER);
		this.out.setIOFlowType(IOFlowType.OUTBOUND);
		this.out.setPipelineType(PipelineType.BUS);
		this.out.setHostName(nodeEndPointConfiguration.getOutboundPipelineHostName());
		this.out.setPortNumber(nodeEndPointConfiguration.getOutboundPipelinePortNumber());
				
		this.nodeConfig = new NodeConfig(
							in,
							out,
							nodeEndPointConfiguration.getChannelName(),
							nodeEndPointConfiguration.getNetworkGroupType(),
							true,
							nodeEndPointConfiguration.getPipelineMetricSubscriber(),
							nodeEndPointConfiguration.getProtocolPublishType(),
							nodeEndPointConfiguration.getHandleFor(),
							nodeEndPointConfiguration.getHandleAs()
		);
		
		serverPublisher = new NetworkProcessor(nodeConfig);
		NetworkGroupStatus networkGroupStatus = init();
		System.out.println("NodePipelineServer : SERVER : networkGroupStatus : "+networkGroupStatus);
	}
	
	@Override
	public void run() {
		
		while(keepRunning) {
			
			System.out.println(" NodePipelineServer : "+Instant.now().toString()+ " | < IN : "+in.getQueue().size()+" : "+
					inSocketAddress+" > | < OUT : "+out.getQueue().size()+" : "+outSocketAddress+" >");					
			
			try {
				Thread.sleep(NodeConstants.NODE_PIPELINE_SERVER_SLEEP_INTERVAL);
			} catch (InterruptedException e) {
					e.printStackTrace();
			}
		}
	}
		
	@Override
	public SocketAddress getSocketAddress(ChannelFlowType channelFlowType) {
		
		SocketAddress socketAddress = null;
		
		switch(channelFlowType) {
		
			case INBOUND:{
				
				socketAddress = inServerSocketChannel.getSocketAddress();
				break;
			}case OUTBOUND:{
				
				socketAddress = outServerSocketChannel.getSocketAddress();
				break;
			}default:{
				break;
			}
		}
		
		return socketAddress;
	}
	
	@Override
	public NetworkGroupStatus init() throws IOException {
		
		NetworkGroupStatus networkGroupStatus = NetworkGroupStatus.UNKNOWN;
		
		this.networkGroup			= NodeGroup.instance(nodeConfig);
		this.networkGroup.init(in,out,nodeEndPointConfiguration.getPipelineHandler());
		
		this.inServerSocketChannel  = new NetworkServerSocketChannel(this.networkGroup,ChannelFlowType.INBOUND,serverPublisher);
		this.outServerSocketChannel  = new NetworkServerSocketChannel(this.networkGroup,ChannelFlowType.OUTBOUND,serverPublisher);			
		keepRunning	  			= true;
		
		this.inSocketAddress  = getSocketAddress(ChannelFlowType.INBOUND);
		this.outSocketAddress = getSocketAddress(ChannelFlowType.OUTBOUND);

		networkGroupStatus = NetworkGroupStatus.ASSOCIATED;
		
		return networkGroupStatus;
	}
	
	@Override
	public ByteBuffer readFromNetwork()  {
				
		return this.inServerSocketChannel.readFromPipeline();
	}
	
	@Override
	public void writeToNetwork() {
		
		this.outServerSocketChannel.writeToPipeline();	
	}
		
	@Override
	public ChannelStatus connect(SocketAddress serverSocketAddress,ChannelFlowType channelFlowType) throws IOException {
		
		ChannelStatus serverChannelStatus = ChannelStatus.DISCONNECTED;
		
		switch(channelFlowType) {
		
			case INBOUND:{
			
				if(
						this.inServerSocketChannel.connect(serverSocketAddress)	== ChannelStatus.CONNECTED 
				) {
					serverChannelStatus = ChannelStatus.CONNECTED;
				}
				System.out.println("NodePipelineServer : connect : inServerSocketChannel : "+inServerSocketChannel.getChannelStatus());
				break;
			}case OUTBOUND:{
				if(
						this.outServerSocketChannel.connect(serverSocketAddress) == ChannelStatus.CONNECTED 
				) {
					serverChannelStatus = ChannelStatus.CONNECTED;
				}
				System.out.println("NodePipelineServer : connect : outServerSocketChannel : "+outServerSocketChannel.getChannelStatus());
				break;
			}default:{
				break;
			}
		}		

		return serverChannelStatus;
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
			
				socketOption = this.inServerSocketChannel.getDefaultSupportedOption();
				break;
			}case OUTBOUND:{
				
				socketOption = this.outServerSocketChannel.getDefaultSupportedOption();
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
			
				NodeChannel.getNodeChannelSocketOption().configure((Channel) this.inServerSocketChannel, 
					this.inServerSocketChannel.getDefaultSupportedOption());
				break;
			}case OUTBOUND:{
				
				NodeChannel.getNodeChannelSocketOption().configure((Channel) this.outServerSocketChannel, 
						this.outServerSocketChannel.getDefaultSupportedOption());				
				break;
			}default:{
				break;
			}
		}
	}
	
	@Override
	public String getChannelName() {
		return this.nodeConfig.getChannelName();
	}
	
	@Override
	public ChannelStatus getChannelStatus(ChannelFlowType channelFlowType) {
		
		ChannelStatus channelStatus = ChannelStatus.DEFAULT;
		
		switch(channelFlowType) {
		
			case INBOUND:{
				
				channelStatus = inServerSocketChannel.getChannelStatus();
				break;
			}case OUTBOUND:{
				
				channelStatus = outServerSocketChannel.getChannelStatus();
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
				
				channelState = inServerSocketChannel.getChannelState();
				break;
			}case OUTBOUND:{
				
				channelState = outServerSocketChannel.getChannelState();
				break;
			}default:{
				break;
			}
		}
		return channelState;
	}
	
	@Override
	public NetworkGroupStatus close() {
		
		NetworkGroupStatus networkGroupStatus = NetworkGroupStatus.UNKNOWN;
		
		if(
				inServerSocketChannel.close() 	== ChannelStatus.DISCONNECTED &&
				outServerSocketChannel.close() 	== ChannelStatus.DISCONNECTED &&
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
