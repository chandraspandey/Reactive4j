package org.flowr.framework.core.exception;

/**
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public class AccessSecurityException extends Exception{

	private static final long serialVersionUID = 1L;

	private String warningMessage;
	private String securityMessage;
	
	public AccessSecurityException(int errorCode,String errorMessage,String 
		contextMessage){
		//super(errorCode,errorMessage,contextMessage);
	}
	
	public String getWarningMessage() {
		return warningMessage;
	}

	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}

	public String getSecurityMessage() {
		return securityMessage;
	}

	public void setSecurityMessage(String securityMessage) {
		this.securityMessage = securityMessage;
	}
	
	public String getLocalizedMessage(){		
		
		return super.toString()+" : "+warningMessage+" : "+securityMessage;		
	}


}
