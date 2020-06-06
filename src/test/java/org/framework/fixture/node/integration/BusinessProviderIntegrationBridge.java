
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.framework.fixture.node.integration;

import org.apache.log4j.Logger;
import org.flowr.framework.core.node.io.flow.handler.IntegrationBridge;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandlerContext;

public class BusinessProviderIntegrationBridge implements IntegrationBridge {

    @Override
    public void handle(IntegrationPipelineHandlerContext handlerContext) {
       
            Logger.getRootLogger().debug("\t\tProviderIntegrationBridge : "+handlerContext);
    }

}
