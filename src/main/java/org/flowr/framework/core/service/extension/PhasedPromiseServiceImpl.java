package org.flowr.framework.core.service.extension;

import java.util.Properties;

import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.process.management.ManagedProcessHandler;
import org.flowr.framework.core.promise.phase.PhasedPromise;
import org.flowr.framework.core.promise.phase.PhasedPromiseHandler;
import org.flowr.framework.core.service.ServiceFramework;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class PhasedPromiseServiceImpl<REQUEST,RESPONSE> implements PhasedPromiseService<REQUEST,RESPONSE>{

	private ServiceUnit serviceUnit 						= ServiceUnit.SINGELTON;
	private String dependencyName							= PhasedPromiseService.class.getSimpleName();
	private DependencyType dependencyType 					= DependencyType.MANDATORY;
	private String serviceName								= FrameworkConstants.FRAMEWORK_SERVICE_PROMISE_PHASED;
	private ServiceType serviceType							= ServiceType.PROMISE_PHASED;
	@SuppressWarnings("unused")
	private ServiceFramework<REQUEST,RESPONSE> serviceFramework	= null;
	private ManagedProcessHandler managedProcessHandler 	= ManagedService.getDefaultProcessHandler();
	private PhasedPromiseHandler<REQUEST,RESPONSE> phasedPromiseHandler 		
															= new PhasedPromiseHandler<REQUEST,RESPONSE>();

	
	@SuppressWarnings("unchecked")
	@Override
	public void setServiceFramework(ServiceFramework<?,?> serviceFramework) {
		
		this.serviceFramework = (ServiceFramework<REQUEST, RESPONSE>) serviceFramework;
		
		serviceFramework.getEventService().registerEventPipeline(
				FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_PHASED,
				PipelineType.TRANSFER, 
				PipelineFunctionType.PIPELINE_PROMISE_PHASED_EVENT
				,phasedPromiseHandler);
		
		serviceFramework.getEventService().registerEventPipeline(
				FrameworkConstants.FRAMEWORK_PIPELINE_MANAGEMENT,
				PipelineType.TRANSFER, 
				PipelineFunctionType.PIPELINE_MANAGEMENT_EVENT
				,managedProcessHandler);
		
		phasedPromiseHandler.associateProcessHandler(managedProcessHandler);	
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
	public PhasedPromise<REQUEST, RESPONSE> getPromise() {
		return this.phasedPromiseHandler;
	}

}
