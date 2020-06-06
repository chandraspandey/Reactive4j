
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.service.internal;

import java.util.List;

import org.flowr.framework.core.config.CacheConfiguration;
import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.config.DataSourceConfiguration;
import org.flowr.framework.core.config.NodeServiceConfiguration;
import org.flowr.framework.core.config.PipelineConfiguration;
import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.promise.Promisable.PromisableType;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.service.FrameworkService;

public interface ConfigurationService extends FrameworkService{

    void configure(String configurationFilePath) throws ConfigurationException;
    
    static ConfigurationService getInstance() {
        
        return DefaultConfigurationService.getInstance();
    }
    
    public final class DefaultConfigurationService{
        
        private static ConfigurationService configurationService;
        
        private DefaultConfigurationService() {
            
        }
        
        public static ConfigurationService getInstance() {
            
            if(configurationService == null) {
                configurationService = new ConfigurationServiceImpl();
            }
            
            return configurationService;
        }
        
    }

    Scale getProgressScale(PromisableType promisableType, String clientSubscriptionId) throws ConfigurationException;

    RequestScale getRequestScale(String clientSubscriptionId) throws ConfigurationException;

    List<PipelineConfiguration> getPipelineConfiguration(ConfigurationType configurationType) 
            throws ConfigurationException;

    ServiceConfiguration getServiceConfiguration(ConfigurationType configurationType) throws ConfigurationException;
    
    List<NodeServiceConfiguration> getIntegrationServiceConfiguration(ConfigurationType configurationType) 
            throws ConfigurationException;
    
    CacheConfiguration getCacheConfiguration() throws ConfigurationException;
    
    List<DataSourceConfiguration> getDataSourceConfiguration() throws ConfigurationException;

}
