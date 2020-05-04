
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.token;

import org.flowr.framework.action.Algorithm;

public class TokenAlgorithm implements Algorithm,Tokenization {

    private AlgorithmSeed algorithmSeed;

    @Override
    public void setSeed(AlgorithmSeed algorithmSeed) {
        this.algorithmSeed = algorithmSeed;
    }

    @Override
    public AlgorithmSeed getSeed() {
        return this.algorithmSeed;
    }

}
