
/**
 * Concrete implementation of Node process Handler capabilities as a managed process.
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.service.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.Flow.Subscriber;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.context.Context;
import org.flowr.framework.core.event.ChangeEvent;
import org.flowr.framework.core.event.ChangeEventEntity;
import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter.AdapterFlowConfig;
import org.flowr.framework.core.process.management.NodeProcessHandler;

public class DefaultNodeProcessHandler implements NodeProcessHandler{

    private boolean isEnabled                                       = true;

    private Subscriber<? super Event<EventModel>> subscriber;
    
    public Process lookupProcess(String executable) throws ConfigurationException {
        
        Process process = null;
        
        try {
            process = Runtime.getRuntime().exec(executable);

        } catch (IOException e) {
            Logger.getRootLogger().error(e);
            throw new ConfigurationException(ErrorMap.ERR_CONFIG,"Unable to lookup executable process.");
        }       
        return process;
    }
    
    public InputStream processIn(String executable) throws ConfigurationException {
        
        InputStream inputStream = null;

        Process process = lookupProcess(executable);
        
        if(process != null && process.toHandle().isAlive()) {
            inputStream = process.getInputStream();
        }
        
        return inputStream;
    }
    
    public OutputStream processOut(String executable) throws ConfigurationException {
        
        OutputStream outputStream = null;

        Process process = lookupProcess(executable);
        
        if(process != null && process.toHandle().isAlive()) {
            outputStream = process.getOutputStream();           
        }
        
        return outputStream;
    }
    
    public InputStream processError(String executable) throws ConfigurationException {
        
        InputStream errStream = null;
        
        Process process = lookupProcess(executable);
        
        if(process != null && process.toHandle().isAlive()) {
            errStream = process.getErrorStream();       
        }
    
        return errStream;
    }
    
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
