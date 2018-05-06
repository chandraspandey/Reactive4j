package org.flowr.framework.core.promise;

import org.flowr.framework.core.context.PromiseContext;
import org.flowr.framework.core.security.ClientIdentity;
import org.flowr.framework.core.service.ServiceRequest;

/**
 * Defines Model which encloses ProgressScale & Promisable. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class PromiseRequest<REQUEST,RESPONSE> implements ServiceRequest{

	private boolean isTimelineRequired;
	
	// This is populated only when target invocation is successfull & returned as part ProgressScale
	private String acknowledgmentIdentifier;	
	private PromiseContext<REQUEST> promiseContext;
	private RequestType requestType;
	private ClientIdentity clientIdentity;

	public boolean isTimelineRequired() {
		return isTimelineRequired;
	}

	public void setTimelineRequired(boolean isTimelineRequired) {
		this.isTimelineRequired = isTimelineRequired;
	}	

	public String getAcknowledgmentIdentifier() {
		return acknowledgmentIdentifier;
	}

	public void setAcknowledgmentIdentifier(String acknowledgmentIdentifier) {
		this.acknowledgmentIdentifier = acknowledgmentIdentifier;
	}
	
	public PromiseContext<REQUEST> getPromiseContext() {
		return promiseContext;
	}

	public void setPromiseContext(PromiseContext<REQUEST> promiseContext) {
		this.promiseContext = promiseContext;
	}
	
	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}
	
	public ClientIdentity getClientIdentity() {
		return clientIdentity;
	}

	public void setClientIdentity(ClientIdentity clientIdentity) {
		this.clientIdentity = clientIdentity;
	}

	public String toString(){
		
		return "\n PromiseRequest{"+
				"\n clientIdentity : "+clientIdentity+
				"\n requestType : "+requestType+	
				"\n isTimelineRequired : "+isTimelineRequired+						
				"\n promiseContext : "+promiseContext+
				"\n acknowledgmentIdentifier : "+acknowledgmentIdentifier+		
				"}\n";
	}



}
