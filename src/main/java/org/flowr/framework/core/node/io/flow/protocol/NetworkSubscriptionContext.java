
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io.flow.protocol;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.constants.Constant.NodeConstants;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessRequest;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessResponse;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.MetricType;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.ProtocolPublishType;

public class NetworkSubscriptionContext{
    
    public static final long DEFAULT_TIMEOUT        = NodeConstants.NODE_PIPELINE_CLIENT_SEEK_TIMEOUT;
    public static final TimeUnit DEFAULT_TIMEUNIT   = NodeConstants.NODE_PIPELINE_CLIENT_SEEK_TIMEUNIT;
    private MetricType metricType;
    private long seekTimeout                        = -1;   
    private TimeUnit timeUnit                       = NodeConstants.NODE_PIPELINE_CLIENT_SEEK_TIMEUNIT;
    private Optional<IntegrationProcessRequest> handleFor;
    private Optional<IntegrationProcessResponse> handleAs;
    private ProtocolPublishType protocolPublishType;

    public NetworkSubscriptionContext(
            MetricType metricType,
            long seekTimeout,
            TimeUnit timeUnit,
            ProtocolPublishType protocolPublishType,
            Optional<IntegrationProcessRequest> handleFor,
            Optional<IntegrationProcessResponse> handleAs           
            ) {
        
        this.metricType         = metricType;
        this.seekTimeout        = seekTimeout;
        this.timeUnit           = timeUnit;
        this.protocolPublishType= protocolPublishType;
        this.handleFor          = handleFor;
        this.handleAs           = handleAs;
    }
    
    public MetricType getMetricType() {
        return metricType;
    }


    public long getSeekTimeout() {
        return seekTimeout;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
    public Optional<IntegrationProcessRequest> getHandleFor() {
        return handleFor;
    }

    public Optional<IntegrationProcessResponse> getHandleAs() {
        return handleAs;
    }

    public ProtocolPublishType getProtocolPublishType() {
        return protocolPublishType;
    }
    
    public String toString() {
        
        return "NetworkSubscriptionContext { metricType : "+metricType+" | seekTimeout : "+seekTimeout+" | "+timeUnit+
                " | "+protocolPublishType+" | "+handleFor+" | "+handleAs+
                " | } ";
    }


}
