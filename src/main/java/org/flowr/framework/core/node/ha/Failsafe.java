package org.flowr.framework.core.node.ha;

import java.util.concurrent.ExecutionException;

import org.flowr.framework.core.node.Autonomic.ResponseCode;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Failsafe{

	public <V> V fallforward(FailsafeCallable<V> callable)  throws InterruptedException, 
		ExecutionException;
	
	public <V> V fallback(FailsafeCallable<V> callable,ResponseCode responseCode);
}
