package org.flowr.framework.core.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class DataSourceConfiguration implements Configuration{

	private String dataSource;	
	private String dataSourceName;	
	private String dataSourceProvider;	
	private String dialect;
	private String connectionDriverClass;
	private String connectionUserName;
	private String connectionPassword;
	private String connectionURL;
	private String cacheProviderClass;
	private String cacheProviderFactoryClass;
	private String cacheQueryClass;
	private String cacheTimestampClass;
	private String jpaVersion;
	
	private int connectionPoolSize;
	private boolean showQuery;
	private boolean formatQuery;
	private boolean cacheQuery;
	private boolean cacheExternal;

	private List<String> mappingEntityList = new ArrayList<String>();
	
	// Populated at run time for provider settings, not intended for static usage
	private Map<String, String> persistenceUnitInfoMap = new HashMap<String,String>();
	private Properties persistenceUnitInfoProperties   = new Properties();
	
	public boolean isValid(){
			
		boolean isValid = false;
		
		if(
				dataSource != null && dataSourceName != null && dataSourceProvider != null &&
				dialect != null && connectionDriverClass != null && 
				cacheQueryClass != null && cacheTimestampClass != null &&
				connectionUserName != null && connectionPassword != null &&
				connectionURL != null && connectionPoolSize > 0 
		){
			
			isValid = true;
		}
		
		return isValid;
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
	
	public void addMappingEntityClass(String canonicalClassName) {
		
		this.mappingEntityList.add(canonicalClassName);
	}
	
	public void setMappingEntityClassList(List<String> mappingEntityList) {
		
		this.mappingEntityList.addAll(mappingEntityList);
	}
	
	public List<String> getMappingEntityClassNames() {
		
		return this.mappingEntityList;
	}
	
	public List<Class<?>> getMappingEntityClassList(){
		
		List<Class<?>> mappingEntityClassList = new ArrayList<Class<?>>();
		
		mappingEntityList.forEach(
			(e) ->{
				
				try {
					mappingEntityClassList.add(Class.forName(e, true,Thread.currentThread().getContextClassLoader()));
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
				
		);
		
		return mappingEntityClassList;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getConnectionDriverClass() {
		return connectionDriverClass;
	}

	public void setConnectionDriverClass(String connectionDriverClass) {
		this.connectionDriverClass = connectionDriverClass;
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

	public String getConnectionUserName() {
		return connectionUserName;
	}

	public void setConnectionUserName(String connectionUserName) {
		this.connectionUserName = connectionUserName;
	}

	public String getConnectionPassword() {
		return connectionPassword;
	}

	public void setConnectionPassword(String connectionPassword) {
		this.connectionPassword = connectionPassword;
	}

	public String getConnectionURL() {
		return connectionURL;
	}

	public void setConnectionURL(String connectionURL) {
		this.connectionURL = connectionURL;
	}

	public int getConnectionPoolSize() {
		return connectionPoolSize;
	}

	public void setConnectionPoolSize(int connectionPoolSize) {
		this.connectionPoolSize = connectionPoolSize;
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
	
	public String getDataSourceProvider() {
		return dataSourceProvider;
	}

	public void setDataSourceProvider(String dataSourceProvider) {
		this.dataSourceProvider = dataSourceProvider;
	}
	
	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	
	public Map<String, String> getProviderConfig() {
		return persistenceUnitInfoMap;
	}

	public void setProviderConfig(Map<String, String> persistenceUnitInfoMap) {
		this.persistenceUnitInfoMap = persistenceUnitInfoMap;
		
		persistenceUnitInfoMap.forEach(
			(k,v)	->{
				this.persistenceUnitInfoProperties.put(k, v);
			}
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

	public String toString(){
		
		return "DataSourceConfiguration{\n"+
				" dataSourceName : "+dataSourceName+
				" | dataSource : "+dataSource+	
				" | jpaVersion : "+jpaVersion+	
				" | dialect : "+dialect+	
				" | dataSourceProvider : "+dataSourceProvider+	
				" | connectionDriverClass : "+connectionDriverClass+	
				" | connectionUserName : "+connectionUserName+	
				" | connectionURL : "+connectionURL+
				" | connectionPoolSize : "+connectionPoolSize+
				" | cacheProviderClass : "+cacheProviderClass+
				" | cacheProviderFactoryClass : "+cacheProviderFactoryClass+
				" | cacheQueryClass : "+cacheQueryClass+
				" | cacheTimestampClass : "+cacheTimestampClass+				
				" | showQuery : "+showQuery+
				" | formatQuery : "+formatQuery+
				" | cacheQuery : "+cacheQuery+		
				" | cacheExternal : "+cacheExternal+	
				" | mappingEntityList : "+mappingEntityList+
				" | persistenceUnitInfoMap : "+persistenceUnitInfoMap+
				"}\n";
	}

}
