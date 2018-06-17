package org.flowr.framework.core.flow.persistence;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.flowr.framework.core.config.DataSourceConfiguration;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class DataSourceProvider implements DataSource{

	private DataSourceConfiguration dataSourceConfiguration;
	private DataSource dataSource = null;
	
	public DataSourceProvider(DataSourceConfiguration dataSourceConfiguration) {
		
		this.dataSourceConfiguration = dataSourceConfiguration;
		
		try {
			dataSource = (DataSource) Class.forName(dataSourceConfiguration
								.getDataSource())
								.getDeclaredConstructor()
								.newInstance(new Object[0]);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	public DataSourceConfiguration getDataSourceConfiguration() {
		return this.dataSourceConfiguration;
	}
	
	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return dataSource.getParentLogger();
	}

	@Override
	public boolean isWrapperFor(Class<?> dbProviderClass) throws SQLException {
		return (dbProviderClass.isInstance(dataSource));
	}

	@Override
	public <T> T unwrap(Class<T> dbProviderClass) throws SQLException {

		return dataSource.unwrap(dbProviderClass);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public Connection getConnection(String arg0, String arg1) throws SQLException {
		return dataSource.getConnection(arg0, arg1);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return dataSource.getLoginTimeout();
	}

	@Override
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		dataSource.setLogWriter(arg0);
	}

	@Override
	public void setLoginTimeout(int arg0) throws SQLException {
		dataSource.setLoginTimeout(arg0);
	}

	public String toString(){
		
		return "ConfiguredDataSource{\n"+
				" dataSource : "+dataSource+	
				" | dataSourceConfiguration : "+dataSourceConfiguration+	
				"}\n";
	}
}
