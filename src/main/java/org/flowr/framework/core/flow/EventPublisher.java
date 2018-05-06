package org.flowr.framework.core.flow;

import java.util.concurrent.Flow.Publisher;

import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.model.EventModel;

/**
 * Defines Marker interface of listening capabilities by extending Observer
 * interface.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */


public interface EventPublisher extends EventFlow, Publisher<Event<EventModel>>{
	
	public void setFlowName(String flowName) ;
	
	public String getFlowName();	
	
	public void setFlowType(FlowType flowType);
	
	public FlowType getFlowType();
	
	public void setFlowFunctionType(FlowFunctionType flowFunctionType);
	
	public FlowFunctionType getFlowFunctionType();
	
	public void setFlowPublisherType(FlowPublisherType flowPublisherType);
	
	public FlowPublisherType getFlowPublisherType();

	public void setEnabled(boolean isEnabled) ;

	public boolean isEnabled();

	public boolean isSubscribed();
	
}
