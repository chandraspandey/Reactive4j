package org.flowr.framework.core.node.io.flow.data.binary;

/**
 * Marker interface which returns Byte code as representation of enum value network/operation schematic data as
 * protocol exchange across multiple JVM/Runtimes
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
public interface ByteEnumerableType{
	
	public byte getCode();			
}
