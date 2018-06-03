package org.flowr.framework.core.process;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG;

import org.flowr.framework.core.context.PromiseContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.promise.PromiseRequest;
import org.flowr.framework.core.promise.PromiseTypeServer;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.service.ServiceFramework;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class ProcessRequest<REQ,RES>{
	
	private RequestScale requestScale = null;
	private PromiseContext<REQ> promiseContext;
	private PromiseRequest<REQ> promiseRequest;
	private PromiseTypeServer<REQ,RES> promiseServer;
	
	public ProcessRequest(ServiceFramework<REQ,RES> serviceFramework,PromiseRequest<REQ> promiseRequest, 
			PromiseTypeServer<REQ,RES> promiseServer,REQ request) throws ConfigurationException{
		
		this.promiseRequest = promiseRequest;
		this.promiseServer 	= promiseServer;
		
		requestScale = serviceFramework.getConfigurationService().getRequestScale(ProcessClient.clientSubscriptionId);
		
		//System.out.println("ProcessRequest : requestScale : "+requestScale);
		
		promiseContext = new PromiseContext<REQ>();
		promiseContext.setRequestScale(requestScale);
		promiseContext.setTimelineRequired(true);
		promiseContext.setDeferableRequest(request);	
		
		//System.out.println("ProcessRequest : promiseContext : "+promiseContext);
		
		promiseRequest.setPromiseContext(promiseContext);
		
		//System.out.println("ProcessRequest : "+promiseRequest.getClass().getName()+" | "+promiseServer.getClass().getName());
		//System.out.println("ProcessRequest : "+promiseRequest.getClientIdentity());
		serviceFramework.getRoutingService().bindServiceRoute(promiseRequest.getClientIdentity(),promiseServer.getClass());

	}
	
	public ProcessRequest<REQ,RES> build() throws ConfigurationException{
		
		if(promiseRequest.getClientIdentity() != null){
		
			if( promiseRequest.getRequestType() != null && promiseServer != null &&
					promiseRequest.getRequestType().name().equals(promiseServer.getPromisableType().name())
					){
			
			}else{
				
				throw new ConfigurationException(
						ERR_CONFIG,
						MSG_CONFIG, 
						"RequestType : "+promiseRequest.getRequestType()+" incompatible with the provided the promiseServer"
								+ "supporting : "+promiseServer.getPromisableType());
			}
		}else{
			throw new ConfigurationException(
					ERR_CONFIG,
					MSG_CONFIG, 
					"ClientIdentity : "+promiseRequest.getClientIdentity()+" is required for identifying service response processing ");
		}
		
		return this;
	}
	

	
}

