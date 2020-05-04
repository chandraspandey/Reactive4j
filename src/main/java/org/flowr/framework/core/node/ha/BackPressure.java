
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.ha;

import org.flowr.framework.core.exception.ConfigurationException;

public interface BackPressure {
    
    <V> V react(FailsafeCallable<V> callable) throws ConfigurationException;
    
    boolean isTimedOut();
}
