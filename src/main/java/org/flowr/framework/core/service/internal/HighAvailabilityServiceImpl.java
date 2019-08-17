package org.flowr.framework.core.service.internal;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.event.pipeline.EventPipeline;
import org.flowr.framework.core.event.pipeline.ManagedEventPipelineBusExecutor;
import org.flowr.framework.core.exception.ClientException;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.node.ha.Circuit;
import org.flowr.framework.core.node.ha.Circuit.CircuitStatus;
import org.flowr.framework.core.node.ha.IntegratedCircuit;
import org.flowr.framework.core.process.management.ManagedProcessHandler;
import org.flowr.framework.core.service.ServiceEndPoint;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.dependency.Dependency;
import org.flowr.framework.core.service.dependency.DependencyLoop;
import org.flowr.framework.core.service.internal.EventService.EventRegistrationStatus;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class HighAvailabilityServiceImpl implements HighAvailabilityService,Dependency, DependencyLoop{

	private ServiceUnit serviceUnit 								= ServiceUnit.SINGELTON;
	private String dependencyName									= HighAvailabilityService.class.getSimpleName();
	private DependencyType dependencyType 							= DependencyType.MANDATORY;
	private String serviceName										= FrameworkConstants.FRAMEWORK_SERVICE_HEALTH;
	private ServiceType serviceType									= ServiceType.HEALTH;	
	private ManagedEventPipelineBusExecutor eventBusExecutor 		= ManagedService.getDefaultEventPipelineBusExecutor();
	private ServiceFramework<?,?> serviceFramework					= null;
	private ManagedProcessHandler managedProcessHandler 			= ManagedService.getDefaultProcessHandler();
	private Circuit clientCircuit 									= null;
	private Circuit serverCircuit 									= null;	
	private Circuit externalCircuit 								= null;
	private ServiceStatus serviceStatus								= ServiceStatus.UNUSED;
	private ExecutorService service 								= Executors.newSingleThreadExecutor();
	
	public EventRegistrationStatus registerEventPipeline() {		
		
		EventRegistrationStatus status = EventRegistrationStatus.UNREGISTERED;
	
		if(!managedProcessHandler.isSubscribed()) {
		
			EventPipeline processSubscriber  =  ManagedService.getDefaultEventPipeline();
			
			if(processSubscriber != null) {
				managedProcessHandler.subscribe(processSubscriber);
				status = EventRegistrationStatus.REGISTERED;
			}
		}else {
			status = EventRegistrationStatus.REGISTERED;
		}		
		return status;
	}
	
	
	
	@Override
	public void setServiceFramework(ServiceFramework<?,?> serviceFramework) {
		
		this.serviceFramework = serviceFramework;
		
		this.registerEventPipeline();		
	}
	
	@Override
	public void run() {
		
		while(serviceStatus != ServiceStatus.STOPPED) {
			
			try {
				process();
				Thread.sleep(FrameworkConstants.FRAMEWORK_PIPELINE_NOTIFICATION_TIME_UNIT);
				//System.out.println("HighAvailabilityService : "+dependencyName);
			} catch (ClientException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void process() throws ClientException {
		
		this.serviceFramework.getNotificationService().notify(eventBusExecutor.process());
	}
	
	
	@Override
	public Circuit getCircuit(ConfigurationType configurationType) {
		
		Circuit circuit = null;
		
		switch(configurationType) {
		
			case CLIENT:{
				circuit = clientCircuit;
				break;
			}case SERVER:{
				circuit = serverCircuit;
				break;
			}case EXTERNAL:{
				circuit = externalCircuit;
				break;
			}default:{
				break;
			}
		}
		
		return circuit;
	}
	
	public void addExternalCircuit(Circuit externalCircuit) {
		
		this.externalCircuit = externalCircuit;
	}
	
	
	@Override
	public void buildCircuit() throws ConfigurationException {
				
		Iterator<ConfigurationType> iter = Arrays.asList(ConfigurationType.values()).iterator();
						
			while(iter.hasNext()) {
				
				ConfigurationType configurationType = iter.next();
				
				switch(configurationType) {
					
					case CLIENT:{
						
						try {
						
							clientCircuit = new IntegratedCircuit();
							clientCircuit.buildCircuit(configurationType);
						
						} catch (InterruptedException | ExecutionException e) {
							clientCircuit.setCircuitStatus(CircuitStatus.UNAVAILABLE);
							e.printStackTrace();
							throw new ConfigurationException(
									ERR_CONFIG,
									MSG_CONFIG, 
									"Node instance creation interuppted.");							
						}	
						break;
					}case SERVER:{
						
						try {
							
							serverCircuit = new IntegratedCircuit();
							serverCircuit.buildCircuit(configurationType);
						} catch (InterruptedException | ExecutionException e) {
							serverCircuit.setCircuitStatus(CircuitStatus.UNAVAILABLE);
							e.printStackTrace();
							throw new ConfigurationException(
									ERR_CONFIG,
									MSG_CONFIG, 
									"Node instance creation interuppted.");
						}	
						break;
					}default:{
						break;
					}				
				}				
			}
			
		
	}
	
	@Override
	public EndPointStatus addServiceEndpoint(ConfigurationType configurationType, ServiceEndPoint serviceEndPoint) 
			throws ConfigurationException {
		
		EndPointStatus status = EndPointStatus.UNREACHABLE;
		
		switch(configurationType) {
		
			case CLIENT:{
				status = clientCircuit.addServiceEndpoint(serviceEndPoint);
				break;
			}case SERVER:{	
				status = serverCircuit.addServiceEndpoint(serviceEndPoint);
				break;
			}default:{
				break;
			}	
		}
		
		return status;
	}
	
	@Override
	public EndPointStatus removeServiceEndpoint(ConfigurationType configurationType, ServiceEndPoint serviceEndPoint) 
			throws ConfigurationException {
		
		EndPointStatus status = EndPointStatus.UNREACHABLE;
		
		switch(configurationType) {
		
			case CLIENT:{
				status = clientCircuit.removeServiceEndpoint(serviceEndPoint);
				break;
			}case SERVER:{	
				status = serverCircuit.removeServiceEndpoint(serviceEndPoint);
				break;
			}default:{
				break;
			}	
		}
		
		return status;
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
		
		DependencyStatus status = DependencyStatus.UNSATISFIED;
		
		return status;
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
	public void addServiceListener(EventPublisher engineListener) {
		
	}

	@Override
	public ServiceStatus startup(Optional<Properties> configProperties) {
		
		try {
			service.execute(this);
			buildCircuit();
			serviceStatus = ServiceStatus.STARTED;
		} catch (ConfigurationException e) {
			e.printStackTrace();
			serviceStatus = ServiceStatus.ERROR;
		}
		return serviceStatus;
	}

	@Override
	public ServiceStatus shutdown(Optional<Properties> configProperties) {
		
		clientCircuit.shutdownCircuit();
		serverCircuit.shutdownCircuit();
		service.shutdown();
		serviceStatus = ServiceStatus.STOPPED;
		return serviceStatus;
	}

}
