package org.flowr.framework.core.promise;

import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.event.ReactiveMetaData;
import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;

/**
 * Marker interface.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Scale extends ReactiveMetaData{

	public enum Severity{
		LOW,
		MEDIUM,
		HIGH,
		CRITICAL;
		
		private static double low;
		private static double medium;
		private static double high;
		private static double critical;
		
		public static void setValueOf( Severity severity, double val) {
			
			switch(severity) {
				
				case CRITICAL:{
					critical = val;
					break;
				}case HIGH:{
					high = val;
					break;
				}case LOW:{
					low = val;
					break;
				}case MEDIUM:{
					medium = val;
					break;
				}default:{
					break;
				}				
			}
		}
		
		public static double getValueOf( Severity severity) {
			
			double val = 0.0;
			
			switch(severity) {
				
				case CRITICAL:{
					val = critical;
					break;
				}case HIGH:{
					val = high;
					break;
				}case LOW:{
					val = low;
					break;
				}case MEDIUM:{
					val = medium;
					break;
				}default:{
					break;
				}				
			}			
			return val;
		}		
	}
	
	public enum Priority{
		LOW,
		MEDIUM,
		HIGH,
		CRITICAL;
		
		private static double low;
		private static double medium;
		private static double high;
		private static double critical;
		
		public void setValueOf( Priority priority, double val) {
			
			switch(priority) {
				
				case CRITICAL:{
					critical = val;
					break;
				}case HIGH:{
					high = val;
					break;
				}case LOW:{
					low = val;
					break;
				}case MEDIUM:{
					medium = val;
					break;
				}default:{
					break;
				}				
			}
		}
		
		public static double getValueOf(Priority priority) {
			
			double val = 0.0;
			
			switch(priority) {
				
				case CRITICAL:{
					val = critical;
					break;
				}case HIGH:{
					val = high;
					break;
				}case LOW:{
					val = low;
					break;
				}case MEDIUM:{
					val = medium;
					break;
				}default:{
					break;
				}				
			}
			
			return val;
		}
	}
	
	public void setSeverity(Severity severity);
	
	public Severity getSeverity();
	
	public void setPriority(Priority priority);
	
	public Priority getPriority();
	
	public String getAcknowledgmentIdentifier();

	public void setAcknowledgmentIdentifier(String acknowledgmentIdentifier);
	
	public PromiseState getPromiseState();
	
	public void setPromiseState(PromiseState promiseState);
	
	public PromiseStatus getPromiseStatus();
	
	public void setPromiseStatus(PromiseStatus promiseStatus) ;
	
	public void clone(Scale progressScale);
	
	public void acceptIfApplicable(RequestScale requestScale);
	
	public String getSubscriptionClientId() ;
	public void setSubscriptionClientId(String subscriptionClientId);
	
	public NotificationDeliveryType getNotificationDeliveryType();
	public void setNotificationDeliveryType(NotificationDeliveryType notificationDeliveryType) ;
	
	public double getMIN_SCALE();
	public void setMIN_SCALE(double mIN_SCALE);
	public double getMAX_SCALE();
	public void setMAX_SCALE(double mAX_SCALE);

	public double getScaleUnit();
	public void setScaleUnit(double scaleUnit);
	public TimeUnit getProgressTimeUnit();
	public void setProgressTimeUnit(TimeUnit progressTimeUnit);
	public long getINTERVAL() ;
	public void setINTERVAL(long iNTERVAL) ;
	public double getNow();
	public void setNow(double now);
}
