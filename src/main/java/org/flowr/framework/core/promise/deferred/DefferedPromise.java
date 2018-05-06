package org.flowr.framework.core.promise.deferred;

import org.flowr.framework.core.promise.Promise;

/**
 * Provides a wrapper analogy of IF-THEN-WHAT for asynchronous call using 
 * Future<PromiseResponse>
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface DefferedPromise<REQUEST,RESPONSE> extends Promise<REQUEST,RESPONSE>{

	
}
