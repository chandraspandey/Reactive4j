
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io.network;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.io.flow.ByteEnumerableType;
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupStatus;

public interface NodeChannelLifeCycle {
    
    NetworkGroupStatus close();

    NetworkGroupStatus init() throws ConfigurationException;
    
    /**
     * 
     * Defines channel life cycle type for operational handling
     * @author Chandra Shekhar Pandey
     * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
     *
     */
    public enum ChannelLifeCycleType implements ByteEnumerableType{
        NONE(0),
        INIT(1),
        CONNECT(2),
        ERROR(3),
        SEEK(4),
        CANCEL(5),
        READ(6),
        WRITE(7),
        CLOSE(8);
        
        private byte code;
        
        ChannelLifeCycleType(int code){
            
            this.code = (byte)code;
        }

        @Override
        public byte getCode() {
            
            return code;
        }   
        
        public static ChannelLifeCycleType getType(int code) {
            
            ChannelLifeCycleType channelLifeCycleType = null;
            
            switch((byte) code) {
                
                case 0:{
                    channelLifeCycleType = NONE;
                    break;
                }case 1:{
                    channelLifeCycleType = INIT;
                    break;
                }case 2:{
                    channelLifeCycleType = CONNECT;
                    break;
                }case 3:{
                    channelLifeCycleType = ERROR;
                    break;
                }case 4:{
                    channelLifeCycleType = SEEK;
                    break;
                }case 5:{
                    channelLifeCycleType = CANCEL;
                    break;
                }case 6:{
                    channelLifeCycleType = READ;
                    break;
                }case 7:{
                    channelLifeCycleType = WRITE;
                    break;
                }default :{
                    channelLifeCycleType = NONE;
                    break;
                }           
            }
            
            return channelLifeCycleType;
        }
    }

}

