
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.app;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.flowr.framework.api.Builder;
import org.flowr.framework.core.config.Configuration.NotificationConfig;
import org.flowr.framework.core.config.Configuration.OperationConfig;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.model.Model.RequestModel;
import org.flowr.framework.core.model.Model.ResponseModel;
import org.flowr.framework.core.node.NodeManager.NodePipelineConfig;
import org.flowr.framework.core.node.io.endpoint.NodePipelineClient;
import org.flowr.framework.core.node.io.endpoint.NodePipelineServer;
import org.flowr.framework.core.node.io.network.ServiceMesh.ServiceTopologyMode;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.NotificationTask;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.promise.Promise.PromiseConfig;
import org.flowr.framework.core.promise.PromiseTypeClient;
import org.flowr.framework.core.service.ServiceLifecycle;

public interface AppBuilder extends Builder{
    
    public interface AppEngineBuilder extends ServiceLifecycle{
        
        void configure() throws ConfigurationException;

        SimpleEntry<NodePipelineServer, NodePipelineClient> configurePipeline(
               SimpleEntry<NodePipelineConfig, NodePipelineConfig> serverClientConfigEntry)
                throws ConfigurationException;
        
        SimpleEntry<NodePipelineServer,NodePipelineClient> configureNodePipeline() throws 
            ConfigurationException;
        
        SimpleEntry<NodePipelineServer,NodePipelineClient> configureApp() throws ConfigurationException;
        
        void configureProviders();

        PromiseTypeClient createPromiseTypeClient(PromiseConfig promiseConfig) throws ConfigurationException;

        void runWithNodeOperation() throws ConfigurationException;

        void runWithPromise(PromiseTypeClient promiseTypeClient);
    }

    static PromiseBuilder getPromiseBuilder() {
        
        return new PromiseBuilder();
    }
    
    static ConfigBuilder getConfigBuilder() {
        
        return new ConfigBuilder();
    }
    
    static NotificationConfigBuilder getNotificationConfigBuilder() {
        
        return new NotificationConfigBuilder();
    }
    
    static NotificationAdapterBuilder getNotificationAdapterBuilder() {
        
        return new NotificationAdapterBuilder();
    }
    
    static NotificationTaskBuilder getNotificationTaskBuilder() {
        
        return new NotificationTaskBuilder();
    }
    
    public class PromiseConfigBuilder implements Builder{

        private PromiseTypeClient promiseTypeClient;
        private RequestModel requestModel;
        private ResponseModel responseModel;
        
        private NotificationServiceAdapter clientAdapter;
        private Properties clientAdapterProperties;    
        private NotificationTask  clientNotificationTask;
        private Properties clientTaskTopologyProperties;
        private Map<NotificationProtocolType,NotificationSubscription> clientSubscriptionMap;
        
        private NotificationServiceAdapter serverAdapter;
        private Properties serverAdapterProperties;    
        private NotificationTask  serverNotificationTask;
        private Properties serverTaskTopologyProperties;
        private Map<NotificationProtocolType,NotificationSubscription> serverSubscriptionMap;
        
        public PromiseConfigBuilder withPromiseTypeClientAs(PromiseTypeClient promiseTypeClient) {
            
            this.promiseTypeClient = promiseTypeClient;
            return this;
        }
        
        public PromiseConfigBuilder withRequestModelAs(RequestModel requestModel) {
            
            this.requestModel = requestModel;
            return this;
        }
        
        public PromiseConfigBuilder withResponseModelAs(ResponseModel responseModel) {
            
            this.responseModel = responseModel;
            return this;
        }        
        
        public PromiseConfigBuilder withClientNotificationServiceAdapterAs(NotificationServiceAdapter clientAdapter) {
            
            this.clientAdapter = clientAdapter;
            return this;
        }
        
        public PromiseConfigBuilder withClientAdapterPropertiesAs(Properties clientAdapterProperties) {
            
            this.clientAdapterProperties = clientAdapterProperties;
            return this;
        }
        
