
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io.flow.handler;

import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TransferQueue;

import org.flowr.framework.core.node.io.flow.metric.MetricContext;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.MetricType;
import org.flowr.framework.core.node.io.flow.protocol.NetworkSubscriptionContext;

public interface IntegrationProcessResponse {

    /**
     * IntegrationProcessRequest : incomingProperties are recycled back to the caller for precise trace ability.
     * @param outgoingProperties
     */
    void setProperties(Properties outgoingProperties);
    
    Properties getProperties();
    
    void setMetricType(MetricType metricType);
    
    MetricType getMetricType();
    
    void setMetricQueue(Optional<TransferQueue<MetricContext>> metricQueue);
    
    Optional<TransferQueue<MetricContext>> getMetricQueue();
    
    void setSubscriptionQueue(Optional<TransferQueue<NetworkSubscriptionContext>> subscriptionQueue);
    
    Optional<TransferQueue<NetworkSubscriptionContext>> getSubscriptionQueue();
    
    class DefaultIntegrationProcessResponse implements IntegrationProcessResponse{
        
        private Properties outgoingProperties                                           = new Properties();
        private MetricType metricType;
        private Optional<TransferQueue<MetricContext>>  metricQueue                     = Optional.empty();
        private Optional<TransferQueue<NetworkSubscriptionContext>> subscriptionQueue   = Optional.empty();
        
        @Override
        public void setProperties(Properties outgoingProperties) {
            this.outgoingProperties = outgoingProperties;
        }

        @Override
        public Properties getProperties() {
            return this.outgoingProperties;
        }   
        
        @Override
        public void setMetricQueue(Optional<TransferQueue<MetricContext>> metricQueue) {            
            this.metricQueue = metricQueue;
        }
        
        @Override
        public Optional<TransferQueue<MetricContext>> getMetricQueue(){         
            return this.metricQueue;
        }
        
        @Override
        public void setSubscriptionQueue(Optional<TransferQueue<NetworkSubscriptionContext>> subscriptionQueue) {
            this.subscriptionQueue = subscriptionQueue;
        }
        
        @Override
        public Optional<TransferQueue<NetworkSubscriptionContext>> getSubscriptionQueue(){          
            return this.subscriptionQueue;
        }

        @Override
        public void setMetricType(MetricType metricType) {
            this.metricType = metricType;
        }

        @Override
        public MetricType getMetricType() {
            return this.metricType;
        }
        
        public String toString() {
            
            return "DefaultIntegrationProcessResponse {  "+
                        metricType+" | metricQueue : "+
                        metricQueue.isPresent()+
                        " | subscriptionQueue : "+
                        subscriptionQueue.isPresent()+" | "+
                        outgoingProperties+"  } ";
        }
    }
}
