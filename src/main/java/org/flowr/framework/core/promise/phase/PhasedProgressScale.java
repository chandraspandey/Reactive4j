package org.flowr.framework.core.promise.phase;

import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.phase.PhasedService.ServicePhase;

/**
 * Defines PhasedProgressScale as enclosed model that has Phase 
 * characteristics which can be used for automatic negotiation of execution 
 * capability at future point of time. The StreamValue attribute is used for
 * chunked response transmission in dissemination phase.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class PhasedProgressScale implements Scale{

	private ServicePhase servicePhase 	= ServicePhase.DEFAULT;
	private ChunkValue chunkBuffer		= null;
	
	private String subscriptionClientId;
	private NotificationDeliveryType notificationDeliveryType;
	private PromiseState promiseState;
	private PromiseStatus promiseStatus;
	protected String acknowledgmentIdentifier;
	private double MIN_SCALE;
	private double MAX_SCALE;	
	private double now;
	private double scaleUnit;
	private TimeUnit progressTimeUnit;
	private long INTERVAL;
	private Hashtable<String, String> metaDataAttributes;
	private EventOrigin eventOrigin;
	private SeverityScale severityScale;
	private PriorityScale priorityScale;
	
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
	
	public boolean equals(Object phasedProgressScale){
		
		boolean isEqual = false;
		
		PhasedProgressScale other = (PhasedProgressScale)phasedProgressScale;
		
		if(
				other != null &&
				this.servicePhase 				== other.servicePhase &&
				this.subscriptionClientId		== other.subscriptionClientId &&
				this.acknowledgmentIdentifier 	== other.acknowledgmentIdentifier &&
				this.promiseState 				== other.promiseState && 
				this.promiseStatus 				== other.promiseStatus &&
				this.MIN_SCALE					== other.MIN_SCALE &&
				this.MAX_SCALE					== other.MAX_SCALE &&
				this.now						== other.now &&
				this.scaleUnit					== other.scaleUnit &&
				this.progressTimeUnit 			== other.progressTimeUnit &&
				this.INTERVAL					== other.INTERVAL &&
				this.metaDataAttributes 		== other.metaDataAttributes &&
				this.eventOrigin				== other.eventOrigin &&
				this.priorityScale				== other.priorityScale &&
				this.severityScale				== other.severityScale
		){
			isEqual = true;
		}
		
		return isEqual;
	}
	
	/**
	 * Override the pass by reference values of ProgressScale class
	 * @param phasedProgressScale
	 */
	public void clone(Scale scale){
		
		PhasedProgressScale phasedProgressScale = (PhasedProgressScale)scale;
		
		if(phasedProgressScale != null){
			
			this.setAcknowledgmentIdentifier(phasedProgressScale.getAcknowledgmentIdentifier());
			this.setNow(phasedProgressScale.getNow());
			this.setPromiseState(phasedProgressScale.getPromiseState());
			this.setPromiseStatus(phasedProgressScale.getPromiseStatus());
			this.setEventOrigin(phasedProgressScale.getEventOrigin());
			this.setINTERVAL(phasedProgressScale.getINTERVAL());
			this.setMAX_SCALE(phasedProgressScale.getMAX_SCALE());
			this.setMIN_SCALE(phasedProgressScale.getMIN_SCALE());
			this.setProgressTimeUnit(phasedProgressScale.getProgressTimeUnit());
			this.setScaleUnit(phasedProgressScale.getScaleUnit());
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
		this.subscriptionClientId 	= requestScale.getSubscriptionClientId();
		this.MIN_SCALE 				= requestScale.getMIN_SCALE();
		this.MAX_SCALE 				= requestScale.getMAX_SCALE();
		this.INTERVAL				= requestScale.getSamplingInterval();
		this.progressTimeUnit 		= requestScale.getProgressTimeUnit();
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
	public long getINTERVAL() {
		return INTERVAL;
	}
	public void setINTERVAL(long iNTERVAL) {
		INTERVAL = iNTERVAL;
	}
	public double getNow() {
		return now;
	}
	public void setNow(double now) {
		this.now = now;
	}

	public String getAcknowledgmentIdentifier() {
		return acknowledgmentIdentifier;
	}

	public void setAcknowledgmentIdentifier(String acknowledgmentIdentifier) {
		this.acknowledgmentIdentifier = acknowledgmentIdentifier;
	}
	
	public Hashtable<String, String> getMetaDataAttributes() {
		return this.metaDataAttributes;
	}

	public void setMetaDataAttributes(Hashtable<String, String> metaDataAttributes) {
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
				" | now : "+now+
				" | promiseState : "+promiseState+
				" | promiseStatus : "+promiseStatus+	
				" | acknowledgmentIdentifier : "+acknowledgmentIdentifier+				
				" | MIN_SCALE : "+MIN_SCALE+
				" | MAX_SCALE : "+MAX_SCALE+
				" | scaleUnit : "+scaleUnit+
				" | progressTimeUnit : "+progressTimeUnit+
				" | INTERVAL : "+INTERVAL+
				" | eventOrigin : "+eventOrigin+
				" | metaDataAttributes : "+metaDataAttributes+							
				"}";
	}


}
