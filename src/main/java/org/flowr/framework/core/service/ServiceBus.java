package org.flowr.framework.core.service;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_SERVICE_ALREADY_EXISTS;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_SERVICE_ALREADY_EXISTS;
import static org.flowr.framework.core.constants.FrameworkConstants.FRAMEWORK_SERVICE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Flow.Subscriber;

import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.exception.ServiceException;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.service.extension.DefferedPromiseService;
import org.flowr.framework.core.service.extension.EventService;
import org.flowr.framework.core.service.extension.ManagedService;
import org.flowr.framework.core.service.extension.MapPromiseService;
import org.flowr.framework.core.service.extension.NotificationService;
import org.flowr.framework.core.service.extension.PhasedPromiseService;
import org.flowr.framework.core.service.extension.PromiseService;
import org.flowr.framework.core.service.extension.RegistryService;
import org.flowr.framework.core.service.extension.RoutingService;
import org.flowr.framework.core.service.extension.ScheduledPromiseService;
import org.flowr.framework.core.service.extension.SecurityService;
import org.flowr.framework.core.service.extension.StagePromiseService;
import org.flowr.framework.core.service.extension.StreamPromiseService;
import org.flowr.framework.core.service.extension.SubscriptionService;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public abstract class ServiceBus<REQUEST,RESPONSE> implements ServiceFramework<REQUEST,RESPONSE>,
	ServiceProvider<REQUEST,RESPONSE>{

	private ServiceUnit serviceUnit 						= ServiceUnit.REGISTRY;
	private String dependencyName							= NotificationService.class.getSimpleName();
	private DependencyType dependencyType 					= DependencyType.MANDATORY;
	private String serviceName								= FRAMEWORK_SERVICE;
	private boolean isEnabled		 						= true; 
	private List<Service> serviceList						= new ArrayList<Service>();	
	private static ServiceStatus frameworkServiceStatus		= ServiceStatus.UNUSED;
	private ServiceType serviceType							= ServiceType.FRAMEWORK;

	private String flowName 								= ServiceBus.class.getSimpleName();
	private FlowType flowType 								= FlowType.PROCESS;
	private FlowFunctionType flowFunctionType 				= FlowFunctionType.SERVER;
	private FlowPublisherType flowPublisherType				= FlowPublisherType.FLOW_PUBLISHER_CLIENT;
	private Subscriber<? super Event<EventModel>> subscriber;
	
	private EventService eventService 						= EventService.getInstance(); 
	private NotificationService notificationService 		= NotificationService.getInstance();
	private RegistryService	registryService					= RegistryService.getInstance();	
	private RoutingService routingService					= RoutingService.getInstance();
	private SubscriptionService subscriptionService			= SubscriptionService.getInstance();
	private SecurityService securityService					= SecurityService.getInstance();
	private ManagedService managedService					= ManagedService.getInstance();
	@SuppressWarnings("unchecked")
	private PromiseService<REQUEST,RESPONSE> promiseService 					= 
			(PromiseService<REQUEST, RESPONSE>) PromiseService.getInstance();
	@SuppressWarnings("unchecked")
	private DefferedPromiseService<REQUEST,RESPONSE> defferedPromiseService 	= 
			(DefferedPromiseService<REQUEST, RESPONSE>) DefferedPromiseService.getInstance();
	@SuppressWarnings("unchecked")
	private PhasedPromiseService<REQUEST,RESPONSE> phasedPromiseService 		= 
			(PhasedPromiseService<REQUEST, RESPONSE>) PhasedPromiseService.getInstance();
	@SuppressWarnings("unchecked")
	private ScheduledPromiseService<REQUEST,RESPONSE> scheduledPromiseService 	= 
			(ScheduledPromiseService<REQUEST, RESPONSE>) ScheduledPromiseService.getInstance();
	@SuppressWarnings("unchecked")
	private StagePromiseService<REQUEST,RESPONSE> stagePromiseService 		= 
			(StagePromiseService<REQUEST, RESPONSE>) StagePromiseService.getInstance();
	@SuppressWarnings("unchecked")
	private StreamPromiseService<REQUEST,RESPONSE> streamPromiseService 		= 
			(StreamPromiseService<REQUEST, RESPONSE>) StreamPromiseService.getInstance();
	@SuppressWarnings("unchecked")
	private MapPromiseService<REQUEST,RESPONSE> mapPromiseService 		= 
			(MapPromiseService<REQUEST, RESPONSE>) MapPromiseService.getInstance();

	
	public ServiceBus(){
		
		eventService.setServiceFramework(this);
		notificationService.setServiceFramework(this);
		promiseService.setServiceFramework(this);
		defferedPromiseService.setServiceFramework(this);
		phasedPromiseService.setServiceFramework(this);
		scheduledPromiseService.setServiceFramework(this);
		stagePromiseService.setServiceFramework(this);
		streamPromiseService.setServiceFramework(this);
		registryService.setServiceFramework(this);
		routingService.setServiceFramework(this);
		subscriptionService.setServiceFramework(this);
		managedService.setServiceFramework(this);
		securityService.setServiceFramework(this);
		mapPromiseService.setServiceFramework(this);
		
		this.serviceList.add(eventService);
		this.serviceList.add(notificationService);
		this.serviceList.add(promiseService);
		this.serviceList.add(defferedPromiseService);
		this.serviceList.add(phasedPromiseService);
		this.serviceList.add(scheduledPromiseService);
		this.serviceList.add(stagePromiseService);
		this.serviceList.add(streamPromiseService);
		this.serviceList.add(registryService);
		this.serviceList.add(routingService);
		this.serviceList.add(subscriptionService);
		this.serviceList.add(managedService);
		this.serviceList.add(securityService);
		this.serviceList.add(mapPromiseService);
	}

	@Override
	public Service lookup(ServiceType serviceType) {
		
		Service service = null;
		
		Iterator<Service> iter = this.serviceList.iterator();
		
		while(iter.hasNext()) {
			
			Service svc = iter.next();
			
			if(svc.getServiceType() == serviceType) {
				
				service = svc;
				break;
			}
		}
		
		return service;
	}
	
	@Override
	public Service lookup(ServiceType serviceType, String serviceName) {
		
		Service service = null;
		
		Iterator<Service> iter = this.serviceList.iterator();
		
		while(iter.hasNext()) {
			
			Service svc = iter.next();
			
			if(svc.getServiceType() == serviceType && svc.getServiceName().equals(serviceName) ) {
				
				service = svc;
				break;
			}
		}
		
		return service;
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
	public void setServiceType(ServiceType serviceType) {
		
		this.serviceType = serviceType;
	}
	
	@Override
	public ServiceType getServiceType() {
		
		return this.serviceType;
	}

	@Override
	public void addService(Service service) throws ServiceException{
		
		if(lookup(service.getServiceType(),service.getServiceName()) == null) {
			this.serviceList.add(service);
		}else {
			
			throw new ServiceException(ERR_SERVICE_ALREADY_EXISTS,MSG_SERVICE_ALREADY_EXISTS, 
					"Attempt to add duplicate service.");
		}
		
		
	}
	
	@Override
	public void removeService(Service service) {
		
		this.serviceList.remove(service);
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
	public void addServiceListener(EventPublisher serviceListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServiceStatus startup(Properties configProperties) {
		
		this.serviceList.forEach(
			
			(s) -> {
				
				ServiceStatus serviceStatus = ((ServiceLifecycle)s).startup(configProperties);
				
				while(
						serviceStatus != ServiceStatus.STARTED ||
						serviceStatus != ServiceStatus.ERROR
				) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				if(serviceStatus == ServiceStatus.ERROR) {
					frameworkServiceStatus = serviceStatus;
				}
			}
		);
		
		return frameworkServiceStatus;
	}

	@Override
	public ServiceStatus shutdown(Properties configProperties) {
		
		this.serviceList.forEach(
			
			(s) -> {
				
				ServiceStatus serviceStatus = ((ServiceLifecycle)s).shutdown(configProperties);
				
				while(
						serviceStatus != ServiceStatus.STARTED ||
						serviceStatus != ServiceStatus.ERROR
				) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				if(serviceStatus == ServiceStatus.ERROR) {
					frameworkServiceStatus = serviceStatus;
				}
			}
		);
		
		return frameworkServiceStatus;
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
	public DependencyStatus loopTest() {
		
		DependencyStatus status = DependencyStatus.UNSATISFIED;
		
		return status;
	}


	public EventService getEventService() {
		return eventService;
	}


	public NotificationService getNotificationService() {
		return notificationService;
	}


	public PromiseService<REQUEST, RESPONSE> getPromiseService() {
		return promiseService;
	}
	
	public DefferedPromiseService<REQUEST, RESPONSE> getDefferedPromiseService() {
		return defferedPromiseService;
	}


	public PhasedPromiseService<REQUEST, RESPONSE> getPhasedPromiseService() {
		return phasedPromiseService;
	}


	public ScheduledPromiseService<REQUEST, RESPONSE> getScheduledPromiseService() {
		return scheduledPromiseService;
	}


	public StagePromiseService<REQUEST, RESPONSE> getStagedPromiseService() {
		return stagePromiseService;
	}
	

	public StreamPromiseService<REQUEST, RESPONSE> getStreamPromiseService() {
		return streamPromiseService;
	}


	public RegistryService getRegistryService() {
		return registryService;
	}


	public RoutingService getRoutingService() {
		return routingService;
	}

	

	public SubscriptionService getSubscriptionService() {
		return subscriptionService;
	}

	public ManagedService getManagedService() {
		return managedService;
	}
	
	public SecurityService getSecurityService() {
		return securityService;
	}

	public MapPromiseService<REQUEST, RESPONSE> getMapPromiseService() {
		return mapPromiseService;
	}
	

	@Override
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	@Override
	public String getFlowName() {
		return this.flowName;
	}

	@Override
	public void setFlowType(FlowType flowType) {
		this.flowType = flowType;
	}

	@Override
	public FlowType getFlowType() {
		return this.flowType;
	}

	@Override
	public void setFlowFunctionType(FlowFunctionType flowFunctionType) {
		this.flowFunctionType = flowFunctionType;
	}

	@Override
	public FlowFunctionType getFlowFunctionType() {
		return this.flowFunctionType;
	}

	@Override
	public void setFlowPublisherType(FlowPublisherType flowPublisherType) {
		this.flowPublisherType = flowPublisherType;
	}

	@Override
	public FlowPublisherType getFlowPublisherType() {
		return this.flowPublisherType;
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public boolean isEnabled() {
		return this.isEnabled;
	}

	@Override
	public void publishEvent(Event<EventModel> event) {
		subscriber.onNext(event);
	}

	@Override
	public void subscribe(Subscriber<? super Event<EventModel>> subscriber) {
		this.subscriber = subscriber;
	}

	public boolean isSubscribed() {
				
		return (this.subscriber != null);
	}

}
