package org.flowr.framework.core.service.internal;

import org.flowr.framework.core.service.ServiceFrameworkComponent;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface SecurityService extends ServiceFrameworkComponent{
	
	public static SecurityService getInstance() {
		
		return DefaultSecurityService.getInstance();
	}
	
	public class DefaultSecurityService{
		
		private static SecurityService securityService = null;
		
		public static SecurityService getInstance() {
			
			if(securityService == null) {
				securityService = new SecurityServiceImpl();
			}
			
			return securityService;
		}
		
	}
}
