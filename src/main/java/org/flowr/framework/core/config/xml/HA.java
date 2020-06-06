
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HA", propOrder = {
    "retry",
    "timeout",
    "heartbeat"
})
public class HA {

    @XmlElement(name = "retry", required = true)
    protected Retry retry;
    @XmlElement(name = "timeout", required = true)
    protected Timeout timeout;
    @XmlElement(name = "heartbeat", required = true)
    protected Heartbeat heartbeat;

    public Retry getRetry() {
        return retry;
    }
    
    public void setRetry(Retry retry) {
        this.retry = retry;
    }
    
    public Timeout getTimeout() {
        return timeout;
    }
    
    public void setTimeout(Timeout timeout) {
        this.timeout = timeout;
    }
    
    public Heartbeat getHeartbeat() {
        return heartbeat;
    }
    
    public void setHeartbeat(Heartbeat heartbeat) {
        this.heartbeat = heartbeat;
    }

    @Override
    public String toString() {
        return "\n    HA \n    [" + retry + ", " + timeout+ ", " + heartbeat + "\n    ]";
    }

}
