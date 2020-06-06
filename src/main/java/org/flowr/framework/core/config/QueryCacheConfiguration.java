
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config;

public class QueryCacheConfiguration implements Configuration{
    
    private String cacheProviderClass;
    private String cacheProviderFactoryClass;
    private String cacheQueryClass;
    private String cacheTimestampClass;
    private boolean cacheQuery;
    private boolean cacheExternal;

    public boolean isValid() {
        
        return (cacheQuery &&  cacheTimestampClass != null  );
    }
    
    public boolean hasValidProvider() {
        
        return (cacheProviderClass != null &&  cacheProviderFactoryClass != null  );
    }

    public boolean isCacheQuery() {
        return cacheQuery;
    }

    public void setCacheQuery(boolean cacheQuery) {
        this.cacheQuery = cacheQuery;
    }
    
    public boolean isCacheExternal() {
        return cacheExternal;
    }

    public void setCacheExternal(boolean cacheExternal) {
        this.cacheExternal = cacheExternal;
    }
    
    public String getCacheProviderClass() {
        return cacheProviderClass;
    }

    public void setCacheProviderClass(String cacheProviderClass) {
        this.cacheProviderClass = cacheProviderClass;
    }
    
    public String getCacheProviderFactoryClass() {
        return cacheProviderFactoryClass;
    }

    public void setCacheProviderFactoryClass(String cacheProviderFactoryClass) {
        this.cacheProviderFactoryClass = cacheProviderFactoryClass;
    }
    
    public String getCacheQueryClass() {
        return cacheQueryClass;
    }

    public void setCacheQueryClass(String cacheQueryClass) {
        this.cacheQueryClass = cacheQueryClass;
    }

    public String getCacheTimestampClass() {
        return cacheTimestampClass;
    }

    public void setCacheTimestampClass(String cacheTimestampClass) {
        this.cacheTimestampClass = cacheTimestampClass;
    }

    
    public String toString(){
    
        return "\n QueryCacheConfiguration{\n"+
                " | cacheProviderClass : "+cacheProviderClass+
                " | cacheProviderFactoryClass : "+cacheProviderFactoryClass+
                " | cacheQueryClass : "+cacheQueryClass+
                " | cacheTimestampClass : "+cacheTimestampClass+                
                " | cacheQuery : "+cacheQuery+      
                " | cacheExternal : "+cacheExternal+    
                "}\n";
    }
    
}
