
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.notification.subscription;

import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.notification.Notification.NotificationFormatType;
import org.flowr.framework.core.notification.Notification.NotificationFrequency;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.Notification.NotificationType;

public class EventNotificationSubscription implements NotificationSubscription {

    private String subscriptionId;
    private SubscriptionType subscriptionType;
    private SubscriptionOption subscriptionOption;
    private NotificationFrequency frequency;
    private NotificationType notificationType;
    private NotificationDeliveryType notificationDeliveryType;
    private NotificationFormatType notificationFormatType;
    private NotificationProtocolType notificationProtocolType;
    private ServiceConfiguration serviceConfiguration;
    
    
    @Override
    public void setNotificationFrequency(NotificationFrequency frequency) {
        this.frequency=frequency;
    }

    @Override
    public NotificationFrequency getNotificationFrequency() {
        return this.frequency;
    }


    @Override
    public void setNotificationType(NotificationType notificationType) {
        this.notificationType=notificationType;
    }


    @Override
    public NotificationType getNotificationType() {
        return this.notificationType;
    }

    @Override
    public NotificationFormatType getNotificationFormatType() {
        return this.notificationFormatType;
    }

    @Override
    public void setNotificationFormatType(NotificationFormatType notificationFormatType) {
        this.notificationFormatType = notificationFormatType;
    }
    
    public ServiceConfiguration getServiceConfiguration() {
        return serviceConfiguration;
    }

    public void setServiceConfiguration(ServiceConfiguration serviceConfiguration) {
        this.serviceConfiguration = serviceConfiguration;
    }

    public NotificationDeliveryType getNotificationDeliveryType() {
        return notificationDeliveryType;
    }

    public void setNotificationDeliveryType(NotificationDeliveryType notificationDeliveryType) {
        this.notificationDeliveryType = notificationDeliveryType;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public SubscriptionOption getSubscriptionOption() {
        return subscriptionOption;
    }

    public void setSubscriptionOption(SubscriptionOption subscriptionOption) {
        this.subscriptionOption = subscriptionOption;
    }
    
    public NotificationProtocolType getNotificationProtocolType() {
        return notificationProtocolType;
    }

    public void setNotificationProtocolType(NotificationProtocolType notificationProtocolType) {
        this.notificationProtocolType = notificationProtocolType;
    }   
    
    public String toString(){
        
        return "\n\t\t EventNotificationSubscription{"+
                "\n\t\t |  subscriptionId             : "+subscriptionId+
                "\n\t\t |  subscriptionType           : "+subscriptionType+
                "\n\t\t |  subscriptionOption         : "+subscriptionOption+
                "\n\t\t |  frequency                  : "+frequency+
                "\n\t\t |  notificationType           : "+notificationType+
                "\n\t\t |  notificationDeliveryType   : "+notificationDeliveryType+
                "\n\t\t |  notificationFormatType     : "+notificationFormatType+
                "\n\t\t |  notificationProtocolType   : "+notificationProtocolType+
                "\n\t\t |  serviceConfiguration       : "+serviceConfiguration+
                "\n\t\t}";
    }


}
