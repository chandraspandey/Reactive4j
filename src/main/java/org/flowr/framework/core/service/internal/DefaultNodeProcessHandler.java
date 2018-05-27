package org.flowr.framework.core.service.internal;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.concurrent.Flow.Subscriber;

import org.flowr.framework.core.context.Context;
import org.flowr.framework.core.event.ChangeEvent;
import org.flowr.framework.core.event.ChangeEventEntity;
import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.process.management.NodeProcessHandler;

/**
 * Concrete implementation of Node process Handler capabilities as a managed process.
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class DefaultNodeProcessHandler implements NodeProcessHandler{

	private boolean isEnabled										= true;
	private String flowName 										= DefaultNodeProcessHandler.class.getSimpleName();
	private FlowPublisherType flowPublisherType						= FlowPublisherType.FLOW_PUBLISHER_PROCESS;
	private FlowFunctionType flowFunctionType 						= FlowFunctionType.EVENT;  
	private FlowType flowType										= FlowType.ENDPOINT;
	private Subscriber<? super Event<EventModel>> subscriber 		= null;
	
	public Process lookupProcess(String executable) throws ConfigurationException {
		
		Process process = null;
		
		try {
			process = Runtime.getRuntime().exec(executable);

		} catch (IOException e) {
			e.printStackTrace();
			throw new ConfigurationException(
					ERR_CONFIG,
					MSG_CONFIG, 
					"Unable to lookup executable process.");
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
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	@Override
	public String getFlowName() {
		return this.flowName;
	}

	@Override
	public void setFlowType(FlowType flowType) {
		this.flowType = flowType;
	}

	@Override
	public FlowType getFlowType() {
		return this.flowType;
	}

	@Override
	public void setFlowFunctionType(FlowFunctionType flowFunctionType) {
		this.flowFunctionType = flowFunctionType;
	}

	@Override
	public FlowFunctionType getFlowFunctionType() {
		return this.flowFunctionType;
	}

	@Override
	public void setFlowPublisherType(FlowPublisherType flowPublisherType) {
		this.flowPublisherType = flowPublisherType;
	}

	@Override
	public FlowPublisherType getFlowPublisherType() {
		return this.flowPublisherType;
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
		changeEvent.setEventTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		
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
