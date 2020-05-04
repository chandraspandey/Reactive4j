
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.process;

public interface Registry {

    enum RegistryType{
        LOCAL,
        CLUSTER
    }
    
    RegistryType getRegistryType();
    
    void setRegistryType(RegistryType registryType);
}
