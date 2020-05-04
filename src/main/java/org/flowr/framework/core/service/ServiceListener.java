
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service;

import org.flowr.framework.core.flow.EventPublisher;


public interface ServiceListener {

    void addServiceListener(EventPublisher serviceListener);
    
    void removeServiceListener(EventPublisher serviceListener);
    
    EventPublisher getServiceListener();
}
