package org.flowr.framework.core.node.io.endpoint;

import java.io.IOException;

import org.flowr.framework.core.node.EndPoint;
import org.flowr.framework.core.node.io.network.NodeChannel;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NodeEndPoint  extends EndPoint{

	public NodeChannel configureAsPipeline(NodeEndPointConfiguration nodeEndPointConfiguration) throws IOException;
		
}
