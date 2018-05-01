package org.flowr.framework.core.node;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface BackPressureWindow {

	public int getRetryCount();
	
	public long getRetryInterval();

	public long getTimeout();


}
