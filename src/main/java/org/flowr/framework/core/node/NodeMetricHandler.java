
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node;

import java.util.Arrays;
import java.util.Optional;

import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessContext;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessHandler;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessRequest;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessResponse;
import org.flowr.framework.core.node.io.flow.metric.MetricContext;
import org.flowr.framework.core.node.io.flow.metric.MetricHandlerMap;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.MetricType;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.ProtocolPublishType;
import org.flowr.framework.core.node.io.flow.protocol.NetworkSubscriptionContext;

public class NodeMetricHandler {

    private MetricHandlerMap metricHandlerMap       = new MetricHandlerMap();
    
    public void registerMetricMapperHandler() {
        
        Arrays.asList(MetricType.values()).forEach(
                
            (MetricType m) -> {
                
                IntegrationProcessHandler internalProcessHandler = 
                            new IntegrationProcessHandler.DefaultIntegrationProcessHandler();
                internalProcessHandler.setProtocolPublishType(ProtocolPublishType.INTERNAL);                
                metricHandlerMap.put( internalProcessHandler,m);
                
                IntegrationProcessHandler externalProcessHandler = 
                            new IntegrationProcessHandler.DefaultIntegrationProcessHandler();
                externalProcessHandler.setProtocolPublishType(ProtocolPublishType.EXTERNAL);                
                metricHandlerMap.put(externalProcessHandler,m);
                
                
            }
        );  
    }   
    
    public Optional<IntegrationProcessResponse> handleMetricType(MetricContext metricContext) {
        
        Optional<IntegrationProcessRequest> handleFor = metricContext.getHandleFor();
        
        Optional<IntegrationProcessResponse> handleAs = metricContext.getHandleAs();
        
        switch(metricContext.getMetricType()) {
            
            case CANCEL:{
                
                NetworkSubscriptionContext networkSubscriptionContext = 
                    new NetworkSubscriptionContext(
                        MetricType.CANCEL,
                        NetworkSubscriptionContext.DEFAULT_TIMEOUT,
                        NetworkSubscriptionContext.DEFAULT_TIMEUNIT,
                        metricContext.getProtocolPublishType(),
                        metricContext.getHandleFor(),
                        metricContext.getHandleAs()         
                    );                      
                
                IntegrationProcessContext networkActuatorContext = new IntegrationProcessContext(
                        ProtocolPublishType.INTERNAL,MetricType.CANCEL,  Optional.empty(), 
                        Optional.of(networkSubscriptionContext),handleFor,handleAs);                
                                
                handleAs = metricHandlerMap.delegate(networkActuatorContext);

                break;
            }case COMPLETION:{
                if(metricContext.getChannelMetric().isPresent()) {
                    
                    IntegrationProcessContext networkActuatorContext = new IntegrationProcessContext(
                            ProtocolPublishType.INTERNAL,MetricType.COMPLETION, Optional.of(metricContext), 
                            Optional.empty(), handleFor,handleAs);
                    handleAs = metricHandlerMap.delegate(networkActuatorContext);
                }
                break;
            }case ERROR:{
                
                if(metricContext.getThrowable().isPresent() ) {                 

                    IntegrationProcessContext networkActuatorContext = new IntegrationProcessContext(
                            ProtocolPublishType.INTERNAL,MetricType.COMPLETION, Optional.of(metricContext), 
                            Optional.empty(), handleFor,handleAs);
                    handleAs = metricHandlerMap.delegate(networkActuatorContext);
                }
                break;
            }case SUBSCRIPTION:{
                
                if(metricContext.getSubscription().isPresent()) {
                    
                    IntegrationProcessContext networkActuatorContext = new IntegrationProcessContext(
                            ProtocolPublishType.INTERNAL,MetricType.SUBSCRIPTION, Optional.of(metricContext), 
                            Optional.empty(), handleFor,handleAs);
                    handleAs = metricHandlerMap.delegate(networkActuatorContext);
                }
                break;
            }case TRANSFER:{
                
                if(metricContext.getChannelMetric().isPresent()) {
    
                    IntegrationProcessContext networkActuatorContext = new IntegrationProcessContext(
                            ProtocolPublishType.INTERNAL,MetricType.TRANSFER, Optional.of(metricContext), 
                            Optional.empty(), handleFor,handleAs);
                    handleAs = metricHandlerMap.delegate(networkActuatorContext);
                }
                break;
            }case SEEK:{
                
                NetworkSubscriptionContext networkSubscriptionContext = 
                    new NetworkSubscriptionContext(
                        MetricType.SEEK,
                        NetworkSubscriptionContext.DEFAULT_TIMEOUT,
                        NetworkSubscriptionContext.DEFAULT_TIMEUNIT,
                        metricContext.getProtocolPublishType(),
                        metricContext.getHandleFor(),
                        metricContext.getHandleAs()         
                    );                          
                        
                IntegrationProcessContext networkActuatorContext = new IntegrationProcessContext(
                        ProtocolPublishType.INTERNAL,MetricType.SEEK,  Optional.empty(), 
                        Optional.of(networkSubscriptionContext), handleFor,handleAs);

                handleAs = metricHandlerMap.delegate(networkActuatorContext);
                break;
            }default:{
                break;
            }
        
        }
        
        return handleAs;
        
    }

    public MetricHandlerMap getMetricHandlerMap() {
        return metricHandlerMap;
    }
    
    public String toString(){
        
        return "NodeMetricHandler{"+
                "\n metricHandlerMap : "+metricHandlerMap+
                "\n}\n";
    }
}
