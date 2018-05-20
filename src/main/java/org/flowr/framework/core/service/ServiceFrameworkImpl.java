package org.flowr.framework.core.service;

import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.exception.ConfigurationException;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

class ServiceFrameworkImpl<REQUEST,RESPONSE> extends ServiceFrameworkProvider<REQUEST,RESPONSE>{

	
	@Override
	public void configure(String configurationFilePath) throws ConfigurationException{
		
		
		if(configurationFilePath == null) {
			
			configurationFilePath = System.getProperty(FrameworkConstants.FRAMEWORK_CONFIG_PATH);			
		}
			
		this.getConfigurationService().configure(configurationFilePath);		
	}
	
}
