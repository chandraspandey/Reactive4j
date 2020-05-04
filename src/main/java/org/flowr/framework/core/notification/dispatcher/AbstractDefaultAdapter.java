
/**
 * Extends EventPublisher to provide Notification Adapter configuration  required for notification.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.notification.dispatcher;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.context.Context;
import org.flowr.framework.core.context.Context.ServiceContext;
import org.flowr.framework.core.event.ChangeEvent;
import org.flowr.framework.core.event.ChangeEventEntity;
import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.event.pipeline.EventBus;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.node.EndPointDispatcher;
import org.flowr.framework.core.node.ha.Circuit;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
import org.flowr.framework.core.service.Service.ServiceMode;
import org.flowr.framework.core.service.Service.ServiceState;
import org.flowr.framework.core.service.ServiceEndPoint;
import org.flowr.framework.core.service.internal.NotificationService.NotificationServiceStatus;

public abstract class AbstractDefaultAdapter implements NotificationServiceAdapter{

    @Override
    public Event<EventModel> onServiceChange(ServiceEndPoint serviceEndPoint, ServiceState serviceState) {
    
        ChangeEvent<EventModel> changeEvent = new ChangeEventEntity();
                
        changeEvent.setSubscriptionClientId(serviceEndPoint.getServiceConfiguration().getNotificationEndPoint());
        
        changeEvent.setEventTimestamp(Timestamp.from(Instant.now()));
        
        EventModel eventModel = new EventModel();
        eventModel.setReactiveMetaData(null);
        
        
        ServiceContext serviceContext = Context.getServiceContext(
                serviceEndPoint.getServiceConfiguration().getNotificationEndPoint(), 
                ServiceMode.SERVER,
                serviceState,
                ServerNotificationProtocolType.SERVER_HEALTHCHECK);
        
        eventModel.setContext(serviceContext);      
        changeEvent.setChangedModel(eventModel);
        changeEvent.setEventType(EventType.HEALTHCHECK);
        
        return changeEvent;
    }
    
    @Override
    public Map<NotificationProtocolType,EndPointDispatcher> buildPipelineDispatcher(
        Circuit circuit,
        EventBus eventBus, ConfigurationType configurationType,
        Class<? extends EndPointDispatcher> dispatcher ) throws ConfigurationException{

        Map<NotificationProtocolType,EndPointDispatcher> dispatcherMap  = null;
        
        if(configurationType == ConfigurationType.CLIENT) {
            
            dispatcherMap   =  ClientPipelineDispatcher.buildClientPipelineDispatcher(circuit,eventBus,dispatcher);
        }
        
        if(configurationType == ConfigurationType.SERVER) {
            dispatcherMap   =  ServerPipelineDispatcher.buildServerPipelineDispatcher(circuit,eventBus,dispatcher);
        }
        
        return dispatcherMap;
    }
     
    @Override
    public Map<NotificationProtocolType, NotificationServiceStatus> processNotification(
            Future<Map<NotificationProtocolType, NotificationServiceStatus>> notificationServiceFuture){
        
        Map<NotificationProtocolType, NotificationServiceStatus> statusMap = null;
        
        while(!notificationServiceFuture.isDone()){
            
            try {
                    Thread.sleep(1000);
                
                    statusMap = notificationServiceFuture.get();
                
                Logger.getRootLogger().info("AbstractDefaultAdapter : notificationServiceStatus :"+statusMap);
            } catch (InterruptedException | ExecutionException e) {
                Logger.getRootLogger().error("AbstractDefaultAdapter : Service Unreachable : ", e);
                Thread.currentThread().interrupt();
            }
        }
        
        return statusMap;
    }
    
    @Override
    public void handleServiceEndPoint(NotificationServiceStatus v, ServiceEndPoint serviceEndPoint) {
        if(serviceEndPoint.getEndPointStatus() == EndPointStatus.UNREACHABLE ){
            
            try {
                suspendOperationOn(serviceEndPoint, serviceEndPoint.getEndPointStatus(),v);
            } catch (ConfigurationException e) {
                Logger.getRootLogger().error(e);
                Thread.currentThread().interrupt();
            }
        }
    }
    
    @Override
    public void handleError(Circuit circuit,Map<NotificationProtocolType, NotificationServiceStatus> statusMap){
        
        statusMap.forEach(
            
            (NotificationProtocolType k, NotificationServiceStatus v) -> {
                
                if(
                        v == NotificationServiceStatus.ERROR ||
                        v == NotificationServiceStatus.TIMEOUT
                ){
                                        
                    Collection<ServiceEndPoint> endPointList = circuit.getAvailableServiceEndPoints(k);
                    
                    Iterator<ServiceEndPoint> endPointIterator = endPointList.iterator();
                    
                    while(endPointIterator.hasNext()){
                        
                        ServiceEndPoint serviceEndPoint = endPointIterator.next();
                        
                        handleServiceEndPoint(v, serviceEndPoint);
                    }
                }
            }
        );            
    }

}
