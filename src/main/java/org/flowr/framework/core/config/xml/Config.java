
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
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Config", propOrder = {
    "clientConfiguration",
    "serverConfiguration",
    "nodeConfiguration",
    "dataSourceConfig",
    "policyConfig"
})
public class Config {

    @XmlElement(name = "clientConfiguration", required = true)
    protected ClientConfiguration clientConfiguration;
    
    @XmlElement(name = "serverConfiguration", required = true)
    protected ServerConfiguration serverConfiguration;
    
    @XmlElement(name = "nodeConfiguration", required = true)
    protected NodeConfiguration nodeConfiguration;
    
    @XmlElement(name = "dataSourceConfig", required = true)
    protected DataSourceConfig dataSourceConfig;    
    
    @XmlElement(name = "policyConfig", required = true)
    protected PolicyConfig policyConfig; 

    public ClientConfiguration getClientConfiguration() {
        return clientConfiguration;
    }

    public void setClientConfiguration(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }
    
    public ServerConfiguration getServerConfiguration() {
        return serverConfiguration;
    }

    public void setServerConfiguration(ServerConfiguration serverConfiguration) {
        this.serverConfiguration = serverConfiguration;
    }
    
    public NodeConfiguration getNodeConfiguration() {
        return nodeConfiguration;
    }

    public void setNodeConfiguration(NodeConfiguration nodeConfiguration) {
        this.nodeConfiguration = nodeConfiguration;
    }
    
    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

    public void setDataSourceConfig(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }
    
    public PolicyConfig getPolicyConfig() {
        return policyConfig;
    }

    public void setPolicyConfig(PolicyConfig policyConfig) {
        this.policyConfig = policyConfig;
    }

    @Override
    public String toString() {
        return "\nConfig\n[ " + clientConfiguration + serverConfiguration+ nodeConfiguration+ dataSourceConfig+
                policyConfig+"\n]\n";
    }



}

