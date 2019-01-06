package org.flowr.framework.core.node.io.flow.metric;

import java.util.Optional;
import java.util.concurrent.Flow.Subscriber;

import org.flowr.framework.core.node.io.flow.data.binary.ByteEnumerableType;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessResponse;
import org.flowr.framework.core.node.io.flow.protocol.NetworkSubscriptionContext;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NetworkMetric{

	/**
	 * 
	 * Defines high level metric publication type for notification flow classification & handling
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum ProtocolPublishType implements ByteEnumerableType{
		NONE(0),
		INTERNAL(1),
		EXTERNAL(2);
		
		private byte code = 0;
		
		ProtocolPublishType(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static ProtocolPublishType getType(int code) {
			
			ProtocolPublishType protocolPublishType = NONE;
			
			switch((byte) code) {
				
				case 0:{
					protocolPublishType = NONE;
					break;
				}case 1:{
					protocolPublishType = INTERNAL;
					break;
				}case 2:{
					protocolPublishType = EXTERNAL;
					break;
				}default :{
					protocolPublishType = NONE;
					break;
				}			
			}
			
			return protocolPublishType;
		}
	}
	
	/**
	 * 
	 * Defines metric  type for notification metric classification & handling
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum MetricType implements ByteEnumerableType{
		NONE(0),
		SUBSCRIPTION(1),
		COMPLETION(2),
		TRANSFER(3),
		CANCEL(4),
		SEEK(5),
		ERROR(6);
		
		private byte code = 0;
		
		MetricType(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static MetricType getType(int code) {
			
			MetricType metricType = NONE;
			
			switch((byte) code) {
				
				case 0:{
					metricType = NONE;
					break;
				}case 1:{
					metricType = SUBSCRIPTION;
					break;
				}case 2:{
					metricType = COMPLETION;
					break;
				}case 3:{
					metricType = TRANSFER;
					break;
				}case 4:{
					metricType = CANCEL;
					break;
				}case 5:{
					metricType = SEEK;
					break;
				}case 6:{
					metricType = ERROR;
					break;
				}default :{
					metricType = NONE;
					break;
				}			
			}
			
			return metricType;
		}
	}
	
	public Optional<IntegrationProcessResponse> handleMetricType(MetricContext metricContext);
	
	public void onCancel(NetworkSubscriptionContext networkSubscriptionContext);

	public void onSeek(NetworkSubscriptionContext networkSubscriptionContext);
	
	public void setNetworkMetricSubscriber(Subscriber<? super ProtocolPublishType> networkMetricSubscriber);
	
	public Subscriber<? super ProtocolPublishType> getNetworkMetricSubscriber();
	
	public String getNetworkMetricSubscriberName();	
	
}
