
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io.flow.handler;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

import org.apache.log4j.Logger;
import org.flowr.framework.core.node.io.flow.metric.MetricContext;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.ProtocolPublishType;
import org.flowr.framework.core.node.io.flow.protocol.NetworkSubscriptionContext;

public interface IntegrationProcessHandler {

    String getProcessHandlerName();
    
    Optional<IntegrationProcessResponse> handle(IntegrationProcessContext integrationProcessContext) ;
    
    Collection<MetricContext> getMetricQueueAsCollection();
    
    Collection<NetworkSubscriptionContext> getSubscriptionQueueAsCollection();
    
    void setProtocolPublishType(ProtocolPublishType protocolPublishType);
    
    ProtocolPublishType getProtocolPublishType();
    
    class DefaultIntegrationProcessHandler implements IntegrationProcessHandler{

        private ProtocolPublishType protocolPublishType             = ProtocolPublishType.INTERNAL;
        private String integrationProcessHandlerName = DefaultIntegrationProcessHandler.class.getSimpleName();
        private static TransferQueue<MetricContext> metricQueue     = new LinkedTransferQueue<>();
        private static TransferQueue<NetworkSubscriptionContext> subscriptionQueue  = 
                new LinkedTransferQueue<>();
        
        @Override
        public String getProcessHandlerName() {

            return this.integrationProcessHandlerName;
        }


        @Override
        public Optional<IntegrationProcessResponse> handle(IntegrationProcessContext processContext) {
                        
            Optional<IntegrationProcessRequest> processRequest   = processContext.getHandleFor();
            Optional<IntegrationProcessResponse> processResponse = processContext.getHandleAs();
            
            LinkedTransferQueue<MetricContext> bufferMetricQueue = new LinkedTransferQueue<>();
            LinkedTransferQueue<NetworkSubscriptionContext> bufferSubscriptionQueue     
                = new LinkedTransferQueue<>();          
            
            Optional<MetricContext>  contextOption = processContext.getMetricContext();
            
            if(contextOption.isPresent() ) {
                
                metricQueue.add(contextOption.get());                       
        
                handleMetricBufferQueue(processContext, processRequest, processResponse,bufferMetricQueue);     
                
                metricQueue.removeAll(bufferMetricQueue);
            }
            
            Optional<NetworkSubscriptionContext> contextSubscriptionOption = 
                    processContext.getNetworkSubscriptionContext();
            
            if(contextSubscriptionOption.isPresent()) {
                
                subscriptionQueue.add(contextSubscriptionOption.get());
                handleNetworkQueue(processContext, processRequest, processResponse,bufferMetricQueue,
                        bufferSubscriptionQueue);
                subscriptionQueue.removeAll(bufferSubscriptionQueue);
            }
            
            if(processRequest.isPresent() && processResponse.isPresent()) {
                
                processResponse.get().setProperties(processRequest.get().getProperties());
                
            }else {
                processResponse = Optional.empty();
            }
            
            Logger.getRootLogger().debug("\t DefaultIntegrationProcessHandler : handle : "+processResponse);
            
            return processResponse;
        }


        private static void handleMetricBufferQueue(IntegrationProcessContext processContext,
                Optional<IntegrationProcessRequest> processRequest,
                Optional<IntegrationProcessResponse> processResponse,
                LinkedTransferQueue<MetricContext> bufferMetricQueue) {
            
            
            if(processRequest.isPresent() && processResponse.isPresent()) {
                
                Iterator<MetricContext>  iter = metricQueue.iterator();
                
                while(iter.hasNext()) {
                
                    MetricContext q = iter.next();                  
                        
                    if(processRequest.get().getMetricTypeSet().contains(q.getMetricType())) {
                                                
                        handleMetricProcess(processContext, processResponse, bufferMetricQueue, q);
                    }else {
                        metricQueue.drainTo(bufferMetricQueue);
                        processResponse.get().setMetricQueue(Optional.of(bufferMetricQueue));
                    }                   
                }
                
            }
        }


