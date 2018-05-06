package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.service.ServiceFrameworkComponent;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface StagePromiseService<REQUEST,RESPONSE> extends ServiceFrameworkComponent{

	public static StagePromiseService<?,?> getInstance() {
		
		return DefaultStagedPromiseService.getInstance();
	}
	
	public class DefaultStagedPromiseService<REQUEST,RESPONSE>{
		
		private static StagePromiseService<?,?> stagedPromiseService = null;
		
		@SuppressWarnings("rawtypes")
		public static StagePromiseService<?,?> getInstance() {
			
			if(stagedPromiseService == null) {
				stagedPromiseService = new StagePromiseServiceImpl();
			}
			
			return stagedPromiseService;
		}
		
	}
}
