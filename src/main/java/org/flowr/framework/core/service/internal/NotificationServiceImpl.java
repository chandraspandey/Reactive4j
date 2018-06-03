package org.flowr.framework.core.service.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.context.RouteContext;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.exception.ClientException;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.ServerException;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.NotificationBufferQueue;
import org.flowr.framework.core.notification.NotificationEventHelper;
import org.flowr.framework.core.notification.NotificationHelper;
import org.flowr.framework.core.notification.NotificationRoute;
import org.flowr.framework.core.notification.NotificationServiceAdapter;
import org.flowr.framework.core.notification.NotificationServiceAdapter.NotificationServiceAdapterType;
import org.flowr.framework.core.service.ServiceFramework;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class NotificationServiceImpl implements NotificationService{

	private ServiceUnit serviceUnit 							= ServiceUnit.SINGELTON;
	private String serviceName									= FrameworkConstants.FRAMEWORK_SERVICE_NOTIFICATION;
	private ServiceType serviceType								= ServiceType.NOTIFICATION;	
	private NotificationHelper notificationHelper				= new NotificationEventHelper();
	private boolean isEnabled 									= true;
	@SuppressWarnings("unused")
	private EventPublisher 	notificationServiceListener			= null;
	@SuppressWarnings("unused")
	private ServiceFramework<?,?> serviceFramework				= null;
	
	@Override
	public void setServiceFramework(ServiceFramework<?,?> serviceFramework) {
		this.serviceFramework = serviceFramework;
	}
	
	@Override
	public void setNotificationRouteContext(RouteContext routeContext) throws ConfigurationException {
		this.notificationHelper.bindRoutes(routeContext.getRouteSet());
	}

	@Override
	public void publishEvent(NotificationBufferQueue notificationBufferQueue) throws ClientException{

		//System.out.println("NotificationServiceImpl : notificationBufferQueue : "+notificationBufferQueue);
		
		if(!notificationBufferQueue.isEmpty()) {
	
			ArrayList<NotificationServiceAdapter> adapterList = null;
			
			try {						
			
				adapterList = notificationHelper.getNotificationRoute(notificationBufferQueue.getEventType());
				
				adapterList.forEach((k)-> ((NotificationServiceAdapter)k).publishEvent(notificationBufferQueue));
				
			} catch (ServerException serverException) {
				System.err.println("NotificationServiceImpl : ServerException : "+serverException.getContextMessage());	
				serverException.printStackTrace();
			}
		}
	}
	
	@Override
	public ArrayList<NotificationServiceAdapter> getNotificationRoute(NotificationProtocolType notificationProtocolType,
			EventType eventType) throws ServerException{
		
		return notificationHelper.getNotificationRoute(notificationProtocolType, eventType);
	}
	
	@Override
	public HashSet<NotificationRoute<NotificationServiceAdapter, NotificationProtocolType>> getNotificationRoutes() {
		return notificationHelper.getNotificationRoutes();
	}

	@Override
	public void notify(NotificationBufferQueue bufferQueue) throws ClientException {
		
		publishEvent(bufferQueue);
	}

	@Override
	public void addServiceListener(EventPublisher notificationServiceListener) {
		this.notificationServiceListener = notificationServiceListener;
	}

	@Override
	public ServiceStatus startup(Properties configProperties) {
		
		System.out.println("NotificationServiceImpl : startup : "+configProperties);
		
		HashSet<NotificationRoute<NotificationServiceAdapter, NotificationProtocolType>> routeSet = 
				notificationHelper.getNotificationRoutes();

		// Starts all the server adapter's for event processing 
		if(!routeSet.isEmpty()){
			
			routeSet.forEach(
				(k)-> 
					{
						NotificationServiceAdapter adapter = 
								((NotificationRoute<NotificationServiceAdapter, NotificationProtocolType>)k).getKey();
						
						if(	adapter.getNotificationServiceAdapterType() == NotificationServiceAdapterType.SERVER
						){
							((NotificationRoute<NotificationServiceAdapter, NotificationProtocolType>)k).
							getKey().startup(configProperties);
							
						}
					}
			);
		}

		return ServiceStatus.STARTED;
	}

	@Override
	public ServiceStatus shutdown(Properties configProperties) {
		
		HashSet<NotificationRoute<NotificationServiceAdapter, NotificationProtocolType>> routeSet = 
				notificationHelper.getNotificationRoutes();
		
		// Shuts all the server adapter's running for event processing 
		if(!routeSet.isEmpty()){
			
			routeSet.forEach(
				(k)-> 
					{
						NotificationServiceAdapter adapter = 
								((NotificationRoute<NotificationServiceAdapter, NotificationProtocolType>)k).getKey();
						
						if(	adapter.getNotificationServiceAdapterType() == NotificationServiceAdapterType.SERVER
						){
							((NotificationRoute<NotificationServiceAdapter, NotificationProtocolType>)k).
							getKey().shutdown(configProperties);
							
						}
					}
			);
		}
		
		return ServiceStatus.STOPPED;
	}

	@Override
	public boolean isEnabled() {
		return this.isEnabled;
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
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

}
