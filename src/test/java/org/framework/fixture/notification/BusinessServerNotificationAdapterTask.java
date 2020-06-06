
/**
 * 
 * use private long timeout = 11000 for timeout scenario testing
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

package org.framework.fixture.notification;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.flowr.framework.core.event.ChangeEvent;
import org.flowr.framework.core.event.ChangeEventEntity;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.node.EndPointDispatcher;
import org.flowr.framework.core.node.io.network.ServiceMesh.ServiceTopologyMode;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.dispatcher.NotificationBufferQueue;
import org.flowr.framework.core.notification.NotificationTask;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.internal.NotificationService.NotificationServiceStatus;

public class BusinessServerNotificationAdapterTask implements NotificationTask {

     private NotificationBufferQueue notificationBufferQueue;
     private Map<NotificationProtocolType,EndPointDispatcher> dispatcherMap;
    
    @Override
    public void configureTopology(ServiceTopologyMode serviceTopologyMode, Properties topologyProperties) {
        
        if(serviceTopologyMode == ServiceTopologyMode.LOCAL){           
            
            Logger.getRootLogger().info("ServerNotificationAdapterTask  : serviceTopologyMode : " +serviceTopologyMode);
        }
    }
    
    @Override
    public void configure(Map<NotificationProtocolType,EndPointDispatcher> dispatcherMap) {

        this.dispatcherMap  = dispatcherMap;
    }

    @Override
    public Map<NotificationProtocolType, NotificationServiceStatus> call() throws Exception {

        return dispatch(notificationBufferQueue);
    }
    
    @Override
    public Map<NotificationProtocolType, NotificationServiceStatus> dispatch(
            NotificationBufferQueue notificationBufferQueue) {
        
        HashMap<NotificationProtocolType, NotificationServiceStatus> statusMap = new HashMap<>();
        
        Logger.getRootLogger().info(" ServerNotificationAdapterTask : notificationBufferQueue : "+
                notificationBufferQueue);
        
        // Call for external interface based on type for endPointList
        
        if(!notificationBufferQueue.isEmpty()) {
            
            Iterator<ChangeEventEntity> iter = notificationBufferQueue.iterator();
            
            while(iter.hasNext()) {
                
                ChangeEventEntity event =  iter.next();
                
                removeProcessedEvent(statusMap, iter, event);
            }
        }
        
        return statusMap;
    }

    private void removeProcessedEvent(HashMap<NotificationProtocolType, NotificationServiceStatus> statusMap,
            Iterator<ChangeEventEntity> iter, ChangeEventEntity event) {        
        
        String subscriptionId = deriveSubscriptionContext(event);               
            
        if(subscriptionId != null && !subscriptionId.isEmpty()) {
            
            SimpleEntry<NotificationProtocolType, NotificationServiceStatus> entry =
                    dispatchNotification( event, subscriptionId);
            
            if(entry != null && entry.getValue() == NotificationServiceStatus.EXECUTED) {
                statusMap.put(entry.getKey(), entry.getValue());
                iter.remove();
            }
        }
    }

    @Override
    public SimpleEntry<NotificationProtocolType, NotificationServiceStatus> dispatchNotification(
        ChangeEvent<EventModel> event, String subscriptionId) {
        
        SimpleEntry<NotificationProtocolType, NotificationServiceStatus> entry = null;
        
        NotificationServiceStatus notificationServiceStatus;        
        
        String hostName = event.getSubscriptionClientId();
        
        NotificationSubscription notificationSubscription = ServiceFramework.getInstance().getCatalog()
                .getSubscriptionService().lookup(subscriptionId);
        
        if(notificationSubscription != null) {
            
            NotificationProtocolType notificationProtocolType = notificationSubscription.getNotificationProtocolType();
            
            Logger.getRootLogger().info("NotificationDispath dispatcherMap : " +dispatcherMap);
            
            notificationServiceStatus = dispatcherMap.get(notificationProtocolType).dispatch(event);
                    
            entry = new SimpleEntry<>(notificationProtocolType, notificationServiceStatus);
            
            if(notificationServiceStatus!= NotificationServiceStatus.EXECUTED) {
                                
                Logger.getRootLogger().error("NotificationDispath Failed with status : " +notificationServiceStatus);
            }
        }else {

            Logger.getRootLogger().error("Unable to find NotificationSubscription for "+hostName+" for event : "
                    +event);
            
        }
        
        return entry;
    }

    @Override
    public String deriveSubscriptionContext(ChangeEvent<EventModel> event) {
           
        return ChangeEvent.deriveSubscriptionContext(event);
    }
    
    public void setNotificationBufferQueue(NotificationBufferQueue notificationBufferQueue) {
        this.notificationBufferQueue = notificationBufferQueue;
    }


}
