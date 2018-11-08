package org.flowr.framework.core.security.policy.action;

import org.flowr.framework.action.Algorithm;

/**
 * Defines Obfuscation of identity text in communication.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface Obfuscation {

	public enum ObfuscationType{
		NETWORK,
		COMPUTE,
		STORAGE,
		IDENTITY		
	}
	
	public String obfuscate(String input, Algorithm algorithm);
}
