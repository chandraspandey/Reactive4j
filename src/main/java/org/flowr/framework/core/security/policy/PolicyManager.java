
/**
 * Defines the behavior of PolicyManager which can be used Policy enforcement.
 * Domain specific interfaces should extend the behavior as applicable to the
 * domain specific use cases.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.security.policy;

import org.flowr.framework.core.model.PersistenceProvider;
import org.flowr.framework.core.security.policy.Policy.ViolationPolicy;

public interface PolicyManager {
    
    PersistenceProvider getSecurePersistence();
    
    void setSecurePersistence(PersistenceProvider securePersistence);
    
    void setViolationPolicy(ViolationPolicy violationPolicy);
    
    ViolationPolicy getViolationPolicy();
        
}
