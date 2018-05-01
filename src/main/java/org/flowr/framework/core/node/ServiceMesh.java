package org.flowr.framework.core.node;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ServiceMesh extends IngressController, EgressController{

	public enum ServiceTopologyMode{
		LOCAL,
		DISTRIBUTED
	}
}
