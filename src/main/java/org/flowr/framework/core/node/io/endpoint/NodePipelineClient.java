
/**
 * Models 1:N relationship between local Server & remote/local Clients.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

package org.flowr.framework.core.node.io.endpoint;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.constants.Constant.NodeConstants;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.io.network.NetworkGroup;
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupStatus;
import org.flowr.framework.core.node.io.network.NetworkGroup.NodeGroup;
import org.flowr.framework.core.node.io.network.NodeChannel;

public class NodePipelineClient extends AbstractPipelineComponent implements Runnable{

    private NetworkGroup networkGroup;
    private boolean keepRunning;

    private SocketAddress   inSocketAddress;         
    private NetworkClientSocketChannel inSocketChannel;

    private SocketAddress   outSocketAddress; 
    private NetworkClientSocketChannel outSocketChannel;
        
    public NodePipelineClient(NodeEndPointConfiguration nodeEndPointConfiguration){         
        super(nodeEndPointConfiguration);       
    }
 
    
    @Override
    public void run() {
        
        while(keepRunning) {
            
            Logger.getRootLogger().info("\n NodePipelineClient  | < IN : "+
                    super.getIn().getQueue().size()+" : "+ inSocketAddress+" > | < OUT : "+
                    super.getOut().getQueue().size() +" : "+outSocketAddress+" >\n"); 
            
            try {
                Thread.sleep(NodeConstants.NODE_PIPELINE_CLIENT_SLEEP_INTERVAL);
            } catch (InterruptedException e) {
                Logger.getRootLogger().error(e);
                Thread.currentThread().interrupt();
            }
        }
    }
    
    @Override
    public NetworkGroupStatus init() throws ConfigurationException {
        
        NetworkGroupStatus status = NetworkGroupStatus.DISASSOCIATED;
        
        this.networkGroup           = NodeGroup.geInstance().createGroup(super.getNodeConfig());       

        this.networkGroup.init(
                super.getIn(),
                super.getOut(),
                super.getNodeEndPointConfiguration().getPipelineHandler()
        );   
        
        try {
        
            this.inSocketChannel    = new NetworkClientSocketChannel(this.networkGroup,
                                            ChannelFlowType.INBOUND,super.getServerPublisher());
            this.outSocketChannel   = new NetworkClientSocketChannel(this.networkGroup,
                                            ChannelFlowType.OUTBOUND,super.getServerPublisher());
    
            this.inSocketAddress    = getSocketAddress(ChannelFlowType.INBOUND);
            this.outSocketAddress   = getSocketAddress(ChannelFlowType.OUTBOUND);
        
            keepRunning             = true;
            
            status                  = NetworkGroupStatus.ASSOCIATED;
        
        } catch (IOException e) {
            
            Logger.getRootLogger().error(e);
            Thread.currentThread().interrupt();
            throw new ConfigurationException(ErrorMap.ERR_CONFIG, e.getMessage());
        }   
        
        Logger.getRootLogger().info("NodePipelineClient : networkGroupStatus : "+status);
        
        return status;
    }

    @Override
    public SocketAddress getSocketAddress(ChannelFlowType channelFlowType) {
        
        SocketAddress socketAddress = null;
        
        switch(channelFlowType) {
        
            case INBOUND:{
                
                socketAddress = inSocketChannel.getSocketAddress();
                break;
            }case OUTBOUND:{
                
                socketAddress = outSocketChannel.getSocketAddress();
                break;
            }default:{
                break;
            }
        }
        
        return socketAddress;
    }
    
    @Override
    public ByteBuffer readFromNetwork() {
        return this.inSocketChannel.readFromPipeline();
    }    
    
    @Override
    public void writeToNetwork() {
            
        this.outSocketChannel.writeToPipeline();    
    }
    
    @Override
    public ChannelStatus connect(SocketAddress serverSocketAddress,ChannelFlowType channelFlowType) 
        throws ConfigurationException {
                
        ChannelStatus channelStatus = ChannelStatus.DISCONNECTED;
        
        try {
        
            switch(channelFlowType) {
            
                case INBOUND:{
                
                    if(
                            this.inSocketChannel.connect(serverSocketAddress)   == ChannelStatus.CONNECTED 
                    ) {
                        channelStatus = ChannelStatus.CONNECTED;
                    }
                    Logger.getRootLogger().info("NodePipelineClient : connect : inServerSocketChannel : "+
                            inSocketChannel.getChannelStatus());
                    break;
                }case OUTBOUND:{
                    if(
                            this.outSocketChannel.connect(serverSocketAddress) == ChannelStatus.CONNECTED 
                    ) {
                        channelStatus = ChannelStatus.CONNECTED;
                    }
                    Logger.getRootLogger().info("NodePipelineClient : connect : outServerSocketChannel : "+
                            outSocketChannel.getChannelStatus());
        
                    break;
                }default:{
                    break;
                }
            }
        } catch (IOException e) {
            
            Logger.getRootLogger().error(e);
            Thread.currentThread().interrupt();
            throw new ConfigurationException(ErrorMap.ERR_CONFIG,e.getMessage());
        }      

        return channelStatus;       
        
    }
        
    
    @SuppressWarnings("unchecked")
    @Override
    public Set<SocketOption<?>> getDefaultSupportedOption(ChannelFlowType channelFlowType) {
        
        Set<SocketOption<?>> socketOption = null;
        
        switch(channelFlowType) {
        
            case INBOUND:{
            
                socketOption = this.inSocketChannel.getDefaultSupportedOption();
                break;
            }case OUTBOUND:{
                
                socketOption = this.outSocketChannel.getDefaultSupportedOption();
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
            
                NodeChannel.getNodeChannelSocketOption().configure((Channel) this.inSocketChannel, 
                    this.inSocketChannel.getDefaultSupportedOption());
                break;
            }case OUTBOUND:{
                
                NodeChannel.getNodeChannelSocketOption().configure((Channel) this.outSocketChannel, 
                        this.outSocketChannel.getDefaultSupportedOption());             
                break;
            }default:{
                break;
            }
        }
    }
    
    @Override
    public boolean equals(Object other){
        
        return super.equals(other);
    }
    
    @Override
    public int hashCode() {
        
        return super.hashCode();
    }
            
    @Override   
    public ChannelStatus getChannelStatus(ChannelFlowType channelFlowType) {

        ChannelStatus channelStatus = ChannelStatus.DEFAULT;
        
        switch(channelFlowType) {
        
            case INBOUND:{
                
                channelStatus = inSocketChannel.getChannelStatus();
                break;
            }case OUTBOUND:{
                
                channelStatus = outSocketChannel.getChannelStatus();
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
                
                channelState = inSocketChannel.getChannelState();
                break;
            }case OUTBOUND:{
                
                channelState = outSocketChannel.getChannelState();
                break;
            }default:{
                break;
            }
        }
        return channelState;
    }

    @Override
    public NetworkGroupStatus close()  {
        
        NetworkGroupStatus networkGroupStatus = NetworkGroupStatus.UNKNOWN;
        
        if(
                inSocketChannel.close()     == ChannelStatus.DISCONNECTED &&
                outSocketChannel.close()    == ChannelStatus.DISCONNECTED &&
                networkGroup.close(super.getNodeEndPointConfiguration().getPipelineHandler())  
                                            == NetworkGroupStatus.DISASSOCIATED
        ) {
            
            networkGroupStatus = NetworkGroupStatus.DISASSOCIATED;
        }
        
        this.setDeRegistrationTime(new Timestamp(Instant.now().toEpochMilli()));
        keepRunning   = false;
        return networkGroupStatus;
    }

    
    @Override
    public NetworkGroup getNetworkGroup() {
        return networkGroup;
    }
    
    @Override
    public String toString(){
        
        StringBuilder sb = new StringBuilder();
        sb.append("\n NodePipelineClient{");
        sb.append(" \n| networkGroup : "+networkGroup);    
        
        if(inSocketAddress != null) {
            sb.append(" \n| inSocketAddress : "+inSocketAddress.toString());
        }
        sb.append(" \n| inSocketChannel : "+inSocketChannel);
        
        if(outSocketAddress != null) {
            sb.append(" \n| outSocketAddress : "+outSocketAddress.toString());
        }
        sb.append(" \n| outSocketChannel : "+outSocketChannel);  
        sb.append(" \n| "+super.toString());  
        sb.append("\n}\n");
        
        return sb.toString();
    }
}
