
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.service.FrameworkService;

public interface StagePromiseService extends FrameworkService{

   static StagePromiseService getInstance() {
        
        return DefaultStagedPromiseService.getInstance();
    }
    
    public final class DefaultStagedPromiseService{
        
        private static StagePromiseService stagedPromiseService;
        
        private DefaultStagedPromiseService() {
            
        }
        
        public static StagePromiseService getInstance() {
            
            if(stagedPromiseService == null) {
                stagedPromiseService = new StagePromiseServiceImpl();
            }
            
            return stagedPromiseService;
        }
        
    }
}
