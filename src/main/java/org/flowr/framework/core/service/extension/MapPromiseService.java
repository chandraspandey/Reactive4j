
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.service.FrameworkService;

public interface MapPromiseService extends FrameworkService{
        
    static MapPromiseService getInstance() {
        
        return DefaultMapPromiseService.getInstance();
    }
    
    public final class DefaultMapPromiseService{
        
        private static MapPromiseService mapPromiseService;
        
        private DefaultMapPromiseService() {
            
        }
        
        public static MapPromiseService getInstance() {
            
            if(mapPromiseService == null) {
                mapPromiseService = new MapPromiseServiceImpl();
            }
            
            return mapPromiseService;
        }
        
    }
}
