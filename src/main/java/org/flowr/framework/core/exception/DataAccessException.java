package org.flowr.framework.core.exception;

public class DataAccessException extends FrameworkException{

	private static final long serialVersionUID = 1L;
	
	public DataAccessException(int errorCode, String errorMessage, String contextMessage) {
		super(errorCode, errorMessage, contextMessage);
	}
	
}
