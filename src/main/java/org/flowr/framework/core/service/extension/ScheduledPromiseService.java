package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.promise.scheduled.ScheduledPromise;
import org.flowr.framework.core.service.ServiceFrameworkComponent;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ScheduledPromiseService<REQUEST,RESPONSE> extends ServiceFrameworkComponent{

	public ScheduledPromise<REQUEST,RESPONSE> getPromise();
	
	public static ScheduledPromiseService<?,?> getInstance() {
		
		return DefaultPromiseService.getInstance();
	}
	
	public class DefaultPromiseService<REQUEST,RESPONSE>{
		
		private static ScheduledPromiseService<?,?> scheduledPromiseService = null;
		
		@SuppressWarnings("rawtypes")
		public static ScheduledPromiseService<?,?> getInstance() {
			
			if(scheduledPromiseService == null) {
				scheduledPromiseService = new ScheduledPromiseServiceImpl();
			}
			
			return scheduledPromiseService;
		}
		
	}
}
