
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.promise.scheduled.ScheduledPromise;
import org.flowr.framework.core.service.FrameworkService;

public interface ScheduledPromiseService extends FrameworkService{

    ScheduledPromise getPromise();
    
    static ScheduledPromiseService getInstance() {
        
        return DefaultPromiseService.getInstance();
    }
    
    public final class DefaultPromiseService{
        
        private static ScheduledPromiseService scheduledPromiseService;
        
        private DefaultPromiseService() {
            
        }
        
        public static ScheduledPromiseService getInstance() {
            
            if(scheduledPromiseService == null) {
                scheduledPromiseService = new ScheduledPromiseServiceImpl();
            }
            
            return scheduledPromiseService;
        }
        
    }
}
