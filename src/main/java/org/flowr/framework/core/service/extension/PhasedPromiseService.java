
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.promise.phase.PhasedPromise;
import org.flowr.framework.core.service.FrameworkService;

public interface PhasedPromiseService extends FrameworkService{

    PhasedPromise getPromise();
    
    static PhasedPromiseService getInstance() {
        
        return DefaultPhasedPromiseService.getInstance();
    }
    
    public final class DefaultPhasedPromiseService{
        
        private static PhasedPromiseService phasedPromiseService;
    
        private DefaultPhasedPromiseService() {
            
        }
        
        public static PhasedPromiseService getInstance() {
            
            if(phasedPromiseService == null) {
                phasedPromiseService = new PhasedPromiseServiceImpl();
            }
            
            return phasedPromiseService;
        }
        
    }
}
