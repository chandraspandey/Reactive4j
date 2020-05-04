
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.api;

import java.util.AbstractMap.SimpleEntry;

import org.flowr.framework.core.constants.Constant.FrameworkConstants;

public interface Provider extends Builder{

    
    public enum ProviderType{
        PERSISTENCE(FrameworkConstants.FRAMEWORK_CONFIG_PERSISTENCE),
        DATASOURCE(FrameworkConstants.FRAMEWORK_CONFIG_DATASOURCE),
        SERVICE(FrameworkConstants.FRAMEWORK_CONFIG_SERVICE),
        SECURITY(FrameworkConstants.FRAMEWORK_CONFIG_SECURITY),
        CLIENT(FrameworkConstants.FRAMEWORK_CONFIG_CLIENT),
        SERVER(FrameworkConstants.FRAMEWORK_CONFIG_SERVER),
        FRAMEWORK(FrameworkConstants.FRAMEWORK_CONFIG_SERVER),
        ;
        
        private String lookupName;
        
        ProviderType(String lookupName){
            
            this.lookupName = lookupName;
        }

        public String getLookupName() {
            return lookupName;
        }
        
    }

    SimpleEntry<ProviderType, String> getProviderConfiguration();
    
}
