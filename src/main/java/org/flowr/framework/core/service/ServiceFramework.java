package org.flowr.framework.core.service;

import java.util.List;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.ServiceException;
import org.flowr.framework.core.flow.SingleEventPublisher;
import org.flowr.framework.core.service.dependency.Dependency;
import org.flowr.framework.core.service.dependency.DependencyLoop;
import org.flowr.framework.core.service.extension.DefferedPromiseService;
import org.flowr.framework.core.service.extension.MapPromiseService;
import org.flowr.framework.core.service.extension.PhasedPromiseService;
import org.flowr.framework.core.service.extension.PromiseService;
import org.flowr.framework.core.service.extension.ScheduledPromiseService;
import org.flowr.framework.core.service.extension.StagePromiseService;
import org.flowr.framework.core.service.extension.StreamPromiseService;
import org.flowr.framework.core.service.internal.AdministrationService;
import org.flowr.framework.core.service.internal.ConfigurationService;
import org.flowr.framework.core.service.internal.EventService;
import org.flowr.framework.core.service.internal.HighAvailabilityService;
import org.flowr.framework.core.service.internal.ManagedService;
import org.flowr.framework.core.service.internal.NodeService;
import org.flowr.framework.core.service.internal.NotificationService;
import org.flowr.framework.core.service.internal.RegistryService;
import org.flowr.framework.core.service.internal.RoutingService;
import org.flowr.framework.core.service.internal.SecurityService;
import org.flowr.framework.core.service.internal.SubscriptionService;

/**
 * Aggregation Service interface for defined service as Service registry supported as part of framework, 
 * concrete implementation provides the behavior for service lookup.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ServiceFramework<REQUEST,RESPONSE> extends Service,Dependency, DependencyLoop,SingleEventPublisher{

	
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
	
	public DefferedPromiseService<REQUEST, RESPONSE> getDefferedPromiseService();

	public PhasedPromiseService<REQUEST,RESPONSE> getPhasedPromiseService();

	public ScheduledPromiseService<REQUEST,RESPONSE> getScheduledPromiseService();

	public StagePromiseService<REQUEST,RESPONSE> getStagedPromiseService();
	
	public StreamPromiseService<REQUEST,RESPONSE> getStreamPromiseService();

	public RegistryService getRegistryService();

	public RoutingService getRoutingService();

	public SubscriptionService getSubscriptionService();

	public ManagedService getManagedService();
	
	public ConfigurationService getConfigurationService() ;

	public AdministrationService getAdministrationService();
	
	public SecurityService getSecurityService();

	public MapPromiseService<REQUEST, RESPONSE> getMapPromiseService();
	
	public HighAvailabilityService getHighAvailabilityService(); 
	
	public NodeService getNodeService();
	
	public void configure(String configurationFilePath) throws ConfigurationException;
	
	public List<Service> getServiceList();
	
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
