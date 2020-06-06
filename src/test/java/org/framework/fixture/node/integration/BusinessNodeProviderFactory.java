
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */


package org.framework.fixture.node.integration;

import org.flowr.framework.api.Provider;
import org.flowr.framework.api.Provider.ProviderType;
import org.flowr.framework.api.ProviderFactory;
import org.flowr.framework.core.service.ProviderServiceHandler;
import org.framework.fixture.node.BusinessNodeDataSourceHandler;
import org.framework.fixture.node.BusinessNodePersistenceHandler;
import org.framework.fixture.node.BusinessNodeSecurityHandler;
import org.framework.fixture.node.BusinessNodeServiceHandler;

public class BusinessNodeProviderFactory implements ProviderFactory {

    private ProviderServiceHandler nodeServiceHandler       = new BusinessNodeServiceHandler();
    
    public Provider instantiate(ProviderType providerType) {
        
        Provider provider = null;
        
        switch(providerType) {
            case CLIENT:
                provider = new BusinessNodeClient(nodeServiceHandler,null);
                break;
            case DATASOURCE:
                provider = new BusinessNodeDataSourceHandler();
                break;
            case PERSISTENCE:
                provider = new BusinessNodePersistenceHandler();
                break;
            case SECURITY:
                provider = new BusinessNodeSecurityHandler();
                break;
            case SERVER:
                provider = new BusinessNodeServer(nodeServiceHandler,null);
                break;
            case SERVICE:
                provider = new BusinessNodeServiceHandler();
                break;
            default:
                break;
        
        }
        
        return provider;
    }
 

}
