
/**
 * Defines Chunked response which form a stream as series of values.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.promise.phase;

import java.util.concurrent.Delayed;

import org.flowr.framework.core.process.Chunking;

public interface ChunkValue extends Delayed,Chunking{

    void appendChunk(byte[] chunk);
    
    Object[] asStream();
    
    void setChunkData(byte[] chunkData);

    byte[] getChunkData();
    
}
