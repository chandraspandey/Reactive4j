package org.flowr.framework.core.service.extension;

import java.util.Optional;
import java.util.Properties;

import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.process.management.ManagedProcessHandler;
import org.flowr.framework.core.promise.deferred.DeferredPromiseHandler;
import org.flowr.framework.core.promise.deferred.DefferedPromise;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.internal.ManagedService;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class DefferedPromiseServiceImpl<REQUEST,RESPONSE> implements DefferedPromiseService<REQUEST,RESPONSE>{

	private ServiceUnit serviceUnit 								= ServiceUnit.SINGELTON;
	private String serviceName										= FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_DEFFERED;
	private ServiceType serviceType									= ServiceType.PROMISE;
	@SuppressWarnings("unused")
	private ServiceFramework<REQUEST,RESPONSE> serviceFramework		= null;
	private ManagedProcessHandler managedProcessHandler 			= ManagedService.getDefaultProcessHandler();
	private DeferredPromiseHandler<REQUEST,RESPONSE> promiseHandler = new DeferredPromiseHandler<REQUEST,RESPONSE>();
	
	@SuppressWarnings("unchecked")
	@Override
	public void setServiceFramework(ServiceFramework<?,?> serviceFramework) {
		
		this.serviceFramework = (ServiceFramework<REQUEST, RESPONSE>) serviceFramework;
		
		serviceFramework.getEventService().registerEventPipeline(
				FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_DEFFERED,
				PipelineType.TRANSFER, 
				PipelineFunctionType.PIPELINE_PROMISE_DEFFERED_EVENT 
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
	public void addServiceListener(EventPublisher engineListener) {
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
	public DefferedPromise<REQUEST, RESPONSE> getPromise() {
		return this.promiseHandler;
	}
}
