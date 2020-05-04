
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
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.promise.PromiseRequest;
import org.flowr.framework.core.security.ClientIdentity;
import org.flowr.framework.core.service.AbstractService;
import org.flowr.framework.core.service.ServiceResponse;
import org.flowr.framework.core.service.dependency.Dependency.DependencyType;
import org.flowr.framework.core.service.route.ServiceRouteMapping;

public class RoutingServiceImpl extends AbstractService implements RoutingService{

    private static ServiceRouteMapping routeMapping = new ServiceRouteMapping();
    
    private ServiceConfig serviceConfig     = new ServiceConfig(
                                                true,
                                                ServiceUnit.SINGELTON,
                                                FrameworkConstants.FRAMEWORK_SERVICE_ROUTING,
                                                ServiceType.ROUTING,
                                                ServiceStatus.UNUSED,
                                                this.getClass().getSimpleName(),
                                                DependencyType.MANDATORY
                                            );

    @Override
    public ServiceConfig getServiceConfig() {    
        return this.serviceConfig;
    }    
    
    @Override
    public void bindServiceRoute(ClientIdentity clientIdentity,Class<? extends ServiceResponse>  responseClass) 
        throws ConfigurationException{
        
        routeMapping.add(clientIdentity,responseClass);
    }
    
    @Override
    public Class<? extends ServiceResponse> getServiceRoute(PromiseRequest promiseRequest){
        
        ClientIdentity clientIdentity =  promiseRequest.getClientIdentity();
        
        return routeMapping.getRoute(clientIdentity);
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
        
        return "RoutingService{"+
                " | serviceConfig : "+serviceConfig+    
                " | \n routeMapping : "+routeMapping+
                super.toString()+  
                "}\n";
    }

}
