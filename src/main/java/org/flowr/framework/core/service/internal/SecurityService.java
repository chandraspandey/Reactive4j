
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.internal;

import org.flowr.framework.core.security.SecurityServiceHandler;
import org.flowr.framework.core.service.FrameworkService;

public interface SecurityService extends FrameworkService{
    
    static SecurityService getInstance() {
        
        return DefaultSecurityService.getInstance();
    }
    
    SecurityServiceHandler getSecurityHandler();
    
    public final class DefaultSecurityService{
        
        private static SecurityService securityService;
        
        private DefaultSecurityService() {
            
        }
        
        public static SecurityService getInstance() {
            
            if(securityService == null) {
                securityService = new SecurityServiceImpl();
            }
            
            return securityService;
        }
        
    }
}
