package org.flowr.framework.core.node.ha;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.EndPoint;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.service.ServiceEndPoint;
import org.flowr.framework.core.service.internal.NotificationService.NotificationServiceStatus;

/**
 * 
 * Preempts the existence of fault state on a defined health analogy & breaks the circuit to prevent bigger outage.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface CircuitBreaker {

	/**
	 * Recieves Trigger as input & Health as last known parameters to perform
	 * last evaluation for operation suspension
	 * @param health
	 * @param trigger
	 */
	public void suspendOperationOn(ServiceEndPoint serviceEndPoint, EndPointStatus health,NotificationServiceStatus trigger)
			throws ConfigurationException;
	
	/**
	 * Recieves Trigger as input & Health as last known parameters to perform
	 * last evaluation for operation resumption
	 * @param health
	 * @param trigger
	 */
	public void resumeOperationOn(ServiceEndPoint serviceEndPoint, EndPointStatus health,NotificationServiceStatus trigger)
			throws ConfigurationException;
	

}
