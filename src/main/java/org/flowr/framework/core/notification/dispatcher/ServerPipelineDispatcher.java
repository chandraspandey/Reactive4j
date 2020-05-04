
/**
 * Extends EventPublisher to provide Notification Adapter configuration  required for notification.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.notification.dispatcher;

import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.ALL;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.HEALTH;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_SERVER_INTEGRATION;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_SERVER_SERVICE;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineType.TRANSFER;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.event.pipeline.EventBus;
import org.flowr.framework.core.event.pipeline.EventPipeline;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.EndPointDispatcher;
import org.flowr.framework.core.node.ha.Circuit;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
import org.flowr.framework.core.service.ServiceEndPoint;

public final class ServerPipelineDispatcher {
    
    private ServerPipelineDispatcher(){
        
    }
    
    private static EventPipeline createServerPipeline() {
        
        EventPipeline pipeline =  new EventPipeline();
        pipeline.setPipelineType(TRANSFER);
        pipeline.setEventType(EventType.SERVER);
        
        return pipeline;
    }
    
    private static void setPipelineAttributes(EventPipeline pipeline, String pipelineName,
            PipelineFunctionType pipelineFunctionType) {
        
        pipeline.setPipelineName(pipelineName);
        pipeline.setPipelineFunctionType(pipelineFunctionType);
    }

    public static Map<NotificationProtocolType, EndPointDispatcher> buildServerPipelineDispatcher(
            Circuit serverCircuit, EventBus eventBus,Class<? extends EndPointDispatcher> 
            notificationProtocolDispatcher )  throws ConfigurationException {
        
        Map<NotificationProtocolType,EndPointDispatcher> dispatcherMap  = new HashMap<>();

        if(serverCircuit != null && eventBus != null) {
        
            Arrays.asList(ServerNotificationProtocolType.values()).forEach(
                    
                (ServerNotificationProtocolType protocolType) ->{
                    
                    EventPipeline pipeline  = createServerPipeline();
                    createServerPipelineAttributes(pipeline,protocolType);
                    
                    try {
                        
                        Collection<ServiceEndPoint> serviceEndPointList = 
                                serverCircuit.getAvailableServiceEndPoints(
                                protocolType);
                        EndPointDispatcher dispatcher = 
                                notificationProtocolDispatcher.getDeclaredConstructor().newInstance();
                        dispatcher.configure(protocolType, serviceEndPointList,pipeline);
                        
                        dispatcherMap.put(protocolType, dispatcher);
                        eventBus.addEventPipeline(pipeline);
                        
                    }catch (InstantiationException | IllegalAccessException |
                            IllegalArgumentException | InvocationTargetException
                            | NoSuchMethodException | SecurityException e) {
                        Logger.getRootLogger().error(e);
                    }
                }
            );
        }else {
            throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,"Circuit & EventBus not configured for pipeline creation.");
        }
        
        return dispatcherMap;
    }

    private static EventPipeline createServerPipelineAttributes(EventPipeline pipeline,
       ServerNotificationProtocolType protocolType) {
                                    
        switch(protocolType) {
           
            case SERVER_ALL:{                                                                  
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_PIPELINE_MANAGEMENT,
                        ALL); 
                break;
            }case SERVER_HEALTHCHECK:{
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_PIPELINE_HEALTH,
                        HEALTH);                     
                break;
            }case SERVER_INFORMATION:{
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_SERVICE_EVENT,
                        PIPELINE_SERVER_SERVICE); 
                break;
            }case SERVER_INTEGRATION:{
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_SERVICE_MANAGEMENT,
                        PIPELINE_SERVER_INTEGRATION); 
                break;
            }default:{
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_PIPELINE_MANAGEMENT,
                        ALL); 
                break;
            }                                        
        }

        return pipeline;
    }
    

}
