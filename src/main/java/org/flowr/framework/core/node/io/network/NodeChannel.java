package org.flowr.framework.core.node.io.network;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.flowr.framework.core.node.io.endpoint.NodeConfig;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NodeChannel extends NodeChannelLifeCycle{

	public enum ChannelFlowType{
		INBOUND,
		OUTBOUND,
		INTERRUPT
	}
	
	public enum ChannelStatus{
		DEFAULT,
		INITIALIZED,
		REGISTERED,
		SEEK,
		CANCEL,
		READ,
		WRITE,
		CONNECTED,
		DISCONNECTED,
		ERROR
	}	
	
	public enum ChannelState{
		DEFAULT,
		READ_INITIATED,
		READ_ERROR,
		READ_COMPLETED,
		WRITE_INITIATED,
		WRITE_ERROR,
		WRITE_COMPLETED		
	}
	
	public enum ChannelType{
		CLIENT,
		SERVER,
		PIPE
	}	
	
	public enum ChannelProtocol{
		START,
		ACK,
		NACK,
		ERROR,
		TIMEOUT,
		END
	}	
	
	public String getChannelName();
	
	public ByteBuffer readFromNetwork();

	public void writeToNetwork();
	
	public SocketAddress getSocketAddress(ChannelFlowType channelFlowType);

	public ChannelStatus connect(SocketAddress serverSocketAddress,ChannelFlowType channelFlowType) throws IOException;

	public Set<SocketOption<?>> getConfiguredOption();
	
	public Set<SocketOption<?>> getDefaultSupportedOption(ChannelFlowType channelFlowType);

	public void configureSocketOption(ChannelFlowType channelFlowType);
	
	public ChannelStatus getChannelStatus(ChannelFlowType channelFlowType);
	
	public ChannelState getChannelState(ChannelFlowType channelFlowType); 
	
	public NodeConfig getNodeConfig();
	
	public NetworkGroup getNetworkGroup();

	public static NodeChannelSocketOption getNodeChannelSocketOption() {
		
		return NodeChannelSocketOption.getInstance();
	}

	public class NodeChannelSocketOption{

		private static NodeChannelSocketOption nodeChannelSocketOption = null;
		private SocketOption<Boolean> lifeOption 			= StandardSocketOptions.SO_KEEPALIVE;
		private SocketOption<Boolean> addressOption 		= StandardSocketOptions.SO_REUSEADDR;
		private SocketOption<Boolean> portOption 			= StandardSocketOptions.SO_REUSEPORT;
		private SocketOption<Boolean> tcpOption 			= StandardSocketOptions.TCP_NODELAY;
		private SocketOption<Integer> sendBufferOption 		= StandardSocketOptions.SO_SNDBUF;
		private SocketOption<Integer> recieveBufferOption 	= StandardSocketOptions.SO_RCVBUF;
		private Set<SocketOption<?>> configuredOptions		= new HashSet<SocketOption<?>>();
		
		public static NodeChannelSocketOption getInstance() {
						
			
			if(nodeChannelSocketOption == null ) {
				
				nodeChannelSocketOption = new NodeChannelSocketOption();
			}
			
			return nodeChannelSocketOption;
		}
		
		private NodeChannelSocketOption() {
			
			configuredOptions.add(lifeOption);
			configuredOptions.add(addressOption);
			configuredOptions.add(portOption);
			configuredOptions.add(tcpOption);
			configuredOptions.add(sendBufferOption);
			configuredOptions.add(recieveBufferOption);			
		}
		
		@SuppressWarnings("unchecked")
		public Channel configure(Channel channel, Set<SocketOption<?>> supportedOptions ) {
			
			if(supportedOptions == null) {
				supportedOptions = configuredOptions;
			}		
		
			Iterator<SocketOption<?>> optionIterator = supportedOptions.iterator();
			
			while(optionIterator.hasNext()) {
				
				SocketOption<?> socketOption = optionIterator.next();
				
				try {
					
					if(socketOption.type() == Boolean.class) {
						
						SocketOption<Boolean> sOption = (SocketOption<Boolean>) socketOption;
				
						if(channel instanceof AsynchronousServerSocketChannel) {														
							
							((AsynchronousServerSocketChannel)channel).setOption(sOption, Boolean.TRUE);								
						}else if(channel instanceof AsynchronousSocketChannel) {
							((AsynchronousSocketChannel)channel).setOption(sOption, Boolean.TRUE);
						}
					}else if(socketOption.type() == Integer.class){
						
						SocketOption<Integer> sOption = (SocketOption<Integer>) socketOption;						

						if(channel instanceof AsynchronousServerSocketChannel) {															
								((AsynchronousServerSocketChannel)channel).setOption(sOption, Integer.valueOf("1024"));
								
						}else if(channel instanceof AsynchronousSocketChannel) {
							((AsynchronousSocketChannel)channel).setOption(sOption, Integer.valueOf("1024"));
						}
						
					}						

				} catch (IOException | UnsupportedOperationException e) {
					e.printStackTrace();
					System.err.println("NodeChannelSocketOption : Unsupported SocketOption : "+socketOption);
					optionIterator.remove();
				}
			}
			
			return channel;			
		}
		
		public Set<SocketOption<?>> getConfiguredOptions(){
			
			return configuredOptions;
		}
		
	}


}
