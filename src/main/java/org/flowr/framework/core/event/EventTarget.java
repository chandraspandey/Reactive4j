
/**
 * Extends Event interface to set Target MetaData information as part of Event.
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.event;

public interface EventTarget extends Event<ReactiveMetaData>{

    ReactiveMetaData getTarget();
    
    void setTarget(ReactiveMetaData targetMetaData);
}
