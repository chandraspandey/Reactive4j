package org.flowr.framework.core.context;

import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
import org.flowr.framework.core.service.Service.ServiceMode;
import org.flowr.framework.core.service.Service.ServiceState;
import org.flowr.framework.core.service.Service.ServiceType;
import org.flowr.framework.core.service.extension.PromiseService.PromiseServerState;
import org.flowr.framework.core.service.extension.PromiseService.PromiseServerStatus;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Context extends Model{

	public ServiceType getServiceType();
	
	public static ProcessContext ProcessLifecycleContext(
			String serverIdentifier, 
			PromiseServerState promiseServerState,
			PromiseServerStatus promiseServerStatus,
			String serviceIdentifier,
			String processIdentifier){
		
		ProcessContext processContext = new ProcessContext();
		processContext.setServerIdentifier(serverIdentifier);
		processContext.setServerState(promiseServerState);
		processContext.setServerStatus(promiseServerStatus);
		processContext.setServiceIdentifier(serviceIdentifier);
		processContext.setProcessIdentifier(processIdentifier);			
		processContext.setNotificationDeliveryType(NotificationDeliveryType.INTERNAL);
		processContext.setNotificationProtocolType(ServerNotificationProtocolType.SERVER_INFORMATION);
		
		return processContext;
		
	}
	
	public static ProcessContext ServiceContext(
			String serverIdentifier, 
			PromiseServerState promiseServerState,
			PromiseServerStatus promiseServerStatus,
			String serviceIdentifier,
			String processIdentifier,
			NotificationDeliveryType notificationDeliveryType,
			ServerNotificationProtocolType serverNotificationProtocolType
			){
		
		ProcessContext processContext = new ProcessContext();
		processContext.setServerIdentifier(serverIdentifier);
		processContext.setServerState(promiseServerState);
		processContext.setServerStatus(promiseServerStatus);
		processContext.setServiceIdentifier(serviceIdentifier);
		processContext.setProcessIdentifier(processIdentifier);			
		processContext.setNotificationDeliveryType(notificationDeliveryType);
		processContext.setNotificationProtocolType(serverNotificationProtocolType);
		
		return processContext;
		
	}
	
	public static ServerContext ServerContext(
			String subscriptionIdentifier, 
			ServiceMode serviceMode,
			ServiceState serviceState,
			ServerNotificationProtocolType serverNotificationProtocolType) {
		
		ServerContext serverContext = new ServerContext();
		serverContext.setSubscriptionClientId(subscriptionIdentifier);
		serverContext.setServiceMode(serviceMode);
		serverContext.setServiceState(serviceState);
		serverContext.setNotificationProtocolType(serverNotificationProtocolType);
		
		return serverContext;
	}

}
