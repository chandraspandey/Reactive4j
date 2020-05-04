
/**
 * Extends MetaData for providing additional differentiation of EventOrigin where EventModel may be composite to
 * include both EventSource & EventTarget
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.event;

import org.flowr.framework.core.model.MetaData;

public interface ReactiveMetaData extends MetaData{

    public enum EventOrigin{
        SOURCE,
        TARGET
    }
    
    void setEventOrigin(EventOrigin eventOrigin);
    
    EventOrigin getEventOrigin();
    
    String getSubscriptionClientId();
    
    void setSubscriptionClientId(String clientId);
}
