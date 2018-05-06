package org.flowr.framework.core.service.extension;

import java.util.Properties;

import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.process.management.ManagedProcessHandler;
import org.flowr.framework.core.promise.scheduled.ScheduledPromise;
import org.flowr.framework.core.promise.scheduled.ScheduledPromiseHandler;
import org.flowr.framework.core.service.ServiceFramework;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ScheduledPromiseServiceImpl<REQUEST,RESPONSE> implements ScheduledPromiseService<REQUEST,RESPONSE>{

	private ServiceUnit serviceUnit 						= ServiceUnit.SINGELTON;
	private String dependencyName							= ScheduledPromise.class.getSimpleName();
	private DependencyType dependencyType 					= DependencyType.MANDATORY;
	private String serviceName								= FrameworkConstants.FRAMEWORK_SERVICE_PROMISE_SCHEDULED;
	private ServiceType serviceType							= ServiceType.PROMISE_SCHEDULED;
	@SuppressWarnings("unused")
	private ServiceFramework<REQUEST,RESPONSE> serviceFramework	= null;
	private ManagedProcessHandler managedProcessHandler 	= ManagedService.getDefaultProcessHandler();
	private ScheduledPromiseHandler<REQUEST,RESPONSE> scheduledPromiseHandler 	
															= new ScheduledPromiseHandler<REQUEST,RESPONSE>();

	
	@SuppressWarnings("unchecked")
	@Override
	public void setServiceFramework(ServiceFramework<?,?> serviceFramework) {
		
		this.serviceFramework = (ServiceFramework<REQUEST, RESPONSE>) serviceFramework;
		
		serviceFramework.getEventService().registerEventPipeline(
				FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_SCHEDULED,
				PipelineType.TRANSFER, 
				PipelineFunctionType.PIPELINE_PROMISE_SCHEDULED_EVENT,
				scheduledPromiseHandler);
		
		serviceFramework.getEventService().registerEventPipeline(
				FrameworkConstants.FRAMEWORK_PIPELINE_MANAGEMENT,
				PipelineType.TRANSFER, 
				PipelineFunctionType.PIPELINE_MANAGEMENT_EVENT,
				managedProcessHandler);
		
		scheduledPromiseHandler.associateProcessHandler(managedProcessHandler);
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
		return null;
	}

	@Override
	public ServiceStatus shutdown(Properties configProperties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScheduledPromise<REQUEST, RESPONSE> getPromise() {
		return this.scheduledPromiseHandler;
	}

}
