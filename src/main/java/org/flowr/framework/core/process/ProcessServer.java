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
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
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
public class ProcessServer<REQ,RES>{
	
	// Server Process configuration
	private String serverSubscriptionId 					= null;	
	public static ConfigProperties SERVER_CONFIG 			= null;
	private static ServiceConfiguration serverConfiguration = null;
	private ServiceProvider<REQ,RES> processProvider;
	
	ProcessServer(ServiceProvider<REQ,RES> processProvider){		
		this.processProvider 	= processProvider;
	}
	
	public ProcessServer<REQ,RES> build(RouteContext serverRouteContext) throws ConfigurationException{
		
		//System.out.println("ProcessServer : serverRouteContext : "+serverRouteContext);
		
		((ServiceFramework<?,?>)processProvider).getNotificationService().setNotificationRouteContext(serverRouteContext); 
		return this;
	}
	
	public ProcessServer<REQ,RES> withServerConfiguration(String filePath) 
		throws ConfigurationException{
			
		serverConfiguration = ((ServiceFramework<?,?>)processProvider).getConfigurationService().getServiceConfiguration(ConfigurationType.SERVER); 
		SERVER_CONFIG		= serverConfiguration.getConfigAsProperties();
		return this;
	}
	
	public NotificationContext withServerNotificationSubscription(
		HashMap<NotificationProtocolType,NotificationSubscription> notificationMap) throws ConfigurationException{
	
		NotificationContext serverNotificationContext = new NotificationContext();

		ArrayList<SubscriptionContext> subscriptionContextList = new ArrayList<SubscriptionContext>();
	
		//System.out.println("ProcessServer : serviceConfiguration : "+serverConfiguration);
		
		serverNotificationContext.setServiceConfiguration(serverConfiguration);
		
		if(notificationMap!= null && !notificationMap.isEmpty()){			
			
			serverNotificationContext.setNotificationSubscriptionMap(notificationMap);
			
			Iterator<Entry<NotificationProtocolType, NotificationSubscription>> notificationSubscriptionIter = 
					serverNotificationContext.getNotificationSubscriptionMap().entrySet().iterator();
			
			while(notificationSubscriptionIter.hasNext()){
				
				Entry<NotificationProtocolType, NotificationSubscription> entry = 
						notificationSubscriptionIter.next();
				
				NotificationProtocolType notificationProtocolType = entry.getKey();
				
				NotificationSubscription notificationSubscription = entry.getValue();
				
				if(notificationProtocolType == notificationSubscription.getNotificationProtocolType()){
					
					if(notificationProtocolType != null && notificationProtocolType instanceof ServerNotificationProtocolType
							&& notificationSubscription.getSubscriptionType() == SubscriptionType.SERVER && 
							notificationSubscription.getNotificationProtocolType() == ServerNotificationProtocolType.ALL){
						
						SubscriptionContext serverSubscriptionContext = new SubscriptionContext();		
						
						serverSubscriptionContext.setSubscriptionStatus(SubscriptionStatus.REQUESTED);
						serverSubscriptionContext.setNotificationSubscription(notificationSubscription);
						
						serverSubscriptionContext = ((ServiceFramework<?,?>)processProvider).getSubscriptionService()
								.subscribeNotification(serverSubscriptionContext);
						
						serverSubscriptionId = serverSubscriptionContext.getSubcriptionId();
						
						notificationSubscription.setSubscriptionId(serverSubscriptionId);
						
						serverSubscriptionContext.setNotificationSubscription(notificationSubscription);
						
						subscriptionContextList.add(serverSubscriptionContext);
						
						// Only one subscription with ALL is sufficient
						break;
					}else{
						throw new ConfigurationException(
							ERR_CONFIG,
							MSG_CONFIG, 
							"Server configuration can only subscribe for SubscriptionType.SERVER with "
								+ "NotificationProtocolType of Notification.ServerNotificationProtocolType as ALL : "+
								notificationProtocolType);
	
					}
				}else{
					throw new ConfigurationException(
							ERR_CONFIG,
							MSG_CONFIG, 
							"Server configuration mismatch for NotificationProtocolType in NotificationSubscription.");

				}
			}
			
		}else{
			
			throw new ConfigurationException(
					ERR_CONFIG,
					MSG_CONFIG, 
					"NotificationSubscription List can not be empty.");
		}
		
		//System.out.println(" notificationSubscriptionList : "+notificationSubscriptionList);
		
		return serverNotificationContext;
	}
	
	public RouteContext withServerNotificationTask(NotificationTask notificationTask, NotificationServiceAdapter 
		notificationServiceAdapter,NotificationContext serverNotificationContext) throws ConfigurationException{

		RouteContext serverRouteContext = new RouteContext();
		
		if(serverNotificationContext != null && serverNotificationContext.getNotificationSubscriptionMap() != null
				&& !serverNotificationContext.getNotificationSubscriptionMap().isEmpty()){
		
			Iterator<Entry<NotificationProtocolType, NotificationSubscription>> notificationSubscriptionIter = 
					serverNotificationContext.getNotificationSubscriptionMap().entrySet().iterator();
			
			NotificationRoute<NotificationServiceAdapter,NotificationProtocolType> route = null;
			HashSet<NotificationRoute<NotificationServiceAdapter,NotificationProtocolType>> routeSet = 
					new HashSet<NotificationRoute<NotificationServiceAdapter,NotificationProtocolType>>();
			
			while(notificationSubscriptionIter.hasNext()){
				
				Entry<NotificationProtocolType, NotificationSubscription> entry = notificationSubscriptionIter.next();
					
				route = new NotificationRoute<NotificationServiceAdapter,NotificationProtocolType>();
				
				route.set(notificationServiceAdapter, entry.getKey());
				routeSet.add(route);
			}
			
			serverRouteContext.setRouteSet(routeSet);
			
			notificationTask.setNotificationContext(serverNotificationContext);	
			notificationServiceAdapter.setNotificationTask(notificationTask);
		}else{
			throw new ConfigurationException(
					ERR_CONFIG,
					MSG_CONFIG, 
					"Invalid NotificationContext for Notification Routing : "+serverNotificationContext);

		}
		
		return serverRouteContext;		
	}
	
}