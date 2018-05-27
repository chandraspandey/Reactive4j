package org.flowr.framework.core.service.internal;

import org.flowr.framework.core.service.ServiceFrameworkComponent;


/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface RegistryService extends ServiceFrameworkComponent{

	public static RegistryService getInstance() {
		
		return DefaultRegistryService.getInstance();
	}
	
	public class DefaultRegistryService{
		
		private static RegistryService registryService = null;
		

		public static RegistryService getInstance() {
			
			if(registryService == null) {
				registryService = new RegistryServiceImpl();
			}
			
			return registryService;
		}
		
	}
}
