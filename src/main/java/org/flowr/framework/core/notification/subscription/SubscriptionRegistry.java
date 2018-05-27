package org.flowr.framework.core.notification.subscription;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_NOTIFICATION_PROTOCOL;
import static org.flowr.framework.core.constants.ExceptionConstants.ERR_NOTIFICATION_SUBSCRIPTION;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_NOTIFICATION_PROTOCOL;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_NOTIFICATION_SUBSCRIPTION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.flowr.framework.core.context.SubscriptionContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.notification.Notification.ClientNotificationProtocolType;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
import org.flowr.framework.core.notification.subscription.NotificationSubscription.SubscriptionStatus;
import org.flowr.framework.core.process.ProcessRegistry;


/**
 * Defines SubscriptionRegistry as a Registry with 1:N association between Client & NotificationSubscription. 

 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class SubscriptionRegistry implements ProcessRegistry<String,NotificationSubscription>, 
	SubscriptionLifecycle{

	private static HashMap<NotificationProtocolType,ArrayList<NotificationSubscription>> subscriptionMap = 
			new HashMap<NotificationProtocolType,ArrayList<NotificationSubscription>>();
	private RegistryType registryType 										= RegistryType.LOCAL;
	
	
	public SubscriptionRegistry() {
		
		Arrays.asList(ClientNotificationProtocolType.values()).forEach(
			
			(c) -> {
				subscriptionMap.put(c,new ArrayList<NotificationSubscription>());
			}
		);
		
		Arrays.asList(ServerNotificationProtocolType.values()).forEach(
			
			(c) -> {
				subscriptionMap.put(c,new ArrayList<NotificationSubscription>());
			}
		);
	}

	@Override
	public SubscriptionContext register(SubscriptionContext subscriptionContext) throws ConfigurationException {
		
		// reuse previous subscription
		String subcriptionId = subscriptionContext.getSubcriptionId();
				
		if(subcriptionId == null  ){		
			subcriptionId = UUID.randomUUID().toString();
		}
		
		subscriptionContext.setSubscriptionStatus(SubscriptionStatus.REQUESTED);
		
		if(subscriptionContext.getNotificationSubscription() != null){
		
			NotificationProtocolType notificationProtocolType = 
					subscriptionContext.getNotificationSubscription().getNotificationProtocolType();
			
			if(notificationProtocolType != null){
		
				bind(subcriptionId,subscriptionContext.getNotificationSubscription());
						
				subscriptionContext.setSubcriptionId(subcriptionId);
				subscriptionContext.setSubscriptionStatus(SubscriptionStatus.SUBSCRIBED);
			}else{
				
				throw new ConfigurationException(ERR_NOTIFICATION_PROTOCOL,MSG_NOTIFICATION_PROTOCOL, 
						"NotificationProtocolType : "+notificationProtocolType);
			}
		}else{
			throw new ConfigurationException(ERR_NOTIFICATION_SUBSCRIPTION,MSG_NOTIFICATION_SUBSCRIPTION, 
					"NotificationSubscription : "+subscriptionContext.getNotificationSubscription());
		}
				
		return subscriptionContext;
	}

	@Override
	public SubscriptionContext deregister(SubscriptionContext subscriptionContext) throws ConfigurationException {
		
		subscriptionContext.setSubscriptionStatus(SubscriptionStatus.REQUESTED);
		
		if(subscriptionContext.getNotificationSubscription() != null){
			
			NotificationProtocolType notificationProtocolType = 
					subscriptionContext.getNotificationSubscription().getNotificationProtocolType();
			
			if(notificationProtocolType != null ){
		
				unbind(subscriptionContext.getSubcriptionId(),subscriptionContext.getNotificationSubscription());
				subscriptionContext.setSubscriptionStatus(SubscriptionStatus.UNSUBSCRIBED);
			}else{
				
				throw new ConfigurationException(ERR_NOTIFICATION_PROTOCOL,MSG_NOTIFICATION_PROTOCOL, 
						"NotificationProtocolTypeList : "+notificationProtocolType);
			}
		}else{
			throw new ConfigurationException(ERR_NOTIFICATION_SUBSCRIPTION,MSG_NOTIFICATION_SUBSCRIPTION, 
					"NotificationSubscription : "+subscriptionContext.getNotificationSubscription());
		}
		
		return subscriptionContext;
	}
	
	@Override
	public SubscriptionContext change(SubscriptionContext subscriptionContext) throws ConfigurationException {
		
		String subcriptionId = subscriptionContext.getSubcriptionId();
		
		subscriptionContext.setSubscriptionStatus(SubscriptionStatus.REQUESTED);
		
		if( subcriptionId != null && subscriptionContext.getNotificationSubscription() != null){
		
			subcriptionId = UUID.randomUUID().toString();
			
			NotificationProtocolType notificationProtocolType = 
					subscriptionContext.getNotificationSubscription().getNotificationProtocolType();
			
			if(notificationProtocolType != null){
		
				rebind(subcriptionId,subscriptionContext.getNotificationSubscription());
						
				subscriptionContext.setSubcriptionId(subcriptionId);
				subscriptionContext.setSubscriptionStatus(SubscriptionStatus.CHANGED);
			}else{
				
				throw new ConfigurationException(ERR_NOTIFICATION_PROTOCOL,MSG_NOTIFICATION_PROTOCOL, 
						"NotificationProtocolType : "+notificationProtocolType);
			}
		}else{
			throw new ConfigurationException(ERR_NOTIFICATION_SUBSCRIPTION,MSG_NOTIFICATION_SUBSCRIPTION, 
					"NotificationSubscription : "+subscriptionContext.getNotificationSubscription());
		}
				
		return subscriptionContext;
	}
	
			
	@Override
	public void bind(String subscriptionId, NotificationSubscription notificationSubscription) {
		
		subscriptionMap.get(notificationSubscription.getNotificationProtocolType()).add(notificationSubscription);
	}

	@Override
	public void unbind(String subscriptionId,NotificationSubscription notificationSubscription) {
		
		subscriptionMap.get(notificationSubscription.getNotificationProtocolType()).remove(notificationSubscription);
	}

	@Override
	public void rebind(String subscriptionId, NotificationSubscription notificationSubscription) {
		
		unbind(subscriptionId, notificationSubscription);
		bind(subscriptionId, notificationSubscription);
	}

	@Override
	public Collection<NotificationSubscription> getNotificationProtocolTypeList(
			NotificationProtocolType notificationProtocolType) {
		
		return subscriptionMap.get(notificationProtocolType);
	}
	
	@Override
	public Collection<NotificationSubscription> list() {
		
		Collection<NotificationSubscription> susbcriptions = new ArrayList<NotificationSubscription>();
		
		subscriptionMap.values().forEach(
				(v) -> {
					susbcriptions.addAll(v);
				}
		);
		
		return susbcriptions;
	}

	@Override
	public NotificationSubscription lookup(String subscriptionId) {

		NotificationSubscription notificationSubscription = null;
		//System.out.println("notificationMap : "+notificationMap);
		
		Iterator<NotificationSubscription> iter = list().iterator();

		while(iter.hasNext()) {			
			
			NotificationSubscription subscription = iter.next();
			
			if(subscription.getSubscriptionId().equals(subscriptionId)) {
				
				notificationSubscription = subscription;
				break;
			}

		}
		
		return notificationSubscription;

	}

	@Override
	public void persist() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String, NotificationSubscription> restore() {
		
		return null;
	}
	
	@Override
	public void clear() {
		subscriptionMap.clear();
	}


	@Override
	public RegistryType getRegistryType() {
		return this.registryType;
	}

	@Override
	public void setRegistryType(RegistryType registryType) {
		this.registryType = registryType;
	}

	public String toString(){
		
		return "SubscriptionRegistry{  registryType : "+registryType+" | "+
				"\n subscriptionMap : "+subscriptionMap+
				"\n}\n";
	}

}
