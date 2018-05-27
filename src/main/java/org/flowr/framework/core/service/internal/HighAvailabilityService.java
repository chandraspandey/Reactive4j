package org.flowr.framework.core.service.internal;

import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.Circuit;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.service.ServiceEndPoint;
import org.flowr.framework.core.service.ServiceFrameworkComponent;

/**
 * Defines High availability service for client & server end points.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface HighAvailabilityService extends ServiceFrameworkComponent{

	public EndPointStatus addServiceEndpoint(ConfigurationType configurationType, ServiceEndPoint serviceEndPoint) 
			throws ConfigurationException;
		
	public EndPointStatus removeServiceEndpoint(ConfigurationType configurationType, ServiceEndPoint serviceEndPoint)
			throws ConfigurationException;	
	
	public void buildCircuit() throws ConfigurationException; 
	
	public Circuit getCircuit(ConfigurationType configurationType);
	
	public static HighAvailabilityService getInstance() {
		
		return DefaultHighAvailabilityService.getInstance();
	}
	
	public class DefaultHighAvailabilityService{
		
		private static HighAvailabilityService highAvailabilityService = null;
		
		public static HighAvailabilityService getInstance() {
			
			if(highAvailabilityService == null) {
				highAvailabilityService = new HighAvailabilityServiceImpl();
			}
			
			return highAvailabilityService;
		}
		
	}
}
