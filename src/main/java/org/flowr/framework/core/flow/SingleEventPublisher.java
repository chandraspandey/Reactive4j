package org.flowr.framework.core.flow;

import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.model.EventModel;

/**
 * Defines extension for single event publishing.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */


public interface SingleEventPublisher extends EventPublisher{
			
	public void publishEvent(Event<EventModel> event); 	
}
