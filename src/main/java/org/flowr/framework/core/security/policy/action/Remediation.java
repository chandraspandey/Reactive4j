
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.policy.action;

import org.flowr.framework.action.Algorithm;

public interface Remediation {

    public enum ObfuscationType{
        NETWORK,
        COMPUTE,
        STORAGE,
        IDENTITY        
    }
    
    String obfuscate(String input, Algorithm algorithm);
}
