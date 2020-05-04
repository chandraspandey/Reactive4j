
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io.endpoint;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.EndPoint;
import org.flowr.framework.core.node.io.network.NodeChannel;

public interface NodeEndPoint  extends EndPoint{

    NodeChannel configureAsPipeline(NodeEndPointConfiguration nodeEndPointConfiguration) throws ConfigurationException;
        
}
