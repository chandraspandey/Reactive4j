
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.app;

import java.nio.ByteBuffer;
import java.util.concurrent.Flow.Subscriber;

import org.apache.log4j.Logger;
import org.flowr.framework.core.node.io.NetworkByteBuffer;
import org.flowr.framework.core.node.io.endpoint.NodePipelineClient;
import org.flowr.framework.core.node.io.endpoint.NodePipelineServer;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelFlowType;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelStatus;

public abstract class AbstractNodePipelineManager extends AbstractNodeBusExecutor{
    
    private Subscriber<ProtocolPublishType> networkMetricSubscriber;  

    @Override
    public void setNetworkMetricSubscriber(Subscriber<ProtocolPublishType> networkMetricSubscriber){
        
        this.networkMetricSubscriber = networkMetricSubscriber;
    }
    
    @Override
    public Subscriber<ProtocolPublishType> getNetworkMetricSubscriber(){
        
        return this.networkMetricSubscriber;
    }
    
    @Override
    public String getNetworkMetricSubscriberName() {
        
        return this.getClass().getSimpleName();
    }    
      
    @Override
    public byte[] clientToServer(byte[] in,NodePipelineClient fromClient, NodePipelineServer toServer) {
        
        byte[] out = null;
        
        Logger.getRootLogger().info("NodePipelineManager : clienttoServer : readFromNetwork : "+
                toServer.getChannelStatus(ChannelFlowType.INBOUND) +" : "+
                fromClient.getChannelStatus(ChannelFlowType.OUTBOUND)
        );
        
        if(
                toServer.getChannelStatus(ChannelFlowType.INBOUND) == ChannelStatus.CONNECTED && 
                fromClient.getChannelStatus(ChannelFlowType.OUTBOUND) == ChannelStatus.CONNECTED 
        ) {
        
            
            fromClient.getNodeConfig().getOut().add(new NetworkByteBuffer(ByteBuffer.wrap(in)));
            fromClient.writeToNetwork();            
            ByteBuffer buffer = toServer.readFromNetwork();     
            buffer.flip();
            out = new byte[buffer.limit()];
            
            buffer.get(out);
        }

        return out;
    }
    
    @Override
    public byte[] serverToClient(byte[] in,NodePipelineServer fromServer,NodePipelineClient toClient ) {
        
        byte[] out = null;
        
        Logger.getRootLogger().info("NodePipelineManager : serverToClient : readFromNetwork : "+
                fromServer.getChannelStatus(ChannelFlowType.OUTBOUND) +" : "+
                toClient.getChannelStatus(ChannelFlowType.INBOUND)
        );          
        
        if(
                fromServer.getChannelStatus(ChannelFlowType.OUTBOUND) == ChannelStatus.CONNECTED && 
                toClient.getChannelStatus(ChannelFlowType.INBOUND) == ChannelStatus.CONNECTED 
        ) {
                        
            fromServer.getNodeConfig().getIn().add(new NetworkByteBuffer(ByteBuffer.wrap(in)));
            
            ByteBuffer buffer = toClient.readFromNetwork();         
            buffer.flip();
            out = new byte[buffer.limit()];
            
            buffer.get(out);            
            
        }
        
        return out;
    }

}