        private static void handleMetricProcess(IntegrationProcessContext processContext,
                Optional<IntegrationProcessResponse> processResponse,
                LinkedTransferQueue<MetricContext> bufferMetricQueue, MetricContext q) {
           
            Optional<IntegrationProcessRequest>  requestOption = processContext.getHandleFor();
            
            if(requestOption.isPresent() && processResponse.isPresent()) {
            
                Optional<SimpleEntry<Integer,Integer>> responseMinMaxLimit = 
                        requestOption.get().getResponseMinMaxLimit();
                
                if(responseMinMaxLimit.isPresent()) {
                                                    
                    if(
                            metricQueue.size()       >= responseMinMaxLimit.get().getKey() &&
                            bufferMetricQueue.size() <= responseMinMaxLimit.get().getValue()
                            
                    ) {
                        bufferMetricQueue.add(q);
                    }
                    
                }else {                                 
                    bufferMetricQueue.add(q);   
                }                           
                
                processResponse.get().setMetricType(q.getMetricType());
                processResponse.get().setMetricQueue(Optional.of(bufferMetricQueue));
            }
        }


        private static void handleNetworkQueue(IntegrationProcessContext processContext,
                Optional<IntegrationProcessRequest> processRequest,
                Optional<IntegrationProcessResponse> processResponse,
                LinkedTransferQueue<MetricContext> bufferMetricQueue,
                LinkedTransferQueue<NetworkSubscriptionContext> bufferSubscriptionQueue) {
            
            
            if(processRequest.isPresent() && processResponse.isPresent()) {
                
                Iterator<NetworkSubscriptionContext> iter = subscriptionQueue.iterator();
            
                while(iter.hasNext()) {
                        
                    NetworkSubscriptionContext q = iter.next();
                        
                    if(processRequest.get().getMetricTypeSet().contains(    q.getMetricType())) {
                        
                        handleNetworkProcess(processContext, processResponse, bufferMetricQueue, 
                                bufferSubscriptionQueue, q);
                    }else {
                        subscriptionQueue.drainTo(bufferSubscriptionQueue);
                        processResponse.get().setSubscriptionQueue(Optional.of(bufferSubscriptionQueue));
                    }
                }

            }
        }


        private static  void handleNetworkProcess(IntegrationProcessContext processContext,
                Optional<IntegrationProcessResponse> processResponse,
                LinkedTransferQueue<MetricContext> bufferMetricQueue,
                LinkedTransferQueue<NetworkSubscriptionContext> bufferSubscriptionQueue, NetworkSubscriptionContext q) {
            
            Optional<IntegrationProcessRequest>  requestOption = processContext.getHandleFor();
            
            if(requestOption.isPresent() && processResponse.isPresent()) {
                

                Optional<SimpleEntry<Integer,Integer>> responseMinMaxLimit = 
                        requestOption.get().getResponseMinMaxLimit();
                
                if(responseMinMaxLimit.isPresent()) {
                                                    
                    if(
                            subscriptionQueue.size() >= responseMinMaxLimit.get().getKey() &&
                            bufferMetricQueue.size() <= responseMinMaxLimit.get().getValue()
                            
                    ) {
                        bufferSubscriptionQueue.add(q);
                    }
                    
                }else {
                    
                    bufferSubscriptionQueue.add(q); 
                }
                
                processResponse.get().setMetricType(q.getMetricType());
                processResponse.get().setSubscriptionQueue(Optional.of(bufferSubscriptionQueue));
            }
        }
        

        @Override
        public Collection<MetricContext> getMetricQueueAsCollection(){
            
            Collection<MetricContext> collection = new ArrayList<>();
            
            metricQueue.drainTo(collection);
            
            return collection;
        }
        
        @Override
        public Collection<NetworkSubscriptionContext> getSubscriptionQueueAsCollection(){
            
            Collection<NetworkSubscriptionContext> collection = new ArrayList<>();
            
            subscriptionQueue.drainTo(collection);
            
            return collection;
        }
        
        @Override
        public void setProtocolPublishType(ProtocolPublishType protocolPublishType) {
            
            this.protocolPublishType = protocolPublishType;
        }
        
        @Override
        public ProtocolPublishType getProtocolPublishType() {
            
            return this.protocolPublishType;
        }
        
    }
}
