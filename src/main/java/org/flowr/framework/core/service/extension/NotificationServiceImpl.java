package org.flowr.framework.core.service.extension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.context.EventContext;
import org.flowr.framework.core.context.RouteContext;
import org.flowr.framework.core.event.ChangeEvent;
import org.flowr.framework.core.exception.ClientException;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.ServerException;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.model.MetaData;
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
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class NotificationServiceImpl implements NotificationService{

	private ServiceUnit serviceUnit 							= ServiceUnit.SINGELTON;
	private String dependencyName								= NotificationService.class.getSimpleName();
	private DependencyType dependencyType 						= DependencyType.MANDATORY;
	private String serviceName									= FrameworkConstants.FRAMEWORK_SERVICE_NOTIFICATION;
	private ServiceType serviceType								= ServiceType.NOTIFICATION;
	
	private NotificationHelper notificationHelper				= new NotificationEventHelper();	
	private static NotificationBufferQueue notificationQueue 	= new NotificationBufferQueue();
	private boolean isEnabled 									= true;
	@SuppressWarnings("unused")
	private EventPublisher<MetaData> notificationServiceListener= null;
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
	public void publishEvent() throws ClientException {
		
		// Correct he removal from queue
		if(! notificationQueue.isEmpty()){
			
			//System.out.println("NotificationEngine : notificationQueue : "+notificationQueue);
			
			@SuppressWarnings("unchecked")
			ChangeEvent<EventModel> event = (ChangeEvent<EventModel>) notificationQueue.poll();				
				
			
			System.out.println("NotificationEngine : event : "+event);	
			
			ArrayList<NotificationServiceAdapter> adapterList = null;
			
			try {
							
			
				adapterList = notificationHelper.getNotificationRoute(event);
				
				adapterList.forEach((k)-> ((NotificationServiceAdapter)k).publishEvent(event));
				
			} catch (ServerException serverException) {
				System.err.println("NotificationEngine : ServerException : "+serverException.getContextMessage());	
				serverException.printStackTrace();
			} 
			
			
		}
				
	}

	@Override
	public void notify(NotificationBufferQueue bufferQueue) {
		
		notificationQueue.addAll(bufferQueue);
	}
	
	@Override
	public void notify(EventContext eventContext) throws ClientException {
		
		ChangeEvent<EventModel> event;
		
		try {
			
			event = notificationHelper.convert(eventContext);
			System.out.println("NotificationEngine : event : "+event);				
			
			notificationQueue.add(event);
			
			publishEvent();
		} catch (ServerException serverException) {
			System.err.println("NotificationEngine : ServerException :"+serverException.getContextMessage());
			serverException.printStackTrace();
		}
	}

	@Override
	public void addServiceListener(EventPublisher<MetaData> notificationServiceListener) {
		this.notificationServiceListener = notificationServiceListener;
	}

	@Override
	public ServiceStatus startup(Properties configProperties) {
		
		System.out.println("NotificationEngine : startup : "+configProperties);
		
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

}
