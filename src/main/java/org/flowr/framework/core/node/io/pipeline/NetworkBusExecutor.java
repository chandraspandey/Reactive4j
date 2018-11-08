package org.flowr.framework.core.node.io.pipeline;

import org.flowr.framework.core.service.ServiceLifecycle;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NetworkBusExecutor extends ServiceLifecycle,Runnable{

	public void process();
}
