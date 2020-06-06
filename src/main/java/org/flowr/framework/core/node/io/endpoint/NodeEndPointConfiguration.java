
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.node.io.endpoint;

import java.util.AbstractMap.SimpleEntry;
import java.util.Optional;

import org.flowr.framework.core.node.NodeManager.NetworkIntegrationConfig;
import org.flowr.framework.core.node.NodeManager.NodeEndPointConfig;
import org.flowr.framework.core.node.NodeManager.ProtocolConfig;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessRequest;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessResponse;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.ProtocolPublishType;
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupType;

public class NodeEndPointConfiguration {

    private ProtocolConfig  protocolConfig;
    private String inboundPipelineName; 
    private String inboundPipelineHostName; 
    private int inboundPipelinePortNumber;
    private String outboundPipelineName; 
    private String outboundPipelineHostName; 
    private int outboundPipelinePortNumber;
    private NetworkMetric pipelineMetricSubscriber; 
    private String channelName;
    private NetworkGroupType networkGroupType;
    private boolean reuseNetworkGroup;
    private SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> pipelineHandler;
    private Optional<IntegrationProcessRequest> handleFor;
    private Optional<IntegrationProcessResponse> handleAs;
    private ProtocolPublishType protocolPublishType;
    
    public NodeEndPointConfiguration( NodeEndPointConfig nodeEndPointConfig, 
            NetworkIntegrationConfig networkIntegrationConfig
    ) {

        this.protocolConfig             = nodeEndPointConfig.getNetworkPipelineConfig().getProtocolConfig();
        this.inboundPipelineName        = nodeEndPointConfig.getInboundPipelineName();
        this.inboundPipelineHostName    = nodeEndPointConfig.getInboundPipelineHostName();
        this.inboundPipelinePortNumber  = nodeEndPointConfig.getInboundPipelinePortNumber();
        this.outboundPipelineName       = nodeEndPointConfig.getOutboundPipelineName();
        this.outboundPipelineHostName   = nodeEndPointConfig.getOutboundPipelineHostName();
        this.outboundPipelinePortNumber = nodeEndPointConfig.getOutboundPipelinePortNumber();
        this.pipelineMetricSubscriber   = nodeEndPointConfig.getPipelineMetricSubscriber();
        this.channelName                = nodeEndPointConfig.getChannelName();
        this.networkGroupType           = nodeEndPointConfig.getNetworkGroupType();
        this.reuseNetworkGroup          = nodeEndPointConfig.isReuseNetworkGroup();
        this.pipelineHandler            = networkIntegrationConfig.getPipelineHandler();
        this.protocolPublishType        = networkIntegrationConfig.getProtocolPublishType();
        this.handleFor                  = networkIntegrationConfig.getHandleFor();
        this.handleAs                   = networkIntegrationConfig.getHandleAs();
    }
    
    public String getInboundPipelineName() {
        return inboundPipelineName;
    }

    public String getInboundPipelineHostName() {
        return inboundPipelineHostName;
    }

    public int getInboundPipelinePortNumber() {
        return inboundPipelinePortNumber;
    }

    public String getOutboundPipelineName() {
        return outboundPipelineName;
    }

    public String getOutboundPipelineHostName() {
        return outboundPipelineHostName;
    }

    public int getOutboundPipelinePortNumber() {
        return outboundPipelinePortNumber;
    }

    public NetworkMetric getPipelineMetricSubscriber() {
        return pipelineMetricSubscriber;
    }

    public String getChannelName() {
        return channelName;
    }

    public NetworkGroupType getNetworkGroupType() {
        return networkGroupType;
    }

    public boolean isReuseNetworkGroup() {
        return reuseNetworkGroup;
    }
    
    public Optional<IntegrationProcessRequest> getHandleFor() {
        return handleFor;
    }

    public Optional<IntegrationProcessResponse> getHandleAs() {
        return handleAs;
    }

    public ProtocolPublishType getProtocolPublishType() {
        return protocolPublishType;
    }
    
    public SimpleEntry<IntegrationPipelineHandler, IntegrationPipelineHandler> getPipelineHandler() {
        return pipelineHandler;
    }
    
    public ProtocolConfig getProtocolConfig() {
        return protocolConfig;
    }

    public String toString(){
        
        return "\n NodeEndPointConfiguration{"+
                " networkGroupType : "+networkGroupType+    
                " | reuseNetworkGroup : "+reuseNetworkGroup+    
                " | channelName : "+channelName+    
                " | inboundPipelineName : "+inboundPipelineName+
                " | inboundPipelineHostName : "+inboundPipelineHostName+
                " | inboundPipelinePortNumber : "+inboundPipelinePortNumber+
                " | outboundPipelineName : "+outboundPipelineName+
                " | outboundPipelineHostName : "+outboundPipelineHostName+
                " | outboundPipelinePortNumber : "+outboundPipelinePortNumber+
                " |\n handleFor : "+handleFor+
                " |\n handleAs : "+handleAs+                
                " |\n pipelineMetricSubscriber : "+pipelineMetricSubscriber+
                " |\n pipelineHandler : "+pipelineHandler+
                " |\n protocolConfig : "+protocolConfig+
                "\n}";

    }



    
}
