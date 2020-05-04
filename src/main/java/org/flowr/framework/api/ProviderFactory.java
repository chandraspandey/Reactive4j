
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */


package org.flowr.framework.api;

import org.flowr.framework.api.Provider.ProviderType;

public interface ProviderFactory {
    
    Provider instantiate(ProviderType providerType);
}
