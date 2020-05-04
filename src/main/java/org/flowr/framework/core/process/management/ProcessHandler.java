
/**
 * Defines Process Handler capabilities as a managed process.
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.process.management;

import org.flowr.framework.core.context.Context;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.flow.SingleEventPublisher;

public interface ProcessHandler extends SingleEventPublisher{

    void publishProcessEvent(EventType eventType,Context context);
}
