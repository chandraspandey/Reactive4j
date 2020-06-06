
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config.xml;

import org.flowr.framework.core.config.CacheConfiguration;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Cache", propOrder = {
    "name",
    "eternal",
    "strategy",
    "policy",
    "statistics",
    "path",
    "disk",
    "cacheElements"
})
public class Cache {

    @XmlElement(name = "name", required = true)
    protected String name;
    @XmlElement(name = "eternal", required = true)
    protected String eternal;
    @XmlElement(name = "strategy", required = true)
    protected String strategy;
    @XmlElement(name = "cachePolicy", required = true)
    protected String policy;
    @XmlElement(name = "statistics", required = true)
    protected String statistics;
    @XmlElement(name = "path", required = true)
    protected String path;
    @XmlElement(name = "disk", required = true)
    protected Disk disk;
    @XmlElement(name = "cacheElements", required = true)
    protected CacheElements cacheElements;
    
    public CacheConfiguration toCacheConfiguration() {
        
        CacheConfiguration configuration = new CacheConfiguration();
        
        configuration.setCacheDiskMax(disk.getMax().longValue());
        configuration.setCacheDiskSpool(disk.getSpool().longValue());
        configuration.setCacheName(name);
        configuration.setCacheOverFlowToDisk(Boolean.valueOf(disk.getOverflow()));
        configuration.setCachePath(path);
        configuration.setCachePolicy(policy);
        configuration.setCacheStatistics(Boolean.valueOf(statistics));
        configuration.setCacheStrategy(strategy);
        configuration.setElementExpiryDisk(cacheElements.getDiskExpiry().longValue());
        configuration.setElementMaxDisk(cacheElements.getDiskMax().longValue());
        configuration.setElementMaxHeap(cacheElements.getHeapMax().longValue());
        configuration.setElementMaxMemory(cacheElements.getMemoryMax().longValue());
        configuration.setElementTimeToIdle(cacheElements.getTimeToIdle().longValue());
        configuration.setElementTimeToLive(cacheElements.getTimeToLive().longValue());
        configuration.setEternal(Boolean.valueOf(eternal));
        
        return configuration;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getEternal() {
        return eternal;
    }

    public void setEternal(String value) {
        this.eternal = value;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String value) {
        this.strategy = value;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String value) {
        this.policy = value;
    }

    public String getStatistics() {
        return statistics;
    }

    public void setStatistics(String value) {
        this.statistics = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String value) {
        this.path = value;
    }

    public Disk getDisk() {
        return disk;
    }

    public void setDisk(Disk value) {
        this.disk = value;
    }

    public CacheElements getCacheElements() {
        return cacheElements;
    }

    public void setCacheElements(CacheElements value) {
        this.cacheElements = value;
    }
    
    @Override
    public String toString() {
        return "\n  Cache\n  [ "+
                "   \n   name        : "+name + 
                " , \n   eternal     : "+eternal + 
                " , \n   strategy    : "+strategy + 
                " , \n   policy      : "+policy + 
                " , \n   statistics  : "+statistics + 
                " , \n   path        : "+path + 
                " , "+disk + 
                "   , "+cacheElements + 
                "  ] \n";
    }
}
