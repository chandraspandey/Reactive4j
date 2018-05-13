package org.flowr.framework.core.process;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import org.flowr.framework.core.config.ConfigProperties;
import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.context.NotificationContext;
import org.flowr.framework.core.context.RouteContext;
import org.flowr.framework.core.context.SubscriptionContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.notification.Notification.ClientNotificationProtocolType;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.NotificationRoute;
import org.flowr.framework.core.notification.NotificationServiceAdapter;
import org.flowr.framework.core.notification.NotificationTask;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.notification.subscription.NotificationSubscription.SubscriptionStatus;
import org.flowr.framework.core.notification.subscription.NotificationSubscription.SubscriptionType;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.ServiceProvider;
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class ProcessClient<REQ,RES>{
	
	// Client configuration
	public static String clientSubscriptionId 				= null;	
	public static ConfigProperties CLIENT_CONFIG 			= null;
	public static ServiceConfiguration clientConfiguration  = null;

	private ServiceProvider<REQ,RES> processProvider;

	ProcessClient(ServiceProvider<REQ,RES> processProvider){
		
		this.processProvider 	= processProvider;
	}
	
	public ProcessClient<REQ,RES> build(RouteContext clientRouteContext) throws ConfigurationException{
		
		//System.out.println(" ---------------------> ProcessClient : build :"+clientRouteContext);
		((ServiceFramework<?,?>)processProvider).getNotificationService().setNotificationRouteContext(clientRouteContext); 
		return this;
	}
	
	public ProcessClient<REQ,RES> andClientConfiguration(String filePath) 
			throws ConfigurationException{
				
		clientConfiguration = ((ServiceFramework<?,?>)processProvider).getConfigurationService().getServiceConfiguration(ConfigurationType.CLIENT);
		CLIENT_CONFIG 		= clientConfiguration.getConfigAsProperties();
		return this;
	}
	
	
	public SubscriptionContext withClientNotificationSubscription(NotificationSubscription 
		notificationSubscription) throws ConfigurationException{	
		
		SubscriptionContext clientSubscriptionContext = new SubscriptionContext();	
		
		if(notificationSubscription!= null ){			
			
			clientSubscriptionContext.setSubscriptionStatus(SubscriptionStatus.REQUESTED);
			clientSubscriptionContext.setNotificationSubscription(notificationSubscription);
			
			clientSubscriptionContext = ((ServiceFramework<?,?>)processProvider).getSubscriptionService().
					subscribeNotification(clientSubscriptionContext);
			
			clientSubscriptionId = clientSubscriptionContext.getSubcriptionId();
			
			notificationSubscription.setSubscriptionId(clientSubscriptionId);
			
			clientSubscriptionContext.setNotificationSubscription(notificationSubscription);
			
		}else{
			
			throw new ConfigurationException(
					ERR_CONFIG,
					MSG_CONFIG, 
					"NotificationSubscription List can not be empty.");
		}
		
		//System.out.println(" notificationSubscriptionList : "+notificationSubscriptionList);
		
		return clientSubscriptionContext;
	}
	
	public NotificationContext andClientNotificationSubscription(HashMap<NotificationProtocolType,
			NotificationSubscription> notificationMap) throws ConfigurationException{
	
		NotificationContext clientNotificationContext = new NotificationContext();		
		
		//System.out.println("ProcessClient : serviceConfiguration : "+clientConfiguration);
		
		clientNotificationContext.setServiceConfiguration(clientConfiguration);
		
		ArrayList<SubscriptionContext> subscriptionContextList = new ArrayList<SubscriptionContext>();
	
		//System.out.println("ProcessClient : serviceConfiguration : "+clientConfiguration);
		
		clientNotificationContext.setServiceConfiguration(clientConfiguration);
		
		if(notificationMap!= null && !notificationMap.isEmpty()){			
			
			clientNotificationContext.setNotificationSubscriptionMap(notificationMap);
			
			Iterator<Entry<NotificationProtocolType, NotificationSubscription>> notificationSubscriptionIter = 
					clientNotificationContext.getNotificationSubscriptionMap().entrySet().iterator();
			
			while(notificationSubscriptionIter.hasNext()){
				
				Entry<NotificationProtocolType, NotificationSubscription> entry = 
						notificationSubscriptionIter.next();
				
				NotificationProtocolType notificationProtocolType = entry.getKey();
				
				NotificationSubscription notificationSubscription = entry.getValue();
				
				if(notificationProtocolType == notificationSubscription.getNotificationProtocolType()){
				
					if(notificationProtocolType != null && notificationProtocolType instanceof ClientNotificationProtocolType
							&& notificationSubscription.getSubscriptionType() == SubscriptionType.CLIENT){
					
						SubscriptionContext clientSubscriptionContext = new SubscriptionContext();		
						
						clientSubscriptionContext.setSubscriptionStatus(SubscriptionStatus.REQUESTED);
						clientSubscriptionContext.setNotificationSubscription(notificationSubscription);
						
						clientSubscriptionContext = ((ServiceFramework<?,?>)processProvider).getSubscriptionService()
								.subscribeNotification(clientSubscriptionContext);
						
						clientSubscriptionId = clientSubscriptionContext.getSubcriptionId();
						
						notificationSubscription.setSubscriptionId(clientSubscriptionId);
						
						clientSubscriptionContext.setNotificationSubscription(notificationSubscription);
						
						subscriptionContextList.add(clientSubscriptionContext);
					}else{
						
						throw new ConfigurationException(
							ERR_CONFIG,
							MSG_CONFIG, 
							"Client configuration can only subscribe for NotificationProtocolType of "
									+ "Notification.ClientNotificationProtocolType : "+notificationProtocolType);
					}
				}else{
					throw new ConfigurationException(
							ERR_CONFIG,
							MSG_CONFIG, 
							"Client configuration mismatch for NotificationProtocolType in NotificationSubscription.");
				}
				
			}
			
		}else{
			
			throw new ConfigurationException(
					ERR_CONFIG,
					MSG_CONFIG, 
					"NotificationSubscription List can not be empty.");
		}
		
		//System.out.println(" notificationSubscriptionList : "+notificationSubscriptionList);
		
		return clientNotificationContext;
	}
	
	public RouteContext andClientNotificationTask(NotificationTask notificationTask,
			NotificationServiceAdapter notificationServiceAdapter,
			NotificationContext clientNotificationContext) throws ConfigurationException{
		
		RouteContext clientRouteContext = new RouteContext();
		
		if(clientNotificationContext != null && clientNotificationContext.getNotificationSubscriptionMap() != null &&
				!clientNotificationContext.getNotificationSubscriptionMap().isEmpty()){
			
			Iterator<Entry<NotificationProtocolType, NotificationSubscription>> notificationSubscriptionIter = 
					clientNotificationContext.getNotificationSubscriptionMap().entrySet().iterator();
			
			NotificationRoute<NotificationServiceAdapter,NotificationProtocolType> route = null;
			HashSet<NotificationRoute<NotificationServiceAdapter,NotificationProtocolType>> routeSet = new
					HashSet<NotificationRoute<NotificationServiceAdapter,NotificationProtocolType>>();
			
			while(notificationSubscriptionIter.hasNext()){
				
				Entry<NotificationProtocolType, NotificationSubscription> entry = notificationSubscriptionIter.next();

				route = new NotificationRoute<NotificationServiceAdapter,NotificationProtocolType>();
				
				route.set(notificationServiceAdapter, entry.getKey());
				
				//System.out.println("ProcessClient : andClientNotificationTask : route : "+route);
				
				routeSet.add(route);
			}			
			
			clientRouteContext.setRouteSet(routeSet);
			
			notificationTask.setNotificationContext(clientNotificationContext);	
			notificationServiceAdapter.setNotificationTask(notificationTask);
		}else{
			
			throw new ConfigurationException(
					ERR_CONFIG,
					MSG_CONFIG, 
					"Invalid NotificationContext for Notification Routing : "+clientNotificationContext);
		}
		
		return clientRouteContext;		
	}
}