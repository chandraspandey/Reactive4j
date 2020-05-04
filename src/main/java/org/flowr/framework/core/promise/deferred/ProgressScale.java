
/**
 * Defines ProgressScale as enclosed model that can be used to convey progress along with relevant data & meta data.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.promise.deferred;

import java.util.HashMap;

import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;

public class ProgressScale implements Scale{

    private String subscriptionClientId;
    private NotificationDeliveryType notificationDeliveryType;
    private PromiseState promiseState;
    private PromiseStatus promiseStatus;
    protected String acknowledgmentIdentifier;
    private HashMap<String, String> metaDataAttributes;
    private EventOrigin eventOrigin;
    private SeverityScale severityScale;
    private PriorityScale priorityScale;
    private ScaleOption scaleOption;
    
    public void setScaleOption(ScaleOption scaleOption) {
        
        this.scaleOption = scaleOption;
    }
    
    public ScaleOption getScaleOption() {
        
        return this.scaleOption;
    }
    
    public void setSeverityScale(SeverityScale severityScale) {
        this.severityScale = severityScale;
    }
    
    public SeverityScale getSeverityScale() {
        return this.severityScale;
    }
    
    public void setPriorityScale(PriorityScale priorityScale) {
        this.priorityScale = priorityScale;
    }
    
    public PriorityScale getPriorityScale() {
        return this.priorityScale;
    }
    
    public String getSubscriptionClientId() {
        return subscriptionClientId;
    }
    public void setSubscriptionClientId(String subscriptionClientId) {
        this.subscriptionClientId = subscriptionClientId;
    }
    
    public NotificationDeliveryType getNotificationDeliveryType() {
        return notificationDeliveryType;
    }
    public void setNotificationDeliveryType(NotificationDeliveryType notificationDeliveryType) {
        this.notificationDeliveryType = notificationDeliveryType;
    }
    
    public void clone(Scale scale){
        
        ProgressScale progressScale = (ProgressScale)scale; 
        
        if(progressScale != null){
            
            this.setAcknowledgmentIdentifier(progressScale.getAcknowledgmentIdentifier());
            this.setScaleOption(progressScale.getScaleOption());
            this.setPromiseState(progressScale.getPromiseState());
            this.setPromiseStatus(progressScale.getPromiseStatus());
            this.setEventOrigin(progressScale.getEventOrigin());
            this.setMetaDataAttributes(progressScale.getMetaDataAttributes());
            this.setSubscriptionClientId(progressScale.getSubscriptionClientId());
            this.setNotificationDeliveryType(progressScale.getNotificationDeliveryType());
            this.setPriorityScale(progressScale.getPriorityScale());
            this.setSeverityScale(progressScale.getSeverityScale());
        }
    }
    
     @Override
     public int hashCode() {
         
         return subscriptionClientId.hashCode();
     }
    
    public boolean equals(Object progressScale){
        
        boolean isEqual = false;
        
        
        
        if(progressScale != null && progressScale.getClass() == this.getClass()) {
        
            ProgressScale other = (ProgressScale)progressScale;
            
            if(
                    this.subscriptionClientId       == other.subscriptionClientId &&
                    this.acknowledgmentIdentifier   == other.acknowledgmentIdentifier &&
                    this.promiseState               == other.promiseState && 
                    this.promiseStatus              == other.promiseStatus &&
                    this.scaleOption                == other.scaleOption &&
                    this.metaDataAttributes         == other.metaDataAttributes &&
                    this.eventOrigin                == other.eventOrigin &&
                    this.priorityScale              == other.priorityScale &&
                    this.severityScale              == other.severityScale
            ){
                isEqual = true;
            }
        }
            
        return isEqual;
    }
    
    public void acceptIfApplicable(RequestScale requestScale){
        
        // validate business capabilities
        this.subscriptionClientId   = requestScale.getSubscriptionClientId();
        this.scaleOption.clone(requestScale.getScaleOption());
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
    
    public String getAcknowledgmentIdentifier() {
        return acknowledgmentIdentifier;
    }

    public void setAcknowledgmentIdentifier(String acknowledgmentIdentifier) {
        this.acknowledgmentIdentifier = acknowledgmentIdentifier;
    }
    
    public HashMap<String, String> getMetaDataAttributes() {
        return this.metaDataAttributes;
    }

    public void setMetaDataAttributes(HashMap<String, String> metaDataAttributes) {
        this.metaDataAttributes = metaDataAttributes;
    }

    public void setEventOrigin(EventOrigin eventOrigin) {
        this.eventOrigin = eventOrigin;
    }

    public EventOrigin getEventOrigin() {
        return this.eventOrigin;
    }

    public String toString(){
        
        return "\n ProgressScale{"+
                " | subscriptionClientId : "+subscriptionClientId+
                " | severityScale : "+severityScale+
                " | priorityScale : "+priorityScale+                
                " | promiseState : "+promiseState+
                " | promiseStatus : "+promiseStatus+    
                " | acknowledgmentIdentifier : "+acknowledgmentIdentifier+              
                " | eventOrigin : "+eventOrigin+
                " | metaDataAttributes : "+metaDataAttributes+
                " | "+scaleOption+
                "}";
    }


}
