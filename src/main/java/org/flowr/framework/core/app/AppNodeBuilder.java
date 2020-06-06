
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.app;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TransferQueue;

import org.apache.log4j.Logger;
import org.flowr.framework.api.Builder;
import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.config.NodeServiceConfiguration;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.node.NodeManager.NetworkIntegrationConfig;
import org.flowr.framework.core.node.NodeManager.NetworkPipelineConfig;
import org.flowr.framework.core.node.NodeManager.NodeEndPointConfig;
import org.flowr.framework.core.node.NodeManager.NodePipelineConfig;
import org.flowr.framework.core.node.NodeManager.ProtocolConfig;
import org.flowr.framework.core.node.io.endpoint.NodeEndPointConfiguration;
import org.flowr.framework.core.node.io.endpoint.NodePipelineClient;
import org.flowr.framework.core.node.io.endpoint.NodePipelineClientEndPoint;
import org.flowr.framework.core.node.io.endpoint.NodePipelineServer;
import org.flowr.framework.core.node.io.endpoint.NodePipelineServerEndPoint;
import org.flowr.framework.core.node.io.flow.IOFlow.IOFlowType;
import org.flowr.framework.core.node.io.flow.handler.IntegrationBridge;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler.HandlerType;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessRequest;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessResponse;
import org.flowr.framework.core.node.io.flow.metric.MetricContext;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.MetricType;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.ProtocolPublishType;
import org.flowr.framework.core.node.io.flow.protocol.NetworkSubscriptionContext;
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupType;
import org.flowr.framework.core.security.Identity.IdentityConfig;

public interface AppNodeBuilder extends Builder{
    
    public class IntegrationPipelineBuilder implements Builder{
        
        private String handlerName      = ConfigurationType.SERVER.name();
        private IntegrationPipelineHandler handler;
        private IntegrationBridge bridge;        
        private HandlerType handlerType;
        private IOFlowType ioFlowType;
        
        public IntegrationPipelineBuilder withNameAs(String handlerName) {
            
            this.handlerName = handlerName;
            return this;
        }
        
        public IntegrationPipelineBuilder withIntegrationPipelineHandlerAs(IntegrationPipelineHandler handler) {
            
            this.handler = handler;
            return this;
        }
        
        public IntegrationPipelineBuilder withIntegrationBridgeAs(IntegrationBridge bridge) {
            
            this.bridge = bridge;
            return this;
        }
        
        public IntegrationPipelineBuilder withHandlerTypeAs(HandlerType handlerType) {
            
            this.handlerType = handlerType;
            return this;
        }
        
        public IntegrationPipelineBuilder withIOFlowTypeAs(IOFlowType ioFlowType) {
            
            this.ioFlowType = ioFlowType;
            return this;
        }
        
        public IntegrationPipelineHandler build() throws ConfigurationException {
            
            if(bridge != null) {
            
                if(handler == null) {
                    handler =  new IntegrationPipelineHandler.DefaultIntegrationPipelineHandler(
                                handlerName, 
                                handlerType,
                                ioFlowType, 
                                bridge
                            );
                }
            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create IntegrationPipelineHandler with inputs as : ");
                sb.append("\n handlerName : "+handlerName);
                sb.append("\n handlerType : "+handlerType);
                sb.append("\n ioFlowType  : "+ioFlowType);
                sb.append("\n bridge      : "+bridge);
                
                Logger.getRootLogger().error(sb.toString());
                
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for IntegrationPipelineBuilder. "
                );
            }
            
