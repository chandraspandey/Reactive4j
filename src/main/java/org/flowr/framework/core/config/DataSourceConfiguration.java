
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.flowr.framework.core.model.Model.ModelORM;

public class DataSourceConfiguration implements Configuration{

    private String dataSourceName;  
    private String dataSourceProvider;  

    private String jpaVersion;
    private boolean showQuery;
    private boolean formatQuery;
    
    private ConnectionConfiguration connectionConfiguration; 
    private QueryCacheConfiguration queryCacheConfiguration;
    private CacheConfiguration cacheConfiguration;

    private List<Class<? extends ModelORM>> mappingEntityList = new ArrayList<>();
    
    // Populated at run time for provider settings, not intended for static usage
    private Map<String, String> persistenceUnitInfoMap = new HashMap<>();
    private Properties persistenceUnitInfoProperties   = new Properties();
    
    public boolean isValid(){
            
        boolean isValid = false;
        
        if( isValidDataSource() && connectionConfiguration.isValidConnectionConfiguration() && 
                connectionConfiguration.isValidCredential() ){
            
            isValid = true;
        }
        
        return isValid;
    }
    
    private boolean isValidDataSource() {
        
        return (  dataSourceName != null && dataSourceProvider != null);
    }
    

    
    public void addMappingEntityClass(Class<? extends ModelORM> canonicalClassName) {
        
        this.mappingEntityList.add(canonicalClassName);
    }
    
    public void setMappingEntityClassList(List<Class<? extends ModelORM>> mappingEntityList) {
        
        this.mappingEntityList.addAll(mappingEntityList);
    }
    
    public List<Class<? extends ModelORM>> getMappingEntityClassNames() {
        
        return this.mappingEntityList;
    }
     
    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public boolean isShowQuery() {
        return showQuery;
    }

    public void setShowQuery(boolean showQuery) {
        this.showQuery = showQuery;
    }

    public boolean isFormatQuery() {
        return formatQuery;
    }

    public void setFormatQuery(boolean formatQuery) {
        this.formatQuery = formatQuery;
    }

    
    public String getDataSourceProvider() {
        return dataSourceProvider;
    }

    public void setDataSourceProvider(String dataSourceProvider) {
        this.dataSourceProvider = dataSourceProvider;
    }
    
     
    public Map<String, String> getProviderConfig() {
        return persistenceUnitInfoMap;
    }

    public void setProviderConfig(Map<String, String> persistenceUnitInfoMap) {
        this.persistenceUnitInfoMap = persistenceUnitInfoMap;
        
        persistenceUnitInfoMap.forEach(
            
                (k,v)   -> this.persistenceUnitInfoProperties.put(k, v)
            
        );
        
    }
    
    public Properties getPersistenceUnitInfoProperties() {
        
        return this.persistenceUnitInfoProperties;
    }
    
    public String getJpaVersion() {
        return jpaVersion;
    }

    public void setJpaVersion(String jpaVersion) {
        this.jpaVersion = jpaVersion;
    }
    
    public QueryCacheConfiguration getQueryCacheConfiguration() {
        return queryCacheConfiguration;
    }

    public void setQueryCacheConfiguration(QueryCacheConfiguration cacheConfiguration) {
        this.queryCacheConfiguration = cacheConfiguration;
    }
    
    public ConnectionConfiguration getConnectionConfiguration() {
        return connectionConfiguration;
    }

    public void setConnectionConfiguration(ConnectionConfiguration connectionConfiguration) {
        this.connectionConfiguration = connectionConfiguration;
    } 
    
    public void setCacheConfiguration(CacheConfiguration cacheConfiguration) {
        this.cacheConfiguration = cacheConfiguration;
    }
    
    public CacheConfiguration getCacheConfiguration() {
        return cacheConfiguration;
    }


    public String toString(){
        
        return "\n DataSourceConfiguration{\n"+
                " dataSourceName : "+dataSourceName+
                " | jpaVersion : "+jpaVersion+  
                " | dataSourceProvider : "+dataSourceProvider+  
                " | showQuery : "+showQuery+
                " | formatQuery : "+formatQuery+
                " | mappingEntityList : "+mappingEntityList+
                " | persistenceUnitInfoMap : "+persistenceUnitInfoMap+
                " | queryCacheConfiguration : "+queryCacheConfiguration+
                " | cacheConfiguration : "+cacheConfiguration+
                " | connectionConfiguration : "+connectionConfiguration+
                "}\n";
    }
}
