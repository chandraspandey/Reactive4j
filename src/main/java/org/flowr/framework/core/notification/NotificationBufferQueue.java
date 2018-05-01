package org.flowr.framework.core.notification;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

/**
 * Concrete implementation & extension of DelayQueue for storing in process
 * listener I/O capabilities.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class NotificationBufferQueue extends DelayQueue<Delayed> implements NotificationQueue{

	private QueueType queueType	= QueueType.FIFO;


	@Override
	public void setQueueType(QueueType queueType) {
		this.queueType = queueType;
	}

	@Override
	public QueueType getQueueType() {

		return this.queueType;
	}
	
	public String toString() {
		
		return "NotificationBufferQueue { "+queueType+" | "+super.toString()+" } ";
	}
	
}

