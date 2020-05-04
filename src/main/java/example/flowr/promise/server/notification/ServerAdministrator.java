
/**
 * 
 * use private long timeout = 11000 for timeout scenario testing
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

package example.flowr.promise.server.notification;

import java.util.concurrent.Flow.Subscription;

import org.apache.log4j.Logger;
import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.flow.EventSubscriber;
import org.flowr.framework.core.model.EventModel;

public class ServerAdministrator implements EventSubscriber{

    private FlowSubscriberType flowSubscriberType = FlowSubscriberType.FLOW_SUBSCRIBER_CLIENT;
    
    @Override
    public void onComplete() {
        Logger.getRootLogger().info("ServerAdministrator : Subscription Complete ");

    }

    @Override
    public void onError(Throwable throwable) {
        Logger.getRootLogger().info("ServerAdministrator : Error : "+throwable.getMessage());
    }

    @Override
    public void onNext(Event<EventModel> event) {
        Logger.getRootLogger().info("ServerAdministrator : Recieved Event : "+event);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        Logger.getRootLogger().info("ServerAdministrator : Subscription : "+subscription);
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
