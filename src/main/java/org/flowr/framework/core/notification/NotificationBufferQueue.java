package org.flowr.framework.core.notification;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.event.ChangeEventEntity;
import org.flowr.framework.core.event.Event.EventType;
/**
 * Concrete implementation & extension of DelayQueue for storing in process
 * listener I/O capabilities.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class NotificationBufferQueue extends DelayQueue<Delayed> implements NotificationQueue {

	private QueueType queueType					= QueueType.STAGING;
	private QueueStagingType queueStagingType	= QueueStagingType.SEVERITY;
	private EventType eventType 				= null; 

	public boolean add(Delayed e) {
		
		if(e instanceof ChangeEventEntity) {
			((ChangeEventEntity)e).setQueueType(queueType);
			((ChangeEventEntity)e).setQueueStagingType(queueStagingType);
		}
		return super.add(e);
	}
	
	public boolean offer(Delayed e) {
		
		if(e instanceof ChangeEventEntity) {
			((ChangeEventEntity)e).setQueueType(queueType);
			((ChangeEventEntity)e).setQueueStagingType(queueStagingType);
		}
		return super.offer(e);
	}
	
	public void put(Delayed e) {
		
		if(e instanceof ChangeEventEntity) {
			((ChangeEventEntity)e).setQueueType(queueType);
			((ChangeEventEntity)e).setQueueStagingType(queueStagingType);
		}
		super.put(e);
	}
	
	public boolean offer(Delayed e, long timeout, TimeUnit unit) {
		
		if(e instanceof ChangeEventEntity) {
			((ChangeEventEntity)e).setQueueType(queueType);
			((ChangeEventEntity)e).setQueueStagingType(queueStagingType);
		}
		return super.offer(e,timeout,unit);
	}
	
	@Override
	public void setQueueType(QueueType queueType) {
		this.queueType = queueType;
	}

	@Override
	public QueueType getQueueType() {

		return this.queueType;
	}
	
	@Override
	public EventType getEventType() {
		return this.eventType;
	}

	@Override
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	
	@Override
	public void setQueueStagingType(QueueStagingType queueStagingType) {
		this.queueStagingType = queueStagingType;
	}
	
	@Override
	public QueueStagingType getQueueStagingType() {
		
		return this.queueStagingType;
	}
	
	public String toString() {
		
		return "NotificationBufferQueue { "+queueType+" | "+queueStagingType+" | "+eventType+" | "+super.toString()+" } ";
	}




	
}

