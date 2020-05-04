
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io.endpoint;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.node.io.NetworkByteBuffer;
import org.flowr.framework.core.node.io.flow.metric.ChannelMetric;
import org.flowr.framework.core.node.io.flow.protocol.FlowSocketChannel;
import org.flowr.framework.core.node.io.flow.protocol.NetworkProcessor;
import org.flowr.framework.core.node.io.flow.protocol.NetworkSubscription;
import org.flowr.framework.core.node.io.flow.protocol.NetworkSubscriptionContext;
import org.flowr.framework.core.node.io.network.NetworkGroup;
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupRoleType;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelFlowType;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelState;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelStatus;
import org.flowr.framework.core.node.io.pipeline.NetworkPipeline;

public class NetworkClientSocketChannel implements FlowSocketChannel{

    private ChannelStatus channelStatus                                     = ChannelStatus.DEFAULT;
    private ChannelState channelState                                       = ChannelState.DEFAULT;
    private NetworkProcessor channelPublisher;
    private ChannelFlowType channelFlowType;
    private NetworkGroup networkGroup;
    private AsynchronousSocketChannel socketChannel;
    private SocketAddress socketAddress;
    private NetworkPipeline networkPipeline;

    
    public NetworkClientSocketChannel(NetworkGroup networkGroup,ChannelFlowType channelFlowType,
        NetworkProcessor channelPublisher) throws IOException {
        
        this.networkGroup       = networkGroup;
        this.channelFlowType    = channelFlowType;
        this.channelPublisher   = channelPublisher;
        
        this.socketChannel  = AsynchronousSocketChannel.open(networkGroup.getGroup());
        
        switch(channelFlowType) {
            
            case INBOUND:{
                
                this.networkPipeline = this.networkGroup.getIn();
                break;
            }case OUTBOUND:{
                
                this.networkPipeline = this.networkGroup.getOut();
                break;
            }default:{
                break;
            }
        }
        
        this.socketAddress      = networkGroup.getSocketAddress(
                                    this.networkPipeline.getHostName(),
                                    this.networkPipeline.getPortNumber());
        this.networkGroup.getIn().setSocketAddress(socketAddress);
        this.socketChannel.bind(socketAddress);
        
        channelStatus           = ChannelStatus.INITIALIZED;
    }
    
    @Override
    public ChannelStatus register() {
        
        networkGroup.registerSocketAddress(NetworkGroupRoleType.CLIENT,socketAddress);
        channelStatus = ChannelStatus.REGISTERED;
        return channelStatus;
    }
    
    @Override
    public ChannelStatus close() {
        
        networkGroup.deregisterSocketAddress(NetworkGroupRoleType.CLIENT,socketAddress);
        
        try {
            socketChannel.close();

        } catch (IOException e) {
           Logger.getRootLogger().error(e);
        }
        channelStatus = ChannelStatus.DISCONNECTED;
        
        return channelStatus;
    }
    
    @Override
    public ChannelStatus connect(SocketAddress serverSocketAddress) throws IOException {
                                
        socketChannel.connect(serverSocketAddress, channelPublisher,new CompletionHandler<Void,NetworkProcessor>() {
            
            @Override
            public void completed(Void arg0, NetworkProcessor channelProcessor) {
                
                register();
                channelStatus = ChannelStatus.CONNECTED;
                channelProcessor.onNext(ChannelMetric.connectSuccessMetric(networkPipeline));
            }

            @Override
            public void failed(Throwable arg0, NetworkProcessor channelProcessor) {
                close();            
                channelStatus = ChannelStatus.DISCONNECTED;
                channelProcessor.onNext(ChannelMetric.connectErrorMetric(networkPipeline));
            }
            
        });
        
        while(channelStatus == ChannelStatus.CONNECTED || channelStatus == ChannelStatus.DISCONNECTED) {
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Logger.getRootLogger().error(e);
                Thread.currentThread().interrupt();
            }
        }
        
