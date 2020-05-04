
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.internal;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.context.Context.RouteContext;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.exception.ClientException;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.ServerException;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.NotificationEventHelper;
import org.flowr.framework.core.notification.NotificationHelper;
import org.flowr.framework.core.notification.NotificationRoute;
import org.flowr.framework.core.notification.dispatcher.NotificationBufferQueue;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter.NotificationServiceAdapterType;
import org.flowr.framework.core.service.AbstractService;
import org.flowr.framework.core.service.ServiceListener;
import org.flowr.framework.core.service.dependency.Dependency.DependencyType;

public class NotificationServiceImpl extends AbstractService implements NotificationService,ServiceListener{

    private NotificationHelper notificationHelper               = new NotificationEventHelper();
    private EventPublisher  notificationServiceListener;
    private ServiceConfig serviceConfig                         = new ServiceConfig(
                                                                    true,
                                                                    ServiceUnit.SINGELTON,
                                                                    FrameworkConstants.FRAMEWORK_SERVICE_NOTIFICATION,
                                                                    ServiceType.NOTIFICATION,
                                                                    ServiceStatus.UNUSED,
                                                                    this.getClass().getSimpleName(),
                                                                    DependencyType.MANDATORY
                                                                );

    @Override
    public ServiceConfig getServiceConfig() {
    
        return this.serviceConfig;
    }
    
    @Override
    public void setNotificationRouteContext(RouteContext routeContext) throws ConfigurationException {
        this.notificationHelper.bindRoutes(routeContext.getRouteSet());
    }

    @Override
    public void publishEvent(NotificationBufferQueue notificationBufferQueue) throws ClientException{
        
        if(!notificationBufferQueue.isEmpty()) {
    
            List<NotificationServiceAdapter> adapterList = null;
            
            try {                       
            
                adapterList = notificationHelper.getNotificationRoute(notificationBufferQueue.getEventType());
                
                adapterList.forEach(k-> k.publishEvent(notificationBufferQueue));
                
            } catch (ServerException serverException) {
                Logger.getRootLogger().error(serverException);
            }
        }
    }
    
    @Override
    public List<NotificationServiceAdapter> getNotificationRoute(NotificationProtocolType notificationProtocolType,
            EventType eventType) throws ServerException{
        
        return notificationHelper.getNotificationRoute(notificationProtocolType, eventType);
    }
    
    @Override
    public Set<NotificationRoute> getNotificationRoutes() {
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
    public void removeServiceListener(EventPublisher notificationServiceListener) {
        this.notificationServiceListener = null;
    }   
    
    @Override
    public EventPublisher getServiceListener() {
        
        return notificationServiceListener;
    }

    @Override
    public ServiceStatus startup(Optional<Properties> configProperties) {
        
        Set<NotificationRoute> routeSet = notificationHelper.getNotificationRoutes();

        // Starts all the server adapter's for event processing 
        if(!routeSet.isEmpty()){
            
            routeSet.forEach(
                    
                ( NotificationRoute k)-> 
                    {
                        NotificationServiceAdapter adapter = k.getKey();
                        
                        if( adapter.getAdapterNotificationConfig().getAdapterType()
                                == NotificationServiceAdapterType.SERVER
                        ){
                            try {
                                k.getKey().startup(configProperties);
                            } catch (ConfigurationException e) {                                
                                Logger.getRootLogger().error(e);
                                Thread.currentThread().interrupt();
                            }
                            
                        }
                    }
            );
        }

        return ServiceStatus.STARTED;
    }

    @Override
    public ServiceStatus shutdown(Optional<Properties> configProperties) {
        
        Set<NotificationRoute> routeSet = notificationHelper.getNotificationRoutes();
        
        // Shuts all the server adapter's running for event processing 
        if(!routeSet.isEmpty()){
            
            routeSet.forEach(
                
               (NotificationRoute k)-> 
                    {
                        NotificationServiceAdapter adapter = k.getKey();
                        
                        if( adapter.getAdapterNotificationConfig().getAdapterType()
                                == NotificationServiceAdapterType.SERVER
                        ){
                            try {
                                k.getKey().shutdown(configProperties);
                            } catch (ConfigurationException e) {
                                Logger.getRootLogger().error(e);
                                Thread.currentThread().interrupt();
                            }
                            
                        }
                    }
            );
        }
        
        return ServiceStatus.STOPPED;
    }

    @Override
    public boolean isEnabled() {
        return this.serviceConfig.isEnabled();
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        this.serviceConfig.setEnabled(isEnabled);
    }
    
    @Override
    public String toString(){
        
        return "NotificationService{"+
                " | serviceConfig : "+serviceConfig+    
                " | \n notificationHelper : "+notificationHelper+
                " | \n notificationServiceListener : "+notificationServiceListener+
                super.toString()+  
                "}\n";
    }

}
