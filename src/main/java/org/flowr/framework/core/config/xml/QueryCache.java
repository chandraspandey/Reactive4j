
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config.xml;

import org.flowr.framework.core.config.QueryCacheConfiguration;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryCache", propOrder = {
    "enabled",
    "defaultCache",
    "external",
    "provider",
    "providerFactory",
    "timestamp"
})
public class QueryCache {

    @XmlElement(name = "enabled", required = true)
    protected String enabled;
    @XmlElement(name = "defaultCache", required = true)
    protected String defaultCache;
    @XmlElement(name = "external", required = true)
    protected String external;
    @XmlElement(name = "provider", required = true)
    protected String provider;
    @XmlElement(name = "providerFactory", required = true)
    protected String providerFactory;
    @XmlElement(name = "timestamp", required = true)
    protected String timestamp;
    
    public QueryCacheConfiguration toQueryCacheConfiguration() {
        
        QueryCacheConfiguration config = new QueryCacheConfiguration();
        
        config.setCacheExternal(Boolean.valueOf(external));
        config.setCacheProviderClass(provider);
        config.setCacheProviderFactoryClass(providerFactory);
        config.setCacheQuery(Boolean.valueOf(enabled));
        config.setCacheTimestampClass(timestamp);
        
        return config;
    }
    
    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String value) {
        this.enabled = value;
    }

    public String getDefaultCache() {
        return defaultCache;
    }

    public void setDefaultCache(String value) {
        this.defaultCache = value;
    }

    public String getExternal() {
        return external;
    }

    public void setExternal(String value) {
        this.external = value;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String value) {
        this.provider = value;
    }

    public String getProviderFactory() {
        return providerFactory;
    }

    public void setProviderFactory(String value) {
        this.providerFactory = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String value) {
        this.timestamp = value;
    }

    
    @Override
    public String toString() {
        return "\n   QueryCache\n   [ "+
                "   \n    enabled            : "+enabled + 
                " , \n    defaultCache       : "+defaultCache + 
                " , \n    external           : "+external + 
                " , \n    provider           : "+provider + 
                " , \n    providerFactory    : "+providerFactory + 
                " , \n    timestamp          : "+defaultCache + 
                "   ] \n";
    }
}
