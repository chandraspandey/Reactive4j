
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.app;

import java.util.AbstractMap.SimpleEntry;
import java.util.Optional;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.NodeManager;
import org.flowr.framework.core.node.NodeMetricHandler;
import org.flowr.framework.core.node.io.endpoint.NodePipelineClient;
import org.flowr.framework.core.node.io.endpoint.NodePipelineServer;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessResponse;
import org.flowr.framework.core.node.io.flow.metric.ChannelMetric;
import org.flowr.framework.core.node.io.flow.metric.MetricContext;
import org.flowr.framework.core.node.io.flow.protocol.NetworkSubscriptionContext;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelFlowType;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelStatus;
import org.flowr.framework.core.node.io.pipeline.NetworkBus;
import org.flowr.framework.core.node.io.pipeline.NetworkBusExecutor;
import org.flowr.framework.core.node.io.pipeline.NetworkPipelineBus;
import org.flowr.framework.core.node.io.pipeline.NetworkPipelineBusExecutor;
import org.flowr.framework.core.service.Service.ServiceStatus;

public abstract class AbstractNodeBusExecutor implements  NodeManager{
    
    protected NetworkBusExecutor networkBusExecutor;
    private NetworkBus  networkBus                  = new NetworkPipelineBus();
    private NodeMetricHandler nodeMetricHandler     = new NodeMetricHandler(); 
    
     
    @Override
    public ServiceStatus initNetworkBusExecutor(Optional<Properties> configProperties) throws ConfigurationException {
        
        networkBusExecutor = new NetworkPipelineBusExecutor(networkBus);
        
        return networkBusExecutor.startup(configProperties);
    }
    
    @Override
    public ServiceStatus closeNetworkBusExecutor(Optional<Properties> configProperties) throws ConfigurationException {
        
        networkBusExecutor = new NetworkPipelineBusExecutor(networkBus);
        
        return networkBusExecutor.shutdown(configProperties);
    }

    
    @Override
    public SimpleEntry<Thread, Thread> startInboundNetworkPipeline(NodePipelineServer toServer,
        NodePipelineClient fromClient) throws ConfigurationException{
        
        Thread serverThread = new Thread(toServer);
        serverThread.start();   
        
        ChannelStatus inboundServerChannelStatus = toServer.connect(null,ChannelFlowType.INBOUND);
        Logger.getRootLogger().info("NodePipelineManager : SERVER : inboundServerChannelStatus : "+
                inboundServerChannelStatus);
    
        Thread clientThread = new Thread(fromClient);
        clientThread.start();
        
        ChannelStatus outboundClientChannelStatus = fromClient.connect(
                toServer.getSocketAddress(ChannelFlowType.INBOUND),
                ChannelFlowType.OUTBOUND);
        
        Logger.getRootLogger().info("NodePipelineManager : CLIENT : outboundClientChannelStatus : "+
                outboundClientChannelStatus);
        
        while(
                (toServer.getChannelStatus(ChannelFlowType.INBOUND) != ChannelStatus.CONNECTED ) &&
                (fromClient.getChannelStatus(ChannelFlowType.OUTBOUND) != ChannelStatus.CONNECTED )
            ){
                
                Logger.getRootLogger().info("NodePipelineManager : Waiting for sync ");
            
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Logger.getRootLogger().error(e);
                    Thread.currentThread().interrupt();
                }
        }

    
        return new SimpleEntry<>(serverThread,clientThread);
    }
    
    @Override
    public SimpleEntry<Thread, Thread> startOutboundNetworkPipeline(NodePipelineClient fromClient,
                NodePipelineServer toServer) throws ConfigurationException{
            
        Thread serverThread = new Thread(toServer);
        serverThread.start();   
    
        ChannelStatus outboundServerChannelStatus = toServer.connect(null,ChannelFlowType.OUTBOUND);
        Logger.getRootLogger().info("NodePipelineManager : SERVER : outboundServerChannelStatus : "+
                outboundServerChannelStatus);
    
        Thread clientThread = new Thread(fromClient);
        clientThread.start();
        
        ChannelStatus outboundClientChannelStatus = fromClient.connect(
                toServer.getSocketAddress(ChannelFlowType.OUTBOUND),
                ChannelFlowType.INBOUND);
        Logger.getRootLogger().info("NodePipelineManager : CLIENT : outboundClientChannelStatus : "+
                    outboundClientChannelStatus);
        
        while(
                (toServer.getChannelStatus(ChannelFlowType.OUTBOUND) != ChannelStatus.CONNECTED ) &&
                (fromClient.getChannelStatus(ChannelFlowType.INBOUND) != ChannelStatus.CONNECTED )
            ){
                
                Logger.getRootLogger().info("NodePipelineManager : Waiting for sync ");
            
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Logger.getRootLogger().error(e);
                    Thread.currentThread().interrupt();
                }
        }
    
        return new SimpleEntry<>(serverThread,clientThread);
    }
    
 
    @Override
    public NetworkBus getNetworkBus() {
        return networkBus;
    }
    
     
    @Override
    public void registerMetricMapperHandler() {
        
        nodeMetricHandler.registerMetricMapperHandler();
    }   
    
    @Override
    public Optional<IntegrationProcessResponse> handleMetricType(MetricContext metricContext) {
        
        return nodeMetricHandler.handleMetricType(metricContext);
    }
    
    @Override
    public void onSeek(NetworkSubscriptionContext networkSubscriptionContext) {
        
        MetricContext metricContext = new MetricContext(
                                            MetricType.SEEK,
                                            Optional.of(ChannelMetric.seekMetric(null)),
                                            Optional.empty(),
                                            Optional.empty(),
                                            networkSubscriptionContext.getProtocolPublishType(),
                                            networkSubscriptionContext.getHandleFor(),
                                            networkSubscriptionContext.getHandleAs()
                                        );
        this.handleMetricType(metricContext);
        
    }
    
    @Override
    public void onCancel(NetworkSubscriptionContext networkSubscriptionContext) {
            
        MetricContext metricContext = new MetricContext(
                                            MetricType.CANCEL,
                                            Optional.of(ChannelMetric.cancelMetric(null)),
                                            Optional.empty(),
                                            Optional.empty(),
                                            networkSubscriptionContext.getProtocolPublishType(),
                                            networkSubscriptionContext.getHandleFor(),
                                            networkSubscriptionContext.getHandleAs()
                                        );
        this.handleMetricType(metricContext);
    }


}
