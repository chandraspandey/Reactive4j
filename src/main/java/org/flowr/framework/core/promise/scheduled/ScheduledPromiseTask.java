
/**
 * Implements Callable to provide concrete functionality for Promisable. The
 * concrete implementation provided by Promisable generates the PromiseResponse
 * on agreed schedule.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.promise.scheduled;

import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.promise.AbstractPromiseTask;
import org.flowr.framework.core.promise.Promise.PromiseState;
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
            
                processPromise(promiseRequest,promiseResponse,scale,identifier);
            }else{
                promiseResponse.setStatus(ResponseCode.TYPE_ERROR.getResponseStatus()); 
                promiseResponse.setErrorMessage(ErrorMap.ERR_TYPE.getErrorMessage()+
                        "Unexpected Format of type ScheduledProgressScale recieved : "+
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
