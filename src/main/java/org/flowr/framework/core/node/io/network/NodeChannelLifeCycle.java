package org.flowr.framework.core.node.io.network;

import java.io.IOException;

import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupStatus;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NodeChannelLifeCycle {
	
	public enum ChannelLifeCycleType{
		INIT,
		CONNECT,
		ERROR,
		SEEK,
		CANCEL,
		READ,
		WRITE,
		CLOSE
	}

	public NetworkGroupStatus close();

	public NetworkGroupStatus init() throws IOException;

}
