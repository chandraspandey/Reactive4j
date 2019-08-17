package org.flowr.framework.core.service.internal;

import java.util.Collection;
import java.util.Optional;
import java.util.Properties;

import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.context.SubscriptionContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.notification.subscription.NotificationSubscription.SubscriptionType;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.notification.subscription.Subscription;
import org.flowr.framework.core.notification.subscription.SubscriptionHelper;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.ServiceProvider;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class SubscriptionServiceImpl implements SubscriptionService{

	private ServiceUnit serviceUnit 						= ServiceUnit.POOL;
	private String serviceName								= FrameworkConstants.FRAMEWORK_SERVICE_SUBSCRIPTION;
	private ServiceType serviceType							= ServiceType.SUBSCRIPTION;
	private ServiceFramework<?,?> serviceFramework			= null;
	private Subscription subscriptionHelper					= new SubscriptionHelper();
	
	@Override
	public void setServiceFramework(ServiceFramework<?,?> serviceFramework) {
		this.serviceFramework = serviceFramework;
	}
	
	@Override
	public SubscriptionContext changeNotificationSubscription(SubscriptionContext subscriptionContext) 
			throws ConfigurationException{
				
		SubscriptionContext subContext  = subscriptionHelper.onSubscriptionChange(subscriptionContext);
		
		if(subscriptionContext.getNotificationSubscription().getSubscriptionType() == SubscriptionType.SERVER){
			
			((ServiceProvider<?,?>)this.serviceFramework).setServerSubscriptionIdentifier(subContext.getSubcriptionId());
		}	
		
		return subContext;	
	}
	
	@Override
	public SubscriptionContext subscribeNotification(SubscriptionContext subscriptionContext) 
			throws ConfigurationException{

		SubscriptionContext subContext  = subscriptionHelper.subscribe(subscriptionContext);
		
		if(subscriptionContext.getNotificationSubscription().getSubscriptionType() == SubscriptionType.SERVER){
			((ServiceProvider<?,?>)this.serviceFramework).setServerSubscriptionIdentifier(subContext.getSubcriptionId());
		}
		
		return subContext;
	}
	
	@Override
	public SubscriptionContext unsubscribeNotification(SubscriptionContext subscriptionContext) 
			throws ConfigurationException{
		
		return subscriptionHelper.unsubscribe(subscriptionContext);
	}

	@Override
	public Collection<NotificationSubscription> getNotificationProtocolTypelist(
			NotificationProtocolType notificationProtocolType) {
		
		return subscriptionHelper.getNotificationProtocolTypelist(notificationProtocolType);
	}
	
	@Override
	public NotificationSubscription lookup(String subscriptionId) {
		return subscriptionHelper.lookup(subscriptionId);
	}
	
	@Override
	public void setServiceType(ServiceType serviceType) {
		
		this.serviceType = serviceType;
	}
	
	@Override
	public ServiceType getServiceType() {
		
		return this.serviceType;
	}
	
	@Override
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	@Override
	public String getServiceName() {

		return this.serviceName;
	}	
	
	@Override
	public void setServiceUnit(ServiceUnit serviceUnit) {
		this.serviceUnit = serviceUnit;
	}

	@Override
	public ServiceUnit getServiceUnit() {
		return this.serviceUnit;
	}

	@Override
	public ServiceStatus startup(Optional<Properties> configProperties) {
		return ServiceStatus.STARTED;
	}

	@Override
	public ServiceStatus shutdown(Optional<Properties> configProperties) {
		return ServiceStatus.STOPPED;
	}

	@Override
	public void addServiceListener(EventPublisher serviceListener) {
	}

}
