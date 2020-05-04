
/**
 * Marker interface.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.promise;

import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.event.ReactiveMetaData;
import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;



public interface Scale extends ReactiveMetaData{

    void setScaleOption(ScaleOption scaleOption);
    
    ScaleOption getScaleOption();
    
    public enum Severity{
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }
    
    public class ScaleOption{
        
        private double minScale;
        private double maxScale;    
        private double now;
        private double scaleUnit;
        private TimeUnit progressTimeUnit;
        private long interval;
        
        public ScaleOption() {
            
        }
        
        /**
         * Updates all the attributes, typically from RequestScale except now. 
         * @param other
         */
        public ScaleOption(ScaleOption other) {
            this.minScale           = other.minScale;
            this.maxScale           = other.maxScale;
            this.scaleUnit          = other.scaleUnit;
            this.progressTimeUnit   = other.progressTimeUnit;
            this.interval           = other.interval;
        }
        
        /**
         * Updates all the attributes, typically from RequestScale except now. 
         * @param other
         */
        public void clone(ScaleOption other) {
            this.minScale           = other.minScale;
            this.maxScale           = other.maxScale;
            this.scaleUnit          = other.scaleUnit;
            this.progressTimeUnit   = other.progressTimeUnit;
            this.interval           = other.interval;
        }
        
        public double getMinScale() {
            return minScale;
        }
        public void setMinScale(double minScale) {
            this.minScale = minScale;
        }
        public double getMaxScale() {
            return maxScale;
        }
        public void setMaxScale(double maxScale) {
            this.maxScale = maxScale;
        }
        public double getNow() {
            return now;
        }
        public void setNow(double now) {
            this.now = now;
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
        public long getInterval() {
            return interval;
        }
        public void setInterval(long interval) {
            this.interval = interval;
        }
        
        public String toString(){
            
            return "\n ScaleOption{"+
                    " | now : "+now+          
                    " | minScale : "+minScale+
                    " | maxScale : "+maxScale+
                    " | scaleUnit : "+scaleUnit+
                    " | progressTimeUnit : "+progressTimeUnit+
                    " | interval : "+interval+
                    "}";
        }
        
    }
    
    public class SeverityScale{
        
        private Severity severity;
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
            }else {
                builder.append("");
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
        
        private Priority priority;
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
            }else {
                builder.append(" . ");
            }
            
            return builder.toString();
        }
    }   
    
    void setSeverityScale(SeverityScale severityScale);
    
    SeverityScale getSeverityScale();
    
    void setPriorityScale(PriorityScale priorityScale);
    
    PriorityScale getPriorityScale();
    
    String getAcknowledgmentIdentifier();

    void setAcknowledgmentIdentifier(String acknowledgmentIdentifier);
    
    PromiseState getPromiseState();
    
    void setPromiseState(PromiseState promiseState);
    
    PromiseStatus getPromiseStatus();
    
    void setPromiseStatus(PromiseStatus promiseStatus) ;
    
    void clone(Scale progressScale);
    
    void acceptIfApplicable(RequestScale requestScale);
    
    String getSubscriptionClientId() ;
    void setSubscriptionClientId(String subscriptionClientId);
    
    NotificationDeliveryType getNotificationDeliveryType();
    void setNotificationDeliveryType(NotificationDeliveryType notificationDeliveryType) ;

}
