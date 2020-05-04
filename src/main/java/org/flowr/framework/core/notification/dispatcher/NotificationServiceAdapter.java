
/**
 * Extends EventPublisher to provide Notification Adapter configuration  required for notification.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.notification.dispatcher;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;

import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.event.pipeline.EventBus;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.flow.BatchEventPublisher;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.node.EndPointDispatcher;
import org.flowr.framework.core.node.ha.Circuit;
import org.flowr.framework.core.node.ha.CircuitBreaker;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.NotificationTask;
import org.flowr.framework.core.service.Service.ServiceState;
import org.flowr.framework.core.service.ServiceEndPoint;
import org.flowr.framework.core.service.ServiceLifecycle;
import org.flowr.framework.core.service.ServiceListener;
import org.flowr.framework.core.service.internal.NotificationService.NotificationServiceMode;
import org.flowr.framework.core.service.internal.NotificationService.NotificationServiceStatus;

public interface NotificationServiceAdapter extends BatchEventPublisher, ServiceLifecycle,CircuitBreaker{

    /*
     * Provides Event handling integration capability based on the Event type qualified as CLIENT or SERVER
     * CLIENT : Defined for discrete implementation of Notification servicing client side notification
     * SERVER : Defined for discrete implementation of Notification servicing server side notification
     */
    public enum NotificationServiceAdapterType{
        CLIENT,
        SERVER;
        
            
        public boolean equalsType(EventType eventType){
                
            boolean isEqual = false;
            
            if(this.name().equals(eventType.toString())){
                isEqual = true;
            }
            
            return isEqual;
        }
    }
    
    Event<EventModel> onServiceChange(ServiceEndPoint serviceEndPoint, ServiceState serviceState);
    
    AdapterNotificationConfig getAdapterNotificationConfig();
    
    Map<NotificationProtocolType,EndPointDispatcher> buildPipelineDispatcher(
            Circuit circuit,
            EventBus eventBus, ConfigurationType configurationType,
            Class<? extends EndPointDispatcher> dispatcher ) throws ConfigurationException; 
    
    /**
     * Weaves NotificationTask to the adapter to address different NotificationProtocolTypes
     * @param notificationTask
     */
    void configure(NotificationTask notificationTask);
    
    void configureRuntime(Properties adapterProperties) throws ConfigurationException;
    
    Map<NotificationProtocolType, NotificationServiceStatus> processNotification(
            Future<Map<NotificationProtocolType, NotificationServiceStatus>> notificationServiceFuture);

    void handleError(Circuit circuit, Map<NotificationProtocolType, NotificationServiceStatus> statusMap);
    
    void handleServiceEndPoint(NotificationServiceStatus v, ServiceEndPoint serviceEndPoint);
    
     
    /**
     * 
     * 
     * @author Chandra Shekhar Pandey
     * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
     */
    public class AdapterFlowConfig {

        private String flowName;
        private FlowPublisherType flowPublisherType;
        private FlowFunctionType flowFunctionType;  
        private FlowType flowType;
        
        public AdapterFlowConfig(String flowName, FlowPublisherType flowPublisherType, 
                FlowFunctionType flowFunctionType, FlowType flowType) {
       
            this.flowName = flowName;
            this.flowPublisherType = flowPublisherType;
            this.flowFunctionType = flowFunctionType;
            this.flowType = flowType;
        }
        
        public String getFlowName() {
            return flowName;
        }
        public FlowPublisherType getFlowPublisherType() {
            return flowPublisherType;
        }
        public FlowFunctionType getFlowFunctionType() {
            return flowFunctionType;
        }
        public FlowType getFlowType() {
            return flowType;
        }
        
        public String toString(){
            
            return "\n AdapterFlowConfig{"+
                    " flowName : "+flowName+"\n"+ 
                    " flowPublisherType : "+flowPublisherType+"\n"+   
                    " flowFunctionType : "+flowFunctionType+"\n"+   
                    " flowType : "+flowType+"\n"+   
                    " flowType : "+flowType+"\n"+
                    "}\n";
        }
        
    }
    
    public class AdapterNotificationConfig implements ServiceListener{
        
        private NotificationServiceMode mode;
        private NotificationTask notificationTask;
        private String adapterName;
        private NotificationServiceAdapterType adapterType;
        private EventPublisher serviceListener;
        
        public AdapterNotificationConfig(
                String adapterName,
                NotificationServiceMode mode, 
                NotificationServiceAdapterType adapterType,
                EventPublisher serviceListener
        ) {
            this.adapterName    = adapterName;
            this.mode           = mode;
            this.adapterType    = adapterType;
            this.serviceListener= serviceListener;
        }

        public NotificationTask getNotificationTask() {
            return notificationTask;
        }

        public void setNotificationTask(NotificationTask notificationTask) {
            this.notificationTask = notificationTask;
        }

        public NotificationServiceMode getMode() {
            return mode;
        }

        public String getAdapterName() {
            return adapterName;
        }

        public NotificationServiceAdapterType getAdapterType() {
            return adapterType;
        }

        public EventPublisher getServiceListener() {
            return serviceListener;
        }
        
        @Override
        public void addServiceListener(EventPublisher serviceListener) {
            this.serviceListener = serviceListener;
        }

        @Override
        public void removeServiceListener(EventPublisher serviceListener) {
            this.serviceListener = null;
        }

        public String toString(){
            
            return "\n AdapterNotificationConfig{"+
                    " adapterName : "+adapterName+"\n"+ 
                    " mode : "+mode+"\n"+ 
                    " notificationTask : "+notificationTask+"\n"+   
                    " adapterType : "+adapterType+"\n"+   
                    " serviceListener : "+serviceListener+"\n"+   
                    "}\n";
        }
  
    }



}
