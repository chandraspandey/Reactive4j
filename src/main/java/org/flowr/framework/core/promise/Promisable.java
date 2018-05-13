package org.flowr.framework.core.promise;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.PromiseException;

/**
 * Defines Promisable as a type which can return PromiseResponse on invocation.
 * The concrete implementation provides the underlying functionality. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Promisable<REQUEST,RESPONSE> {

	public enum PromisableType{
		PROMISE,
		PROMISE_DEFFERED,
		PROMISE_PHASED,
		PROMISE_SCHEDULED,
		PROMISE_STAGED
	}
	
	/**
	 * Provides functional support for generating PromiseResponse
	 * @return
	 * @throws PromiseException 
	 * @throws ConfigurationException 
	 */
	public PromiseResponse<RESPONSE> honorPromise() throws PromiseException, ConfigurationException;
	
}
