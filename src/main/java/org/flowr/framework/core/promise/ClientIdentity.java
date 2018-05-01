package org.flowr.framework.core.promise;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG;

import java.sql.Timestamp;
import java.util.Calendar;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.promise.PromiseTypeClient.ClientType;
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ClientIdentity {

	private String clientApplicationName;	  	
	private Timestamp registrationTime;
	private Timestamp deRegistrationTime;
	private ClientType clientType;
	
	public ClientIdentity(String clientApplicationName,ClientType clientType) throws ConfigurationException{
		
		if(clientApplicationName != null && clientType != null){
			
			this.clientApplicationName  = clientApplicationName;
			this.clientType				= clientType;
			this.registrationTime		= new Timestamp(Calendar.getInstance().getTimeInMillis());
		}else{
			throw new ConfigurationException(
					ERR_CONFIG,
					MSG_CONFIG, 
					"Invalid parameters for ClientIdentity creation. clientApplicationName : "+clientApplicationName+
					" | "+ "ClientType : "+clientType);

		}
		
	}
	
	public ClientType getClientType(){
		return this.clientType;
	}
	
	public String getClientApplicationName() {
		return clientApplicationName;
	}
	public void setClientApplicationName(String clientApplicationName) {
		this.clientApplicationName = clientApplicationName;
	}
	public Timestamp getRegistrationTime() {
		return registrationTime;
	}
	public void setRegistrationTime(Timestamp registrationTime) {
		this.registrationTime = registrationTime;
	}
	public Timestamp getDeRegistrationTime() {
		return deRegistrationTime;
	}
	public void setDeRegistrationTime(Timestamp deRegistrationTime) {
		this.deRegistrationTime = deRegistrationTime;
	}
	
	public boolean equals(ClientIdentity clientIdentity){
		
		boolean isEqual = false;
		
		if(
				this.clientApplicationName.equals(clientIdentity.getClientApplicationName()) &&
				this.clientType.equals(clientIdentity.getClientType())
		){
			isEqual = true;
		}
		
		return isEqual;
	}
	
	public String toString(){
		
		return "ClientIdentity{"+
				" | clientType : "+clientType+	
				" | clientApplicationName : "+clientApplicationName+
				" | registrationTime : "+registrationTime+						
				" | deRegistrationTime : "+deRegistrationTime+	
				"}\n";
	}

	
}
