
/**
 * Defines the interlinking capabilities of Agent using Configuration to the
 * security brokering chain for update configuration.  
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.security;

import org.flowr.framework.core.exception.AccessSecurityException;

public interface SecurityConfiguration {

    
    Identity syncConfiguration(BrokingContext brokingContext) throws AccessSecurityException;
    
    
    void setAccessEndpoint(String accessURL);
    

    String getAccessEndpoint();

}
