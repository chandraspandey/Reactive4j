package org.flowr.framework.core.event;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Defines EventEntity as change persistable encapsulation event type of EventModel. Defines UUID as an event identity. 
 * As support to dynamic addition of attribute as a  DataAttribute model
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class EventEntity implements EventSource,EventTarget {
	
	private UUID identity = UUID.randomUUID();
	private Timestamp eventTimestamp;
	private ReactiveMetaData sourceMetaData;
	private ReactiveMetaData targetMetaData;
	private EventType eventType;

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
	public ReactiveMetaData getTarget() {
		return this.targetMetaData;
	}

	@Override
	public void setTarget(ReactiveMetaData targetMetaData) {
		this.targetMetaData = targetMetaData;
	}

	@Override
	public ReactiveMetaData getSource() {
		return this.sourceMetaData;
	}

	@Override
	public void setSource(ReactiveMetaData sourceMetaData) {
		this.sourceMetaData = sourceMetaData;
	}
	
	@Override
	public void setEventTimestamp(Timestamp eventTimestamp) {
		this.eventTimestamp=eventTimestamp;
	}

	@Override
	public Timestamp getEventTimestamp() {
 		return this.eventTimestamp;
	}
	
	
	public String toString(){
		
		return "EventEntity{"+
				"\n eventType : "+eventType+
				"\n eventTimestamp : "+eventTimestamp+
				"\n identity : "+identity+
				"\n sourceMetaData : "+sourceMetaData+
				"\n targetMetaData : "+targetMetaData+
				"\n}";
	}

	@Override
	public long getDelay(TimeUnit unit) {
				
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



}
