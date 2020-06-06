
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config.xml;

import org.flowr.framework.core.config.DataSourceConfiguration;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataSourceConfig", propOrder = {
    "name",
    "jpaVersion",
    "provider",
    "connection",
    "query",
    "mapping",
    "cache"
})
public class DataSourceConfig {

    @XmlElement(name = "name", required = true)
    protected String name;
    @XmlElement(name = "jpaVersion", required = true)
    protected String jpaVersion;
    @XmlElement(name = "provider", required = true)
    protected String provider;
    @XmlElement(name = "connection", required = true)
    protected Connection connection;
    @XmlElement(name = "query", required = true)
    protected Query query;
    @XmlElement(name = "mapping", required = true)
    protected Mapping mapping;
    @XmlElement(name = "cache", required = true)
    protected Cache cache;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getJpaVersion() {
        return jpaVersion;
    }

    public void setJpaVersion(String value) {
        this.jpaVersion = value;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String value) {
        this.provider = value;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection value) {
        this.connection = value;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query value) {
        this.query = value;
    }

    public Mapping getMapping() {
        return mapping;
    }

    public void setMapping(Mapping value) {
        this.mapping = value;
    }


    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache value) {
        this.cache = value;
    }
    
    public DataSourceConfiguration toDataSourceConfiguration() {
        
        DataSourceConfiguration config = new DataSourceConfiguration();
        config.setDataSourceName(name);
        config.setDataSourceProvider(provider);
        config.setJpaVersion(jpaVersion);
        
        config.setCacheConfiguration(cache.toCacheConfiguration());
        config.setConnectionConfiguration(connection.toConnectionConfiguration());
        
        config.setFormatQuery(Boolean.valueOf(query.getFormat()));
        config.setShowQuery(Boolean.valueOf(query.getShow()));
        
        config.setQueryCacheConfiguration(query.getQueryCache().toQueryCacheConfiguration());
        
        config.setMappingEntityClassList(mapping.classes());
        
        return config;
    }

    @Override
    public String toString() {
        return "\n DataSourceConfig\n [ "
                + " \n  name         : " + name+
                " , \n  jpaVersion   : "+jpaVersion + 
                " , \n  provider     : "+provider + 
                " , "+connection + 
                "  , "+query + 
                "  , "+mapping + 
                "  , "+cache + 
                " ] \n";
    }
}

