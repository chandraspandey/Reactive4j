
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.event.pipeline;

import org.flowr.framework.core.exception.ClientException;
import org.flowr.framework.core.notification.dispatcher.NotificationBufferQueue;

public interface EventBusExecutor {

    NotificationBufferQueue process() throws ClientException;
}
