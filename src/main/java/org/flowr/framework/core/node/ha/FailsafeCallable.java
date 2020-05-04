
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.node.ha;

import java.util.concurrent.Callable;

import org.flowr.framework.core.node.Autonomic.ResponseCode;
import org.flowr.framework.core.promise.PromiseRequest;

public interface FailsafeCallable<V> extends Callable<V> {

    V handlePromiseError(PromiseRequest promiseRequest,ResponseCode responseCode);

    V handleTimeout(PromiseRequest promiseRequest, ResponseCode responseCode);

    V handleError(PromiseRequest promiseRequest, ResponseCode responseCode);

}
