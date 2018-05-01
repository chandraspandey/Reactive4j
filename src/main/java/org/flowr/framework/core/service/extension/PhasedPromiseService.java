package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.promise.phase.PhasedPromise;
import org.flowr.framework.core.service.ServiceFrameworkComponent;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface PhasedPromiseService<REQUEST,RESPONSE> extends ServiceFrameworkComponent{

	public PhasedPromise<REQUEST,RESPONSE> getPromise();
	
	public static PhasedPromiseService<?,?> getInstance() {
		
		return DefaultPhasedPromiseService.getInstance();
	}
	
	public class DefaultPhasedPromiseService<REQUEST,RESPONSE>{
		
		private static PhasedPromiseService<?,?> phasedPromiseService = null;
		
		@SuppressWarnings("rawtypes")
		public static PhasedPromiseService<?,?> getInstance() {
			
			if(phasedPromiseService == null) {
				phasedPromiseService = new PhasedPromiseServiceImpl();
			}
			
			return phasedPromiseService;
		}
		
	}
}
