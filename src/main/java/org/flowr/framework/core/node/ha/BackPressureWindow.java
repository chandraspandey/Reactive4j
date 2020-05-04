
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.ha;

public interface BackPressureWindow {

    int getRetryCount();
    
    long getRetryInterval();

    long getTimeout();


}
