
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.model;

import org.flowr.framework.api.Provider;

public interface DataSourceProvider extends Provider{
    
    public enum PeristenceType{
        TUPLE,
        TUPLE_LIST
    }
}
