package org.flowr.framework.core.config;

import static org.flowr.framework.core.config.ConfigProperties.PropertyType.*;

import static org.flowr.framework.core.constants.ClientConstants.*;
import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG;
import static org.flowr.framework.core.constants.ServerConstants.*;

import java.util.ArrayList;
import java.util.List;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.Promisable.PromisableType;
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
		
		pipelineConfiguration.setPipelineName("DEFAULT");
		pipelineConfiguration.setPipelinePolicy(PipelinePolicy.DISPOSE_AFTER_PROCESSING);
		pipelineConfiguration.setBatchMode(false);
		pipelineConfiguration.setBatchSize(0);
		pipelineConfiguration.setMAX_ELEMENTS(10000);
		
		return pipelineConfiguration;
		
	}
	
	public static List<PipelineConfiguration> ClientPipelineConfiguration(String configName, String filePath) 
			throws ConfigurationException{
		
		List<PipelineConfiguration> configurationList = new ArrayList<PipelineConfiguration>();

		for(int index=CONFIG_CLIENT_PIPELINE_MIN; index < CONFIG_CLIENT_PIPELINE_MAX;index++ ){
			
			ConfigProperties prop 		= new ConfigProperties(configName,filePath);
			
			PipelineConfiguration pipelineConfiguration = new PipelineConfiguration();
			
			pipelineConfiguration.setPipelineName(prop.get(STRING, CONFIG_CLIENT_PIPELINE_NAME+"."+index));
			pipelineConfiguration.setPipelinePolicy(prop.get(PipelinePolicy.class, ENUM,CONFIG_CLIENT_PIPELINE_POLICY_ELEMENT+"."+index));
			pipelineConfiguration.setBatchMode(prop.get(BOOLEAN, CONFIG_CLIENT_PIPELINE_BATCH_MODE+"."+index));
			pipelineConfiguration.setBatchSize(prop.get(INTEGER, CONFIG_CLIENT_PIPELINE_BATCH_SIZE+"."+index));
			pipelineConfiguration.setMAX_ELEMENTS(prop.get(LONG, CONFIG_CLIENT_PIPELINE_ELEMENT_MAX+"."+index));
			
			pipelineConfiguration.setConfigAsProperties(prop);
		}
		return configurationList;		
	}
	
	public static List<PipelineConfiguration> ServerPipelineConfiguration(String configName, String filePath) 
			throws ConfigurationException{
		
		List<PipelineConfiguration> configurationList = new ArrayList<PipelineConfiguration>();

		for(int index=CONFIG_SERVER_PIPELINE_MIN; index < CONFIG_SERVER_PIPELINE_MAX;index++ ){
			
			ConfigProperties prop 		= new ConfigProperties(configName,filePath);
			
			PipelineConfiguration pipelineConfiguration = new PipelineConfiguration();
			
			pipelineConfiguration.setPipelineName(prop.get(STRING, CONFIG_SERVER_PIPELINE_NAME+"."+index));
			pipelineConfiguration.setPipelinePolicy(prop.get(PipelinePolicy.class, ENUM,CONFIG_SERVER_PIPELINE_POLICY_ELEMENT+"."+index));
			pipelineConfiguration.setBatchMode(prop.get(BOOLEAN, CONFIG_SERVER_PIPELINE_BATCH_MODE+"."+index));
			pipelineConfiguration.setBatchSize(prop.get(INTEGER, CONFIG_SERVER_PIPELINE_BATCH_SIZE+"."+index));
			pipelineConfiguration.setMAX_ELEMENTS(prop.get(LONG, CONFIG_SERVER_PIPELINE_ELEMENT_MAX+"."+index));
			
			pipelineConfiguration.setConfigAsProperties(prop);
		}
		return configurationList;		
	}
	
	public static List<ServiceConfiguration> ClientEndPointConfiguration(String configName, String filePath) 
			throws ConfigurationException{
			
		List<ServiceConfiguration> configurationList = new ArrayList<ServiceConfiguration>();
		
		
		for(int index=CONFIG_CLIENT_SERVICE_ENDPOINT_MIN; index < CONFIG_CLIENT_SERVICE_ENDPOINT_MAX;index++ ){
			
			ConfigProperties prop 		= new ConfigProperties(configName,filePath);
			
		
			if(
				( prop.get(STRING,CONFIG_CLIENT_SERVICE_ENDPOINT_NAME+"."+index) != null ) &&
				( prop.get(STRING,CONFIG_CLIENT_SERVICE_ENDPOINT_URL+"."+index)  !=null )
			){
				
				ServiceConfiguration config = new ServiceConfiguration(); 				
						
				config.setMIN_THREADS(prop.get(INTEGER,CONFIG_CLIENT_THREADS_MIN));
				config.setMAX_THREADS(prop.get(INTEGER,CONFIG_CLIENT_THREADS_MAX));
				config.setTimeout(prop.get(LONG,CONFIG_CLIENT_SERVICE_ENDPOINT_TIMEOUT));
				config.setTimeoutTimeUnit(prop.get(TIMEUNIT, CONFIG_CLIENT_SERVICE_ENDPOINT_TIMEOUT_UNIT));
				config.setConfigName(configName);
				config.setFilePath(filePath);
				config.setHeartbeatInterval(prop.get(LONG,CONFIG_CLIENT_SERVICE_ENDPOINT_HEARTBEAT));
				config.setHeartbeatTimeUnit(prop.get(TIMEUNIT, CONFIG_CLIENT_SERVICE_ENDPOINT_HEARTBEAT_UNIT));
				
				config.setServerName(prop.get(STRING,CONFIG_CLIENT_SERVICE_ENDPOINT_NAME+"."+index));
				config.setNotificationEndPoint(prop.get(STRING,CONFIG_CLIENT_SERVICE_ENDPOINT_URL+"."+index));
				config.setEndPointType(prop.get(STRING,CONFIG_CLIENT_SERVICE_ENDPOINT_TYPE+"."+index));
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
		
		for(int index=CONFIG_SERVER_SERVICE_ENDPOINT_MIN; index < CONFIG_SERVER_SERVICE_ENDPOINT_MAX;index++ ){
					
			if(
				( prop.get(STRING,CONFIG_SERVER_SERVICE_ENDPOINT_NAME+"."+index) != null ) &&
				( prop.get(STRING,CONFIG_SERVER_SERVICE_ENDPOINT_URL+"."+index)  !=null )
			){
				ServiceConfiguration config = new ServiceConfiguration(); 
				
				config.setMIN_THREADS(prop.get(INTEGER,CONFIG_SERVER_THREADS_MIN));
				config.setMAX_THREADS(prop.get(INTEGER,CONFIG_SERVER_THREADS_MAX));
				config.setTimeout(prop.get(LONG,CONFIG_SERVER_SERVICE_ENDPOINT_TIMEOUT));
				config.setTimeoutTimeUnit(prop.get(TIMEUNIT, CONFIG_SERVER_SERVICE_ENDPOINT_TIMEOUT_UNIT));
				config.setConfigName(configName);
				config.setFilePath(filePath);
				config.setHeartbeatInterval(prop.get(LONG,CONFIG_SERVER_SERVICE_ENDPOINT_HEARTBEAT));
				config.setHeartbeatTimeUnit(prop.get(TIMEUNIT, CONFIG_SERVER_SERVICE_ENDPOINT_HEARTBEAT_UNIT));
				
				config.setServerName(prop.get(STRING,CONFIG_SERVER_SERVICE_ENDPOINT_NAME+"."+index));
				config.setNotificationEndPoint(prop.get(STRING,CONFIG_SERVER_SERVICE_ENDPOINT_URL+"."+index));
				config.setEndPointType(prop.get(STRING,CONFIG_SERVER_SERVICE_ENDPOINT_TYPE+"."+index));
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
	
	public static Scale PhasedProgressScale(PromisableType promisableType,String clientSubscriptionId,String fileName, String filePath) 
			throws ConfigurationException{
	
		//System.out.println("Configuration : PhasedProgressScale : promisableType : "+promisableType);
		Scale scale = new PhasedProgressScale();
		scale.setSubscriptionClientId(clientSubscriptionId);
		
		return populateProperties(scale,fileName,filePath);
	}
	
	public static Scale ProgressScale(PromisableType promisableType,String clientSubscriptionId,String fileName, String filePath) 
			throws ConfigurationException{
	
		//System.out.println("Configuration : ProgressScale : promisableType : "+promisableType);
		Scale scale = new ProgressScale();
		scale.setSubscriptionClientId(clientSubscriptionId);
		
		return populateProperties(scale,fileName,filePath);
	}
	
	public static Scale ScheduledProgressScale(PromisableType promisableType,String clientSubscriptionId,String fileName, String filePath) 
			throws ConfigurationException{
	
		//System.out.println("Configuration : ScheduledProgressScale : promisableType : "+promisableType);
		Scale scale = new ScheduledProgressScale();
		scale.setSubscriptionClientId(clientSubscriptionId);
		
		return populateProperties(scale,fileName,filePath);
	}
	
	static Scale populateProperties(Scale scale,String fileName, String filePath) throws ConfigurationException{
		
		
		ConfigProperties prop = new ConfigProperties(fileName,filePath);			
		
		scale.setMIN_SCALE(prop.get(DOUBLE, CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_MIN));
		scale.setMAX_SCALE(prop.get(DOUBLE, CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_MAX));

		scale.setScaleUnit(prop.get(DOUBLE, CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_UNIT));
		scale.setProgressTimeUnit(prop.get(TIMEUNIT, CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_TIME_UNIT));
		scale.setINTERVAL(prop.get(LONG, CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_INTERVAL));
		scale.setNotificationDeliveryType(prop.get(NotificationDeliveryType.EXTERNAL.getClass(), ENUM,CONFIG_SERVER_RESPONSE_PROGRESS_NOTIFICATION_DELIVERY_TYPE));	

		return scale;
	}
}
