
/**
 * Aggregation Service interface for defined service as Service registry supported as part of framework, 
 * concrete implementation provides the behavior for service lookup.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service;

import java.util.List;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.ServiceException;
import org.flowr.framework.core.flow.SingleEventPublisher;
import org.flowr.framework.core.service.dependency.DependencyLoop;

public interface ServiceFramework extends Service, DependencyLoop,SingleEventPublisher{

    
    void addService(ServiceFrameworkComponent service) throws ServiceException;
    
    void removeService(ServiceFrameworkComponent service);
    
    /**
     * Lookup Service at the framework level for usage for singleton ServiceType other than API
     * @param serviceType
     * @return
     */
    Service lookup(ServiceType serviceType);
    
    /**
     * Lookup Service at the framework level for usage for ServiceType API, where multiple client services can be 
     * created with same ServiceType but different names to cater to different end point use cases.
     * @param serviceType
     * @param serviceName
     * @return
     */
    Service lookup(ServiceType serviceType, String serviceName);
    
    void configure(String configurationFilePath) throws ConfigurationException;
    
    List<ServiceFrameworkComponent> getServiceList();
    
    Catalog getCatalog();
    
    Catalog buildCatalog();
    
    static ServiceFramework getInstance() {
        
        return DefaultServiceFramework.getInstance();
    }
    
    public final class DefaultServiceFramework{
        
        private static ServiceFramework serviceFramework;
        
        private DefaultServiceFramework() {
            
        }
        
        public static ServiceFramework getInstance() {
            
            if(serviceFramework == null) {
                serviceFramework = new ServiceFrameworkImpl();
            }
            
            return serviceFramework;
        }
        
    }
}
