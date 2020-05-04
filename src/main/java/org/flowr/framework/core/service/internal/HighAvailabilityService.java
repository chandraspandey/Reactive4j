
/**
 * Defines High availability service for client & server end points.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.internal;

import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.node.ha.Circuit;
import org.flowr.framework.core.service.FrameworkService;
import org.flowr.framework.core.service.ServiceEndPoint;

public interface HighAvailabilityService extends FrameworkService,Runnable{

    EndPointStatus addServiceEndpoint(ConfigurationType configurationType, ServiceEndPoint serviceEndPoint) 
            throws ConfigurationException;
        
    EndPointStatus removeServiceEndpoint(ConfigurationType configurationType, ServiceEndPoint serviceEndPoint)
            throws ConfigurationException;  
    
    void buildCircuit() throws ConfigurationException; 
    
    Circuit getCircuit(ConfigurationType configurationType);
    
    static HighAvailabilityService getInstance() {
        
        return DefaultHighAvailabilityService.getInstance();
    }
    
    public final class DefaultHighAvailabilityService{
        
        private static HighAvailabilityService highAvailabilityService;
        
        private DefaultHighAvailabilityService() {
            
        }
        
        public static HighAvailabilityService getInstance() {
            
            if(highAvailabilityService == null) {
                highAvailabilityService = new HighAvailabilityServiceImpl();
            }
            
            return highAvailabilityService;
        }
        
    }
}
