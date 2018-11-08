package org.flowr.framework.core.event;

import java.sql.Timestamp;
import java.util.concurrent.Delayed;

/**
 * Marker interface for Event. Event Type, mechanism & event details are provided by concrete implementation classes.
 * Defines event timestamp tagging functionality as core functionality. Inheriting interfaces provides domain
 * specific functionalities.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Event<E> extends Delayed{
	
	public enum EventType{
		CLIENT,
		SERVER,
		HEALTHCHECK,
		NETWORK
	}
	
	public void setEventTimestamp(Timestamp eventTimestamp);
	
	public Timestamp getEventTimestamp();
	
	public void setEventType(EventType eventType);
	
	public EventType getEventType();
	
}
