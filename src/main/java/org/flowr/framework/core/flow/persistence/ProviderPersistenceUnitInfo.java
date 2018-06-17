package org.flowr.framework.core.flow.persistence;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import org.flowr.framework.core.config.DataSourceConfiguration;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ProviderPersistenceUnitInfo implements PersistenceUnitInfo{

	private String jpaVersion;
	private DataSourceProvider jtaDataSource;
	private DataSourceProvider nonJtaDataSource;
	private String persistenceUnitName;
	private String providerClassName;
	private DataSourceConfiguration dataSourceConfiguration;
	private Properties persistenceUnitInfoProperties;
	private boolean excludeUnlistedClasses					= Boolean.FALSE;
	private SharedCacheMode sharedCacheMode 				= SharedCacheMode.UNSPECIFIED;
	private ValidationMode validationMode 					= ValidationMode.NONE; 
	private PersistenceUnitTransactionType puTransactionType= PersistenceUnitTransactionType.RESOURCE_LOCAL;
	private List<String> mappingFileNames					= new ArrayList<String>();	
	
	public ProviderPersistenceUnitInfo(DataSourceConfiguration dataSourceConfiguration) {
					
		this.dataSourceConfiguration 		= dataSourceConfiguration;				
		this.jtaDataSource 					= new DataSourceProvider(dataSourceConfiguration);
		this.nonJtaDataSource 				= new DataSourceProvider(dataSourceConfiguration);
		this.persistenceUnitName 			= dataSourceConfiguration.getDataSourceName();
		this.persistenceUnitInfoProperties 	= dataSourceConfiguration.getPersistenceUnitInfoProperties();
		this.jpaVersion						= dataSourceConfiguration.getJpaVersion();
		this.providerClassName				= dataSourceConfiguration.getDataSourceProvider();
	}
	
	@Override
	public void addTransformer(ClassTransformer classTransformer) {
	}

	@Override
	public boolean excludeUnlistedClasses() {
		return this.excludeUnlistedClasses;
	}

	@Override
	public ClassLoader getClassLoader() {
		return this.getClassLoader();
	}

	@Override
	public List<URL> getJarFileUrls() {
		return Collections.emptyList();
	}

	@Override
	public DataSource getJtaDataSource() {
		
		return jtaDataSource;
	}

	@Override
	public List<String> getManagedClassNames() {
		
		return dataSourceConfiguration.getMappingEntityClassNames();	
	}

	@Override
	public List<String> getMappingFileNames() {
		return this.mappingFileNames;
	}

	@Override
	public ClassLoader getNewTempClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	@Override
	public DataSource getNonJtaDataSource() {
		return nonJtaDataSource;
	}

	@Override
	public String getPersistenceUnitName() {
		return persistenceUnitName;
	}

	@Override
	public URL getPersistenceUnitRootUrl() {
		return null;
	}

	@Override
	public String getPersistenceXMLSchemaVersion() {
		return this.jpaVersion;
	}

	@Override
	public Properties getProperties() {
		return this.persistenceUnitInfoProperties;
	}
	
	 @Override
	 public String getPersistenceProviderClassName() {
	        return this.providerClassName;
	  }

	@Override
	public SharedCacheMode getSharedCacheMode() {
		return this.sharedCacheMode;
	}

	@Override
	public PersistenceUnitTransactionType getTransactionType() {
		return this.puTransactionType;
	}

	@Override
	public ValidationMode getValidationMode() {
		return this.validationMode;
	}

	public String toString(){
		
		return "ProviderPersistenceUnitInfo{\n"+
				" persistenceUnitName : "+persistenceUnitName+				
				" | jpaVersion : "+jpaVersion+
				" | providerClassName : "+providerClassName+	
				" | sharedCacheMode : "+sharedCacheMode+	
				" | persistenceUnitTransactionType : "+puTransactionType+	
				" | validationMode : "+validationMode+	
				" | jtaDataSource : "+jtaDataSource+	
				" | nonJtaDataSource : "+nonJtaDataSource+	
				" | dataSourceConfiguration : "+dataSourceConfiguration+
				" \n| persistenceUnitInfoProperties : "+persistenceUnitInfoProperties+				
				"}\n";
	}
}
