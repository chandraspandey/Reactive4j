package org.flowr.framework.core.node.io.pipeline;

import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.service.Service.ServiceStatus;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class NetworkPipelineBusExecutor implements NetworkBusExecutor {

	private ExecutorService service 	= Executors.newCachedThreadPool();
	private NetworkBus	networkBus		= null;
	private ServiceStatus serviceStatus = ServiceStatus.UNUSED;
	
	public NetworkPipelineBusExecutor(NetworkBus networkBus) {
		this.networkBus = networkBus; 
		System.out.println("networkBus : eventBus : "+networkBus);		
	}

	@Override
	public void addServiceListener(EventPublisher serviceListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServiceStatus startup(Optional<Properties> configProperties) {
		
		service.execute(this);
		this.serviceStatus = ServiceStatus.STARTED;
		return serviceStatus;
	}

	@Override
	public ServiceStatus shutdown(Optional<Properties> configProperties) {
		service.shutdown();
		this.serviceStatus = ServiceStatus.STOPPED;
		return serviceStatus;	
	}

	@Override
	public void process() {
				
		networkBus.execute();
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
