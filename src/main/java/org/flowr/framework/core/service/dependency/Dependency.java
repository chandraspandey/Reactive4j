
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.dependency;

public interface Dependency {

    public enum DependencyType{
        MANDATORY,
        OPTIONAL
    }
    
    public enum DependencyStatus{
        SATISFIED,
        UNSATISFIED
    }
    
    String getDependencyName();
    
    DependencyType getDependencyType();
    
}
