
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.event.pipeline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Flow.Subscription;

import org.flowr.framework.core.config.Configuration;
import org.flowr.framework.core.config.PipelineConfiguration;
import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.flow.EventSubscriber;
import org.flowr.framework.core.model.EventModel;

public class EventPipeline extends DelayQueue<Event<EventModel>> implements Pipeline, EventSubscriber{

    private FlowSubscriberType flowSubscriberType       = FlowSubscriberType.FLOW_SUBSCRIBER_CLIENT;
    private PipelineType pipelineType                   = PipelineType.TRANSFER;
    private EventType eventType; 
    private PipelineFunctionType pipelineFunctionType;
    private String pipelineName;
    private Subscription subscription;
    private PipelineConfiguration pipelineConfiguration = Configuration.getDefaultPipelineConfiguration();

    @Override
    public void setPipelineType(PipelineType pipelineType) {
        this.pipelineType = pipelineType;
    }

    @Override
    public PipelineType getPipelineType() {
        return this.pipelineType;
    }
    
    @Override
    public String getPipelineName() {
        return pipelineName;
    }

    @Override
    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }
    

    @Override
    public void onComplete() {
        this.subscription.cancel();
    }

    @Override
    public void onError(Throwable throwable) {
        this.subscription.cancel();
    }
    
    

    @Override
    public void onSubscribe(Subscription subscription) {
        
        this.subscription = subscription;
    }
    
    public Collection<Event<EventModel>> getBatch() {
        
        Collection<Event<EventModel>> coll = new ArrayList<>();
        
        synchronized(this){
            this.drainTo(coll, this.pipelineConfiguration.getBatchSize());
        }
            
                
        return coll;
    }
    
    public PipelineConfiguration getPipelineConfiguration() {
        return pipelineConfiguration;
    }

    public void setPipelineConfiguration(PipelineConfiguration pipelineConfiguration) {
        this.pipelineConfiguration = pipelineConfiguration;
    }
    
    @Override
    public void setEventSubscriberType(FlowSubscriberType flowSubscriberType) {
        this.flowSubscriberType = flowSubscriberType;
    }

    @Override
    public FlowSubscriberType getEventSubscriberType() {
        return this.flowSubscriberType;
    }

    @Override
    public void setPipelineFunctionType(PipelineFunctionType pipelineFunctionType) {
        this.pipelineFunctionType = pipelineFunctionType;
    }

    @Override
    public PipelineFunctionType getPipelineFunctionType() {
        return this.pipelineFunctionType;
    }
    
    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
    
    @Override
    public void onNext(Event<EventModel> event) {
        this.add(event);
    }

    @Override
    public String toString() {
        
        return "EventPipeline { flowSubscriberType : "+flowSubscriberType+" | "+pipelineName+" | "+pipelineType+" | "+
                eventType+" | "+pipelineFunctionType+" | "+super.toString()+" | "+pipelineConfiguration+" } ";
    }


}
