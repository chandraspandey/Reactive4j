
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.internal;

import java.util.Optional;
import java.util.Properties;

import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.service.AbstractService;
import org.flowr.framework.core.service.dependency.Dependency.DependencyStatus;
import org.flowr.framework.core.service.dependency.Dependency.DependencyType;
import org.flowr.framework.core.service.dependency.DependencyLoop;

public class SecurityServiceImpl extends AbstractService implements SecurityService, DependencyLoop{

    private ServiceConfig serviceConfig             = new ServiceConfig(
                                                        true,
                                                        ServiceUnit.SINGELTON,
                                                        FrameworkConstants.FRAMEWORK_SERVICE_SECURITY,
                                                        ServiceType.SECURITY,
                                                        ServiceStatus.UNUSED,
                                                        this.getClass().getSimpleName(),
                                                        DependencyType.MANDATORY
                                                    );

    @Override
    public ServiceConfig getServiceConfig() {    
        return this.serviceConfig;
    }
 
    @Override
    public DependencyStatus verify() {
        
        DependencyStatus status = DependencyStatus.UNSATISFIED;
        
        if(this.getSecurityHandler() != null) {
            
            status = DependencyStatus.SATISFIED;
        }
        
        return status;
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
        
        return "SecurityService{"+
                " | serviceConfig : "+serviceConfig+    
                super.toString()+  
                "}\n";
    }
 
}
