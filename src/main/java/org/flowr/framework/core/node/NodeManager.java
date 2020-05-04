
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.flowr.framework.core.config.NodeServiceConfiguration;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.io.endpoint.NodePipelineClient;
import org.flowr.framework.core.node.io.endpoint.NodePipelineServer;
import org.flowr.framework.core.node.io.flow.handler.IntegrationBridge;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessRequest;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessResponse;
import org.flowr.framework.core.node.io.flow.handler.MetricHandler;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric;
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupType;
import org.flowr.framework.core.node.io.pipeline.NetworkBus;
import org.flowr.framework.core.security.Identity.IdentityConfig;
import org.flowr.framework.core.service.Service.ServiceStatus;

public interface NodeManager extends MetricHandler{
    
    SimpleEntry<Thread, Thread> startInboundNetworkPipeline(NodePipelineServer toServer,
            NodePipelineClient fromClient) throws ConfigurationException;
    
    SimpleEntry<Thread, Thread> startOutboundNetworkPipeline(NodePipelineClient fromClient,NodePipelineServer toServer)
            throws ConfigurationException;
    
    byte[] clientToServer(byte[] in,NodePipelineClient fromClient, NodePipelineServer toServer);
    
    byte[] serverToClient(byte[] in,NodePipelineServer fromServer,NodePipelineClient toClient );
    
    SimpleEntry<NodePipelineServer, List<NodePipelineClient>> configure(
            SimpleEntry<NodePipelineConfig, NodePipelineConfig> serverClientConfigEntry) throws ConfigurationException;
    
    NetworkBus getNetworkBus();
    
    ServiceStatus startup() throws ConfigurationException;
    
    ServiceStatus shutdown() throws ConfigurationException;

    ServiceStatus initNetworkBusExecutor(Optional<Properties> configProperties) throws ConfigurationException;

    ServiceStatus closeNetworkBusExecutor(Optional<Properties> configProperties) throws ConfigurationException;
    
    public class NodeEndPointConfig{
        
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
        private NetworkPipelineConfig networkPipelineConfig;
        
        public NodeEndPointConfig(NetworkPipelineConfig networkPipelineConfig,
                String inboundPipelineHostName, int inboundPipelinePortNumber, 
                String outboundPipelineHostName, int outboundPipelinePortNumber,
                String channelName) {
            
            this.networkPipelineConfig      = networkPipelineConfig;
            this.inboundPipelineName        = networkPipelineConfig.inboundConfiguration.getNodePipelineName();
            this.outboundPipelineName       = networkPipelineConfig.outboundConfiguration.getNodePipelineName();
            this.pipelineMetricSubscriber   = networkPipelineConfig.getPipelineMetricSubscriber();
            this.networkGroupType           = networkPipelineConfig.getNetworkGroupType();
            this.reuseNetworkGroup          = networkPipelineConfig.isReuseNetworkGroup();
            this.channelName                = channelName;
            this.inboundPipelineHostName    = inboundPipelineHostName;
            this.inboundPipelinePortNumber  = inboundPipelinePortNumber;
            this.outboundPipelineHostName   = outboundPipelineHostName;
            this.outboundPipelinePortNumber = outboundPipelinePortNumber;
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
        
        public NetworkPipelineConfig getNetworkPipelineConfig() {
            return networkPipelineConfig;
        }

        @Override
        public String toString() {
            return "NodeEndPointConfig [inboundPipelineName=" + inboundPipelineName + ", inboundPipelineHostName="
                    + inboundPipelineHostName + ", inboundPipelinePortNumber=" + inboundPipelinePortNumber
                    + ", outboundPipelineName=" + outboundPipelineName + ", outboundPipelineHostName="
                    + outboundPipelineHostName + ", outboundPipelinePortNumber=" + outboundPipelinePortNumber
                    + ", pipelineMetricSubscriber=" + pipelineMetricSubscriber + ", channelName=" + channelName
                    + ", networkGroupType=" + networkGroupType + ", reuseNetworkGroup=" + reuseNetworkGroup + "]";
        }
        
    }
    
    public class NetworkPipelineConfig{
        
        private NodeServiceConfiguration inboundConfiguration;
        private NodeServiceConfiguration outboundConfiguration;
        private NetworkGroupType networkGroupType;
        private NetworkMetric pipelineMetricSubscriber;
        private boolean reuseNetworkGroup;
        
        public NetworkPipelineConfig() {
            
        }
        
        public NetworkPipelineConfig(NodeServiceConfiguration inboundConfiguration,
            NodeServiceConfiguration outboundConfiguration, NetworkGroupType networkGroupType,
            NetworkMetric pipelineMetricSubscriber, boolean reuseNetworkGroup) {
            
            this.inboundConfiguration       = inboundConfiguration;
            this.outboundConfiguration      = outboundConfiguration;
            this.networkGroupType           = networkGroupType;
            this.pipelineMetricSubscriber   = pipelineMetricSubscriber;
            this.reuseNetworkGroup          = reuseNetworkGroup;
        }

        public NodeServiceConfiguration getInboundConfiguration() {
            return inboundConfiguration;
        }

        public NodeServiceConfiguration getOutboundConfiguration() {
            return outboundConfiguration;
        }

        public NetworkGroupType getNetworkGroupType() {
            return networkGroupType;
        }

        public NetworkMetric getPipelineMetricSubscriber() {
            return pipelineMetricSubscriber;
        }

        public boolean isReuseNetworkGroup() {
            return reuseNetworkGroup;
        }
        
        public void setInboundConfiguration(NodeServiceConfiguration inboundConfiguration) {
            this.inboundConfiguration = inboundConfiguration;
        }

