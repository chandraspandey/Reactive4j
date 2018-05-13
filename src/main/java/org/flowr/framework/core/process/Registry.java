package org.flowr.framework.core.process;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Registry {

	public enum RegistryType{
		LOCAL,
		CLUSTER
	}
	
	public RegistryType getRegistryType();
	
	public void setRegistryType(RegistryType registryType);
}
