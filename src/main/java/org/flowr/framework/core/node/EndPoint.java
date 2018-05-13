package org.flowr.framework.core.node;

import java.sql.Timestamp;

import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */


public interface EndPoint {

	public enum EndPointStatus{
		NEGOTIATE,
		REACHABLE,
		UNREACHABLE
	}
	
	public void setNotificationProtocolType(NotificationProtocolType notificationProtocolType);
	
	public NotificationProtocolType getNotificationProtocolType();
	
	public EndPointStatus getEndPointStatus();
	
	public ServiceConfiguration getServiceConfiguration();
	
	public Timestamp getLastUpdated();

	public PipelineFunctionType getPipelineFunctionType();

	public void setPipelineFunctionType(PipelineFunctionType pipelineFunctionType);
}
