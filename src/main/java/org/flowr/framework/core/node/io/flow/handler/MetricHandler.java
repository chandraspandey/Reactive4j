
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io.flow.handler;

import java.util.Optional;

import org.flowr.framework.core.node.io.flow.metric.MetricContext;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric;

public interface MetricHandler extends NetworkMetric{
    
    void registerMetricMapperHandler();
    
    Optional<IntegrationProcessResponse> handleMetricType(MetricContext metricContext); 

}