        public void setOutboundConfiguration(NodeServiceConfiguration outboundConfiguration) {
            this.outboundConfiguration = outboundConfiguration;
        }

        public void setNetworkGroupType(NetworkGroupType networkGroupType) {
            this.networkGroupType = networkGroupType;
        }

        public void setPipelineMetricSubscriber(NetworkMetric pipelineMetricSubscriber) {
            this.pipelineMetricSubscriber = pipelineMetricSubscriber;
        }

        public void setReuseNetworkGroup(boolean reuseNetworkGroup) {
            this.reuseNetworkGroup = reuseNetworkGroup;
        }   

        @Override
        public String toString() {
            return "NetworkPipelineConfig [inboundConfiguration=" + inboundConfiguration + ", outboundConfiguration="
                    + outboundConfiguration + ", outboundNetworkGroupType=" + networkGroupType
                    + ", pipelineMetricSubscriber=" + pipelineMetricSubscriber + ", reuseNetworkGroup="
                    + reuseNetworkGroup + "]";
        }

    
    }
    
    public class NetworkIntegrationConfig{
        
        private SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> pipelineHandler;
        private ProtocolPublishType protocolPublishType;
        private Optional<IntegrationProcessRequest> handleFor;
        private Optional<IntegrationProcessResponse> handleAs;
        
        public NetworkIntegrationConfig(
                SimpleEntry<IntegrationPipelineHandler, IntegrationPipelineHandler> pipelineHandler,
                ProtocolPublishType protocolPublishType, Optional<IntegrationProcessRequest> handleFor,
                Optional<IntegrationProcessResponse> handleAs) {
            
            this.pipelineHandler = pipelineHandler;
            this.protocolPublishType = protocolPublishType;
            this.handleFor = handleFor;
            this.handleAs = handleAs;
        }
        
        public NetworkIntegrationConfig( 
                SimpleEntry<IntegrationPipelineHandler, IntegrationPipelineHandler> pipelineHandler,
                ProtocolPublishType protocolPublishType) {
            
            this.pipelineHandler = pipelineHandler;
            this.protocolPublishType = protocolPublishType;
        }

        public Optional<IntegrationProcessRequest> getHandleFor() {
            return handleFor;
        }

        public void setHandleFor(Optional<IntegrationProcessRequest> handleFor) {
            this.handleFor = handleFor;
        }

        public Optional<IntegrationProcessResponse> getHandleAs() {
            return handleAs;
        }

        public void setHandleAs(Optional<IntegrationProcessResponse> handleAs) {
            this.handleAs = handleAs;
        }

        public SimpleEntry<IntegrationPipelineHandler, IntegrationPipelineHandler> getPipelineHandler() {
            return pipelineHandler;
        }

        public ProtocolPublishType getProtocolPublishType() {
            return protocolPublishType;
        }

        @Override
        public String toString() {
            return "NetworkIntegrationConfig [pipelineHandler=" + pipelineHandler + ", protocolPublishType="
                    + protocolPublishType + ", handleFor=" + handleFor + ", handleAs=" + handleAs + "]";
        }
    }
    
    public class NodePipelineConfig {

        private NodeServiceConfiguration inboundConfiguration; 
        private NodeServiceConfiguration outboundConfiguration;
        private IntegrationBridge inboundBridge;
        private IntegrationBridge outboundBridge;
        private Optional<SimpleEntry<Integer,Integer>> processMinMaxLimitOption = Optional.empty();
        private Optional<Properties> propertiesOption  = Optional.empty();  
        private IdentityConfig  identityConfig;
         
        public NodePipelineConfig(
                NodeServiceConfiguration inboundConfiguration,
                NodeServiceConfiguration outboundConfiguration, 
                IntegrationBridge inboundBridge,
                IntegrationBridge outboundBridge, 
                Optional<SimpleEntry<Integer, Integer>> processMinMaxLimitOption,
                Optional<Properties> propertiesOption,
                IdentityConfig  identityConfig
        ) {
            this.inboundConfiguration       = inboundConfiguration;
            this.outboundConfiguration      = outboundConfiguration;
            this.inboundBridge              = inboundBridge;
            this.outboundBridge             = outboundBridge;
            this.processMinMaxLimitOption   = processMinMaxLimitOption;
            this.propertiesOption           = propertiesOption;
            this.identityConfig             = identityConfig;
        }

        public NodeServiceConfiguration getInboundConfiguration() {
            return inboundConfiguration;
        }

        public NodeServiceConfiguration getOutboundConfiguration() {
            return outboundConfiguration;
        }

        public IntegrationBridge getInboundBridge() {
            return inboundBridge;
        }

        public IntegrationBridge getOutboundBridge() {
            return outboundBridge;
        }

        public Optional<SimpleEntry<Integer, Integer>> getProcessMinMaxLimitOption() {
            return processMinMaxLimitOption;
        }

        public Optional<Properties> getPropertiesOption() {
            return propertiesOption;
        }
        
        public IdentityConfig getIdentityConfig() {
            return identityConfig;
        }
        
        public String toString(){
            
            return "\n NodePipelineConfig{"+
                    "\n | inboundConfiguration  : "+inboundConfiguration.isValid()+ 
                    "\n | outboundConfiguration : "+outboundConfiguration.isValid()+   
                    "\n | inboundBridge         : "+inboundBridge+
                    "\n | outboundBridge        : "+outboundBridge+   
                    "\n | processMinMaxLimit    : "+processMinMaxLimitOption+ 
                    "\n | properties            : "+propertiesOption+ 
                    "\n | identityConfig        : "+identityConfig+ 
                    "\n}\n";
        }


    }

    
}
