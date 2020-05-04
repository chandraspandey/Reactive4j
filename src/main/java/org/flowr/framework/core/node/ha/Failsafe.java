
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.ha;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.Autonomic.ResponseCode;
import org.flowr.framework.core.promise.PromiseRequest;

public interface Failsafe{

    <V> V fallforward(FailsafeCallable<V> callable)  throws ConfigurationException;
    
    <V> V fallback(FailsafeCallable<V> callable,PromiseRequest promiseRequest,ResponseCode responseCode);
}
