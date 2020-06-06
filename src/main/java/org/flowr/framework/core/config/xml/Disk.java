
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
@XmlType(name = "Disk", propOrder = {
    "overflow",
    "spool",
    "max"
})
public class Disk {

    @XmlElement(name = "overflow", required = true)
    protected String overflow;
    @XmlElement(name = "spool", required = true)
    protected BigInteger spool;
    @XmlElement(name = "max", required = true)
    protected BigInteger max;

    public String getOverflow() {
        return overflow;
    }

    public void setOverflow(String value) {
        this.overflow = value;
    }

    public BigInteger getSpool() {
        return spool;
    }

    public void setSpool(BigInteger value) {
        this.spool = value;
    }

    public BigInteger getMax() {
        return max;
    }

    public void setMax(BigInteger value) {
        this.max = value;
    }
    
    @Override
    public String toString() {
        return "\n   Disk\n   [ "+
                "   \n    overflow    : "+overflow + 
                " , \n    spool      : "+spool + 
                " , \n    max        : "+max + 
                " \n   ] \n";
    }

}
