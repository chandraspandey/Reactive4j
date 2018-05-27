package org.flowr.framework.core.notification;

import java.util.HashMap;

import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.service.internal.NotificationService.NotificationServiceStatus;


/**
 * Defines notification dispatching capabilities where the subscription is for NotificationFrequency other than ALL. 
 * This requires both the usage of persistent event as mechanism & capability for scheduled lookup with aggregation 
 * from persistent store.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NotificationDispatcher {

	public enum DispatcherType{
		INSTANT,
		SCHEDULED
	}
	
	public HashMap<NotificationProtocolType, NotificationServiceStatus> dispatch(NotificationBufferQueue notificationBufferQueue);
	
}
