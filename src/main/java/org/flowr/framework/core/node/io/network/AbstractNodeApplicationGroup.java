
/**
 * 
 * Models Network group.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.AsynchronousChannelGroup;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;
import org.flowr.framework.core.node.ha.BackPressureExecutors;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler;
import org.flowr.framework.core.node.io.pipeline.NetworkBus;
import org.flowr.framework.core.node.io.pipeline.NetworkPipeline;

public abstract class AbstractNodeApplicationGroup implements NetworkGroup{

    private AtomicReference<NetworkPipeline> in  = new AtomicReference<>();
    private AtomicReference<NetworkPipeline> out = new AtomicReference<>();
    private String groupName;
    private AsynchronousChannelGroup asynchronousChannelGroup;
    
    public AbstractNodeApplicationGroup() throws IOException {
        
        asynchronousChannelGroup = AsynchronousChannelGroup.withThreadPool(
                                    BackPressureExecutors.newCachedBackPressureThreadPool());
        
    }
    
    public NetworkGroupStatus init(NetworkPipeline inPipeline,NetworkPipeline outPipeline,
            SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> pipelineHandlerSet) {
    
        this.in.set(inPipeline);
        this.out.set(outPipeline);
        
        return NetworkBus.getInstance().addNetworkGroup(this,pipelineHandlerSet);
    }
    
    public NetworkGroupStatus close(SimpleEntry<IntegrationPipelineHandler,
            IntegrationPipelineHandler> pipelineHandlerSet) {        
        
        this.in     = null;
        this.out    = null; 
        this.asynchronousChannelGroup.shutdown();
        return NetworkBus.getInstance().removeNetworkGroup(this,pipelineHandlerSet);
    }
    
    public SocketAddress getSocketAddress(String hostName, int portNumber) throws UnknownHostException {
        
        SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(hostName), portNumber);
        Logger.getRootLogger().info("NodeApplicationGroup : "+socketAddress);

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
        return in.get();
    }

    public NetworkPipeline getOut() {
        return out.get();
    }

    
    public String toString(){
        
        return "\n NodeApplicationGroup{"+
                " \n | groupName : "+groupName+  
                " \n | in : "+in.get().getPipelineName()+
                " \n | out : "+out.get().getPipelineName()+
                " \n | asynchronousChannelGroup : "+asynchronousChannelGroup+    
                "}\n";

    }

}
