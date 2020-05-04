
/**
 * List implementation of filtering chain, based on the scope provided 
 * AnalyticFilter progressively filters as part of processing and the filtered 
 * data is finally returned as DataPointSet.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.access.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.flowr.framework.core.context.Context.AccessContext;
import org.flowr.framework.core.model.Model;

public class FilterChain {

    private Collection<Filter> filterList = new ArrayList<>();
    
    
    public void addFilter(Filter filter) {
        filterList.add(filter);
    }
    
    public Collection<Model> doFilter(AccessContext accessContext, Iterable<Model> collection){
        
        Collection<Model> filterCollection = new ArrayList<>();
        
        collection.forEach(
                
            c -> 
                
                filterList.forEach(
                        
                    (Filter f) -> {
                        
                        Optional<Model> modelOption = f.doFilter(accessContext,c );
                        
                        if(modelOption.isPresent() ) {
                            
                            filterCollection.add(modelOption.get());
                        }                       
                    }
                    
                )
            
        );
        
        
        return filterCollection;
    }
}
