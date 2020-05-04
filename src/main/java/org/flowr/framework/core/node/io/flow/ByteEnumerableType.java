
/**
 * Marker interface which returns Byte code as representation of enum value network/operation schematic data as
 * protocol exchange across multiple JVM/Runtimes
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

package org.flowr.framework.core.node.io.flow;

import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_BYTE_FIELD_DEFAULT;

public interface ByteEnumerableType{
        
    byte getCode(); 
    
    /**
     * Returns Enums of ByteEnumerableType as 8 bit array type that can be used as primary or secondary bitmap
     * @param byteEnumerableType
     * @return
     */
    static byte[] type(ByteEnumerableType byteEnumerableType) {
                
        return new byte[] {
                FRAMEWORK_BYTE_FIELD_DEFAULT,
                FRAMEWORK_BYTE_FIELD_DEFAULT,
                FRAMEWORK_BYTE_FIELD_DEFAULT,
                FRAMEWORK_BYTE_FIELD_DEFAULT,
                FRAMEWORK_BYTE_FIELD_DEFAULT,
                FRAMEWORK_BYTE_FIELD_DEFAULT,
                FRAMEWORK_BYTE_FIELD_DEFAULT,
                byteEnumerableType.getCode()
            };
    }
}
