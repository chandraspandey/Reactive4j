package org.flowr.framework.core.context;

import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.service.Service.ServiceMode;
import org.flowr.framework.core.service.Service.ServiceState;
import org.flowr.framework.core.service.Service.ServiceStatus;
import org.flowr.framework.core.service.Service.ServiceType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ServerContext implements Context{

	private ServiceType serviceType = ServiceType.SERVER;
	private String subscriptionClientId;
	private ServiceState serviceState;
	private ServiceMode serviceMode;
	private ServiceStatus serviceStatus;
	private NotificationProtocolType notificationProtocolType; 
	private String serverIdentifier;

	public String getSubscriptionClientId() {
		return subscriptionClientId;
	}
	public void setSubscriptionClientId(String subscriptionClientId) {
		this.subscriptionClientId = subscriptionClientId;
	}
	
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	
	public String getServerIdentifier() {
		return serverIdentifier;
	}

	public void setServerIdentifier(String serverIdentifier) {
		this.serverIdentifier = serverIdentifier;
	}
	
	public NotificationProtocolType getNotificationProtocolType() {
		return notificationProtocolType;
	}

	public void setNotificationProtocolType(NotificationProtocolType notificationProtocolType) {
		this.notificationProtocolType = notificationProtocolType;
	}
	
	public ServiceState getServiceState() {
		return serviceState;
	}

	public void setServiceState(ServiceState serviceState) {
		this.serviceState = serviceState;
	}

	public ServiceMode getServiceMode() {
		return serviceMode;
	}

	public void setServiceMode(ServiceMode serviceMode) {
		this.serviceMode = serviceMode;
	}
	
	public ServiceType getServiceType(){
		return serviceType;
	}	
	
	public String toString(){
		
		return "\n ServerContext{"+
				" serviceType : "+serviceType+
				" | subscriptionClientId : "+subscriptionClientId+
				" | serviceState : "+serviceState+
				" | serviceMode : "+serviceMode+
				" | serviceStatus : "+serviceStatus+
				" | serverIdentifier : "+serverIdentifier+	
				" | notificationProtocolType : "+notificationProtocolType+	
				"}";
	}


}
