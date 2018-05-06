package org.flowr.framework.core.notification.subscription;

import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.notification.Notification.NotificationFormatType;
import org.flowr.framework.core.notification.Notification.NotificationFrequency;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.Notification.NotificationType;

/**
 * Defines NotificationSubscription as logical identity for:
 * - Addressing 1 : N Subscription for different clients
 * - Addressing 1 : N Subscription for different NotificationProtocolType
 * - Addressing differential treatment for SubscriptionType i.e.  CLIENT & SERVER
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NotificationSubscription {
	
	/*
	 * CLIENT : Maps to NotificationDeliveryType as EXTERNAL for Processing
	 * SERVER : Maps to NotificationDeliveryType as INTERNAL for Processing
	 */
	public enum SubscriptionType{
		CLIENT,
		SERVER
	}
	
	/*
	 * REQUESTED : Subscription requested but not yet subscribed
	 * SUBSCRIBED : Subscription successfull
	 * ALREADY_SUBSCRIBED : Subscription already exists
	 * UNSUBSCRIBED : Subscription successfull
	 * REJECTED : Represents the status where criteria for Subscription is not met & rejected
	 */
	public enum SubscriptionStatus{
		REQUESTED,
		SUBSCRIBED,
		ALREADY_SUBSCRIBED,
		UNSUBSCRIBED,
		REJECTED,
		CHANGED
	}
	
	public enum SubscriptionOption{
		AUTOMATIC,
		WORKFLOW
	}
	
	public String getSubscriptionId();
	
	public void setSubscriptionId(String subscriptionId);
	
	public void setSubscriptionOption(SubscriptionOption subscriptionOption);
	
	public SubscriptionOption getSubscriptionOption();

	public void setSubscriptionType(SubscriptionType subscriptionType);
	
	public SubscriptionType getSubscriptionType();
	
	public void setNotificationDeliveryType(NotificationDeliveryType notificationDeliveryType);
	
	public NotificationDeliveryType getNotificationDeliveryType();
	
	public void setNotificationFrequency(NotificationFrequency frequency);

	public NotificationFrequency getNotificationFrequency();

	void setNotificationType(NotificationType notificationType);

	public NotificationType getNotificationType();

	public NotificationFormatType getNotificationFormatType();

	public void setNotificationFormatType(NotificationFormatType notificationFormatType);

	public NotificationProtocolType getNotificationProtocolType();

	public void setNotificationProtocolType(NotificationProtocolType notificationProtocolType);
}
