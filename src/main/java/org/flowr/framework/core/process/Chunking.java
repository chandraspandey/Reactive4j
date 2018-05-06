package org.flowr.framework.core.process;

/**
 * Defines chunking behavior of splitting & assembling a Block segment into 
 * portable chunks over network. The order & dispatch of chunks can be done 
 * in parallel/concurrent thereby allowing concurrent assembly & time efficiency
 * if bandwidth can support mutliple streams.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Chunking {

	// QoS is correct fit at transport layer should be moved there 
	/**
	 * Evaluates bandwidth with roundtrip of network ping to make a judgment. 
	 * @return
	 */
	public boolean isQoSApplicable();
	
	/**
	 * Marks the start of segment chunk. Mandatory presence for I/O operation in 
	 * publisher subscriber mechanism.
	 * @return
	 */
	public byte[] getChunkStartIndex();
	
	public void setChunkStartIndex(byte[] chunkStartIndex);
	
	
	/**
	 * Marks the chunk length.Mandatory presence for I/O operation.
	 * @return
	 */
	public byte[] getChunkLength();
	
	public void setChunkLength(byte[] chunkLength);
	
	/**
	 * Marks the end of segment chunk. Based on chunk length implicitly computed
	 *  to facilitate utility offering.
	 * @return
	 */
	public byte[] getChunkEndIndex();
	

}
