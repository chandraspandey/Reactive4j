
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.service.internal;

import java.util.List;

import org.flowr.framework.core.service.FrameworkService;
import org.flowr.framework.core.service.ServiceFrameworkComponent;

public interface AdministrationService extends FrameworkService{
        
    static AdministrationService getInstance() {
        
        return DefaultConfigurationService.getInstance();
    }
    
    List<ServiceFrameworkComponent> getServiceList();
    
    public final class DefaultConfigurationService{
        
        private static AdministrationService administrationrationService;
        
        private DefaultConfigurationService() {
            
        }
        
        public static AdministrationService getInstance() {
            
            if(administrationrationService == null) {
                administrationrationService = new AdministrationServiceImpl();
            }
            
            return administrationrationService;
        }
        
    }
}
