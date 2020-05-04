
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io.flow.metric;

import java.util.Optional;
import java.util.concurrent.Flow.Subscription;

import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessRequest;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessResponse;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.MetricType;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.ProtocolPublishType;

public class MetricContext {

    private MetricType metricType;
    private Optional<ChannelMetric> channelMetric;
    private Optional<Throwable> throwable;
    private Optional<Subscription> subscription; 
    private Optional<IntegrationProcessRequest> handleFor;
    private Optional<IntegrationProcessResponse> handleAs;
    private ProtocolPublishType protocolPublishType;
    
    
    public MetricContext(MetricContext that) {
        
        this.metricType         = that.metricType;
        this.channelMetric      = that.channelMetric;
        this.throwable          = that.throwable;
        this.subscription       = that.subscription;
        this.handleFor          = that.handleFor;
        this.handleAs           = that.handleAs;
        this.protocolPublishType= that.protocolPublishType;
    }
    
    public MetricContext(MetricType metricType,
            Optional<ChannelMetric> channelMetric,Optional<Throwable> throwable,
            Optional<Subscription> subscription,
            ProtocolPublishType protocolPublishType,
            Optional<IntegrationProcessRequest> handleFor,
            Optional<IntegrationProcessResponse> handleAs) {        
        
        this.metricType             = metricType;
        this.channelMetric          = channelMetric;
        this.throwable              = throwable;
        this.subscription           = subscription;
        this.protocolPublishType    = protocolPublishType;
        this.handleFor              = handleFor;
        this.handleAs               = handleAs;
    }

    public MetricType getMetricType() {
        return metricType;
    }

    public Optional<ChannelMetric> getChannelMetric() {
        return channelMetric;
    }

    public Optional<Throwable> getThrowable() {
        return throwable;
    }
    
    public Optional<Subscription> getSubscription() {
        return subscription;
    }
    
    public void setMetricType(MetricType metricType) {
        this.metricType = metricType;
    }

    public void setChannelMetric(Optional<ChannelMetric> channelMetric) {
        this.channelMetric = channelMetric;
    }

    public void setThrowable(Optional<Throwable> throwable) {
        this.throwable = throwable;
    }

    public void setSubscription(Optional<Subscription> subscription) {
        this.subscription = subscription;
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

    public ProtocolPublishType getProtocolPublishType() {
        return protocolPublishType;
    }

    public void setProtocolPublishType(ProtocolPublishType protocolPublishType) {
        this.protocolPublishType = protocolPublishType;
    }
    
    public String toString() {
        
        return "MetricContext { metricType : "+metricType+" | throwable : "+throwable+" | "+channelMetric+
                " | "+protocolPublishType+" | "+handleFor+" | "+handleAs+" | } ";
    }

}
