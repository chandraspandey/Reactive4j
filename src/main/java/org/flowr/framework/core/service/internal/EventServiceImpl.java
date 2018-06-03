package org.flowr.framework.core.service.internal;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.event.pipeline.EventBus;
import org.flowr.framework.core.event.pipeline.EventPipeline;
import org.flowr.framework.core.event.pipeline.EventPipelineBus;
import org.flowr.framework.core.event.pipeline.EventPipelineBusExecutor;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;
import org.flowr.framework.core.exception.ClientException;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.service.ServiceFramework;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class EventServiceImpl implements EventService{

	private ServiceUnit serviceUnit 					= ServiceUnit.SINGELTON;
	private static EventBus eventBus					= new EventPipelineBus();
	private String serviceName							= FrameworkConstants.FRAMEWORK_SERVICE_EVENT;
	private ServiceStatus serviceStatus					= ServiceStatus.UNUSED;
	private ServiceType serviceType						= ServiceType.EVENT;
	private EventPipelineBusExecutor eventBusExecutor 	= null;
	private ServiceFramework<?,?> serviceFramework		= null;
	private ExecutorService service 					= Executors.newSingleThreadExecutor();
	
	public EventRegistrationStatus registerEventPipeline(String pipelineName,PipelineType pipelineType, PipelineFunctionType 
			pipelineFunctionType,EventPublisher eventPublisher) {		
		
		EventRegistrationStatus status = EventRegistrationStatus.UNREGISTERED;
	
		if(!eventPublisher.isSubscribed()) {
		
			EventPipeline processSubscriber  =  eventBus.lookup(pipelineName,pipelineType,pipelineFunctionType);
			
			if(processSubscriber != null) {
				eventPublisher.subscribe(processSubscriber);
				status = EventRegistrationStatus.REGISTERED;
			}
		}else {
			status = EventRegistrationStatus.REGISTERED;
		}
		
		return status;
	}
	
	public void process() throws ClientException {
		
		this.serviceFramework.getNotificationService().notify(eventBusExecutor.process());
	}
	
	public EventServiceImpl() {
		
		registerEventPipeline(
				FrameworkConstants.FRAMEWORK_PIPELINE_MANAGEMENT,
				PipelineType.TRANSFER, 
				PipelineFunctionType.PIPELINE_MANAGEMENT_EVENT,
				ManagedService.getDefaultProcessHandler()
		);
		
		
		Arrays.asList(PipelineFunctionType.values()).forEach(
				
				(e) -> {
					switch(e) {
						
						case PIPELINE_MANAGEMENT_EVENT:{
							EventPipeline processSubscriber  =  new EventPipeline();
							processSubscriber.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_MANAGEMENT);
							processSubscriber.setPipelineType(PipelineType.TRANSFER);
							processSubscriber.setPipelineFunctionType(PipelineFunctionType.PIPELINE_MANAGEMENT_EVENT);
							processSubscriber.setEventType(EventType.SERVER);
							eventBus.addEventPipeline(processSubscriber);
							break;
						}case PIPELINE_PROMISE_EVENT:{
							EventPipeline processSubscriber  =  new EventPipeline();
							processSubscriber.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE);
							processSubscriber.setPipelineType(PipelineType.TRANSFER);
							processSubscriber.setPipelineFunctionType(PipelineFunctionType.PIPELINE_PROMISE_EVENT);	
							processSubscriber.setEventType(EventType.CLIENT);
							eventBus.addEventPipeline(processSubscriber);
							break;
						}case PIPELINE_PROMISE_DEFFERED_EVENT:{
							EventPipeline processSubscriber  =  new EventPipeline();
							processSubscriber.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_DEFFERED);
							processSubscriber.setPipelineType(PipelineType.TRANSFER);
							processSubscriber.setPipelineFunctionType(PipelineFunctionType.PIPELINE_PROMISE_DEFFERED_EVENT);	
							processSubscriber.setEventType(EventType.CLIENT);
							eventBus.addEventPipeline(processSubscriber);
							break;
						}case PIPELINE_PROMISE_PHASED_EVENT:{
							EventPipeline processSubscriber  =  new EventPipeline();
							processSubscriber.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_PHASED);
							processSubscriber.setPipelineType(PipelineType.TRANSFER);
							processSubscriber.setPipelineFunctionType(PipelineFunctionType.PIPELINE_PROMISE_PHASED_EVENT);
							processSubscriber.setEventType(EventType.CLIENT);
							eventBus.addEventPipeline(processSubscriber);
							break;
						}case PIPELINE_PROMISE_SCHEDULED_EVENT:{
							EventPipeline processSubscriber  =  new EventPipeline();
							processSubscriber.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_SCHEDULED);
							processSubscriber.setPipelineType(PipelineType.TRANSFER);
							processSubscriber.setPipelineFunctionType(PipelineFunctionType.PIPELINE_PROMISE_SCHEDULED_EVENT);		
							processSubscriber.setEventType(EventType.CLIENT);
							eventBus.addEventPipeline(processSubscriber);
							break;
						}case PIPELINE_PROMISE_STAGE_EVENT:{
							EventPipeline processSubscriber  =  new EventPipeline();
							processSubscriber.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_STAGE);
							processSubscriber.setPipelineType(PipelineType.TRANSFER);
							processSubscriber.setPipelineFunctionType(PipelineFunctionType.PIPELINE_PROMISE_STAGE_EVENT);	
							processSubscriber.setEventType(EventType.CLIENT);
							eventBus.addEventPipeline(processSubscriber);
							break;
						}case PIPELINE_PROMISE_STREAM_EVENT:{
							EventPipeline processSubscriber  =  new EventPipeline();
							processSubscriber.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_STREAM);
							processSubscriber.setPipelineType(PipelineType.TRANSFER);
							processSubscriber.setPipelineFunctionType(PipelineFunctionType.PIPELINE_PROMISE_STREAM_EVENT);
							processSubscriber.setEventType(EventType.CLIENT);
							eventBus.addEventPipeline(processSubscriber);
							break;
						}default:{
							break;
						}
						
					}
				}
		);
		
		eventBusExecutor = new EventPipelineBusExecutor(eventBus);
		
	}
	
	@Override
	public void run() {
		
		while(serviceStatus != ServiceStatus.STOPPED) {
			
			try {
				process();
				Thread.sleep(FrameworkConstants.FRAMEWORK_PIPELINE_NOTIFICATION_TIME_UNIT);
			} catch (ClientException | InterruptedException e) {
				e.printStackTrace();
			}
		}
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServiceStatus startup(Properties configProperties) {
		
		service.execute(this);
		serviceStatus = ServiceStatus.STARTED;
		
		return serviceStatus;
	}

	@Override
	public ServiceStatus shutdown(Properties configProperties) {
		
		service.shutdown();
		serviceStatus = ServiceStatus.STOPPED;
		return serviceStatus;
	}

	@Override
	public void setServiceFramework(ServiceFramework<?,?> serviceFramework) {
		this.serviceFramework = serviceFramework;
	}




}
