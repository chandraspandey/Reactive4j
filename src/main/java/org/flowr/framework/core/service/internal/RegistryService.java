
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.internal;

import org.flowr.framework.core.service.FrameworkService;

public interface RegistryService extends FrameworkService{

    static RegistryService getInstance() {
        
        return DefaultRegistryService.getInstance();
    }
    
    public final class DefaultRegistryService{
        
        private static RegistryService registryService;

        private DefaultRegistryService() {
            
        }

        public static RegistryService getInstance() {
            
            if(registryService == null) {
                registryService = new RegistryServiceImpl();
            }
            
            return registryService;
        }
        
    }
}
