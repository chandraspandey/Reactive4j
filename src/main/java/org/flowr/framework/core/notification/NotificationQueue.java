package org.flowr.framework.core.notification;

import org.flowr.framework.core.event.Event.EventType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NotificationQueue{
	
	public enum QueueType{
		LIFO,
		FIFO,
		STAGING
	}
	
	public enum QueueStagingType{
		PRIORITY,
		SEVERITY
	}
	
	public EventType getEventType();

	public void setEventType(EventType eventType);
	
	public void setQueueStagingType(QueueStagingType queueStagingType);
	
	public QueueStagingType getQueueStagingType();
	
	public void setQueueType(QueueType queueType);
	
	public QueueType getQueueType();
}
