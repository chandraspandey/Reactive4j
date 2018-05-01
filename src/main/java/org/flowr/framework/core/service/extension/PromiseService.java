package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.promise.Promise;
import org.flowr.framework.core.service.ServiceFrameworkComponent;

/**
 * Defines Promise behavior for processor functionality.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface PromiseService<REQUEST,RESPONSE> extends ServiceFrameworkComponent{

	/* Marks the states that a promise can hold as a state change machine. 
	 * REGISTERED 	: Registration for Future occurrence is acknowledged.
	 * PROCESSING 	: The client can make an assumption that promise would held 
	 * good & the state as CALLBACK should happen in normal course.
	 * CALLBACK : The data processing has completed successfully & callback is
	 * in progress to the registered client
	 * TIMEOUT : Marks a state where the promise can not be honored as the
	 * response generation has timed out. Relevant for network failure
	 * for 1:N relationship end point listener processing on server side 
	 * ERROR : Marks a state where the promise can not be honored. 
	 * COMPLETE : Marks the final state 
	 */
	public enum PromiseServerState{
		REGISTERED,
		PROCESSING,
		CALLBACK,
		TIMEOUT,
		ERROR,
		COMPLETE,
		UNREGISTERED
	}
	
	/*
	 * STARTED : When the processing has been started successfully.	  
	 * SHUTDOWN : When the processing has been shut down successfully.	.
	 */
	public enum PromiseServerStatus{
		STARTED,
		RUNNING,
		SHUTDOWN,
		HEALTHCHECK
	}
	
	
	public Promise<REQUEST,RESPONSE> getPromise();
	
	public static PromiseService<?,?> getInstance() {
		
		return DefaultPromiseService.getInstance();
	}
	
	public class DefaultPromiseService<REQUEST,RESPONSE>{
		
		private static PromiseService<?,?> promiseService = null;
		
		@SuppressWarnings("rawtypes")
		public static PromiseService<?,?> getInstance() {
			
			if(promiseService == null) {
				promiseService = new PromiseServiceImpl();
			}
			
			return promiseService;
		}
		
	}
}
