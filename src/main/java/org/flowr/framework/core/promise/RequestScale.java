
/**
 * Defines RequestScale as enclosed model that can be used to request Target or
 * remote executable to confirm to. However, based on capability of remote 
 * target attributes of ProgressScale will be overriding irrespective of request. 
 * By Default less than 60 Seconds Timeout is not supported for distributed ecosystem.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.promise;

import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.node.ha.BackPressureWindow;
import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.promise.Scale.ScaleOption;

public class RequestScale implements BackPressureWindow{

    private String subscriptionClientId;
    private long timeout;
    private int retryCount;
    private long retryInterval;
    private TimeUnit timeoutTimeUnit;   
    private NotificationDeliveryType notificationDeliveryType;
    private long defaultTimeout = 10000;    
    private ScaleOption scaleOption;
    
    public void setScaleOption(ScaleOption scaleOption) {
        
        this.scaleOption = scaleOption;
    }
    
    public ScaleOption getScaleOption() {
        
        return this.scaleOption;
    }
    
    public boolean isValid(){
        
        boolean isValid = false;
        
        if(subscriptionClientId != null && isValidTimeout() &&  notificationDeliveryType != null){
            
            isValid = true;
        }
        
        return isValid;
    }
    
    
    private boolean isValidTimeout() {
        
        return ((timeout >= (defaultTimeout) ) && timeoutTimeUnit != null && retryInterval > 0);
    }
    
    public String getSubscriptionClientId() {
        return subscriptionClientId;
    }
    public void setSubscriptionClientId(String subscriptionClientId) {
        this.subscriptionClientId = subscriptionClientId;
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

    public TimeUnit getTimeoutTimeUnit() {
        return timeoutTimeUnit;
    }
    public void setTimeoutTimeUnit(TimeUnit timeoutTimeUnit) {
                
        this.timeoutTimeUnit = timeoutTimeUnit;
        
        if(timeout > 0){
            this.timeout = convert(timeout);
        }
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
    
    public long getDefaultTimeout() {
        return defaultTimeout;
    }
    
    public String toString(){
        
        return "\n RequestScale{"+
                " | subscriptionClientId : "+subscriptionClientId+
                " | notificationDeliveryType : "+notificationDeliveryType+
                " | timeout : "+timeout+
                " | retryCount : "+retryCount+
                " | retryInterval : "+retryInterval+
                " | timeoutTimeUnit : "+timeoutTimeUnit+
                " | scaleOption : "+scaleOption+
                "}";
    }

 


}
