package org.flowr.framework.core.service;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_SERVICE_ALREADY_EXISTS;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_SERVICE_ALREADY_EXISTS;
import static org.flowr.framework.core.constants.FrameworkConstants.FRAMEWORK_SERVICE;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Flow.Subscriber;

import org.flowr.framework.core.context.Context;
import org.flowr.framework.core.context.ServerContext;
import org.flowr.framework.core.event.ChangeEvent;
import org.flowr.framework.core.event.ChangeEventEntity;
import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.exception.ServiceException;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
import org.flowr.framework.core.process.management.ManagedProcessHandler;
import org.flowr.framework.core.promise.PromiseTypeServer;
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
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public abstract class ServiceBus<REQUEST,RESPONSE> implements ServiceFramework<REQUEST,RESPONSE>,
	ServiceProvider<REQUEST,RESPONSE>{

	private static ServerContext serverContext 				= Context.ServerContext(
																PromiseTypeServer.ServerIdentifier(), 
																ServiceMode.SERVER,
																ServiceState.INITIALIZING,
																ServerNotificationProtocolType.SERVER_INFORMATION);
	private static ManagedProcessHandler processHandler		= ManagedService.getDefaultProcessHandler();
	private ServiceUnit serviceUnit 						= ServiceUnit.REGISTRY;
	private String dependencyName							= ServiceBus.class.getSimpleName();
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
	private ConfigurationService configService				= ConfigurationService.getInstance();
	private AdministrationService adminService				= AdministrationService.getInstance();
	private HighAvailabilityService highAvailabilityService = HighAvailabilityService.getInstance();
	private NodeService nodeService 						= NodeService.getInstance();
	
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
		configService.setServiceFramework(this);
		adminService.setServiceFramework(this);
		highAvailabilityService.setServiceFramework(this);
		nodeService.setServiceFramework(this);
		
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
		this.serviceList.add(configService);
		this.serviceList.add(adminService);
		this.serviceList.add(highAvailabilityService);
		this.serviceList.add(nodeService);
	}
	
	public void setServerSubscriptionIdentifier(String subscriptionIdentifier) {
		
		serverContext.setSubscriptionClientId(subscriptionIdentifier);
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
				System.out.println(s.getServiceName()+" | "+serviceStatus);
				
				/*while(
						serviceStatus != ServiceStatus.STARTED ||
						serviceStatus != ServiceStatus.ERROR
				) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}*/
				
				if(serviceStatus == ServiceStatus.ERROR) {
					frameworkServiceStatus = serviceStatus;
				}
			}
		);
		
		if(frameworkServiceStatus != ServiceStatus.ERROR) {
			frameworkServiceStatus = ServiceStatus.STARTED;
		}

	/*	getEventService().registerEventPipeline(
				FrameworkConstants.FRAMEWORK_PIPELINE_MANAGEMENT,
				PipelineType.TRANSFER, 
				PipelineFunctionType.PIPELINE_MANAGEMENT_EVENT
				,processHandler);
		*/

		serverContext.setServiceState(ServiceState.STARTING);
		serverContext.setServiceStatus(frameworkServiceStatus);
		
		ChangeEvent<EventModel> changeEvent = new ChangeEventEntity();
		changeEvent.setEventTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		changeEvent.setSubscriptionClientId(PromiseTypeServer.ServerIdentifier());
		
		EventModel eventModel = new EventModel();
		eventModel.setContext(serverContext);
		changeEvent.setChangedModel(eventModel);
		changeEvent.setEventType(EventType.SERVER);
		
		publishEvent(changeEvent);
		
		System.out.println(this.getServiceName()+" | "+frameworkServiceStatus);
		
		return frameworkServiceStatus;
	}

	@Override
	public ServiceStatus shutdown(Properties configProperties) {
		
		this.serviceList.forEach(
			
			(s) -> {
				
				ServiceStatus serviceStatus = ((ServiceLifecycle)s).shutdown(configProperties);
				System.out.println(s.getServiceName()+" | "+serviceStatus);
				
				/*while(
						serviceStatus != ServiceStatus.STOPPED ||
						serviceStatus != ServiceStatus.ERROR
				) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}*/
				
				if(serviceStatus == ServiceStatus.ERROR) {
					frameworkServiceStatus = serviceStatus;
				}
			}
		);
		
		if(frameworkServiceStatus != ServiceStatus.ERROR) {
			frameworkServiceStatus = ServiceStatus.STOPPED;
		}

		serverContext.setServiceState(ServiceState.STOPPING);
		serverContext.setServiceStatus(frameworkServiceStatus);
		
		ChangeEvent<EventModel> changeEvent = new ChangeEventEntity();
		changeEvent.setEventTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		changeEvent.setSubscriptionClientId(PromiseTypeServer.ServerIdentifier());
		
		EventModel eventModel = new EventModel();
		eventModel.setContext(serverContext);
		changeEvent.setChangedModel(eventModel);
		changeEvent.setEventType(EventType.SERVER);
		
		publishEvent(changeEvent);
		
		System.out.println(this.getServiceName()+" | "+frameworkServiceStatus);
		
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

	@Override
	public EventService getEventService() {
		return eventService;
	}

	@Override
	public NotificationService getNotificationService() {
		return notificationService;
	}

	@Override
	public PromiseService<REQUEST, RESPONSE> getPromiseService() {
		return promiseService;
	}
	
	@Override
	public DefferedPromiseService<REQUEST, RESPONSE> getDefferedPromiseService() {
		return defferedPromiseService;
	}

	@Override
	public PhasedPromiseService<REQUEST, RESPONSE> getPhasedPromiseService() {
		return phasedPromiseService;
	}

	@Override
	public ScheduledPromiseService<REQUEST, RESPONSE> getScheduledPromiseService() {
		return scheduledPromiseService;
	}

	@Override
	public StagePromiseService<REQUEST, RESPONSE> getStagedPromiseService() {
		return stagePromiseService;
	}
	
	@Override
	public StreamPromiseService<REQUEST, RESPONSE> getStreamPromiseService() {
		return streamPromiseService;
	}

	@Override
	public RegistryService getRegistryService() {
		return registryService;
	}

	@Override
	public RoutingService getRoutingService() {
		return routingService;
	}
	
	@Override
	public SubscriptionService getSubscriptionService() {
		return subscriptionService;
	}

	@Override
	public ManagedService getManagedService() {
		return managedService;
	}
	
	@Override
	public SecurityService getSecurityService() {
		return securityService;
	}

	@Override
	public MapPromiseService<REQUEST, RESPONSE> getMapPromiseService() {
		return mapPromiseService;
	}

	@Override
	public ConfigurationService getConfigurationService() {
		return configService;
	}

	@Override
	public AdministrationService getAdministrationService() {
		return adminService;
	}

	@Override
	public HighAvailabilityService getHighAvailabilityService() {
		return highAvailabilityService;
	}
	
	@Override
	public NodeService getNodeService() {
		return nodeService;
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
		
		processHandler.publishEvent(event);
		//subscriber.onNext(event);
	}

	@Override
	public void subscribe(Subscriber<? super Event<EventModel>> subscriber) {
		this.subscriber = subscriber;
	}

	public boolean isSubscribed() {
				
		return (this.subscriber != null);
	}





}
