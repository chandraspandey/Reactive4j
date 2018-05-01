package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.service.ServiceFrameworkComponent;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface StreamPromiseService<REQUEST,RESPONSE> extends ServiceFrameworkComponent{

	public static StreamPromiseService<?,?> getInstance() {
		
		return DefaultStreamPromiseService.getInstance();
	}
	
	public class DefaultStreamPromiseService<REQUEST,RESPONSE>{
		
		private static StreamPromiseService<?,?> streamPromiseService = null;
		
		@SuppressWarnings("rawtypes")
		public static StreamPromiseService<?,?> getInstance() {
			
			if(streamPromiseService == null) {
				streamPromiseService = new StreamPromiseServiceImpl();
			}
			
			return streamPromiseService;
		}
		
	}
}
