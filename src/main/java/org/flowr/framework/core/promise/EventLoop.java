
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.promise;

import org.flowr.framework.core.exception.PromiseException;

public interface EventLoop {

    PromiseResponse iterate(PromiseRequest promiseRequest) throws PromiseException;
}
