
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package example.flowr.node.integration;

import org.flowr.framework.api.Server;
import org.flowr.framework.core.service.ProviderServiceHandler;

public class NodeServer extends Server{

    public NodeServer(ProviderServiceHandler providerServiceHandler, String clientCanonicalName) {
        super(providerServiceHandler, clientCanonicalName);
    }

}
