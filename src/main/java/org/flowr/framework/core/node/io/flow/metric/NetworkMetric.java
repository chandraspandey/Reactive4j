package org.flowr.framework.core.node.io.flow.metric;

import java.util.Optional;
import java.util.concurrent.Flow.Subscriber;

import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessResponse;
import org.flowr.framework.core.node.io.flow.protocol.NetworkSubscriptionContext;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NetworkMetric{

	public enum ProtocolPublishType{
		INTERNAL,
		EXTERNAL
	}
	
	public enum MetricType{
		SUBSCRIPTION,
		COMPLETION,
		TRANSFER,
		CANCEL,
		SEEK,
		ERROR
	}
	
	public Optional<IntegrationProcessResponse> handleMetricType(MetricContext metricContext);
	
	public void onCancel(NetworkSubscriptionContext networkSubscriptionContext);

	public void onSeek(NetworkSubscriptionContext networkSubscriptionContext);
	
	public void setNetworkMetricSubscriber(Subscriber<? super ProtocolPublishType> networkMetricSubscriber);
	
	public Subscriber<? super ProtocolPublishType> getNetworkMetricSubscriber();
	
	public String getNetworkMetricSubscriberName();	
	
}
