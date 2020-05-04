
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config;

public class CacheConfiguration implements Configuration{
    
    private String cacheName;
    private String cacheStrategy;
    private String cachePath;
    private String cachePolicy;
    
    private boolean cacheOverFlowToDisk;
    private boolean cacheStatistics;
    private boolean isEternal;
    
    private long cacheDiskSpool;
    private long cacheDiskMax;
    private long elementMaxMemory;
    private long elementMaxHeap;
    private long elementMaxDisk;
    private long elementTimeToLive;
    private long elementTimeToIdle;
    private long elementExpiryDisk;
    
    public boolean isValid(){
        
        boolean isValid = false;
        
        if( elementMaxMemory > 0 && elementTimeToLive > 0 ){
            
            isValid = true;
        }
        
        return isValid;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getCacheStrategy() {
        return cacheStrategy;
    }

    public void setCacheStrategy(String cacheStrategy) {
        this.cacheStrategy = cacheStrategy;
    }

    public String getCachePath() {
        return cachePath;
    }

    public void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }

    public String getCachePolicy() {
        return cachePolicy;
    }

    public void setCachePolicy(String cachePolicy) {
        this.cachePolicy = cachePolicy;
    }
    
    public boolean isCacheOverFlowToDisk() {
        return cacheOverFlowToDisk;
    }

    public void setCacheOverFlowToDisk(boolean cacheOverFlowToDisk) {
        this.cacheOverFlowToDisk = cacheOverFlowToDisk;
    }

    public boolean isCacheStatistics() {
        return cacheStatistics;
    }

    public void setCacheStatistics(boolean cacheStatistics) {
        this.cacheStatistics = cacheStatistics;
    }

    public long getCacheDiskSpool() {
        return cacheDiskSpool;
    }

    public void setCacheDiskSpool(long cacheDiskSpool) {
        this.cacheDiskSpool = cacheDiskSpool;
    }

    public long getElementMaxMemory() {
        return elementMaxMemory;
    }

    public void setElementMaxMemory(long elementMaxMemory) {
        this.elementMaxMemory = elementMaxMemory;
    }

    public long getElementMaxHeap() {
        return elementMaxHeap;
    }

    public void setElementMaxHeap(long elementMaxHeap) {
        this.elementMaxHeap = elementMaxHeap;
    }

    public long getElementMaxDisk() {
        return elementMaxDisk;
    }

    public void setElementMaxDisk(long elementMaxDisk) {
        this.elementMaxDisk = elementMaxDisk;
    }

    public long getElementTimeToLive() {
        return elementTimeToLive;
    }

    public void setElementTimeToLive(long elementTimeToLive) {
        this.elementTimeToLive = elementTimeToLive;
    }

    public long getElementTimeToIdle() {
        return elementTimeToIdle;
    }

    public void setElementTimeToIdle(long elementTimeToIdle) {
        this.elementTimeToIdle = elementTimeToIdle;
    }

    public long getElementExpiryDisk() {
        return elementExpiryDisk;
    }

    public void setElementExpiryDisk(long elementExpiryDisk) {
        this.elementExpiryDisk = elementExpiryDisk;
    }
    
    public boolean isEternal() {
        return isEternal;
    }

    public void setEternal(boolean isEternal) {
        this.isEternal = isEternal;
    }

    public long getCacheDiskMax() {
        return cacheDiskMax;
    }

    public void setCacheDiskMax(long cacheDiskMax) {
        this.cacheDiskMax = cacheDiskMax;
    }
    
    public String toString(){
        
        return "CacheConfiguration{\n"+
                " cacheName : "+cacheName+
                " | cacheStrategy : "+cacheStrategy+    
                " | cachePath : "+cachePath+    
                " | cachePolicy : "+cachePolicy+    
                " | cacheOverFlowToDisk : "+cacheOverFlowToDisk+        
                " | cacheStatistics : "+cacheStatistics+    
                " | isEternal : "+isEternal+
                " | cacheDiskMax : "+cacheDiskMax+
                " | cacheDiskSpool : "+cacheDiskSpool+
                " | elementMaxMemory : "+elementMaxMemory+
                " | elementMaxHeap : "+elementMaxHeap+
                " | elementMaxDisk : "+elementMaxDisk+
                " | elementTimeToLive : "+elementTimeToLive+
                " | elementTimeToIdle : "+elementTimeToIdle+
                " | elementTimeToIdle : "+elementTimeToIdle+
                " | elementExpiryDisk : "+elementExpiryDisk+    
                "}\n";
    }


}
