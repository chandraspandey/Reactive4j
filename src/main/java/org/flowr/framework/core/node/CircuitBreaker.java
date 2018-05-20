package org.flowr.framework.core.node;

import org.flowr.framework.api.Node;
import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.service.extension.NotificationService.NotificationServiceStatus;

/**
 * Preemts the existence of fault state on a defined health analogy & breaks
 * the circuit to prevent bigger outage.
 * @author "Chandra Pandey"
 *
 */
public interface CircuitBreaker {

	/**
	 * Recieves Trigger as input & Health as last known parameters to perform
	 * last evaluation for operation suspension
	 * @param health
	 * @param trigger
	 */
	public <HEALTH,TRIGGER> void suspendOperationOn(EndPointStatus health,NotificationServiceStatus trigger);
	
	/**
	 * Recieves Trigger as input & Health as last known parameters to perform
	 * last evaluation for operation resumption
	 * @param health
	 * @param trigger
	 */
	public <HEALTH,TRIGGER> void resumeOperationOn(EndPointStatus health,NotificationServiceStatus trigger);
	

	/**
	 * Builds integrated circuit for redundant HA deployment
	 * @return
	 */
	public Circuit buildCircuit(Node node,ConfigurationType configurationType) throws ConfigurationException;
	

}
