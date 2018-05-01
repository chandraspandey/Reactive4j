package org.flowr.framework.core.event;

import org.flowr.framework.core.model.MetaData;

/**
 * Extends MetaData for providing additional differentiation of EventOrigin where EventModel may be composite to
 * include both EventSource & EventTarget
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ReactiveMetaData extends MetaData{

	public enum EventOrigin{
		SOURCE,
		TARGET
	}
	
	public void setEventOrigin(EventOrigin eventOrigin);
	
	public EventOrigin getEventOrigin();
	
	public String getSubscriptionClientId();
	
	public void setSubscriptionClientId(String clientId);
}
