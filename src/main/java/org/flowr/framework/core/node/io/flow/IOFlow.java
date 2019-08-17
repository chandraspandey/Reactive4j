package org.flowr.framework.core.node.io.flow;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface IOFlow {

	/**
	 * Defines high level operation flow type for data flow classification & handling
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum IOFlowType implements ByteEnumerableType{
		NONE(0),
		INBOUND(1),
		OUTBOUND(2);
		
		private byte code = 0;
		
		IOFlowType(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static IOFlowType getType(int code) {
			
			IOFlowType operationFlowType = NONE;
			
			switch((byte) code) {
				
				case 0:{
					operationFlowType = NONE;
					break;
				}case 1:{
					operationFlowType = INBOUND;
					break;
				}case 2:{
					operationFlowType = OUTBOUND;
					break;
				}default :{
					operationFlowType = NONE;
					break;
				}			
			}
			
			return operationFlowType;
		}
	}
	
}
