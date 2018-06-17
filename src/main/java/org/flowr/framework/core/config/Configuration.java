package org.flowr.framework.core.config;

import static org.flowr.framework.core.config.ConfigProperties.PropertyType.BOOLEAN;
import static org.flowr.framework.core.config.ConfigProperties.PropertyType.DOUBLE;
import static org.flowr.framework.core.config.ConfigProperties.PropertyType.ENUM;
import static org.flowr.framework.core.config.ConfigProperties.PropertyType.INTEGER;
import static org.flowr.framework.core.config.ConfigProperties.PropertyType.LONG;
import static org.flowr.framework.core.config.ConfigProperties.PropertyType.STRING;
import static org.flowr.framework.core.config.ConfigProperties.PropertyType.TIMEUNIT;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_NOTIFICATION_ENDPOINT;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_PIPELINE_BATCH_MODE;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_PIPELINE_BATCH_SIZE;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_PIPELINE_ELEMENT_MAX;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_PIPELINE_ENDPOINT_HEARTBEAT;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_PIPELINE_ENDPOINT_HEARTBEAT_UNIT;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_PIPELINE_ENDPOINT_MAX;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_PIPELINE_ENDPOINT_MIN;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_PIPELINE_ENDPOINT_NAME;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_PIPELINE_ENDPOINT_TIMEOUT;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_PIPELINE_ENDPOINT_TIMEOUT_UNIT;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_PIPELINE_ENDPOINT_TYPE;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_PIPELINE_ENDPOINT_URL;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_PIPELINE_FUNCTION_TYPE;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_PIPELINE_MAX;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_PIPELINE_MIN;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_PIPELINE_NAME;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_PIPELINE_POLICY_ELEMENT;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_REQUEST_NOTIFICATION_DELIVERY_TYPE;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_REQUEST_RETRY_INTERVAL;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_REQUEST_SCALE_INTERVAL;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_REQUEST_SCALE_MAX;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_REQUEST_SCALE_MIN;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_REQUEST_SCALE_PROGRESS_UNIT;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_REQUEST_SCALE_RETRY;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_REQUEST_SCALE_TIMEOUT;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_REQUEST_SCALE_TIMEOUT_UNIT;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_REQUEST_SCALE_UNIT;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_SERVICE_SERVER_NAME;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_SERVICE_SERVER_PORT;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_THREADS_MAX;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_THREADS_MIN;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_TIMEOUT;
import static org.flowr.framework.core.constants.ClientConstants.CONFIG_CLIENT_TIMEOUT_UNIT;
import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_NAME;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_NOTIFICATION_ENDPOINT;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_PIPELINE_BATCH_MODE;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_PIPELINE_BATCH_SIZE;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_PIPELINE_ELEMENT_MAX;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_PIPELINE_ENDPOINT_HEARTBEAT;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_PIPELINE_ENDPOINT_HEARTBEAT_UNIT;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_PIPELINE_ENDPOINT_MAX;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_PIPELINE_ENDPOINT_MIN;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_PIPELINE_ENDPOINT_NAME;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_PIPELINE_ENDPOINT_TIMEOUT;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_PIPELINE_ENDPOINT_TIMEOUT_UNIT;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_PIPELINE_ENDPOINT_TYPE;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_PIPELINE_ENDPOINT_URL;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_PIPELINE_FUNCTION_TYPE;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_PIPELINE_MAX;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_PIPELINE_MIN;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_PIPELINE_NAME;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_PIPELINE_POLICY_ELEMENT;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_PORT;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_RESPONSE_PROGRESS_NOTIFICATION_DELIVERY_TYPE;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_INTERVAL;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_MAX;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_MIN;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_TIME_UNIT;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_UNIT;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_THREADS_MAX;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_THREADS_MIN;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_TIMEOUT;
import static org.flowr.framework.core.constants.ServerConstants.CONFIG_SERVER_TIMEOUT_UNIT;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_MAPPING_ENTITY_COUNT;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CACHE_DISK_OVERFLOW;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CACHE_DISK_MAX;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CACHE_ETERNAL;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CACHE_DISK_SPOOL;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CACHE_ELEMENTS_EXPIRY_DISK;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CACHE_ELEMENTS_DISK_MAX;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CACHE_ELEMENTS_HEAP_MAX;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CACHE_ELEMENTS_MEMORY_MAX;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CACHE_ELEMENTS_TIME_TO_IDLE;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CACHE_ELEMENTS_TIME_TO_LIVE;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CACHE_NAME;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_PROVIDER;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_DB;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_JPA_VERSION;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CACHE_PATH;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CACHE_POLICY;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_QUERY_CACHE_PROVIDER;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_QUERY_CACHE_PROVIDER_FACTORY;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_QUERY_CACHE_DEFAULT;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CACHE_STATISTICS;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CACHE_STRATEGY;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_QUERY_CACHE_TIMESTAMP;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CONNECTION_DRIVER;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CONNECTION_PASSWORD;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CONNECTION_USERNAME;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CONNECTION_URL;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CONNECTION_POOL_SIZE;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_COUNT;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_DIALECT;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_MAPPING_ENTITY;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_NAME;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_QUERY_CACHE;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_QUERY_CACHE_EXTERNAL;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_QUERY_FORMAT;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_QUERY_SHOW;
import static org.flowr.framework.core.constants.DataSourceConstants.DATASOURCE_CONFIG_MIN;


