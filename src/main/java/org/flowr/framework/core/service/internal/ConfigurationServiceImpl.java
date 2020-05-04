
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.internal;

import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_SUBSCRIPTION_DEFAULT_ID;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.flowr.framework.core.config.CacheConfiguration;
import org.flowr.framework.core.config.Configuration;
import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.config.DataSourceConfiguration;
import org.flowr.framework.core.config.NodeServiceConfiguration;
import org.flowr.framework.core.config.PipelineConfiguration;
import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.promise.Promisable.PromisableType;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.service.AbstractService;
import org.flowr.framework.core.service.dependency.Dependency.DependencyType;

public class ConfigurationServiceImpl extends AbstractService implements ConfigurationService{

    private String configPath;
    private ServiceConfiguration clientConfiguration;
    private ServiceConfiguration serverConfiguration; 
    private List<PipelineConfiguration> clientPipelineConfiguration;
    private List<PipelineConfiguration> serverPipelineConfiguration;
    private List<NodeServiceConfiguration> nodeInboundConfiguration;
    private List<NodeServiceConfiguration> nodeOutboundConfiguration;
    
    private ServiceConfig serviceConfig         = new ServiceConfig(
                                                    true,
                                                    ServiceUnit.SINGELTON,
                                                    FrameworkConstants.FRAMEWORK_SERVICE_CONFIGURATION,
                                                    ServiceType.CONFIGURATION,
                                                    ServiceStatus.UNUSED,
                                                    this.getClass().getSimpleName(),
                                                    DependencyType.MANDATORY
                                                );

    @Override
    public ServiceConfig getServiceConfig() {
    
        return this.serviceConfig;
    }
     
