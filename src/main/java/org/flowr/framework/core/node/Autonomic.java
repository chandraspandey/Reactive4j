package org.flowr.framework.core.node;

import java.util.HashMap;
import java.util.Map;

import org.flowr.framework.core.exception.ConfigurationException;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Autonomic<T,V> {
	
	public enum ResponseCode{
		
		OK(200),
		SERVER_ERROR(500),
		TYPE_ERROR(406),
		TIMEOUT(408);
		
		private int status;
		private static Map<Integer,ResponseCode> map = new HashMap<Integer,ResponseCode>();
		
		static{
			
			for(ResponseCode responseCode : ResponseCode.values()){
				
				map.put(responseCode.getResponseStatus(),responseCode );
			}
		}
		ResponseCode(int status){
			this.status = status;
		}
		
		public int getResponseStatus(){
						
			return status;			
		}
		
		public static ResponseCode valueOf(int status){
			
			return map.get(status);
		}
	}

	public  V negotiate(T t) throws ConfigurationException;
	
	public boolean isNegotiated();
}
