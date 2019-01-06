package org.flowr.framework.core.node.io.network;

import org.flowr.framework.core.node.io.flow.data.binary.ByteEnumerableType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ServiceMesh extends IngressController, EgressController{

	/**
	 * Defines service topology for classification & handling
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum ServiceTopologyMode implements ByteEnumerableType{
		NONE(0),
		LOCAL(1),
		DISTRIBUTED(2);
		
		private byte code = 0;
		
		ServiceTopologyMode(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static ServiceTopologyMode getType(int code) {
			
			ServiceTopologyMode serviceTopologyMode = NONE;
			
			switch((byte) code) {
				
				case 0:{
					serviceTopologyMode = NONE;
					break;
				}case 1:{
					serviceTopologyMode = LOCAL;
					break;
				}case 2:{
					serviceTopologyMode = DISTRIBUTED;
					break;
				}default :{
					serviceTopologyMode = NONE;
					break;
				}			
			}
			
			return serviceTopologyMode;
		}		
	}
}
