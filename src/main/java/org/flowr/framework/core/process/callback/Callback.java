
/**
 * Defines behavior for Callback with registration & callback instrumentation.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.process.callback;

public interface Callback<T>{

    void register(Callback<T> callback);
    
    T doCallback(T t);
}
