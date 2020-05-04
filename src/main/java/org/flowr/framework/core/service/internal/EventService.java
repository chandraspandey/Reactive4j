
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.internal;

import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_PIPELINE_ALL;
import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_PIPELINE_HEALTH;
import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_PIPELINE_INTEGRATION;
import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_PIPELINE_MANAGEMENT;
import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_PIPELINE_NETWORK_CLIENT;
import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_PIPELINE_NETWORK_SERVER;
import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_PIPELINE_NOTIFICATION;
import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE;
import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_DEFFERED;
import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_MAP;
import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_PHASED;
import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_SCHEDULED;
import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_STAGE;
import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_STREAM;
import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_PIPELINE_SERVICE;
import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_PIPELINE_USECASE;
import static org.flowr.framework.core.event.Event.EventType.CLIENT;
import static org.flowr.framework.core.event.Event.EventType.HEALTHCHECK;
import static org.flowr.framework.core.event.Event.EventType.SERVER;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.ALL;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.HEALTH;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.NOTIFICATION;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_CLIENT_SERVICE;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_MANAGEMENT_EVENT;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_NETWORK_CLIENT;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_NETWORK_SERVER;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_NOTIFICATION_EVENT;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_PROMISE_DEFFERED_EVENT;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_PROMISE_EVENT;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_PROMISE_MAP_EVENT;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_PROMISE_PHASED_EVENT;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_PROMISE_SCHEDULED_EVENT;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_PROMISE_STAGE_EVENT;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_PROMISE_STREAM_EVENT;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_SERVER_INTEGRATION;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.PIPELINE_SERVER_SERVICE;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType.USECASE;
import static org.flowr.framework.core.event.pipeline.Pipeline.PipelineType.TRANSFER;

import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.event.pipeline.EventBus;
import org.flowr.framework.core.event.pipeline.EventPipeline;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;
import org.flowr.framework.core.exception.ClientException;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.service.FrameworkService;

public interface EventService extends FrameworkService, Runnable{

    public enum EventRegistrationStatus{
        REGISTERED,
        UNREGISTERED
    }
    
    EventRegistrationStatus registerEventPipeline(String pipelineName,PipelineType pipelineType, PipelineFunctionType 
            pipelineFunctionType,EventPublisher eventPublisher);
    
    void process() throws ClientException;
    
    static EventService getInstance() {
        
        return DefaultEventService.getInstance();
    }
    
    public final class DefaultEventService{
        
        private static EventService eventService;
        
        private DefaultEventService() {
            
        }
        
        public static EventService getInstance() {
            
            if(eventService == null) {
                eventService = new EventServiceImpl();
            }
            
            return eventService;
        }        
    }
    
    public static final class PipelineMapper{
        
        private PipelineMapper() {
            
        }
        
