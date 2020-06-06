
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.flowr.framework.core.config.xml.Config;
import org.flowr.framework.core.config.xml.ConfigReader;
import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.model.Model.RequestModel;
import org.flowr.framework.core.model.Model.ResponseModel;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.NotificationTask;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.deferred.ProgressScale;
import org.flowr.framework.core.promise.phase.PhasedProgressScale;
import org.flowr.framework.core.promise.scheduled.ScheduledProgressScale;

public interface Configuration {
    
    /**
     * 
     * 
     * @author Chandra Shekhar Pandey
     * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
     */
    public class ConfigType{

        public <E extends Enum<E>> Enum<E> get(Class<E> enumClass,String name){
                  
            return Enum.valueOf( enumClass, name);
        }
           
    }
    
    /**
     * 
     * 
     * @author Chandra Shekhar Pandey
     * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
     */
    public class ConfigSet{

        public Set<ConfigurationType> configuration(){
            
            return EnumSet.allOf(ConfigurationType.class);
        }

    }

    
    public enum ConfigurationType{
        NODE,
        CLIENT,
        SERVER,
        INTEGRATION_INBOUND,
        INTEGRATION_OUTBOUND,
        EXTERNAL
    }
    
    /*
     * DISPOSE_AFTER_PROCESSING : Default
     * PERSIST_AFTER_PROCESSING : 
     */
    public enum PipelinePolicy{
        
        DISPOSE_AFTER_PROCESSING,
        PERSIST_AFTER_PROCESSING 
    }
    
        
    public static final class DefaultConfigReader{
        
        private static DefaultConfigReader defaultConfigReader;

        private DefaultConfigReader() {
        }
        
        public static DefaultConfigReader getInstance() {
            
            if(defaultConfigReader == null) {
                defaultConfigReader = new DefaultConfigReader();
            }
            
            return defaultConfigReader;
        }
 
        
        public Config read() throws ConfigurationException {
            return ConfigReader.load();
        }
        
        public static Config reload() throws ConfigurationException{
            
            return ConfigReader.reload();
        }
        
    }
    
    static PipelineConfiguration getDefaultPipelineConfiguration() {

        PipelineConfiguration pipelineConfiguration = new PipelineConfiguration();
        
        pipelineConfiguration.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_DEFAULT_NAME);
        pipelineConfiguration.setPipelinePolicy(PipelinePolicy.DISPOSE_AFTER_PROCESSING);
        pipelineConfiguration.setBatchMode(FrameworkConstants.FRAMEWORK_PIPELINE_BATCH_DEFAULT_MODE);
        pipelineConfiguration.setBatchSize(FrameworkConstants.FRAMEWORK_PIPELINE_BATCH_DEFAULT_MIN);
        pipelineConfiguration.setMaxElements(FrameworkConstants.FRAMEWORK_PIPELINE_DEFAULT_MAX_ELEMENTS);
        
