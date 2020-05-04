
/**
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.security;

import java.util.Calendar;

public interface Lease {

    Calendar leaseStartTime();
    
    Calendar leaseEndTime();
}
