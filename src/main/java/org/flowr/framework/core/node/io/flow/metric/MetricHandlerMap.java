
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io.flow.metric;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessContext;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessHandler;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessResponse;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.MetricType;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.ProtocolPublishType;
import org.flowr.framework.core.node.io.flow.protocol.NetworkSubscriptionContext;

public class MetricHandlerMap extends HashMap<IntegrationProcessHandler,MetricType>{

    private static final long serialVersionUID = FrameworkConstants.FRAMEWORK_VERSION_ID;
    
    private IntegrationProcessHandler get(MetricType metricType,ProtocolPublishType protocolPublishType) {
        
        IntegrationProcessHandler integrationProcessHandler = null;     
        
        Iterator<Entry<IntegrationProcessHandler, MetricType>> contextIterator =this.entrySet().iterator();
        
        while(contextIterator.hasNext()) {
            
            Entry<IntegrationProcessHandler, MetricType> entry = contextIterator.next();
            
            if(entry.getValue() == metricType && entry.getKey().getProtocolPublishType() == protocolPublishType) {
                
                integrationProcessHandler = entry.getKey();
                break;
            }           
        }
        
        return integrationProcessHandler;
    }
    
    
    public Optional<IntegrationProcessResponse> delegate(IntegrationProcessContext processContext) {
        
        Optional<IntegrationProcessResponse> handleAs = Optional.empty();       
        
        IntegrationProcessHandler handler = this.get(
                                                    processContext.getMetricType(), 
                                                    processContext.getProtocolPublishType()
                                            );
                
        if(handler != null) {       
             
            
            switch(processContext.getMetricType()) {
                
                case CANCEL:{
                    
                    handleAs = handleNetworkContext(processContext, handler);
                    break;
                }case COMPLETION:{
                    
                    handleAs = handleMetricContext(processContext, handler);
                    break;
                }case ERROR:{
                    
                    handleAs = handleMetricContext(processContext, handler);
                    break;
                }case SEEK:{
                    
                    handleAs = handleNetworkContext(processContext, handler);
                    break;
                }case SUBSCRIPTION:{
                    
                    handleAs = handleMetricContext(processContext, handler);
                    break;
                }case TRANSFER:{
                    
                    handleAs = handleMetricContext(processContext,  handler);
                    break;
                }default:{
                    break;
                }       
                
            }
        }
            
        return handleAs;
        
    }


    private static Optional<IntegrationProcessResponse> handleMetricContext(
        IntegrationProcessContext integrationProcessContext, IntegrationProcessHandler handler) {
        
        Optional<IntegrationProcessResponse> handleAs = Optional.empty();   
        
        Optional<MetricContext> metricContextOption = integrationProcessContext.getMetricContext();
        
        if(metricContextOption.isPresent()) {
            handleAs = handler.handle(integrationProcessContext);
        }
        
        
        return handleAs;
    }


    private static Optional<IntegrationProcessResponse> handleNetworkContext(
            IntegrationProcessContext integrationProcessContext, 
            IntegrationProcessHandler integrationProcessHandler) {
        
        Optional<IntegrationProcessResponse> handleAs = Optional.empty();   
        
        Optional<NetworkSubscriptionContext>  networkSubscriptionContextOption =
                integrationProcessContext.getNetworkSubscriptionContext();
        
        if(networkSubscriptionContextOption.isPresent()) {
            handleAs = integrationProcessHandler.handle(integrationProcessContext);
        }
        
        return handleAs;
    }
}
