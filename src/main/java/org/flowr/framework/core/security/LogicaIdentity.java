
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.security;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import org.flowr.framework.core.security.access.Entitlements;
import org.flowr.framework.core.security.identity.IdentityData;


public class LogicaIdentity implements Identity{

    private String identityName;       
    private Timestamp registrationTime;
    private Timestamp deRegistrationTime;
    private IdentityType identityType;
    private Entitlements entitlements;
    private IdentityData identityData;
     
    public void init(IdentityConfig identityConfig) {
        
        this.identityName       = identityConfig.getIdentityName();
        this.registrationTime   = new Timestamp(Instant.now().toEpochMilli()); 
        this.identityType       = identityConfig.getIdentityType();
        this.entitlements       = identityConfig.getEntitlements();
        this.identityData       = identityConfig.getIdentityData();
    }

    public void setClientType(IdentityType identityType) {
        
        this.identityType = identityType;
    }
    
    public IdentityType getIdentityType(){
        return this.identityType;
    }
    
    public String getIdentityName() {
        return identityName;
    }
 
    public Timestamp getRegistrationTime() {
        return registrationTime;
    }
 
    public Timestamp getDeRegistrationTime() {
        return deRegistrationTime;
    }
    public void setDeRegistrationTime(Timestamp deRegistrationTime) {
        this.deRegistrationTime = deRegistrationTime;
    }
    
    @Override
    public int hashCode() {
       
        return UUID.randomUUID().hashCode();
    }
    
    public boolean equals(Object other){
        
        boolean isEqual = false;
        
        if(
                other != null && other.getClass() == this.getClass() &&
                this.identityName.equals( ((LogicaIdentity)other).getIdentityName()) &&
                this.identityType == ((LogicaIdentity)other).identityType
        ){
            isEqual = true;
        }
        
        return isEqual;
    }
    
    public void setIdentityData(IdentityData identityData) {
        
        this.identityData = identityData;
    }
    
    @Override
    public IdentityData getIdentityData() {
        return this.identityData;
    }
    
    public void setEntitlements(Entitlements entitlements) {
        
        this.entitlements = entitlements;
    }

    @Override
    public Entitlements getEntitlements() {
        return this.entitlements;
    }
    
    public String toString(){
        
        return "\n LogicalIdentity{"+
                " | identityType : "+identityType+  
                " | identityName : "+identityName+
                " | entitlements : "+entitlements+
                " | registrationTime : "+registrationTime+                      
                " | deRegistrationTime : "+deRegistrationTime+  
                "\n }\n";
    }  
    
}


