
/**
 * Defines domain agnostic filtering mechanism. Extending interfaces provide 
 * domain specific instrumentation.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.security.access.filter;

import java.util.Collection;
import java.util.Optional;

import org.flowr.framework.core.context.Context;
import org.flowr.framework.core.exception.AccessSecurityException;
import org.flowr.framework.core.model.Model;

public interface Filter{

    public enum FilterRetentionType {
        PERSIST,
        NON_PERSISTENT,
        DISPOSE,
        PROPAGATED
    }
    
    /* RESTRICTED : Does not permits access
     * READ_ONLY  : Read access
     * DMZ        : Privilege Access required
     */
    public enum FilterType {
        RESTRICTED,
        READ_ONLY,
        DMZ
    }
    
    public enum FilterBy {
        IP_RANGE,
        SUB_NET,
        DNS,
        GATEWAY,
        PROXY,
        ROUTE
    }
    
    
    /**
     * Performs filter operation in a provided context
     * @param context
     * @return
     * @throws AccessSecurityException
     */
    Collection<Model> doFilter(Context context, Collection<Model> collection);
    
    
    /**
     * Performs filter operation in a provided context
     * @param context
     * @return
     * @throws AccessSecurityException
     */
    Optional<Model> doFilter(Context context, Model model);

        
    void setFilterRetentionType(FilterRetentionType filterRetentionType);
    
    FilterRetentionType getFilterRetentionType();
    
    void setFilterName(String filterName);
    
    String getFilterName();
    
    void setFilterType(FilterType filterType);
    
    FilterType getFilterType();
    
}
