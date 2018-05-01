package org.flowr.framework.core.promise.deferred;

import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;

/**
 * Defines ProgressScale as enclosed model that can be used to convey progress along with relevant data & meta data.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ProgressScale implements Scale{

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
			this.setNow(progressScale.getNow());
			this.setPromiseState(progressScale.getPromiseState());
			this.setPromiseStatus(progressScale.getPromiseStatus());
			this.setEventOrigin(progressScale.getEventOrigin());
			this.setINTERVAL(progressScale.getINTERVAL());
			this.setMAX_SCALE(progressScale.getMAX_SCALE());
			this.setMIN_SCALE(progressScale.getMIN_SCALE());
			this.setProgressTimeUnit(progressScale.getProgressTimeUnit());
			this.setScaleUnit(progressScale.getScaleUnit());
			this.setMetaDataAttributes(progressScale.getMetaDataAttributes());
			this.setSubscriptionClientId(progressScale.getSubscriptionClientId());
			this.setNotificationDeliveryType(progressScale.getNotificationDeliveryType());
		}
	}
	
	public boolean equals(Object progressScale){
		
		boolean isEqual = false;
		
		ProgressScale other = (ProgressScale)progressScale;
		
		if(
				other != null &&
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
				this.eventOrigin				== other.eventOrigin
		){
			isEqual = true;
		}
		
		//System.out.println("ProgressScale : this : "+this);
		
		//System.out.println("ProgressScale : other : "+other);
		
		return isEqual;
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
		
		return "\n ProgressScale{"+
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
