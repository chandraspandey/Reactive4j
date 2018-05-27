package org.flowr.framework.core.service.extension;

import java.util.Properties;

import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.process.management.ManagedProcessHandler;
import org.flowr.framework.core.promise.Promise;
import org.flowr.framework.core.promise.PromiseHandler;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.internal.ManagedService;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class PromiseServiceImpl<REQUEST,RESPONSE> implements PromiseService<REQUEST,RESPONSE>{

	private ServiceUnit serviceUnit 						= ServiceUnit.SINGELTON;
	private String dependencyName							= PromiseService.class.getSimpleName();
	private DependencyType dependencyType 					= DependencyType.MANDATORY;
	private String serviceName								= FrameworkConstants.FRAMEWORK_SERVICE_PROMISE;
	private ServiceType serviceType							= ServiceType.PROMISE;
	@SuppressWarnings("unused")
	private ServiceFramework<REQUEST,RESPONSE> serviceFramework	= null;
	private ManagedProcessHandler managedProcessHandler 	= ManagedService.getDefaultProcessHandler();
	private PromiseHandler<REQUEST,RESPONSE> promiseHandler = new PromiseHandler<REQUEST,RESPONSE>();
	
	@SuppressWarnings("unchecked")
	@Override
	public void setServiceFramework(ServiceFramework<?,?> serviceFramework) {
		
		this.serviceFramework = (ServiceFramework<REQUEST, RESPONSE>) serviceFramework;

		serviceFramework.getEventService().registerEventPipeline(
				FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE,
				PipelineType.TRANSFER, 
				PipelineFunctionType.PIPELINE_PROMISE_EVENT 
				,promiseHandler);
		
		serviceFramework.getEventService().registerEventPipeline(
				FrameworkConstants.FRAMEWORK_PIPELINE_MANAGEMENT,
				PipelineType.TRANSFER, 
				PipelineFunctionType.PIPELINE_MANAGEMENT_EVENT
				,managedProcessHandler);
		
		promiseHandler.associateProcessHandler(managedProcessHandler);		

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
		// TODO Auto-generated method stub
		return ServiceStatus.STARTED;
	}

	@Override
	public ServiceStatus shutdown(Properties configProperties) {
		// TODO Auto-generated method stub
		return ServiceStatus.STOPPED;
	}

	@Override
	public Promise<REQUEST,RESPONSE> getPromise() {
		
		return this.promiseHandler;
	}
}
