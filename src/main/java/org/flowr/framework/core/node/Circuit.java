package org.flowr.framework.core.node;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.service.ServiceEndPoint;

/**
 * Defines HA as deployment of an integrated Circuit for facilitating automatic fallback & fallforward features
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */


public interface Circuit {

	public enum CircuitStatus{
		AVAILABLE,
		UNAVAILABLE
	}
	
	public void buildCircuit(String configName, String filePath) throws ConfigurationException, 
		InterruptedException, ExecutionException;
	
	public EndPointStatus handleEndPoint(ServiceEndPoint serviceEndPoint) throws InterruptedException, 
		ExecutionException;
	
	public void heartbeat() throws InterruptedException, ExecutionException;
	
	public void shutdownCircuit();

	public Collection<ServiceEndPoint> getAvailableServiceEndPoints(String endPointType);
	
	public CircuitStatus getCircuitStatus();
	
	public void setCircuitStatus(CircuitStatus circuitStatus);
}
