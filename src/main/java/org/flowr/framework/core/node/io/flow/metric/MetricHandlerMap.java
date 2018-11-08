package org.flowr.framework.core.node.io.flow.metric;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessContext;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessHandler;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessResponse;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.MetricType;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.ProtocolPublishType;

public class MetricHandlerMap extends HashMap<IntegrationProcessHandler,MetricType>{

	private static final long serialVersionUID = 1L;
	
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
	
	
	public Optional<IntegrationProcessResponse> delegate(IntegrationProcessContext integrationProcessContext) {
		
		Optional<IntegrationProcessResponse> handleAs = Optional.empty();		
		
		IntegrationProcessHandler integrationProcessHandler = this.get(
																integrationProcessContext.getMetricType(), 
																integrationProcessContext.getProtocolPublishType());
				
		
		//System.out.println(" MetricHandlerMap : "+integrationProcessContext);
		
		switch(integrationProcessContext.getMetricType()) {
			
			case CANCEL:{
				
				if(integrationProcessContext.getNetworkSubscriptionContext().isPresent()) {
					handleAs = integrationProcessHandler.handle(integrationProcessContext);
				}
				break;
			}case COMPLETION:{
				
				if(integrationProcessContext.getMetricContext().isPresent()) {
					handleAs = integrationProcessHandler.handle(integrationProcessContext);
				}
				break;
			}case ERROR:{
				
				if(integrationProcessContext.getMetricContext().isPresent()) {
					handleAs = integrationProcessHandler.handle(integrationProcessContext);
				}
				break;
			}case SEEK:{
				
				if(integrationProcessContext.getNetworkSubscriptionContext().isPresent()) {
					handleAs = integrationProcessHandler.handle(integrationProcessContext);							
				}
				break;
			}case SUBSCRIPTION:{
				
				if(integrationProcessContext.getMetricContext().isPresent()) {
					handleAs = integrationProcessHandler.handle(integrationProcessContext);
				}
				break;
			}case TRANSFER:{
				
				if(integrationProcessContext.getMetricContext().isPresent()) {
					handleAs = integrationProcessHandler.handle(integrationProcessContext);
				}
				break;
			}default:{
				break;
			}		
			
		}
		
		return handleAs;
	}
}
