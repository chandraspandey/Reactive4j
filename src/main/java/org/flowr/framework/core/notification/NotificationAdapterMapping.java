package org.flowr.framework.core.notification;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.flowr.framework.core.constants.ExceptionConstants;
import org.flowr.framework.core.constants.ExceptionMessages;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.ServerException;
import org.flowr.framework.core.notification.Notification.ClientNotificationProtocolType;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
import org.flowr.framework.core.notification.NotificationServiceAdapter.NotificationServiceAdapterType;

/**
 * Provides Route Mapping capabilities associated with notification routing capabilities for different mapped route 
 * part of Notification associated with NotificationServiceAdapter.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class NotificationAdapterMapping<K,V> {

	
	private HashSet<NotificationRoute<NotificationServiceAdapter,NotificationProtocolType>> notificationRouteSet = 
			new HashSet<NotificationRoute<NotificationServiceAdapter,NotificationProtocolType>>();
	
	public HashSet<NotificationRoute<NotificationServiceAdapter,NotificationProtocolType>> getRouteList() {
		return notificationRouteSet;
	}
	
	public void add(NotificationServiceAdapter notificationServiceAdapterInstance,NotificationProtocolType  notificationProtocolType)
		throws ConfigurationException{
				
		NotificationProtocolType existingMapping = lookupMapping(notificationServiceAdapterInstance);
		
		if(existingMapping == null){
			
			NotificationRoute<NotificationServiceAdapter,NotificationProtocolType> notificationRoute = 
					new NotificationRoute<NotificationServiceAdapter,	NotificationProtocolType>();
			
			notificationRoute.set(notificationServiceAdapterInstance, notificationProtocolType);
			
			notificationRouteSet.add(notificationRoute) ;
			
			//System.out.println("NotificationAdapterMapping : Added : \n :"+notificationRoute+"\n to : "+notificationRouteSet);
			
		}else if(existingMapping instanceof ServerNotificationProtocolType){
			System.out.println(" IGNORED : NotificationProtocolType : "+notificationProtocolType+
					" already configured for NotificationServiceAdapter instance with name : "+
					notificationServiceAdapterInstance.getNotificationServiceAdapterName());
		}else if(existingMapping instanceof ClientNotificationProtocolType){
			System.out.println(" IGNORED : NotificationProtocolType : "+notificationProtocolType+
					" already configured for NotificationServiceAdapter instance with name : "+
					notificationServiceAdapterInstance.getNotificationServiceAdapterName());
		}else{
								
			throw new ConfigurationException(			
				ERR_CONFIG,
				MSG_CONFIG, 
				"NotificationProtocolType : "+notificationProtocolType+
				" already configured for NotificationServiceAdapter instance with name : "+
						notificationServiceAdapterInstance.getNotificationServiceAdapterName());

		}
		
		
	}
	
	
	public NotificationProtocolType lookupMapping(NotificationServiceAdapter notificationServiceAdapterInstance){
		
		NotificationProtocolType existingMapping = null;
		
		if(!notificationRouteSet.isEmpty()){
			
			Iterator<NotificationRoute<NotificationServiceAdapter, NotificationProtocolType>> routeIterator = 
					notificationRouteSet.iterator();
			
			while(routeIterator.hasNext()){
				
				NotificationRoute<NotificationServiceAdapter, NotificationProtocolType> route = routeIterator.next();
				
				if(route.isKeyPresent(notificationServiceAdapterInstance)){
					
					existingMapping = route.getValues();
					break;
				}
			}
			
		}
		
		return existingMapping;
	}
	
	public ArrayList<NotificationServiceAdapter> getRoute(NotificationProtocolType notificationProtocolType, 
		EventType eventType) throws ServerException{
		
		ArrayList<NotificationServiceAdapter> adapterList = new ArrayList<NotificationServiceAdapter>();
		
		
		if(!notificationRouteSet.isEmpty()){
			
			if(notificationProtocolType != null){
			
				Iterator<NotificationRoute<NotificationServiceAdapter, NotificationProtocolType>> routeIterator = 
						notificationRouteSet.iterator();
				
				while(routeIterator.hasNext()){
					
					NotificationRoute<NotificationServiceAdapter, NotificationProtocolType> notificationRoute = 
							routeIterator.next();
					
					//System.out.println("NotificationAdapterMapping : notificationRouteSet : "+notificationRouteSet);
					
					//System.out.println("NotificationAdapterMapping : "+notificationProtocolType +" | "+notificationRoute);
					
					if(notificationRoute.getKey().getNotificationServiceAdapterType().equals(eventType)){
												
	
						if(
							notificationProtocolType == ServerNotificationProtocolType.SERVER_INFORMATION &&
							notificationRoute.getKey().getNotificationServiceAdapterType() == NotificationServiceAdapterType.SERVER	){
							
							adapterList.add(notificationRoute.getKey());
							break;
							
						}else if(notificationRoute.isValuePresent(notificationProtocolType)){
						
							adapterList.add(notificationRoute.getKey());
						}else{
														
							//System.out.println("NotificationAdapterMapping : notificationProtocolTypes[index] : "+notificationProtocolType+" | "+notificationRoute);
							ServerException serverException = new ServerException(
									ExceptionConstants.ERR_SERVER_PROCESSING,
									ExceptionMessages.MSG_SERVER_PROCESSING,
									"Notification Route Not defined for notificationProtocolType : "+
											notificationProtocolType);
							throw serverException;
						}

						/*System.out.println("NotificationAdapterMapping : "+notificationRoute.getKey().getNotificationServiceAdapterType() +" : "+eventType+
								" | adapterList : "+adapterList);*/
					}								
				}
			}else{
				
				ServerException serverException = new ServerException(
						ExceptionConstants.ERR_NOTIFICATION_PROTOCOL,
						ExceptionMessages.MSG_NOTIFICATION_PROTOCOL,
						"Notification Protocol not defined for processing : notificationProtocolTypeList : "+
						notificationProtocolType);
				throw serverException;
			}
		}else{
			ServerException serverException = new ServerException(
					ExceptionConstants.ERR_SERVER_PROCESSING,
					ExceptionMessages.MSG_SERVER_PROCESSING,
					"Notification Route Not defined for processing : routeSet : "+notificationRouteSet);
			throw serverException;
		}
		
		if(adapterList.isEmpty()){
			
			//System.out.println(" NotificationAdapterMapping : "+eventType+	" | adapterList : "+adapterList);
			
			ServerException serverException = new ServerException(
					ExceptionConstants.ERR_SERVER_PROCESSING,
					ExceptionMessages.MSG_SERVER_PROCESSING,
					"Notification Route Not defined for event type : "+eventType);
			throw serverException;
		}
		
		return adapterList;
	}
	
	public ArrayList<NotificationServiceAdapter> getRoute(EventType eventType) throws ServerException{
			
		ArrayList<NotificationServiceAdapter> adapterList = new ArrayList<NotificationServiceAdapter>();
		
		
		if(!notificationRouteSet.isEmpty()){
			
			Iterator<NotificationRoute<NotificationServiceAdapter, NotificationProtocolType>> routeIterator = 
					notificationRouteSet.iterator();
			
			while(routeIterator.hasNext()){
				
				NotificationRoute<NotificationServiceAdapter, NotificationProtocolType> notificationRoute = 
						routeIterator.next();
				
				//System.out.println("NotificationAdapterMapping : notificationRouteSet : "+notificationRouteSet);
				
				//System.out.println("NotificationAdapterMapping : "+notificationProtocolType +" | "+notificationRoute);
				
				if(notificationRoute.getKey().getNotificationServiceAdapterType().equals(eventType)){
						
					
					adapterList.add(notificationRoute.getKey());

					/*System.out.println("NotificationAdapterMapping : "+notificationRoute.getKey().getNotificationServiceAdapterType() +" : "+eventType+
							" | adapterList : "+adapterList);*/
				}								
			}

		}else{
			ServerException serverException = new ServerException(
					ExceptionConstants.ERR_SERVER_PROCESSING,
					ExceptionMessages.MSG_SERVER_PROCESSING,
					"Notification Route Not defined for processing : routeSet : "+notificationRouteSet);
			throw serverException;
		}
		
		if(adapterList.isEmpty()){
			
			//System.out.println(" NotificationAdapterMapping : "+eventType+	" | adapterList : "+adapterList);
			
			ServerException serverException = new ServerException(
					ExceptionConstants.ERR_SERVER_PROCESSING,
					ExceptionMessages.MSG_SERVER_PROCESSING,
					"Notification Route Not defined for event type : "+eventType);
			throw serverException;
		}
		System.out.println(" NotificationAdapterMapping : "+eventType+	" | adapterList : "+adapterList);
		return adapterList;
	}

	
	public String toString(){
		
		return "NotificationAdapterMapping{"+
				" routeSet : "+notificationRouteSet+	
				"}\n";
	}


	
}
