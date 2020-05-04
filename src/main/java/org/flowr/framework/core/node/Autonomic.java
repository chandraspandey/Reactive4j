
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node;

import java.util.HashMap;
import java.util.Map;

import org.flowr.framework.core.exception.PromiseException;

public interface Autonomic<T,V> {
    
   V negotiate(T t) throws PromiseException;
    
   boolean isNegotiated();
    
    public enum ResponseCode{
        
        OK(200),
        SERVER_ERROR(500),
        TYPE_ERROR(406),
        TIMEOUT(408);
        
        private int status;
        private static Map<Integer,ResponseCode> map = new HashMap<>();
        
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


}
