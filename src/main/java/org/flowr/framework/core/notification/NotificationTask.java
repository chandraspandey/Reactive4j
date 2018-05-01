package org.flowr.framework.core.notification;

import java.util.concurrent.Callable;

import org.flowr.framework.core.context.NotificationContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.CircuitBreaker;
import org.flowr.framework.core.node.ServiceMesh.ServiceTopologyMode;
import org.flowr.framework.core.service.extension.NotificationService.NotificationServiceStatus;

/**
 * Defines notification as discrete task executable as thread and provides integration with dispatcher capability. 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface NotificationTask extends Callable<NotificationServiceStatus>,NotificationDispatcher,CircuitBreaker {

	public void setNotificationContext(NotificationContext notificationContext);
	
	public NotificationContext getNotificationContext();
	
	public void setServiceMode(ServiceTopologyMode serviceTopologyMode);
	
	public ServiceTopologyMode getServiceTopologyMode();
	
	public void buildPipeline() throws ConfigurationException;
		
}
