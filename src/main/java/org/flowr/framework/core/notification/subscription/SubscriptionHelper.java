package org.flowr.framework.core.notification.subscription;

import org.flowr.framework.core.context.SubscriptionContext;
import org.flowr.framework.core.exception.ConfigurationException;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class SubscriptionHelper implements Subscription{

	private static SubscriptionRegistry subscriptionRegistry= new SubscriptionRegistry();
	
	public SubscriptionContext subscribe(SubscriptionContext subscriptionContext) throws ConfigurationException{
		
		return subscriptionRegistry.register(subscriptionContext);
	}
	
	public SubscriptionContext unsubscribe(SubscriptionContext subscriptionContext) throws ConfigurationException{
		
		return subscriptionRegistry.deregister(subscriptionContext);
	}	
	
	@Override
	public NotificationSubscription lookupNotificationSubscription(String subscriptionId){
		
		return subscriptionRegistry.lookup(subscriptionId);
	}
	

	@Override
	public SubscriptionContext onSubscriptionChange(SubscriptionContext subscriptionContext) throws ConfigurationException {

		return subscriptionRegistry.change(subscriptionContext);
	}
}
