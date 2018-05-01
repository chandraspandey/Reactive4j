package org.flowr.framework.core.promise;

import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.event.ReactiveMetaData;
import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;

/**
 * Marker interface.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Scale extends ReactiveMetaData{

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
