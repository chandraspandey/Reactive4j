package org.flowr.framework.core.service;

import java.util.Properties;

import org.flowr.framework.api.Provider;
import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.promise.PromiseRequest;
import org.flowr.framework.core.promise.PromiseResponse;


/**
 * Defines high level behavior for Service Provider
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ServiceProvider<REQUEST,RESPONSE> extends Service, ServiceLifecycle,Provider{

		
	public void setServerSubscriptionIdentifier(String subscriptionIdentifier);

	public  PromiseResponse<RESPONSE> service(PromiseRequest<REQUEST,RESPONSE> promiseRequest) throws PromiseException,
		ConfigurationException;

	public ServiceConfiguration loadConfiguration(Properties configProperties) throws ConfigurationException;
	

}
