
/**
 * Marker interface for Event. Event Type, mechanism & event details are provided by concrete implementation classes.
 * Defines event timestamp tagging functionality as core functionality. Inheriting interfaces provides domain
 * specific functionalities.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.event;

import java.sql.Timestamp;
import java.util.concurrent.Delayed;

public interface Event<E> extends Delayed{
    
    public enum EventType{
        CLIENT,
        SERVER,
        HEALTHCHECK,
        NETWORK
    }
    
    void setEventTimestamp(Timestamp eventTimestamp);
    
    Timestamp getEventTimestamp();
    
    void setEventType(EventType eventType);
    
    EventType getEventType();
        
    E get();
}
