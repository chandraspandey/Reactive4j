
/**
 * Defines extension for batch event publishing.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.flow;

import org.flowr.framework.core.notification.dispatcher.NotificationBufferQueue;

public interface BatchEventPublisher extends EventPublisher{
    
        
    void publishEvent(NotificationBufferQueue notificationBufferQueue); 
}
