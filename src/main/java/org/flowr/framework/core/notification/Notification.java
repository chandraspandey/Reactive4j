package org.flowr.framework.core.notification;

import org.flowr.framework.core.model.Model;

/**
 * Defines the Notification characteristics to be applied for Notification purpose. Concerete implementation of 
 * notification should be based on the defined characteristics.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Notification extends Model{

	public enum NotificationFrequency{
		ALL,
		ONCE,
		WEEKLY,
		FORTNIGHTLY,
		MONTHLY,
		YEARLY
	}
	
	public enum NotificationType{
		TOPIC,
		QUEUE,
		AMQP
	}
	
	public enum NotificationFormatType{
		TEXT,
		BYTE,
		MAP,
		OBJECT
	}
	
	public interface NotificationProtocolType{
		
	}
	
	/**
	 * INFORMATION : When NotificationDeliveryType is marked as INTERNAL the event would be published as INFORMATION.
	 * Restricted for Server usage. Its meant for internal processing classification of framework and external app
	 * should not be allowed for subscription. The uSage is resticted for framework only.
	 * ALL :  Is defined for Server monitoring & health check of notification context 
	 * HEALTHCHECK : Used for HealthCheck
	 */
	public enum ServerNotificationProtocolType implements NotificationProtocolType{
		ALL,
		INFORMATION,
		INTEGRATION,
		HEALTHCHECK
	}
	
	/**
	 * Client should explicitly register for different types of Protocol type for decoupled & manageable architecture
	 *  
	 */
	public enum ClientNotificationProtocolType implements NotificationProtocolType{		
		EMAIL,
		SMS,
		PUSH_NOTIFICATION,
		INTEGRATION,
		HEALTHCHECK
	}
	
	/* INTERNAL : The delivery of notification marked for internal consumption
	 * EXTERNAL : The delivery of notification marked for external consumption
	 */
	public enum NotificationDeliveryType{
		INTERNAL,
		EXTERNAL
	}

}
