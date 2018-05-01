package org.flowr.framework.core.service;


/**
 * Marker request interface for Service Route Binding.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ServiceRequest {

	public enum RequestType{
		PROMISE,
		PROMISE_DEFFERED,
		PROMISE_PHASED,
		PROMISE_SCHEDULED,
		PROMISE_STAGED
	}
	
	public void setRequestType(RequestType requestType);
	
	public RequestType getRequestType();
}