        return channelStatus;
    }
    
    @Override
    public ByteBuffer readFromPipeline()  {
        
        Logger.getRootLogger().info(socketChannel);
        
        ByteBuffer buffer = ByteBuffer.allocateDirect(FrameworkConstants.FRAMEWORK_NETWORK_IO_BUFFER_BLOCK);    
                    
        channelState    = ChannelState.READ_INITIATED;
    
        socketChannel.read(buffer,  channelPublisher,new CompletionHandler<Integer,NetworkProcessor>() {

            @Override
            public void completed(Integer arg0, NetworkProcessor channelProcessor) {
                                
                networkPipeline.add(new NetworkByteBuffer(buffer));
                channelProcessor.onNext(ChannelMetric.readSuccessMetric(networkPipeline));
                buffer.flip();
            }

            @Override
            public void failed(Throwable throwable, NetworkProcessor channelProcessor) {
                channelProcessor.onError(throwable); 
                channelProcessor.onNext(ChannelMetric.readErrorMetric(networkPipeline));
            }
            
        });
        
        channelState    = ChannelState.READ_COMPLETED;
        
        return buffer;
    }
        
    @Override
    public ByteBuffer seek()  {
        
        Logger.getRootLogger().info(socketChannel);
        
        ByteBuffer buffer = ByteBuffer.allocateDirect(FrameworkConstants.FRAMEWORK_NETWORK_IO_BUFFER_UNIT); 
                    
        channelState    = ChannelState.READ_INITIATED;
    
        Optional<NetworkSubscriptionContext> contextOptional =
                ((NetworkSubscription)this.channelPublisher.getSubscription()).getOptionalSubscriptionContext();
        
        if( contextOptional.isPresent()) {
        
            NetworkSubscriptionContext networkSubscriptionContext = contextOptional.get();
            
            socketChannel.read(
                buffer, 
                networkSubscriptionContext.getSeekTimeout(), 
                networkSubscriptionContext.getTimeUnit(), 
                channelPublisher, 
                new CompletionHandler<Integer,NetworkProcessor>() {
    
                    @Override
                    public void completed(Integer arg0, NetworkProcessor channelProcessor) {
                                        
                        networkPipeline.onSeek(new NetworkByteBuffer(buffer));
                        channelProcessor.onSeek(ChannelMetric.seekMetric(networkPipeline));
                        buffer.flip();
                    }
    
                    @Override
                    public void failed(Throwable throwable, NetworkProcessor channelProcessor) {
                        channelProcessor.onError(throwable); 
                        channelProcessor.onSeek(ChannelMetric.seekErrorMetric(networkPipeline));
                    }
                    
                });
                    
            
            channelState    = ChannelState.READ_COMPLETED;
            channelStatus   = ChannelStatus.SEEK;
        }
        
        return buffer;
    }
    
    @Override
    public void cancel()  {
        
        this.networkPipeline.onCancel();
        this.channelPublisher.onCancel(ChannelMetric.cancelMetric(networkPipeline));        
        
        channelState    = ChannelState.DEFAULT;     
        channelStatus   = ChannelStatus.CANCEL;
        
    }
    
    @Override
    public void writeToPipeline() {
        
        if(!networkPipeline.getQueue().isEmpty()) {
            
            channelState    = ChannelState.WRITE_INITIATED;
        
            networkPipeline.getQueue().forEach( 
                    
                q -> 
                
                    socketChannel.write(q.getByteBuffer(), channelPublisher,
                            new CompletionHandler<Integer,NetworkProcessor>() {
            
                        @Override
                        public void completed(Integer arg0, NetworkProcessor channelProcessor) {
                            channelProcessor.onNext(ChannelMetric.writeSuccessMetric(networkPipeline));
                        }
            
                        @Override
                        public void failed(Throwable throwable, NetworkProcessor channelProcessor) {
                            channelProcessor.onError(throwable);
                            channelProcessor.onNext(ChannelMetric.writeErrorMetric(networkPipeline));
                        }
                        
                    })
                
            );  
            
            channelState    = ChannelState.WRITE_COMPLETED;         
        }   
    
    }

    @Override
    public ChannelFlowType getChannelFlowType() {
        return channelFlowType;
    }

    @Override
    public NetworkGroup getNetworkGroup() {
        return networkGroup;
    }

    @Override
    public AsynchronousSocketChannel getSocketChannel() {
        return socketChannel;
    }

    @Override
    public SocketAddress getSocketAddress() {
        return socketAddress;
    }
    
    @Override
    public ChannelStatus getChannelStatus() {
        return channelStatus;
    }

    @Override
    public void setChannelStatus(ChannelStatus channelStatus) {
        this.channelStatus = channelStatus;
    }

    @Override
    public ChannelState getChannelState() {
        return channelState;
    }

    @Override
    public void setChannelState(ChannelState channelState) {
        this.channelState = channelState;
    }
    
    public Set<SocketOption<?>> getDefaultSupportedOption() {
        return socketChannel.supportedOptions();
    }
    
    public String toString() {
        
        return "NetworkServerSocketChannel { "
                + channelFlowType+" | "+networkGroup.getGroupName()+" | "+
                " | "+channelStatus+" | "+channelStatus+" | "+              
                " | "+socketChannel+" | "+
                " | "+socketAddress+" } ";
    }

 


}
