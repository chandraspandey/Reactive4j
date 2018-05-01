package org.flowr.framework.core.exception;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ConfigurationException extends Exception{

	private static final long serialVersionUID = 1L;

	private int errorCode;
	private String errorMessage;
	private String contextMessage;
	
	public ConfigurationException(int errorCode,String errorMessage,String 
		contextMessage){
		super(errorMessage);
		this.errorCode		= errorCode;
		this.errorMessage 	= errorMessage;
		this.contextMessage = contextMessage;
	}
	
	
	public String getLocalizedMessage(){		
		
		return errorCode+" : "+errorMessage+" : "+contextMessage;		
	}
	

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getContextMessage() {
		return contextMessage;
	}

	public void setContextMessage(String contextMessage) {
		this.contextMessage = contextMessage;
	}

}
