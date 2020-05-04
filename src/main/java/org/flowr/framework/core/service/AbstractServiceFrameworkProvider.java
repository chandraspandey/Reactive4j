
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.context.Context.PromiseContext;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.promise.PromiseRequest;
import org.flowr.framework.core.promise.PromiseResponse;
import org.flowr.framework.core.target.ReactiveTarget;

public abstract class AbstractServiceFrameworkProvider extends AbstractServiceBus {


    @Override
    public PromiseResponse service(PromiseRequest promiseRequest) throws PromiseException{
        
        PromiseResponse response = null;        
        
        try {
        
            if(promiseRequest != null && promiseRequest.getRequestType() != null){
                
                PromiseContext promiseContext = promiseRequest.getPromiseContext();
                
                if(     promiseContext != null && promiseContext.getRequestScale() != null &&
                        promiseContext.getRequestScale().getSubscriptionClientId() != null){
                
                        response = hamdleServiceResponse(promiseRequest, response);
                }else{
                    
                    throw new PromiseException(ErrorMap.ERR_REQUEST_INVALID,
                            "Invalid PromiseContext, RequestScale or Subscription id : "+promiseContext);
                }
            }else{
                
                response = new PromiseResponse();               
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);               
                response.setErrorMessage(ErrorMap.ERR_REQUEST_TYPE_NOT_FOUND.getErrorMessage()+
                    "Request Type not provided for the request."+promiseRequest);   
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException 
                | InvocationTargetException | SecurityException | NoSuchMethodException e) {
            Logger.getRootLogger().error(e);
            throw new PromiseException(ErrorMap.ERR_ROUTE_MAPPING_NOT_FOUND, 
                    "Unable to instantiate mapping classes.");
        }
        
        return response;
    }

    private PromiseResponse hamdleServiceResponse(PromiseRequest promiseRequest,
            PromiseResponse response) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException, PromiseException {
        
        
        Class<? extends ServiceResponse> responseHandler = this.getCatalog().getRoutingService().getServiceRoute(
                promiseRequest);

        if(responseHandler != null){
                
            @SuppressWarnings("unchecked")
            Class<? extends ServiceResponse> klass = 
                    (Class<? extends ServiceResponse>) Class.forName(responseHandler.getCanonicalName());
            
            ReactiveTarget reactiveTarget =  klass.getDeclaredConstructor().newInstance().getReactiveTarget();

            Logger.getRootLogger().info(" ServiceFrameworkProvider : "+
                    "responseHandler : "+ responseHandler.getSimpleName()+
                    " | reactiveTarget : "+reactiveTarget.getClass().getSimpleName()+
                    " | RequestType : "+promiseRequest.getRequestType()+
                    " | ClientIdentity "+promiseRequest.getClientIdentity()
            );
            
            
            response = handleServiceRequest(promiseRequest, response, reactiveTarget);
            
            if(response != null) {
                response.setStatus(HttpServletResponse.SC_OK);
            }
        
        }else{
            
            response = new PromiseResponse();               
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);               
            response.setErrorMessage(ErrorMap.ERR_ROUTE_MAPPING_NOT_FOUND.getErrorMessage()+
                "Configuration mapping for request class "+promiseRequest.getClass().getName()+" does not exists.");
        }
        return response;
    }

    private PromiseResponse handleServiceRequest(PromiseRequest promiseRequest,
            PromiseResponse response, ReactiveTarget reactiveTarget)
            throws PromiseException {
        
        
        switch(promiseRequest.getRequestType()){
            
            case PROMISE:{
                this.getCatalog().getPromiseService().getPromise().setReactiveTarget(reactiveTarget);
                response = this.getCatalog().getPromiseService().getPromise().handle(promiseRequest);
                break;
            }case PROMISE_DEFFERED:{
                this.getCatalog().getDefferedPromiseService().getPromise().setReactiveTarget(reactiveTarget);
                response = this.getCatalog().getDefferedPromiseService().getPromise().handle(promiseRequest);
                break;
            }case PROMISE_PHASED:{
                
                this.getCatalog().getPhasedPromiseService().getPromise().setReactiveTarget(reactiveTarget);
                response = this.getCatalog().getPhasedPromiseService().getPromise().handle(promiseRequest);
                break;
            }case PROMISE_SCHEDULED:{
                this.getCatalog().getScheduledPromiseService().getPromise().setReactiveTarget(reactiveTarget);
                response = this.getCatalog().getScheduledPromiseService().getPromise().handle(promiseRequest);
                break;
            }case PROMISE_STAGED:{
                break;
            }default:{
                this.getCatalog().getPromiseService().getPromise().setReactiveTarget(reactiveTarget);
                response = this.getCatalog().getPromiseService().getPromise().handle(promiseRequest);
                break;
            }
        }
        return response;
    }
}
