package org.flowr.framework.core.security;

import org.flowr.framework.core.exception.AccessSecurityException;


/**
 * Provides wrapper functionality for security broking & delegates the calls to
 * SecurityBrokingEndPoint via SecurityFabric. Concrete implementation of 
 * Network, port & hardware capabilities are provided by SecurityBrokingEndPoint.
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class SecurityBroker implements SecurityConfiguration{

	
	@Override
	public SecurityConfiguration syncConfiguration(BrokingContext 
			brokingContext) throws AccessSecurityException {
		
		SecurityConfiguration securityConfiguration = null;
		
		
		
		return securityConfiguration;
	}

	
	@Override
	public void setAccessEndpoint(String accessURL) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAccessEndpoint() {
		// TODO Auto-generated method stub
		return null;
	}

}
