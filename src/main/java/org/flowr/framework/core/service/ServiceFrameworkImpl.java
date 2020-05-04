
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.exception.ConfigurationException;

class ServiceFrameworkImpl extends AbstractServiceFrameworkProvider{

    private boolean isEnabled = true;
    private SimpleEntry<ProviderType,String> entry = new SimpleEntry<>(
                                                        ProviderType.FRAMEWORK,
                                                        this.getClass().getCanonicalName()
                                                     );
    
    @Override
    public void configure(String configurationFilePath) throws ConfigurationException{
                
        if(configurationFilePath == null) {
            
            configurationFilePath = System.getProperty(FrameworkConstants.FRAMEWORK_CONFIG_PATH);           
        }
            
        this.getCatalog().getConfigurationService().configure(configurationFilePath);        
    }

    @Override
    public SimpleEntry<ProviderType, String> getProviderConfiguration() {

        return this.entry;
    }

    @Override
    public List<ServiceFrameworkComponent> getServiceList() {
        return this.getCatalog().getServiceList();
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;        
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
    
}
