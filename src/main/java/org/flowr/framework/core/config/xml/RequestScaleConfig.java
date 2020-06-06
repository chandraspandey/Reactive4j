
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
@XmlType(name = "RequestScaleConfig", propOrder = {
    "min",
    "max",
    "progressUnit",
    "notificationDeliveryType"
})
public class RequestScaleConfig {

    @XmlElement(name = "min", required = true)
    protected BigInteger min;
    @XmlElement(name = "max", required = true)
    protected BigInteger max;
    @XmlElement(name = "progressUnit", required = true)
    protected ProgressUnit progressUnit;
    @XmlElement(name = "notificationDeliveryType", required = true)
    protected String notificationDeliveryType;
    
    public BigInteger getMin() {
        return min;
    }

    public void setMin(BigInteger value) {
        this.min = value;
    }

    public BigInteger getMax() {
        return max;
    }

    public void setMax(BigInteger value) {
        this.max = value;
    }

    public ProgressUnit getProgressUnit() {
        return progressUnit;
    }

    public void setProgressUnit(ProgressUnit value) {
        this.progressUnit = value;
    }

    public String getNotificationDeliveryType() {
        return notificationDeliveryType;
    }

    public void setNotificationDeliveryType(String value) {
        this.notificationDeliveryType = value;
    }

    @Override
    public String toString() {
        return "\n  RequestScaleConfig\n  [\n   min=" + min + ",\n   max=" + max 
                + ", \n   notificationDeliveryType=" + notificationDeliveryType + ", " + progressUnit
                + "  ]\n";
    }

}
