package org.flowr.framework.core.node.io.pipeline;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.LinkedTransferQueue;

import org.flowr.framework.core.config.Configuration;
import org.flowr.framework.core.config.PipelineConfiguration;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.event.pipeline.Pipeline;
import org.flowr.framework.core.node.io.NetworkByteBuffer;
import org.flowr.framework.core.node.io.flow.IOFlow.IOFlowType;
import org.flowr.framework.core.node.io.flow.handler.NetworkBufferQueue;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class NetworkPipeline implements Pipeline<NetworkByteBuffer>{	
	
	private String hostName								= null;
	private int portNumber 								= Integer.MIN_VALUE;
	private SocketAddress socketAddress					= null;
	private IOFlowType ioFlowType						= null;	
	private PipelineType pipelineType 					= PipelineType.TRANSFER;
	private EventType eventType 						= null; 
	private PipelineFunctionType pipelineFunctionType	= null;
	private String pipelineName							= null;
	private PipelineConfiguration pipelineConfiguration	= Configuration.DefaultPipelineConfiguration();
	private static LinkedTransferQueue<NetworkByteBuffer> queue = new LinkedTransferQueue<NetworkByteBuffer>();

	public boolean add(NetworkByteBuffer e) {

		return queue.add(e);
	}
	
	@Override
	public void setPipelineType(PipelineType pipelineType) {
		this.pipelineType = pipelineType;
	}

	@Override
	public PipelineType getPipelineType() {
		return this.pipelineType;
	}
	
	@Override
	public String getPipelineName() {
		return pipelineName;
	}

	@Override
	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}
	
	public void onSeek(NetworkByteBuffer networkByteBuffer) {
		this.add(networkByteBuffer);
	}
	
	public void onCancel() {		
		queue.clear();
	}

	public Collection<NetworkByteBuffer> getBatch() throws InterruptedException{
		
		Collection<NetworkByteBuffer> coll = new ArrayList<NetworkByteBuffer>();
		
		synchronized(this){
			queue.drainTo(coll, this.pipelineConfiguration.getBatchSize());
		}
			
				
		return coll;
	}
	
	public Collection<NetworkByteBuffer> getAll() throws InterruptedException{
		
		Collection<NetworkByteBuffer> coll = new ArrayList<NetworkByteBuffer>();
		
		synchronized(this){
			queue.drainTo(coll,queue.size());
		}
			
				
		return coll;
	}
	
	public NetworkBufferQueue asNetworkBufferQueue() {
			
		NetworkBufferQueue networkBufferQueue = new NetworkBufferQueue();
		
		if(!queue.isEmpty()) {
			
			synchronized(this){
				queue.drainTo(networkBufferQueue.asBufferQueue(),queue.size());
			}
			
		}
		
		return networkBufferQueue;
	}
	
	public PipelineConfiguration getPipelineConfiguration() {
		return pipelineConfiguration;
	}

	public void setPipelineConfiguration(PipelineConfiguration pipelineConfiguration) {
		this.pipelineConfiguration = pipelineConfiguration;
	}

	@Override
	public void setPipelineFunctionType(PipelineFunctionType pipelineFunctionType) {
		this.pipelineFunctionType = pipelineFunctionType;
	}

	@Override
	public PipelineFunctionType getPipelineFunctionType() {
		return this.pipelineFunctionType;
	}
	
	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public IOFlowType getIOFlowType() {
		return ioFlowType;
	}

	public void setIOFlowType(IOFlowType ioFlowType) {
		this.ioFlowType = ioFlowType;
	}

	public SocketAddress getSocketAddress() {
		return socketAddress;
	}

	public void setSocketAddress(SocketAddress socketAddress) {
		this.socketAddress = socketAddress;
	}
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
	public LinkedTransferQueue<NetworkByteBuffer> getQueue() {
		return queue;
	}
	
	public String toString() {
		
		return "NetworkPipeline { "+
				" | "+hostName+" | "+portNumber+
				" | "+ioFlowType+" | "+socketAddress+
				" | "+pipelineName+" | "+pipelineType+
				" | "+eventType+" | "+pipelineFunctionType+
				" | "+queue.toString()+
				" | "+pipelineConfiguration+" } ";
	}
}
