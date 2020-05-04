
/**
 * Defines high level behavior for Service Provider
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service;

import org.flowr.framework.api.Provider;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.promise.PromiseRequest;
import org.flowr.framework.core.promise.PromiseResponse;

public interface ServiceProvider extends Service, Provider{
        
    void setServerSubscriptionIdentifier(String subscriptionIdentifier);

    PromiseResponse service(PromiseRequest promiseRequest) throws PromiseException;

}
