
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package example.flowr.node;

import java.util.AbstractMap.SimpleEntry;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.flowr.framework.core.service.ProviderServiceHandler;

public class NodeServiceHandler implements ProviderServiceHandler{

    private SimpleEntry<ProviderType,String> entry = new SimpleEntry<>(
                                                        ProviderType.SERVICE,
                                                        "example.flowr.node.NodeServiceHandler"
                                                     );
    
    @Override
    public void delegateService(ServletContextEvent event) {
        Logger.getRootLogger().info("NodeServiceHandler : delegateService : "+event);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        Logger.getRootLogger().info("NodeServiceHandler : contextDestroyed : "+event);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        Logger.getRootLogger().info("NodeServiceHandler : contextInitialized : "+event);
    }

    @Override
    public SimpleEntry<ProviderType, String> getProviderConfiguration() {
        return this.entry;
    }


}
