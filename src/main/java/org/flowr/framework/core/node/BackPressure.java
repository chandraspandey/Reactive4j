package org.flowr.framework.core.node;

import java.util.concurrent.ExecutionException;
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface BackPressure {
	
	public <V> V react(FailsafeCallable<V> callable) throws InterruptedException, ExecutionException;
	
	public boolean isTimedOut();
}
