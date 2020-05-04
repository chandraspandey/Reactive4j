
/**
 * Defines Promise behavior for processor functionality.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.promise.deferred.DefferedPromise;
import org.flowr.framework.core.service.FrameworkService;

public interface DefferedPromiseService extends FrameworkService{
    
    DefferedPromise getPromise();
    
    static DefferedPromiseService getInstance() {
        
        return DefaultPromiseService.getInstance();
    }
    
    public final class DefaultPromiseService{
        
        private static DefferedPromiseService promiseService;

        private DefaultPromiseService() {
            
        }
        
        public static DefferedPromiseService getInstance() {
            
            if(promiseService == null) {
                promiseService = new DefferedPromiseServiceImpl();
            }
            
            return promiseService;
        }
        
    }
}
