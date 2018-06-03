package org.flowr.framework.core.promise.phase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Delayed;

import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.process.DelayBufferQueue;

/**
 * Provides Chunk Aggregation. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class ChunkBuffer {

	private DelayBufferQueue delayBufferQueue = new DelayBufferQueue();
	
	public void addChunkValue(ChunkValue chunkValue) {
		delayBufferQueue.add(chunkValue);
		
	}
	
	public Collection<Delayed> done() throws PromiseException {
		
		Collection<Delayed> streamValues = new ArrayList<Delayed>();
		
		synchronized(delayBufferQueue){
		
			streamValues.addAll(delayBufferQueue);
			delayBufferQueue.clear();
		}				
		return streamValues;			
	}
}
