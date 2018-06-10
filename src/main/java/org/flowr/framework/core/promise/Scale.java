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
		CRITICAL
	}
	
	public class SeverityScale{
		
		private Severity severity = null;
		private double low;
		private double medium;
		private double high;
		private double critical;
		
		public SeverityScale( Severity severity, double val) {
			
			this.severity  = severity;
			
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
		
		public Severity getSeverity() {
			return this.severity;
		}
		
		public double getValueOf(Severity severity) {
			
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
		
		
		public String toString() {
			
			StringBuilder builder = new StringBuilder();
			builder.append(severity);
			
			if(low > 0) {
				builder.append(" : "+low);
			}else if(medium > 0) {
				builder.append(" : "+medium);
			}else if(high > 0) {
				builder.append(" : "+high);
			}else if(critical > 0) {
				builder.append(" : "+critical);
			}
			
			return builder.toString();
		}
	}	
	
	public enum Priority{
		LOW,
		MEDIUM,
		HIGH,
		CRITICAL
	}
	

	
	public class PriorityScale{
		
		private Priority priority = null;
		private double low;
		private double medium;
		private double high;
		private double critical;
		
		public PriorityScale( Priority priority, double val) {
			
			this.priority  = priority;
			
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
		
		public Priority getPriority() {
			return this.priority;
		}
		
		public double getValueOf(Priority priority) {
			
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
		
		
		public String toString() {
			
			StringBuilder builder = new StringBuilder();
			builder.append(priority);
			
			if(low > 0) {
				builder.append(" : "+low);
			}else if(medium > 0) {
				builder.append(" : "+medium);
			}else if(high > 0) {
				builder.append(" : "+high);
			}else if(critical > 0) {
				builder.append(" : "+critical);
			}
			
			return builder.toString();
		}
	}	
	
	public void setSeverityScale(SeverityScale severityScale);
	
	public SeverityScale getSeverityScale();
	
	public void setPriorityScale(PriorityScale priorityScale);
	
	public PriorityScale getPriorityScale();
	
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
