
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service;

import java.util.Optional;
import java.util.Properties;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.service.Service.ServiceStatus;



public interface ServiceLifecycle {

    
    /**
     * Provides hookup services for start up
     * @param configProperties : Optional properties that can be configured for granular startup of services
     * @return
     */
    ServiceStatus startup(Optional<Properties> configProperties) throws ConfigurationException;

    /**
     * Provides hookup services for shut down
     * @param configProperties : Optional properties that can be configured for granular shutdown of services
     * @return
     */
    ServiceStatus shutdown(Optional<Properties> configProperties) throws ConfigurationException;
}
