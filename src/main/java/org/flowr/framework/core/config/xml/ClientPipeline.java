
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
@XmlType(name = "ClientPipeline", propOrder = {
    "pipeline"
})
public class ClientPipeline {
    
    @XmlElement(name = "pipeline", required = true)
    protected Pipeline pipeline;

    public Pipeline getClientPipeline() {
        return pipeline;
    }

    public void setClientPipeline(Pipeline clientPipeline) {
        this.pipeline = clientPipeline;
    }
    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        sb.append("\n   ClientPipeline\n   {\n "); 
        sb.append(pipeline); 
        sb.append("\n    }\n "); 
        return sb.toString();
    }
}
