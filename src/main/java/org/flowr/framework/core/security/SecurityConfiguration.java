package org.flowr.framework.core.security;

import org.flowr.framework.core.exception.AccessSecurityException;

/**
 * Defines the interlinking capabilities of Agent using Configuration to the
 * security brokering chain for update configuration.  
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface SecurityConfiguration {

	
	public SecurityConfiguration syncConfiguration(BrokingContext 
			brokingContext) throws AccessSecurityException;
	
	
	public void setAccessEndpoint(String accessURL);
	

	public String getAccessEndpoint();

}
