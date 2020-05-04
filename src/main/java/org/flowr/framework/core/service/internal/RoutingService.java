
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.internal;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.promise.PromiseRequest;
import org.flowr.framework.core.security.ClientIdentity;
import org.flowr.framework.core.service.FrameworkService;
import org.flowr.framework.core.service.ServiceResponse;

public interface RoutingService extends FrameworkService{

    void bindServiceRoute(ClientIdentity clientIdentity,Class<? extends ServiceResponse>  responseClass) 
            throws ConfigurationException;      

    Class<? extends ServiceResponse> getServiceRoute(PromiseRequest promiseRequest);
    
    static RoutingService getInstance() {
        
        return DefaultRegistryService.getInstance();
    }
    
    public final class DefaultRegistryService{
        
        private static RoutingService routingService;
        
        private DefaultRegistryService() {
            
        }
        
        public static RoutingService getInstance() {
            
            if(routingService == null) {
                routingService = new RoutingServiceImpl();
            }
            
            return routingService;
        }
        
    }
}
