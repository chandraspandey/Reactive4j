
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.process;

import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.context.Context.PromiseContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.model.Model.RequestModel;
import org.flowr.framework.core.promise.PromiseRequest;
import org.flowr.framework.core.promise.PromiseTypeServer;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.service.ServiceFramework;

public class ProcessRequest{
    
    private RequestScale requestScale;
    private PromiseContext promiseContext;
    private PromiseRequest promiseRequest;
    private PromiseTypeServer promiseServer;
    
    public ProcessRequest(ServiceFramework serviceFramework,PromiseRequest promiseRequest, 
            PromiseTypeServer promiseServer,RequestModel request) throws ConfigurationException{
        
        this.promiseRequest = promiseRequest;
        this.promiseServer  = promiseServer;
        
        requestScale = serviceFramework.getCatalog().getConfigurationService().getRequestScale(
                            ProcessClient.subscriptionId());
        
        promiseContext = new PromiseContext();
        promiseContext.setRequestScale(requestScale);
        promiseContext.setTimelineRequired(true);
        promiseContext.setDeferableRequest(request);    
        
        promiseRequest.setPromiseContext(promiseContext);
        serviceFramework.getCatalog().getRoutingService().bindServiceRoute(promiseRequest.getClientIdentity()
                ,promiseServer.getClass());

    }
    
    public ProcessRequest build() throws ConfigurationException{
        
        if(promiseRequest.getClientIdentity() != null){
        
            if( promiseRequest.getRequestType() != null && promiseServer != null &&
                    promiseRequest.getRequestType().name().equals(promiseServer.getPromisableType().name())
                    ){
            
                return this;
            }else{
                
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"RequestType : "+promiseRequest.getRequestType()+
                        " incompatible with the provided the promiseServer supporting : "+promiseServer);
            }
        }else{
            throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,"ClientIdentity : "+promiseRequest.getClientIdentity()+
                    " is required for identifying service response processing ");
        }
        
    }    
}

