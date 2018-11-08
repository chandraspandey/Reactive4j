package org.flowr.framework.core.node.io.flow.handler;

import java.util.AbstractMap.SimpleEntry;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Properties;

import org.flowr.framework.core.constants.NodeConstants;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.MetricType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface IntegrationProcessRequest {

	public void setProperties(Properties incomingProperties);
	
	public Properties getProperties();
	
	public Optional<SimpleEntry<Integer,Integer>> getResponseMinMaxLimit();

	public void setResponseMinMaxLimit(Optional<SimpleEntry<Integer,Integer>> responseMinMaxLimit);

	public EnumSet<MetricType> getMetricTypeSet();

	public void setMetricTypeSet(EnumSet<MetricType> metricTypeSet) ;
	
	public void addMetricType(MetricType metricType);
	
	
	class DefaultIntegrationProcessRequest implements IntegrationProcessRequest{
		
		private Properties incomingProperties 								= new Properties();
		private Optional<SimpleEntry<Integer,Integer>> responseMinMaxLimit	= Optional.of(
				new SimpleEntry<Integer,Integer>(
						NodeConstants.NODE_PIPELINE_RESPONSE_LIMIT_MIN,
						NodeConstants.NODE_PIPELINE_RESPONSE_LIMIT_MAX)); 
		private EnumSet<MetricType> metricTypeSet							= EnumSet.noneOf(MetricType.class);	
		
		@Override
		public void setProperties(Properties incomingProperties) {
			this.incomingProperties = incomingProperties;
		}

		@Override
		public Properties getProperties() {
			return this.incomingProperties;
		}

		@Override
		public Optional<SimpleEntry<Integer,Integer>> getResponseMinMaxLimit() {
			return responseMinMaxLimit;
		}

		@Override
		public void setResponseMinMaxLimit(Optional<SimpleEntry<Integer,Integer>> responseMinMaxLimit) {
			this.responseMinMaxLimit = responseMinMaxLimit;
		}
		
		@Override
		public EnumSet<MetricType> getMetricTypeSet() {
			return metricTypeSet;
		}
		
		@Override
		public void setMetricTypeSet(EnumSet<MetricType> metricTypeSet) {
			this.metricTypeSet = metricTypeSet;		
		}	
		
		@Override
		public void addMetricType(MetricType metricType) {
			metricTypeSet.add(metricType);			
		}	
		
		public String toString() {
			
			return "DefaultIntegrationProcessRequest {  "+
						metricTypeSet+" | "+
						responseMinMaxLimit+" | "+
						incomingProperties+"  } ";
		}
	}
}
