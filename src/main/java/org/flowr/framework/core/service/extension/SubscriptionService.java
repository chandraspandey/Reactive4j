package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.context.SubscriptionContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.service.ServiceFrameworkComponent;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface SubscriptionService extends ServiceFrameworkComponent{
	
	public SubscriptionContext subscribeNotification(SubscriptionContext subscriptionContext) 
			throws ConfigurationException;
	
	public SubscriptionContext unsubscribeNotification(SubscriptionContext subscriptionContext)
			throws ConfigurationException;

	public SubscriptionContext changeNotificationSubscription(SubscriptionContext subscriptionContext)
			throws ConfigurationException;
	
	public static SubscriptionService getInstance() {
		
		return DefaultSubscriptionService.getInstance();
	}
	
	public class DefaultSubscriptionService{
		
		private static SubscriptionService subscriptionService = null;
		
		public static SubscriptionService getInstance() {
			
			if(subscriptionService == null) {
				subscriptionService = new SubscriptionServiceImpl();
			}
			
			return subscriptionService;
		}
		
	}
}
