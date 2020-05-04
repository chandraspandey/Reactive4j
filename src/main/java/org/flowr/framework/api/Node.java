
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.api;

import java.util.EnumMap;
import java.util.Map;

import org.flowr.framework.api.Provider.ProviderType;
import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.model.DataSourceProvider;
import org.flowr.framework.core.model.PersistenceProvider;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.node.io.network.ServiceMesh;
import org.flowr.framework.core.process.management.NodeProcessHandler;
import org.flowr.framework.core.security.SecurityServiceHandler;
import org.flowr.framework.core.service.ProviderServiceHandler;
import org.flowr.framework.core.service.ServiceEndPoint;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.internal.ConfigurationService;

public interface Node extends ServiceMesh{ 

    EndPointStatus addServiceEndpoint(ConfigurationType configurationType, ServiceEndPoint serviceEndPoint) 
            throws ConfigurationException;
        
    EndPointStatus removeServiceEndpoint(ConfigurationType configurationType, ServiceEndPoint serviceEndPoint)
            throws ConfigurationException;
    
    NodeProcessHandler getNodeProcessHandler();
    
    ProviderServiceHandler getProviderServiceHandler();
    
    SecurityServiceHandler getSecurityServiceHandler();
    
    PersistenceProvider getPersistenceProvider();
    
    DataSourceProvider getDataSourceProvider();
    
    void configure(Map<ProviderType, Provider> map);
    
    Map<ProviderType, Provider> getProviderMap();
    
    public class DefaultNode implements Node{
        
        private SecurityServiceHandler securityHandler;
        private ProviderServiceHandler  serviceHandler;
        private PersistenceProvider persistenceHandler;
        private DataSourceProvider dataSourceHandler;
        private Map<ProviderType, Provider> providerMap = new EnumMap<>(ProviderType.class);
        
        public DefaultNode(){
                        
            ConfigurationService configurationService = ServiceFramework.getInstance().getCatalog()
                    .getConfigurationService();
            
            this.securityHandler                = (SecurityServiceHandler) 
                    configurationService.getProvider(ProviderType.SECURITY);
            this.serviceHandler                 = (ProviderServiceHandler) 
                    configurationService.getProvider(ProviderType.SERVICE);
            this.persistenceHandler             = (PersistenceProvider) 
                    configurationService.getProvider(ProviderType.PERSISTENCE);
            this.dataSourceHandler              = (DataSourceProvider) 
                    configurationService.getProvider(ProviderType.DATASOURCE);
            
            providerMap.put(ProviderType.SERVICE, serviceHandler);
            providerMap.put(ProviderType.SECURITY, securityHandler);
            providerMap.put(ProviderType.PERSISTENCE, persistenceHandler);
            providerMap.put(ProviderType.DATASOURCE, dataSourceHandler);
        }
        
        public void configure(Map<ProviderType, Provider> map){
                        
            providerMap.put(ProviderType.SERVICE, map.get(ProviderType.SERVICE));
            providerMap.put(ProviderType.SECURITY, map.get(ProviderType.SECURITY));
            providerMap.put(ProviderType.PERSISTENCE, map.get(ProviderType.PERSISTENCE));        
            providerMap.put(ProviderType.DATASOURCE, map.get(ProviderType.DATASOURCE));
        }
        
        @Override
        public NodeProcessHandler getNodeProcessHandler() {
            
            return ServiceFramework.getInstance().getCatalog().getNodeService().getNodeProcessHandler();
        }
        
        @Override
        public EndPointStatus addServiceEndpoint(ConfigurationType configurationType, ServiceEndPoint serviceEndPoint) 
                throws ConfigurationException {
            
            return ServiceFramework.getInstance().getCatalog().getHighAvailabilityService().addServiceEndpoint(
                    configurationType, serviceEndPoint);

        }
        
        @Override
        public EndPointStatus removeServiceEndpoint(ConfigurationType configurationType, 
                ServiceEndPoint serviceEndPoint) throws ConfigurationException {
            
            return ServiceFramework.getInstance().getCatalog().getHighAvailabilityService().removeServiceEndpoint(
                    configurationType, serviceEndPoint);
        }

        public ProviderServiceHandler getProviderServiceHandler() {
            
            return serviceHandler;
        }
        
        public SecurityServiceHandler getSecurityServiceHandler() {
        
            return securityHandler;
        }
        
        public PersistenceProvider getPersistenceProvider(){      
            
            return persistenceHandler;
        }
        
        public Map<ProviderType, Provider> getProviderMap(){
            
            return this.providerMap;
        }

        public DataSourceProvider getDataSourceProvider() {
            return this.dataSourceHandler;
        }
        
    }
}
