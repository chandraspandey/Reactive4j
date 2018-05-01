package org.flowr.framework.core.event.pipeline;

import org.flowr.framework.core.notification.NotificationBufferQueue;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface EventBusExecutor {

	public NotificationBufferQueue process();
}
