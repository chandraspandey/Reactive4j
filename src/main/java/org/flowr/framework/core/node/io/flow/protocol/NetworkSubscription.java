package org.flowr.framework.core.node.io.flow.protocol;

import java.util.Optional;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import org.flowr.framework.core.node.io.flow.metric.NetworkMetric;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.MetricType;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.ProtocolPublishType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class NetworkSubscription implements Subscription{

	private Subscriber<ProtocolPublishType> subscriber;
	private Optional<NetworkSubscriptionContext> optionalSubscriptionContext;
	
	public NetworkSubscription(Subscriber<ProtocolPublishType> subscriber, Optional<NetworkSubscriptionContext> 
		optionalSubscriptionContext){
		
		this.subscriber 				= subscriber;
		this.optionalSubscriptionContext = optionalSubscriptionContext;
	}
	
	@Override
	public void cancel() {
		
		((NetworkMetric)this.subscriber).onCancel(
				new NetworkSubscriptionContext(
						MetricType.CANCEL, 
						optionalSubscriptionContext.get().getSeekTimeout(),
						optionalSubscriptionContext.get().getTimeUnit(),
						optionalSubscriptionContext.get().getProtocolPublishType(),
						optionalSubscriptionContext.get().getHandleFor(),
						optionalSubscriptionContext.get().getHandleAs()	
				));
	}

	@Override
	public void request(long seek) {
		
		if(optionalSubscriptionContext.isPresent()) {
			
			((NetworkMetric)this.subscriber).onSeek(
					new NetworkSubscriptionContext(
						MetricType.SEEK, 
						optionalSubscriptionContext.get().getSeekTimeout(),
						optionalSubscriptionContext.get().getTimeUnit(),
						optionalSubscriptionContext.get().getProtocolPublishType(),
						optionalSubscriptionContext.get().getHandleFor(),
						optionalSubscriptionContext.get().getHandleAs()					
					));
		}else {
			((NetworkMetric)this.subscriber).onSeek(
					new NetworkSubscriptionContext(
						MetricType.SEEK, 
						NetworkSubscriptionContext.DEFAULT_TIMEOUT,
						NetworkSubscriptionContext.DEFAULT_TIMEUNIT,
						optionalSubscriptionContext.get().getProtocolPublishType(),
						optionalSubscriptionContext.get().getHandleFor(),
						optionalSubscriptionContext.get().getHandleAs()	
					));
		}
		
		
	}

	public Optional<NetworkSubscriptionContext> getOptionalSubscriptionContext() {
		
		if(!optionalSubscriptionContext.isPresent()) {
			
			NetworkSubscriptionContext networkSubscriptionContext =new NetworkSubscriptionContext(
					MetricType.SEEK, 
					NetworkSubscriptionContext.DEFAULT_TIMEOUT,
					NetworkSubscriptionContext.DEFAULT_TIMEUNIT,
					optionalSubscriptionContext.get().getProtocolPublishType(),
					optionalSubscriptionContext.get().getHandleFor(),
					optionalSubscriptionContext.get().getHandleAs()	
				);
			
			optionalSubscriptionContext = Optional.of(networkSubscriptionContext);
			
		}
		return optionalSubscriptionContext;
	}
	


}