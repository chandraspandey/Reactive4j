package org.flowr.framework.core.node.io.flow;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface IOFlow {

	public enum IOFlowType{
		INBOUND,
		OUTBOUND
	}
	
	public enum IOType{
		READ,
		WRITE
	}
	
	public enum IOStatus{
		INITIATED,
		PROCESSING,
		COMPLETED,
		TIMEOUT,
		ERROR
	}
}
