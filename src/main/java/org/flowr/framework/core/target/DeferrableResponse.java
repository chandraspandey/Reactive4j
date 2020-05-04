
/**
 * Defines DeferableResponse as a type which can support ProgressScale with 
 * Deferrable results. For long running programs asynchronous call may lookup the 
 * ProgressScale before extracting the Response of type RESPONSE. The Response  
 * can be generated at once or can be used as chunked response
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.target;

import org.flowr.framework.core.promise.deferred.ProgressScale;

public interface DeferrableResponse<R> {

    ProgressScale getProgressScale();
    
    R getResponse();
}
