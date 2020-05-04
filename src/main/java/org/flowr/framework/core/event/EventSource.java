
/**
 *  Extends Event interface to set Source MetaData information as part of Event
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.event;

public interface EventSource extends Event<ReactiveMetaData>{

    ReactiveMetaData getSource();
    
    void setSource(ReactiveMetaData sourceMetaData);
}
