package org.flowr.framework.core.service;

import java.util.Properties;

import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.service.Service.ServiceStatus;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ServiceLifecycle {

	/**
	 * Provides listeners of the service to act according to information 
	 * provided by EngineTask
	 * @param engineTask
	
	public void publishServiceChange(NotificationTask notificationTask); 
	 */
	
	public void addServiceListener(EventPublisher serviceListener);
	
	/**
	 * Provides hookup services for start up
	 * @param configProperties : Optional properties that can be configured for granular startup of services
	 * @return
	 */
	public ServiceStatus startup(Properties configProperties);

	/**
	 * Provides hookup services for shut down
	 * @param configProperties : Optional properties that can be configured for granular shutdown of services
	 * @return
	 */
	public ServiceStatus shutdown(Properties configProperties);
}
