
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.api;

import java.util.AbstractMap.SimpleEntry;

import javax.servlet.ServletContextEvent;

import org.flowr.framework.core.service.ProviderServiceHandler;
import org.flowr.framework.core.service.ServiceHook;


public class Client implements Provider,ServiceHook{
    
    private SimpleEntry<ProviderType,String> entry;
    
    private ProviderServiceHandler providerServiceHandler;
    
    public Client(ProviderServiceHandler providerServiceHandler, String clientCanonicalName) {
        
        this.providerServiceHandler = providerServiceHandler;
        this.entry                  = new SimpleEntry<>(
                                        ProviderType.CLIENT,
                                        clientCanonicalName
                                     );
    }
        
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        this.providerServiceHandler.contextDestroyed(servletContextEvent);
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        this.providerServiceHandler.contextInitialized(servletContextEvent);
    }

    @Override
    public void delegateService(ServletContextEvent servletContextEvent) {
        
        this.providerServiceHandler.delegateService(servletContextEvent);
    }

    @Override
    public SimpleEntry<ProviderType, String> getProviderConfiguration() {
        return this.entry;
    }
    
    
}
