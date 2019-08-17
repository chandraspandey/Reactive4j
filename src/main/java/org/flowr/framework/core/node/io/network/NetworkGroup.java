package org.flowr.framework.core.node.io.network;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.AsynchronousChannelGroup;
import java.util.AbstractMap.SimpleEntry;

import org.flowr.framework.core.node.io.endpoint.NodeConfig;
import org.flowr.framework.core.node.io.flow.ByteEnumerableType;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler;
import org.flowr.framework.core.node.io.pipeline.NetworkPipeline;

/**
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface NetworkGroup {

	/**
	 * 
	 * Defines network group for classification & handling
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum NetworkGroupType implements ByteEnumerableType{
		NONE(0),
		LOCAL(1),
		REMOTE(2);

		private byte code = 0;
		
		NetworkGroupType(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static NetworkGroupType getType(int code) {
			
			NetworkGroupType networkGroupType = NONE;
			
			switch((byte) code) {
				
				case 0:{
					networkGroupType = NONE;
					break;
				}case 1:{
					networkGroupType = LOCAL;
					break;
				}case 2:{
					networkGroupType = REMOTE;
					break;
				}default :{
					networkGroupType = NONE;
					break;
				}			
			}
			
			return networkGroupType;
		}
	}
	
	/**
	 * 
	 * Defines network group role for classification & handling
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum NetworkGroupRoleType implements ByteEnumerableType{
		NONE(0),
		CLIENT(1),
		SERVER(2);
		
		private byte code = 0;
		
		NetworkGroupRoleType(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static NetworkGroupRoleType getType(int code) {
			
			NetworkGroupRoleType networkGroupRoleType = NONE;
			
			switch((byte) code) {
				
				case 0:{
					networkGroupRoleType = NONE;
					break;
				}case 1:{
					networkGroupRoleType = CLIENT;
					break;
				}case 2:{
					networkGroupRoleType = SERVER;
					break;
				}default :{
					networkGroupRoleType = NONE;
					break;
				}			
			}
			
			return networkGroupRoleType;
		}
	}
	
	/**
	 * 
	 * Defines network group status for operational handling
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum NetworkGroupStatus implements ByteEnumerableType{
		NONE(0),
		UNKNOWN(1),
		INVALID(2),
		IGNORED(3),		
		ASSOCIATED(4),
		DISASSOCIATED(5),
		ADDED(6),
		REMOVED(7);
		
		private byte code = 0;
		
		NetworkGroupStatus(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static NetworkGroupStatus getType(int code) {
			
			NetworkGroupStatus networkGroupStatus = NONE;
			
			switch((byte) code) {
				
				case 0:{
					networkGroupStatus = NONE;
					break;
				}case 1:{
					networkGroupStatus = UNKNOWN;
					break;
				}case 2:{
					networkGroupStatus = INVALID;
					break;
				}case 3:{
					networkGroupStatus = IGNORED;
					break;
				}case 4:{
					networkGroupStatus = ASSOCIATED;
					break;
				}case 5:{
					networkGroupStatus = DISASSOCIATED;
					break;
				}case 6:{
					networkGroupStatus = ADDED;
					break;
				}case 7:{
					networkGroupStatus = REMOVED;
					break;
				}default :{
					networkGroupStatus = NONE;
					break;
				}			
			}
			
			return networkGroupStatus;
		}
	}		
	
	public NetworkGroupStatus init(NetworkPipeline in,NetworkPipeline out, SimpleEntry<IntegrationPipelineHandler,
			IntegrationPipelineHandler>	pipelineHandlerSet);
	
	public NetworkGroupStatus close(SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> pipelineHandlerSet);
	
	public NetworkPipeline getIn();
	
	public NetworkPipeline getOut();
	
	public SocketAddress getSocketAddress(String hostName, int portNumber) throws UnknownHostException;
	
	public String getGroupName();
	
	public AsynchronousChannelGroup getGroup();
	
	public void setGroupName(String groupName);
	
	public NetworkGroupStatus registerSocketAddress(NetworkGroupRoleType networkGroupRoleType,SocketAddress socketAddress);
	
	public NetworkGroupStatus deregisterSocketAddress(NetworkGroupRoleType networkGroupRoleType,SocketAddress socketAddress);

	public NetworkEntry getServerNetworkEntry();
	
	public static class NodeGroup{
		
		public static NetworkGroup localApplicationGroup;
		public static NetworkGroup remoteApplicationGroup;
		
		public static NetworkGroup instance(NodeConfig nodeConfig) throws IOException{
			
			NetworkGroup networkGroup = null;
			
			switch(nodeConfig.getNetworkGroupType()) {
				
				case LOCAL:{
					
					if(nodeConfig.isReuseNetworkGroup()) {
						
						if(localApplicationGroup == null) {
						
							localApplicationGroup = new LocalApplicationGroup();							
						}
						
						networkGroup = localApplicationGroup;
					}else {
						networkGroup = new LocalApplicationGroup();
					}
					
					break;
				}case REMOTE:{
					
					if(nodeConfig.isReuseNetworkGroup()) {
						
						if(remoteApplicationGroup == null) {
						
							localApplicationGroup = new RemoteApplicationGroup();
							
						}						
						networkGroup = remoteApplicationGroup;
					}else {
						networkGroup = new RemoteApplicationGroup();
					}
					break;
				}default:{
					networkGroup = new LocalApplicationGroup();
					break;
				}
				
			}
			
			networkGroup.setGroupName(nodeConfig.getNetworkGroupType().name());
			
			return networkGroup;
		}
		
	}

	
}
