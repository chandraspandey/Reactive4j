
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
@XmlType(name = "Retry", propOrder = {
    "count",
    "interval",
    "timeUnit"
})
public class Retry {

    @XmlElement(name = "count", required = true)
    protected BigInteger count;
    @XmlElement(name = "interval", required = true)
    protected BigInteger interval;
    @XmlElement(name = "timeUnit", required = true)
    protected String timeUnit;

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger value) {
        this.count = value;
    }

    public BigInteger getInterval() {
        return interval;
    }

    public void setInterval(BigInteger value) {
        this.interval = value;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String value) {
        this.timeUnit = value;
    }

    @Override
    public String toString() {
        return "\n     Retry [count=" + count + ", interval=" + interval + ", timeUnit=" + timeUnit + "]";
    }

}
