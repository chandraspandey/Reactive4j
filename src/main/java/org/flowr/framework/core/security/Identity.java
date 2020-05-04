
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.security;

import org.flowr.framework.core.security.access.Entitlements;
import org.flowr.framework.core.security.identity.IdentityData;


public interface Identity {

    public enum IdentityType{
        PHYSICAL,
        LOGICAL
    }   
    
    IdentityData getIdentityData();
    
    Entitlements getEntitlements();
    
    public class IdentityConfig{

        private String identityName;       
        private IdentityType identityType;
        private Entitlements entitlements;
        private IdentityData identityData;
        
        public IdentityConfig(String identityName, IdentityType identityType, Entitlements entitlements, 
            IdentityData identityData) {
            
            this.identityName = identityName;
            this.identityType = identityType;
            this.entitlements = entitlements;
            this.identityData = identityData;
        }
        
        public String getIdentityName() {
            return identityName;
        }

        public IdentityType getIdentityType() {
            return identityType;
        }

        public Entitlements getEntitlements() {
            return entitlements;
        }

        public IdentityData getIdentityData() {
            return identityData;
        }  
        
        public String toString(){
            
            return "IdentityConfig{"+
                    " | identityType : "+identityType+  
                    " | identityName : "+identityName+
                    " | entitlements : "+entitlements+
                    "}\n";
        }
        
    }
}
