package org.flowr.framework.core.service.internal;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG;
import static org.flowr.framework.core.constants.FrameworkConstants.FRAMEWORK_SUBSCRIPTION_DEFAULT_ID;

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
import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.promise.Promisable.PromisableType;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.deferred.ProgressScale;
import org.flowr.framework.core.promise.phase.PhasedProgressScale;
import org.flowr.framework.core.promise.scheduled.ScheduledProgressScale;
import org.flowr.framework.core.service.ServiceFramework;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ConfigurationServiceImpl implements ConfigurationService{

	private static String configPath								= null;
	private ServiceUnit serviceUnit 								= ServiceUnit.SINGELTON;
	private String serviceName										= FrameworkConstants.FRAMEWORK_SERVICE_CONFIGURATION;
	private ServiceType serviceType									= ServiceType.CONFIGURATION;
	private ServiceConfiguration clientConfiguration 				= null;
	private ServiceConfiguration serverConfiguration 				= null;	
	private List<PipelineConfiguration> clientPipelineConfiguration = null;
	private List<PipelineConfiguration> serverPipelineConfiguration = null;
	private ProgressScale progressScale								= null;
	private PhasedProgressScale phasedProgressScale					= null;
	private ScheduledProgressScale scheduledProgressScale			= null;
	private RequestScale requestScale								= null;
	private CacheConfiguration cacheConfiguration 					= null;
	private List<DataSourceConfiguration> dataSourceList 			= null; 
	private List<NodeServiceConfiguration> nodeInboundConfiguration	= null;
	private List<NodeServiceConfiguration> nodeOutboundConfiguration= null;
	
	@SuppressWarnings("unused")
	private ServiceFramework<?,?> serviceFramework			= null;
	
	@Override
	public void setServiceFramework(ServiceFramework<?,?> serviceFramework) {
		this.serviceFramework = serviceFramework;
	}
	
	public void configure(String configurationFilePath) throws ConfigurationException{
		
		if(configurationFilePath != null && ! configurationFilePath.isEmpty() && new File(configurationFilePath).exists()) {
			
			configPath = configurationFilePath;
			loadAdapterConfiguration();
			loadPipelineConfiguration();
			loadProgressScaleConfiguration();
			getRequestScale(FRAMEWORK_SUBSCRIPTION_DEFAULT_ID);
			loadDataSourceConfiguration();
			loadCacheConfiguration();
			loadIntegrationConfiguration();
		}else {
		
			throw new ConfigurationException(
				ERR_CONFIG,
				MSG_CONFIG, 
				"Invalid configuration file path :"+configurationFilePath);
		}
	}
	
	public void reload() {
		clientConfiguration 		= null;
		serverConfiguration 		= null;
		clientPipelineConfiguration = null;
		serverPipelineConfiguration = null;
		progressScale				= null;
		phasedProgressScale			= null;
		scheduledProgressScale		= null;
		requestScale				= null;
		cacheConfiguration			= null;
		dataSourceList				= null;
		nodeInboundConfiguration	= null;
		nodeOutboundConfiguration	= null;
	}
	
	public void loadDataSourceConfiguration() throws ConfigurationException{
			
		dataSourceList = Configuration.DataSourceConfiguration(ConfigurationType.CLIENT.name(), configPath);
	}
	
	public void loadCacheConfiguration() throws ConfigurationException{
			
		cacheConfiguration = Configuration.CacheConfiguration(ConfigurationType.CLIENT.name(), configPath);
	}

	@Override
	public CacheConfiguration getCacheConfiguration() {
		
		return cacheConfiguration;
	}
	
	@Override
	public List<DataSourceConfiguration> getDataSourceConfiguration(){
		
		return dataSourceList;
	}
	
	
	@Override	
	public List<PipelineConfiguration> getPipelineConfiguration(ConfigurationType configurationType) throws ConfigurationException{
		
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
		
		if(requestScale == null ) {
			requestScale = (RequestScale) Configuration.RequestScale(
					clientSubscriptionId,
					ConfigurationType.CLIENT.name(),
					configPath
				);	
		}
		requestScale.setSubscriptionClientId(clientSubscriptionId);
		
		return requestScale;		
	}
	
	public void loadAdapterConfiguration() throws ConfigurationException {
		
		Iterator<ConfigurationType> iter = Arrays.asList(ConfigurationType.values()).iterator();
		
		while(iter.hasNext()) {
			
			switch(iter.next()) {
				case CLIENT:
					clientConfiguration = Configuration.ClientConfiguration(ConfigurationType.CLIENT.name(),configPath);
					break;
				case SERVER:
					serverConfiguration = Configuration.ServerConfiguration(ConfigurationType.SERVER.name(),configPath);
					break;
				default:
					break;		
			}
		}
	}
	
	@Override
	public ServiceConfiguration getServiceConfiguration(ConfigurationType configurationType) throws ConfigurationException{
		
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
	public List<NodeServiceConfiguration> getIntegrationServiceConfiguration(ConfigurationType configurationType) throws ConfigurationException{
		
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
					nodeInboundConfiguration = Configuration.NodeInboundEndPointConfiguration(
							ConfigurationType.INTEGRATION_INBOUND.name(), configPath);
					break;			
				}case INTEGRATION_OUTBOUND:{
					nodeOutboundConfiguration = Configuration.NodeOutboundEndPointConfiguration(
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
					clientPipelineConfiguration = Configuration.ClientPipelineConfiguration(
							ConfigurationType.CLIENT.name(), configPath);
					break;
				case SERVER:
					serverPipelineConfiguration = Configuration.ServerPipelineConfiguration(
							ConfigurationType.SERVER.name(), configPath);
					break;
				default:
					break;		
			}
		}
	}
	
	public void loadProgressScaleConfiguration() throws ConfigurationException {
		
		Iterator<PromisableType> iter = Arrays.asList(PromisableType.values()).iterator();
		
		while(iter.hasNext()) {
			
				switch(iter.next()) {

					case PROMISE:
						progressScale = (ProgressScale) Configuration.ProgressScale(
											PromisableType.PROMISE_DEFFERED,
											FRAMEWORK_SUBSCRIPTION_DEFAULT_ID,
											ConfigurationType.SERVER.name(),
											configPath
										);	
						break;
					case PROMISE_DEFFERED:
						progressScale = (ProgressScale) Configuration.ProgressScale(
											PromisableType.PROMISE_DEFFERED,
											FRAMEWORK_SUBSCRIPTION_DEFAULT_ID,
											ConfigurationType.SERVER.name(),
											configPath
										);				
						break;
					case PROMISE_PHASED:
						phasedProgressScale = (PhasedProgressScale) Configuration.PhasedProgressScale(
												PromisableType.PROMISE_PHASED,
												FRAMEWORK_SUBSCRIPTION_DEFAULT_ID,
												ConfigurationType.SERVER.name(),
												configPath
											);
						break;
					case PROMISE_SCHEDULED:
			
					scheduledProgressScale = (ScheduledProgressScale) Configuration.ScheduledProgressScale(
												PromisableType.PROMISE_SCHEDULED,
												FRAMEWORK_SUBSCRIPTION_DEFAULT_ID,
												ConfigurationType.SERVER.name(),
												configPath
											);				
						break;
					case PROMISE_STAGED:
						break;
					default:
						break;
				
			}
		}

	}
	
	@Override
	public Scale getProgressScale(PromisableType promisableType, String clientSubscriptionId) throws ConfigurationException{
		
		Scale scale = null;
		
		switch(promisableType) {

			case PROMISE:
				scale = progressScale;	
				break;
			case PROMISE_DEFFERED:
				scale = progressScale;				
				break;
			case PROMISE_PHASED:
				scale = phasedProgressScale;
				break;
			case PROMISE_SCHEDULED:
				scale = scheduledProgressScale;
				break;
			case PROMISE_STAGED:
				break;
			default:
				scale = progressScale;	
				break;
		
		}
		scale.setSubscriptionClientId(clientSubscriptionId);
		
		return scale;
	}
	
	
	@Override
	public void setServiceType(ServiceType serviceType) {
		
		this.serviceType = serviceType;
	}
	
	@Override
	public ServiceType getServiceType() {
		
		return this.serviceType;
	}
	@Override
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	@Override
	public String getServiceName() {

		return this.serviceName;
	}		
	
	@Override
	public void setServiceUnit(ServiceUnit serviceUnit) {
		this.serviceUnit = serviceUnit;
	}

	@Override
	public ServiceUnit getServiceUnit() {
		return this.serviceUnit;
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
	public void addServiceListener(EventPublisher serviceListener) {
			
	}

	public String toString(){
		
		return "ConfigurationService{"+
				" configPath : "+configPath+	
				" | serviceUnit : "+serviceUnit+	
				" | serviceName : "+serviceName+	
				" | serviceType : "+serviceType+	
				" | \n clientConfiguration : "+clientConfiguration+	
				" | \n serverConfiguration : "+serverConfiguration+	
				" | \n  clientPipelineConfiguration : "+clientPipelineConfiguration+	
				" | \n serverPipelineConfiguration : "+serverPipelineConfiguration+	
				" | progressScale : "+progressScale+	
				" | phasedProgressScale : "+phasedProgressScale+	
				" | scheduledProgressScale : "+scheduledProgressScale+	
				" | requestScale : "+requestScale+	
				"}\n";
	}


	
}
