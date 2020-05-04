
/**
 * Concrete implementation of Node process Handler capabilities as a managed process.
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.process.management;

import java.io.InputStream;
import java.io.OutputStream;

import org.flowr.framework.core.exception.ConfigurationException;

public interface NodeProcessHandler extends ProcessHandler{

    Process lookupProcess(String executable) throws ConfigurationException ;
    
    InputStream processIn(String executable) throws ConfigurationException;
    
    OutputStream processOut(String executable) throws ConfigurationException ;
    
    InputStream processError(String executable) throws ConfigurationException;
}
