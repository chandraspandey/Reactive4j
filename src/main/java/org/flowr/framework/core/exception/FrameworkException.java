package org.flowr.framework.core.exception;

/**
 * Extends Exception to provide framework specific instrumentation. Provides additional instrumentation for linking the
 * input for which exception can happen which can be used exception chaining kind of scenarios.
 * @author Chandra Pandey
 *
 */

public class FrameworkException extends Exception{

	private static final long serialVersionUID = 1L;

	private int errorCode;
	private String errorMessage;
	private String contextMessage;
	private Object input;
	
	public FrameworkException(int errorCode,String errorMessage,String 
		contextMessage){
		super(errorMessage);
		this.errorCode		= errorCode;
		this.errorMessage 	= errorMessage;
		this.contextMessage = contextMessage;
	}
	
	public Object getInput() {
		return input;
	}

	public void setInput(Object input) {
		this.input = input;
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
