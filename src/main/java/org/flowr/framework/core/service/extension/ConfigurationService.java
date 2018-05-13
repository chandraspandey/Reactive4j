package org.flowr.framework.core.service.extension;

import java.util.List;

import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.config.PipelineConfiguration;
import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.promise.Promisable.PromisableType;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.service.ServiceFrameworkComponent;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ConfigurationService extends ServiceFrameworkComponent{

	public void configure(String configurationFilePath) throws ConfigurationException;
	
	public static ConfigurationService getInstance() {
		
		return DefaultConfigurationService.getInstance();
	}
	
	public class DefaultConfigurationService{
		
		private static ConfigurationService configurationService = null;
		
		public static ConfigurationService getInstance() {
			
			if(configurationService == null) {
				configurationService = new ConfigurationServiceImpl();
			}
			
			return configurationService;
		}
		
	}

	public Scale getProgressScale(PromisableType promisableType, String clientSubscriptionId) throws ConfigurationException;

	public RequestScale getRequestScale(String clientSubscriptionId) throws ConfigurationException;

	public List<PipelineConfiguration> getPipelineConfiguration(ConfigurationType configurationType) throws ConfigurationException;

	public ServiceConfiguration getServiceConfiguration(ConfigurationType configurationType) throws ConfigurationException;
}
