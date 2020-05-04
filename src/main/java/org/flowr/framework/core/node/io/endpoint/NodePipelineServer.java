
/**
 * Models 1:N relationship between local Server & remote/local Clients.
 * @author Chandra Shekhar Pandey
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
import org.flowr.framework.core.constants.Constant.NodeConstants;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.io.network.NetworkGroup;
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupStatus;
import org.flowr.framework.core.node.io.network.NetworkGroup.NodeGroup;
import org.flowr.framework.core.node.io.network.NodeChannel;


public class NodePipelineServer extends AbstractPipelineComponent implements Runnable{

    private NetworkGroup networkGroup;
    private boolean keepRunning;

    private SocketAddress   inSocketAddress; 
    private NetworkServerSocketChannel inServerSocketChannel; 
    
    private SocketAddress   outSocketAddress; 
    private NetworkServerSocketChannel outServerSocketChannel;
   
    
    public NodePipelineServer(NodeEndPointConfiguration nodeEndPointConfiguration) {
        
        super(nodeEndPointConfiguration);
    }
    
    @Override
    public void run() {
        
        while(keepRunning) {
            
            Logger.getRootLogger().info("\n NodePipelineServer  | < IN : "+
                    super.getIn().getQueue().size()+" : "+
                    inSocketAddress+" > | < OUT : "+super.getOut().getQueue().size()+" : "+outSocketAddress+" >\n");
            
            try {
                Thread.sleep(NodeConstants.NODE_PIPELINE_SERVER_SLEEP_INTERVAL);
            } catch (InterruptedException e) {
                Logger.getRootLogger().error(e);
                Thread.currentThread().interrupt();
            }
        }
    }
        
    @Override
    public SocketAddress getSocketAddress(ChannelFlowType channelFlowType) {
        
        SocketAddress socketAddress = null;
        
        switch(channelFlowType) {
        
            case INBOUND:{
                
                socketAddress = inServerSocketChannel.getSocketAddress();
                break;
            }case OUTBOUND:{
                
                socketAddress = outServerSocketChannel.getSocketAddress();
                break;
            }default:{
                break;
            }
        }
        
        return socketAddress;
    }
    
    @Override
    public NetworkGroupStatus init() throws ConfigurationException {
        
        NetworkGroupStatus status = NetworkGroupStatus.DISASSOCIATED;
        
        try{
        
            this.networkGroup           = NodeGroup.geInstance().createGroup(super.getNodeConfig());
            this.networkGroup.init(
                    super.getIn(),
                    super.getOut(),
                    super.getNodeEndPointConfiguration().getPipelineHandler()
            );
            
            this.inServerSocketChannel   = new NetworkServerSocketChannel(this.networkGroup,
                                            ChannelFlowType.INBOUND,super.getServerPublisher());
            this.outServerSocketChannel  = new NetworkServerSocketChannel(this.networkGroup,
                                            ChannelFlowType.OUTBOUND,super.getServerPublisher());          
            keepRunning                  = true;
            
            this.inSocketAddress  = getSocketAddress(ChannelFlowType.INBOUND);
            this.outSocketAddress = getSocketAddress(ChannelFlowType.OUTBOUND);    
            
            status = NetworkGroupStatus.ASSOCIATED;
        
        } catch (IOException e) {
            
            Logger.getRootLogger().error(e);
            Thread.currentThread().interrupt();
            throw new ConfigurationException(ErrorMap.ERR_CONFIG,e);
        }   
        
        Logger.getRootLogger().info("NodePipelineServer : SERVER : networkGroupStatus : "+status);
        
        return status;
    }
    
    @Override
    public ByteBuffer readFromNetwork()  {
                
        return this.inServerSocketChannel.readFromPipeline();
    }
    
    @Override
    public void writeToNetwork() {
        
        this.outServerSocketChannel.writeToPipeline();  
    }
        
    @Override
    public ChannelStatus connect(SocketAddress serverSocketAddress,ChannelFlowType channelFlowType) 
            throws ConfigurationException {
        
        ChannelStatus serverChannelStatus = ChannelStatus.DISCONNECTED;
        
        try{
        
            switch(channelFlowType) {
            
                case INBOUND:{
                
                    if(
                            this.inServerSocketChannel.connect(serverSocketAddress) == ChannelStatus.CONNECTED 
                    ) {
                        serverChannelStatus = ChannelStatus.CONNECTED;
                    }
                    Logger.getRootLogger().info("NodePipelineServer : connect : inServerSocketChannel : "+
                    inServerSocketChannel.getChannelStatus());
                    break;
                }case OUTBOUND:{
                    if(
                            this.outServerSocketChannel.connect(serverSocketAddress) == ChannelStatus.CONNECTED 
                    ) {
                        serverChannelStatus = ChannelStatus.CONNECTED;
                    }
                    Logger.getRootLogger().info("NodePipelineServer : connect : outServerSocketChannel : "+
                    outServerSocketChannel.getChannelStatus());
                    break;
                }default:{
                    break;
                }
            }     
            
        } catch (IOException e) {
            
            Logger.getRootLogger().error(e);
            Thread.currentThread().interrupt();
            throw new ConfigurationException(ErrorMap.ERR_CONFIG,e);
        }       

        return serverChannelStatus;
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
    public Set<SocketOption<?>> getDefaultSupportedOption(ChannelFlowType channelFlowType) {
        
        Set<SocketOption<?>> socketOption = null;
        
        switch(channelFlowType) {
        
            case INBOUND:{
            
                socketOption = this.inServerSocketChannel.getDefaultSupportedOption();
                break;
            }case OUTBOUND:{
                
                socketOption = this.outServerSocketChannel.getDefaultSupportedOption();
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
            
                NodeChannel.getNodeChannelSocketOption().configure((Channel) this.inServerSocketChannel, 
                    this.inServerSocketChannel.getDefaultSupportedOption());
                break;
            }case OUTBOUND:{
                
                NodeChannel.getNodeChannelSocketOption().configure((Channel) this.outServerSocketChannel, 
                        this.outServerSocketChannel.getDefaultSupportedOption());               
                break;
            }default:{
                break;
            }
        }
    }
    
    @Override
    public ChannelStatus getChannelStatus(ChannelFlowType channelFlowType) {
        
        ChannelStatus channelStatus = ChannelStatus.DEFAULT;
        
        switch(channelFlowType) {
        
            case INBOUND:{
                
                channelStatus = inServerSocketChannel.getChannelStatus();
                break;
            }case OUTBOUND:{
                
                channelStatus = outServerSocketChannel.getChannelStatus();
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
                
                channelState = inServerSocketChannel.getChannelState();
                break;
            }case OUTBOUND:{
                
                channelState = outServerSocketChannel.getChannelState();
                break;
            }default:{
                break;
            }
        }
        return channelState;
    }
    
    @Override
    public NetworkGroupStatus close() {
        
        NetworkGroupStatus networkGroupStatus = NetworkGroupStatus.UNKNOWN;
        
        if(
                inServerSocketChannel.close()   == ChannelStatus.DISCONNECTED &&
                outServerSocketChannel.close()  == ChannelStatus.DISCONNECTED &&
                networkGroup.close( super.getNodeEndPointConfiguration().getPipelineHandler())      
                    == NetworkGroupStatus.DISASSOCIATED
        ) {
            
            networkGroupStatus = NetworkGroupStatus.DISASSOCIATED;
        }       
        
        keepRunning   = false;
        this.setDeRegistrationTime(new Timestamp(Instant.now().toEpochMilli()));
        return networkGroupStatus;
    }

    @Override
    public NetworkGroup getNetworkGroup() {
        return networkGroup;
    }
    
    @Override
    public String toString(){
        
        StringBuilder sb = new StringBuilder();
        sb.append("\n NodePipelineServer{");
        sb.append(" \n| networkGroup : "+networkGroup);    
        
        if(inSocketAddress != null) {
            sb.append(" \n| inSocketAddress : "+inSocketAddress.toString());
        }
        sb.append(" \n| inServerSocketChannel : "+inServerSocketChannel);
        
        if(outSocketAddress != null) {
            sb.append(" \n| outSocketAddress : "+outSocketAddress.toString());
        }
        sb.append(" \n| outServerSocketChannel : "+outServerSocketChannel);  
        sb.append(" \n| "+super.toString());  
        sb.append("\n}\n");
        
        return sb.toString();
    }
}
