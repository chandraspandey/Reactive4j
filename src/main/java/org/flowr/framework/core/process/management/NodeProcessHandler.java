package org.flowr.framework.core.process.management;

import java.io.InputStream;
import java.io.OutputStream;

import org.flowr.framework.core.exception.ConfigurationException;

/**
 * Concrete implementation of Node process Handler capabilities as a managed process.
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NodeProcessHandler extends ProcessHandler{

	public Process lookupProcess(String executable) throws ConfigurationException ;
	
	public InputStream processIn(String executable) throws ConfigurationException;
	
	public OutputStream processOut(String executable) throws ConfigurationException ;
	
	public InputStream processError(String executable) throws ConfigurationException;
}
