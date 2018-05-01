package org.flowr.framework.core.event;

/**
 *  Extends Event interface to set Source MetaData information as part of Event
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface EventSource extends Event<ReactiveMetaData>{

	public ReactiveMetaData getSource();
	
	public void setSource(ReactiveMetaData sourceMetaData);
}
