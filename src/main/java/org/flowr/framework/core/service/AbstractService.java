
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.service;

import java.util.EnumMap;
import java.util.Map;

import org.flowr.framework.api.Provider;
import org.flowr.framework.api.Provider.ProviderType;
import org.flowr.framework.core.model.DataSourceProvider;
import org.flowr.framework.core.model.PersistenceProvider;
import org.flowr.framework.core.process.management.ManagedProcessHandler;
import org.flowr.framework.core.process.management.NodeProcessHandler;
import org.flowr.framework.core.security.SecurityServiceHandler;
import org.flowr.framework.core.service.internal.ManagedService;
import org.flowr.framework.core.service.internal.NodeService;

public abstract class AbstractService implements FrameworkService{

    private NodeProcessHandler nodeProcessHandler         = NodeService.getDefaultNodeProcessHandler();
    private ManagedProcessHandler managedProcessHandler     = ManagedService.getDefaultProcessHandler();
    private Map<ProviderType,Provider> providerMap          = new EnumMap<>(ProviderType.class);  
    private ServiceFramework serviceFramework ;    
    
    @Override
    public void setServiceFramework(ServiceFramework serviceFramework) {
        
        this.serviceFramework = serviceFramework;        
     }
    
    
    public void loadProviderConfiguration(Map<ProviderType,Provider> providerMap) {
        
       this.providerMap = providerMap;
   }
    
    
    @Override
    public void setNodeProcessHandler(NodeProcessHandler nodeProcessHandler) {
        
        this.nodeProcessHandler = nodeProcessHandler;
    }
    
    @Override
    public NodeProcessHandler getNodeProcessHandler() {
        
        return this.nodeProcessHandler;
    }
    
    @Override
    public ManagedProcessHandler getManagedProcessHandler(){
        
        return this.managedProcessHandler;
    }
    
    @Override
    public ServiceFramework getServiceFramework() {
        return this.serviceFramework;
    }
    
    @Override
    public SecurityServiceHandler getSecurityHandler() {
        return (SecurityServiceHandler) getProvider(ProviderType.SECURITY);
    }

    @Override
    public ProviderServiceHandler getServiceHandler() {
        return (ProviderServiceHandler) getProvider(ProviderType.SERVICE);
    }

    @Override
    public PersistenceProvider getPersistenceHandler() {
        return (PersistenceProvider) getProvider(ProviderType.PERSISTENCE);
    }
    
    @Override
    public DataSourceProvider getDataSourceProvider() {
        return (DataSourceProvider) getProvider(ProviderType.DATASOURCE);
    }
    
    @Override
    public Map<ProviderType,Provider> getProviderMap() {
        return providerMap;
    }
    
    @Override
    public Provider getProvider(ProviderType type) {

        return providerMap.get(type);    
   }
    
   public String toString(){
        
       StringBuilder sb = new StringBuilder();
       sb.append("\n AbstractService[");
       
       if(providerMap != null && !providerMap.isEmpty()) {
           
           providerMap.keySet().forEach(
                   
                (ProviderType type) -> {
                    
                    if(providerMap.get(type) != null) {
                        
                        sb.append("\n\t | "+type+" : "+providerMap.get(type));
                    }
                }
          );
       }

       sb.append("\n\t}");
        
        return sb.toString();
    }



}
