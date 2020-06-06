
/**
 * Defines ScheduledProgressScale as enclosed model that has Time/Calendar characteristics which can be used for 
 * automatic negotiation of execution at future point of time.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.promise.scheduled;

import java.sql.Timestamp;
import java.util.HashMap;

import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;
import org.flowr.framework.core.promise.Promise.ScheduleStatus;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;

public final class ScheduledProgressScale implements Scale{

    private ScheduleStatus scheduleStatus; 
    private Timestamp scheduledTimestamp;
    private Timestamp deferredTimestamp;
    
    private String subscriptionClientId;
    private NotificationDeliveryType notificationDeliveryType;
    private PromiseState promiseState;
    private PromiseStatus promiseStatus;
    private String acknowledgmentIdentifier;

    private HashMap<String, String> metaDataAttributes;
    private EventOrigin eventOrigin;
    private SeverityScale severityScale;
    private PriorityScale priorityScale;
    private ScaleOption scaleOption;
    
    public boolean isValid(){
        
        boolean isValid = false;
        
        if(subscriptionClientId != null &&  notificationDeliveryType != null){
            
            isValid = true;
        }
        
        return isValid;
    }
    
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
    
    @Override
     public int hashCode() {
         
         return subscriptionClientId.hashCode();
     }
    
    public boolean equals(Object scheduledProgressScale){
        
        boolean isEqual = false;        
        
        if(scheduledProgressScale instanceof ScheduledProgressScale) {
            
            ScheduledProgressScale other = (ScheduledProgressScale)scheduledProgressScale;
        
            if(
                    
                    this.scheduleStatus     == other.scheduleStatus &&
                    this.scheduledTimestamp == other.scheduledTimestamp &&
                    this.deferredTimestamp  == other.deferredTimestamp &&
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
    
    @Override
    public void clone(Scale progressScale) {
        
        ScheduledProgressScale scheduledProgressScale = (ScheduledProgressScale)progressScale;
        
        if(scheduledProgressScale != null){
            this.setAcknowledgmentIdentifier(scheduledProgressScale.getAcknowledgmentIdentifier());
            this.setScaleOption(scheduledProgressScale.getScaleOption());
            this.setPromiseState(scheduledProgressScale.getPromiseState());
            this.setPromiseStatus(scheduledProgressScale.getPromiseStatus());
            this.setEventOrigin(scheduledProgressScale.getEventOrigin());
            this.setMetaDataAttributes(scheduledProgressScale.getMetaDataAttributes());
            this.setScheduleStatus(scheduledProgressScale.getScheduleStatus());
            this.setScheduledTimestamp(scheduledProgressScale.getScheduledTimestamp());
            this.setDeferredTimestamp(scheduledProgressScale.getDeferredTimestamp());
            this.setSubscriptionClientId(scheduledProgressScale.getSubscriptionClientId());
            this.setNotificationDeliveryType(scheduledProgressScale.getNotificationDeliveryType());
        }
    }
    
    /**
     *  Override the pass by reference values of ProgressScale base class
     * @param scheduledProgressScale
     */
    public void clone(ScheduledProgressScale scheduledProgressScale){
        
        this.setAcknowledgmentIdentifier(scheduledProgressScale.getAcknowledgmentIdentifier());
        this.setScaleOption(scheduledProgressScale.getScaleOption());
        this.setPromiseState(scheduledProgressScale.getPromiseState());
        this.setPromiseStatus(scheduledProgressScale.getPromiseStatus());
        this.setEventOrigin(scheduledProgressScale.getEventOrigin());
        this.setMetaDataAttributes(scheduledProgressScale.getMetaDataAttributes());        
        this.setScheduleStatus(scheduledProgressScale.getScheduleStatus());
        this.setScheduledTimestamp(scheduledProgressScale.getScheduledTimestamp());
        this.setDeferredTimestamp(scheduledProgressScale.getDeferredTimestamp());
        this.setSubscriptionClientId(scheduledProgressScale.getSubscriptionClientId());
        this.setNotificationDeliveryType(scheduledProgressScale.getNotificationDeliveryType());
        this.setPriorityScale(scheduledProgressScale.getPriorityScale());
        this.setSeverityScale(scheduledProgressScale.getSeverityScale());

    }

    public ScheduleStatus getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(ScheduleStatus scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public Timestamp getScheduledTimestamp() {
        return scheduledTimestamp;
    }

    public void setScheduledTimestamp(Timestamp scheduledTimestamp) {
        this.scheduledTimestamp = scheduledTimestamp;
    }

    public Timestamp getDeferredTimestamp() {
        return deferredTimestamp;
    }

    public void setDeferredTimestamp(Timestamp deferredTimestamp) {
        this.deferredTimestamp = deferredTimestamp;
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
        
        return "\n ScheduledProgressScale{"+
                " | scheduleStatus : "+scheduleStatus+
                " | severityScale : "+severityScale+
                " | priorityScale : "+priorityScale+
                " | scheduledTimestamp : "+scheduledTimestamp+
                " | deferredTimestamp : "+deferredTimestamp+
                " | subscriptionClientId : "+subscriptionClientId+
                " | promiseState : "+promiseState+
                " | promiseStatus : "+promiseStatus+    
                " | acknowledgmentIdentifier : "+acknowledgmentIdentifier+              
                " | eventOrigin : "+eventOrigin+
                " | metaDataAttributes : "+metaDataAttributes+
                " | "+scaleOption+
                "}";
    }


}
