package org.flowr.framework.core.node.io.flow.handler;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface IntegrationBridge {

	public void handle(IntegrationPipelineHandlerContext handlerContext);
}
