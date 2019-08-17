package org.flowr.framework.core.service.internal;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.flowr.framework.api.Node;
import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.event.pipeline.EventPipeline;
import org.flowr.framework.core.event.pipeline.ManagedEventPipelineBusExecutor;
import org.flowr.framework.core.exception.ClientException;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.process.management.ManagedProcessHandler;
import org.flowr.framework.core.process.management.NodeProcessHandler;
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

public class NodeServiceImpl implements NodeService,Dependency, DependencyLoop{

	private ServiceUnit serviceUnit 								= ServiceUnit.SINGELTON;
	private String dependencyName									= NodeService.class.getSimpleName();
	private DependencyType dependencyType 							= DependencyType.MANDATORY;
	private String serviceName										= FrameworkConstants.FRAMEWORK_SERVICE_NODE;
	private ServiceType serviceType									= ServiceType.NODE;
	private ServiceStatus serviceStatus								= ServiceStatus.UNUSED;
	private ManagedEventPipelineBusExecutor eventBusExecutor 		= ManagedService.getDefaultEventPipelineBusExecutor();
	private ServiceFramework<?,?> serviceFramework					= null;
	private NodeProcessHandler nodeProcessHandler 					= NodeService.getDefaultNodeProcessHandler();
	private ManagedProcessHandler managedProcessHandler 			= ManagedService.getDefaultProcessHandler();
	private Node node												= null;
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
	public void run() {
		
		while(serviceStatus != ServiceStatus.STOPPED) {
			
			try {
				process();
				Thread.sleep(FrameworkConstants.FRAMEWORK_PIPELINE_NOTIFICATION_TIME_UNIT);
				//System.out.println("NodeService : "+dependencyName);
			} catch (ClientException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void setServiceFramework(ServiceFramework<?,?> serviceFramework) {
		
		this.serviceFramework = serviceFramework;
		
		this.registerEventPipeline();	
	}
	
	public void process() throws ClientException {
		
		this.serviceFramework.getNotificationService().notify(eventBusExecutor.process());
	}
	
	@Override
	public void setNode(Node node) {		
		this.node = node;
	}
	
	@Override
	public Node getNode() {		
		return this.node;
	}
	
	@Override
	public void setNodeProcessHandler(NodeProcessHandler nodeProcessHandler) {
		
		this.nodeProcessHandler = nodeProcessHandler;
	}
	
	@Override
	public NodeProcessHandler getNodeProcessHandler() {
		return this.nodeProcessHandler;
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
		return DependencyStatus.SATISFIED;
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
		
		service.execute(this);
		serviceStatus = ServiceStatus.STARTED;
		
		return serviceStatus;
	}

	@Override
	public ServiceStatus shutdown(Optional<Properties> configProperties) {

		service.shutdown();
		serviceStatus = ServiceStatus.STOPPED;
		return serviceStatus;
	}
	
	@Override
	public Process lookupProcess(String executable) throws ConfigurationException {

		return nodeProcessHandler.lookupProcess(executable);
	}
	
	@Override
	public InputStream processIn(String executable) throws ConfigurationException {

		return nodeProcessHandler.processIn(executable);
	}
	
	@Override
	public OutputStream processOut(String executable) throws ConfigurationException {
		return nodeProcessHandler.processOut(executable);
	}

	@Override
	public InputStream processError(String executable) throws ConfigurationException {
		return nodeProcessHandler.processError(executable);
	}

}
