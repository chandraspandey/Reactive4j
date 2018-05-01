package org.flowr.framework.core.context;

import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.service.Service.ServiceType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class PromiseContext<REQUEST> implements Context{

	private ServiceType serviceType = ServiceType.PROMISE;
	private RequestScale requestScale;
	private boolean isTimelineRequired;
	private REQUEST deferableRequest;
	
	public RequestScale getRequestScale() {
		return requestScale;
	}
	public void setRequestScale(RequestScale requestScale) {
		this.requestScale = requestScale;
	}
	public REQUEST getDeferableRequest() {
		return deferableRequest;
	}
	public void setDeferableRequest(REQUEST deferableRequest) {
		this.deferableRequest = deferableRequest;
	}
	public boolean isTimelineRequired() {
		return isTimelineRequired;
	}
	public void setTimelineRequired(boolean isTimelineRequired) {
		this.isTimelineRequired = isTimelineRequired;
	}

	public ServiceType getServiceType(){
		return serviceType;
	}
	
	public String toString(){
		
		return "PromiseContext{ serviceType : "+serviceType+ " | requestScale: "+requestScale+" | isTimelineRequired : "
				+isTimelineRequired+" | deferableRequest : "+deferableRequest;
	}
}
