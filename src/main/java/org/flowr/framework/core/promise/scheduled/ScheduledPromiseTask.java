
/**
 * Implements Callable to provide concrete functionality for Promisable. The
 * concrete implementation provided by Promisable generates the PromiseResponse
 * on agreed schedule.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.promise.scheduled;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.model.Model.ResponseModel;
import org.flowr.framework.core.promise.AbstractPromiseTask;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;
import org.flowr.framework.core.promise.PromiseRequest;
import org.flowr.framework.core.promise.PromiseResponse;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.target.ReactiveTarget;

public class ScheduledPromiseTask extends AbstractPromiseTask {

    private PromiseRequest promiseRequest;
    
    public ScheduledPromiseTask(PromiseRequest promiseRequest,ReactiveTarget reactiveTarget){
        
        super(reactiveTarget);
        this.promiseRequest = promiseRequest;        
    }
     @Override
    public PromiseResponse call() throws Exception {
        return honorPromise();
    }

    @Override
    public PromiseResponse honorPromise() throws PromiseException {
        
        PromiseResponse promiseResponse = new PromiseResponse(); 
                    
        Scale scale = null; 
        
        String identifier = promiseRequest.getAcknowledgmentIdentifier();
        
        if(!isNegotiated()){
            
            scale =  negotiate(promiseRequest.getPromiseContext().getRequestScale());
            scale.setPromiseState(PromiseState.NEGOTIATED);
        }else if(identifier == null){
            
            scale =  getReactiveTarget().invokeAndReturn(
                    promiseRequest.getPromiseContext().getDeferableRequest(), 
                    promiseRequest.getPromiseContext().getRequestScale());                  
        }else{
            
            scale =  getReactiveTarget().invokeForProgress(identifier);              
            promiseResponse.setAcknowledgmentIdentifier(identifier);
        }
        
        if( scale != null ){
            
            if(scale instanceof ScheduledProgressScale){
            
                if(scale.getPromiseStatus() == PromiseStatus.COMPLETED &&
                        scale.getPromiseState() == PromiseState.FULFILLED){
                    
                    promiseResponse.setResponse((ResponseModel) getReactiveTarget().invokeWhenComplete(
                            identifier));
                    scale.getScaleOption().setNow(100.00);
                    Logger.getRootLogger().info("ScheduledPromiseTask : honorPromise : scale :  "+scale);
                }
                
                // Mark the acknowledgement id for identification for multi client/request scenario
                promiseResponse.setAcknowledgmentIdentifier(
                        scale.getAcknowledgmentIdentifier());
                
                promiseResponse.setStatus(ResponseCode.OK.getResponseStatus());
                
                // Mark the NotificationDeliveryType as per request
                scale.setNotificationDeliveryType(promiseRequest.getPromiseContext().getRequestScale()
                        .getNotificationDeliveryType());
                
                // Mark the client id for identification for multi client scenario
                scale.setSubscriptionClientId(promiseRequest.getPromiseContext().getRequestScale()
                        .getSubscriptionClientId());
                promiseResponse.setProgressScale(scale);
            }else{
                promiseResponse.setStatus(ResponseCode.TYPE_ERROR.getResponseStatus()); 
                promiseResponse.setErrorMessage(ErrorMap.ERR_TYPE.getErrorMessage()+
                        "Expected Format of type ScheduledProgressScale recieved : "+
                        scale.getClass().getSimpleName());
            }
            
        }else{
            // Error
            promiseResponse.setStatus(ResponseCode.SERVER_ERROR.getResponseStatus());   
            promiseResponse.setErrorMessage(ErrorMap.ERR_MANDATORY.getErrorMessage() +
                    "Scale is mandatory in response. Recieved : "+scale);
        }
        
        return promiseResponse;
    }
}
