
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.service.route;

import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.security.ClientIdentity;
import org.flowr.framework.core.service.ServiceResponse;

public class ServiceRoute implements Model {
    
    private ClientIdentity  clientIdentity;
    private Class<? extends ServiceResponse> value;
    
    public void set(ClientIdentity  clientIdentity,Class<? extends ServiceResponse> value){
        this.clientIdentity = clientIdentity;
        this.value = value;
    }
    
    public ClientIdentity getKey() {
        return clientIdentity;
    }
    public void setKey(ClientIdentity  clientIdentity) {
        this.clientIdentity = clientIdentity;
    }
    public Class<? extends ServiceResponse> getValue() {
        return value;
    }
    public void setValue(Class<? extends ServiceResponse> value) {
        this.value = value;
    }
    
    @Override
    public int hashCode() {
       
        return clientIdentity.hashCode();
    }
    
    /**
     * Provides concrete implementation  for equals.
     * @param other
     * @return
     */
    public boolean equals(Object other){
        
        boolean isEqual = false;        
        
        if( other != null && other.getClass() == this.getClass() &&
            this.clientIdentity.equals(
            ((ServiceRoute)other).getKey()) &&
            this.getValue().getClass().isAssignableFrom(((ServiceRoute)other).getValue().getClass())
        ){
            
            isEqual = true;         
        }
        
        return isEqual;
    }
    
    public boolean isKeyPresent(ClientIdentity  clientIdentity){
        
        boolean isPresent = false;
        
        if(this.clientIdentity.equals(clientIdentity)){
            
            isPresent =  true;
        }
        return isPresent;
    }
    
    public boolean isValuePresent(Class<? extends ServiceResponse> v){
        
        boolean isPresent = false;
        
        if(value.getClass().isAssignableFrom(v.getClass())){
            
            isPresent =  true;
        }
        
        return isPresent;
    }
    
    
    public String toString(){
        
        return "Route{"+
            "\n ClientIdentity : "+clientIdentity+
            "\n V : "+value+
            "\n}\n";
    }
}
