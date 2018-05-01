package org.flowr.framework.core.context;

import java.util.HashSet;

import org.flowr.framework.core.notification.NotificationRoute;
import org.flowr.framework.core.notification.NotificationServiceAdapter;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.service.Service.ServiceType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class RouteContext implements Context{

	private ServiceType serviceType = ServiceType.EVENT;
	private HashSet<NotificationRoute<NotificationServiceAdapter,NotificationProtocolType>> routeSet;
	
	public HashSet<NotificationRoute<NotificationServiceAdapter, NotificationProtocolType>> getRouteSet() {
		return routeSet;
	}
	public void setRouteSet(HashSet<NotificationRoute<NotificationServiceAdapter, NotificationProtocolType>> routeSet) {
		this.routeSet = routeSet;
	}
	
	public ServiceType getServiceType(){
		return serviceType;
	}
	
	public String toString(){
		
		return "RouteContext{"+
			" serviceType : "+serviceType+
			" | routeSet : "+routeSet+
			" | }\n";
	}
}