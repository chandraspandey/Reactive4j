package org.flowr.framework.core.event;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.model.DataAttribute;
import org.flowr.framework.core.model.EventModel;


/**
 * Defines EventEntity as change persistable encapsulation event type of EventModel. Defines UUID as an event identity. 
 * As support to dynamic addition of attribute as a  DataAttribute model
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ChangeEventEntity implements ChangeEvent<EventModel>,Tagging{
	
	private UUID identity = UUID.randomUUID();
	private String subscriptionClientId;
	private String source;
	private String destination;	
	private EventModel changedModel;	
	private Timestamp eventTimestamp;
	private EventType eventType;
	
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

	@Override
	public int compareTo(Delayed o) {
		// TODO Auto-generated method stub
		return 0;
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
				"\n}";
	}
}