        public PromiseConfigBuilder withClientNotificationTaskAs(NotificationTask  clientNotificationTask) {
            
            this.clientNotificationTask = clientNotificationTask;
            return this;
        }       
        
        public PromiseConfigBuilder withClientTaskTopologyPropertiesAs(Properties clientTaskTopologyProperties) {
            
            this.clientTaskTopologyProperties = clientTaskTopologyProperties;
            return this;
        }
        
        public PromiseConfigBuilder withClientSubscriptionMapAs(
                Map<NotificationProtocolType,NotificationSubscription> clientSubscriptionMap) {
            
            this.clientSubscriptionMap = clientSubscriptionMap;
            return this;
        }
        
       public PromiseConfigBuilder withServerNotificationServiceAdapterAs(NotificationServiceAdapter serverAdapter) {
            
            this.serverAdapter = serverAdapter;
            return this;
        }
        
        public PromiseConfigBuilder withServerAdapterPropertiesAs(Properties serverAdapterProperties) {
            
            this.serverAdapterProperties = serverAdapterProperties;
            return this;
        }
        
        public PromiseConfigBuilder withServerNotificationTaskAs(NotificationTask  serverNotificationTask) {
            
            this.serverNotificationTask = serverNotificationTask;
            return this;
        }       
        
        public PromiseConfigBuilder withServerTaskTopologyPropertiesAs(Properties serverTaskTopologyProperties) {
            
            this.serverTaskTopologyProperties = serverTaskTopologyProperties;
            return this;
        }
        
        public PromiseConfigBuilder withServerSubscriptionMapAs(
                Map<NotificationProtocolType,NotificationSubscription> serverSubscriptionMap) {
            
            this.serverSubscriptionMap = serverSubscriptionMap;
            return this;
        }
        
        private boolean isValidPromiseConfig() {
            
            return ( promiseTypeClient != null && requestModel != null && responseModel != null );
        }
        
        private boolean isValidClientNotificationConfig() {
            
            return (
                    clientNotificationTask != null && 
                    clientTaskTopologyProperties != null && clientSubscriptionMap != null
                    );
        }
        
        private boolean isValidServerNotificationConfig() {
            
            return (
                    serverNotificationTask != null && 
                    serverTaskTopologyProperties != null && serverSubscriptionMap != null
                    );
        }
        
        private boolean isValidClientConfig() {
            
            return ( clientAdapter != null && clientAdapterProperties != null && isValidClientNotificationConfig());
        }
        
        private boolean isValidServerConfig() {
            
            return ( serverAdapter != null && serverAdapterProperties != null && isValidServerNotificationConfig());
        }
        
        public PromiseConfig build() throws ConfigurationException {
            
            PromiseConfig config;
            
            if(isValidPromiseConfig()  && isValidClientConfig() && isValidServerConfig()) {
            
                config = new PromiseConfig();
                
                config.setPromiseTypeClient(promiseTypeClient);
                config.setRequestModel(requestModel);
                config.setResponseModel(responseModel);
                
                config.setClientAdapter(clientAdapter);
                config.setClientAdapterProperties(clientAdapterProperties);
                config.setClientNotificationTask(clientNotificationTask);
                config.setClientTaskTopologyProperties(clientTaskTopologyProperties);
                config.setClientSubscriptionMap(clientSubscriptionMap);
                
                config.setServerAdapter(serverAdapter);
                config.setServerAdapterProperties(serverAdapterProperties);
                config.setServerNotificationTask(serverNotificationTask);
                config.setServerTaskTopologyProperties(serverTaskTopologyProperties);
                config.setServerSubscriptionMap(serverSubscriptionMap);
            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create PromiseConfig with inputs as : ");
                sb.append("\n promiseTypeClient             : "+promiseTypeClient);
                sb.append("\n requestModel                  : "+requestModel);
                sb.append("\n responseModel                 : "+responseModel);
                
                sb.append("\n clientAdapter                 : "+clientAdapter);
                sb.append("\n clientAdapterProperties       : "+clientAdapterProperties);
                sb.append("\n clientNotificationTask        : "+clientNotificationTask);
                sb.append("\n clientTaskTopologyProperties  : "+clientTaskTopologyProperties);
                sb.append("\n clientSubscriptionMap         : "+clientSubscriptionMap);
                
                sb.append("\n serverAdapter                 : "+serverAdapter);
                sb.append("\n serverAdapterProperties       : "+serverAdapterProperties);
                sb.append("\n serverNotificationTask        : "+serverNotificationTask);
                sb.append("\n serverTaskTopologyProperties  : "+serverTaskTopologyProperties);
                sb.append("\n serverSubscriptionMap         : "+serverSubscriptionMap);
                
                Logger.getRootLogger().error(sb.toString());
                
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for PromiseConfigBuilder. "
                );
            }
            
