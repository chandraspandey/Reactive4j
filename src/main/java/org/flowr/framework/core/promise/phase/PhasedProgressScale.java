
/**
 * Defines PhasedProgressScale as enclosed model that has Phase 
 * characteristics which can be used for automatic negotiation of execution 
 * capability at future point of time. The StreamValue attribute is used for
 * chunked response transmission in dissemination phase.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.promise.phase;

import java.util.HashMap;

import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.phase.PhasedService.ServicePhase;

public final class PhasedProgressScale implements Scale{

    private ServicePhase servicePhase   = ServicePhase.DEFAULT;
    private ChunkValue chunkBuffer;
    
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
    public boolean equals(Object phasedProgressScale){
        
        boolean isEqual = false;
        
        if( phasedProgressScale instanceof PhasedProgressScale) {
        
            PhasedProgressScale other = (PhasedProgressScale)phasedProgressScale;
            
            if(                 
                    this.servicePhase               == other.servicePhase &&
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
    public int hashCode() {
        
        return subscriptionClientId.hashCode();
        
    }

    
    /**
     * Override the pass by reference values of ProgressScale class
     * @param phasedProgressScale
     */
    public void clone(Scale scale){
        
        PhasedProgressScale phasedProgressScale = (PhasedProgressScale)scale;
        
        if(phasedProgressScale != null){
            
            this.setAcknowledgmentIdentifier(phasedProgressScale.getAcknowledgmentIdentifier());
            this.setScaleOption(phasedProgressScale.getScaleOption());
            this.setPromiseState(phasedProgressScale.getPromiseState());
            this.setPromiseStatus(phasedProgressScale.getPromiseStatus());
            this.setEventOrigin(phasedProgressScale.getEventOrigin());
            this.setMetaDataAttributes(phasedProgressScale.getMetaDataAttributes());
            this.setChunkBuffer(phasedProgressScale.getChunkBuffer());
            this.setServicePhase(phasedProgressScale.getServicePhase());    
            this.setSubscriptionClientId(phasedProgressScale.getSubscriptionClientId());
            this.setNotificationDeliveryType(phasedProgressScale.getNotificationDeliveryType());
            this.setPriorityScale(phasedProgressScale.getPriorityScale());
            this.setSeverityScale(phasedProgressScale.getSeverityScale());
        }
        
    }
                
    
    public ServicePhase getServicePhase() {
        return servicePhase;
    }
    
    public void setServicePhase(ServicePhase servicePhase) {        
        this.servicePhase = servicePhase;       
    }

    public ChunkValue getChunkBuffer() {
        return chunkBuffer;
    }

    public void setChunkBuffer(ChunkValue chunkBuffer) {
        this.chunkBuffer = chunkBuffer;
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
        
        return "\n PhasedProgressScale{"+
                " | servicePhase : "+servicePhase+" | "+
                " | severityScale : "+severityScale+
                " | priorityScale : "+priorityScale+
                " | chunkBuffer : "+chunkBuffer+" | "+
                " | subscriptionClientId : "+subscriptionClientId+
                " | promiseState : "+promiseState+
                " | promiseStatus : "+promiseStatus+    
                " | acknowledgmentIdentifier : "+acknowledgmentIdentifier+              
                " | eventOrigin : "+eventOrigin+
                " | metaDataAttributes : "+metaDataAttributes+
                " |"+scaleOption+
                "}";
    }


}
