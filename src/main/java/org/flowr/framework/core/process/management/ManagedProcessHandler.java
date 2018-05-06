package org.flowr.framework.core.process.management;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.concurrent.Flow.Subscriber;

import org.flowr.framework.core.context.ProcessContext;
import org.flowr.framework.core.event.ChangeEvent;
import org.flowr.framework.core.event.ChangeEventEntity;
import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.model.EventModel;

/**
 * Concrete implementation of Process Handler capabilities as a managed process.
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ManagedProcessHandler implements ProcessHandler{

	private boolean isEnabled										= true;
	private String flowName 										= ManagedProcessHandler.class.getSimpleName();
	private FlowPublisherType flowPublisherType						= FlowPublisherType.FLOW_PUBLISHER_PROCESS;
	private FlowFunctionType flowFunctionType 						= FlowFunctionType.EVENT;  
	private FlowType flowType										= FlowType.ENDPOINT;
	private Subscriber<? super Event<EventModel>> subscriber = null;
	
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
	public void publishProcessEvent(EventType eventType, ProcessContext processContext) {
		
		ChangeEvent<EventModel> changeEvent = new ChangeEventEntity();
		changeEvent.setEventTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		
		EventModel eventModel = new EventModel();
		eventModel.setContext(processContext);

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
