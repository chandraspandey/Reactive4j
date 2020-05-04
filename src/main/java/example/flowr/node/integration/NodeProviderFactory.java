
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */


package example.flowr.node.integration;

import org.flowr.framework.api.Provider;
import org.flowr.framework.api.Provider.ProviderType;
import org.flowr.framework.api.ProviderFactory;
import org.flowr.framework.core.service.ProviderServiceHandler;

import example.flowr.node.NodeDataSourceHandler;
import example.flowr.node.NodePersistenceHandler;
import example.flowr.node.NodeSecurityHandler;
import example.flowr.node.NodeServiceHandler;

public class NodeProviderFactory implements ProviderFactory {

    private ProviderServiceHandler nodeServiceHandler       = new NodeServiceHandler();
    
    public Provider instantiate(ProviderType providerType) {
        
        Provider provider = null;
        
        switch(providerType) {
            case CLIENT:
                provider = new NodeClient(nodeServiceHandler,null);
                break;
            case DATASOURCE:
                provider = new NodeDataSourceHandler();
                break;
            case PERSISTENCE:
                provider = new NodePersistenceHandler();
                break;
            case SECURITY:
                provider = new NodeSecurityHandler();
                break;
            case SERVER:
                provider = new NodeServer(nodeServiceHandler,null);
                break;
            case SERVICE:
                provider = new NodeServiceHandler();
                break;
            default:
                break;
        
        }
        
        return provider;
    }
 

}
