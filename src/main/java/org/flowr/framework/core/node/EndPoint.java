
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node;

import java.sql.Timestamp;

import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.node.io.flow.ByteEnumerableType;

public interface EndPoint {

    /**
     * 
     * Defines network end point status for operational handling
     * @author Chandra Shekhar Pandey
     * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
     *
     */
    public enum EndPointStatus implements ByteEnumerableType{
        NONE(0),
        ADDED(1),
        NEGOTIATE(2),
        REACHABLE(3),
        UNREACHABLE(4),
        REMOVED(5);
        
        private byte code;
        
        EndPointStatus(int code){
            
            this.code = (byte)code;
        }

        @Override
        public byte getCode() {
            
            return code;
        }   
        
        public static EndPointStatus getType(int code) {
            
            EndPointStatus endPointStatus = null;
            
            switch((byte) code) {
                
                case 0:{
                    endPointStatus = NONE;
                    break;
                }case 1:{
                    endPointStatus = ADDED;
                    break;
                }case 2:{
                    endPointStatus = NEGOTIATE;
                    break;
                }case 3:{
                    endPointStatus = REACHABLE;
                    break;
                }case 4:{
                    endPointStatus = UNREACHABLE;
                    break;
                }case 5:{
                    endPointStatus = REMOVED;
                    break;
                }default :{
                    endPointStatus = NONE;
                    break;
                }           
            }
            
            return endPointStatus;
        }
    }
    
    EndPointStatus getEndPointStatus();
    
    ServiceConfiguration getServiceConfiguration();
    
    Timestamp getLastUpdated();
}
