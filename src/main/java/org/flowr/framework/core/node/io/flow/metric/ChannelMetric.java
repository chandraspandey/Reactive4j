
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io.flow.metric;

import org.flowr.framework.core.node.io.network.NodeChannel.ChannelFlowType;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelProtocol;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelState;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelStatus;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelType;
import org.flowr.framework.core.node.io.network.NodeChannelLifeCycle.ChannelLifeCycleType;
import org.flowr.framework.core.node.io.pipeline.NetworkPipeline;

public class ChannelMetric {

    private NetworkPipeline networkPipeline;
    private ChannelLifeCycleType channelLifeCycleType;
    private ChannelFlowType channelFlowType;
    private ChannelType channelType;
    private ChannelState channelState;
    private ChannelStatus channelStatus;
    private ChannelProtocol channelProtocol;
    
    public ChannelMetric(
        NetworkPipeline networkPipeline,
        ChannelLifeCycleType channelLifeCycleType,
        ChannelFlowType channelFlowType,
        ChannelType channelType,
        ChannelState channelState,
        ChannelStatus channelStatus,
        ChannelProtocol channelProtocol
    ) {
        this.networkPipeline        = networkPipeline;
        this.channelLifeCycleType   = channelLifeCycleType;     
        this.channelFlowType        = channelFlowType;
        this.channelType            = channelType;
        this.channelState           = channelState;
        this.channelStatus          = channelStatus;
        this.channelProtocol        = channelProtocol;
    }
    
    
    public static ChannelMetric connectSuccessMetric(NetworkPipeline networkPipeline) {
        return new ChannelMetric(
                networkPipeline,
                ChannelLifeCycleType.CONNECT,
                ChannelFlowType.INBOUND,
                ChannelType.PIPE,
                ChannelState.DEFAULT,
                ChannelStatus.CONNECTED,
                ChannelProtocol.END
            );
    }
    
    public static ChannelMetric connectErrorMetric(NetworkPipeline networkPipeline) {
        return new ChannelMetric(
                networkPipeline,
                ChannelLifeCycleType.ERROR,
                ChannelFlowType.INBOUND,
                ChannelType.PIPE,
                ChannelState.DEFAULT,
                ChannelStatus.DISCONNECTED,
                ChannelProtocol.END
            );
    }
    
    public static ChannelMetric seekMetric(NetworkPipeline networkPipeline) {
        return new ChannelMetric(
                networkPipeline,
                ChannelLifeCycleType.SEEK,
                ChannelFlowType.INTERRUPT,
                ChannelType.PIPE,
                ChannelState.READ_INITIATED,
                ChannelStatus.SEEK,
                ChannelProtocol.END
            );
    }
    
    public static ChannelMetric seekErrorMetric(NetworkPipeline networkPipeline) {
        return new ChannelMetric(
                networkPipeline,
                ChannelLifeCycleType.SEEK,
                ChannelFlowType.INTERRUPT,
                ChannelType.PIPE,
                ChannelState.READ_ERROR,
                ChannelStatus.SEEK,
                ChannelProtocol.END
            );
    }
    
    public static ChannelMetric cancelMetric(NetworkPipeline networkPipeline) {
        return new ChannelMetric(
                networkPipeline,
                ChannelLifeCycleType.CANCEL,
                ChannelFlowType.INTERRUPT,
                ChannelType.PIPE,
                ChannelState.READ_COMPLETED,
                ChannelStatus.CANCEL,
                ChannelProtocol.END
            );
    }
    
    public static ChannelMetric readInitiatedMetric(NetworkPipeline networkPipeline) {
        return new ChannelMetric(
                networkPipeline,
                ChannelLifeCycleType.READ,
                ChannelFlowType.INBOUND,
                ChannelType.PIPE,
                ChannelState.READ_INITIATED,
                ChannelStatus.READ,
                ChannelProtocol.END
            );
    }
    
    public static ChannelMetric readSuccessMetric(NetworkPipeline networkPipeline) {
        return new ChannelMetric(
                networkPipeline,
                ChannelLifeCycleType.READ,
                ChannelFlowType.INBOUND,
                ChannelType.PIPE,
                ChannelState.READ_COMPLETED,
                ChannelStatus.READ,
                ChannelProtocol.END
            );
    }
    
    public static ChannelMetric readErrorMetric(NetworkPipeline networkPipeline) {
        return new ChannelMetric(
                networkPipeline,
                ChannelLifeCycleType.ERROR,
                ChannelFlowType.INBOUND,
                ChannelType.PIPE,
                ChannelState.READ_ERROR,
                ChannelStatus.ERROR,
                ChannelProtocol.END
            );
    }
    
    public static ChannelMetric writeInitiatedMetric(NetworkPipeline networkPipeline) {
        return new ChannelMetric(
                networkPipeline,
                ChannelLifeCycleType.CONNECT,
                ChannelFlowType.INBOUND,
                ChannelType.PIPE,
                ChannelState.WRITE_INITIATED,
                ChannelStatus.READ,
                ChannelProtocol.END
            );
    }
    
    public static ChannelMetric writeSuccessMetric(NetworkPipeline networkPipeline) {
        return new ChannelMetric(
                networkPipeline,
                ChannelLifeCycleType.CONNECT,
                ChannelFlowType.INBOUND,
                ChannelType.PIPE,
                ChannelState.WRITE_COMPLETED,
                ChannelStatus.READ,
                ChannelProtocol.END
            );
    }
    
    public static ChannelMetric writeErrorMetric(NetworkPipeline networkPipeline) {
        return new ChannelMetric(
                networkPipeline,
                ChannelLifeCycleType.WRITE,
                ChannelFlowType.INBOUND,
                ChannelType.PIPE,
                ChannelState.WRITE_ERROR,
                ChannelStatus.ERROR,
                ChannelProtocol.END
            );
    }

    public NetworkPipeline getNetworkPipeline() {
        return networkPipeline;
    }

    public ChannelLifeCycleType getChannelLifeCycleType() {
        return channelLifeCycleType;
    }

    public ChannelFlowType getChannelFlowType() {
        return channelFlowType;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public ChannelState getChannelState() {
        return channelState;
    }

    public ChannelStatus getChannelStatus() {
        return channelStatus;
    }

    public ChannelProtocol getChannelProtocol() {
        return channelProtocol;
    }   
    
    public String toString() {
        
        return "ChannelMetric { "+ 
                networkPipeline.getPipelineName()+" | "+
                channelLifeCycleType+" | "+
                channelFlowType+" | "+
                channelType+" | "+
                channelStatus+" | "+                
                channelState+" | "+
                channelProtocol+" } ";
    }
}
