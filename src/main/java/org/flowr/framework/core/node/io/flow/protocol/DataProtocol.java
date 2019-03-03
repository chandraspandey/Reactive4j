package org.flowr.framework.core.node.io.flow.protocol;

import org.flowr.framework.core.node.io.flow.data.binary.ByteEnumerableType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface DataProtocol {

	
	/**
	 * Defines high level data transfer mode for data handling & processing.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum DataMode implements ByteEnumerableType{
		NONE(0),
		SYNC(1),
		ASYNC(2);
		
		private byte code = 0;
		
		DataMode(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static DataMode getType(int code) {
			
			DataMode dataMode = NONE;
			
			switch((byte) code) {
				
				case 0:{
					dataMode = NONE;
					break;
				}case 1:{
					dataMode = SYNC;
					break;
				}case 2:{
					dataMode = ASYNC;
					break;
				}default :{
					dataMode = NONE;
					break;
				}			
			}
			
			return dataMode;
		}
	}
	
	/**
	 * Defines high level Data Operation Type for operation delegation.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum DataOperationType implements ByteEnumerableType{
		NONE(0),
		COMMAND(1),
		CREATE(2),
		READ(3),
		UPDATE(4),
		DELETE(5),
		CREATEORUPDATE(6),
		ROLLBACK(7)		
		;
		
		private byte code = 0;
		
		DataOperationType(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static DataOperationType getType(int code) {
			
			DataOperationType dataOperationType = NONE;
			
			switch((byte) code) {
				
				case 0:{
					dataOperationType = NONE;
					break;
				}case 1:{
					dataOperationType = COMMAND;
					break;
				}case 2:{
					dataOperationType = CREATE;
					break;
				}case 3:{
					dataOperationType = READ;
					break;
				}case 4:{
					dataOperationType = UPDATE;
					break;
				}case 5:{
					dataOperationType = DELETE;
					break;
				}case 6:{
					dataOperationType = CREATEORUPDATE;
					break;
				}case 7:{
					dataOperationType = ROLLBACK;
					break;
				}default :{
					dataOperationType = NONE;
					break;
				}			
			}
			
			return dataOperationType;
		}
	}
	
	/**
	 * Defines high level Data Distribution Type for handling distribution in clustered operation.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum DataDistributionType implements ByteEnumerableType{
		NONE(0),
		UNICAST(1),
		MULTICAST(2),
		BROADCAST(3);
		
		private byte code = 0;
		
		DataDistributionType(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static DataDistributionType getType(int code) {
			
			DataDistributionType dataDistributionType = NONE;
			
			switch((byte) code) {
				
				case 0:{
					dataDistributionType = NONE;
					break;
				}case 1:{
					dataDistributionType = UNICAST;
					break;
				}case 2:{
					dataDistributionType = MULTICAST;
					break;
				}case 3:{
					dataDistributionType = BROADCAST;
					break;
				}default :{
					dataDistributionType = NONE;
					break;
				}			
			}
			
			return dataDistributionType;
		}
	}
}
