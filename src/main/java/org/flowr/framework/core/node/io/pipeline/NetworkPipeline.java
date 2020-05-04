
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
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

public class NetworkPipeline implements Pipeline{   
    
    private PipelineType pipelineType                   = PipelineType.TRANSFER;
    private String hostName;
    private int portNumber;
    private SocketAddress socketAddress;
    private IOFlowType ioFlowType; 
    private EventType eventType; 
    private PipelineFunctionType pipelineFunctionType;
    private String pipelineName;
    private PipelineConfiguration pipelineConfiguration = Configuration.getDefaultPipelineConfiguration();
    private static LinkedTransferQueue<NetworkByteBuffer> queue = new LinkedTransferQueue<>();

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

    public Collection<NetworkByteBuffer> getBatch(){
        
        Collection<NetworkByteBuffer> coll = new ArrayList<>();
        
        synchronized(this){
            queue.drainTo(coll, this.pipelineConfiguration.getBatchSize());
        }
            
                
        return coll;
    }
    
    public Collection<NetworkByteBuffer> getAll(){
        
        Collection<NetworkByteBuffer> coll = new ArrayList<>();
        
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
