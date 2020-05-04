
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.service;

import java.util.Map;

import org.flowr.framework.api.Provider;
import org.flowr.framework.api.Provider.ProviderType;
import org.flowr.framework.core.model.DataSourceProvider;
import org.flowr.framework.core.model.PersistenceProvider;
import org.flowr.framework.core.process.management.ManagedProcessHandler;
import org.flowr.framework.core.process.management.NodeProcessHandler;
import org.flowr.framework.core.security.SecurityServiceHandler;

public interface FrameworkService extends ServiceFrameworkComponent{

    void setNodeProcessHandler(NodeProcessHandler nodeProcessHandler);

    NodeProcessHandler getNodeProcessHandler();   
    
    ManagedProcessHandler getManagedProcessHandler();
    
    ServiceFramework getServiceFramework();
    
    void loadProviderConfiguration(Map<ProviderType,Provider> providerMap);

    Provider getProvider(ProviderType type);

    Map<ProviderType, Provider> getProviderMap();

    SecurityServiceHandler getSecurityHandler();

    ProviderServiceHandler getServiceHandler();

    PersistenceProvider getPersistenceHandler();

    DataSourceProvider getDataSourceProvider();

}
