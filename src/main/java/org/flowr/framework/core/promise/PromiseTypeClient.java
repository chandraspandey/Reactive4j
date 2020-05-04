
/**
 * Defines Async Client capabilities that can create DeferableRequest & 
 * generate DeferableResponse to claim a Promise. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.promise;

import java.util.Optional;
import java.util.Properties;

import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.config.OperationConfig;
import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.model.Model.RequestModel;
import org.flowr.framework.core.security.ClientIdentity;
import org.flowr.framework.core.security.Identity.IdentityType;
import org.flowr.framework.core.security.identity.IdentityData;
import org.flowr.framework.core.service.Service.ServiceStatus;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.ServiceRequest.RequestType;
import org.flowr.framework.core.subject.DeferableRequest;

public interface PromiseTypeClient extends DeferableRequest, Runnable{

    public enum ClientType{
        PERSISTENT,
        ADHOC
    }   
    
    PromiseTypeServer buildPromiseTypeServer() throws ConfigurationException;
    
    void configure(OperationConfig clientConfig) throws ConfigurationException;
    
    PromiseRequest  buildPromiseRequest() throws ConfigurationException;
    
    ClientIdentity buildClientIdentity() throws ConfigurationException;
    
    ServiceStatus startupAdapter(Optional<Properties> configProperties) throws ConfigurationException;
    
    ServiceStatus shutdownAdapter(Optional<Properties> configProperties) throws ConfigurationException;

    ClientIdentity getClientIdentity() throws ConfigurationException; 
    
    Optional<Timeline> getTimelineOption();
    
    
    public abstract class AbstractPromiseClient implements PromiseTypeClient {

        private Optional<Timeline> sessionTimeline              = Optional.empty();  
        private boolean isConfigured;
        private RequestType requestType;  
        private OperationConfig operationConfig;
        private ServiceConfiguration adapterServiceConfiguration;
        private String clientClassName;
          
        public AbstractPromiseClient(RequestType requestType, String clientClassName){
            
            this.requestType        = requestType;
            this.clientClassName    = clientClassName;            
        }
        
        @Override
        public ClientIdentity buildClientIdentity() throws ConfigurationException{
              
            ClientIdentity clientIdentity   = new ClientIdentity(this.clientClassName,IdentityType.LOGICAL);
            clientIdentity.setIdentityData(new IdentityData());
            
            return clientIdentity;
        }
        
        
        @Override
        public void configure(OperationConfig operationConfig) throws ConfigurationException{
                    
            if( operationConfig!= null ){
                
                adapterServiceConfiguration = ServiceFramework.getInstance().getCatalog().getConfigurationService()
                                                .getServiceConfiguration(ConfigurationType.CLIENT);
                
                if(adapterServiceConfiguration != null){
                    this.operationConfig    = operationConfig;
                    this.isConfigured       = true;
                }
            }
            
        }
        
        @Override
        public RequestModel getRequest() {

            return operationConfig.getRequestModel();
        }

 
        
        @Override
        public ServiceStatus startupAdapter(Optional<Properties> configProperties) throws ConfigurationException{
            
            return operationConfig.getClientNotificationConfig().getNotificationServiceAdapter().startup(
                    configProperties);
        }
        
        @Override
        public ServiceStatus shutdownAdapter(Optional<Properties> configProperties) throws ConfigurationException{
            
            return operationConfig.getClientNotificationConfig().getNotificationServiceAdapter().shutdown(
                    configProperties);
        }

        @Override
        public ClientIdentity getClientIdentity() throws ConfigurationException {
            
            return buildClientIdentity();
        }
        
        @Override
        public Optional<Timeline> getTimelineOption() {
            
            return this.sessionTimeline;
        }

        public boolean isConfigured() {
            return isConfigured;
        }

        public ServiceConfiguration getAdapterServiceConfiguration() {
            return adapterServiceConfiguration;
        }

        public Optional<Timeline> getSessionTimeline() {
            return sessionTimeline;
        }

        public void setSessionTimeline(Optional<Timeline> sessionTimeline) {
            this.sessionTimeline = sessionTimeline;
        }

        public RequestType getRequestType() {
            return requestType;
        }

        public OperationConfig getOperationConfig() {
            return operationConfig;
        }

    }
}
