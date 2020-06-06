
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
@XmlType(name = "Timeout", propOrder = {
    "period",
    "timeUnit"
})
public class Timeout {

    @XmlElement(name = "period", required = true)
    protected BigInteger period;
    @XmlElement(name = "timeUnit", required = true)
    protected String timeUnit;

    public BigInteger getPeriod() {
        return period;
    }

    public void setPeriod(BigInteger value) {
        this.period = value;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String value) {
        this.timeUnit = value;
    }

    @Override
    public String toString() {
        return "\n     Timeout [period=" + period + ", timeUnit=" + timeUnit + "]";
    }

}
