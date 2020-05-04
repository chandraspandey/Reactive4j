
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.service.FrameworkService;

public interface StreamPromiseService extends FrameworkService{

    static StreamPromiseService getInstance() {
        
        return DefaultStreamPromiseService.getInstance();
    }
    
    public final class DefaultStreamPromiseService{
        
        private static StreamPromiseService streamPromiseService;
        
        private DefaultStreamPromiseService() {
            
        }
        
        public static StreamPromiseService getInstance() {
            
            if(streamPromiseService == null) {
                streamPromiseService = new StreamPromiseServiceImpl();
            }
            
            return streamPromiseService;
        }
        
    }
}
