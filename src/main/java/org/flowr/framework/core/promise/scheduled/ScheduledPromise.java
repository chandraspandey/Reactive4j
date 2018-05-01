package org.flowr.framework.core.promise.scheduled;

import org.flowr.framework.core.promise.Promise;

/**
 * Extends Promise to be honored as scheduled promise as a registration for a
 * future event.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ScheduledPromise<REQUEST,RESPONSE> extends Promise<REQUEST,RESPONSE>{

	/**
	 * Determines the schedule for execution
	 * @param snapshotScale
	 * @return
	 */
	public long determineSchedule(ScheduledProgressScale snapshotScale);
	
	
}
