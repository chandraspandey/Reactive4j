package org.flowr.framework.core.notification;

import java.util.Comparator;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

import org.flowr.framework.core.event.ChangeEvent;
import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.Scale.Priority;
import org.flowr.framework.core.promise.Scale.Severity;
/**
 * Concrete implementation & extension of DelayQueue for storing in process
 * listener I/O capabilities.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class NotificationBufferQueue extends DelayQueue<Delayed> implements NotificationQueue, Comparator<Event<EventModel>>{

	private QueueType queueType					= QueueType.STAGING;
	private QueueStagingType queueStagingType	= QueueStagingType.SEVERITY;
	private EventType eventType 				= null; 

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
	
	@Override
	public int compare(Event<EventModel> e1, Event<EventModel> e2) {
		
		int status = -1;
		
		if(queueType != null) {
		
			switch(queueType) {
				
				case FIFO:{
					
					if(e1.getEventTimestamp() != null && e2.getEventTimestamp() != null) {
						
						if(e1.getEventTimestamp().before(e2.getEventTimestamp())) {
							
							status = -1;
						}else if(e1.getEventTimestamp().equals(e2.getEventTimestamp())) {
							status = 0;
						}else {
							status = 1;
						}						
					}
					
					break;
				}case LIFO:{
					
					if(e1.getEventTimestamp() != null && e2.getEventTimestamp() != null) {
						
						if(e1.getEventTimestamp().before(e2.getEventTimestamp())) {
							
							status = 1;
						}else if(e1.getEventTimestamp().equals(e2.getEventTimestamp())) {
							status = 0;
						}else {
							status = -1;
						}						
					}
					break;
				}case STAGING:{
					
					if(
							queueStagingType != null && 
							e1 instanceof ChangeEvent && 
							e2 instanceof ChangeEvent && 
							((ChangeEvent<EventModel>)e1).getChangedModel().getReactiveMetaData() != null &&
							(((ChangeEvent<EventModel>)e1).getChangedModel().getReactiveMetaData() instanceof Scale) &&
							((ChangeEvent<EventModel>)e2).getChangedModel().getReactiveMetaData() != null &&
							(((ChangeEvent<EventModel>)e2).getChangedModel().getReactiveMetaData() instanceof Scale) 
					) {
						
						switch(queueStagingType) {
							
							case PRIORITY:{
								
																
								Priority priority1 	= ((Scale)((ChangeEvent<EventModel>)e1).getChangedModel().getReactiveMetaData()).getPriority();
								double scale1 		= Scale.Priority.getValueOf(priority1);
								Priority priority2 	= ((Scale)((ChangeEvent<EventModel>)e2).getChangedModel().getReactiveMetaData()).getPriority();
								double scale2 		= Scale.Priority.getValueOf(priority1);
								
								if(priority1 != null && priority2 != null) {
								
									switch(priority1) {
										
										case CRITICAL:{
											
											if(priority2 == Priority.LOW || priority2 == Priority.MEDIUM || priority2 == Priority.HIGH) {
												status = 1;
											}else if(priority2 == Priority.CRITICAL) {
												
												if(scale1 < scale2) {
													status = -1;
												}else if(scale1 == scale2) {
													status = 0;
												}else {
													status = 1;
												}
											}else {
												status = -1;
											}
											
											break;
										}case HIGH:{
											
											if(priority2 == Priority.LOW || priority2 == Priority.MEDIUM) {
												
												status = 1;										
											}else if(priority2 == Priority.HIGH) {
												
												if(scale1 < scale2) {
													status = -1;
												}else if(scale1 == scale2) {
													status = 0;
												}else {
													status = 1;
												}
											}else {
												status = -1;
											}
											
											break;
										}case LOW:{
											
											if(priority2 == Priority.LOW) {
												
												if(scale1 < scale2) {
													status = -1;
												}else if(scale1 == scale2) {
													status = 0;
												}else {
													status = 1;
												}
											}else {
												status = -1;
											}
											break;
										}case MEDIUM:{
											
											if(priority2 == Priority.LOW) {
												
												status = 1;
											}else if(priority2 == Priority.MEDIUM) {
												
												if(scale1 < scale2) {
													status = -1;
												}else if(scale1 == scale2) {
													status = 0;
												}else {
													status = 1;
												}
											}else {
												status = -1;
											}
											break;
										}default:{
											break;
										}
									}
								}
								
								break;
							}case SEVERITY:{
								
								
								Severity severity1 	= ((Scale)((ChangeEvent<EventModel>)e1).getChangedModel().getReactiveMetaData()).getSeverity();
								double scale1 		= Scale.Severity.getValueOf(severity1);
								Severity severity2 	= ((Scale)((ChangeEvent<EventModel>)e2).getChangedModel().getReactiveMetaData()).getSeverity();
								double scale2 		= Scale.Severity.getValueOf(severity1);
								
								if(severity1 != null && severity2 != null) {
								
									switch(severity1) {
										
										case CRITICAL:{
											
											if(severity2 == Severity.LOW || severity2 == Severity.MEDIUM || severity2 == Severity.HIGH) {
												status = 1;
											}else if(severity2 == Severity.CRITICAL) {
												
												if(scale1 < scale2) {
													status = -1;
												}else if(scale1 == scale2) {
													status = 0;
												}else {
													status = 1;
												}
											}else {
												status = -1;
											}
											
											break;
										}case HIGH:{
											
											if(severity2 == Severity.LOW || severity2 == Severity.MEDIUM) {
												
												status = 1;										
											}else if(severity2 == Severity.HIGH) {
												
												if(scale1 < scale2) {
													status = -1;
												}else if(scale1 == scale2) {
													status = 0;
												}else {
													status = 1;
												}
											}else {
												status = -1;
											}
											
											break;
										}case LOW:{
											
											if(severity2 == Severity.LOW) {
												
												if(scale1 < scale2) {
													status = -1;
												}else if(scale1 == scale2) {
													status = 0;
												}else {
													status = 1;
												}
											}else {
												status = -1;
											}
											break;
										}case MEDIUM:{
											
											if(severity2 == Severity.LOW) {
												
												status = 1;
											}else if(severity2 == Severity.MEDIUM) {
												
												if(scale1 < scale2) {
													status = -1;
												}else if(scale1 == scale2) {
													status = 0;
												}else {
													status = 1;
												}
											}else {
												status = -1;
											}
											break;
										}default:{
											break;
										}
									}
								}
								break;
							}default:{
								break;
							}
							
						}
					}
					
					break;
				}default:{
					break;
				}
				
			}
		}
		

		return status;
	}
	
	public String toString() {
		
		return "NotificationBufferQueue { "+queueType+" | "+queueStagingType+" | "+eventType+" | "+super.toString()+" } ";
	}




	
}

