package org.flowr.framework.core.promise.scheduled;

import org.flowr.framework.core.constants.ExceptionMessages;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.node.Autonomic;
import org.flowr.framework.core.node.FailsafeCallable;
import org.flowr.framework.core.promise.Promisable;
import org.flowr.framework.core.promise.PromiseRequest;
import org.flowr.framework.core.promise.PromiseResponse;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;
import org.flowr.framework.core.target.ReactiveTarget;


/**
 * Implements Callable to provide concrete functionality for Promisable. The
 * concrete implementation provided by Promisable generates the PromiseResponse
 * on agreed schedule.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ScheduledPromiseTask<REQUEST,RESPONSE> implements 
	FailsafeCallable<PromiseResponse<RESPONSE>>, Promisable<REQUEST,RESPONSE>,Autonomic<RequestScale,Scale> {

	private PromiseRequest<REQUEST> promiseRequest;
	private ReactiveTarget<REQUEST,RESPONSE> reactiveTarget;
	private boolean isNegotiated = false;

	
	public ScheduledPromiseTask(PromiseRequest<REQUEST> promiseRequest,ReactiveTarget<REQUEST,RESPONSE> 
		reactiveTarget){
		
		this.promiseRequest = promiseRequest;
		this.reactiveTarget = reactiveTarget;
	}
	
	@Override
	public boolean isNegotiated() {
		isNegotiated = reactiveTarget.isNegotiated();
		return isNegotiated;
	}
	
	@Override
	public Scale negotiate(RequestScale requestScale) throws ConfigurationException {
		return reactiveTarget.negotiate(requestScale);
	}

	@Override
	public PromiseResponse<RESPONSE> call() throws Exception {
		return honorPromise();
	}

	@Override
	public PromiseResponse<RESPONSE> honorPromise() throws PromiseException, ConfigurationException {
		
		PromiseResponse<RESPONSE> promiseResponse = new PromiseResponse<RESPONSE>(); 
					
		Scale scale = null; 
		
		String identifier = promiseRequest.getAcknowledgmentIdentifier();
		
		if(!isNegotiated()){
			
			scale =  negotiate(promiseRequest.getPromiseContext().getRequestScale());
			scale.setPromiseState(PromiseState.NEGOTIATED);
		}else if(identifier == null){
			
			scale =  reactiveTarget.invokeAndReturn(
					promiseRequest.getPromiseContext().getDeferableRequest(), 
					promiseRequest.getPromiseContext().getRequestScale());					
		}else{
			
			scale =  reactiveTarget.invokeForProgress(identifier);				
			promiseResponse.setAcknowledgmentIdentifier(identifier);
		}
		
		if( scale != null ){
			
			if(scale instanceof ScheduledProgressScale){
			
				if(scale.getPromiseStatus() == PromiseStatus.COMPLETED &&
						scale.getPromiseState() == PromiseState.FULFILLED){
					
					promiseResponse.setResponse((RESPONSE) reactiveTarget.invokeWhenComplete(
							identifier));
				}
				
				// Mark the acknowledgement id for identification for multi client/request scenario
				promiseResponse.setAcknowledgmentIdentifier(
						scale.getAcknowledgmentIdentifier());
				
				promiseResponse.setStatus(ResponseCode.OK.getResponseStatus());
				
				// Mark the NotificationDeliveryType as per request
				scale.setNotificationDeliveryType(promiseRequest.getPromiseContext().getRequestScale().getNotificationDeliveryType());
				
				// Mark the client id for identification for multi client scenario
				scale.setSubscriptionClientId(promiseRequest.getPromiseContext().getRequestScale().getSubscriptionClientId());
				promiseResponse.setProgressScale(scale);
			}else{
				promiseResponse.setStatus(ResponseCode.TYPE_ERROR.getResponseStatus());	
				promiseResponse.setErrorMessage(ExceptionMessages.MSG_TYPE_ERROR+
						"Expected Format of type ScheduledProgressScale recieved : "+
						scale.getClass().getSimpleName());
			}
			
		}else{
			// Error
			promiseResponse.setStatus(ResponseCode.SERVER_ERROR.getResponseStatus());	
			promiseResponse.setErrorMessage(ExceptionMessages.MSG_MANDATORY +
					"Scale is mandatory in response. Recieved : "+scale);
		}
		
		//System.out.println("PromiseTask : honorPromise : promiseResponse = "+promiseResponse);
		
		return promiseResponse;
	}
	
	@Override
	public PromiseResponse<RESPONSE> handlePromiseError(ResponseCode responseCode){
		
		PromiseResponse<RESPONSE> promiseResponse = null;
		
		switch(responseCode){
			case SERVER_ERROR:
				promiseResponse = handleError(responseCode);
				break;
			case TIMEOUT:
				promiseResponse = handleTimeout(responseCode);
				break;
			default:
				promiseResponse = handleError(responseCode);
				break;
		
		}
		
		return promiseResponse;
	}

	@Override
	public PromiseResponse<RESPONSE> handleError(ResponseCode responseCode){
		
		PromiseResponse<RESPONSE> promiseResponse = new PromiseResponse<RESPONSE>(); 
		
		Scale scale = null;
		
		String identifier = promiseRequest.getAcknowledgmentIdentifier();
		
		scale = new ScheduledProgressScale();
		scale.acceptIfApplicable(promiseRequest.getPromiseContext().getRequestScale());
		scale.setPromiseState(PromiseState.ERROR);
		scale.setPromiseStatus(PromiseStatus.COMPLETED);
		promiseResponse.setStatus(responseCode.getResponseStatus());
		
		// Mark the NotificationDeliveryType as per request
		scale.setNotificationDeliveryType(promiseRequest.getPromiseContext().getRequestScale().getNotificationDeliveryType());
		
		// Mark the client id for identification for multi client scenario
		scale.setSubscriptionClientId(promiseRequest.getPromiseContext().getRequestScale().getSubscriptionClientId());
		
		promiseResponse.setAcknowledgmentIdentifier(identifier);
		
		if(responseCode == ResponseCode.SERVER_ERROR){
			promiseResponse.setErrorMessage(ExceptionMessages.MSG_MANDATORY +
					"Scale is mandatory in response. Recieved : "+scale);
		}else if(responseCode == ResponseCode.TYPE_ERROR){
			promiseResponse.setErrorMessage(ExceptionMessages.MSG_TYPE_ERROR+
					"Expected Format of type ScheduledProgressScale recieved : "+
					scale.getClass().getSimpleName());
		}
		
		promiseResponse.setProgressScale(scale);
		
		return promiseResponse;
	}
	
	@Override
	public PromiseResponse<RESPONSE> handleTimeout(ResponseCode responseCode){
		
		PromiseResponse<RESPONSE> promiseResponse = new PromiseResponse<RESPONSE>(); 
		
		Scale scale = null;
		
		String identifier = promiseRequest.getAcknowledgmentIdentifier();
		
		scale = new ScheduledProgressScale();
		scale.acceptIfApplicable(promiseRequest.getPromiseContext().getRequestScale());
		scale.setPromiseState(PromiseState.TIMEOUT);
		scale.setPromiseStatus(PromiseStatus.COMPLETED);
		promiseResponse.setStatus(responseCode.getResponseStatus());
		
		// Mark the NotificationDeliveryType as per request
		scale.setNotificationDeliveryType(promiseRequest.getPromiseContext().getRequestScale().getNotificationDeliveryType());
		
		// Mark the client id for identification for multi client scenario
		scale.setSubscriptionClientId(promiseRequest.getPromiseContext().getRequestScale().getSubscriptionClientId());
		
		promiseResponse.setAcknowledgmentIdentifier(identifier);
		
		promiseResponse.setProgressScale(scale);
		
		promiseResponse.setErrorMessage(ExceptionMessages.MSG_TIMEOUT);
		
		return promiseResponse;
	}

}
