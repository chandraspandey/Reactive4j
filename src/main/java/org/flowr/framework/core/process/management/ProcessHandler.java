package org.flowr.framework.core.process.management;

import org.flowr.framework.core.context.ProcessContext;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.flow.SingleEventPublisher;

/**
 * Defines Process Handler capabilities as a managed process.
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ProcessHandler extends SingleEventPublisher{

	public void publishProcessEvent(EventType eventType, ProcessContext processContext);
}
