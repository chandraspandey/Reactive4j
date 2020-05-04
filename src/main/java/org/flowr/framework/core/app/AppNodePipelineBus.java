
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.app;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.io.endpoint.NodePipelineClient;
import org.flowr.framework.core.node.io.endpoint.NodePipelineServer;
import org.flowr.framework.core.node.io.flow.IOFlow.IOFlowType;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler.HandlerType;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessRequest;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessResponse;
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupType;
import org.flowr.framework.core.service.Service.ServiceStatus;

public class AppNodePipelineBus extends AbstractNodePipelineManager implements AppNodeBuilder{
  
    private Map<NodePipelineServer,List<NodePipelineClient>> serverClientMap = new HashMap<>();
    private List<SimpleEntry<Thread, Thread>> networkThreadPool = new ArrayList<>();
    
    public void addToNetworkThreadPool(SimpleEntry<Thread, Thread> clientServerPair) {
        
        networkThreadPool.add(clientServerPair);
    }
     
    public NodePipelineServer confgureInboundNodePipelineServer(NodePipelineConfig serverConfig) throws 
        ConfigurationException {
      
       return         
        new NodePipelineServerBuilder()
            .withNodeEndPointConfigurationAs(
                    new NodeEndPointConfigurationBuilder()
                    .withNodeEndPointConfigAs(
                         new NodeEndPointConfigBuilder()
                            .withChannelNameAs(serverConfig.getInboundConfiguration().getNodeChannelName())
                            .withNetworkPipelineConfigAs(
                                    new NetworkPipelineConfigBuilder()
                                    .withInboundConfigurationAs(serverConfig.getInboundConfiguration())
                                    .withOutboundConfigurationAs(serverConfig.getOutboundConfiguration())
                                    .withNetworkMetricSubscriberAs(this)
                                    .withNetworkGroupTypeAs(NetworkGroupType.LOCAL)
                                    .withReuseNetworkGroupAs(true)
                                    .build()
                            )
                            .withInboundHostPortAs(serverConfig.getInboundConfiguration().getHostName(), 
                                    serverConfig.getInboundConfiguration().getHostPort())
                            .withOutboundHostPortAs(serverConfig.getOutboundConfiguration().getHostName(), 
                                    serverConfig.getOutboundConfiguration().getHostPort())
                            .build()
                    )
                    .withNetworkIntegrationConfigAs(
                        new NetworkIntegrationConfigBuilder()
                            .withInboundRequestHandlerAs(
                                    new IntegrationPipelineBuilder()
                                    .withNameAs(
                                        serverConfig.getInboundConfiguration().getConfigName()+"-"+
                                        ConfigurationType.SERVER.name())
                                    .withIntegrationBridgeAs(serverConfig.getInboundBridge())
                                    .withHandlerTypeAs(HandlerType.IO)
                                    .withIOFlowTypeAs(IOFlowType.INBOUND)
                                    .build()
                            )
                            .withOutboundResponseHandlerAs(
                                    new IntegrationPipelineBuilder()
                                    .withNameAs(
                                        serverConfig.getInboundConfiguration().getConfigName()+"-"+
                                        ConfigurationType.CLIENT.name())
                                    .withIntegrationBridgeAs(serverConfig.getOutboundBridge())
                                    .withHandlerTypeAs(HandlerType.IO)
                                    .withIOFlowTypeAs(IOFlowType.OUTBOUND)
                                    .build()
                            )               
                            .withProtocolPublishTypeAs(ProtocolPublishType.INTERNAL)
                            .withIntegrationProcessRequestAs(
                                 new IntegrationProcessRequestBuilder()
                                    .withIntegrationProcessRequestAs(
                                            new IntegrationProcessRequest.DefaultIntegrationProcessRequest()
                                     )
                                    .withMetricTypeAs(MetricType.TRANSFER)
                                    .withMinMaxLimitAs(serverConfig.getProcessMinMaxLimitOption())
                                    .withPropertiesAs(serverConfig.getPropertiesOption())
                                    .build()
                            ).withIntegrationProcessResponseAs(
                                 new IntegrationProcessResponseBuilder()
                                    .withIntegrationProcessResponseAs(
                                            new IntegrationProcessResponse.DefaultIntegrationProcessResponse())
                                    .build()
                            ).build()
                    )
                    .build()
            )
            .withNodeServiceConfigurationAs(serverConfig.getInboundConfiguration())
            .build();
    }

    

