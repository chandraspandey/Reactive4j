package org.flowr.framework.core.service.internal;

import java.util.List;

import org.flowr.framework.core.service.Service;
import org.flowr.framework.core.service.ServiceFrameworkComponent;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface AdministrationService extends ServiceFrameworkComponent{

		
	public static AdministrationService getInstance() {
		
		return DefaultConfigurationService.getInstance();
	}
	
	public List<Service> getServiceList();
	
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
