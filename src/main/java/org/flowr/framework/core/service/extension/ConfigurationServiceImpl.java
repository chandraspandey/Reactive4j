package org.flowr.framework.core.service.extension;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG;
import static org.flowr.framework.core.constants.FrameworkConstants.FRAMEWORK_SUBSCRIPTION_DEFAULT_ID;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.flowr.framework.core.config.Configuration;
import org.flowr.framework.core.config.Configuration.ConfigurationType;
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
	private String dependencyName									= ConfigurationService.class.getSimpleName();
	private DependencyType dependencyType 							= DependencyType.MANDATORY;
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
	


	
	/*public List<ServiceConfiguration> getClientEndPointConfiguration() throws ConfigurationException{
		
		return Configuration.ClientEndPointConfiguration(ConfigurationType.CLIENT.name(),configPath);
	}

	public List<ServiceConfiguration> getServerEndPointConfiguration() throws ConfigurationException{
		
		return Configuration.ServerEndPointConfiguration(ConfigurationType.SERVER.name(),configPath);
	}*/


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
	public DependencyStatus loopTest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDependencyName() {
		return this.dependencyName;
	}

	@Override
	public DependencyType getDependencyType() {
		return this.dependencyType;
	}

	@Override
	public DependencyStatus verify() {
		DependencyStatus status = DependencyStatus.UNSATISFIED;
		
		return status;
	}
	
	@Override
	public ServiceStatus startup(Properties configProperties) {
		
		return ServiceStatus.STARTED;
	}

	@Override
	public ServiceStatus shutdown(Properties configProperties) {
		// TODO Auto-generated method stub
		return ServiceStatus.STOPPED;
	}

	@Override
	public void addServiceListener(EventPublisher serviceListener) {
		// TODO Auto-generated method stub
		
	}

	public String toString(){
		
		return "ConfigurationService{"+
				" configPath : "+configPath+	
				" | serviceUnit : "+serviceUnit+	
				" | dependencyName : "+dependencyName+	
				" | dependencyType : "+dependencyType+	
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
