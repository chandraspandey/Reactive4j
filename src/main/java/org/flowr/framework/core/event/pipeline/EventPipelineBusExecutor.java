package org.flowr.framework.core.event.pipeline;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.model.MetaData;
import org.flowr.framework.core.notification.NotificationBufferQueue;
import org.flowr.framework.core.service.Service.ServiceStatus;
import org.flowr.framework.core.service.ServiceLifecycle;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class EventPipelineBusExecutor implements EventBusExecutor,ServiceLifecycle {

	private ExecutorService service 	= Executors.newCachedThreadPool();
	private EventBus eventBus			= null;
	private EventPipelineTask task		= null;
	private ServiceStatus serviceStatus = ServiceStatus.UNUSED;
	
	public EventPipelineBusExecutor(EventBus bus) {
		eventBus = bus; 
	}

	@Override
	public void addServiceListener(EventPublisher<MetaData> serviceListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServiceStatus startup(Properties configProperties) {
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
	public NotificationBufferQueue process() {
				
		NotificationBufferQueue notificationBufferQueue = null;
		
		task	= new EventPipelineTask(eventBus);
		
		System.out.println("EventPipelineBusExecutor : eventBus : "+eventBus);
		
		Future<NotificationBufferQueue> future = service.submit(task);
		
		while(!future.isDone()) {
			
			try {
				
				Thread.sleep(100);
				
				if(future.isDone()) {
					
					notificationBufferQueue = future.get();
				}
			
			} catch (ExecutionException | InterruptedException e) {

				e.printStackTrace();
			}
		}
		
		return notificationBufferQueue;
	}
	
}
