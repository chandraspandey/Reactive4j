
/**
 * Defines Marker interface of listening capabilities by extending Observer
 * interface.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.flow;

import java.util.concurrent.Flow.Publisher;

import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter.AdapterFlowConfig;

public interface EventPublisher extends EventFlow, Publisher<Event<EventModel>>{
    
    AdapterFlowConfig getAdapterFlowConfig();

    void setEnabled(boolean isEnabled) ;

    boolean isEnabled();

    boolean isSubscribed();
    
}
