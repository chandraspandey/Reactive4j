
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
@XmlType(name = "CacheElements", propOrder = {
    "memoryMax",
    "heapMax",
    "diskMax",
    "diskExpiry",
    "timeToIdle",
    "timeToLive"
})
public class CacheElements {

    @XmlElement(name = "memoryMax", required = true)
    protected BigInteger memoryMax;
    @XmlElement(name = "heapMax", required = true)
    protected BigInteger heapMax;
    @XmlElement(name = "diskMax", required = true)
    protected BigInteger diskMax;
    @XmlElement(name = "diskExpiry", required = true)
    protected BigInteger diskExpiry;
    @XmlElement(name = "timeToIdle", required = true)
    protected BigInteger timeToIdle;
    @XmlElement(name = "timeToLive", required = true)
    protected BigInteger timeToLive;

    public BigInteger getMemoryMax() {
        return memoryMax;
    }

    public void setMemoryMax(BigInteger value) {
        this.memoryMax = value;
    }

    public BigInteger getHeapMax() {
        return heapMax;
    }

    public void setHeapMax(BigInteger value) {
        this.heapMax = value;
    }

    public BigInteger getDiskMax() {
        return diskMax;
    }

    public void setDiskMax(BigInteger value) {
        this.diskMax = value;
    }

    public BigInteger getDiskExpiry() {
        return diskExpiry;
    }

    public void setDiskExpiry(BigInteger value) {
        this.diskExpiry = value;
    }

    public BigInteger getTimeToIdle() {
        return timeToIdle;
    }

    public void setTimeToIdle(BigInteger value) {
        this.timeToIdle = value;
    }

    public BigInteger getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(BigInteger value) {
        this.timeToLive = value;
    }
    
    @Override
    public String toString() {
        return "\n   CacheElements\n   [ "+
                "   \n     memoryMax   : "+memoryMax + 
                " , \n     heapMax     : "+heapMax + 
                " , \n     diskMax     : "+diskMax + 
                " , \n     diskExpiry  : "+diskExpiry + 
                " , \n     timeToIdle  : "+timeToIdle + 
                " , \n     timeToLive  : "+timeToLive + 
                " \n   ] \n";
    }
}
