package org.flowr.framework.core.service;

import org.flowr.framework.core.exception.ServiceException;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.service.dependency.Dependency;
import org.flowr.framework.core.service.dependency.DependencyLoop;
import org.flowr.framework.core.service.extension.EventService;
import org.flowr.framework.core.service.extension.ManagedService;
import org.flowr.framework.core.service.extension.NotificationService;
import org.flowr.framework.core.service.extension.PhasedPromiseService;
import org.flowr.framework.core.service.extension.PromiseService;
import org.flowr.framework.core.service.extension.RegistryService;
import org.flowr.framework.core.service.extension.RoutingService;
import org.flowr.framework.core.service.extension.ScheduledPromiseService;
import org.flowr.framework.core.service.extension.StagedPromiseService;
import org.flowr.framework.core.service.extension.StreamPromiseService;
import org.flowr.framework.core.service.extension.SubscriptionService;

/**
 * Aggregation Service interface for defined service as Service registry supported as part of framework, 
 * concrete implementation provides the behavior for service lookup.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ServiceFramework<REQUEST,RESPONSE> extends Service,Dependency, DependencyLoop,EventPublisher<EventModel>{

	
	public void addService(Service service) throws ServiceException;
	
	public void removeService(Service service);
	
	/**
	 * Lookup Service at the framework level for usage for singleton ServiceType other than API
	 * @param serviceType
	 * @return
	 */
	public Service lookup(ServiceType serviceType);
	
	/**
	 * Lookup Service at the framework level for usage for ServiceType API, where multiple client services can be 
	 * created with same ServiceType but different names to cater to different end point use cases.
	 * @param serviceType
	 * @param serviceName
	 * @return
	 */
	public Service lookup(ServiceType serviceType, String serviceName);
	
	public EventService getEventService();

	public NotificationService getNotificationService();

	public PromiseService<REQUEST,RESPONSE> getPromiseService();

	public PhasedPromiseService<REQUEST,RESPONSE> getPhasedPromiseService();

	public ScheduledPromiseService<REQUEST,RESPONSE> getScheduledPromiseService();

	public StagedPromiseService<REQUEST,RESPONSE> getStagedPromiseService();
	
	public StreamPromiseService<REQUEST,RESPONSE> getStreamPromiseService();

	public RegistryService getRegistryService();

	public RoutingService getRoutingService();

	public SubscriptionService getSubscriptionService();

	public ManagedService getManagedService();
	
	public static ServiceFramework<?,?> getInstance() {
		
		return DefaultServiceFramework.getInstance();
	}
	
	public class DefaultServiceFramework<REQUEST,RESPONSE>{
		
		private static ServiceFramework<?,?> serviceFramework = null;
		
		@SuppressWarnings("rawtypes")
		public static ServiceFramework<?,?> getInstance() {
			
			if(serviceFramework == null) {
				serviceFramework = new ServiceFrameworkImpl();
			}
			
			return serviceFramework;
		}
		
	}
}
