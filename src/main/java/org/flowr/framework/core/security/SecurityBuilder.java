
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.security;

import org.apache.log4j.Logger;
import org.flowr.framework.api.Builder;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.security.Identity.IdentityConfig;
import org.flowr.framework.core.security.Identity.IdentityType;
import org.flowr.framework.core.security.access.Entitlements;
import org.flowr.framework.core.security.identity.IdentityData;

public interface SecurityBuilder extends Builder{
    
    public class IdentityBuilder implements Builder{
        
        private String identityName;       
        private IdentityType identityType;
        private Entitlements entitlements;
        private IdentityData identityData;
        
        public IdentityBuilder withIdentityNameAs(String identityName) {
            
            this.identityName = identityName;
            return this;
        }
 
       public IdentityBuilder withIdentityTypeAs(IdentityType identityType) {
            
            this.identityType = identityType;
            return this;
        }
       
       public IdentityBuilder withEntitlementsAs(Entitlements entitlements) {
           
           this.entitlements = entitlements;
           return this;
       }
       
       public IdentityBuilder withIdentityDataAs(IdentityData identityData) {
           
           this.identityData = identityData;
           return this;
       }
       
        public IdentityConfig build() throws ConfigurationException {
            
            IdentityConfig identityConfig;
            
            if(identityName != null && identityType != null && entitlements != null && identityData != null ) {

                identityConfig = new IdentityConfig(identityName, identityType, entitlements, identityData);

            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create IdentityConfig with inputs as : ");
                sb.append("\n identityName : "+identityName);
                sb.append("\n identityType : "+identityType);
                sb.append("\n entitlements : "+entitlements);
                sb.append("\n identityData : "+identityData);
                
                Logger.getRootLogger().error(sb.toString());
                
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for IdentityBuilder. "
                );
            }
            
            return identityConfig;
        }
    }
}
