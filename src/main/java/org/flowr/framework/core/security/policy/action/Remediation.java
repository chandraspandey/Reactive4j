package org.flowr.framework.core.security.policy.action;

import org.flowr.framework.action.Algorithm;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface Remediation {

	public enum ObfuscationType{
		NETWORK,
		COMPUTE,
		STORAGE,
		IDENTITY		
	}
	
	public String obfuscate(String input, Algorithm algorithm);
}
