
/**
 * Extends EventPublisher to provide Notification Adapter configuration  required for notification.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.notification.dispatcher;

import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.HEALTH;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.NOTIFICATION;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_CLIENT_SERVICE;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_PROMISE_DEFFERED_EVENT;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_PROMISE_EVENT;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_PROMISE_MAP_EVENT;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_PROMISE_PHASED_EVENT;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_PROMISE_SCHEDULED_EVENT;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_PROMISE_STAGE_EVENT;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_PROMISE_STREAM_EVENT;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.USECASE;
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
import org.flowr.framework.core.notification.Notification.ClientNotificationProtocolType;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.service.ServiceEndPoint;

public final class ClientPipelineDispatcher {
    
    private ClientPipelineDispatcher() {
        
    }

    private static EventPipeline createClientPipeline() {
        
        EventPipeline pipeline =  new EventPipeline();
        pipeline.setPipelineType(TRANSFER);
        pipeline.setEventType(EventType.CLIENT);
        
        return pipeline;
    }

    public static Map<NotificationProtocolType,EndPointDispatcher> buildClientPipelineDispatcher(
            Circuit clientCircuit,EventBus eventBus,Class<? extends EndPointDispatcher> 
            notificationProtocolDispatcher ) throws ConfigurationException{
        
        
        HashMap<NotificationProtocolType,EndPointDispatcher> dispatcherMap  = new HashMap<>();

        if(clientCircuit != null && eventBus != null) {
                
            Arrays.asList(ClientNotificationProtocolType.values()).forEach(
                                        
               (ClientNotificationProtocolType protocolType) ->{
                        
                    try {   
                        
                        EventPipeline pipeline  = createClientPipeline();
                        createClientPipelineAttributes(pipeline,protocolType);
                        
                        Collection<ServiceEndPoint> serviceEndPointList = 
                                clientCircuit.getAvailableServiceEndPoints(
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
    
    private static EventPipeline createClientPipelineAttributes(EventPipeline pipeline,
        ClientNotificationProtocolType protocolType) {                        

        switch(protocolType) {
        
            case CLIENT_EMAIL:{
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_PIPELINE_EMAIL,USECASE);  
                break;
            }case CLIENT_HEALTHCHECK:{
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_PIPELINE_HEALTH,HEALTH);  
                break;
            }case CLIENT_INTEGRATION:{
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_PIPELINE_SERVICE,
                        PIPELINE_CLIENT_SERVICE);  
                break;
            }case CLIENT_INTEGRATION_PIPELINE_NOTIFICATION_EVENT:{
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_PIPELINE_NOTIFICATION,
                        NOTIFICATION);  
                break;
            }case CLIENT_INTEGRATION_PIPELINE_PROMISE_DEFFERED_EVENT:{
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_DEFFERED,
                        PIPELINE_PROMISE_DEFFERED_EVENT);  
                break;
            }case CLIENT_INTEGRATION_PIPELINE_PROMISE_EVENT:{
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE,
                        PIPELINE_PROMISE_EVENT); 
                 break;
            }case CLIENT_INTEGRATION_PIPELINE_PROMISE_MAP_EVENT:{
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_MAP,
                        PIPELINE_PROMISE_MAP_EVENT);                     
                 break;
            }case CLIENT_INTEGRATION_PIPELINE_PROMISE_PHASED_EVENT:{  
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_PHASED,
                        PIPELINE_PROMISE_PHASED_EVENT); 
                break;
            }case CLIENT_INTEGRATION_PIPELINE_PROMISE_SCHEDULED_EVENT:{
               
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_SCHEDULED,
                        PIPELINE_PROMISE_SCHEDULED_EVENT); 
                break;
            }case CLIENT_INTEGRATION_PIPELINE_PROMISE_STAGE_EVENT:{
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_STAGE,
                        PIPELINE_PROMISE_STAGE_EVENT); 
                  break;
            }case CLIENT_INTEGRATION_PIPELINE_PROMISE_STREAM_EVENT:{
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_STREAM,
                        PIPELINE_PROMISE_STREAM_EVENT); 
                 break;
            }case CLIENT_PUSH_NOTIFICATION:{
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_PIPELINE_PUSH_NOTIFICATION,
                        USECASE); 
                 break;
            }case CLIENT_SMS:{
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_PIPELINE_SMS,
                        USECASE); 
                  break;    
            }case CLIENT_SERVICE:{ 
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_PIPELINE_SERVICE,
                        PIPELINE_CLIENT_SERVICE); 
                break;    
            }default:{
                setPipelineAttributes(pipeline, FrameworkConstants.FRAMEWORK_PIPELINE_SERVICE,
                        PIPELINE_CLIENT_SERVICE);                      
                break;
            }
        }
        
        return pipeline;
    }
    
    private static void setPipelineAttributes(EventPipeline pipeline, String pipelineName,
            PipelineFunctionType pipelineFunctionType) {
        
        pipeline.setPipelineName(pipelineName);
        pipeline.setPipelineFunctionType(pipelineFunctionType);
    }
    
}