            return config;
        }
    }

     
    
    public class PromiseBuilder implements Builder{

        private OperationConfig operationConfig;
        private PromiseTypeClient promiseClient;
        
        public PromiseBuilder withOperationConfigAs(OperationConfig operationConfig) {
            
            this.operationConfig = operationConfig;
            return this;
        }
        
        public PromiseBuilder withPromiseTypeClientAs(PromiseTypeClient promiseClient) {
            
            this.promiseClient = promiseClient;
            return this;
        }
        
        public PromiseTypeClient build() throws ConfigurationException {
            
            if(promiseClient != null && operationConfig != null) {
            
                promiseClient.configure(operationConfig);
            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create PromiseTypeClient with inputs as : ");
                sb.append("\n operationConfig    : "+operationConfig);
                sb.append("\n promiseClient      : "+promiseClient);
                
                Logger.getRootLogger().error(sb.toString());
                
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for PromiseBuilder. "
                );
            }
            
            return promiseClient;
        }
    }

    
    public class NotificationAdapterBuilder implements Builder{

        private NotificationServiceAdapter serviceAdapter;
        private Properties adapterProperties;
        
        public NotificationAdapterBuilder withNotificationServiceAdapterAs(NotificationServiceAdapter serviceAdapter) {
             
             this.serviceAdapter = serviceAdapter;
             return this;
        }
        
        public NotificationAdapterBuilder withRuntimePropertiesAs(Properties adapterProperties) {
            
            this.adapterProperties = adapterProperties;
            return this;
        }    
       
        public NotificationServiceAdapter build() throws ConfigurationException{        
                   
            if(serviceAdapter != null) {
                
                serviceAdapter.configureRuntime(adapterProperties);
            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create NotificationServiceAdapter with inputs as : ");
                sb.append("\n serviceAdapter        : "+serviceAdapter);
                sb.append("\n adapterProperties     : "+adapterProperties);
                
                Logger.getRootLogger().error(sb.toString());
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for NotificationAdapterBuilder. "
                );
            }
           
           return serviceAdapter;

        }
    }

    
    public class NotificationConfigBuilder implements Builder{

        private NotificationServiceAdapter serviceAdapter;
        private NotificationTask notificationTask;
        private Map<NotificationProtocolType,NotificationSubscription> subscriptionMap;
        
        public NotificationConfigBuilder withSubscriptionMapAs(Map<NotificationProtocolType,NotificationSubscription> 
            subscriptionMap) {
             
             this.subscriptionMap = subscriptionMap;
             return this;
        }
        
        public NotificationConfigBuilder withNotificationAdapterAs(NotificationServiceAdapter 
                serviceAdapter) {
            
            this.serviceAdapter = serviceAdapter;
            return this;
        }
        
        public NotificationConfigBuilder withNotificationTaskAs(NotificationTask notificationTask) {
            
            this.notificationTask = notificationTask;
            return this;
        }
         
        public NotificationConfig build() throws ConfigurationException{     
            
           NotificationConfig notificationConfig = null;
           
           if(subscriptionMap != null && serviceAdapter != null) {
               
               notificationConfig = new NotificationConfig(
                       subscriptionMap,
                       serviceAdapter,               
                       notificationTask
                   );
           }else {
               
               StringBuilder sb = new StringBuilder();
               sb.append("\n Failed to create NotificationConfig with inputs as : ");
               sb.append("\n serviceAdapter         : "+serviceAdapter);
               sb.append("\n subscriptionMap        : "+subscriptionMap);
               
               Logger.getRootLogger().error(sb.toString());
               throw new ConfigurationException(
                       ErrorMap.ERR_CONFIG, "Mandatory Configuration not provided for NotificationConfigBuilder. "
                   );
           }
           
           return notificationConfig;
        }
    }

    public class NotificationTaskBuilder implements Builder{

        private ServiceTopologyMode serviceTopologyMode;
        private NotificationTask notificationTask;
        private Properties topologyProperties;
        
        public NotificationTaskBuilder withServiceTopologyModeAs(ServiceTopologyMode serviceTopologyMode) {
             
             this.serviceTopologyMode = serviceTopologyMode;
             return this;
        }
        
        public NotificationTaskBuilder withTopologyPropertiesAs(Properties topologyProperties) {
            
            this.topologyProperties = topologyProperties;
            return this;
        }
        
        public NotificationTaskBuilder withNotificationTaskAs(NotificationTask notificationTask) {
            
            this.notificationTask = notificationTask;
            return this;
        }
         
        public NotificationTask build() throws ConfigurationException{        
                       
           if(serviceTopologyMode != null) {
               
               notificationTask.configureTopology(serviceTopologyMode,topologyProperties);
           }else {
               
               StringBuilder sb = new StringBuilder();
               sb.append("\n Failed to create NotificationTask with inputs as : ");
               sb.append("\n serviceTopologyMode       : "+serviceTopologyMode);
               sb.append("\n topologyProperties        : "+topologyProperties);
               
               Logger.getRootLogger().error(sb.toString());
               throw new ConfigurationException(
                       ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for NotificationTaskBuilder."
                   );
           }
           
           return notificationTask;

        }
    }
    
    public class ConfigBuilder implements Builder{

        private RequestModel requestModel;
        private ResponseModel responseModel;
        private NotificationConfig clientNotificationConfig;
        private NotificationConfig serverNotificationConfig;

        public ConfigBuilder withRequestModelAs(RequestModel requestModel) {
            
            this.requestModel = requestModel;
            return this;
        }
        
        public ConfigBuilder withResponseModelAs(ResponseModel responseModel) {
            
            this.responseModel = responseModel;
            return this;
        }
        
        public ConfigBuilder withClientNotificationConfigAs(NotificationConfig clientNotificationConfig) {
            
            this.clientNotificationConfig = clientNotificationConfig;
            return this;
        }
        
        public ConfigBuilder withServerNotificationConfigAs(NotificationConfig serverNotificationConfig) {
            
            this.serverNotificationConfig = serverNotificationConfig;
            return this;
        }

        
        public OperationConfig build() throws ConfigurationException {
            
            OperationConfig operationConfig = null; 
            

            
            if(requestModel != null && responseModel != null && clientNotificationConfig != null && 
                    serverNotificationConfig != null) {
            
                    operationConfig = new OperationConfig(
                                                        clientNotificationConfig, 
                                                        serverNotificationConfig, 
                                                        requestModel, 
                                                        responseModel
                                                    );
            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create OperationConfig with inputs as : ");
                sb.append("\n requestModel                   : "+requestModel);
                sb.append("\n responseModel                  : "+responseModel);
                sb.append("\n clientNotificationConfig       : "+requestModel);
                sb.append("\n serverNotificationConfig       : "+responseModel);
                
                Logger.getRootLogger().error(sb.toString());
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for ConfigBuilder. "
                );
            }
            
            return operationConfig;
        }
    }


}


