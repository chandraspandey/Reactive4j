package org.flowr.framework.core.node;

import java.util.concurrent.Callable;

import org.flowr.framework.core.node.Autonomic.ResponseCode;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface FailsafeCallable<V> extends Callable<V> {
	
	public V handlePromiseError(ResponseCode responseCode);
	
	public V handleTimeout(ResponseCode responseCode);

	public V handleError(ResponseCode responseCode);

}
