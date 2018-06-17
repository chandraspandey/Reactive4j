package org.flowr.framework.core.flow.persistence;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.flowr.framework.core.config.DataSourceConfiguration;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class DataSourceHandler {
		
	private Map<String, PersistenceHandler<?>> handlerPool = new HashMap<String, PersistenceHandler<?>>();
	private EntityManagerFactory factory;
	private EntityManager entityManager;
	private ProviderPersistenceUnitInfo providerPersistenceUnitInfo;
	
	public DataSourceHandler(DataSourceConfiguration dataSourceConfiguration) {
		
		providerPersistenceUnitInfo = new ProviderPersistenceUnitInfo(dataSourceConfiguration);
		
		System.out.println("providerPersistenceUnitInfo : "+providerPersistenceUnitInfo);
		
		factory = Persistence.createEntityManagerFactory(dataSourceConfiguration.getDataSourceName(), dataSourceConfiguration.getProviderConfig());
		entityManager = factory.createEntityManager(dataSourceConfiguration.getProviderConfig());
		
		dataSourceConfiguration.getMappingEntityClassList().forEach(
				
			(c) -> {
				
				@SuppressWarnings("unchecked")
				PersistenceHandler<Serializable> persistenceHandler = 
						new PersistenceHandler<Serializable>(entityManager,(Class<Serializable>) c);
				
				handlerPool.put(c.getCanonicalName(), persistenceHandler);
			}
		);

	}

	public EntityManagerFactory getEntityManagerFactory() {
		return factory;
	}
	
	
	public EntityManager getEntityManager() {
		
		return entityManager;
	}

	public ProviderPersistenceUnitInfo getProviderPersistenceUnitInfo() {
		return providerPersistenceUnitInfo;
	}

	public PersistenceHandler<?> getPersistenceHandler(String entityCanonicalClassName) {		
		
		return handlerPool.get(entityCanonicalClassName);
	}
	
	public String toString(){
		
		return "DataSourceHandler{\n"+
				" factory : "+factory+	
				" | entityManager : "+entityManager+	
				" | providerPersistenceUnitInfo : "+providerPersistenceUnitInfo+
				" | handlerPool : "+handlerPool+				
				"}\n";
	}
	
}
