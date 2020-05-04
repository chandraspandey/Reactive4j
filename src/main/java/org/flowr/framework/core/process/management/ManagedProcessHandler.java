
/**
 * Concrete implementation of Process Handler capabilities as a managed process.
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.process.management;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.Flow.Subscriber;

import org.flowr.framework.core.context.Context;
import org.flowr.framework.core.event.ChangeEvent;
import org.flowr.framework.core.event.ChangeEventEntity;
import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter.AdapterFlowConfig;

public class ManagedProcessHandler implements ProcessHandler{

    private boolean isEnabled                                       = true;
    private Subscriber<? super Event<EventModel>> subscriber;
    
    @Override
    public void subscribe(Subscriber<? super Event<EventModel>> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public AdapterFlowConfig getAdapterFlowConfig() {
        
        return new AdapterFlowConfig(
                this.getClass().getSimpleName(),
                FlowPublisherType.FLOW_PUBLISHER_PROCESS,
                FlowFunctionType.EVENT,
                FlowType.ENDPOINT
            );
    }
    
    @Override
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
    
    @Override
    public void publishProcessEvent(EventType eventType, Context context) {
        
        ChangeEvent<EventModel> changeEvent = new ChangeEventEntity();
        changeEvent.setEventTimestamp(new Timestamp(Instant.now().toEpochMilli()));
        
        EventModel eventModel = new EventModel();
        eventModel.setContext(context);

        changeEvent.setChangedModel(eventModel);
        changeEvent.setEventType(eventType);
        
        publishEvent(changeEvent);
    }
    

    @Override
    public void publishEvent(Event<EventModel> event) {
        subscriber.onNext(event);
    }

    @Override
    public boolean isSubscribed() {
        
        return (this.subscriber != null);
    }
}
