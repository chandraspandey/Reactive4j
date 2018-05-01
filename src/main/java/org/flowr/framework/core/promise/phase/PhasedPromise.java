package org.flowr.framework.core.promise.phase;

import java.util.Collection;
import java.util.concurrent.Delayed;

import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.promise.Promise;


/**
 * Extends Promise to be honored as phased promise.
 * Defines a type which can support scheduled results that can be delivered as
 * chunked results to reduce the transportation & handling pay loads both for 
 * sender & the receiver. The calibration can be instrumented either by 
 * ScheduledState or ScheduledInterval as unit or a combination of both.
 * Long running programs asynchronous call may lookup the ScheduledState or
 * ScheduledInterval before extracting the Response of type CHUNKED_RESPONSE.  
 * Concrete implementation provides the support for CHUNKED_RESPONSE. 
 * This may be dynamically handled by the client program such as loading of 
 * Thumbnails/media in the GUI first & then the actual size/media versions. 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface PhasedPromise<REQUEST,RESPONSE> extends Promise<REQUEST,RESPONSE>{

	
	/**
	 * Returns accumulated chunk types & performs the completion activities when 
	 * all the phases are executed. It may be used for consuming the genearted
	 * outputs as necessary by the end user client.
	 * @throws PromiseException
	 */
	public Collection<Delayed> done() throws PromiseException;		

}
