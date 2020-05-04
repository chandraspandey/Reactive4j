
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
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.promise.phase;

import org.flowr.framework.core.flow.SingleEventPublisher;
import org.flowr.framework.core.promise.EventLoop;
import org.flowr.framework.core.promise.Promise;

public interface PhasedPromise extends Promise, SingleEventPublisher, EventLoop{

}
