package org.flowr.framework.core.notification;

import org.flowr.framework.core.context.NotificationContext;
import org.flowr.framework.core.service.extension.NotificationService.NotificationServiceStatus;


/**
 * Defines notification dispatching capabilities where the subscription is for NotificationFrequency other than ALL. 
 * This requires both the usage of persistent event as mechanism & capability for scheduled lookup with aggregation 
 * from persistent store.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NotificationDispatcher {

	public enum DispatcherType{
		INSTANT,
		SCHEDULED
	}
	
	public NotificationServiceStatus dispatch(NotificationContext notificationContext);
	
}
