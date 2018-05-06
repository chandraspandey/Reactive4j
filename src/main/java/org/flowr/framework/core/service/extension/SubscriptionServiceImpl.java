package org.flowr.framework.core.service.extension;

import java.util.Properties;

import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.context.SubscriptionContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.notification.subscription.NotificationSubscription.SubscriptionType;
import org.flowr.framework.core.notification.subscription.Subscription;
import org.flowr.framework.core.notification.subscription.SubscriptionHelper;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.ServiceProvider;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class SubscriptionServiceImpl implements SubscriptionService{

	private ServiceUnit serviceUnit 						= ServiceUnit.POOL;
	private String dependencyName							= SubscriptionService.class.getSimpleName();
	private DependencyType dependencyType 					= DependencyType.MANDATORY;
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
	public DependencyStatus loopTest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDependencyName() {
		return this.dependencyName;
	}

	@Override
	public DependencyType getDependencyType() {
		return this.dependencyType;
	}

	@Override
	public DependencyStatus verify() {
		DependencyStatus status = DependencyStatus.UNSATISFIED;
		
		return status;
	}

	@Override
	public ServiceStatus startup(Properties configProperties) {
		// TODO Auto-generated method stub
		return ServiceStatus.STARTED;
	}

	@Override
	public ServiceStatus shutdown(Properties configProperties) {
		// TODO Auto-generated method stub
		return ServiceStatus.STOPPED;
	}

	@Override
	public void addServiceListener(EventPublisher serviceListener) {
		// TODO Auto-generated method stub
		
	}

}
