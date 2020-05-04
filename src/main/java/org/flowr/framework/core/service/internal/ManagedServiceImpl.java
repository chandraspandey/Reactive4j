
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
import org.flowr.framework.core.process.management.ManagedRegistry;
import org.flowr.framework.core.process.management.ProcessHandler;
import org.flowr.framework.core.service.AbstractService;
import org.flowr.framework.core.service.dependency.Dependency.DependencyStatus;
import org.flowr.framework.core.service.dependency.Dependency.DependencyType;
import org.flowr.framework.core.service.dependency.DependencyLoop;

public class ManagedServiceImpl extends AbstractService implements ManagedService, DependencyLoop{

    private static ManagedRegistry managedRegistry  = new ManagedRegistry();
    
    private ServiceConfig serviceConfig             = new ServiceConfig(
                                                        true,
                                                        ServiceUnit.POOL,
                                                        FrameworkConstants.FRAMEWORK_SERVICE_MANAGEMENT,
                                                        ServiceType.MANAGEMENT,
                                                        ServiceStatus.UNUSED,
                                                        this.getClass().getSimpleName(),
                                                        DependencyType.MANDATORY
                                                    );

    @Override
    public ServiceConfig getServiceConfig() {    
        return this.serviceConfig;
    }
    
    public void put(String name, ProcessHandler handler) {
        managedRegistry.bind(name, handler);        
    }
    
    public ProcessHandler get(String name) {
        
        return managedRegistry.lookup(name);
    }
    

    @Override
    public DependencyStatus verify() {
 
        DependencyStatus status = DependencyStatus.UNSATISFIED;
        
        if(!managedRegistry.list().isEmpty()) {
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
        
        return "ManagedService{"+
                " | serviceConfig : "+serviceConfig+    
                " | \n managedRegistry : "+managedRegistry+
                super.toString()+  
                "}\n";
    }
}
