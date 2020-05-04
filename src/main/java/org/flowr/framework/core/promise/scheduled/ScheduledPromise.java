
/**
 * Extends Promise to be honored as scheduled promise as a registration for a
 * future event.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.promise.scheduled;

import org.flowr.framework.core.flow.SingleEventPublisher;
import org.flowr.framework.core.promise.EventLoop;
import org.flowr.framework.core.promise.Promise;

public interface ScheduledPromise extends Promise, SingleEventPublisher,  EventLoop{

    /**
     * Determines the schedule for execution
     * @param snapshotScale
     * @return
     */
    long determineSchedule(ScheduledProgressScale snapshotScale);
    
    
}
