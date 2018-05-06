package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.promise.deferred.DefferedPromise;
import org.flowr.framework.core.service.ServiceFrameworkComponent;

/**
 * Defines Promise behavior for processor functionality.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface DefferedPromiseService<REQUEST,RESPONSE> extends ServiceFrameworkComponent{
	
	public DefferedPromise<REQUEST,RESPONSE> getPromise();
	
	public static DefferedPromiseService<?,?> getInstance() {
		
		return DefaultPromiseService.getInstance();
	}
	
	public class DefaultPromiseService<REQUEST,RESPONSE>{
		
		private static DefferedPromiseService<?,?> promiseService = null;
		
		@SuppressWarnings("rawtypes")
		public static DefferedPromiseService<?,?> getInstance() {
			
			if(promiseService == null) {
				promiseService = new DefferedPromiseServiceImpl();
			}
			
			return promiseService;
		}
		
	}
}
