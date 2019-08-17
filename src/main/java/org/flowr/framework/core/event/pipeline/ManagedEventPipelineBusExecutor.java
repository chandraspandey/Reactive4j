package org.flowr.framework.core.event.pipeline;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.notification.NotificationBufferQueue;
import org.flowr.framework.core.service.Service.ServiceStatus;
import org.flowr.framework.core.service.ServiceLifecycle;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ManagedEventPipelineBusExecutor implements EventBusExecutor,ServiceLifecycle,Runnable {

	private ExecutorService service 	= Executors.newCachedThreadPool();
	private EventBus eventBus			= null;
	private EventPipelineTask task		= null;
	private ServiceStatus serviceStatus = ServiceStatus.UNUSED;
	
	public ManagedEventPipelineBusExecutor(EventBus bus) {
		eventBus = bus; 
		//System.out.println("ManagedEventPipelineBusExecutor : eventBus : "+eventBus);
		service.execute(this);
	}

	@Override
	public void addServiceListener(EventPublisher serviceListener) {
		
	}

	@Override
	public ServiceStatus startup(Optional<Properties> configProperties) {
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
	public synchronized NotificationBufferQueue process() {
				
		NotificationBufferQueue notificationBufferQueue = new NotificationBufferQueue();
		
		List<EventPipeline> pipelineList = eventBus.getAllPipelines();
			
			pipelineList.forEach(
					
				(p) -> {
						
					if(!p.isEmpty()) {
						
						task	= new EventPipelineTask(p);
						
						Future<NotificationBufferQueue> future = service.submit(task);
						
						while(!future.isDone()) {
							
							try {
								
								Thread.sleep(100);
								
								if(future.isDone()) {
									notificationBufferQueue.setEventType(p.getEventType());
									notificationBufferQueue.addAll(future.get());
								}
							
							} catch (ExecutionException | InterruptedException e) {
				
								e.printStackTrace();
							}
						}
					}

				}
			);
		
		return notificationBufferQueue;
	}
	
	@Override
	public void run() {
		
		while(serviceStatus != ServiceStatus.STOPPED) {
			
			try {
				process();
				
				Thread.sleep(1000);
			} catch ( InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	
}
