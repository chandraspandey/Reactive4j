
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package example.flowr.promise.client.notification;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.event.pipeline.EventPipelineBus;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.node.EndPointDispatcher;
import org.flowr.framework.core.node.ha.Circuit;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.NotificationTask;
import org.flowr.framework.core.notification.dispatcher.AbstractDefaultAdapter;
import org.flowr.framework.core.notification.dispatcher.NotificationBufferQueue;
import org.flowr.framework.core.service.Service.ServiceState;
import org.flowr.framework.core.service.Service.ServiceStatus;
import org.flowr.framework.core.service.ServiceEndPoint;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.internal.NotificationService.NotificationServiceMode;
import org.flowr.framework.core.service.internal.NotificationService.NotificationServiceStatus;

import example.flowr.notification.NotificationProtocolDispatcher;


public class ClientNotificationAdapter extends AbstractDefaultAdapter {
    
    private AdapterFlowConfig flowConfig                = new AdapterFlowConfig(
                                                                this.getClass().getSimpleName(),
                                                                FlowPublisherType.FLOW_PUBLISHER_SERVER,
                                                                FlowFunctionType.DISSEMINATOR,
                                                                FlowType.ENDPOINT
                                                            );
    
    private AdapterNotificationConfig notificationConfig = new AdapterNotificationConfig(
                                                                this.getClass().getSimpleName(),
                                                                NotificationServiceMode.CENTRAL,
                                                                NotificationServiceAdapterType.CLIENT,
                                                                this
                                                            );

    private Subscriber<? super Event<EventModel>> subscriber = new ClientAdministrator();
    private ServiceStatus serviceStatus;
    private Circuit clientCircuit;
    private ExecutorService service                         = Executors.newSingleThreadScheduledExecutor();

    private boolean isEnabled                               = true;
    private Properties adapterProperties;
    private NotificationTask notificationTask;

    
    @Override
    public void configure(NotificationTask notificationTask) {

        try {
            
            this.notificationTask = notificationTask;
            clientCircuit   = ServiceFramework.getInstance().getCatalog().getHighAvailabilityService().getCircuit(
                                ConfigurationType.CLIENT);
           
            Map<NotificationProtocolType,EndPointDispatcher> dispatcherMap  = 
                    super.buildPipelineDispatcher(clientCircuit, new EventPipelineBus(), ConfigurationType.SERVER,
                    NotificationProtocolDispatcher.class);
            
            notificationTask.configure(dispatcherMap);            
            notificationConfig.setNotificationTask(notificationTask);       
        } catch (ConfigurationException e) {
            
            Logger.getRootLogger().error(e);
        }
    }
    

    
    
    @Override
    public void publishEvent(NotificationBufferQueue notificationBufferQueue) {
                
        this.notificationTask.setNotificationBufferQueue(notificationBufferQueue);
        
        Future<Map<NotificationProtocolType, NotificationServiceStatus>> notificationServiceFuture = 
                service.submit(this.notificationTask);
        
        Map<NotificationProtocolType, NotificationServiceStatus> statusMap = 
                processNotification(notificationServiceFuture);
        
        if(     statusMap != null && ! statusMap.isEmpty() &&
                (
                        statusMap.values().contains(NotificationServiceStatus.ERROR) ||
                        statusMap.values().contains(NotificationServiceStatus.TIMEOUT)
                )
        ){
            handleError(clientCircuit,statusMap);
        }
    }
    

    
    @Override
    public void suspendOperationOn(ServiceEndPoint serviceEndPoint,EndPointStatus health, 
            NotificationServiceStatus trigger) throws ConfigurationException {
        
        clientCircuit.removeServiceEndpoint(serviceEndPoint);
        subscriber.onNext(onServiceChange(serviceEndPoint,ServiceState.STOPPING));
    }

    @Override
    public void resumeOperationOn(ServiceEndPoint serviceEndPoint,EndPointStatus health, 
            NotificationServiceStatus trigger) throws ConfigurationException {

        clientCircuit.addServiceEndpoint(serviceEndPoint);
        subscriber.onNext(onServiceChange(serviceEndPoint,ServiceState.STARTING));
    }


    @Override
    public ServiceStatus startup(Optional<Properties> configProperties) {
                
        if(notificationConfig.getMode() == NotificationServiceMode.CENTRAL){
            
            service = Executors.newSingleThreadExecutor();
        }else{
            service = Executors.newCachedThreadPool();
        }
        
        serviceStatus = ServiceStatus.STARTED;
        
        Logger.getRootLogger().info("ClientNotificationAdapter : startup : "+serviceStatus);
        return serviceStatus;
    }
    
    @Override
    public ServiceStatus shutdown(Optional<Properties> configProperties) {
                
        service.shutdown();
        serviceStatus = ServiceStatus.STOPPED;
        Logger.getRootLogger().info("ClientNotificationAdapter : shutdown : "+serviceStatus);
        return serviceStatus;
    }

    @Override
    public AdapterFlowConfig getAdapterFlowConfig() {
        
        return this.flowConfig;
    }

    @Override
    public AdapterNotificationConfig getAdapterNotificationConfig() {
        
        return this.notificationConfig;
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
    public void subscribe(Subscriber<? super Event<EventModel>> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public boolean isSubscribed() {
        
        return (this.subscriber != null);
    }
    
    @Override
    public void configureRuntime(Properties adapterProperties) throws ConfigurationException {
        
        this.adapterProperties = adapterProperties;
    }

    public Properties getAdapterProperties() {
        return adapterProperties;
    }

}

