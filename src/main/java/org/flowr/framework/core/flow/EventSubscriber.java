package org.flowr.framework.core.flow;

import java.util.concurrent.Flow.Subscriber;

import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.model.EventModel;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface EventSubscriber extends EventFlow, Subscriber<Event<EventModel>>{

	public void setEventSubscriberType(FlowSubscriberType flowSubscriberType);
	
	public FlowSubscriberType getEventSubscriberType();
	
}
