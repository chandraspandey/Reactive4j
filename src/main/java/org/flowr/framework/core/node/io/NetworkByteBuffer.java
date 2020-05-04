
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.event.Event.EventType;

public class NetworkByteBuffer  implements Delayed{

    private ByteBuffer byteBuffer;
    private Timestamp networkTimestamp; 
    
    public NetworkByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
        this.networkTimestamp = Timestamp.from(Instant.now());
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    
    public void setNetworkTimestamp(Timestamp networkTimestamp) {
        this.networkTimestamp=networkTimestamp;
    }

    
    public Timestamp getNetworkTimestamp() {
        return this.networkTimestamp;
    }

    
    public EventType getEventType() {

        return EventType.NETWORK;
    }

    
    @Override
    public int compareTo(Delayed other) {
    
        int status = -1;
        
        if(
                (other instanceof NetworkByteBuffer) &&
                ((NetworkByteBuffer)other) == this
        ) {
            status = 0;
        }
        
        return status;
    }
    
    @Override
    public long getDelay(TimeUnit unit) {

        long  delay = System.currentTimeMillis() - networkTimestamp.getTime();
        
        switch(unit){
        
            case SECONDS:{
                delay = delay/1000;
                break;
            }case DAYS:{
                delay = delay/(1000*60*60*24);
                break;
            }case HOURS:{
                delay = delay/(1000*60*60);
                break;
            }case MILLISECONDS:{
                break;
            }case MINUTES:{
                delay = delay/(1000*60);
                break;
            }default:{
                break;
            }
        }
        return delay;
    }
}
