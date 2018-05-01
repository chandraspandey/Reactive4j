package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.service.ServiceFrameworkComponent;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface StagedPromiseService<REQUEST,RESPONSE> extends ServiceFrameworkComponent{

	public static StagedPromiseService<?,?> getInstance() {
		
		return DefaultStagedPromiseService.getInstance();
	}
	
	public class DefaultStagedPromiseService<REQUEST,RESPONSE>{
		
		private static StagedPromiseService<?,?> stagedPromiseService = null;
		
		@SuppressWarnings("rawtypes")
		public static StagedPromiseService<?,?> getInstance() {
			
			if(stagedPromiseService == null) {
				stagedPromiseService = new StagedPromiseServiceImpl();
			}
			
			return stagedPromiseService;
		}
		
	}
}
