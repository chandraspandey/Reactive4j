package org.flowr.framework.core.promise;

import java.util.concurrent.ExecutionException;

import org.flowr.framework.core.exception.PromiseException;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface EventLoop<REQUEST,RESPONSE> {

	public PromiseResponse<RESPONSE> iterate(PromiseRequest<REQUEST> promiseRequest) throws PromiseException, 
	InterruptedException, ExecutionException;
}
