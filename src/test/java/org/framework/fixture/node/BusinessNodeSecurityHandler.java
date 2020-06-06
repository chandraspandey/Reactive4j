
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.framework.fixture.node;

import java.util.AbstractMap.SimpleEntry;

import org.apache.log4j.Logger;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.security.SecurityServiceHandler;

public class BusinessNodeSecurityHandler implements SecurityServiceHandler {
    
    private SimpleEntry<ProviderType,String> entry = new SimpleEntry<>(
                                                        ProviderType.SECURITY,
                                                        "example.flowr.node.NodeSecurityHandler"
                                                     );
    
    @Override
    public void loadSecurityProviderConfig() throws ConfigurationException {
       
        Logger.getRootLogger().info("NodeSecurityHandler called.");
    }
    
    @Override
    public SimpleEntry<ProviderType,String> getProviderConfiguration(){
        
        return this.entry;
    }

}
