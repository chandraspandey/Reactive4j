package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.service.ServiceFrameworkComponent;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface MapPromiseService<REQUEST,RESPONSE> extends ServiceFrameworkComponent{

	public static MapPromiseService<?,?> getInstance() {
		
		return DefaultMapPromiseService.getInstance();
	}
	
	public class DefaultMapPromiseService<REQUEST,RESPONSE>{
		
		private static MapPromiseService<?,?> mapPromiseService = null;
		
		@SuppressWarnings("rawtypes")
		public static MapPromiseService<?,?> getInstance() {
			
			if(mapPromiseService == null) {
				mapPromiseService = new MapPromiseServiceImpl();
			}
			
			return mapPromiseService;
		}
		
	}
}
