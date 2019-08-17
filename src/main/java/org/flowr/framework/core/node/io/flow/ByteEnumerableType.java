package org.flowr.framework.core.node.io.flow;

/**
 * Marker interface which returns Byte code as representation of enum value network/operation schematic data as
 * protocol exchange across multiple JVM/Runtimes
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
public interface ByteEnumerableType{
	
	public static final int FIELD_DEFAULT						= 0;
	
	public byte getCode();	
	
	/**
	 * Returns Enums of ByteEnumerableType as 8 bit array type that can be used as primary or secondary bitmap
	 * @param byteEnumerableType
	 * @return
	 */
	public static byte[] type(ByteEnumerableType byteEnumerableType) {
		
		byte[] type = new byte[] {
					FIELD_DEFAULT,
					FIELD_DEFAULT,
					FIELD_DEFAULT,
					FIELD_DEFAULT,
					FIELD_DEFAULT,
					FIELD_DEFAULT,
					FIELD_DEFAULT,
					byteEnumerableType.getCode()};
		
		
		return type;
	}
}
