
/**
 * Provides wrapper functionality for security broking & delegates the calls to
 * SecurityBrokingEndPoint via SecurityFabric. Concrete implementation of 
 * Network, port & hardware capabilities are provided by SecurityBrokingEndPoint.
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.security;

import org.flowr.framework.core.exception.AccessSecurityException;

public class SecurityBroker implements SecurityConfiguration{

    private String accessURL;
    
    @Override
    public Identity syncConfiguration(BrokingContext brokingContext) throws AccessSecurityException {
        
        return brokingContext.getIdentity();
    }

    
    @Override
    public void setAccessEndpoint(String accessURL) {       
        this.accessURL = accessURL;
    }

    @Override
    public String getAccessEndpoint() {
        return this.accessURL;
    }

}
