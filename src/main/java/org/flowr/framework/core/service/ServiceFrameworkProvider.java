package org.flowr.framework.core.service;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_REQUEST_INVALID;
import static org.flowr.framework.core.constants.ExceptionConstants.ERR_ROUTE_MAPPING_NOT_FOUND;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_REQUEST_INVALID;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_REQUEST_TYPE_NOT_FOUND;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_ROUTE_MAPPING_NOT_FOUND;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletResponse;

import org.flowr.framework.core.context.PromiseContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.promise.PromiseRequest;
import org.flowr.framework.core.promise.PromiseResponse;
import org.flowr.framework.core.target.ReactiveTarget;

public abstract class ServiceFrameworkProvider<REQUEST,RESPONSE> extends ServiceBus<REQUEST,RESPONSE> {


	@Override
	public PromiseResponse<RESPONSE> service(PromiseRequest<REQUEST> promiseRequest) throws PromiseException,
		ConfigurationException{
		
		PromiseResponse<RESPONSE> response = null;		
		
		try {
		
			if(promiseRequest != null && promiseRequest.getRequestType() != null){
				
				PromiseContext<REQUEST> promiseContext = promiseRequest.getPromiseContext();
				
				if( 	promiseContext != null && promiseContext.getRequestScale() != null &&
						promiseContext.getRequestScale().getSubscriptionClientId() != null){
				
					Class<? extends ServiceResponse> responseHandler = this.getRoutingService().getServiceRoute(promiseRequest);
					
						if(responseHandler != null){
								
							@SuppressWarnings("unchecked")
							Class<? extends ServiceResponse> klass = 
									(Class<? extends ServiceResponse>) Class.forName(responseHandler.getCanonicalName());
							
							ReactiveTarget<REQUEST, RESPONSE> reactiveTarget = ((ServiceResponse) klass.getDeclaredConstructor().newInstance(new Object[0])).getReactiveTarget();

							System.out.println(" ServiceFrameworkProvider : "+
									"responseHandler : "+ responseHandler.getSimpleName()+
									" | reactiveTarget : "+reactiveTarget.getClass().getSimpleName()+
									" | RequestType : "+promiseRequest.getRequestType()+
									" | ClientIdentity "+promiseRequest.getClientIdentity()
							);
							
							
							switch(promiseRequest.getRequestType()){
								
								case PROMISE:{
									this.getPromiseService().getPromise().setReactiveTarget(reactiveTarget);
									response = this.getPromiseService().getPromise().handle(promiseRequest);
									break;
								}case PROMISE_DEFFERED:{
									this.getDefferedPromiseService().getPromise().setReactiveTarget(reactiveTarget);
									response = this.getDefferedPromiseService().getPromise().handle(promiseRequest);
									break;
								}case PROMISE_PHASED:{
									
									this.getPhasedPromiseService().getPromise().setReactiveTarget(reactiveTarget);
									response = this.getPhasedPromiseService().getPromise().handle(promiseRequest);									
									break;
								}case PROMISE_SCHEDULED:{
									this.getScheduledPromiseService().getPromise().setReactiveTarget(reactiveTarget);
									response = this.getScheduledPromiseService().getPromise().handle(promiseRequest);
									break;
								}case PROMISE_STAGED:{
									break;
								}default:{
									this.getPromiseService().getPromise().setReactiveTarget(reactiveTarget);
									response = this.getPromiseService().getPromise().handle(promiseRequest);
									break;
								}
							}
							
							response.setStatus(HttpServletResponse.SC_OK);
						
						}else{
							
							response = new PromiseResponse<RESPONSE>(); 				
							response.setStatus(HttpServletResponse.SC_NOT_FOUND);				
							response.setErrorMessage(MSG_ROUTE_MAPPING_NOT_FOUND+
								"Configuration mapping for request class "+promiseRequest.getClass().getName()+" does not exists.");				
						}
				}else{
					
					throw new ConfigurationException(ERR_REQUEST_INVALID,MSG_REQUEST_INVALID,
							"Invalid PromiseContext, RequestScale or Subscription id : "+promiseContext);
				}
			}else{
				
				response = new PromiseResponse<RESPONSE>(); 				
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);				
				response.setErrorMessage(MSG_REQUEST_TYPE_NOT_FOUND+
					"Request Type not provided for the request."+promiseRequest.getClass().getName());	
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchMethodException e) {
			e.printStackTrace();
			throw new ConfigurationException(ERR_ROUTE_MAPPING_NOT_FOUND,MSG_ROUTE_MAPPING_NOT_FOUND, 
					"Unable to instantiate mapping classes.");
		}
		
		return response;
	}
}
