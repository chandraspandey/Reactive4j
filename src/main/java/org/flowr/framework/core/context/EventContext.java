package org.flowr.framework.core.context;

import org.flowr.framework.core.event.ReactiveMetaData;
import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;
import org.flowr.framework.core.promise.Promise.ScheduleStatus;
import org.flowr.framework.core.service.Service.ServiceType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class EventContext implements Context{

	private ServiceType serviceType = ServiceType.EVENT;
	private String subscriptionClientId;
	private PromiseState promiseState;
	private PromiseStatus promiseStatus;
	private ScheduleStatus scheduleStatus;
	private NotificationProtocolType notificationProtocolType; 
	private NotificationDeliveryType notificationDeliveryType;
	private Object change; 
	private ReactiveMetaData reactiveMetaData;
	
	public String getSubscriptionClientId() {
		return subscriptionClientId;
	}
	public void setSubscriptionClientId(String subscriptionClientId) {
		this.subscriptionClientId = subscriptionClientId;
	}
	public PromiseState getPromiseState() {
		return promiseState;
	}
	public void setPromiseState(PromiseState promiseState) {
		this.promiseState = promiseState;
	}
	public PromiseStatus getPromiseStatus() {
		return promiseStatus;
	}
	public void setPromiseStatus(PromiseStatus promiseStatus) {
		this.promiseStatus = promiseStatus;
	}
	public ScheduleStatus getScheduleStatus() {
		return scheduleStatus;
	}
	public void setScheduleStatus(ScheduleStatus scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}

	public NotificationProtocolType getNotificationProtocolType() {
		return notificationProtocolType;
	}
	public void setNotificationProtocolType(NotificationProtocolType notificationProtocolType) {
		this.notificationProtocolType = notificationProtocolType;
	}

	public Object getChange() {
		return change;
	}
	public void setChange(Object change) {
		this.change = change;
	}
	public ReactiveMetaData getReactiveMetaData() {
		return reactiveMetaData;
	}
	public void setReactiveMetaData(ReactiveMetaData reactiveMetaData) {
		this.reactiveMetaData = reactiveMetaData;
	}
	public NotificationDeliveryType getNotificationDeliveryType() {
		return notificationDeliveryType;
	}
	public void setNotificationDeliveryType(NotificationDeliveryType notificationDeliveryType) {
		this.notificationDeliveryType = notificationDeliveryType;
	}
	
	public ServiceType getServiceType(){
		return serviceType;
	}
	
	public String toString(){
		
		return "EventContext{"+
				" serviceType : "+serviceType+
				" | change : "+change+
				" | promiseState : "+promiseState+
				" | promiseStatus : "+promiseStatus+
				" | scheduleStatus : "+scheduleStatus+
				" | notificationDeliveryType : "+notificationDeliveryType+
				" | notificationProtocolType : "+notificationProtocolType+				
				" | reactiveMetaData : "+reactiveMetaData+				
				"}\n";
	}
}
