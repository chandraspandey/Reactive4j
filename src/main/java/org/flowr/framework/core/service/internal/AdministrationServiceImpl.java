
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.service.internal;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.service.AbstractService;
import org.flowr.framework.core.service.ServiceFrameworkComponent;
import org.flowr.framework.core.service.dependency.Dependency.DependencyType;

public class AdministrationServiceImpl extends AbstractService implements AdministrationService{
  
    private ServiceConfig serviceConfig         = new ServiceConfig(
                                                    true,
                                                    ServiceUnit.SINGELTON,
                                                    FrameworkConstants.FRAMEWORK_SERVICE_ADMINISTRATION,
                                                    ServiceType.ADMINISTRATION,
                                                    ServiceStatus.UNUSED,
                                                    this.getClass().getSimpleName(),
                                                    DependencyType.MANDATORY
                                                );

    @Override
    public ServiceConfig getServiceConfig() {
    
        return this.serviceConfig;
    }
    
    @Override
    public List<ServiceFrameworkComponent> getServiceList(){
        return this.getServiceFramework().getServiceList();
    }
 
    @Override
    public ServiceStatus startup(Optional<Properties> configProperties) {
        return ServiceStatus.STARTED;
    }

    @Override
    public ServiceStatus shutdown(Optional<Properties> configProperties) {
        return ServiceStatus.STOPPED;
    }
    
    @Override
    public String toString(){
        
        return "AdministrationService{"+
                " | serviceConfig : "+serviceConfig+    
                super.toString()+  
                "}\n";
    }

}
