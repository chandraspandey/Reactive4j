
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
@XmlType(name = "PolicyConfig", propOrder = {
    "policy"
})
public class PolicyConfig {

    @XmlElement(name = "policy", required = true)
    protected Policy policy;

    public Policy getPolicy() {
        return policy;
    }
    
    public void setPolicy(Policy policy) {
        this.policy = policy;
    }
    
    @Override
    public String toString() {
        return "\n  PolicyConfig  \n  [" + policy + "\n  ]";
    }
}
