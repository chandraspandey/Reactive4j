package org.flowr.framework.core.service.internal;

import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.event.pipeline.EventPipeline;
import org.flowr.framework.core.event.pipeline.ManagedEventPipelineBus;
import org.flowr.framework.core.event.pipeline.ManagedEventPipelineBusExecutor;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;
import org.flowr.framework.core.process.management.ManagedProcessHandler;
import org.flowr.framework.core.service.ServiceFrameworkComponent;

public interface ManagedService extends ServiceFrameworkComponent{

	public static ManagedProcessHandler getDefaultProcessHandler() {
		
		return DefaultManagedService.getDefaultProcessHandler();
	}
	
	public static ManagedService getInstance() {
		
		return DefaultManagedService.getInstance();
	}
	
	public static EventPipeline getDefaultEventPipeline() {
		
		return DefaultManagedService.getDefaultEventPipeline();
	}
	
	public static ManagedEventPipelineBus getDefaultEventPipelineBus() {
		
		return DefaultManagedService.getDefaultManagedEventPipelineBus();
	}
	
	public static ManagedEventPipelineBusExecutor getDefaultEventPipelineBusExecutor() {
		
		return DefaultManagedService.getDefaultManagedEventPipelineBusExecutor();
	}
	
	public class DefaultManagedService{
		
		private static ManagedService managedService 				= null;
		private static ManagedProcessHandler defaultProcessHandler 	= null;
		private static ManagedEventPipelineBus eventBus				= null;
		private static ManagedEventPipelineBusExecutor executor 	= null;
		private static EventPipeline processSubscriber  			= null;

		public static ManagedService getInstance() {
			
			if(managedService == null) {
				managedService = new ManagedServiceImpl();
			}
			
			return managedService;
		}
		
		public static ManagedProcessHandler getDefaultProcessHandler() {
			
			if(defaultProcessHandler == null) {
				defaultProcessHandler 	= new ManagedProcessHandler();
			}
			
			return defaultProcessHandler;
		}
		
		public static EventPipeline getDefaultEventPipeline() {
			
			if(processSubscriber == null) {
				processSubscriber 	= new EventPipeline();
			}
			
			return processSubscriber;
		}
		
		public static ManagedEventPipelineBus getDefaultManagedEventPipelineBus() {
			
			if(eventBus == null) {
				eventBus 	= new ManagedEventPipelineBus();
			}
			
			return eventBus;
		}
		
		public static ManagedEventPipelineBusExecutor getDefaultManagedEventPipelineBusExecutor() {
			
			if(executor == null) {
				
				getDefaultEventPipeline();
				getDefaultManagedEventPipelineBus();				
				processSubscriber.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_MANAGEMENT);
				processSubscriber.setPipelineType(PipelineType.TRANSFER);
				processSubscriber.setPipelineFunctionType(PipelineFunctionType.PIPELINE_MANAGEMENT_EVENT);
				processSubscriber.setEventType(EventType.SERVER);
				eventBus.addEventPipeline(processSubscriber);
				
				executor 	= new ManagedEventPipelineBusExecutor(eventBus);
			}
			
			return executor;
		}
		
	}
}
