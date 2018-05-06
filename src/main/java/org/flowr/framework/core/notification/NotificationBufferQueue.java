package org.flowr.framework.core.notification;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import org.flowr.framework.core.event.Event.EventType;
/**
 * Concrete implementation & extension of DelayQueue for storing in process
 * listener I/O capabilities.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class NotificationBufferQueue extends DelayQueue<Delayed> implements NotificationQueue{

	private QueueType queueType	= QueueType.FIFO;
	private EventType eventType =null; 

	@Override
	public void setQueueType(QueueType queueType) {
		this.queueType = queueType;
	}

	@Override
	public QueueType getQueueType() {

		return this.queueType;
	}
	
	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	
	public String toString() {
		
		return "NotificationBufferQueue { "+queueType+" | "+eventType+" | "+super.toString()+" } ";
	}


	
}

