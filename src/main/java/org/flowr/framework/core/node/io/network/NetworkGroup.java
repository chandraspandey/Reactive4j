package org.flowr.framework.core.node.io.network;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.AsynchronousChannelGroup;
import java.util.AbstractMap.SimpleEntry;

import org.flowr.framework.core.node.io.endpoint.NodeConfig;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler;
import org.flowr.framework.core.node.io.pipeline.NetworkPipeline;

/**
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface NetworkGroup {

	public enum NetworkGroupType{
		LOCAL,
		REMOTE
	}
	
	public enum NetworkGroupRoleType{
		CLIENT,
		SERVER
	}
	
	public enum NetworkGroupStatus{
		UNKNOWN,
		INVALID,
		IGNORED,		
		ASSOCIATED,
		DISASSOCIATED,
		ADDED,
		REMOVED
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
