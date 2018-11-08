package org.flowr.framework.core.node.io.flow.handler;

import java.util.Optional;

import org.flowr.framework.core.node.io.flow.metric.MetricContext;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.MetricType;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.ProtocolPublishType;
import org.flowr.framework.core.node.io.flow.protocol.NetworkSubscriptionContext;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class IntegrationProcessContext{
	
	private MetricType metricType;
	private Optional<MetricContext> metricContext;
	private Optional<NetworkSubscriptionContext> networkSubscriptionContext;
	private Optional<IntegrationProcessRequest> handleFor;
	private Optional<IntegrationProcessResponse> handleAs;
	private ProtocolPublishType protocolPublishType;
	
	public IntegrationProcessContext() {}
	
	public IntegrationProcessContext(ProtocolPublishType protocolPublishType, MetricType metricType, 
			Optional<MetricContext> metricContext,
			Optional<NetworkSubscriptionContext> networkSubscriptionContext, 
			Optional<IntegrationProcessRequest> handleFor,
			Optional<IntegrationProcessResponse> handleAs
			) {
		
		this.protocolPublishType		= protocolPublishType;
		this.metricType 				= metricType;
		this.metricContext				= metricContext;
		this.networkSubscriptionContext = networkSubscriptionContext;
		this.handleFor					= handleFor;
		this.handleAs					= handleAs;
	}

	public Optional<MetricContext> getMetricContext() {
		return this.metricContext;
	}
	

	public Optional<NetworkSubscriptionContext> getNetworkSubscriptionContext() {
		return networkSubscriptionContext;
	}

	public void setNetworkSubscriptionContext(Optional<NetworkSubscriptionContext> networkSubscriptionContext) {
		this.networkSubscriptionContext = networkSubscriptionContext;
	}

	public void setMetricContext(Optional<MetricContext> metricContext) {
		this.metricContext = metricContext;
	}
	
	public Optional<IntegrationProcessRequest> getHandleFor() {
		return handleFor;
	}

	public void setHandleFor(Optional<IntegrationProcessRequest> handleFor) {
		this.handleFor = handleFor;
	}

	public ProtocolPublishType getProtocolPublishType() {
		return protocolPublishType;
	}

	public void setProtocolPublishType(ProtocolPublishType protocolPublishType) {
		this.protocolPublishType = protocolPublishType;
	}
	
	public MetricType getMetricType() {
		return metricType;
	}

	public void setMetricType(MetricType metricType) {
		this.metricType = metricType;
	}
	
	public Optional<IntegrationProcessResponse> getHandleAs() {
		return handleAs;
	}

	public void setHandleAs(Optional<IntegrationProcessResponse> handleAs) {
		this.handleAs = handleAs;
	}	

	public String toString() {
		
		return "IntegrationProcessContext {  "+
					networkSubscriptionContext+" | "+
					metricContext+" | "+
					protocolPublishType+" | "+
					handleFor+" | "+
					handleAs+" | "+
					metricType+"  } ";
	}

}
