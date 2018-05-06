package org.flowr.framework.core.notification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.ServerException;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class NotificationEventHelper implements NotificationHelper{

	private static NotificationAdapterMapping<NotificationServiceAdapter,NotificationProtocolType> 
		notificationAdapterMapping = new NotificationAdapterMapping<NotificationServiceAdapter,NotificationProtocolType>();


	@Override
	public void bindRoutes(HashSet<NotificationRoute<NotificationServiceAdapter,NotificationProtocolType>> routeSet) 
			throws ConfigurationException {
	
		if(!routeSet.isEmpty()){
				
			Iterator<NotificationRoute<NotificationServiceAdapter,NotificationProtocolType>> routeIter =  
					routeSet.iterator();
	
			if(routeIter.hasNext()){	
				
				NotificationRoute<NotificationServiceAdapter,NotificationProtocolType> notificationRoute =
						routeIter.next();
				
				NotificationServiceAdapter notificationServiceAdapter = notificationRoute.getKey();
				
				NotificationProtocolType notificationProtocolType = notificationRoute.getValues();
									
				notificationAdapterMapping.add(notificationServiceAdapter,notificationProtocolType);
				
			}
			
		}
		
		//System.out.println("NotificationEventHelper : bindRoutes :  "+routeSet);
		
	}
	
	@Override
	public void bindNotificationRoute(NotificationProtocolType notificationProtocolType, NotificationServiceAdapter 
			notificationServiceAdapter) throws ConfigurationException{
				
		notificationAdapterMapping.add(notificationServiceAdapter,notificationProtocolType);
	}
	
	@Override
	public ArrayList<NotificationServiceAdapter> getNotificationRoute(NotificationProtocolType notificationProtocolType,
			EventType eventType) throws ServerException{
			
		//System.out.println("notificationAdapterMapping : "+routeList);
		
		return notificationAdapterMapping.getRoute(notificationProtocolType,eventType);
	}
	
	public ArrayList<NotificationServiceAdapter> getNotificationRoute(EventType eventType) throws ServerException{
		
		return notificationAdapterMapping.getRoute( eventType);
	}

	@Override
	public HashSet<NotificationRoute<NotificationServiceAdapter, NotificationProtocolType>> getNotificationRoutes() {
		return notificationAdapterMapping.getRouteList();
	}
}
