
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Policy", propOrder = {
    "policyName",
    "ha"
})
public class Policy {

    @XmlElement(name = "policyName", required = true)
    protected String policyName;
    @XmlElement(name = "ha", required = true)
    protected HA ha;
    @XmlAttribute(name = "id", required = true)
    @XmlID
    protected String id;

    public String getPolicyName() {
        return policyName;
    }
    
    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public HA getHa() {
        return ha;
    }

    public void setHa(HA ha) {
        this.ha = ha;
    }

    @Override
    public String toString() {
        return "\n   Policy \n   [ \n    policyName=" + policyName + ",\n    id=" + id+ ", " + ha + "\n   ]";
    }
}
