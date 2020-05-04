
/**
 * Defines extension for single event publishing.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.flow;

import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.model.EventModel;

public interface SingleEventPublisher extends EventPublisher{
            
    void publishEvent(Event<EventModel> event);     
}