    public void configure(String configurationFilePath) throws ConfigurationException{
        
        if(configurationFilePath != null && ! configurationFilePath.isEmpty() && 
                new File(configurationFilePath).exists()) {
            
            configPath = configurationFilePath;
            loadAdapterConfiguration();
            loadPipelineConfiguration();
            getRequestScale(FRAMEWORK_SUBSCRIPTION_DEFAULT_ID);
            loadIntegrationConfiguration();
        }else {
        
            throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,"Invalid configuration file path :"+configurationFilePath);
        }
    }
    
    public void reload() {
        clientConfiguration             = null;
        serverConfiguration             = null;
        clientPipelineConfiguration     = null;
        serverPipelineConfiguration     = null;
        nodeInboundConfiguration        = null;
        nodeOutboundConfiguration       = null;
    }
    
 
    @Override
    public CacheConfiguration getCacheConfiguration() throws ConfigurationException{
        
        return Configuration.getCacheConfiguration(ConfigurationType.NODE.name(), configPath);
    }
    
    @Override
    public List<DataSourceConfiguration> getDataSourceConfiguration() throws ConfigurationException{
        
        return Configuration.getDataSourceConfiguration(ConfigurationType.NODE.name(), configPath);
    }
    
    
    @Override   
    public List<PipelineConfiguration> getPipelineConfiguration(ConfigurationType configurationType) 
            throws ConfigurationException{
        
        List<PipelineConfiguration> pipelineConfiguration = null;
        
        switch(configurationType) {
            case CLIENT:
                pipelineConfiguration = clientPipelineConfiguration;
                break;
            case SERVER:
                pipelineConfiguration = serverPipelineConfiguration;
                break;
            default:
                break;      
        }       
        return pipelineConfiguration;
    }

    @Override
    public RequestScale getRequestScale(String clientSubscriptionId) throws ConfigurationException {

        RequestScale  requestScale = Configuration.getRequestScale(
                                        clientSubscriptionId,
                                        ConfigurationType.CLIENT.name(),
                                        configPath
                                    );  

        requestScale.setSubscriptionClientId(clientSubscriptionId);
        
        return requestScale;        
    }
    
    public void loadAdapterConfiguration() throws ConfigurationException {
        
        Iterator<ConfigurationType> iter = Arrays.asList(ConfigurationType.values()).iterator();
        
        while(iter.hasNext()) {
            
            switch(iter.next()) {
                case CLIENT:
                    clientConfiguration = Configuration.getClientConfiguration(ConfigurationType.CLIENT.name(),
                                            configPath);
                    break;
                case SERVER:
                    serverConfiguration = Configuration.getServerConfiguration(ConfigurationType.SERVER.name(),
                                            configPath);
                    break;
                default:
                    break;      
            }
        }
    }
    
    @Override
    public ServiceConfiguration getServiceConfiguration(ConfigurationType configurationType) 
            throws ConfigurationException{
        
        ServiceConfiguration serviceConfiguration = null;
        
        switch(configurationType) {
            case CLIENT:
                serviceConfiguration = clientConfiguration;
                break;
            case SERVER:
                serviceConfiguration = serverConfiguration;
                break;
            default:
                break;      
        }       
        return serviceConfiguration;
    }
    
    @Override
    public List<NodeServiceConfiguration> getIntegrationServiceConfiguration(ConfigurationType configurationType) 
            throws ConfigurationException{
        
        List<NodeServiceConfiguration> serviceConfigurationList = null;
        
        switch(configurationType) {
        
            case INTEGRATION_INBOUND:{
                
                serviceConfigurationList = nodeInboundConfiguration;
                break;          
            }case INTEGRATION_OUTBOUND:{
                
                serviceConfigurationList = nodeOutboundConfiguration;
                break;          
            }default:{
                break;
            }           
        }       
        return serviceConfigurationList;
    }
    
    public void loadIntegrationConfiguration() throws ConfigurationException{
        
        Iterator<ConfigurationType> iter = Arrays.asList(ConfigurationType.values()).iterator();
        
        while(iter.hasNext()) {
            
            switch(iter.next()) {
            
    
                case INTEGRATION_INBOUND:{
                    nodeInboundConfiguration = Configuration.getNodeInboundEndPointConfiguration(
                            ConfigurationType.INTEGRATION_INBOUND.name(), configPath);
                    break;          
                }case INTEGRATION_OUTBOUND:{
                    nodeOutboundConfiguration = Configuration.getNodeOutboundEndPointConfiguration(
                            ConfigurationType.INTEGRATION_OUTBOUND.name(), configPath);
                    break;          
                }default:{
                    break;
                }               
            }
        }
    }   

    public void loadPipelineConfiguration() throws ConfigurationException {
            
        Iterator<ConfigurationType> iter = Arrays.asList(ConfigurationType.values()).iterator();
        
        while(iter.hasNext()) {
            
            switch(iter.next()) {
                case CLIENT:
                    clientPipelineConfiguration = Configuration.getClientPipelineConfiguration(
                            ConfigurationType.CLIENT.name(), configPath);
                    break;
                case SERVER:
                    serverPipelineConfiguration = Configuration.getServerPipelineConfiguration(
                            ConfigurationType.SERVER.name(), configPath);
                    break;
                default:
                    break;      
            }
        }
    }
    
    public Scale loadProgressScaleConfiguration(PromisableType promisableType) throws ConfigurationException {
 
        Scale scale = null; 
                
        switch(promisableType) {

            case PROMISE:
                scale =  Configuration.getProgressScale(FRAMEWORK_SUBSCRIPTION_DEFAULT_ID, configPath);  
                break;
            case PROMISE_DEFFERED:
                scale =  Configuration.getProgressScale(FRAMEWORK_SUBSCRIPTION_DEFAULT_ID,configPath);              
                break;
            case PROMISE_PHASED:
                scale = Configuration.getPhasedProgressScale(FRAMEWORK_SUBSCRIPTION_DEFAULT_ID,configPath);
                break;
            case PROMISE_SCHEDULED:    
                scale =  Configuration.getScheduledProgressScale(FRAMEWORK_SUBSCRIPTION_DEFAULT_ID,configPath);
                break;
            case PROMISE_STAGED:
                break;
            default:
                scale = Configuration.getProgressScale(FRAMEWORK_SUBSCRIPTION_DEFAULT_ID,configPath);  
                break;        
        }
    
        return scale;

    }
    
    @Override
    public Scale getProgressScale(PromisableType promisableType, String clientSubscriptionId) 
        throws ConfigurationException{
        
        Scale scale = loadProgressScaleConfiguration(promisableType);        
      
        if(scale != null){
            scale.setSubscriptionClientId(clientSubscriptionId);
        }else {
            
            throw new ConfigurationException(ErrorMap.ERR_CLIENT_INVALID_CONFIG, 
                    "Invalid PromisableType provided for creating Scale model.");
        }
        
        return scale;
    }
      
    @Override
    public ServiceStatus startup(Optional<Properties> configProperties) {
        
        return ServiceStatus.STARTED;
    }

    @Override
    public ServiceStatus shutdown(Optional<Properties> configProperties) {
        return ServiceStatus.STOPPED;
    }

    @Override
    public String toString(){
        
        return "ConfigurationService{"+
                "\n | configPath : "+configPath+    
                serviceConfig+    
                "\n | clientConfiguration           : "+clientConfiguration.isValid()+ 
                "\n | serverConfiguration           : "+serverConfiguration.isValid()+ 
                "\n | nodeInboundConfiguration      : "+nodeInboundConfiguration.size()+ 
                "\n | nodeOutboundConfiguration     : "+nodeOutboundConfiguration.size()+ 
                "\n | clientPipelineConfiguration   : "+clientPipelineConfiguration.size()+    
                "\n | clientPipelineConfiguration   : "+clientPipelineConfiguration+    
                "\n | serverPipelineConfiguration   : "+serverPipelineConfiguration.size()+ 
                "\n | serverPipelineConfiguration   : "+serverPipelineConfiguration+ 
                super.toString()+  
                "\n}\n";
        
    }
    
}
