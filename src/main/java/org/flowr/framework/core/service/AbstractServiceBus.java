
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.service;

import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_SERVICE;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Iterator;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.Flow.Subscriber;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.context.Context;
import org.flowr.framework.core.context.Context.ServerContext;
import org.flowr.framework.core.event.ChangeEvent;
import org.flowr.framework.core.event.ChangeEventEntity;
import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.ServiceException;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter.AdapterFlowConfig;
import org.flowr.framework.core.process.management.ManagedProcessHandler;
import org.flowr.framework.core.promise.PromiseTypeServer;
import org.flowr.framework.core.service.dependency.Dependency.DependencyStatus;
import org.flowr.framework.core.service.dependency.Dependency.DependencyType;
import org.flowr.framework.core.service.internal.ManagedService;

public abstract class AbstractServiceBus implements ServiceFramework, ServiceProvider{

    private Catalog catalog                                 = new ServiceCatalog();
    private static ServerContext serverContext              = Context.getServerContext(
                                                                PromiseTypeServer.serverIdentifier(), 
                                                                ServiceMode.SERVER,
                                                                ServiceState.INITIALIZING,
                                                                ServerNotificationProtocolType.SERVER_INFORMATION);
    
   
    private ServiceConfig serviceConfig                     = new ServiceConfig(
                                                                true,
                                                                ServiceUnit.REGISTRY,
                                                                FRAMEWORK_SERVICE,
                                                                ServiceType.FRAMEWORK,
                                                                ServiceStatus.UNUSED,
                                                                catalog.getClass().getSimpleName(),
                                                                DependencyType.MANDATORY
                                                            );
    private static ManagedProcessHandler processHandler     = ManagedService.getDefaultProcessHandler();
    private ServiceStatus frameworkServiceStatus            = ServiceStatus.UNUSED;
  
    private Subscriber<? super Event<EventModel>> subscriber;
    
 
    @Override
    public ServiceConfig getServiceConfig() {
        
        return this.serviceConfig;
    }
    
    public void setServerSubscriptionIdentifier(String subscriptionIdentifier) {
        
        serverContext.setSubscriptionClientId(subscriptionIdentifier);
    }
  
    @Override
    public Catalog buildCatalog() {
        
        this.catalog = this.catalog.postProcess(this);
        
        return this.catalog;
    } 
    
    @Override
    public Catalog getCatalog() {
        
        return this.catalog;
    } 
 

    @Override
    public Service lookup(ServiceType serviceType) {
        
        Service service = null;
        
        Iterator<ServiceFrameworkComponent> iter = this.catalog.getServiceList().iterator();
        
        while(iter.hasNext()) {
            
            Service svc = iter.next();
            
            if(svc.getServiceConfig().getServiceType() == serviceType) {
                
                service = svc;
                break;
            }
        }
        
        iter = this.catalog.getFrameworkServiceList().iterator();
        
        while(iter.hasNext()) {
            
            Service svc = iter.next();
            
            if(svc.getServiceConfig().getServiceType() == serviceType) {
                
                service = svc;
                break;
            }
        }
        
        return service;
    }
    
    @Override
    public Service lookup(ServiceType serviceType, String serviceName) {
        
        Service service = null;
        
        Iterator<ServiceFrameworkComponent> iter = this.catalog.getServiceList().iterator();
        
        while(iter.hasNext()) {
            
            Service svc = iter.next();
            
            if(svc.getServiceConfig().getServiceType() == serviceType && 
                    svc.getServiceConfig().getServiceName().equals(serviceName) ) {
                
                service = svc;
                break;
            }
        }
        
        iter = this.catalog.getFrameworkServiceList().iterator();
        
        while(iter.hasNext()) {
            
            Service svc = iter.next();
            
            if(svc.getServiceConfig().getServiceType() == serviceType && 
                    svc.getServiceConfig().getServiceName().equals(serviceName) ) {
                
                service = svc;
                break;
            }
        }
        
        return service;
    }
    
 

    @Override
    public void addService(ServiceFrameworkComponent service) throws ServiceException{
        
        if(lookup(service.getServiceConfig().getServiceType(),service.getServiceConfig().getServiceName()) == null) {
            this.catalog.getServiceList().add(service);
        }else {
            
            throw new ServiceException(ErrorMap.ERR_SERVICE_ALREADY_EXISTS, 
                    "Attempt to add duplicate service.");
        }
        
        
    }
    
    @Override
    public void removeService(ServiceFrameworkComponent service) {
        
        this.catalog.getServiceList().remove(service);
    }
   

