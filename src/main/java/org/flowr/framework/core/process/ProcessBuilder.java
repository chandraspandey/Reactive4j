
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.process;

import java.util.Map;

import org.flowr.framework.core.context.Context.NotificationContext;
import org.flowr.framework.core.context.Context.RouteContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.model.Model.RequestModel;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.NotificationTask;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.promise.PromiseRequest;
import org.flowr.framework.core.promise.PromiseTypeServer;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.ServiceProvider;

public class ProcessBuilder implements ProcessLifecycle{

    private ServiceProvider processProvider;
    
    public ServiceProvider build(){

        return processProvider;
    }

    public ProcessBuilder withProvider() {
                
        processProvider = (ServiceProvider) ServiceFramework.getInstance(); 
        
        return this;
    }
    
    public ProcessBuilder withServerConfigurationAs(
        Map<NotificationProtocolType,NotificationSubscription> notificationMap,NotificationTask notificationTask, 
        NotificationServiceAdapter notificationServiceAdapter) throws ConfigurationException{
        
        ProcessServer processServer = new ProcessServer(processProvider);
        NotificationContext serverNotificationContext = processServer.withServerNotificationSubscription(
                notificationMap);
        RouteContext serverRouteContext = processServer.withServerNotificationTask(notificationTask, 
                notificationServiceAdapter,serverNotificationContext);
        processServer.build(serverRouteContext);
        
        return this;
    }
    
    public ProcessBuilder andClientConfigurationAs(Map<NotificationProtocolType,
        NotificationSubscription> notificationMap,NotificationTask notificationTask, 
        NotificationServiceAdapter notificationServiceAdapter) throws ConfigurationException{
    
        ProcessClient processClient = new ProcessClient(processProvider);
        NotificationContext clientNotificationContext = processClient.andClientNotificationSubscription(
                notificationMap);
        RouteContext clientRouteContext = processClient.andClientNotificationTask(notificationTask, 
                notificationServiceAdapter,clientNotificationContext);
        processClient.build(clientRouteContext);
        
        return this;
    }
    
    public ProcessBuilder forPromiseRequestAndResponseServerAs(PromiseRequest promiseRequest, RequestModel req,
        PromiseTypeServer promiseServer) throws ConfigurationException{     

        ProcessRequest  processRequest = new ProcessRequest(
                                                    ((ServiceFramework)processProvider),
                                                    promiseRequest,
                                                    promiseServer,req);
        
        processRequest.build();     
            
        return this;
    }

}
