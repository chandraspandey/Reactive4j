package org.flowr.framework.core.event;


/**
 * Marker interface for Event. Event Type, mechanism & event details are provided by concrete implementation classes.
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ChangeEvent<T> extends Event<T>{
	
	public String getSubscriptionClientId();
	public void setSubscriptionClientId(String subscriptionClientId);
	public String getSource();
	public void setSource(String source) ;
	public String getDestination();
	public void setDestination(String destination);
	public T getChangedModel() ;
	public void setChangedModel(T changedModel);
}
