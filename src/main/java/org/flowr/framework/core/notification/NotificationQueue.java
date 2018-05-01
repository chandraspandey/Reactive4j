package org.flowr.framework.core.notification;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NotificationQueue{
	
	public enum QueueType{
		LIFO,
		FIFO
	}
	
	public void setQueueType(QueueType queueType);
	
	public QueueType getQueueType();
}
