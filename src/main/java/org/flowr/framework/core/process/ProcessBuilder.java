package org.flowr.framework.core.process;

import java.util.HashMap;

import org.flowr.framework.core.context.NotificationContext;
import org.flowr.framework.core.context.RouteContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.NotificationServiceAdapter;
import org.flowr.framework.core.notification.NotificationTask;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.promise.PromiseRequest;
import org.flowr.framework.core.promise.PromiseTypeServer;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.ServiceProvider;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ProcessBuilder<REQ,RES> implements ProcessLifecycle{

	private ServiceProvider<REQ,RES> processProvider 		= null;
	
	public ServiceProvider<REQ,RES> build() throws ConfigurationException{

		return processProvider;
	}
	
	@SuppressWarnings("unchecked")
	public ProcessBuilder<REQ,RES> withProvider(REQ request,RES response) 
		throws ConfigurationException{
				
		processProvider = (ServiceProvider<REQ, RES>) ServiceFramework.getInstance();	
		
		System.out.println("processProvider : "+processProvider);
		
		return this;
	}
	
	public ProcessBuilder<REQ,RES> withServerConfigurationAs(
		HashMap<NotificationProtocolType,NotificationSubscription> notificationMap,NotificationTask notificationTask, 
		NotificationServiceAdapter notificationServiceAdapter) throws ConfigurationException{
		
		ProcessServer<REQ,RES> processServer = new ProcessServer<REQ,RES>(processProvider);
		NotificationContext serverNotificationContext = processServer.withServerNotificationSubscription(
				notificationMap);
		RouteContext serverRouteContext = processServer.withServerNotificationTask(notificationTask, 
				notificationServiceAdapter,serverNotificationContext);
		processServer.build(serverRouteContext);
		
		return this;
	}
	
	public ProcessBuilder<REQ,RES> andClientConfigurationAs(HashMap<NotificationProtocolType,
		NotificationSubscription> notificationMap,NotificationTask notificationTask, 
		NotificationServiceAdapter notificationServiceAdapter) throws ConfigurationException{
	
		ProcessClient<REQ,RES> processClient = new ProcessClient<REQ,RES>(processProvider);
		NotificationContext clientNotificationContext = processClient.andClientNotificationSubscription(
				notificationMap);
		RouteContext clientRouteContext = processClient.andClientNotificationTask(notificationTask, 
				notificationServiceAdapter,clientNotificationContext);
		processClient.build(clientRouteContext);
		
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public ProcessBuilder<REQ,RES> forPromiseRequestAndResponseServerAs(PromiseRequest<REQ> promiseRequest, REQ req,
		PromiseTypeServer<REQ,RES> promiseServer) throws ConfigurationException{		

		@SuppressWarnings("rawtypes")
		ProcessRequest<REQ,RES>  processRequest = new ProcessRequest<REQ,RES>(
													((ServiceFramework)processProvider),
													promiseRequest,
													promiseServer,req);
		
		processRequest.build();		
			
		return this;
	}

}