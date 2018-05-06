package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.service.ServiceFrameworkComponent;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface AdministrationService extends ServiceFrameworkComponent{

		
	public static AdministrationService getInstance() {
		
		return DefaultConfigurationService.getInstance();
	}
	
	public class DefaultConfigurationService{
		
		private static AdministrationService administrationrationService = null;
		
		public static AdministrationService getInstance() {
			
			if(administrationrationService == null) {
				administrationrationService = new AdministrationServiceImpl();
			}
			
			return administrationrationService;
		}
		
	}
}
