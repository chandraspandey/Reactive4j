
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config.xml;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.flowr.framework.core.config.ServiceConfiguration;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceConfig", propOrder = {
    "notificationEndpoint",
    "serverHostName",
    "serverHostPort",
    "clientHostName",
    "clientHostPort",
    "minThreads",
    "maxThreads",
    "policy"
})
public class ServiceConfig {

    @XmlElement(name = "notificationEndpoint", required = true)
    protected String notificationEndpoint;
    @XmlElement(name = "serverHostName", required = true)
    protected String serverHostName;
    @XmlElement(name = "serverHostPort", required = true)
    protected String serverHostPort;
    @XmlElement(name = "clientHostName", required = true)
    protected String clientHostName;
    @XmlElement(name = "clientHostPort", required = true)
    protected String clientHostPort;
    @XmlElement(name = "minThreads", required = true)
    protected BigInteger minThreads;
    @XmlElement(name = "maxThreads", required = true)
    protected BigInteger maxThreads;
    
    @XmlElement(name = "policy", required = true)
    protected Policy policy;
    
    public ServiceConfiguration toServiceConfiguration() {
        
        ServiceConfiguration config = new ServiceConfiguration(); 
       
        config.setMinThreads(minThreads.intValue());
        config.setMaxThreads(maxThreads.intValue());           
        config.setServerHostName(serverHostName);
        config.setServerHostPort(Integer.parseInt(serverHostPort));
        config.setNotificationEndPoint(notificationEndpoint);
        
        if(policy != null) {
            
            config.setTimeout(policy.getHa().getTimeout().getPeriod().longValue());
            config.setTimeoutTimeUnit(TimeUnit.valueOf(policy.getHa().getTimeout().getTimeUnit()));
            config.setHeartbeatInterval(policy.getHa().getHeartbeat().getInterval().longValue());
            config.setHeartbeatTimeUnit(TimeUnit.valueOf(policy.getHa().getHeartbeat().getTimeUnit())); 
        }
        
        if(clientHostName != null) {
            config.setClientHostName(clientHostName);
        }
        
        if(clientHostPort != null) {
            config.setClientHostPort(Integer.parseInt(clientHostPort));
        }
        
        Logger.getRootLogger().info(this);
        
        return config;
    }
    
    public String getNotificationEndpoint() {
        return notificationEndpoint;
    }

    public void setNotificationEndpoint(String value) {
        this.notificationEndpoint = value;
    }

    public String getServerHostName() {
        return serverHostName;
    }

    public void setServerHostName(String value) {
        this.serverHostName = value;
    }

    public String getServerHostPort() {
        return serverHostPort;
    }

    public void setServerHostPort(String value) {
        this.serverHostPort = value;
    }

    public BigInteger getMinThreads() {
        return minThreads;
    }

    public void setMinThreads(BigInteger value) {
        this.minThreads = value;
    }

    public BigInteger getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(BigInteger value) {
        this.maxThreads = value;
    }
    
    public String getClientHostName() {
        return clientHostName;
    }
    
    public void setClientHostName(String clientHostName) {
        this.clientHostName = clientHostName;
    }
    
    public String getClientHostPort() {
        return clientHostPort;
    }

    public void setClientHostPort(String clientHostPort) {
        this.clientHostPort = clientHostPort;
    }
    
    public Policy getPolicy() {
        return policy;
    }
    
    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    @Override
    public String toString() {
        return "\n  ServiceConfig\n  [\n   notificationEndpoint=" + notificationEndpoint 
                +",\n   serverHostName=" + serverHostName
                + ",\n   serverHostPort=" + serverHostPort
                +",\n   clientHostName=" + clientHostName
                + ",\n   clientHostPort=" + clientHostPort 
                + ",\n   minThreads=" + minThreads 
                + ",\n   maxThreads=" + maxThreads
                + ",   " + policy + "\n  ]\n";
    }

}
