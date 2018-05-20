package org.flowr.framework.core.notification;

import java.util.HashMap;
import java.util.concurrent.Callable;

import org.flowr.framework.core.node.EndPointDispatcher;
import org.flowr.framework.core.node.ServiceMesh.ServiceTopologyMode;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.service.extension.NotificationService.NotificationServiceStatus;

/**
 * Defines notification as discrete task executable as thread and provides integration with dispatcher capability. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface NotificationTask extends Callable<HashMap<NotificationProtocolType, NotificationServiceStatus>>,
	NotificationDispatcher {
	
	public void setServiceMode(ServiceTopologyMode serviceTopologyMode);
	
	public ServiceTopologyMode getServiceTopologyMode();
		
	public void setNotificationBufferQueue(NotificationBufferQueue notificationBufferQueue) ;
	
	public void configure(HashMap<NotificationProtocolType,EndPointDispatcher> dispatcherMap); 
}
