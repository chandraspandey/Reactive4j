
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.flow;

import java.util.concurrent.Flow.Subscriber;

import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.model.EventModel;

public interface EventSubscriber extends EventFlow, Subscriber<Event<EventModel>>{

    void setEventSubscriberType(FlowSubscriberType flowSubscriberType);
    
    FlowSubscriberType getEventSubscriberType();
    
}
