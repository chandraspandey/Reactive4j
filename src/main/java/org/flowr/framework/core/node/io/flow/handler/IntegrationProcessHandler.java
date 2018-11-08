package org.flowr.framework.core.node.io.flow.handler;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

import org.flowr.framework.core.node.io.flow.metric.MetricContext;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.ProtocolPublishType;
import org.flowr.framework.core.node.io.flow.protocol.NetworkSubscriptionContext;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface IntegrationProcessHandler {

	public String getProcessHandlerName();
	
	public Optional<IntegrationProcessResponse> handle(IntegrationProcessContext integrationProcessContext) ;
	
	public Collection<MetricContext> getMetricQueueAsCollection();
	
	public Collection<NetworkSubscriptionContext> getSubscriptionQueueAsCollection();
	
	public void setProtocolPublishType(ProtocolPublishType protocolPublishType);
	
	public ProtocolPublishType getProtocolPublishType();
	
	class DefaultIntegrationProcessHandler implements IntegrationProcessHandler{

		private ProtocolPublishType protocolPublishType				= ProtocolPublishType.INTERNAL;
		private String integrationProcessHandlerName 				= DefaultIntegrationProcessHandler.class.getSimpleName();
		private static TransferQueue<MetricContext> metricQueue  	= new LinkedTransferQueue<MetricContext>();
		private static TransferQueue<NetworkSubscriptionContext> subscriptionQueue  = 
				new LinkedTransferQueue<NetworkSubscriptionContext>();
		
		@Override
		public String getProcessHandlerName() {

			return this.integrationProcessHandlerName;
		}


		@Override
		public Optional<IntegrationProcessResponse> handle(IntegrationProcessContext integrationProcessContext) {
						
			Optional<IntegrationProcessRequest> integrationProcessRequest   = integrationProcessContext.getHandleFor();
			Optional<IntegrationProcessResponse> integrationProcessResponse = integrationProcessContext.getHandleAs();
			
			LinkedTransferQueue<MetricContext> bufferMetricQueue = new LinkedTransferQueue<MetricContext>();
			LinkedTransferQueue<NetworkSubscriptionContext> bufferSubscriptionQueue 	
				= new LinkedTransferQueue<NetworkSubscriptionContext>();			
			
			if(integrationProcessContext.getMetricContext().isPresent() ) {
				
				metricQueue.add(integrationProcessContext.getMetricContext().get());						
				
				if(integrationProcessRequest.isPresent() && integrationProcessRequest.isPresent()) {
					
					Iterator<MetricContext>  iter = metricQueue.iterator();
					
					while(iter.hasNext()) {
					
						MetricContext q = iter.next();					
							
						if(integrationProcessRequest.get().getMetricTypeSet().contains(	q.getMetricType())) {
							
							Optional<SimpleEntry<Integer,Integer>> responseMinMaxLimit = 
									integrationProcessContext.getHandleFor().get().getResponseMinMaxLimit();
							
							if(responseMinMaxLimit.isPresent()) {
																
								if(
										metricQueue.size() 		 >= responseMinMaxLimit.get().getKey() &&
										bufferMetricQueue.size() <= responseMinMaxLimit.get().getValue()
										
								) {
									bufferMetricQueue.add(q);
								}
								
							}else {									
								bufferMetricQueue.add(q);	
							}							
							
							integrationProcessResponse.get().setMetricType(q.getMetricType());
							integrationProcessResponse.get().setMetricQueue(Optional.of(bufferMetricQueue));
						}else {
							metricQueue.drainTo(bufferMetricQueue);
							integrationProcessResponse.get().setMetricQueue(Optional.of(bufferMetricQueue));
						}
						
					}		
					
					metricQueue.removeAll(bufferMetricQueue);
				}				
			}
			
			if(integrationProcessContext.getNetworkSubscriptionContext().isPresent()) {
				
				subscriptionQueue.add(integrationProcessContext.getNetworkSubscriptionContext().get());
				
				if(integrationProcessRequest.isPresent() && integrationProcessRequest.isPresent()) {
					
					Iterator<NetworkSubscriptionContext> iter = subscriptionQueue.iterator();
				
					while(iter.hasNext()) {
							
						NetworkSubscriptionContext q = iter.next();
							
						if(integrationProcessRequest.get().getMetricTypeSet().contains(	q.getMetricType())) {
							
							Optional<SimpleEntry<Integer,Integer>> responseMinMaxLimit = 
									integrationProcessContext.getHandleFor().get().getResponseMinMaxLimit();
							
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
							
							integrationProcessResponse.get().setMetricType(q.getMetricType());
							integrationProcessResponse.get().setSubscriptionQueue(Optional.of(bufferSubscriptionQueue));
						}else {
							subscriptionQueue.drainTo(bufferSubscriptionQueue);
							integrationProcessResponse.get().setSubscriptionQueue(Optional.of(bufferSubscriptionQueue));
						}
					}

				}
				subscriptionQueue.removeAll(bufferSubscriptionQueue);
			}
			
			if(integrationProcessRequest.isPresent() && integrationProcessResponse.isPresent()) {
				
				integrationProcessResponse.get().setProperties(	integrationProcessRequest.get().getProperties());
				
			}else {
				integrationProcessResponse = Optional.empty();
			}
			
			//System.out.println("DefaultIntegrationProcessHandler : handle : "+integrationProcessResponse);
			
			return integrationProcessResponse;
		}
		

		@Override
		public Collection<MetricContext> getMetricQueueAsCollection(){
			
			Collection<MetricContext> collection = new ArrayList<MetricContext>();
			
			metricQueue.drainTo(collection);
			
			return collection;
		}
		
		@Override
		public Collection<NetworkSubscriptionContext> getSubscriptionQueueAsCollection(){
			
			Collection<NetworkSubscriptionContext> collection = new ArrayList<NetworkSubscriptionContext>();
			
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
