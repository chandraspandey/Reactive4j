
/**
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.access.filter;

public interface AccessSecurityFilter extends Filter{
    
    void setFilterName(String filterName);
    
    String getFilterName();
    
    
}
