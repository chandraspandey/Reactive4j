
/**
 * 
 * use private long timeout = 11000 for timeout scenario testing
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

package org.framework.fixture.notification;

import java.util.concurrent.Flow.Subscription;

import org.apache.log4j.Logger;
import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.flow.EventSubscriber;
import org.flowr.framework.core.model.EventModel;

public class BusinessServerAdministrator implements EventSubscriber{

    private FlowSubscriberType flowSubscriberType = FlowSubscriberType.FLOW_SUBSCRIBER_CLIENT;
    
    @Override
    public void onComplete() {
        Logger.getRootLogger().info("BusinessServerAdministrator : Subscription Complete ");

    }

    @Override
    public void onError(Throwable throwable) {
        Logger.getRootLogger().info("BusinessServerAdministrator : Error : "+throwable.getMessage());
    }

    @Override
    public void onNext(Event<EventModel> event) {
        Logger.getRootLogger().info("BusinessServerAdministrator : Recieved Event : "+event);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        Logger.getRootLogger().info("BusinessServerAdministrator : Subscription : "+subscription);
    }

    @Override
    public void setEventSubscriberType(FlowSubscriberType flowSubscriberType) {
        this.flowSubscriberType = flowSubscriberType;
    }

    @Override
    public FlowSubscriberType getEventSubscriberType() {
        return this.flowSubscriberType;
    }

}
