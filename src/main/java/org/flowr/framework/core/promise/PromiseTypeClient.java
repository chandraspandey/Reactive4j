package org.flowr.framework.core.promise;

import java.util.Properties;

import org.flowr.framework.core.config.ClientConfig;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.service.Service.ServiceStatus;
import org.flowr.framework.core.subject.DeferableRequest;


/**
 * @author Chandra Pandey
 *
 */

/**
 * Defines Async Client capabilities that can create DeferableRequest & 
 * generate DeferableResponse to claim a Promise. 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface PromiseTypeClient<REQUEST,RESPONSE> extends DeferableRequest<REQUEST>, Runnable{

	public enum ClientType{
		PERSISTENT,
		ADHOC
	}	
	
	public PromiseTypeServer<REQUEST, RESPONSE> buildPromiseTypeServer() throws ConfigurationException;
	
	public void configure(ClientConfig<REQUEST, RESPONSE> clientConfig) throws ConfigurationException;
	
	public PromiseRequest<REQUEST, RESPONSE>  buildPromiseRequest() throws ConfigurationException;
	
	public ClientIdentity buildClientIdentity() throws ConfigurationException;
	
	public ServiceStatus startupAdapter(Properties properties) throws ConfigurationException;
	
	public ServiceStatus shutdownAdapter(Properties properties) throws ConfigurationException;

}
