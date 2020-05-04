
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.security;

import java.sql.Timestamp;
import java.time.Instant;

import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.security.access.Entitlements;
import org.flowr.framework.core.security.identity.IdentityData;


public final class ClientIdentity implements Identity{

    private String clientApplicationName;       
    private Timestamp registrationTime;
    private Timestamp deRegistrationTime;
    private IdentityType identityType;
    private Entitlements entitlements;
    private IdentityData identityData;
    
    public ClientIdentity(String clientApplicationName,IdentityType identityType) throws ConfigurationException{
        
        if(clientApplicationName != null && identityType != null){
            
            this.clientApplicationName  = clientApplicationName;
            this.identityType           = identityType;
            this.registrationTime       = new Timestamp(Instant.now().toEpochMilli());
        }else{
            
            throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,
                    "Invalid parameters for ClientIdentity creation. clientApplicationName : "+clientApplicationName+
                    " | "+ "identityType : "+identityType);

        }
        
    }
    
    public IdentityType getIdentityType(){
        return this.identityType;
    }
    
    public String getClientApplicationName() {
        return clientApplicationName;
    }
    public void setClientApplicationName(String clientApplicationName) {
        this.clientApplicationName = clientApplicationName;
    }
    public Timestamp getRegistrationTime() {
        return registrationTime;
    }
    public void setRegistrationTime(Timestamp registrationTime) {
        this.registrationTime = registrationTime;
    }
    public Timestamp getDeRegistrationTime() {
        return deRegistrationTime;
    }
    public void setDeRegistrationTime(Timestamp deRegistrationTime) {
        this.deRegistrationTime = deRegistrationTime;
    }
    
    @Override
    public int hashCode() {
       
        return clientApplicationName.hashCode();
    }
    
    public boolean equals(Object other){
        
        boolean isEqual = false;
        
        if(
                other instanceof ClientIdentity &&
                this.clientApplicationName.equals( ((ClientIdentity)other).getClientApplicationName()) &&
                this.identityType == ((ClientIdentity)other).getIdentityType()
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
        
        return "ClientIdentity{"+
                " | identityType : "+identityType+  
                " | clientApplicationName : "+clientApplicationName+
                " | registrationTime : "+registrationTime+                      
                " | deRegistrationTime : "+deRegistrationTime+  
                "}\n";
    }
    
}
