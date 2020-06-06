
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
@XmlType(name = "Heartbeat", propOrder = {
    "interval",
    "timeUnit"
})
public class Heartbeat {

    @XmlElement(name = "interval", required = true)
    protected BigInteger interval;
    @XmlElement(name = "timeUnit", required = true)
    protected String timeUnit;

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
        return "\n     Heartbeat [interval=" + interval + ", timeUnit=" + timeUnit + "]";
    }

}
