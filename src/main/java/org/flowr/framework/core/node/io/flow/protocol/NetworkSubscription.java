
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io.flow.protocol;

import java.util.Optional;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import org.flowr.framework.core.node.io.flow.metric.NetworkMetric;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.MetricType;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.ProtocolPublishType;

public class NetworkSubscription implements Subscription{

    private Subscriber<ProtocolPublishType> subscriber;
    private Optional<NetworkSubscriptionContext> contextOption;
    
    public NetworkSubscription(Subscriber<ProtocolPublishType> subscriber, Optional<NetworkSubscriptionContext> 
        contextOption){
        
        this.subscriber    = subscriber;
        this.contextOption = contextOption;
    }
    
    @Override
    public void cancel() {
        
        if(contextOption.isPresent()) {
        
            ((NetworkMetric)this.subscriber).onCancel(
                new NetworkSubscriptionContext(
                        MetricType.CANCEL, 
                        contextOption.get().getSeekTimeout(),
                        contextOption.get().getTimeUnit(),
                        contextOption.get().getProtocolPublishType(),
                        contextOption.get().getHandleFor(),
                        contextOption.get().getHandleAs() 
                ));
            
        }else {
            
            ((NetworkMetric)this.subscriber).onCancel(
                new NetworkSubscriptionContext(
                        MetricType.CANCEL, 
                        NetworkSubscriptionContext.DEFAULT_TIMEOUT,
                        NetworkSubscriptionContext.DEFAULT_TIMEUNIT,
                        null,
                        Optional.empty(),
                        Optional.empty()
                ));
        }
    }

    @Override
    public void request(long seek) {
        
        if(contextOption.isPresent()) {
            
            ((NetworkMetric)this.subscriber).onSeek(
                    new NetworkSubscriptionContext(
                        MetricType.SEEK, 
                        contextOption.get().getSeekTimeout(),
                        contextOption.get().getTimeUnit(),
                        contextOption.get().getProtocolPublishType(),
                        contextOption.get().getHandleFor(),
                        contextOption.get().getHandleAs()                 
                    ));
        }else {
            ((NetworkMetric)this.subscriber).onSeek(
                    new NetworkSubscriptionContext(
                        MetricType.SEEK, 
                        NetworkSubscriptionContext.DEFAULT_TIMEOUT,
                        NetworkSubscriptionContext.DEFAULT_TIMEUNIT,
                        null,
                        Optional.empty(),
                        Optional.empty()
                    ));
        }
        
        
    }

    public Optional<NetworkSubscriptionContext> getOptionalSubscriptionContext() {
        
        if(contextOption.isPresent()) {
            
            NetworkSubscriptionContext networkSubscriptionContext =new NetworkSubscriptionContext(
                    MetricType.SEEK, 
                    NetworkSubscriptionContext.DEFAULT_TIMEOUT,
                    NetworkSubscriptionContext.DEFAULT_TIMEUNIT,
                    contextOption.get().getProtocolPublishType(),
                    contextOption.get().getHandleFor(),
                    contextOption.get().getHandleAs() 
                );
            
            contextOption = Optional.of(networkSubscriptionContext);
            
        }
        return contextOption;
    }
}
