package org.flowr.framework.core.notification;

import org.flowr.framework.core.context.SubscriptionContext;
import org.flowr.framework.core.exception.ConfigurationException;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NotificationLifecycle {

	public SubscriptionContext register(SubscriptionContext subscriptionContext) throws ConfigurationException;
	
	public SubscriptionContext deregister(SubscriptionContext subscriptionContext) throws ConfigurationException;
	
	public SubscriptionContext change(SubscriptionContext subscriptionContext) throws ConfigurationException;
}