    public List<NodePipelineClient> configureOutboundNetworkPipeline(NodePipelineConfig clientConfig) 
            throws ConfigurationException {

          return  
          new NodePipelineClientBuilder()
               .withNodeEndPointConfigAs(
                    new NodeEndPointConfigBuilder()
                       .withChannelNameAs(clientConfig.getOutboundConfiguration().getNodeChannelName())
                       .withNetworkPipelineConfigAs(
                            new NetworkPipelineConfigBuilder()
                               .withInboundConfigurationAs(clientConfig.getInboundConfiguration())
                               .withOutboundConfigurationAs(clientConfig.getOutboundConfiguration())
                               .withNetworkMetricSubscriberAs(this)
                               .withNetworkGroupTypeAs(NetworkGroupType.LOCAL)
                               .withReuseNetworkGroupAs(true)
                               .build()
                       )
                       .withInboundHostPortAs(
                               clientConfig.getInboundConfiguration().getHostName(), 
                               clientConfig.getInboundConfiguration().getHostPort()
                       ).withOutboundHostPortAs(
                               clientConfig.getOutboundConfiguration().getHostName(), 
                               clientConfig.getOutboundConfiguration().getHostPort())
                       .build()
                )
               .withNetworkIntegrationConfigAs(
                       new NetworkIntegrationConfigBuilder()

                       .withProtocolPublishTypeAs(ProtocolPublishType.INTERNAL)
                       .withInboundRequestHandlerAs(
                            new IntegrationPipelineBuilder()
                               .withNameAs(clientConfig.getOutboundConfiguration().getConfigName()+
                                       "-"+ConfigurationType.CLIENT.name())
                               .withIOFlowTypeAs(IOFlowType.INBOUND)
                               .withHandlerTypeAs(HandlerType.IO)
                               .withIntegrationBridgeAs(clientConfig.getInboundBridge())
                               .build()
                       )
                       .withOutboundResponseHandlerAs(
                               new IntegrationPipelineBuilder()
                               .withNameAs(clientConfig.getOutboundConfiguration().getConfigName()+
                                       "-"+ConfigurationType.SERVER.name())
                               .withIOFlowTypeAs(IOFlowType.OUTBOUND)
                               .withHandlerTypeAs(HandlerType.IO)
                               .withIntegrationBridgeAs(clientConfig.getOutboundBridge())
                               .build()
                       )
                       .withIntegrationProcessResponseAs(
                            new IntegrationProcessResponseBuilder()
                               .withIntegrationProcessResponseAs(
                                       new IntegrationProcessResponse.DefaultIntegrationProcessResponse())
                               .build()
                       )
                       .withIntegrationProcessRequestAs(
                            new IntegrationProcessRequestBuilder()
                               .withIntegrationProcessRequestAs(
                                       new IntegrationProcessRequest.DefaultIntegrationProcessRequest()
                               )
                               .withMetricTypeAs(MetricType.TRANSFER)
                               .withMinMaxLimitAs(clientConfig.getProcessMinMaxLimitOption())
                               .withPropertiesAs(clientConfig.getPropertiesOption())
                               .build()
                       )
                       .build()
                )
               .withNodeServiceConfigurationAs(
                       clientConfig.getOutboundConfiguration()
               )
               .build();

    }
    

    
    public ServiceStatus startup() throws ConfigurationException{
        
        this.initNetworkBusExecutor(Optional.empty());
        
        return ServiceStatus.STARTED;
    }
    
    public ServiceStatus shutdown() throws ConfigurationException{
        
        serverClientMap.entrySet().forEach(

            (Map.Entry<NodePipelineServer, List<NodePipelineClient>> entry) -> {

                entry.getKey().close();
                entry.getValue().forEach(

                        client -> client.close()

                        );
            }
        );

        // See if NodeThread with KeepRunning would be better alternative
        networkThreadPool.forEach(

            (SimpleEntry<Thread, Thread> t) -> {
                try {
                    t.getKey().join();
                    t.getValue().join();
                } catch (InterruptedException e) {
                    Logger.getRootLogger().error(e);
                    Thread.currentThread().interrupt();
                }
            }
        );
        
        return ServiceStatus.STOPPED;    
    }

    @Override
    public SimpleEntry<NodePipelineServer,List<NodePipelineClient>> configure(
            SimpleEntry<NodePipelineConfig,NodePipelineConfig> serverClientConfigEntry) 
            throws ConfigurationException{

        List<NodePipelineClient> nodePipelineClientList = this.configureOutboundNetworkPipeline(
                                                                serverClientConfigEntry.getValue());

        NodePipelineServer nodeServer = confgureInboundNodePipelineServer(serverClientConfigEntry.getKey());
        
        /**
         * Initialize the Server for settings
         */
        nodeServer.init();
        
        serverClientMap.put(nodeServer, nodePipelineClientList);
       
        return new SimpleEntry<>(nodeServer,nodePipelineClientList);
    }

}
