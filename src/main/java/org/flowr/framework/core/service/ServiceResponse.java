
/**
 * Marker response interface for Service Route Binding.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service;

import org.flowr.framework.core.target.ReactiveTarget;

public interface ServiceResponse {

    /**
     * Returns the Reactive Target that services the request, in distributed setup can be delegate server
     * @return
     */
    ReactiveTarget getReactiveTarget();
    
}
