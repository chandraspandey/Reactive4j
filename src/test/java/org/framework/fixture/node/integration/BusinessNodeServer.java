
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.framework.fixture.node.integration;

import org.flowr.framework.api.Server;
import org.flowr.framework.core.service.ProviderServiceHandler;

public class BusinessNodeServer extends Server{

    public BusinessNodeServer(ProviderServiceHandler providerServiceHandler, String clientCanonicalName) {
        super(providerServiceHandler, clientCanonicalName);
    }

}
