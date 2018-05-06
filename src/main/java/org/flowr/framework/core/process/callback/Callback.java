package org.flowr.framework.core.process.callback;

/**
 * Defines behavior for Callback with registration & callback instrumentation.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */


public interface Callback<T>{

	public void register(Callback<T> callback);
	
	public T doCallback(T t);
}