    @Override
    public ServiceStatus startup(Optional<Properties> configProperties) throws ConfigurationException {
        
        buildCatalog();
        
        this.catalog.getServiceList().forEach(
            
           (Service s) -> {
                
                ServiceStatus serviceStatus;
                try {
                    serviceStatus = s.startup(configProperties);
                } catch (ConfigurationException e) {
                    Logger.getRootLogger().error(e);
                    Thread.currentThread().interrupt();
                    serviceStatus = ServiceStatus.ERROR;
                }
                s.getServiceConfig().setServiceStatus(serviceStatus);
                
                
                Logger.getRootLogger().info(s.getServiceConfig().getServiceName()+
                        FrameworkConstants.CONTENT_TAB_SPACE.substring(
                                s.getServiceConfig().getServiceName().length())+serviceStatus);
                    
                if(serviceStatus == ServiceStatus.ERROR) {
                    
                    s.getServiceConfig().setServiceStatus(serviceStatus);
                 }
            }
        );
        
        this.catalog.getFrameworkServiceList().forEach(
                
            (Service s) -> {
                 
                 ServiceStatus serviceStatus;
                try {
                    serviceStatus = s.startup(configProperties);
                } catch (ConfigurationException e) {
                    Logger.getRootLogger().error(e);
                    Thread.currentThread().interrupt();
                    serviceStatus = ServiceStatus.ERROR;
                }
                 s.getServiceConfig().setServiceStatus(serviceStatus);
                 
                 Logger.getRootLogger().info(s.getServiceConfig().getServiceName()+
                         FrameworkConstants.CONTENT_TAB_SPACE.substring(
                                 s.getServiceConfig().getServiceName().length())+serviceStatus);
                     
                 if(serviceStatus == ServiceStatus.ERROR) {
                     
                     s.getServiceConfig().setServiceStatus(serviceStatus);
                  }
             }
         );
        
        if(frameworkServiceStatus != ServiceStatus.ERROR) {
            frameworkServiceStatus = ServiceStatus.STARTED;
        }

        serverContext.setServiceState(ServiceState.STARTING);
        serverContext.setServiceStatus(frameworkServiceStatus);
        
        ChangeEvent<EventModel> changeEvent = new ChangeEventEntity();
        changeEvent.setEventTimestamp(new Timestamp(Instant.now().toEpochMilli()));
        changeEvent.setSubscriptionClientId(PromiseTypeServer.serverIdentifier());
        
        EventModel eventModel = new EventModel();
        eventModel.setContext(serverContext);
        changeEvent.setChangedModel(eventModel);
        changeEvent.setEventType(EventType.SERVER);
        
        publishEvent(changeEvent);
        
        Logger.getRootLogger().info(this.getServiceConfig().getServiceName()+" | "+frameworkServiceStatus);
        
        return frameworkServiceStatus;
    }

    @Override
    public ServiceStatus shutdown(Optional<Properties> configProperties) {
        
        this.catalog.getServiceList().forEach(
            
          (Service  s) -> {
                
                ServiceStatus serviceStatus;
                try {
                    serviceStatus = s.shutdown(configProperties);
                } catch (ConfigurationException e) {
                    Logger.getRootLogger().error(e);
                    Thread.currentThread().interrupt();
                    serviceStatus = ServiceStatus.ERROR;
                }
                s.getServiceConfig().setServiceStatus(serviceStatus);
                
                 Logger.getRootLogger().info(s.getServiceConfig().getServiceName()+
                         FrameworkConstants.CONTENT_TAB_SPACE.substring(
                                 s.getServiceConfig().getServiceName().length())+serviceStatus);
                
                
                if(serviceStatus == ServiceStatus.ERROR) {
                    frameworkServiceStatus = serviceStatus;
                }
            }
        );
        
        this.catalog.getFrameworkServiceList().forEach(
            (Service  s) -> {
                
                ServiceStatus serviceStatus;
                try {
                    serviceStatus = s.shutdown(configProperties);
                } catch (ConfigurationException e) {
                    Logger.getRootLogger().error(e);
                    Thread.currentThread().interrupt();
                    serviceStatus = ServiceStatus.ERROR;
                }
                s.getServiceConfig().setServiceStatus(serviceStatus);
                
                 Logger.getRootLogger().info(s.getServiceConfig().getServiceName()+
                         FrameworkConstants.CONTENT_TAB_SPACE.substring(
                                 s.getServiceConfig().getServiceName().length())+serviceStatus);  
                
                
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
        changeEvent.setEventTimestamp(new Timestamp(Instant.now().toEpochMilli()));
        changeEvent.setSubscriptionClientId(PromiseTypeServer.serverIdentifier());
        
        EventModel eventModel = new EventModel();
        eventModel.setContext(serverContext);
        changeEvent.setChangedModel(eventModel);
        changeEvent.setEventType(EventType.SERVER);
        
        publishEvent(changeEvent);
        
        Logger.getRootLogger().info(this.getServiceConfig().getServiceName()+" | "+frameworkServiceStatus);
        
        return frameworkServiceStatus;
    }

    @Override
    public DependencyStatus verify() {
            
        DependencyStatus status = DependencyStatus.UNSATISFIED;
        
        if(!this.catalog.getServiceList().isEmpty()) {
            
            status = DependencyStatus.SATISFIED;
        }
        
        return status;
    }
    
    @Override
    public AdapterFlowConfig getAdapterFlowConfig() {
        
        return new AdapterFlowConfig(
                this.getClass().getSimpleName(),
                FlowPublisherType.FLOW_PUBLISHER_CLIENT,
                FlowFunctionType.SERVER,
                FlowType.PROCESS
            );
    }

    @Override
    public void publishEvent(Event<EventModel> event) {
        
        processHandler.publishEvent(event);
    }

    @Override
    public void subscribe(Subscriber<? super Event<EventModel>> subscriber) {
        this.subscriber = subscriber;
    }

    public boolean isSubscribed() {
                
        return (this.subscriber != null);
    }

}
