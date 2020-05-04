
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package example.flowr.promise.client;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.flowr.framework.core.config.NotificationConfig;
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

import example.flowr.promise.server.PhasedPromiseServer;

public class PhasedPromiseClient extends AbstractPromiseClient {

    private ProcessBuilder builder                          = new ProcessBuilder();

    public PhasedPromiseClient() {
        super(RequestType.PROMISE_PHASED,PhasedPromiseClient.class.getSimpleName());
    }

    
    @Override
    public PromiseRequest  buildPromiseRequest() throws ConfigurationException{
        
        PromiseRequest promiseRequest = new PromiseRequest();
        
        promiseRequest.setRequestType(RequestType.PROMISE_PHASED);        
        promiseRequest.setTimelineRequired(true);
        promiseRequest.setClientIdentity(buildClientIdentity());
        return promiseRequest;  
    }
    
    @Override
    public PromiseTypeServer buildPromiseTypeServer() throws ConfigurationException{
                
        return new PhasedPromiseServer();
    }
 
    @Override
    public void run()  {
        
        ResponseModel response = null;
        
        try {
                    
            if(isConfigured()){
                
                startupAdapter(Optional.of(getAdapterServiceConfiguration().getConfigAsProperties()));
            
                PromiseRequest promiseRequest       = buildPromiseRequest();
                
                PromiseTypeServer integrationServer = buildPromiseTypeServer();
                
                NotificationConfig serverConfig = getOperationConfig().getServerNotificationConfig();
                NotificationConfig clientConfig = getOperationConfig().getClientNotificationConfig();
                
                ServiceProvider processProvider = 
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
                
                response = promiseResponse.getResponse();
                
                Logger.getRootLogger().info("PhasedPromiseClient : Response = "+response);
                Logger.getRootLogger().info("PhasedPromiseClient : PromiseResponse = "+promiseResponse);    
                
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
