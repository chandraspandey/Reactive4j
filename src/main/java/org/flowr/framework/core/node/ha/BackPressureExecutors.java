
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.ha;

public final class BackPressureExecutors {

    private BackPressureExecutors() {
        
    }
    
    public static BackPressureExecutorService newCachedBackPressureThreadPool(){
        
        return new BackPressureExecutorService();
    }

}
