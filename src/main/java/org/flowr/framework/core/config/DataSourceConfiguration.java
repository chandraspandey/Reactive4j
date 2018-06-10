package org.flowr.framework.core.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class DataSourceConfiguration implements Configuration{

	private String dataSourceName;			
	private String dialect;
	private String connectionDriverClass;
	private String connectionUserName;
	private String connectionPassword;
	private String connectionURL;
	private int connectionPoolSize;
	private boolean showQuery;
	private boolean formatQuery;
	private boolean cacheQuery;
	private boolean cacheExternal;
	private List<String> mappingEntityList = new ArrayList<String>();
	private ConfigProperties configAsProperties;
	
	public boolean isValid(){
			
		boolean isValid = false;
		
		if(
				dialect != null && connectionDriverClass != null && 
				connectionUserName != null && connectionPassword != null &&
				connectionURL != null && connectionPoolSize > 0 
		){
			
			isValid = true;
		}
		
		return isValid;
	}
	
	public void addMappingEntityClass(String canonicalClassName) {
		
		this.mappingEntityList.add(canonicalClassName);
	}
	
	public void setMappingEntityClassList(List<String> mappingEntityList) {
		
		this.mappingEntityList.addAll(mappingEntityList);
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
	
	public ConfigProperties getConfigAsProperties() {
		return configAsProperties;
	}

	public void setConfigAsProperties(ConfigProperties configAsProperties) {
		this.configAsProperties = configAsProperties;
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

	public String toString(){
		
		return "DataSourceConfiguration{\n"+
				" dataSourceName : "+dataSourceName+
				" | dialect : "+dialect+	
				" | connectionDriverClass : "+connectionDriverClass+	
				" | connectionUserName : "+connectionUserName+	
				" | connectionURL : "+connectionURL+
				" | connectionPoolSize : "+connectionPoolSize+
				" | showQuery : "+showQuery+
				" | formatQuery : "+formatQuery+
				" | cacheQuery : "+cacheQuery+		
				" | cacheExternal : "+cacheExternal+	
				" | mappingEntityList : "+mappingEntityList+
				"}\n";
	}
	

}
