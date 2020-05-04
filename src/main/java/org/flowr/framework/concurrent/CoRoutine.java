/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.concurrent;

public interface CoRoutine {

    void prePromise();
    
    void postPromise();
}
