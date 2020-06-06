
/**
 * Implements Callable to provide abstract common functionality for Promisable.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.promise;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.model.Model.ResponseModel;
import org.flowr.framework.core.node.Autonomic;
import org.flowr.framework.core.node.ha.FailsafeCallable;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;
import org.flowr.framework.core.promise.Scale.ScaleOption;
import org.flowr.framework.core.promise.deferred.ProgressScale;
import org.flowr.framework.core.promise.phase.PhasedProgressScale;
import org.flowr.framework.core.promise.scheduled.ScheduledProgressScale;
import org.flowr.framework.core.target.ReactiveTarget;

public abstract class AbstractPromiseTask implements FailsafeCallable<PromiseResponse>, Promisable,
    Autonomic<RequestScale,Scale> {

    private ReactiveTarget reactiveTarget;
    
    public AbstractPromiseTask(ReactiveTarget reactiveTarget){
        this.reactiveTarget = reactiveTarget;
    }
    
    @Override
    public boolean isNegotiated() {
        return reactiveTarget.isNegotiated();
    }
    
    @Override
    public Scale negotiate(RequestScale requestScale) throws PromiseException {
        return reactiveTarget.negotiate(requestScale);
    }
    
    protected void processPromise(PromiseRequest promiseRequest, PromiseResponse promiseResponse,Scale scale,
        String identifier) throws PromiseException{
        
        PromiseState promiseState = scale.getPromiseState();
        
        processForPromiseCompletion(promiseResponse,scale,identifier);
        
        // Mark the acknowledgement id for identification for multi client/request scenario
        promiseResponse.setAcknowledgmentIdentifier(scale.getAcknowledgmentIdentifier());
                
        if(promiseState == PromiseState.ERROR) {
            promiseResponse.setStatus(ResponseCode.SERVER_ERROR.getResponseStatus());
        }else if( promiseState == PromiseState.TIMEOUT ) {
            promiseResponse.setStatus(ResponseCode.TIMEOUT.getResponseStatus());
        }else {
            promiseResponse.setStatus(ResponseCode.OK.getResponseStatus());
        }
        
        // Mark the NotificationDeliveryType as per request
        scale.setNotificationDeliveryType(promiseRequest.getPromiseContext().getRequestScale()
                .getNotificationDeliveryType());
        
        // Mark the client id for identification for multi client scenario
        scale.setSubscriptionClientId(promiseRequest.getPromiseContext().getRequestScale()
                .getSubscriptionClientId());
        promiseResponse.setProgressScale(scale);
    }

    protected boolean processForPromiseCompletion(PromiseResponse promiseResponse,Scale scale,
            String identifier) throws PromiseException{
        
        boolean isCompletionProcessed = false;
        
        PromiseState promiseState = scale.getPromiseState();
        
        if(scale.getPromiseStatus() == PromiseStatus.COMPLETED &&
              (  
                      promiseState == PromiseState.FULFILLED ||
                      promiseState == PromiseState.ERROR ||
                      promiseState == PromiseState.TIMEOUT
              )
        ){
            
            promiseResponse.setResponse((ResponseModel) getReactiveTarget().invokeWhenComplete(identifier));
            
            scale.getScaleOption().setNow(100.00);              
            isCompletionProcessed = true;
        }
 
        Logger.getRootLogger().info("AbstractPromiseTask : processForPromiseCompletion : isCompletionProcessed : "+
                isCompletionProcessed+", \n scale :  "+scale);
        
        return isCompletionProcessed;
    }

    
    @Override
    public PromiseResponse handlePromiseError(PromiseRequest promiseRequest,ResponseCode responseCode){
        
        PromiseResponse promiseResponse = null;
        
        switch(responseCode){
            case SERVER_ERROR:
                promiseResponse = handleError(promiseRequest,responseCode);
                break;
            case TIMEOUT:
                promiseResponse = handleTimeout(promiseRequest,responseCode);
                break;
            default:
                promiseResponse = handleError(promiseRequest,responseCode);
                break;
        
        }
        
        return promiseResponse;
    }
    
    private static Scale deriveType(PromiseRequest promiseRequest) {
        
        Scale scale = null;
        
        switch(promiseRequest.getRequestType()) {
        
            case PROMISE:
                scale = new ProgressScale();
                break;
            case PROMISE_DEFFERED:
                scale = new ProgressScale();
                break;
            case PROMISE_PHASED:
                scale = new PhasedProgressScale();
                break;
            case PROMISE_SCHEDULED:
                scale = new ScheduledProgressScale();
                break;
            case PROMISE_STAGED:
                scale = new ProgressScale();
                break;
            default:
                scale = new ProgressScale();
                break;        
        }        
        
        ScaleOption  scaleOption = new ScaleOption();
        scale.setScaleOption(scaleOption);
        
        scale.acceptIfApplicable(promiseRequest.getPromiseContext().getRequestScale());        
        scale.setPromiseStatus(PromiseStatus.COMPLETED);
        scale.setPromiseState(PromiseState.FULFILLED);
        scale.getScaleOption().setNow(100.00);          
        
        // Mark the NotificationDeliveryType as per request
        scale.setNotificationDeliveryType(promiseRequest.getPromiseContext().getRequestScale()
                .getNotificationDeliveryType());
        
        // Mark the client id for identification for multi client scenario
        scale.setSubscriptionClientId(promiseRequest.getPromiseContext().getRequestScale().getSubscriptionClientId());
        
        return scale;
    }

    @Override
    public PromiseResponse handleError(PromiseRequest promiseRequest,ResponseCode responseCode){
        
        PromiseResponse promiseResponse = new PromiseResponse(); 
                        
        Scale scale = deriveType(promiseRequest);
        scale.setPromiseState(PromiseState.ERROR);

        promiseResponse.setStatus(responseCode.getResponseStatus());
        promiseResponse.setAcknowledgmentIdentifier(promiseRequest.getAcknowledgmentIdentifier());
        
        promiseResponse.setProgressScale(scale);
        
        if(responseCode == ResponseCode.SERVER_ERROR){
            promiseResponse.setErrorMessage(ErrorMap.ERR_TYPE.getErrorMessage() +
                    "Scale is mandatory in response. Recieved : "+scale);
        }
        
        if(responseCode == ResponseCode.TYPE_ERROR){
            promiseResponse.setErrorMessage(ErrorMap.ERR_MANDATORY.getErrorMessage()+
                    "Expected Format of type ProgressScale recieved : "+
                    scale.getClass().getSimpleName());
        }
        
        Logger.getRootLogger().info("AbstractPromiseTask : handleError : promiseResponse = "+promiseResponse);
        
        return promiseResponse;
    }
    
    @Override
    public PromiseResponse handleTimeout(PromiseRequest promiseRequest,ResponseCode responseCode){
        
        PromiseResponse promiseResponse = new PromiseResponse(); 
        
        Scale scale = deriveType(promiseRequest);

        scale.setPromiseState(PromiseState.TIMEOUT);        
        promiseResponse.setAcknowledgmentIdentifier(promiseRequest.getAcknowledgmentIdentifier());
        promiseResponse.setStatus(responseCode.getResponseStatus());
        
        promiseResponse.setProgressScale(scale);
        promiseResponse.setErrorMessage(ErrorMap.ERR_TIMEOUT.getErrorMessage());
        
        Logger.getRootLogger().info("AbstractPromiseTask : handleTimeout : promiseResponse = "+promiseResponse);
        
        return promiseResponse;
    }

    public ReactiveTarget getReactiveTarget() {
        return reactiveTarget;
    }

}
