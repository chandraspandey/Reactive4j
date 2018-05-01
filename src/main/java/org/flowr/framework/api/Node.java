package org.flowr.framework.api;

import java.util.concurrent.ExecutionException;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.Circuit;
import org.flowr.framework.core.node.ServiceMesh;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface Node extends ServiceMesh {

	public Circuit buildCircuit(String configName, String filePath) throws ConfigurationException, InterruptedException, 
		ExecutionException;
}