
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
@XmlType(name = "NodeConfiguration", propOrder = {
    "inboundConfig",
    "outboundConfig"
})
public class NodeConfiguration {
    
    @XmlElement(name = "inboundConfig", required = true)
    protected InboundConfig inboundConfig;
    
    @XmlElement(name = "outboundConfig", required = true)
    protected OutboundConfig outboundConfig;
    
    public InboundConfig getInboundConfig() {
        return inboundConfig;
    }

    public void setInboundConfig(InboundConfig inboundConfig) {
        this.inboundConfig = inboundConfig;
    }

    public OutboundConfig getOutboundConfig() {
        return outboundConfig;
    }

    public void setOutboundConfig(OutboundConfig outboundConfig) {
        this.outboundConfig = outboundConfig;
    }
    
    @Override
    public String toString() {
        return "\n NodeConfiguration\n [ " + inboundConfig+" , "+outboundConfig + " ] \n";
    }


}
