package org.flowr.framework.core.event;

/**
 * Extends Event interface to set Target MetaData information as part of Event.
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface EventTarget extends Event<ReactiveMetaData>{

	public ReactiveMetaData getTarget();
	
	public void setTarget(ReactiveMetaData targetMetaData);
}
