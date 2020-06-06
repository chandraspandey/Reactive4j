
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.extension;

import java.util.Optional;
import java.util.Properties;

import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.service.AbstractService;
import org.flowr.framework.core.service.dependency.Dependency.DependencyType;

public class StreamPromiseServiceImpl extends AbstractService implements StreamPromiseService{
     
    private ServiceConfig serviceConfig     = new ServiceConfig(
                                                true,
                                                ServiceUnit.SINGELTON,
                                                FrameworkConstants.FRAMEWORK_SERVICE_PROMISE_STREAM,
                                                ServiceType.PROMISE_STREAM,
                                                ServiceStatus.UNUSED,
                                                this.getClass().getSimpleName(),
                                                DependencyType.MANDATORY
                                            );

    @Override
    public ServiceConfig getServiceConfig() {    
        return this.serviceConfig;
    }
    
    @Override
    public ServiceStatus startup(Optional<Properties> configProperties) {
        return ServiceStatus.STARTED;
    }

    @Override
    public ServiceStatus shutdown(Optional<Properties> configProperties) {
        return ServiceStatus.STOPPED;
    }

}
