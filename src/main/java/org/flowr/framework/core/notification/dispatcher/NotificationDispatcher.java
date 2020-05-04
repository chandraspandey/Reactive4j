
/**
 * Defines notification dispatching capabilities where the subscription is for NotificationFrequency other than ALL. 
 * This requires both the usage of persistent event as mechanism & capability for scheduled lookup with aggregation 
 * from persistent store.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.notification.dispatcher;

import java.util.Map;

import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.service.internal.NotificationService.NotificationServiceStatus;

public interface NotificationDispatcher {

    public enum DispatcherType{
        INSTANT,
        SCHEDULED
    }
    
    Map<NotificationProtocolType, NotificationServiceStatus> dispatch(NotificationBufferQueue queue);
    
}
