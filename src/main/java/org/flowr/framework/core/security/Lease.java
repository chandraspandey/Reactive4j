package org.flowr.framework.core.security;

import java.util.Calendar;

/**
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Lease {

	public Calendar leaseStartTime();
	
	public Calendar leaseEndTime();
}
