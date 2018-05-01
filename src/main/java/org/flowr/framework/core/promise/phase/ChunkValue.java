package org.flowr.framework.core.promise.phase;

import java.util.concurrent.Delayed;

import org.flowr.framework.core.process.Chunking;

/**
 * Defines Chunked response which form a stream as series of values.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ChunkValue extends Delayed,Chunking{

	public void appendChunk(byte[] chunk);
	
	public Object[] asStream();
	
	public void setChunkData(byte[] chunkData);

	public byte[] getChunkData();
	
}