        return pipelineConfiguration;
        
    }
    
    static List<PipelineConfiguration> getClientPipelineConfiguration() throws ConfigurationException{

        return DefaultConfigReader.getInstance().read().getClientConfiguration().toPipelineConfiguration();      
    }
    
        
    static CacheConfiguration getCacheConfiguration() throws ConfigurationException{
         
        return DefaultConfigReader.getInstance().read().getDataSourceConfig().getCache().toCacheConfiguration();
    }
    
    static List<DataSourceConfiguration> getDataSourceConfiguration() throws ConfigurationException{
        
        List<DataSourceConfiguration> configurationList = new ArrayList<>();
        
        DataSourceConfiguration config = 
                DefaultConfigReader.getInstance().read().getDataSourceConfig().toDataSourceConfiguration();
        configurationList.add(config);
        
        return configurationList;
    }
    
    static List<PipelineConfiguration> getServerPipelineConfiguration() throws ConfigurationException{

        return DefaultConfigReader.getInstance().read().getServerConfiguration().toPipelineConfiguration();      
    }
    
       
    static List<ServiceConfiguration> getClientEndPointConfiguration() throws ConfigurationException{
            
        List<ServiceConfiguration> configurationList = new ArrayList<>();
        
        ServiceConfiguration configuration =  DefaultConfigReader.getInstance().read().getClientConfiguration()
                                                .getServiceConfig().toServiceConfiguration();
        
        configurationList.add(configuration);        
         
        return configurationList;       
    }
    
    static List<ServiceConfiguration> getServerEndPointConfiguration() throws ConfigurationException{
            
        List<ServiceConfiguration> configurationList = new ArrayList<>();
        
        ServiceConfiguration configuration =  DefaultConfigReader.getInstance().read().getServerConfiguration()
                .getServiceConfig().toServiceConfiguration();

        configurationList.add(configuration);        
        
        return configurationList;    
    }
    
    static ServiceConfiguration getClientConfiguration() throws ConfigurationException{
        
        return DefaultConfigReader.getInstance().read().getClientConfiguration()
                .getServiceConfig().toServiceConfiguration(); 
    }
    
    static ServiceConfiguration getServerConfiguration() throws ConfigurationException{
            
        return DefaultConfigReader.getInstance().read().getServerConfiguration()
                .getServiceConfig().toServiceConfiguration();
    }
    
    static RequestScale getRequestScale(String clientSubscriptionId) throws ConfigurationException{
        
        RequestScale requestScale = DefaultConfigReader.getInstance().read().getClientConfiguration().toRequestScale();
       
        requestScale.setSubscriptionClientId(clientSubscriptionId); 
                
        if(!requestScale.isValid()){
            
            throw new ConfigurationException(
                ErrorMap.ERR_CONFIG,"Mandatory Configurations not provided for RequestScale. Timeout must exceed "+
                        requestScale.getDefaultTimeout()+" milli seconds.");
        }
        
        return requestScale;
    }
    

    static Scale getPhasedProgressScale(String clientSubscriptionId) throws ConfigurationException{
        
        Scale scale = new PhasedProgressScale();
        scale.setSubscriptionClientId(clientSubscriptionId);
        
        return populateProperties(scale);
    }
    

    static Scale getProgressScale(String clientSubscriptionId) throws ConfigurationException{
    
        Scale scale = new ProgressScale();
        scale.setSubscriptionClientId(clientSubscriptionId);
        
        return populateProperties(scale);
    }
    
    static Scale getDefferedProgressScale(String clientSubscriptionId) throws ConfigurationException{
        
        Scale scale = new ProgressScale();
        scale.setSubscriptionClientId(clientSubscriptionId);
        
        return populateProperties(scale);
    }
    

    static Scale getScheduledProgressScale(String clientSubscriptionId) throws ConfigurationException{
    
        Scale scale = new ScheduledProgressScale();
        scale.setSubscriptionClientId(clientSubscriptionId);
        
        return populateProperties(scale);
    }
    
    static Scale populateProperties(Scale scale) throws ConfigurationException{
        
        return DefaultConfigReader.getInstance().read().getServerConfiguration()
                .getProgressScaleConfig().toScale(scale);
    }
        
    
    static List<NodeServiceConfiguration> getNodeInboundEndPointConfiguration() throws ConfigurationException{
            
        List<NodeServiceConfiguration> configurationList = new ArrayList<>();
        
        NodeServiceConfiguration configuration = DefaultConfigReader.getInstance().read()
                .getNodeConfiguration().getInboundConfig().toNodeServiceConfiguration();
        
        configurationList.add(configuration);
        
        return configurationList;       
    }
    
    static List<NodeServiceConfiguration> getNodeOutboundEndPointConfiguration() throws ConfigurationException{
            
        List<NodeServiceConfiguration> configurationList = new ArrayList<>();
        
        NodeServiceConfiguration configuration = DefaultConfigReader.getInstance().read()
                .getNodeConfiguration().getOutboundConfig().toNodeServiceConfiguration();
        
        configurationList.add(configuration);
        
        return configurationList;       
    }
        
    public class  NotificationConfig{
        
        private Map<NotificationProtocolType,NotificationSubscription> subscriptionMap;
        private NotificationServiceAdapter notificationServiceAdapter;
        private NotificationTask notificationTask;
        
        public NotificationConfig() {
            
        }
        
        public NotificationConfig(Map<NotificationProtocolType, NotificationSubscription> subscriptionMap,
                NotificationServiceAdapter notificationServiceAdapter, NotificationTask notificationTask) {
            this.subscriptionMap = subscriptionMap;
            this.notificationServiceAdapter = notificationServiceAdapter;
            this.notificationTask = notificationTask;
        }
        
        public boolean isValidConfig() {
        
            boolean isValidConfig = false;
            
            if( 
                subscriptionMap != null && !subscriptionMap.isEmpty() && 
                notificationServiceAdapter != null && notificationTask != null 
            ){  
                
                isValidConfig = true;
            }
            
            return isValidConfig;
        }

        public Map<NotificationProtocolType, NotificationSubscription> getSubscriptionMap() {
            return subscriptionMap;
        }

        public void setSubscriptionMap(Map<NotificationProtocolType, NotificationSubscription> subscriptionMap) {
            this.subscriptionMap = subscriptionMap;
        }

        public NotificationServiceAdapter getNotificationServiceAdapter() {
            return notificationServiceAdapter;
        }

        public void setNotificationServiceAdapter(NotificationServiceAdapter notificationServiceAdapter) {
            this.notificationServiceAdapter = notificationServiceAdapter;
        }

        public NotificationTask getNotificationTask() {
            return notificationTask;
        }

        
        public String toString(){
            
            StringBuilder builder = new StringBuilder(); 
                    
            subscriptionMap.forEach(
                    
                    ( NotificationProtocolType k, NotificationSubscription v) -> 
                        builder.append("\n\t | "+k+" : \t"+v)                
            );

            
            return "\n\t NotificationConfig{"+
                    "\n\t | notificationServiceAdapter : "+notificationServiceAdapter+   
                    "\n\t | notificationTask : "+notificationTask+
                     builder.toString()+ 
                    "\n\t}\n";
        }
    }
    
    public class OperationConfig {

        private  NotificationConfig clientNotificationConfig;
        private NotificationConfig serverNotificationConfig;
        private RequestModel requestModel; 
        private ResponseModel responseModel;
        
        private boolean isValidConfig;
        
        public OperationConfig( 
            NotificationConfig clientNotificationConfig,
            NotificationConfig serverNotificationConfig,
            RequestModel requestModel, 
            ResponseModel responseModel
        ) throws ConfigurationException {

                this.clientNotificationConfig   = clientNotificationConfig;
                this.serverNotificationConfig   = serverNotificationConfig;
                this.requestModel               = requestModel;
                this.responseModel              = responseModel;    

                if( 
                    clientNotificationConfig.isValidConfig() && 
                    serverNotificationConfig.isValidConfig() && 
                    requestModel != null && responseModel != null
                ){  
                    
                    isValidConfig = true;
                }else{
                    
                    throw new ConfigurationException(
                            ErrorMap.ERR_CONFIG,"Mandatory Client configuration not provided for execution."+this);
                }       
        }

        public NotificationConfig getClientNotificationConfig() {
            return clientNotificationConfig;
        }

        public NotificationConfig getServerNotificationConfig() {
            return serverNotificationConfig;
        }

        public RequestModel getRequestModel() {
            return requestModel;
        }

        public ResponseModel getResponseModel() {
            return responseModel;
        }   
        
        public boolean isValidConfig() {
            return isValidConfig;
        }
        
        public String toString(){
            
            return "\n OperationConfig{"+
                    "\n | isValidConfig             : "+isValidConfig+ 
                    "\n | requestModel              : "+requestModel+   
                    "\n | responseModel             : "+responseModel+
                    "\n | clientNotificationConfig  : "+clientNotificationConfig+   
                    "\n | serverNotificationConfig  : "+serverNotificationConfig+ 
                    "\n}\n";
        }
    }


}
