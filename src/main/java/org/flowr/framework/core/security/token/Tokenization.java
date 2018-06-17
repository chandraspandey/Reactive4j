package org.flowr.framework.core.security.token;

/**
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
public interface Tokenization {

	public void setSeed(AlgorithmSeed algorithmSeed);
	
	public AlgorithmSeed getSeed();
}
