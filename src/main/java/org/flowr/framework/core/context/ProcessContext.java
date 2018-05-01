package org.flowr.framework.core.context;

import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.service.Service.ServiceType;
import org.flowr.framework.core.service.extension.PromiseService.PromiseServerState;
import org.flowr.framework.core.service.extension.PromiseService.PromiseServerStatus;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ProcessContext implements Context{

	private ServiceType serviceType = ServiceType.PROCESS;
	private String serverIdentifier;
	private PromiseServerState serverState ;
	private PromiseServerStatus serverStatus ;	
	private String processIdentifier;
	private String serviceIdentifier;
	private NotificationDeliveryType notificationDeliveryType;
	private NotificationProtocolType notificationProtocolType; 
		
	public String getServerIdentifier() {
		return serverIdentifier;
	}
	public void setServerIdentifier(String serverIdentifier) {
		this.serverIdentifier = serverIdentifier;
	}
	
	public PromiseServerState getServerState() {
		return serverState;
	}
	public void setServerState(PromiseServerState serverState) {
		this.serverState = serverState;
	}
	public PromiseServerStatus getServerStatus() {
		return serverStatus;
	}
	public void setServerStatus(PromiseServerStatus serverStatus) {
		this.serverStatus = serverStatus;
	}

	public String getProcessIdentifier() {
		return processIdentifier;
	}
	public void setProcessIdentifier(String processIdentifier) {
		this.processIdentifier = processIdentifier;
	}
	
	public String getServiceIdentifier() {
		return serviceIdentifier;
	}
	public void setServiceIdentifier(String serviceIdentifier) {
		this.serviceIdentifier = serviceIdentifier;
	}
	public NotificationDeliveryType getNotificationDeliveryType() {
		return notificationDeliveryType;
	}
	public void setNotificationDeliveryType(NotificationDeliveryType notificationDeliveryType) {
		this.notificationDeliveryType = notificationDeliveryType;
	}
	
	public NotificationProtocolType getNotificationProtocolType() {
		return notificationProtocolType;
	}
	public void setNotificationProtocolType(NotificationProtocolType notificationProtocolType) {
		this.notificationProtocolType = notificationProtocolType;
	}
	
	public ServiceType getServiceType(){
		return serviceType;
	}
	
	public String toString(){
		
		return "\n ProcessContext{"+
				" serviceType : "+serviceType+
				" | serverIdentifier : "+serverIdentifier+
				" | processorState : "+serverState+
				" | processorStatus : "+serverStatus+	
				" | notificationDeliveryType : "+notificationDeliveryType+
				" | notificationProtocolType : "+notificationProtocolType+
				" | processIdentifier : "+processIdentifier+
				" | serviceIdentifier : "+serviceIdentifier+
				"}";
	}




}
