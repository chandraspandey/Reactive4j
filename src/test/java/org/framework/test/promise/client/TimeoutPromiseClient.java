
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.framework.test.promise.client;

import java.util.AbstractMap.SimpleEntry;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.flowr.framework.core.config.Configuration.NotificationConfig;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.model.Model.ResponseModel;
import org.flowr.framework.core.process.ProcessBuilder;
import org.flowr.framework.core.promise.PromiseRequest;
import org.flowr.framework.core.promise.PromiseResponse;
import org.flowr.framework.core.promise.PromiseTypeClient.AbstractPromiseClient;
import org.flowr.framework.core.promise.PromiseTypeServer;
import org.flowr.framework.core.service.ServiceProvider;
import org.flowr.framework.core.service.ServiceRequest.RequestType;
import org.framework.test.ConfigurableTest.PromiseCallback.CallbackHandler;
import org.framework.test.ConfigurableTest.PromiseCallback.CallbackType;
import org.framework.test.promise.server.TimeoutPromiseServer;
import org.junit.Assert;

public final class TimeoutPromiseClient extends AbstractPromiseClient {

    private ProcessBuilder builder          = new ProcessBuilder();
    private CallbackHandler callbackHandler;
    
    public TimeoutPromiseClient() {
        super(RequestType.PROMISE,TimeoutPromiseClient.class.getSimpleName());
    }
    
    public void setCallbackHandler(CallbackHandler callbackHandler) {
        
        this.callbackHandler = callbackHandler;
    }
    
    @Override
    public PromiseRequest  buildPromiseRequest() throws ConfigurationException{
        
        PromiseRequest promiseRequest = new PromiseRequest();
        
        promiseRequest.setRequestType(RequestType.PROMISE_DEFFERED);        
        promiseRequest.setTimelineRequired(true);
        promiseRequest.setClientIdentity(buildClientIdentity());
        return promiseRequest;  
    }
    
    @Override
    public PromiseTypeServer buildPromiseTypeServer() throws ConfigurationException{
                
        TimeoutPromiseServer businessPromiseServer = new TimeoutPromiseServer();
        TimeoutPromiseServer.setCallbackHandler(callbackHandler);
        
        return businessPromiseServer;
    }

    public void setArtificialDelay(long artificialDelay) {
        TimeoutPromiseServer.setArtificialDelay(artificialDelay);
    }

    @Override
    public void run() {
        
        ResponseModel response = null;
        
        try {
        
            if(isConfigured()){
                
                startupAdapter(Optional.of(getAdapterServiceConfiguration().getConfigAsProperties()));
                
                Logger.getRootLogger().info("TimeoutPromiseClient : "+getOperationConfig()); 
            
                PromiseRequest promiseRequest       = buildPromiseRequest();
                
                PromiseTypeServer integrationServer = buildPromiseTypeServer();
                
                NotificationConfig serverConfig     = getOperationConfig().getServerNotificationConfig();
                NotificationConfig clientConfig     = getOperationConfig().getClientNotificationConfig();
                
                ServiceProvider processProvider     = 
                builder.withProvider()
                    .withServerConfigurationAs(
                            serverConfig.getSubscriptionMap(), 
                            serverConfig.getNotificationTask(), 
                            serverConfig.getNotificationServiceAdapter()
                     )
                    .andClientConfigurationAs(
                            clientConfig.getSubscriptionMap(), 
                            clientConfig.getNotificationTask(), 
                            clientConfig.getNotificationServiceAdapter()
                    )
                    .forPromiseRequestAndResponseServerAs(promiseRequest, getRequest(),integrationServer)
                    .build();               
                
                PromiseResponse promiseResponse = processProvider.service(promiseRequest);
                
                Logger.getRootLogger().info("TimeoutPromiseClient : PromiseResponse = "+promiseResponse);   
                


                this.callbackHandler.doCallback(
                        new SimpleEntry<>(CallbackType.CLIENT,promiseResponse.getProgressScale())
                );               
                
                response = promiseResponse.getResponse();
                
                Logger.getRootLogger().info("TimeoutPromiseClient : response = "+response); 
                
                Assert.assertNull(" TimeoutPromiseClient should be null.", response);
                
                setSessionTimeline(Optional.of(promiseResponse.getTimeline()));
                
                shutdownAdapter(Optional.of(getAdapterServiceConfiguration().getConfigAsProperties()));
        
            }else{
                throw new ConfigurationException(ErrorMap.ERR_CONFIG,"Client not configured for execution.");
            }
            
        } catch (ConfigurationException | PromiseException e) {
            Logger.getRootLogger().error(e);
        }
        
    }
 
}
