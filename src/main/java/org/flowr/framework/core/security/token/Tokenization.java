
/**
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.token;

public interface Tokenization {

    void setSeed(AlgorithmSeed algorithmSeed);
    
    AlgorithmSeed getSeed();
}
