package org.flowr.framework.core.node.io.endpoint;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Set;

import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.node.io.NetworkByteBuffer;
import org.flowr.framework.core.node.io.flow.metric.ChannelMetric;
import org.flowr.framework.core.node.io.flow.protocol.FlowSocketChannel;
import org.flowr.framework.core.node.io.flow.protocol.NetworkProcessor;
import org.flowr.framework.core.node.io.flow.protocol.NetworkSubscription;
import org.flowr.framework.core.node.io.flow.protocol.NetworkSubscriptionContext;
import org.flowr.framework.core.node.io.network.NetworkGroup;
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupRoleType;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelFlowType;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelState;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelStatus;
import org.flowr.framework.core.node.io.pipeline.NetworkPipeline;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class NetworkServerSocketChannel implements FlowSocketChannel{

	private NetworkProcessor channelPublisher 								= null;
	private ChannelStatus channelStatus 									= ChannelStatus.DEFAULT;
	private ChannelState channelState										= ChannelState.DEFAULT;
	private ChannelFlowType channelFlowType 								= null;
	private AsynchronousServerSocketChannel asynchronousServerSocketChannel = null;
	private NetworkGroup networkGroup 										= null;
	private AsynchronousSocketChannel socketChannel							= null;
	private SocketAddress socketAddress										= null;
	private NetworkPipeline networkPipeline									= null;
	
	public NetworkServerSocketChannel(NetworkGroup networkGroup,ChannelFlowType channelFlowType,
			NetworkProcessor channelPublisher) throws IOException {
		
		this.networkGroup 		= networkGroup;
		this.channelFlowType	= channelFlowType;
		this.channelPublisher	= channelPublisher;
		
		this.asynchronousServerSocketChannel 	= AsynchronousServerSocketChannel.open(networkGroup.getGroup());
		
		switch(channelFlowType) {
			
			case INBOUND:{
				
				this.networkPipeline = this.networkGroup.getIn();
				break;
			}case OUTBOUND:{
				
				this.networkPipeline = this.networkGroup.getOut();
				break;
			}default:{
				break;
			}
		}
		
		this.socketAddress 		= networkGroup.getSocketAddress(
									this.networkPipeline.getHostName(),
									this.networkPipeline.getPortNumber());
		this.networkGroup.getIn().setSocketAddress(socketAddress);
		this.asynchronousServerSocketChannel.bind(socketAddress);
		
		channelStatus 			= ChannelStatus.INITIALIZED;
		//System.out.println("NetworkServerSocketChannel : serverSocketAddress : "+socketAddress);
	}
	
	@Override
	public ChannelStatus register() {
		
		networkGroup.registerSocketAddress(NetworkGroupRoleType.SERVER,socketAddress);
		channelStatus = ChannelStatus.REGISTERED;
		return channelStatus;
	}
	
	@Override
	public ChannelStatus close() {
		
		networkGroup.deregisterSocketAddress(NetworkGroupRoleType.SERVER,socketAddress);
		
		try {
			socketChannel.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		channelStatus = ChannelStatus.DISCONNECTED;
		
		return channelStatus;
	}
	
	@Override
	public ChannelStatus connect(SocketAddress serverSocketAddress) throws IOException {
				
		asynchronousServerSocketChannel.accept(channelPublisher, new CompletionHandler<AsynchronousSocketChannel,NetworkProcessor>(){

			@Override
			public void completed(AsynchronousSocketChannel asynchronousSocketChannel, NetworkProcessor channelProcessor) {
				
				socketChannel = asynchronousSocketChannel;				
				register();
				channelStatus = ChannelStatus.CONNECTED;				
				channelProcessor.onNext(ChannelMetric.connectSuccessMetric(networkPipeline)); 
			}

			@Override
			public void failed(Throwable arg0, NetworkProcessor channelProcessor) {
				close();
				channelStatus = ChannelStatus.DISCONNECTED;
				channelProcessor.onNext(ChannelMetric.connectErrorMetric(networkPipeline));
			}
			
		});		
		
		while(channelStatus == ChannelStatus.CONNECTED || channelStatus == ChannelStatus.DISCONNECTED) {
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		
		return channelStatus;
	}
	
	@Override
	public ByteBuffer readFromPipeline()  {
		
		ByteBuffer buffer = ByteBuffer.allocateDirect(FrameworkConstants.FRAMEWORK_NETWORK_IO_BUFFER_BLOCK);	
					
		channelState	= ChannelState.READ_INITIATED;
	
		socketChannel.read(buffer,  channelPublisher,new CompletionHandler<Integer,NetworkProcessor>() {

			@Override
			public void completed(Integer arg0, NetworkProcessor channelProcessor) {
								
				networkPipeline.add(new NetworkByteBuffer(buffer));
				channelProcessor.onNext(ChannelMetric.readSuccessMetric(networkPipeline));
				buffer.flip();
			}

			@Override
			public void failed(Throwable throwable, NetworkProcessor channelProcessor) {
				channelProcessor.onNext(ChannelMetric.readErrorMetric(networkPipeline));
				channelProcessor.onError(throwable);
			}
			
		});
		
		channelState	= ChannelState.READ_COMPLETED;
		
		return buffer;
	}
	
	@Override
	public ByteBuffer seek()  {
			
		ByteBuffer buffer = ByteBuffer.allocateDirect(FrameworkConstants.FRAMEWORK_NETWORK_IO_BUFFER_UNIT);	
					
		channelState	= ChannelState.READ_INITIATED;
	
		NetworkSubscriptionContext networkSubscriptionContext = 
				((NetworkSubscription)this.channelPublisher.getSubscription()).getOptionalSubscriptionContext().get();
		
		socketChannel.read(
			buffer, 
			networkSubscriptionContext.getSeekTimeout(), 
			networkSubscriptionContext.getTimeUnit(), 
			channelPublisher, 
			new CompletionHandler<Integer,NetworkProcessor>() {

				@Override
				public void completed(Integer arg0, NetworkProcessor channelProcessor) {
									
					networkPipeline.onSeek(new NetworkByteBuffer(buffer));
					channelProcessor.onSeek(ChannelMetric.seekMetric(networkPipeline));
					buffer.flip();
				}

				@Override
				public void failed(Throwable throwable, NetworkProcessor channelProcessor) {					
					
					channelProcessor.onError(throwable);
					channelProcessor.onSeek(ChannelMetric.seekErrorMetric(networkPipeline));
				}
				
			});
				
		
		channelState	= ChannelState.READ_COMPLETED;
		channelStatus = ChannelStatus.SEEK;
		
		return buffer;
	}
	
	@Override
	public void cancel()  {
		
		this.networkPipeline.onCancel();
		this.channelPublisher.onCancel(ChannelMetric.cancelMetric(networkPipeline));		
		
		channelState	= ChannelState.DEFAULT;		
		channelStatus = ChannelStatus.CANCEL;
		
	}
	
	@Override
	public void writeToPipeline() {
		
		if(!networkPipeline.getQueue().isEmpty()) {
			
			channelState	= ChannelState.WRITE_INITIATED;
		
			networkPipeline.getQueue().forEach( 
					
				(q) -> {
				
					socketChannel.write(q.getByteBuffer(), channelPublisher,new CompletionHandler<Integer,NetworkProcessor>() {
			
						@Override
						public void completed(Integer arg0, NetworkProcessor channelProcessor) {
							channelProcessor.onNext(ChannelMetric.writeSuccessMetric(networkPipeline));
						}
			
						@Override
						public void failed(Throwable throwable, NetworkProcessor channelProcessor) {
							channelProcessor.onError(throwable);
							channelProcessor.onNext(ChannelMetric.writeErrorMetric(networkPipeline));
						}
						
					});	
				}
			);	
			
			channelState	= ChannelState.WRITE_COMPLETED;			
		}	
	
	}
	
	public Set<SocketOption<?>> getDefaultSupportedOption(){
		
		return socketChannel.supportedOptions();
	}

	public ChannelFlowType getChannelFlowType() {
		return channelFlowType;
	}

	public AsynchronousServerSocketChannel getAsynchronousServerSocketChannel() {
		return asynchronousServerSocketChannel;
	}

	public NetworkGroup getNetworkGroup() {
		return networkGroup;
	}

	public AsynchronousSocketChannel getSocketChannel() {
		return socketChannel;
	}

	public SocketAddress getSocketAddress() {
		return socketAddress;
	}
	
	public ChannelStatus getChannelStatus() {
		return channelStatus;
	}

	public void setChannelStatus(ChannelStatus channelStatus) {
		this.channelStatus = channelStatus;
	}

	public ChannelState getChannelState() {
		return channelState;
	}

	public void setChannelState(ChannelState channelState) {
		this.channelState = channelState;
	}
	
	public String toString() {
		
		return "NetworkServerSocketChannel { "
				+ channelFlowType+" | "+networkGroup+" | "+
				" | "+channelStatus+" | "+channelStatus+" | "+				
				" | "+asynchronousServerSocketChannel+" | "+socketChannel+" | "+
				" | "+socketAddress+" } ";
	}


}
