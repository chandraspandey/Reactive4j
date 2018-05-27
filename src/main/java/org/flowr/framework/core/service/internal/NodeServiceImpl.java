package org.flowr.framework.core.service.internal;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.flowr.framework.api.Node;
import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.process.management.ManagedProcessHandler;
import org.flowr.framework.core.process.management.NodeProcessHandler;
import org.flowr.framework.core.service.ServiceFramework;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class NodeServiceImpl implements NodeService{

	private ServiceUnit serviceUnit 								= ServiceUnit.SINGELTON;
	private String dependencyName									= HighAvailabilityService.class.getSimpleName();
	private DependencyType dependencyType 							= DependencyType.MANDATORY;
	private String serviceName										= FrameworkConstants.FRAMEWORK_SERVICE_NODE;
	private ServiceType serviceType									= ServiceType.NODE;
	private ServiceFramework<?,?> serviceFramework					= null;
	private NodeProcessHandler nodeProcessHandler 					= NodeService.getDefaultNodeProcessHandler();
	private ManagedProcessHandler managedProcessHandler 			= ManagedService.getDefaultProcessHandler();
	private Node node												= null;
	
	@Override
	public void setServiceFramework(ServiceFramework<?,?> serviceFramework) {
		
		this.serviceFramework = serviceFramework;
		
		this.serviceFramework.getEventService().registerEventPipeline(
				FrameworkConstants.FRAMEWORK_PIPELINE_HEALTH,
				PipelineType.TRANSFER, 
				PipelineFunctionType.HEALTH 
				,managedProcessHandler);	
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
	public void addServiceListener(EventPublisher engineListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServiceStatus startup(Properties configProperties) {
		return ServiceStatus.STARTED;
	}

	@Override
	public ServiceStatus shutdown(Properties configProperties) {

		return ServiceStatus.STOPPED;
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
