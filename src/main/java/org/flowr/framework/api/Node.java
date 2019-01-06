package org.flowr.framework.api;

import org.flowr.framework.core.config.Configuration;
import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.node.io.network.ServiceMesh;
import org.flowr.framework.core.process.management.NodeProcessHandler;
import org.flowr.framework.core.service.ServiceEndPoint;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface Node extends ServiceMesh{ 

	public EndPointStatus addServiceEndpoint(ConfigurationType configurationType, ServiceEndPoint serviceEndPoint) 
			throws ConfigurationException;
		
	public EndPointStatus removeServiceEndpoint(ConfigurationType configurationType, ServiceEndPoint serviceEndPoint)
			throws ConfigurationException;
	
	public NodeProcessHandler getNodeProcessHandler();
	
	/**
	 * Loads configuration of QueueProvider
	 * @throws ConfigurationException 
	 */
	public Configuration loadQueueProviderConfig() throws ConfigurationException;
}
