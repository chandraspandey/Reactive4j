package org.flowr.framework.core.flow.persistence;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.flowr.framework.core.config.DataSourceConfiguration;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class DataSourceManager {
	
	private Map<String, DataSourceHandler> dataSourceMap = new HashMap<String, DataSourceHandler>();	
	
	public DataSourceManager(List<DataSourceConfiguration> dataSourceList) {
		
		Iterator<DataSourceConfiguration> iter = dataSourceList.iterator();
		
		while(iter.hasNext()) {
			
			DataSourceConfiguration dataSourceConfiguration = iter.next();			
			
			dataSourceMap.put(
					dataSourceConfiguration.getDataSourceName(), 
					new DataSourceHandler(dataSourceConfiguration));
		}
	}
	
	public DataSourceHandler getDataSourceHandler(String dataSourceName) {
		
		return dataSourceMap.get(dataSourceName);
	}
	
	public void close() {
		
		dataSourceMap.forEach(
				(k,p) ->{
					p.getEntityManager().close();
				}
		);
	}	
	
	public String toString(){
		
		return "PersistenceManager{\n"+
				"\n dataSourceMap : "+dataSourceMap+	
				"}\n";
	}
}