            return handler;
        }
    }
    
    
    public class IntegrationProcessRequestBuilder implements Builder{
        
        private IntegrationProcessRequest requestHandle;
        private MetricType metricType;
        private Optional<SimpleEntry<Integer,Integer>> processMinMaxLimitOption = Optional.empty();
        private Optional<Properties> incomingPropertiesOption  = Optional.empty();  
        
        public IntegrationProcessRequestBuilder withIntegrationProcessRequestAs(IntegrationProcessRequest 
            requestHandle) {
            
            this.requestHandle = requestHandle;
            return this;
        }

        public IntegrationProcessRequestBuilder withMetricTypeAs(MetricType metricType) {
            
            this.metricType = metricType;
            return this;
        }
        
        public IntegrationProcessRequestBuilder withPropertiesAs(Optional<Properties> incomingPropertiesOption) {
            
            this.incomingPropertiesOption = incomingPropertiesOption;
            return this;
        }
        
        public IntegrationProcessRequestBuilder withMinMaxLimitAs(Optional<SimpleEntry<Integer,Integer>> 
            processMinMaxLimitOption) {
                
            this.processMinMaxLimitOption = processMinMaxLimitOption;
            return this;
        }
        
        public Optional<IntegrationProcessRequest> build() throws ConfigurationException {
            
            Optional<IntegrationProcessRequest> handlerOption;
            
            if(requestHandle != null ) {
            
                if(incomingPropertiesOption.isPresent()) {
                    requestHandle.setProperties(incomingPropertiesOption.get()); 
                }
                
                if(processMinMaxLimitOption.isPresent()) {
                    requestHandle.setResponseMinMaxLimit(processMinMaxLimitOption);
                }
                
                if(metricType != null) {
                    requestHandle.addMetricType(metricType);
                }
                
                handlerOption = Optional.of(requestHandle);
            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create IntegrationProcessRequest with inputs as : ");
                sb.append("\n requestHandle             : "+requestHandle);
                sb.append("\n incomingPropertiesOption  : "+incomingPropertiesOption);
                sb.append("\n processMinMaxLimitOption  : "+processMinMaxLimitOption);
                sb.append("\n metricType                : "+metricType);
                
                Logger.getRootLogger().error(sb.toString());
                
                throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for IntegrationProcessRequestBuilder."
                );
            }
            
            return handlerOption;
        }
    
    }
    
    public class IntegrationProcessResponseBuilder implements Builder{
        
        private IntegrationProcessResponse responseHandleAs;
        private Optional<TransferQueue<MetricContext>>  metricQueue = Optional.empty();
        private Optional<TransferQueue<NetworkSubscriptionContext>> subscriptionQueue = Optional.empty();
        private MetricType metricType;
        private Properties outgoingProperties  = new Properties();    
        
        public IntegrationProcessResponseBuilder withIntegrationProcessResponseAs(IntegrationProcessResponse 
                responseHandleAs) {
            
            this.responseHandleAs = responseHandleAs;
            return this;
        }

        public IntegrationProcessResponseBuilder withMetricTypeAs(MetricType metricType) {
            
            this.metricType = metricType;
            return this;
        }
        
        public IntegrationProcessResponseBuilder withMetricQueueAs(Optional<TransferQueue<MetricContext>>  
            metricQueue) {
                
            this.metricQueue = metricQueue;
            return this;
        }
        
        public IntegrationProcessResponseBuilder withSubscriptionQueueAs(
                Optional<TransferQueue<NetworkSubscriptionContext>> subscriptionQueue) {
            
            this.subscriptionQueue = subscriptionQueue;
            return this;
        }
         
        public Optional<IntegrationProcessResponse> build() throws ConfigurationException {
            
            Optional<IntegrationProcessResponse> handlerOption;
            
            if(responseHandleAs != null ) {
            
                responseHandleAs.setProperties(outgoingProperties);                 
                
                if(metricType != null) {
                    responseHandleAs.setMetricType(metricType);
                }
                
                if(metricQueue.isPresent()) {
                    responseHandleAs.setMetricQueue(metricQueue);
                }
                
                if(subscriptionQueue.isPresent()) {
                    responseHandleAs.setSubscriptionQueue(subscriptionQueue);
                }
                
                handlerOption = Optional.of(responseHandleAs);
            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create IntegrationProcessResponse with inputs as : ");
                sb.append("\n responseHandleAs  : "+responseHandleAs);
                sb.append("\n outgoingProperties: "+outgoingProperties);
                sb.append("\n metricQueue       : "+metricQueue);
                sb.append("\n subscriptionQueue : "+subscriptionQueue);
                sb.append("\n metricType        : "+metricType);
                
                Logger.getRootLogger().error(sb.toString());
                
                throw new ConfigurationException(
                   ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for IntegrationProcessResponseBuilder."
                );
            }
            
            return handlerOption;
        }
    
    }
    
   public class NetworkPipelineConfigBuilder implements Builder{
     
        private ProtocolConfig  protocolConfig;
        private NodeServiceConfiguration inboundConfiguration;
        private NodeServiceConfiguration outboundConfiguration;
        private NetworkGroupType networkGroupType;
        private NetworkMetric pipelineMetricSubscriber;
        private boolean reuseNetworkGroup;    
        
        public NetworkPipelineConfigBuilder withProtocolConfigAs(ProtocolConfig  protocolConfig) {
            
            this.protocolConfig = protocolConfig;
            return this;
        }
        
        public NetworkPipelineConfigBuilder withInboundConfigurationAs(NodeServiceConfiguration 
                inboundConfiguration) {
            
            this.inboundConfiguration = inboundConfiguration;
            return this;
        }

        public NetworkPipelineConfigBuilder withOutboundConfigurationAs(NodeServiceConfiguration 
                outboundConfiguration) {
            
            this.outboundConfiguration = outboundConfiguration;
            return this;
        }
        
        public NetworkPipelineConfigBuilder withNetworkGroupTypeAs(NetworkGroupType networkGroupType) {
                
            this.networkGroupType = networkGroupType;
            return this;
        }
        
        public NetworkPipelineConfigBuilder withNetworkMetricSubscriberAs(NetworkMetric pipelineMetricSubscriber) {
            
            this.pipelineMetricSubscriber = pipelineMetricSubscriber;
            return this;
        }
        
        public NetworkPipelineConfigBuilder withReuseNetworkGroupAs(boolean reuseNetworkGroup) {
            
            this.reuseNetworkGroup = reuseNetworkGroup;
            return this;
        }
         
        public NetworkPipelineConfig build() throws ConfigurationException {
            
            NetworkPipelineConfig config;
            
            if(inboundConfiguration != null  && outboundConfiguration != null && protocolConfig != null) {
            
                config = new NetworkPipelineConfig(             
                            inboundConfiguration,
                            outboundConfiguration,
                            networkGroupType,
                            pipelineMetricSubscriber,
                            reuseNetworkGroup,
                            protocolConfig
                        );                
            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create NetworkPipelineConfig with inputs as : ");
                sb.append("\n inboundConfiguration      : "+inboundConfiguration);
                sb.append("\n outboundConfiguration     : "+outboundConfiguration);
                sb.append("\n networkGroupType          : "+networkGroupType);
                sb.append("\n pipelineMetricSubscriber  : "+pipelineMetricSubscriber);
                sb.append("\n reuseNetworkGroup         : "+reuseNetworkGroup);
                
                Logger.getRootLogger().error(sb.toString());
                
                throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for NetworkPipelineConfigBuilder. "
                );
            }
            
            return config;
        }
    
    }
   
   
   
   public class NodeEndPointConfigBuilder implements Builder{
       
       private NetworkPipelineConfig networkPipelineConfig;
       private String inboundPipelineHostName;
       private int inboundPipelinePortNumber;
       private String outboundPipelineHostName;
       private int outboundPipelinePortNumber;    
       private String channelName;
       
       public NodeEndPointConfigBuilder withNetworkPipelineConfigAs(NetworkPipelineConfig networkPipelineConfig) {
           
           this.networkPipelineConfig = networkPipelineConfig;
           return this;
       }

       public NodeEndPointConfigBuilder withInboundHostPortAs(String inboundPipelineHostName,
               int inboundPipelinePortNumber) {
           
           this.inboundPipelineHostName     = inboundPipelineHostName;
           this.inboundPipelinePortNumber   = inboundPipelinePortNumber;
           return this;
       }
       
       public NodeEndPointConfigBuilder withOutboundHostPortAs(String outboundPipelineHostName,
               int outboundPipelinePortNumber) {
           
           this.outboundPipelineHostName     = outboundPipelineHostName;
           this.outboundPipelinePortNumber   = outboundPipelinePortNumber;
           return this;
       }
       
       public NodeEndPointConfigBuilder withChannelNameAs(String channelName) {
               
           this.channelName = channelName;
           return this;
       }
       
       private static boolean isValidHostPort(String hostName, int portNumber ) {
           
           return (hostName != null && portNumber > 0);
       }
        
       public NodeEndPointConfig build() throws ConfigurationException {
           
           NodeEndPointConfig config;
           
           if(networkPipelineConfig != null  && isValidHostPort(inboundPipelineHostName,inboundPipelinePortNumber)
                   && isValidHostPort(outboundPipelineHostName ,outboundPipelinePortNumber)) {
           
               config = new NodeEndPointConfig( 
                           networkPipelineConfig,
                           inboundPipelineHostName,
                           inboundPipelinePortNumber, 
                           outboundPipelineHostName,  
                           outboundPipelinePortNumber,
                           channelName
                       );          
           }else {
               
               
               StringBuilder sb = new StringBuilder();
               sb.append("\n Failed to create NodeEndPointConfig with inputs as : ");
               sb.append("\n networkPipelineConfig      : "+networkPipelineConfig);
               sb.append("\n inboundPipelineHostName    : "+inboundPipelineHostName);
               sb.append("\n inboundPipelinePortNumber  : "+inboundPipelinePortNumber);
               sb.append("\n outboundPipelineHostName   : "+outboundPipelineHostName);
               sb.append("\n outboundPipelinePortNumber : "+outboundPipelinePortNumber);
               sb.append("\n channelName                : "+channelName);
               
               Logger.getRootLogger().error(sb.toString());
               throw new ConfigurationException(
                       ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for NodeEndPointConfigBuilder."
               );
           }
           
           return config;
       }
   
   }
   

  public class NetworkIntegrationConfigBuilder implements Builder{

       private ProtocolPublishType protocolPublishType;
       private Optional<IntegrationProcessRequest> handleFor = Optional.empty();
       private Optional<IntegrationProcessResponse> handleAs = Optional.empty();
       private IntegrationPipelineHandler inboundRequestHandler;
       private IntegrationPipelineHandler outboundResponseHandler;
       
       public NetworkIntegrationConfigBuilder withInboundRequestHandlerAs( IntegrationPipelineHandler 
           inboundRequestHandler) {
           
           this.inboundRequestHandler = inboundRequestHandler;
           return this;
       }
       
       public NetworkIntegrationConfigBuilder withOutboundResponseHandlerAs( IntegrationPipelineHandler 
               outboundResponseHandler) {
           
           this.outboundResponseHandler = outboundResponseHandler;
           return this;
       }

       public NetworkIntegrationConfigBuilder withProtocolPublishTypeAs(ProtocolPublishType protocolPublishType) {
           
           this.protocolPublishType     = protocolPublishType;
           return this;
       }
       
       public NetworkIntegrationConfigBuilder withIntegrationProcessRequestAs(Optional<IntegrationProcessRequest> 
           handleFor) {
           
           this.handleFor     = handleFor;
           return this;
       }
       
       public NetworkIntegrationConfigBuilder withIntegrationProcessResponseAs(
           Optional<IntegrationProcessResponse> handleAs) {
               
           this.handleAs = handleAs;
           return this;
       }
        
       public NetworkIntegrationConfig build() throws ConfigurationException {
           
           NetworkIntegrationConfig config;
           
           if(inboundRequestHandler != null  && outboundResponseHandler != null  && protocolPublishType != null ) {
               
               SimpleEntry<IntegrationPipelineHandler, IntegrationPipelineHandler> pipelineHandler = 
                       new SimpleEntry<>(inboundRequestHandler,outboundResponseHandler);
           
               config = new NetworkIntegrationConfig(
                           pipelineHandler,
                           protocolPublishType, 
                           handleFor,
                           handleAs
                       );    
           }else {
               
               StringBuilder sb = new StringBuilder();
               sb.append("\n Failed to create NetworkIntegrationConfig with inputs as : ");
               sb.append("\n inboundRequestHandler      : "+inboundRequestHandler);
               sb.append("\n outboundResponseHandler    : "+outboundResponseHandler);
               sb.append("\n protocolPublishType        : "+protocolPublishType);
               sb.append("\n IntegrationProcessRequest  : "+handleFor);
               sb.append("\n IntegrationProcessResponse : "+handleAs);
               
               Logger.getRootLogger().error(sb.toString());
               throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for NetworkIntegrationConfigBuilder."
               );
           }
           
           return config;
       }
   
   }
  
   public class NodePipelineConfigBuilder implements Builder{
      
    private NodeServiceConfiguration inboundConfiguration; 
    private NodeServiceConfiguration outboundConfiguration;
    private IntegrationBridge inboundBridge;
    private IntegrationBridge outboundBridge;
    private Optional<SimpleEntry<Integer,Integer>> processMinMaxLimitOption = Optional.empty();
    private Optional<Properties> propertiesOption  = Optional.empty();      
    private IdentityConfig  identityConfig;
    private ProtocolConfig protocolConfig; 
      
    public NodePipelineConfigBuilder withProtocolConfigAs(ProtocolConfig protocolConfig) {
        
        this.protocolConfig = protocolConfig;
        return this;
    }  
    
    public NodePipelineConfigBuilder withInboundConfigurationAs(NodeServiceConfiguration inboundConfiguration) {
    
        this.inboundConfiguration = inboundConfiguration;
        return this;
    }    
    
    public NodePipelineConfigBuilder withOutboundConfigurationAs(NodeServiceConfiguration outboundConfiguration) {
    
        this.outboundConfiguration = outboundConfiguration;
        return this;
    } 
    
    public NodePipelineConfigBuilder withInboundBridgeAs(IntegrationBridge inboundBridge) {
        
        this.inboundBridge = inboundBridge;
        return this;
    } 
    
    public NodePipelineConfigBuilder withOutboundBridgeAs(IntegrationBridge outboundBridge) {
        
        this.outboundBridge = outboundBridge;
        return this;
    } 
    
    public NodePipelineConfigBuilder withProcessOptionAs(Optional<SimpleEntry<Integer,Integer>> 
        processMinMaxLimitOption) {
        
        this.processMinMaxLimitOption = processMinMaxLimitOption;
        return this;
    } 
    
    public NodePipelineConfigBuilder withPropertiesOptionAs(Optional<Properties> propertiesOption) {
        
        this.propertiesOption = propertiesOption;
        return this;
    } 
    
    public NodePipelineConfigBuilder withIdentityConfigAs(IdentityConfig identityConfig) {
        
        this.identityConfig = identityConfig;
        return this;
    } 
    
    public NodePipelineConfig build() throws ConfigurationException {
        
        NodePipelineConfig config;
        
        if(inboundConfiguration != null  && outboundConfiguration != null && identityConfig != null &&
                protocolConfig != null ) {
        
            config = new NodePipelineConfig(
                        inboundConfiguration,
                        outboundConfiguration, 
                        inboundBridge,
                        outboundBridge,
                        processMinMaxLimitOption,
                        propertiesOption,
                        identityConfig,
                        protocolConfig
                    );    
        }else {
            
            StringBuilder sb = new StringBuilder();
            sb.append("\n Failed to create NodePipelineConfig with inputs as : ");
            sb.append("\n inboundConfiguration      : "+inboundConfiguration);
            sb.append("\n outboundConfiguration     : "+outboundConfiguration);
            sb.append("\n inboundBridge             : "+inboundBridge);
            sb.append("\n outboundBridge            : "+outboundBridge);
            sb.append("\n processMinMaxLimitOption  : "+processMinMaxLimitOption);
            sb.append("\n propertiesOption          : "+propertiesOption);
            sb.append("\n identityConfig            : "+identityConfig);
            sb.append("\n protocolConfig            : "+protocolConfig);
            
            Logger.getRootLogger().error(sb.toString());
            throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for NodePipelineConfigBuilder."
            );
        }
        
        return config;
    }
    
   }
   
   public class NodeEndPointConfigurationBuilder implements Builder{
       
       private NodeEndPointConfig endPointConfig;
       private NetworkIntegrationConfig integrationConfig;
       
       public NodeEndPointConfigurationBuilder withNodeEndPointConfigAs(NodeEndPointConfig endPointConfig) {
           
           this.endPointConfig = endPointConfig;
           return this;
       } 
       
       public NodeEndPointConfigurationBuilder withNetworkIntegrationConfigAs(NetworkIntegrationConfig 
           integrationConfig) {
           
           this.integrationConfig = integrationConfig;
           return this;
       }
       
       public NodeEndPointConfiguration build() throws ConfigurationException {
           
           NodeEndPointConfiguration nodeEndPointServerConfiguration;

           
           if(endPointConfig != null  && integrationConfig != null ) {
           
               nodeEndPointServerConfiguration = new NodeEndPointConfiguration(
                                                    endPointConfig,
                                                    integrationConfig
                                               );
           }else {
               
               StringBuilder sb = new StringBuilder();
               sb.append("\n Failed to create NodeEndPointConfiguration with inputs as : ");
               sb.append("\n endPointConfig      : "+endPointConfig);
               sb.append("\n integrationConfig   : "+integrationConfig);

               
               Logger.getRootLogger().error(sb.toString());
               throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for NodeEndPointConfigurationBuilder."
               );
           }
           
           return nodeEndPointServerConfiguration;
       }
    }
   
    public class NodePipelineServerBuilder implements Builder{
        
        private NodeEndPointConfiguration nodeEndPointServerConfiguration;
        private NodeServiceConfiguration inboundServiceConfiguration;
        
        public NodePipelineServerBuilder withNodeServiceConfigurationAs(NodeServiceConfiguration 
            inboundServiceConfiguration) {
            
            this.inboundServiceConfiguration = inboundServiceConfiguration;
            return this;
        } 
        
        public NodePipelineServerBuilder withNodeEndPointConfigurationAs(NodeEndPointConfiguration 
            nodeEndPointServerConfiguration) {
            
            this.nodeEndPointServerConfiguration = nodeEndPointServerConfiguration;
            return this;
        } 
        
        public NodePipelineServer build() throws ConfigurationException {
            
            NodePipelineServer nodePipelineServer;

            
            if(nodeEndPointServerConfiguration != null  && inboundServiceConfiguration != null ) {
            
                NodePipelineServerEndPoint nodePipelineServerEndPoint = new NodePipelineServerEndPoint();
                
                nodePipelineServerEndPoint.setEndPointStatus(EndPointStatus.REACHABLE);
                nodePipelineServerEndPoint.setLastUpdated(Timestamp.from(Instant.now()));
                nodePipelineServerEndPoint.setNotificationProtocolType(
                        nodeEndPointServerConfiguration.getProtocolConfig().getNotificationProtocolType());
                nodePipelineServerEndPoint.setPipelineFunctionType(
                        nodeEndPointServerConfiguration.getProtocolConfig().getPipelineFunctionType());
                
                nodePipelineServer = (NodePipelineServer) nodePipelineServerEndPoint.configureAsPipeline(
                        nodeEndPointServerConfiguration);

            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create NodePipelineServer with inputs as : ");
                sb.append("\n nodeEndPointServerConfiguration : "+nodeEndPointServerConfiguration);
                sb.append("\n inboundServiceConfiguration     : "+inboundServiceConfiguration);
                
                Logger.getRootLogger().error(sb.toString());
                throw new ConfigurationException(
                     ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for NodePipelineServerBuilder."
                );
            }
            
            return nodePipelineServer;
        }
    }

    public class NodePipelineClientBuilder implements Builder{

        private NodeServiceConfiguration outboundServiceConfiguration;
        private NodeEndPointConfig nodeEndPointConfig;
        private NetworkIntegrationConfig networkIntegrationConfig; 
        
        public NodePipelineClientBuilder withNodeServiceConfigurationAs(NodeServiceConfiguration 
            outboundServiceConfiguration) {
            
            this.outboundServiceConfiguration = outboundServiceConfiguration;
            return this;
        } 
        
        public NodePipelineClientBuilder withNodeEndPointConfigAs(NodeEndPointConfig nodeEndPointConfig) {
            
            this.nodeEndPointConfig = nodeEndPointConfig;
            return this;
        } 
        
        public NodePipelineClientBuilder withNetworkIntegrationConfigAs(NetworkIntegrationConfig 
            networkIntegrationConfig) {
            
            this.networkIntegrationConfig = networkIntegrationConfig;
            return this;
        } 
        
        private List<NodeEndPointConfiguration> createOutboundNetworkPipelineConfiguration() {
                        
            List<NodeEndPointConfiguration> nodeEndPointConfigurationList = new ArrayList<>();
            
            Map<SimpleEntry<String, Integer>, SimpleEntry<String, Integer>> pipelineHostPortMap = new HashMap<>();
                
            Iterator<SimpleEntry<String, Integer>> inboundIter = 
                    nodeEndPointConfig.getNetworkPipelineConfig().getInboundConfiguration()
                        .getClientEndPointList().iterator();
            
            Iterator<SimpleEntry<String, Integer>> outboundIter = 
                    nodeEndPointConfig.getNetworkPipelineConfig().getOutboundConfiguration()
                    .getClientEndPointList().iterator();
            
            while(inboundIter.hasNext() && outboundIter.hasNext()) {

                pipelineHostPortMap.put(inboundIter.next(), outboundIter.next());                           
            }       
            
            Iterator<Entry<SimpleEntry<String, Integer>, SimpleEntry<String, Integer>>> pipelineHostPortMapIter =
                    pipelineHostPortMap.entrySet().iterator();
            
            while(pipelineHostPortMapIter.hasNext()) {
                    
                Entry<SimpleEntry<String, Integer>, SimpleEntry<String, Integer>> entry = 
                        pipelineHostPortMapIter.next();
                
                SimpleEntry<String, Integer> inboundHostPortConfig  = entry.getKey();
                SimpleEntry<String, Integer> outboundHostPortConfig = entry.getValue();   
            
                nodeEndPointConfig.getNetworkPipelineConfig().getInboundConfiguration().setServerHostName(
                        inboundHostPortConfig.getKey());
                nodeEndPointConfig.getNetworkPipelineConfig().getInboundConfiguration().setServerHostPort(
                        inboundHostPortConfig.getValue());

                nodeEndPointConfig.getNetworkPipelineConfig().getOutboundConfiguration().setServerHostName(
                        outboundHostPortConfig.getKey());
                nodeEndPointConfig.getNetworkPipelineConfig().getOutboundConfiguration().setServerHostPort(
                        outboundHostPortConfig.getValue());

                nodeEndPointConfigurationList.add(
                    new NodeEndPointConfiguration(
                        nodeEndPointConfig,
                        networkIntegrationConfig
                    )
                );
            }   
            return nodeEndPointConfigurationList;  
        }
        
        public List<NodePipelineClient> build() throws ConfigurationException {
            
            List<NodePipelineClient> nodePipelineClientList = new ArrayList<>();
            
            if(nodeEndPointConfig != null  && networkIntegrationConfig != null  && 
                    outboundServiceConfiguration != null ) {
                
                List<NodeEndPointConfiguration> nodeEndPointConfigurationList = 
                        createOutboundNetworkPipelineConfiguration();
                
                NodePipelineClientEndPoint clientEndPoint = new NodePipelineClientEndPoint();
                
                Iterator<NodeEndPointConfiguration> iter = nodeEndPointConfigurationList.iterator();
                
                while(iter.hasNext()) {
                    
                    NodeEndPointConfiguration c = iter.next();
                    
                    clientEndPoint.setEndPointStatus(EndPointStatus.REACHABLE);
                    clientEndPoint.setLastUpdated(Timestamp.from(Instant.now()));
                    clientEndPoint.setNotificationProtocolType(
                            nodeEndPointConfig.getNetworkPipelineConfig().getProtocolConfig()
                            .getNotificationProtocolType());
                    
                    nodePipelineClientList.add((NodePipelineClient) clientEndPoint.configureAsPipeline(c));
                }

            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create NodePipelineClient with inputs as : ");
                sb.append("\n nodeEndPointConfig            : "+nodeEndPointConfig);
                sb.append("\n networkIntegrationConfig      : "+networkIntegrationConfig);
                sb.append("\n outboundServiceConfiguration  : "+outboundServiceConfiguration);
                
                Logger.getRootLogger().error(sb.toString());
                
                throw new ConfigurationException(
                   ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for NodePipelineClientBuilder. "
                );
            }
            
            return nodePipelineClientList;
        }
    }

}


