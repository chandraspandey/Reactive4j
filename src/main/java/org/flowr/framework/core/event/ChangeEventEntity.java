package org.flowr.framework.core.event;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.model.DataAttribute;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.notification.NotificationQueue.QueueStagingType;
import org.flowr.framework.core.notification.NotificationQueue.QueueType;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.Scale.Priority;
import org.flowr.framework.core.promise.Scale.PriorityScale;
import org.flowr.framework.core.promise.Scale.Severity;
import org.flowr.framework.core.promise.Scale.SeverityScale;


/**
 * Defines EventEntity as change persistable encapsulation event type of EventModel. Defines UUID as an event identity. 
 * As support to dynamic addition of attribute as a  DataAttribute model
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ChangeEventEntity implements ChangeEvent<EventModel>,Tagging{
	
	private UUID identity = UUID.randomUUID();
	private String subscriptionClientId;
	private String source;
	private String destination;	
	private EventModel changedModel;	
	private Timestamp eventTimestamp;
	private EventType eventType;
	
	// To be set dynamically by Dispatcher for prioritizing events in queue
	private QueueType queueType					= QueueType.STAGING;
	private QueueStagingType queueStagingType	= QueueStagingType.SEVERITY;
	
	public String getSubscriptionClientId() {
		return subscriptionClientId;
	}
	public void setSubscriptionClientId(String subscriptionClientId) {
		this.subscriptionClientId = subscriptionClientId;
	}

	@Override
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	@Override
	public EventType getEventType() {
		return this.eventType;
	}
	
	public UUID getIdentity() {			
		return identity;
	}
	
	@Override
	public String getSource() {
		return this.source;
	}

	@Override
	public void setSource(String source) {
		this.source=source;
	}

	@Override
	public String getDestination() {
		return this.destination;
	}

	@Override
	public void setDestination(String destination) {
		this.destination=destination;
	}

	@Override
	public EventModel getChangedModel() {
		return this.changedModel;
	}

	@Override
	public void setChangedModel(EventModel changedModel) {
		this.changedModel=changedModel;
	}

	@Override
	public void addAttribute(DataAttribute dataAttribute) {
		changedModel.addDataAttribute(dataAttribute);		
	}
	
	@Override
	public void setEventTimestamp(Timestamp eventTimestamp) {
		this.eventTimestamp=eventTimestamp;
	}

	@Override
	public Timestamp getEventTimestamp() {
 		return this.eventTimestamp;
	}
	
	@Override
	public long getDelay(TimeUnit unit) {
				
		
		return 0;
	}
	
	public long getNotificationDelay(TimeUnit unit) {
		
		long  delay = System.currentTimeMillis() - eventTimestamp.getTime();
		
		switch(unit){
		
			case SECONDS:{
				delay = delay/1000;
				break;
			}case DAYS:{
				delay = delay/(1000*60*60*24);
				break;
			}case HOURS:{
				delay = delay/(1000*60*60);
				break;
			}case MILLISECONDS:{
				break;
			}case MINUTES:{
				delay = delay/(1000*60);
				break;
			}default:{
				break;
			}
		}
		return delay;
	}
	
	//@Override
	public void setQueueType(QueueType queueType) {
		this.queueType = queueType;
	}

	//@Override
	public QueueType getQueueType() {

		return this.queueType;
	}
	
	//@Override
	public void setQueueStagingType(QueueStagingType queueStagingType) {
		this.queueStagingType = queueStagingType;
	}
	
	//@Override
	public QueueStagingType getQueueStagingType() {
		
		return this.queueStagingType;
	}
	
	@Override
	public int compareTo(Delayed o ) {

		ChangeEventEntity other = (ChangeEventEntity) o;
		
		int status = -1;
		
		if(queueType != null) {
		
			switch(queueType) {
				
				case FIFO:{
					
					if(this.getEventTimestamp() != null && other.getEventTimestamp() != null) {
						
						if(this.getEventTimestamp().before(other.getEventTimestamp())) {
							
							status = -1;
						}else if(this.getEventTimestamp().equals(other.getEventTimestamp())) {
							status = 0;
						}else {
							status = 1;
						}						
					}
					
					break;
				}case LIFO:{
					
					if(this.getEventTimestamp() != null && other.getEventTimestamp() != null) {
						
						if(this.getEventTimestamp().before(other.getEventTimestamp())) {
							
							status = 1;
						}else if(this.getEventTimestamp().equals(other.getEventTimestamp())) {
							status = 0;
						}else {
							status = -1;
						}						
					}
					break;
				}case STAGING:{
					
					if(
							queueStagingType != null && 
							(this.getChangedModel().getReactiveMetaData() != null) &&
							(this.getChangedModel().getReactiveMetaData() instanceof Scale) &&
							(other.getChangedModel().getReactiveMetaData() != null) &&
							(other.getChangedModel().getReactiveMetaData() instanceof Scale) 
					) {
						
						switch(queueStagingType) {
							
							case PRIORITY:{
													
								PriorityScale priorityScale1 = ((Scale)this.getChangedModel().getReactiveMetaData()).getPriorityScale();
								PriorityScale priorityScale2 = ((Scale)other.getChangedModel().getReactiveMetaData()).getPriorityScale();

								if(priorityScale1 != null && priorityScale2 != null) {
									
									Priority priority1 	= priorityScale1.getPriority();
									double scale1 		= priorityScale1.getValueOf(priority1);
									
									Priority priority2 	= priorityScale2.getPriority();
									double scale2 		= priorityScale2.getValueOf(priority2);
									
									//System.out.println(" priority1 "+priorityScale1+" <> priority2 "+priorityScale2);
									
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
								}
								
								break;
							}case SEVERITY:{
								
								SeverityScale severityScale1 = ((Scale)this.getChangedModel().getReactiveMetaData()).getSeverityScale();
								SeverityScale severityScale2 = ((Scale)other.getChangedModel().getReactiveMetaData()).getSeverityScale();
								
								if(severityScale1 != null && severityScale2 != null) {
									
									Severity severity1 			= severityScale1.getSeverity();
									double scale1 				= severityScale1.getValueOf(severity1);
									Severity severity2 	= severityScale2.getSeverity();
									double scale2 		= severityScale2.getValueOf(severity2);
									
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
								}
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
	

	public String toString(){
		
		return "ChangeEventEntity{"+
				" | subscriptionClientId : "+subscriptionClientId+
				" | eventType : "+eventType+
				" | eventTimestamp : "+eventTimestamp+
				" | identity : "+identity+
				" | source : "+source+
				" | destination : "+destination+
				" | changedModel : "+changedModel+
				" | queueType : "+queueType+
				" | queueStagingType : "+queueStagingType+
				"\n}";
	}
}
