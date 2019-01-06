package org.flowr.framework.core.promise;

import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.node.ha.BackPressureWindow;
import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;

/**
 * Defines RequestScale as enclosed model that can be used to request Target or
 * remote executable to confirm to. However, based on capability of remote 
 * target attributes of ProgressScale will be overriding irrespective of request. 
 * By Default less than 60 Seconds Timeout is not supported for distributed ecosystem.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class RequestScale implements BackPressureWindow{

	private String subscriptionClientId;
	private double MIN_SCALE;
	private double MAX_SCALE;	
	private long timeout;
	private int retryCount;
	private long retryInterval;
	private TimeUnit timeoutTimeUnit;	
	private double scaleUnit;
	private TimeUnit progressTimeUnit;
	private long samplingInterval;
	private NotificationDeliveryType notificationDeliveryType;
	private long defaultTimeout	= 10000;
	
	public boolean isValid(){
		
		boolean isValid = false;
		
		if(subscriptionClientId != null && (timeout >= (defaultTimeout) ) && timeoutTimeUnit != null && 
				retryInterval > 0 &&	notificationDeliveryType != null){
			
			isValid = true;
		}
		
		return isValid;
	}
	
	public String getSubscriptionClientId() {
		return subscriptionClientId;
	}
	public void setSubscriptionClientId(String subscriptionClientId) {
		this.subscriptionClientId = subscriptionClientId;
	}
	
	public double getMIN_SCALE() {
		return MIN_SCALE;
	}
	public void setMIN_SCALE(double mIN_SCALE) {
		MIN_SCALE = mIN_SCALE;
	}
	public double getMAX_SCALE() {
		return MAX_SCALE;
	}
	public void setMAX_SCALE(double mAX_SCALE) {
		MAX_SCALE = mAX_SCALE;
	}
	public long getTimeout() {
		return timeout;
	}
	public void setTimeout(long timeout) {
		this.timeout = timeout;
		if(timeoutTimeUnit != null){
			this.timeout = convert(timeout);
		}
	}
	public int getRetryCount() {
		return retryCount;
	}
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public double getScaleUnit() {
		return scaleUnit;
	}
	public void setScaleUnit(double scaleUnit) {
		this.scaleUnit = scaleUnit;
	}
	public TimeUnit getProgressTimeUnit() {
		return progressTimeUnit;
	}
	public void setProgressTimeUnit(TimeUnit progressTimeUnit) {		
		this.progressTimeUnit = progressTimeUnit;
	}
	public TimeUnit getTimeoutTimeUnit() {
		return timeoutTimeUnit;
	}
	public void setTimeoutTimeUnit(TimeUnit timeoutTimeUnit) {
				
		this.timeoutTimeUnit = timeoutTimeUnit;
		
		if(timeout > 0){
			this.timeout = convert(timeout);
		}
	}
	public long getSamplingInterval() {
		return samplingInterval;
	}
	public void setSamplingInterval(long samplingInterval) {
		this.samplingInterval = samplingInterval;
	}

	public NotificationDeliveryType getNotificationDeliveryType() {
		return notificationDeliveryType;
	}
	public void setNotificationDeliveryType(NotificationDeliveryType notificationDeliveryType) {
		this.notificationDeliveryType = notificationDeliveryType;
	}
	
	public long getRetryInterval() {
		return retryInterval;
	}

	public void setRetryInterval(long retryInterval) {
		this.retryInterval = retryInterval;
	}
	
	private long convert(long v){
		
		long l = 0;
		
		switch(timeoutTimeUnit){
			case DAYS:
				l =  v*24*60*60*1000;
				break;
			case HOURS:
				l =  v*60*60*1000;
				break;
			case MICROSECONDS:
				l =  v/1000000;
				break;
			case MILLISECONDS:
				break;
			case MINUTES:
				l =  v*60*1000;
				break;
			case NANOSECONDS:
				l =  v/1000000000;
				break;
			case SECONDS:
				l =  v*1000;
				break;
			default:
				break;
	
		}
		
		return l;
	}
	
	public String toString(){
		
		return "\n RequestScale{"+
				" | subscriptionClientId : "+subscriptionClientId+
				" | notificationDeliveryType : "+notificationDeliveryType+
				" | MIN_SCALE : "+MIN_SCALE+
				" | MAX_SCALE : "+MAX_SCALE+			
				" | timeout : "+timeout+
				" | retryCount : "+retryCount+
				" | retryInterval : "+retryInterval+
				" | timeoutTimeUnit : "+timeoutTimeUnit+
				" | scaleUnit : "+scaleUnit+
				" | progressTimeUnit : "+progressTimeUnit+
				" | samplingInterval : "+samplingInterval+
				"}";
	}

	public long getDefaultTimeout() {
		return defaultTimeout;
	}


}