        public static void addToEventBus(EventBus eventBus,PipelineFunctionType e) {
            
            switch(e) {
                
                case ALL:
                    createPipeline(eventBus,FRAMEWORK_PIPELINE_ALL, TRANSFER, ALL, CLIENT);
                    break;
                case HEALTH:{
                    createPipeline(eventBus,FRAMEWORK_PIPELINE_HEALTH, TRANSFER, HEALTH, HEALTHCHECK);
                    break;
                }case NOTIFICATION:{                    
                    createPipeline(eventBus,FRAMEWORK_PIPELINE_NOTIFICATION,TRANSFER,NOTIFICATION,CLIENT);
                    break;
                }case PIPELINE_CLIENT_SERVICE:{                    
                    createPipeline(eventBus,FRAMEWORK_PIPELINE_SERVICE,TRANSFER,PIPELINE_CLIENT_SERVICE,CLIENT);
                    break;
                }case PIPELINE_MANAGEMENT_EVENT:{
                    createPipeline(eventBus,FRAMEWORK_PIPELINE_MANAGEMENT,TRANSFER,PIPELINE_MANAGEMENT_EVENT,SERVER);
                    break;
                }case PIPELINE_NETWORK_CLIENT:{                    
                    createPipeline(eventBus,FRAMEWORK_PIPELINE_SERVICE,TRANSFER,PIPELINE_NETWORK_CLIENT,CLIENT);
                    break;
                }case PIPELINE_NETWORK_SERVER:{                    
                    createPipeline(eventBus,FRAMEWORK_PIPELINE_NETWORK_SERVER,TRANSFER,PIPELINE_NETWORK_SERVER,SERVER);
                    break;
                }case PIPELINE_NOTIFICATION_EVENT:{
                    createPipeline(eventBus,FRAMEWORK_PIPELINE_NETWORK_CLIENT,TRANSFER,PIPELINE_NOTIFICATION_EVENT,
                                    SERVER);
                    break;
                }case PIPELINE_PROMISE_DEFFERED_EVENT:{
                    createPipeline(eventBus,FRAMEWORK_PIPELINE_PROMISE_DEFFERED,TRANSFER,
                            PIPELINE_PROMISE_DEFFERED_EVENT, CLIENT);
                    break;
                }case PIPELINE_PROMISE_EVENT:{     
                    createPipeline(eventBus,FRAMEWORK_PIPELINE_PROMISE,TRANSFER,PIPELINE_PROMISE_EVENT,CLIENT);
                    break;
                }case PIPELINE_PROMISE_MAP_EVENT:{                    
                    createPipeline(eventBus,FRAMEWORK_PIPELINE_PROMISE_MAP,TRANSFER,PIPELINE_PROMISE_MAP_EVENT,CLIENT);
                    break;
                }case PIPELINE_PROMISE_PHASED_EVENT:{
                    createPipeline(eventBus,FRAMEWORK_PIPELINE_PROMISE_PHASED,TRANSFER,PIPELINE_PROMISE_PHASED_EVENT,
                                    CLIENT);
                     break;
                }case PIPELINE_PROMISE_SCHEDULED_EVENT:{
                    createPipeline(eventBus,FRAMEWORK_PIPELINE_PROMISE_SCHEDULED,TRANSFER,
                            PIPELINE_PROMISE_SCHEDULED_EVENT,CLIENT);                    
                    break;
                }case PIPELINE_PROMISE_STAGE_EVENT:{
                    createPipeline(eventBus,FRAMEWORK_PIPELINE_PROMISE_STAGE,TRANSFER,PIPELINE_PROMISE_STAGE_EVENT,
                                    CLIENT);
                     break;
                }case PIPELINE_PROMISE_STREAM_EVENT:{
                    createPipeline(eventBus,FRAMEWORK_PIPELINE_PROMISE_STREAM,TRANSFER,PIPELINE_PROMISE_STREAM_EVENT,
                                    CLIENT);
                    break;
                }case PIPELINE_SERVER_INTEGRATION:{
                    createPipeline(eventBus,FRAMEWORK_PIPELINE_INTEGRATION,TRANSFER,PIPELINE_SERVER_INTEGRATION,
                                    SERVER);
                    break;
                }case PIPELINE_SERVER_SERVICE:{
                    createPipeline(eventBus,FRAMEWORK_PIPELINE_SERVICE,TRANSFER,PIPELINE_SERVER_SERVICE,SERVER);
                    break;
                }case USECASE:{
                    createPipeline(eventBus,FRAMEWORK_PIPELINE_USECASE,TRANSFER,USECASE,CLIENT);
                    break;
                }default:
                    break;
            
            }
        }
        
        private static void createPipeline(EventBus eventBus, String pipelineName, PipelineType pipelineType, 
                PipelineFunctionType pipelineFunctionType, EventType eventType ) {
            
            EventPipeline processSubscriber  =  new EventPipeline();
            processSubscriber.setPipelineName(pipelineName);
            processSubscriber.setPipelineType(pipelineType);
            processSubscriber.setPipelineFunctionType(pipelineFunctionType); 
            processSubscriber.setEventType(eventType);
                
            eventBus.addEventPipeline(processSubscriber);
        }
    }
    
}
