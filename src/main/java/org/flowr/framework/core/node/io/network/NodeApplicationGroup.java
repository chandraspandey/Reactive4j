package org.flowr.framework.core.node.io.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.AsynchronousChannelGroup;
import java.util.AbstractMap.SimpleEntry;

import org.flowr.framework.core.node.ha.BackPressureExecutors;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler;
import org.flowr.framework.core.node.io.pipeline.NetworkBus;
import org.flowr.framework.core.node.io.pipeline.NetworkPipeline;

/**
 * 
 * Models Network group.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public abstract class NodeApplicationGroup implements NetworkGroup{

	private volatile NetworkPipeline in;
	private volatile NetworkPipeline out;
	private String groupName;
	private AsynchronousChannelGroup asynchronousChannelGroup;
	
	public NodeApplicationGroup() throws IOException {
		
		asynchronousChannelGroup = AsynchronousChannelGroup.withThreadPool(
									BackPressureExecutors.newCachedBackPressureThreadPool());
		
	}
	
	public NetworkGroupStatus init(NetworkPipeline in,NetworkPipeline out,SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler>
		pipelineHandlerSet) {
		
		this.in 	= in;
		this.out	= out; 
		
		return NetworkBus.getInstance().addNetworkGroup(this,pipelineHandlerSet);
	}
	
	public NetworkGroupStatus close(SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> pipelineHandlerSet) {		
		
		this.in 	= null;
		this.out	= null; 
		this.asynchronousChannelGroup.shutdown();
		return NetworkBus.getInstance().removeNetworkGroup(this,pipelineHandlerSet);
	}
	
	public SocketAddress getSocketAddress(String hostName, int portNumber) throws UnknownHostException {
		
		SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(hostName), portNumber);
		System.out.println("NodeApplicationGroup : "+socketAddress);

		return socketAddress;
	}
		
	
	public String getGroupName() {
		
		return this.groupName;
	}
	
	public AsynchronousChannelGroup getGroup() {
		
		return this.asynchronousChannelGroup;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public NetworkPipeline getIn() {
		return in;
	}

	public void setIn(NetworkPipeline in) {
		this.in = in;
	}

	public NetworkPipeline getOut() {
		return out;
	}

	public void setOut(NetworkPipeline out) {
		this.out = out;
	}
	
	public String toString(){
		
		return "NodeApplicationGroup{"+
				" groupName : "+groupName+	
				" in : "+in.getPipelineName()+
				" out : "+out.getPipelineName()+
				" asynchronousChannelGroup : "+asynchronousChannelGroup+	
				"}\n";

	}



}
