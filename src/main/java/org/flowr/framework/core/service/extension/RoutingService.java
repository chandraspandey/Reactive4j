package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.promise.ClientIdentity;
import org.flowr.framework.core.promise.PromiseRequest;
import org.flowr.framework.core.service.ServiceFrameworkComponent;
import org.flowr.framework.core.service.ServiceResponse;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface RoutingService extends ServiceFrameworkComponent{

	public void bindServiceRoute(ClientIdentity clientIdentity,Class<? extends ServiceResponse>  responseClass) 
			throws ConfigurationException;		

	public Class<? extends ServiceResponse> getServiceRoute(PromiseRequest<?,?> promiseRequest);
	
	public static RoutingService getInstance() {
		
		return DefaultRegistryService.getInstance();
	}
	
	public class DefaultRegistryService{
		
		private static RoutingService routingService = null;
		
		public static RoutingService getInstance() {
			
			if(routingService == null) {
				routingService = new RoutingServiceImpl();
			}
			
			return routingService;
		}
		
	}
}
