
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.dependency;

import org.flowr.framework.core.service.dependency.Dependency.DependencyStatus;

public class DependencyItem implements Comparable<DependencyItem>, DependencyLoop{

    private Integer order;
    private Dependency dependency;
    
    public DependencyItem(Integer order,Dependency dependency){
        
        this.order      = order;
        this.dependency = dependency;   
    }
    
    
     @Override
     public int hashCode() {
         
         return order.intValue();
     }
     
    @Override
    public boolean equals(Object dependencyItem){
        
        boolean isEqual = false;    
        
        if( 
                dependencyItem != null &&
                dependencyItem.getClass() == this.getClass() &&
                this.order.intValue() == ((DependencyItem)dependencyItem).getOrder().intValue()
        ) { 
            isEqual = true;
        }
        
        return isEqual;
    }

    @Override
    public int compareTo(DependencyItem other) {
        
        int status = -1;
        
        if(this.order.intValue() == other.getOrder().intValue()){
            status = 0;
        }
        
        return status;
    }
    
    public Dependency getDependency(){
        return dependency;
    }

    public Integer getOrder() {
        return order;
    }
    
    @Override
    public DependencyStatus verify() {
 
        DependencyStatus status = DependencyStatus.UNSATISFIED;
        
        if( 
                order > 0 || dependency != null                
        ) {
            status = DependencyStatus.SATISFIED;
        }        
        return status;
    }
    
    public String toString(){
        
        return "\n | "+order+" | "+dependency.getDependencyType()+" | "+dependency.getDependencyName()+" |";
    }



}