import java.util.ArrayList;
import java.util.List;

import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.notification.Notification.ClientNotificationProtocolType;
import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
import org.flowr.framework.core.promise.Promisable.PromisableType;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.deferred.ProgressScale;
import org.flowr.framework.core.promise.phase.PhasedProgressScale;
import org.flowr.framework.core.promise.scheduled.ScheduledProgressScale;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Configuration {
	
	public enum ConfigurationType{
		CLIENT,
		SERVER,
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
	
	public static PipelineConfiguration DefaultPipelineConfiguration() {

		PipelineConfiguration pipelineConfiguration = new PipelineConfiguration();
		
		pipelineConfiguration.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_DEFAULT_NAME);
		pipelineConfiguration.setPipelinePolicy(PipelinePolicy.DISPOSE_AFTER_PROCESSING);
		pipelineConfiguration.setBatchMode(FrameworkConstants.FRAMEWORK_PIPELINE_BATCH_DEFAULT_MODE);
		pipelineConfiguration.setBatchSize(FrameworkConstants.FRAMEWORK_PIPELINE_BATCH_DEFAULT_MIN);
		pipelineConfiguration.setMAX_ELEMENTS(FrameworkConstants.FRAMEWORK_PIPELINE_DEFAULT_MAX_ELEMENTS);
		
		return pipelineConfiguration;
		
	}
	
	public static List<PipelineConfiguration> ClientPipelineConfiguration(String configName, String filePath) 
			throws ConfigurationException{
		
		List<PipelineConfiguration> configurationList = new ArrayList<PipelineConfiguration>();

		for(int index=CONFIG_CLIENT_PIPELINE_MIN; index < CONFIG_CLIENT_PIPELINE_MAX;index++ ){
			
			ConfigProperties prop 		= new ConfigProperties(configName,filePath);
			
			if(prop.get(STRING, CONFIG_CLIENT_PIPELINE_NAME+"."+index) != null) {
			
				PipelineConfiguration pipelineConfiguration = new PipelineConfiguration();
				
				pipelineConfiguration.setPipelineName(prop.get(STRING, CONFIG_CLIENT_PIPELINE_NAME+"."+index));
				pipelineConfiguration.setPipelinePolicy(prop.get(PipelinePolicy.class, ENUM,CONFIG_CLIENT_PIPELINE_POLICY_ELEMENT+"."+index));
				pipelineConfiguration.setBatchMode(prop.get(BOOLEAN, CONFIG_CLIENT_PIPELINE_BATCH_MODE+"."+index));
				pipelineConfiguration.setBatchSize(prop.get(INTEGER, CONFIG_CLIENT_PIPELINE_BATCH_SIZE+"."+index));
				pipelineConfiguration.setMAX_ELEMENTS(prop.get(LONG, CONFIG_CLIENT_PIPELINE_ELEMENT_MAX+"."+index));
				
				List<ServiceConfiguration> endPointconfigurationList = new ArrayList<ServiceConfiguration>();
				
				for(int eIndex=CONFIG_CLIENT_PIPELINE_ENDPOINT_MIN; eIndex < CONFIG_CLIENT_PIPELINE_ENDPOINT_MAX;eIndex++ ){
					
					if(
						( prop.get(STRING,CONFIG_CLIENT_PIPELINE_ENDPOINT_NAME+"."+eIndex) != null ) &&
						( prop.get(STRING,CONFIG_CLIENT_PIPELINE_ENDPOINT_URL+"."+eIndex)  !=null )
					){
						
						ServiceConfiguration config = new ServiceConfiguration(); 				
								
						config.setMIN_THREADS(prop.get(INTEGER,CONFIG_CLIENT_THREADS_MIN));
						config.setMAX_THREADS(prop.get(INTEGER,CONFIG_CLIENT_THREADS_MAX));
						config.setTimeout(prop.get(LONG,CONFIG_CLIENT_PIPELINE_ENDPOINT_TIMEOUT));
						config.setTimeoutTimeUnit(prop.get(TIMEUNIT, CONFIG_CLIENT_PIPELINE_ENDPOINT_TIMEOUT_UNIT));
						config.setConfigName(configName);
						config.setFilePath(filePath);
						config.setHeartbeatInterval(prop.get(LONG,CONFIG_CLIENT_PIPELINE_ENDPOINT_HEARTBEAT));
						config.setHeartbeatTimeUnit(prop.get(TIMEUNIT, CONFIG_CLIENT_PIPELINE_ENDPOINT_HEARTBEAT_UNIT));
						
						config.setServerName(prop.get(STRING,CONFIG_CLIENT_PIPELINE_ENDPOINT_NAME+"."+eIndex));
						config.setNotificationEndPoint(prop.get(STRING,CONFIG_CLIENT_PIPELINE_ENDPOINT_URL+"."+eIndex));
						config.setPipelineFunctionType(prop.get(PipelineFunctionType.class,ENUM,CONFIG_CLIENT_PIPELINE_FUNCTION_TYPE+"."+eIndex)); 
						config.setNotificationProtocolType( prop.get(ClientNotificationProtocolType.class,ENUM,CONFIG_CLIENT_PIPELINE_ENDPOINT_TYPE+"."+eIndex));
	
						endPointconfigurationList.add(config);
						
					}	
					
				}
				pipelineConfiguration.setConfigurationList(endPointconfigurationList);
				configurationList.add(pipelineConfiguration);
			}
		}
		return configurationList;		
	}
	
	public static CacheConfiguration CacheConfiguration(String configName, String filePath) 
			throws ConfigurationException{
		
		CacheConfiguration cacheConfiguration = new CacheConfiguration();
		
		ConfigProperties prop 		= new ConfigProperties(configName,filePath);
		
		cacheConfiguration.setCacheName(prop.get(STRING, DATASOURCE_CACHE_NAME));
		cacheConfiguration.setCachePath(prop.get(STRING, DATASOURCE_CACHE_PATH));
		cacheConfiguration.setCachePolicy(prop.get(STRING, DATASOURCE_CACHE_POLICY));


		cacheConfiguration.setCacheStrategy(prop.get(STRING, DATASOURCE_CACHE_STRATEGY));
		
		cacheConfiguration.setCacheStatistics(prop.get(BOOLEAN, DATASOURCE_CACHE_STATISTICS));		
		cacheConfiguration.setCacheOverFlowToDisk(prop.get(BOOLEAN, DATASOURCE_CACHE_DISK_OVERFLOW));
		cacheConfiguration.setEternal(prop.get(BOOLEAN, DATASOURCE_CACHE_ETERNAL));
		
		cacheConfiguration.setCacheDiskSpool(prop.get(LONG, DATASOURCE_CACHE_DISK_SPOOL));
		cacheConfiguration.setELEMENT_EXPIRY_DISK(prop.get(LONG, DATASOURCE_CACHE_ELEMENTS_EXPIRY_DISK));
		cacheConfiguration.setELEMENT_MAX_DISK(prop.get(LONG, DATASOURCE_CACHE_ELEMENTS_DISK_MAX));
		cacheConfiguration.setELEMENT_MAX_HEAP(prop.get(LONG, DATASOURCE_CACHE_ELEMENTS_HEAP_MAX));
		cacheConfiguration.setELEMENT_MAX_MEMORY(prop.get(LONG, DATASOURCE_CACHE_ELEMENTS_MEMORY_MAX));
		cacheConfiguration.setELEMENT_TIME_TO_IDLE(prop.get(LONG, DATASOURCE_CACHE_ELEMENTS_TIME_TO_IDLE));
		cacheConfiguration.setELEMENT_TIME_TO_LIVE(prop.get(LONG, DATASOURCE_CACHE_ELEMENTS_TIME_TO_LIVE));		
		cacheConfiguration.setCacheDiskMax(prop.get(LONG, DATASOURCE_CACHE_DISK_MAX));
		
	
		System.out.println("Configuration : cacheConfiguration : "+cacheConfiguration);
		
		return cacheConfiguration;
	}
	
	
	public static List<DataSourceConfiguration> DataSourceConfiguration(String configName, String filePath) 
			throws ConfigurationException{
		
		List<DataSourceConfiguration> dataSourceconfigurationList = new ArrayList<DataSourceConfiguration>();
		
		ConfigProperties prop 				= new ConfigProperties(configName,filePath);
		int dataSourceMax	 				= prop.get(INTEGER,DATASOURCE_COUNT);
		int dataEntityMappingMax	 		= prop.get(INTEGER,DATASOURCE_MAPPING_ENTITY_COUNT);
		
		System.out.println("Configuration : dataSourceMax :"+dataSourceMax);
		System.out.println("Configuration : dataEntityMappingMax :"+dataEntityMappingMax);
		
		List<String> mappingEntityList 		= new ArrayList<String>();
		
		for(int index=DATASOURCE_CONFIG_MIN; index <= dataEntityMappingMax;index++ ){
			
			String mappingEntityClass = prop.get(STRING, DATASOURCE_MAPPING_ENTITY+"."+index);

			if(mappingEntityClass != null) {
				mappingEntityList.add(mappingEntityClass);
			}
		}

		for(int index=DATASOURCE_CONFIG_MIN; index <= dataSourceMax;index++ ){
			
			if(prop.get(STRING, DATASOURCE_NAME+"."+index) != null) {
				
				DataSourceConfiguration dataSourceConfiguration = new DataSourceConfiguration();
				
				dataSourceConfiguration.setDataSourceName(prop.get(STRING, DATASOURCE_NAME+"."+index));
				dataSourceConfiguration.setDataSource(prop.get(STRING, DATASOURCE_DB+"."+index));				
				dataSourceConfiguration.setDataSourceProvider(prop.get(STRING, DATASOURCE_PROVIDER+"."+index));
				dataSourceConfiguration.setConnectionDriverClass(prop.get(STRING, DATASOURCE_CONNECTION_DRIVER+"."+index));
				dataSourceConfiguration.setConnectionPassword(prop.get(STRING, DATASOURCE_CONNECTION_PASSWORD+"."+index));
				dataSourceConfiguration.setConnectionURL(prop.get(STRING, DATASOURCE_CONNECTION_URL+"."+index));
				dataSourceConfiguration.setConnectionUserName(prop.get(STRING, DATASOURCE_CONNECTION_USERNAME+"."+index));
				
				dataSourceConfiguration.setDialect(prop.get(STRING, DATASOURCE_DIALECT+"."+index));
				dataSourceConfiguration.setConnectionPoolSize(prop.get(INTEGER, DATASOURCE_CONNECTION_POOL_SIZE+"."+index));
				dataSourceConfiguration.setFormatQuery(prop.get(BOOLEAN, DATASOURCE_QUERY_FORMAT+"."+index));
				dataSourceConfiguration.setShowQuery(prop.get(BOOLEAN, DATASOURCE_QUERY_SHOW+"."+index));				
				dataSourceConfiguration.setCacheQuery(prop.get(BOOLEAN, DATASOURCE_QUERY_CACHE+"."+index));
				
				dataSourceConfiguration.setCacheExternal(prop.get(BOOLEAN, DATASOURCE_QUERY_CACHE_EXTERNAL));
				dataSourceConfiguration.setCacheProviderClass(prop.get(STRING, DATASOURCE_QUERY_CACHE_PROVIDER));
				dataSourceConfiguration.setCacheProviderFactoryClass(prop.get(STRING, DATASOURCE_QUERY_CACHE_PROVIDER_FACTORY));
				dataSourceConfiguration.setCacheQueryClass(prop.get(STRING, DATASOURCE_QUERY_CACHE_DEFAULT));
				dataSourceConfiguration.setCacheTimestampClass(prop.get(STRING, DATASOURCE_QUERY_CACHE_TIMESTAMP));
				dataSourceConfiguration.setJpaVersion(prop.get(STRING, DATASOURCE_JPA_VERSION));
								
				
				if(!mappingEntityList.isEmpty()) {
					
					dataSourceConfiguration.setMappingEntityClassList(mappingEntityList);
				}
				
				dataSourceconfigurationList.add(dataSourceConfiguration);
					
			}
		}
		
		//System.out.println("Configuration : dataSourceconfigurationList : "+dataSourceconfigurationList);
		return dataSourceconfigurationList;
	}
	
	public static List<PipelineConfiguration> ServerPipelineConfiguration(String configName, String filePath) 
			throws ConfigurationException{
		
		List<PipelineConfiguration> configurationList = new ArrayList<PipelineConfiguration>();

		for(int index=CONFIG_SERVER_PIPELINE_MIN; index < CONFIG_SERVER_PIPELINE_MAX;index++ ){
			
			ConfigProperties prop 		= new ConfigProperties(configName,filePath);
			
			if(prop.get(STRING, CONFIG_SERVER_PIPELINE_NAME+"."+index) != null) {
			
				PipelineConfiguration pipelineConfiguration = new PipelineConfiguration();
				
				pipelineConfiguration.setPipelineName(prop.get(STRING, CONFIG_SERVER_PIPELINE_NAME+"."+index));
				pipelineConfiguration.setPipelinePolicy(prop.get(PipelinePolicy.class, ENUM,CONFIG_SERVER_PIPELINE_POLICY_ELEMENT+"."+index));
				pipelineConfiguration.setBatchMode(prop.get(BOOLEAN, CONFIG_SERVER_PIPELINE_BATCH_MODE+"."+index));
				pipelineConfiguration.setBatchSize(prop.get(INTEGER, CONFIG_SERVER_PIPELINE_BATCH_SIZE+"."+index));
				pipelineConfiguration.setMAX_ELEMENTS(prop.get(LONG, CONFIG_SERVER_PIPELINE_ELEMENT_MAX+"."+index));
				
				List<ServiceConfiguration> endPointconfigurationList = new ArrayList<ServiceConfiguration>();
				
				for(int eIndex=CONFIG_SERVER_PIPELINE_ENDPOINT_MIN; eIndex < CONFIG_SERVER_PIPELINE_ENDPOINT_MAX;eIndex++ ){
					
					if(
						( prop.get(STRING,CONFIG_SERVER_PIPELINE_ENDPOINT_NAME+"."+eIndex) != null ) &&
						( prop.get(STRING,CONFIG_SERVER_PIPELINE_ENDPOINT_URL+"."+eIndex)  !=null )
					){
						ServiceConfiguration config = new ServiceConfiguration(); 
						
						config.setMIN_THREADS(prop.get(INTEGER,CONFIG_SERVER_THREADS_MIN));
						config.setMAX_THREADS(prop.get(INTEGER,CONFIG_SERVER_THREADS_MAX));
						config.setTimeout(prop.get(LONG,CONFIG_SERVER_PIPELINE_ENDPOINT_TIMEOUT));
						config.setTimeoutTimeUnit(prop.get(TIMEUNIT, CONFIG_SERVER_PIPELINE_ENDPOINT_TIMEOUT_UNIT));
						config.setConfigName(configName);
						config.setFilePath(filePath);
						config.setHeartbeatInterval(prop.get(LONG,CONFIG_SERVER_PIPELINE_ENDPOINT_HEARTBEAT));
						config.setHeartbeatTimeUnit(prop.get(TIMEUNIT, CONFIG_SERVER_PIPELINE_ENDPOINT_HEARTBEAT_UNIT));
						
						config.setServerName(prop.get(STRING,CONFIG_SERVER_PIPELINE_ENDPOINT_NAME+"."+eIndex));
						config.setNotificationEndPoint(prop.get(STRING,CONFIG_SERVER_PIPELINE_ENDPOINT_URL+"."+eIndex));
						config.setPipelineFunctionType(prop.get(PipelineFunctionType.class,ENUM,CONFIG_SERVER_PIPELINE_FUNCTION_TYPE+"."+eIndex)); 
						config.setNotificationProtocolType( prop.get(ServerNotificationProtocolType.class,ENUM,CONFIG_SERVER_PIPELINE_ENDPOINT_TYPE+"."+eIndex));
						
						endPointconfigurationList.add(config);
					}	
				}
				
				pipelineConfiguration.setConfigurationList(endPointconfigurationList);
				configurationList.add(pipelineConfiguration);
			}
		}
		return configurationList;		
	}
	
	public static List<ServiceConfiguration> ClientEndPointConfiguration(String configName, String filePath) 
			throws ConfigurationException{
			
		List<ServiceConfiguration> configurationList = new ArrayList<ServiceConfiguration>();
		
		
		for(int index=CONFIG_CLIENT_PIPELINE_ENDPOINT_MIN; index < CONFIG_CLIENT_PIPELINE_ENDPOINT_MAX;index++ ){
			
			ConfigProperties prop 		= new ConfigProperties(configName,filePath);
			
		
			if(
				( prop.get(STRING,CONFIG_CLIENT_PIPELINE_ENDPOINT_NAME+"."+index) != null ) &&
				( prop.get(STRING,CONFIG_CLIENT_PIPELINE_ENDPOINT_URL+"."+index)  !=null )
			){
				
				ServiceConfiguration config = new ServiceConfiguration(); 				
						
				config.setMIN_THREADS(prop.get(INTEGER,CONFIG_CLIENT_THREADS_MIN));
				config.setMAX_THREADS(prop.get(INTEGER,CONFIG_CLIENT_THREADS_MAX));
				config.setTimeout(prop.get(LONG,CONFIG_CLIENT_PIPELINE_ENDPOINT_TIMEOUT));
				config.setTimeoutTimeUnit(prop.get(TIMEUNIT, CONFIG_CLIENT_PIPELINE_ENDPOINT_TIMEOUT_UNIT));
				config.setConfigName(configName);
				config.setFilePath(filePath);
				config.setHeartbeatInterval(prop.get(LONG,CONFIG_CLIENT_PIPELINE_ENDPOINT_HEARTBEAT));
				config.setHeartbeatTimeUnit(prop.get(TIMEUNIT, CONFIG_CLIENT_PIPELINE_ENDPOINT_HEARTBEAT_UNIT));
				
				config.setServerName(prop.get(STRING,CONFIG_CLIENT_PIPELINE_ENDPOINT_NAME+"."+index));
				config.setNotificationEndPoint(prop.get(STRING,CONFIG_CLIENT_PIPELINE_ENDPOINT_URL+"."+index));
				config.setPipelineFunctionType(prop.get(PipelineFunctionType.class,ENUM,CONFIG_CLIENT_PIPELINE_FUNCTION_TYPE+"."+index)); 
				config.setNotificationProtocolType( prop.get(ClientNotificationProtocolType.class,ENUM,CONFIG_CLIENT_PIPELINE_ENDPOINT_TYPE+"."+index));

				configurationList.add(config);
				config.setConfigAsProperties(prop);
			}	
			
		}
		
		//System.out.println("Configuration : ClientEndPointConfiguration : "+configurationList);
		
		return configurationList;		
	}
	
	public static List<ServiceConfiguration> ServerEndPointConfiguration(String configName, String filePath) 
			throws ConfigurationException{
			
		List<ServiceConfiguration> configurationList = new ArrayList<ServiceConfiguration>();
		
		ConfigProperties prop 		= new ConfigProperties(configName,filePath);
		
		for(int index=CONFIG_SERVER_PIPELINE_ENDPOINT_MIN; index < CONFIG_SERVER_PIPELINE_ENDPOINT_MAX;index++ ){
					
			if(
				( prop.get(STRING,CONFIG_SERVER_PIPELINE_ENDPOINT_NAME+"."+index) != null ) &&
				( prop.get(STRING,CONFIG_SERVER_PIPELINE_ENDPOINT_URL+"."+index)  !=null )
			){
				ServiceConfiguration config = new ServiceConfiguration(); 
				
				config.setMIN_THREADS(prop.get(INTEGER,CONFIG_SERVER_THREADS_MIN));
				config.setMAX_THREADS(prop.get(INTEGER,CONFIG_SERVER_THREADS_MAX));
				config.setTimeout(prop.get(LONG,CONFIG_SERVER_PIPELINE_ENDPOINT_TIMEOUT));
				config.setTimeoutTimeUnit(prop.get(TIMEUNIT, CONFIG_SERVER_PIPELINE_ENDPOINT_TIMEOUT_UNIT));
				config.setConfigName(configName);
				config.setFilePath(filePath);
				config.setHeartbeatInterval(prop.get(LONG,CONFIG_SERVER_PIPELINE_ENDPOINT_HEARTBEAT));
				config.setHeartbeatTimeUnit(prop.get(TIMEUNIT, CONFIG_SERVER_PIPELINE_ENDPOINT_HEARTBEAT_UNIT));
				
				config.setServerName(prop.get(STRING,CONFIG_SERVER_PIPELINE_ENDPOINT_NAME+"."+index));
				config.setNotificationEndPoint(prop.get(STRING,CONFIG_SERVER_PIPELINE_ENDPOINT_URL+"."+index));
				config.setPipelineFunctionType(prop.get(PipelineFunctionType.class,ENUM,CONFIG_SERVER_PIPELINE_FUNCTION_TYPE+"."+index)); 
				config.setNotificationProtocolType( prop.get(ServerNotificationProtocolType.class,ENUM,CONFIG_SERVER_PIPELINE_ENDPOINT_TYPE+"."+index));

				
				configurationList.add(config);
				config.setConfigAsProperties(prop);
			}			
		}
		
		System.out.println("Configuration : ServerEndPointConfiguration : "+configurationList);
		
		return configurationList;		
	}
	
	public static ServiceConfiguration ClientConfiguration(String configName, String filePath) 
			throws ConfigurationException{
			
		ServiceConfiguration config = new ServiceConfiguration(); 
	
		ConfigProperties prop 		= new ConfigProperties(configName,filePath);
		config.setServerName(prop.get(STRING,CONFIG_CLIENT_SERVICE_SERVER_NAME));
		config.setServerPort(prop.get(INTEGER,CONFIG_CLIENT_SERVICE_SERVER_PORT));
		config.setMIN_THREADS(prop.get(INTEGER,CONFIG_CLIENT_THREADS_MIN));
		config.setMAX_THREADS(prop.get(INTEGER,CONFIG_CLIENT_THREADS_MAX));
		config.setTimeout(prop.get(LONG,CONFIG_CLIENT_TIMEOUT));
		config.setTimeoutTimeUnit(prop.get(TIMEUNIT, CONFIG_CLIENT_TIMEOUT_UNIT));
		config.setNotificationEndPoint(prop.get(STRING,CONFIG_CLIENT_NOTIFICATION_ENDPOINT));
		config.setConfigName(configName);
		config.setFilePath(filePath);
		
		config.setConfigAsProperties(prop);
		
		return config;		
	}
	
	public static ServiceConfiguration ServerConfiguration(String configName, String filePath) throws ConfigurationException{
		
		ServiceConfiguration config = new ServiceConfiguration();
		
		ConfigProperties prop 		= new ConfigProperties(configName,filePath);
		config.setServerName(prop.get(STRING,CONFIG_SERVER_NAME));
		config.setServerPort(prop.get(INTEGER,CONFIG_SERVER_PORT));
		config.setMIN_THREADS(prop.get(INTEGER,CONFIG_SERVER_THREADS_MIN));
		config.setMAX_THREADS(prop.get(INTEGER,CONFIG_SERVER_THREADS_MAX));
		config.setTimeout(prop.get(LONG,CONFIG_SERVER_TIMEOUT));
		config.setTimeoutTimeUnit(prop.get(TIMEUNIT, CONFIG_SERVER_TIMEOUT_UNIT));
		config.setNotificationEndPoint(prop.get(STRING,CONFIG_SERVER_NOTIFICATION_ENDPOINT));
		config.setConfigName(configName);
		config.setFilePath(filePath);
		
		config.setConfigAsProperties(prop);
			
		return config;
	}
	
	public static RequestScale RequestScale(String clientSubscriptionId,String fileName, String filePath) 
			throws ConfigurationException{
		
		RequestScale requestScale = new RequestScale();
		
		ConfigProperties prop = new ConfigProperties(fileName,filePath);		
		
		requestScale.setSubscriptionClientId(clientSubscriptionId);
		requestScale.setMIN_SCALE(prop.get(DOUBLE, CONFIG_CLIENT_REQUEST_SCALE_MIN));
		requestScale.setMAX_SCALE(prop.get(DOUBLE, CONFIG_CLIENT_REQUEST_SCALE_MAX));
		requestScale.setRetryCount(prop.get(INTEGER, CONFIG_CLIENT_REQUEST_SCALE_RETRY));
		
		requestScale.setTimeout(prop.get(LONG, CONFIG_CLIENT_REQUEST_SCALE_TIMEOUT));
		requestScale.setTimeoutTimeUnit(prop.get(TIMEUNIT, CONFIG_CLIENT_REQUEST_SCALE_TIMEOUT_UNIT));
		requestScale.setScaleUnit(prop.get(DOUBLE, CONFIG_CLIENT_REQUEST_SCALE_UNIT));
		requestScale.setProgressTimeUnit(prop.get(TIMEUNIT, CONFIG_CLIENT_REQUEST_SCALE_PROGRESS_UNIT));
		requestScale.setSamplingInterval(prop.get(LONG, CONFIG_CLIENT_REQUEST_SCALE_INTERVAL));
		requestScale.setRetryInterval(prop.get(LONG, CONFIG_CLIENT_REQUEST_RETRY_INTERVAL));		
	//	requestScale.setNotificationDeliveryType(prop.get(ENUM, CONFIG_CLIENT_REQUEST_NOTIFICATION_DELIVERY_TYPE));	
		requestScale.setNotificationDeliveryType(prop.get(NotificationDeliveryType.EXTERNAL.getClass(), ENUM,CONFIG_CLIENT_REQUEST_NOTIFICATION_DELIVERY_TYPE));	
		
		
		
		if(!requestScale.isValid()){
			
			throw new ConfigurationException(
					ERR_CONFIG,
					MSG_CONFIG, 
					"Mandatory Configurations not provided for RequestScale. Timeout must exceed "+
							requestScale.getDefaultTimeout()+" milli seconds. : "+
							prop);
		}
		
		return requestScale;
	}
	
	// Need to remove
	public static Scale PhasedProgressScale(PromisableType promisableType,String clientSubscriptionId,String fileName, String filePath) 
			throws ConfigurationException{
	
		//System.out.println("Configuration : PhasedProgressScale : promisableType : "+promisableType);
		Scale scale = new PhasedProgressScale();
		scale.setSubscriptionClientId(clientSubscriptionId);
		
		return populateProperties(scale,fileName,filePath);
	}
	
	// Need to remove
	public static Scale ProgressScale(PromisableType promisableType,String clientSubscriptionId,String fileName, String filePath) 
			throws ConfigurationException{
	
		//System.out.println("Configuration : ProgressScale : promisableType : "+promisableType);
		Scale scale = new ProgressScale();
		scale.setSubscriptionClientId(clientSubscriptionId);
		
		return populateProperties(scale,fileName,filePath);
	}
	
	// Need to remove
	public static Scale ScheduledProgressScale(PromisableType promisableType,String clientSubscriptionId,String fileName, String filePath) 
			throws ConfigurationException{
	
		//System.out.println("Configuration : ScheduledProgressScale : promisableType : "+promisableType);
		Scale scale = new ScheduledProgressScale();
		scale.setSubscriptionClientId(clientSubscriptionId);
		
		return populateProperties(scale,fileName,filePath);
	}
	
	public static Scale populateProperties(Scale scale,String filePath) throws ConfigurationException{
		
		ConfigProperties prop = new ConfigProperties(filePath);			
		
		scale.setMIN_SCALE(prop.get(DOUBLE, CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_MIN));
		scale.setMAX_SCALE(prop.get(DOUBLE, CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_MAX));

		scale.setScaleUnit(prop.get(DOUBLE, CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_UNIT));
		scale.setProgressTimeUnit(prop.get(TIMEUNIT, CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_TIME_UNIT));
		scale.setINTERVAL(prop.get(LONG, CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_INTERVAL));
		scale.setNotificationDeliveryType(prop.get(NotificationDeliveryType.EXTERNAL.getClass(), ENUM,CONFIG_SERVER_RESPONSE_PROGRESS_NOTIFICATION_DELIVERY_TYPE));	

		return scale;
	}
	
	// Need to remove
	public static Scale populateProperties(Scale scale,String fileName, String filePath) throws ConfigurationException{
				
		ConfigProperties prop = new ConfigProperties(filePath);			
		
		scale.setMIN_SCALE(prop.get(DOUBLE, CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_MIN));
		scale.setMAX_SCALE(prop.get(DOUBLE, CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_MAX));

		scale.setScaleUnit(prop.get(DOUBLE, CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_UNIT));
		scale.setProgressTimeUnit(prop.get(TIMEUNIT, CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_TIME_UNIT));
		scale.setINTERVAL(prop.get(LONG, CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_INTERVAL));
		scale.setNotificationDeliveryType(prop.get(NotificationDeliveryType.EXTERNAL.getClass(), ENUM,CONFIG_SERVER_RESPONSE_PROGRESS_NOTIFICATION_DELIVERY_TYPE));	

		return scale;
	}
}
