package org.flowr.framework.core.subject;


/**
 * Defines DeferableRequest as a type which can return Request type.
 * The concrete implementation provides the underlying functionality. The
 * consumer of the API can extract the request of type REQUEST for processing. 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface DeferableRequest<REQUEST> {

	public REQUEST getRequest();
}
