package org.flowr.framework.core.node;

import java.util.Collection;

import org.flowr.framework.core.context.NotificationContext;
import org.flowr.framework.core.event.pipeline.EventPipeline;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.service.ServiceEndPoint;
import org.flowr.framework.core.service.extension.NotificationService.NotificationServiceStatus;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface EndPointDispatcher {

	public void configure(NotificationProtocolType notificationProtocolType, Collection<ServiceEndPoint> serviceEndPointList
		, EventPipeline eventPipeline);
	
	public NotificationServiceStatus dispatch(NotificationContext notificationContext);
	
	public Collection<ServiceEndPoint> getEndPointList() ;

}
