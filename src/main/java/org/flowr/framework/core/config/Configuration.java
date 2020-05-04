
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config;

import static org.flowr.framework.core.config.ConfigProperties.PropertyType.BOOLEAN;
import static org.flowr.framework.core.config.ConfigProperties.PropertyType.DOUBLE;
import static org.flowr.framework.core.config.ConfigProperties.PropertyType.ENUM;
import static org.flowr.framework.core.config.ConfigProperties.PropertyType.INTEGER;
import static org.flowr.framework.core.config.ConfigProperties.PropertyType.LONG;
import static org.flowr.framework.core.config.ConfigProperties.PropertyType.STRING;
import static org.flowr.framework.core.config.ConfigProperties.PropertyType.TIMEUNIT;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_NOTIFICATION_ENDPOINT;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_PIPELINE_BATCH_MODE;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_PIPELINE_BATCH_SIZE;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_PIPELINE_ELEMENT_MAX;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_PIPELINE_ENDPOINT_HEARTBEAT;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_PIPELINE_ENDPOINT_HEARTBEAT_UNIT;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_PIPELINE_ENDPOINT_MAX;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_PIPELINE_ENDPOINT_MIN;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_PIPELINE_ENDPOINT_NAME;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_PIPELINE_ENDPOINT_TIMEOUT;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_PIPELINE_ENDPOINT_TIMEOUT_UNIT;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_PIPELINE_ENDPOINT_TYPE;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_PIPELINE_ENDPOINT_URL;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_PIPELINE_FUNCTION_TYPE;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_PIPELINE_MAX;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_PIPELINE_MIN;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_PIPELINE_NAME;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_PIPELINE_POLICY_ELEMENT;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_REQUEST_NOTIFICATION_DELIVERY_TYPE;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_REQUEST_RETRY_INTERVAL;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_REQUEST_SCALE_INTERVAL;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_REQUEST_SCALE_MAX;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_REQUEST_SCALE_MIN;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_REQUEST_SCALE_PROGRESS_UNIT;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_REQUEST_SCALE_RETRY;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_REQUEST_SCALE_TIMEOUT;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_REQUEST_SCALE_TIMEOUT_UNIT;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_REQUEST_SCALE_UNIT;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_SERVICE_SERVER_NAME;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_SERVICE_SERVER_PORT;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_THREADS_MAX;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_THREADS_MIN;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_TIMEOUT;
import static org.flowr.framework.core.constants.Constant.ClientConstants.CLIENT_TIMEOUT_UNIT;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CACHE_DISK_MAX;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CACHE_DISK_OVERFLOW;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CACHE_DISK_SPOOL;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CACHE_ELEMENTS_DISK_MAX;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CACHE_ELEMENTS_EXPIRY_DISK;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CACHE_ELEMENTS_HEAP_MAX;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CACHE_ELEMENTS_MEMORY_MAX;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CACHE_ELEMENTS_TIME_TO_IDLE;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CACHE_ELEMENTS_TIME_TO_LIVE;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CACHE_ETERNAL;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CACHE_NAME;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CACHE_PATH;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CACHE_POLICY;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CACHE_STATISTICS;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CACHE_STRATEGY;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CONFIG_MIN;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CONNECTION_CRED;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CONNECTION_DRIVER;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CONNECTION_POOL_SIZE;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CONNECTION_URL;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_CONNECTION_USERNAME;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_COUNT;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_DB;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_DIALECT;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_JPA_VERSION;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_MAPPING_ENTITY;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_MAPPING_ENTITY_COUNT;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_NAME;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_PROVIDER;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_QUERY_CACHE;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_QUERY_CACHE_DEFAULT;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_QUERY_CACHE_EXTERNAL;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_QUERY_CACHE_PROVIDER;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_QUERY_CACHE_PROVIDER_FACTORY;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_QUERY_CACHE_TIMESTAMP;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_QUERY_FORMAT;
import static org.flowr.framework.core.constants.Constant.DataSourceConstants.DS_QUERY_SHOW;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_ENDPOINT_HEARTBEAT;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_ENDPOINT_HEARTBEAT_UNIT;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_ENDPOINT_MAX;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_ENDPOINT_MIN;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_INBOUND_CLIENT_HOST_NAME;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_INBOUND_CLIENT_HOST_PORT;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_INBOUND_CLIENT_MAX;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_INBOUND_ENDPOINT_TYPE;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_INBOUND_FUNCTION_TYPE;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_INBOUND_SERVER_CHANNEL_NAME;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_INBOUND_SERVER_HOST_NAME;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_INBOUND_SERVER_HOST_PORT;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_INBOUND_SERVER_NAME;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_NOTIFICATION_ENDPOINT;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_OUTBOUND_CLIENT_HOST_NAME;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_OUTBOUND_CLIENT_HOST_PORT;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_OUTBOUND_CLIENT_MAX;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_OUTBOUND_ENDPOINT_TYPE;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_OUTBOUND_FUNCTION_TYPE;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_OUTBOUND_SERVER_CHANNEL_NAME;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_OUTBOUND_SERVER_HOST_NAME;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_OUTBOUND_SERVER_HOST_PORT;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_OUTBOUND_SERVER_NAME;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_THREADS_MAX;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_THREADS_MIN;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_TIMEOUT;
import static org.flowr.framework.core.constants.Constant.NodeConstants.NODE_PIPELINE_TIMEOUT_UNIT;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_NAME;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_NOTIFICATION_ENDPOINT;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_PIPELINE_BATCH_MODE;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_PIPELINE_BATCH_SIZE;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_PIPELINE_ELEMENT_MAX;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_PIPELINE_ENDPOINT_HEARTBEAT;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_PIPELINE_ENDPOINT_HEARTBEAT_UNIT;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_PIPELINE_ENDPOINT_MAX;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_PIPELINE_ENDPOINT_MIN;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_PIPELINE_ENDPOINT_NAME;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_PIPELINE_ENDPOINT_TIMEOUT;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_PIPELINE_ENDPOINT_TIMEOUT_UNIT;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_PIPELINE_ENDPOINT_TYPE;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_PIPELINE_ENDPOINT_URL;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_PIPELINE_FUNCTION_TYPE;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_PIPELINE_MAX;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_PIPELINE_MIN;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_PIPELINE_NAME;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_PIPELINE_POLICY_ELEMENT;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_PORT;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_RESPONSE_PROGRESS_NOTIFICATION_DELIVERY_TYPE;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_RESPONSE_PROGRESS_SCALE_INTERVAL;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_RESPONSE_PROGRESS_SCALE_MAX;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_RESPONSE_PROGRESS_SCALE_MIN;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_RESPONSE_PROGRESS_SCALE_TIME_UNIT;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_RESPONSE_PROGRESS_SCALE_UNIT;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_THREADS_MAX;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_THREADS_MIN;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_TIMEOUT;
import static org.flowr.framework.core.constants.Constant.ServerConstants.SERVER_TIMEOUT_UNIT;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.notification.Notification.ClientNotificationProtocolType;
import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.Scale.ScaleOption;
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
    
    static PipelineConfiguration getDefaultPipelineConfiguration() {

        PipelineConfiguration pipelineConfiguration = new PipelineConfiguration();
        
        pipelineConfiguration.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_DEFAULT_NAME);
        pipelineConfiguration.setPipelinePolicy(PipelinePolicy.DISPOSE_AFTER_PROCESSING);
        pipelineConfiguration.setBatchMode(FrameworkConstants.FRAMEWORK_PIPELINE_BATCH_DEFAULT_MODE);
        pipelineConfiguration.setBatchSize(FrameworkConstants.FRAMEWORK_PIPELINE_BATCH_DEFAULT_MIN);
        pipelineConfiguration.setMaxElements(FrameworkConstants.FRAMEWORK_PIPELINE_DEFAULT_MAX_ELEMENTS);
        
        return pipelineConfiguration;
        
    }
    
    static List<PipelineConfiguration> getClientPipelineConfiguration(String configName, String filePath) 
            throws ConfigurationException{
        
        List<PipelineConfiguration> configurationList = new ArrayList<>();

        for(int index=CLIENT_PIPELINE_MIN; index < CLIENT_PIPELINE_MAX;index++ ){
            
            ConfigProperties prop       = new ConfigProperties(configName,filePath);
            
            if(prop.get(STRING, CLIENT_PIPELINE_NAME+"."+index) != null) {
            
                PipelineConfiguration pipelineConfiguration = new PipelineConfiguration();
                
                pipelineConfiguration.setPipelineName(prop.get(STRING, CLIENT_PIPELINE_NAME+"."+index));
                pipelineConfiguration.setPipelinePolicy(
                        prop.get(PipelinePolicy.class, ENUM,CLIENT_PIPELINE_POLICY_ELEMENT+"."+index));
                pipelineConfiguration.setBatchMode(prop.get(BOOLEAN, CLIENT_PIPELINE_BATCH_MODE+"."+index));
                pipelineConfiguration.setBatchSize(prop.get(INTEGER, CLIENT_PIPELINE_BATCH_SIZE+"."+index));
                pipelineConfiguration.setMaxElements(prop.get(LONG, CLIENT_PIPELINE_ELEMENT_MAX+"."+index));
                
                List<ServiceConfiguration> endPointconfigurationList = new ArrayList<>();
                
                for(int eIndex=CLIENT_PIPELINE_ENDPOINT_MIN; eIndex < CLIENT_PIPELINE_ENDPOINT_MAX;
                        eIndex++ ){
                         
                    verifyAndAddClientPipelineConfiguration( configName, filePath,
                                                    prop, eIndex,endPointconfigurationList);
                    
                }
            }
        }
        return configurationList;       
    }
    
    static void verifyAndAddClientPipelineConfiguration(String configName, String filePath,
            ConfigProperties prop,int eIndex, List<ServiceConfiguration> endPointconfigurationList) {
          
        if(
            ( prop.get(STRING,CLIENT_PIPELINE_ENDPOINT_NAME+"."+eIndex) != null ) &&
            ( prop.get(STRING,CLIENT_PIPELINE_ENDPOINT_URL+"."+eIndex)  !=null )
        ){
               
            ServiceConfiguration config = new ServiceConfiguration();     
            
            config.setConfigName(configName);
            config.setFilePath(filePath);
            config.setMinThreads(prop.get(INTEGER,CLIENT_THREADS_MIN));
            config.setMaxThreads(prop.get(INTEGER,CLIENT_THREADS_MAX));
            config.setTimeout(prop.get(LONG,CLIENT_PIPELINE_ENDPOINT_TIMEOUT));
            config.setTimeoutTimeUnit(prop.get(TIMEUNIT, CLIENT_PIPELINE_ENDPOINT_TIMEOUT_UNIT));

            config.setHeartbeatInterval(prop.get(LONG,CLIENT_PIPELINE_ENDPOINT_HEARTBEAT));
            config.setHeartbeatTimeUnit(prop.get(TIMEUNIT, CLIENT_PIPELINE_ENDPOINT_HEARTBEAT_UNIT));
            
            config.setHostName(prop.get(STRING,CLIENT_PIPELINE_ENDPOINT_NAME+"."+eIndex));
            config.setNotificationEndPoint(prop.get(STRING,CLIENT_PIPELINE_ENDPOINT_URL+"."+eIndex));
            config.setPipelineFunctionType(prop.get(PipelineFunctionType.class,ENUM,
                    CLIENT_PIPELINE_FUNCTION_TYPE+"."+eIndex)); 
            config.setNotificationProtocolType(prop.get(ClientNotificationProtocolType.class,ENUM,
                    CLIENT_PIPELINE_ENDPOINT_TYPE+"."+eIndex));
           
            endPointconfigurationList.add(config);
        }

    }
    
    static CacheConfiguration getCacheConfiguration(String configName, String filePath) 
            throws ConfigurationException{
        
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        
        ConfigProperties prop       = new ConfigProperties(configName,filePath);
        
        cacheConfiguration.setCacheName(prop.get(STRING, DS_CACHE_NAME));
        cacheConfiguration.setCachePath(prop.get(STRING, DS_CACHE_PATH));
        cacheConfiguration.setCachePolicy(prop.get(STRING, DS_CACHE_POLICY));


        cacheConfiguration.setCacheStrategy(prop.get(STRING, DS_CACHE_STRATEGY));
        
        cacheConfiguration.setCacheStatistics(prop.get(BOOLEAN, DS_CACHE_STATISTICS));      
        cacheConfiguration.setCacheOverFlowToDisk(prop.get(BOOLEAN, DS_CACHE_DISK_OVERFLOW));
        cacheConfiguration.setEternal(prop.get(BOOLEAN, DS_CACHE_ETERNAL));
        
        cacheConfiguration.setCacheDiskSpool(prop.get(LONG, DS_CACHE_DISK_SPOOL));
        cacheConfiguration.setElementExpiryDisk(prop.get(LONG, DS_CACHE_ELEMENTS_EXPIRY_DISK));
        cacheConfiguration.setElementMaxDisk(prop.get(LONG, DS_CACHE_ELEMENTS_DISK_MAX));
        cacheConfiguration.setElementMaxHeap(prop.get(LONG, DS_CACHE_ELEMENTS_HEAP_MAX));
        cacheConfiguration.setElementMaxMemory(prop.get(LONG, DS_CACHE_ELEMENTS_MEMORY_MAX));
        cacheConfiguration.setElementTimeToIdle(prop.get(LONG, DS_CACHE_ELEMENTS_TIME_TO_IDLE));
        cacheConfiguration.setElementTimeToLive(prop.get(LONG, DS_CACHE_ELEMENTS_TIME_TO_LIVE));        
        cacheConfiguration.setCacheDiskMax(prop.get(LONG, DS_CACHE_DISK_MAX));
         
        return cacheConfiguration;
    }
    
    static List<DataSourceConfiguration> getDataSourceConfiguration(String configName, String filePath) 
            throws ConfigurationException{
        
        List<DataSourceConfiguration> dataSourceconfigurationList = new ArrayList<>();
        
        ConfigProperties prop               = new ConfigProperties(configName,filePath);
        int dataSourceMax                   = prop.get(INTEGER,DS_COUNT);
        int dataEntityMappingMax            = prop.get(INTEGER,DS_MAPPING_ENTITY_COUNT);
        
        List<String> mappingEntityList      = new ArrayList<>();
        
        for(int index=DS_CONFIG_MIN; index <= dataEntityMappingMax;index++ ){
            
            String mappingEntityClass = prop.get(STRING, DS_MAPPING_ENTITY+"."+index);

            if(mappingEntityClass != null) {
                mappingEntityList.add(mappingEntityClass);
            }
        }

        for(int index=DS_CONFIG_MIN; index <= dataSourceMax;index++ ){
            
            if(prop.get(STRING, DS_NAME+"."+index) != null) {
                
                DataSourceConfiguration dataSourceConfiguration = new DataSourceConfiguration();
                
                dataSourceConfiguration.setDataSourceName(prop.get(STRING, DS_NAME+"."+index));
                dataSourceConfiguration.setDataSource(prop.get(STRING, DS_DB+"."+index));               
                dataSourceConfiguration.setDataSourceProvider(prop.get(STRING, DS_PROVIDER+"."+index));
                dataSourceConfiguration.setDialect(prop.get(STRING, DS_DIALECT+"."+index));
                
               
                
                dataSourceConfiguration.setFormatQuery(prop.get(BOOLEAN, DS_QUERY_FORMAT+"."+index));
                dataSourceConfiguration.setShowQuery(prop.get(BOOLEAN, DS_QUERY_SHOW+"."+index));  
                dataSourceConfiguration.setJpaVersion(prop.get(STRING, DS_JPA_VERSION));
                
                DataSourceCacheConfiguration cacheConfiguration = new DataSourceCacheConfiguration();
                
                cacheConfiguration.setCacheQuery(prop.get(BOOLEAN, DS_QUERY_CACHE+"."+index));
                
                cacheConfiguration.setCacheExternal(prop.get(BOOLEAN, DS_QUERY_CACHE_EXTERNAL));
                cacheConfiguration.setCacheProviderClass(prop.get(STRING, DS_QUERY_CACHE_PROVIDER));
                cacheConfiguration.setCacheProviderFactoryClass(
                        prop.get(STRING, DS_QUERY_CACHE_PROVIDER_FACTORY));
                cacheConfiguration.setCacheQueryClass(prop.get(STRING, DS_QUERY_CACHE_DEFAULT));
                cacheConfiguration.setCacheTimestampClass(prop.get(STRING, DS_QUERY_CACHE_TIMESTAMP));
                
                dataSourceConfiguration.setCacheConfiguration(cacheConfiguration);
                
                ConnectionConfiguration connConfiguration = new ConnectionConfiguration();
                
                connConfiguration.setConnectionDriverClass(
                        prop.get(STRING, DS_CONNECTION_DRIVER+"."+index));
                connConfiguration.setConnectionPassword(prop.get(STRING, DS_CONNECTION_CRED+"."+index));
                connConfiguration.setConnectionURL(prop.get(STRING, DS_CONNECTION_URL+"."+index));
                connConfiguration.setConnectionUserName(
                        prop.get(STRING, DS_CONNECTION_USERNAME+"."+index));
               
                connConfiguration.setConnectionPoolSize(
                        prop.get(INTEGER, DS_CONNECTION_POOL_SIZE+"."+index));
                
                dataSourceConfiguration.setConnectionConfiguration(connConfiguration);
                
                dataSourceconfigurationList.add(dataSourceConfiguration);
                    
            }
        }
        return dataSourceconfigurationList;
    }
    
    static List<PipelineConfiguration> getServerPipelineConfiguration(String configName, String filePath) 
            throws ConfigurationException{
        
        List<PipelineConfiguration> configurationList = new ArrayList<>();

        for(int index=SERVER_PIPELINE_MIN; index < SERVER_PIPELINE_MAX;index++ ){
            
            ConfigProperties prop       = new ConfigProperties(configName,filePath);
            
            if(prop.get(STRING, SERVER_PIPELINE_NAME+"."+index) != null) {
            
                PipelineConfiguration pipelineConfiguration = new PipelineConfiguration();
                
                pipelineConfiguration.setPipelineName(prop.get(STRING, SERVER_PIPELINE_NAME+"."+index));
                pipelineConfiguration.setPipelinePolicy(
                        prop.get(PipelinePolicy.class, ENUM,SERVER_PIPELINE_POLICY_ELEMENT+"."+index));
                pipelineConfiguration.setBatchMode(prop.get(BOOLEAN, SERVER_PIPELINE_BATCH_MODE+"."+index));
                pipelineConfiguration.setBatchSize(prop.get(INTEGER, SERVER_PIPELINE_BATCH_SIZE+"."+index));
                pipelineConfiguration.setMaxElements(prop.get(LONG, SERVER_PIPELINE_ELEMENT_MAX+"."+index));
                
                List<ServiceConfiguration> endPointconfigurationList = new ArrayList<>();
                
                for(int eIndex=SERVER_PIPELINE_ENDPOINT_MIN; eIndex < SERVER_PIPELINE_ENDPOINT_MAX;
                        eIndex++ ){
                    
                    verifyAndAddServerPipelineConfiguration(configName, filePath, prop,eIndex, 
                            endPointconfigurationList);
                }
            }
        }
        return configurationList;       
    }
    
    static void verifyAndAddServerPipelineConfiguration(String configName, String filePath,
            ConfigProperties prop,int eIndex, List<ServiceConfiguration> endPointconfigurationList) {
        
        if(
                ( prop.get(STRING,SERVER_PIPELINE_ENDPOINT_NAME+"."+eIndex) != null ) &&
                ( prop.get(STRING,SERVER_PIPELINE_ENDPOINT_URL+"."+eIndex)  !=null )
        ){
                     
            ServiceConfiguration config = new ServiceConfiguration(); 
            
            config.setMinThreads(prop.get(INTEGER,SERVER_THREADS_MIN));
            config.setMaxThreads(prop.get(INTEGER,SERVER_THREADS_MAX));
            config.setTimeout(prop.get(LONG,SERVER_PIPELINE_ENDPOINT_TIMEOUT));
            config.setTimeoutTimeUnit(prop.get(TIMEUNIT, SERVER_PIPELINE_ENDPOINT_TIMEOUT_UNIT));
            config.setConfigName(configName);
            config.setFilePath(filePath);
            config.setHeartbeatInterval(prop.get(LONG,SERVER_PIPELINE_ENDPOINT_HEARTBEAT));
            config.setHeartbeatTimeUnit(prop.get(TIMEUNIT, SERVER_PIPELINE_ENDPOINT_HEARTBEAT_UNIT));
            
            config.setHostName(prop.get(STRING,SERVER_PIPELINE_ENDPOINT_NAME+"."+eIndex));
            config.setNotificationEndPoint(prop.get(STRING,SERVER_PIPELINE_ENDPOINT_URL+"."+eIndex));
            config.setPipelineFunctionType(prop.get(PipelineFunctionType.class,ENUM,
                    SERVER_PIPELINE_FUNCTION_TYPE+"."+eIndex)); 
            config.setNotificationProtocolType( prop.get(ServerNotificationProtocolType.class,ENUM,
                    SERVER_PIPELINE_ENDPOINT_TYPE+"."+eIndex));
            
            endPointconfigurationList.add(config);
        }

    }
    
    static List<ServiceConfiguration> getClientEndPointConfiguration(String configName, String filePath) 
            throws ConfigurationException{
            
        List<ServiceConfiguration> configurationList = new ArrayList<>();
        
        
        for(int index=CLIENT_PIPELINE_ENDPOINT_MIN; index < CLIENT_PIPELINE_ENDPOINT_MAX;index++ ){
            
            ConfigProperties prop       = new ConfigProperties(configName,filePath);
            
        
            if(
                ( prop.get(STRING,CLIENT_PIPELINE_ENDPOINT_NAME+"."+index) != null ) &&
                ( prop.get(STRING,CLIENT_PIPELINE_ENDPOINT_URL+"."+index)  !=null )
            ){
                
                ServiceConfiguration config = new ServiceConfiguration();               
                        
                config.setMinThreads(prop.get(INTEGER,CLIENT_THREADS_MIN));
                config.setMaxThreads(prop.get(INTEGER,CLIENT_THREADS_MAX));
                config.setTimeout(prop.get(LONG,CLIENT_PIPELINE_ENDPOINT_TIMEOUT));
                config.setTimeoutTimeUnit(prop.get(TIMEUNIT, CLIENT_PIPELINE_ENDPOINT_TIMEOUT_UNIT));
                config.setConfigName(configName);
                config.setFilePath(filePath);
                config.setHeartbeatInterval(prop.get(LONG,CLIENT_PIPELINE_ENDPOINT_HEARTBEAT));
                config.setHeartbeatTimeUnit(prop.get(TIMEUNIT, CLIENT_PIPELINE_ENDPOINT_HEARTBEAT_UNIT));
                
                config.setHostName(prop.get(STRING,CLIENT_PIPELINE_ENDPOINT_NAME+"."+index));
                config.setNotificationEndPoint(prop.get(STRING,CLIENT_PIPELINE_ENDPOINT_URL+"."+index));
                config.setPipelineFunctionType(
                        prop.get(PipelineFunctionType.class,ENUM,CLIENT_PIPELINE_FUNCTION_TYPE+"."+index)); 
                config.setNotificationProtocolType( prop.get(ClientNotificationProtocolType.class,ENUM,
                        CLIENT_PIPELINE_ENDPOINT_TYPE+"."+index));

                configurationList.add(config);
                config.setConfigAsProperties(prop);
            }   
            
        }
         
        return configurationList;       
    }
    
    static List<ServiceConfiguration> getServerEndPointConfiguration(String configName, String filePath) 
            throws ConfigurationException{
            
        List<ServiceConfiguration> configurationList = new ArrayList<>();
        
        ConfigProperties prop       = new ConfigProperties(configName,filePath);
        
        for(int index=SERVER_PIPELINE_ENDPOINT_MIN; index < SERVER_PIPELINE_ENDPOINT_MAX;index++ ){
                    
            if(
                ( prop.get(STRING,SERVER_PIPELINE_ENDPOINT_NAME+"."+index) != null ) &&
                ( prop.get(STRING,SERVER_PIPELINE_ENDPOINT_URL+"."+index)  !=null )
            ){
                ServiceConfiguration config = new ServiceConfiguration(); 
                
                config.setMinThreads(prop.get(INTEGER,SERVER_THREADS_MIN));
                config.setMaxThreads(prop.get(INTEGER,SERVER_THREADS_MAX));
                config.setTimeout(prop.get(LONG,SERVER_PIPELINE_ENDPOINT_TIMEOUT));
                config.setTimeoutTimeUnit(prop.get(TIMEUNIT, SERVER_PIPELINE_ENDPOINT_TIMEOUT_UNIT));
                config.setConfigName(configName);
                config.setFilePath(filePath);
                config.setHeartbeatInterval(prop.get(LONG,SERVER_PIPELINE_ENDPOINT_HEARTBEAT));
                config.setHeartbeatTimeUnit(prop.get(TIMEUNIT, SERVER_PIPELINE_ENDPOINT_HEARTBEAT_UNIT));
                
                config.setHostName(prop.get(STRING,SERVER_PIPELINE_ENDPOINT_NAME+"."+index));
                config.setNotificationEndPoint(prop.get(STRING,SERVER_PIPELINE_ENDPOINT_URL+"."+index));
                config.setPipelineFunctionType(
                        prop.get(PipelineFunctionType.class,ENUM,SERVER_PIPELINE_FUNCTION_TYPE+"."+index)); 
                config.setNotificationProtocolType( prop.get(ServerNotificationProtocolType.class,ENUM,
                        SERVER_PIPELINE_ENDPOINT_TYPE+"."+index));

                
                configurationList.add(config);
                config.setConfigAsProperties(prop);
            }           
        }
  
        return configurationList;       
    }
    
    static ServiceConfiguration getClientConfiguration(String configName, String filePath) 
            throws ConfigurationException{
            
        ServiceConfiguration config = new ServiceConfiguration(); 
    
        ConfigProperties prop       = new ConfigProperties(configName,filePath);
        config.setHostName(prop.get(STRING,CLIENT_SERVICE_SERVER_NAME));
        config.setHostPort(prop.get(INTEGER,CLIENT_SERVICE_SERVER_PORT));
        config.setMinThreads(prop.get(INTEGER,CLIENT_THREADS_MIN));
        config.setMaxThreads(prop.get(INTEGER,CLIENT_THREADS_MAX));
        config.setTimeout(prop.get(LONG,CLIENT_TIMEOUT));
        config.setTimeoutTimeUnit(prop.get(TIMEUNIT, CLIENT_TIMEOUT_UNIT));
        config.setNotificationEndPoint(prop.get(STRING,CLIENT_NOTIFICATION_ENDPOINT));
        config.setConfigName(configName);
        config.setFilePath(filePath);
        
        config.setConfigAsProperties(prop);
        
        return config;      
    }
    
    static ServiceConfiguration getServerConfiguration(String configName, String filePath) 
            throws ConfigurationException{
        
        ServiceConfiguration config = new ServiceConfiguration();
        
        ConfigProperties prop       = new ConfigProperties(configName,filePath);
        config.setHostName(prop.get(STRING,SERVER_NAME));
        config.setHostPort(prop.get(INTEGER,SERVER_PORT));
        config.setMinThreads(prop.get(INTEGER,SERVER_THREADS_MIN));
        config.setMaxThreads(prop.get(INTEGER,SERVER_THREADS_MAX));
        config.setTimeout(prop.get(LONG,SERVER_TIMEOUT));
        config.setTimeoutTimeUnit(prop.get(TIMEUNIT, SERVER_TIMEOUT_UNIT));
        config.setNotificationEndPoint(prop.get(STRING,SERVER_NOTIFICATION_ENDPOINT));
        config.setConfigName(configName);
        config.setFilePath(filePath);
        
        config.setConfigAsProperties(prop);
            
        return config;
    }
    
    static RequestScale getRequestScale(String clientSubscriptionId,String fileName, String filePath) 
            throws ConfigurationException{
        
        RequestScale requestScale = new RequestScale();
        
        ConfigProperties prop = new ConfigProperties(fileName,filePath);   
        
        ScaleOption scaleOption = new ScaleOption();
        
        scaleOption.setMinScale(prop.get(DOUBLE, CLIENT_REQUEST_SCALE_MIN));
        scaleOption.setMaxScale(prop.get(DOUBLE, CLIENT_REQUEST_SCALE_MAX));
        scaleOption.setScaleUnit(prop.get(DOUBLE, CLIENT_REQUEST_SCALE_UNIT));
        scaleOption.setProgressTimeUnit(prop.get(TIMEUNIT, CLIENT_REQUEST_SCALE_PROGRESS_UNIT));
        scaleOption.setInterval(prop.get(LONG, CLIENT_REQUEST_SCALE_INTERVAL));
        
        requestScale.setScaleOption(scaleOption);
        
        requestScale.setSubscriptionClientId(clientSubscriptionId);
        requestScale.setRetryCount(prop.get(INTEGER, CLIENT_REQUEST_SCALE_RETRY));
        
        requestScale.setTimeout(prop.get(LONG, CLIENT_REQUEST_SCALE_TIMEOUT));
        requestScale.setTimeoutTimeUnit(prop.get(TIMEUNIT, CLIENT_REQUEST_SCALE_TIMEOUT_UNIT));

        requestScale.setRetryInterval(prop.get(LONG, CLIENT_REQUEST_RETRY_INTERVAL));        
        requestScale.setNotificationDeliveryType(prop.get(NotificationDeliveryType.EXTERNAL.getClass(), ENUM,
                CLIENT_REQUEST_NOTIFICATION_DELIVERY_TYPE));    
                
        if(!requestScale.isValid()){
            
            throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,"Mandatory Configurations not provided for RequestScale. Timeout must exceed "+
                            requestScale.getDefaultTimeout()+" milli seconds. : "+
                            prop);
        }
        
        return requestScale;
    }
    

    static Scale getPhasedProgressScale(String clientSubscriptionId,String filePath) 
            throws ConfigurationException{
        Scale scale = new PhasedProgressScale();
        scale.setSubscriptionClientId(clientSubscriptionId);
        
        return populateProperties(scale,filePath);
    }
    

    static Scale getProgressScale(String clientSubscriptionId,String filePath) 
            throws ConfigurationException{
    
        Scale scale = new ProgressScale();
        scale.setSubscriptionClientId(clientSubscriptionId);
        
        return populateProperties(scale,filePath);
    }
    

    static Scale getScheduledProgressScale(String clientSubscriptionId, String filePath) 
            throws ConfigurationException{
    
        Scale scale = new ScheduledProgressScale();
        scale.setSubscriptionClientId(clientSubscriptionId);
        
        return populateProperties(scale,filePath);
    }
    
    static Scale populateProperties(Scale scale,String filePath) throws ConfigurationException{
        
        ConfigProperties prop = new ConfigProperties(filePath);         
        ScaleOption scaleOption = new ScaleOption();
        
        scaleOption.setMinScale(prop.get(DOUBLE, SERVER_RESPONSE_PROGRESS_SCALE_MIN));
        scaleOption.setMaxScale(prop.get(DOUBLE, SERVER_RESPONSE_PROGRESS_SCALE_MAX));

        scaleOption.setScaleUnit(prop.get(DOUBLE, SERVER_RESPONSE_PROGRESS_SCALE_UNIT));
        scaleOption.setProgressTimeUnit(prop.get(TIMEUNIT, SERVER_RESPONSE_PROGRESS_SCALE_TIME_UNIT));
        scaleOption.setInterval(prop.get(LONG, SERVER_RESPONSE_PROGRESS_SCALE_INTERVAL));
        
        scale.setNotificationDeliveryType(prop.get(NotificationDeliveryType.EXTERNAL.getClass(), ENUM,
                SERVER_RESPONSE_PROGRESS_NOTIFICATION_DELIVERY_TYPE)); 
        
        scale.setScaleOption(scaleOption);

        return scale;
    }
        
    
    static List<NodeServiceConfiguration> getNodeInboundEndPointConfiguration(String configName, String filePath) 
            throws ConfigurationException{
            
        List<NodeServiceConfiguration> configurationList = new ArrayList<>();
        
        
        ConfigProperties prop       = new ConfigProperties(configName,filePath);
        
        int nodeMin = prop.get(INTEGER,NODE_PIPELINE_ENDPOINT_MIN);
        int nodeMax = prop.get(INTEGER,NODE_PIPELINE_ENDPOINT_MAX);
        
        for(int index= nodeMin; index < nodeMax;index++ ){
            
        
            if(
                ( prop.get(STRING,NODE_PIPELINE_INBOUND_SERVER_NAME+"."+index) != null ) 
            ){
                
                NodeServiceConfiguration config = new NodeServiceConfiguration();               
                        
                config.setMinThreads(prop.get(INTEGER,NODE_PIPELINE_THREADS_MIN));
                config.setMaxThreads(prop.get(INTEGER,NODE_PIPELINE_THREADS_MAX));
                config.setTimeout(prop.get(LONG,NODE_PIPELINE_TIMEOUT));
                config.setTimeoutTimeUnit(prop.get(TIMEUNIT, NODE_PIPELINE_TIMEOUT_UNIT));
                config.setConfigName(configName);
                config.setFilePath(filePath);
                config.setHeartbeatInterval(prop.get(LONG,NODE_PIPELINE_ENDPOINT_HEARTBEAT));
                config.setHeartbeatTimeUnit(
                        prop.get(TIMEUNIT, NODE_PIPELINE_ENDPOINT_HEARTBEAT_UNIT));              
                config.setNotificationEndPoint(prop.get(STRING,NODE_PIPELINE_NOTIFICATION_ENDPOINT)); 
                
                config.setNodePipelineName(prop.get(STRING,NODE_PIPELINE_INBOUND_SERVER_NAME+"."+index));
                config.setNodeChannelName(prop.get(STRING,NODE_PIPELINE_INBOUND_SERVER_CHANNEL_NAME+"."+index));
                
                config.setHostName(prop.get(STRING,NODE_PIPELINE_INBOUND_SERVER_HOST_NAME+"."+index));
                config.setHostPort(prop.get(INTEGER,NODE_PIPELINE_INBOUND_SERVER_HOST_PORT+"."+index));
                config.setPipelineFunctionType(prop.get(PipelineFunctionType.class,ENUM,
                        NODE_PIPELINE_INBOUND_FUNCTION_TYPE+"."+index)); 
                config.setNotificationProtocolType( prop.get(ServerNotificationProtocolType.class,ENUM,
                        NODE_PIPELINE_INBOUND_ENDPOINT_TYPE+"."+index));

                int clientMax = prop.get(INTEGER,NODE_PIPELINE_INBOUND_CLIENT_MAX+"."+index);
                
                for(int clientIndex= 1; clientIndex <= clientMax;clientIndex++ ){
                    
                        SimpleEntry<String, Integer> clientEndPoint = new SimpleEntry<>(
                            prop.get(STRING,NODE_PIPELINE_INBOUND_CLIENT_HOST_NAME+"."+clientIndex),
                            prop.get(INTEGER,NODE_PIPELINE_INBOUND_CLIENT_HOST_PORT+"."+clientIndex)
                        );
                        config.addClientEndPoint(clientEndPoint);
                }
                
                configurationList.add(config);
                config.setConfigAsProperties(prop);
            }   
            
        }
         
        return configurationList;       
    }
    
    static List<NodeServiceConfiguration> getNodeOutboundEndPointConfiguration(String configName, String filePath) 
            throws ConfigurationException{
            
        List<NodeServiceConfiguration> configurationList = new ArrayList<>();
        
        ConfigProperties prop       = new ConfigProperties(configName,filePath);
        
        int nodeMin = prop.get(INTEGER,NODE_PIPELINE_ENDPOINT_MIN);
        int nodeMax = prop.get(INTEGER,NODE_PIPELINE_ENDPOINT_MAX);
        
        for(int index=nodeMin; index < nodeMax;index++ ){
            
        
            if(
                ( prop.get(STRING,NODE_PIPELINE_OUTBOUND_SERVER_NAME+"."+index) != null ) 
            ){
                
                NodeServiceConfiguration config = new NodeServiceConfiguration();               
                        
                config.setMinThreads(prop.get(INTEGER,NODE_PIPELINE_THREADS_MIN));
                config.setMaxThreads(prop.get(INTEGER,NODE_PIPELINE_THREADS_MAX));
                config.setTimeout(prop.get(LONG,NODE_PIPELINE_TIMEOUT));
                config.setTimeoutTimeUnit(prop.get(TIMEUNIT, NODE_PIPELINE_TIMEOUT_UNIT));
                config.setConfigName(configName);
                config.setFilePath(filePath);
                config.setHeartbeatInterval(prop.get(LONG,NODE_PIPELINE_ENDPOINT_HEARTBEAT));
                config.setHeartbeatTimeUnit(
                        prop.get(TIMEUNIT, NODE_PIPELINE_ENDPOINT_HEARTBEAT_UNIT));              
                config.setNotificationEndPoint(prop.get(STRING,NODE_PIPELINE_NOTIFICATION_ENDPOINT)); 
                
                config.setNodePipelineName(prop.get(STRING,NODE_PIPELINE_OUTBOUND_SERVER_NAME+"."+index));
                config.setNodeChannelName(prop.get(STRING,NODE_PIPELINE_OUTBOUND_SERVER_CHANNEL_NAME+"."+index));

                
                config.setHostName(prop.get(STRING,NODE_PIPELINE_OUTBOUND_SERVER_HOST_NAME+"."+index));
                config.setHostPort(prop.get(INTEGER,NODE_PIPELINE_OUTBOUND_SERVER_HOST_PORT+"."+index));
                config.setPipelineFunctionType(prop.get(PipelineFunctionType.class,ENUM,
                        NODE_PIPELINE_OUTBOUND_FUNCTION_TYPE+"."+index)); 
                config.setNotificationProtocolType( prop.get(ClientNotificationProtocolType.class,ENUM,
                        NODE_PIPELINE_OUTBOUND_ENDPOINT_TYPE+"."+index));

                int clientMax = prop.get(INTEGER,NODE_PIPELINE_OUTBOUND_CLIENT_MAX+"."+index);
                
                for(int clientIndex= 1; clientIndex <= clientMax;clientIndex++ ){
                    
                        SimpleEntry<String, Integer> clientEndPoint = new SimpleEntry<>(
                            prop.get(STRING,NODE_PIPELINE_OUTBOUND_CLIENT_HOST_NAME+"."+clientIndex),
                            prop.get(INTEGER,NODE_PIPELINE_OUTBOUND_CLIENT_HOST_PORT+"."+clientIndex)
                        );
                        config.addClientEndPoint(clientEndPoint);
                }
                
                configurationList.add(config);
                config.setConfigAsProperties(prop);
            }   
            
        }
        
        return configurationList;       
    }
    

}
