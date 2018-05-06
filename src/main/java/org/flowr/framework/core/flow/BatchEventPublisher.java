package org.flowr.framework.core.flow;

import org.flowr.framework.core.notification.NotificationBufferQueue;

/**
 * Defines extension for batch event publishing.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */


public interface BatchEventPublisher extends EventPublisher{
	
		
	public void publishEvent(NotificationBufferQueue notificationBufferQueue); 
	
	
}
