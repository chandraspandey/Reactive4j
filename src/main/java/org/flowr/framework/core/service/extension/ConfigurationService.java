package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.service.ServiceFrameworkComponent;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ConfigurationService extends ServiceFrameworkComponent{

		
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
}
