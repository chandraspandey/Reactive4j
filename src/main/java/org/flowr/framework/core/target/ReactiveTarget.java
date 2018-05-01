package org.flowr.framework.core.target;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.node.Autonomic;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.service.ServiceResponse;

/**
 * Defines Async server capabilities that can consume DeferableRequest & 
 * generate DeferableResponse to honor a Promise.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ReactiveTarget<REQUEST,RESPONSE> extends ServiceResponse,Autonomic<RequestScale,Scale>{
		
	/**
	 * First invocation for remote target which takes Request of Type REQUEST.
	 * If input do not meet the pre conditions of execution throws a 
	 * PromiseException, else returns ProgressScale with attribution as 
	 * PromiseStatus as INITIATED and PromiseState as ACKNOWLEDGED 
	 * @param request
	 * @param requestScale
	 * @return
	 * @throws PromiseException 
	 * @throws ConfigurationException 
	 */
	public Scale invokeAndReturn(REQUEST request, 
		RequestScale requestScale) throws PromiseException,ConfigurationException;
	
	/**
	 * If invokeAndReturn is succesfull and ProgressScale has updated attribution
	 * with PromiseStatus as PROCESSING and PromiseState as ASSURED. Async query
	 * can be performed for progress to be returned of type ProgressScale
	 * @param acknowledgmentIdentifier	 
	 * @return 
	 * @throws PromiseException 
	 * @throws ConfigurationException 
	 */
	public Scale invokeForProgress(String acknowledgmentIdentifier) throws PromiseException, ConfigurationException;
		
	/**
	 * Invocable when the ProgressScale has updated attribution
	 * of PromiseStatus as COMPLETED & PromiseSate as FULFILLED or TIMEOUT or 
	 * ERROR
	 * @param acknowledgmentIdentifier
	 * @return
	 * @throws PromiseException 
	 * @throws ConfigurationException 
	 */
	public RESPONSE invokeWhenComplete(String acknowledgmentIdentifier) throws PromiseException,ConfigurationException;
	
	public String getServerIdentifier();
}
