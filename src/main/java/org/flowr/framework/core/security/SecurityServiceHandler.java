
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.security;

import org.flowr.framework.api.Provider;
import org.flowr.framework.core.exception.ConfigurationException;

public interface SecurityServiceHandler extends Provider{

    /**
     * Loads configuration of QueueProvider
     * @throws ConfigurationException 
     */
    void loadSecurityProviderConfig() throws ConfigurationException;

}
