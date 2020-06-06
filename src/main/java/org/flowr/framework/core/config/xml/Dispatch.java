
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config.xml;

import java.math.BigInteger;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dispatch", propOrder = {
    "size",
    "mode"
})
public class Dispatch {

    @XmlElement(name = "size", required = true)
    protected BigInteger size;
    @XmlElement(name = "mode", required = true)
    protected String mode;

    public BigInteger getSize() {
        return size;
    }

    public void setSize(BigInteger value) {
        this.size = value;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String value) {
        this.mode = value;
    }

    @Override
    public String toString() {
        return "\n     Dispatch [size=" + size + ", mode=" + mode + "]";
    }

}
