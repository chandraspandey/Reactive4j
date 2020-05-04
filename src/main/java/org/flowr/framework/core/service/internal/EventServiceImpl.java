
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.internal;

import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.event.pipeline.EventBus;
import org.flowr.framework.core.event.pipeline.EventPipeline;
import org.flowr.framework.core.event.pipeline.EventPipelineBus;
import org.flowr.framework.core.event.pipeline.EventPipelineBusExecutor;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;
import org.flowr.framework.core.exception.ClientException;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.service.AbstractService;
import org.flowr.framework.core.service.ServiceListener;
import org.flowr.framework.core.service.dependency.Dependency.DependencyType;

public final class EventServiceImpl extends AbstractService implements EventService,ServiceListener{

    private static EventBus eventBus                    = new EventPipelineBus();
    private EventPipelineBusExecutor eventBusExecutor;
    private ExecutorService service                     = Executors.newSingleThreadExecutor();
    private EventPublisher eventPublisher;
    private ServiceConfig serviceConfig                 = new ServiceConfig(
                                                            true,
                                                            ServiceUnit.SINGELTON,
                                                            FrameworkConstants.FRAMEWORK_SERVICE_EVENT,
                                                            ServiceType.EVENT,
                                                            ServiceStatus.UNUSED,
                                                            this.getClass().getSimpleName(),
                                                            DependencyType.MANDATORY
                                                        );

    public EventServiceImpl() {
        
        Arrays.asList(PipelineFunctionType.values()).forEach(
                
               (PipelineFunctionType e) -> PipelineMapper.addToEventBus(eventBus,e)
                
        );
        
        eventBusExecutor = new EventPipelineBusExecutor(eventBus);        
    }
    
    @Override
    public ServiceConfig getServiceConfig() {
    
        return this.serviceConfig;
    }
 
    
    public EventRegistrationStatus registerEventPipeline(String pipelineName,PipelineType pipelineType, 
        PipelineFunctionType pipelineFunctionType,EventPublisher eventPublisher) {       
         
         EventRegistrationStatus status = EventRegistrationStatus.UNREGISTERED;
         this.eventPublisher = eventPublisher;
     
         if(!eventPublisher.isSubscribed()) {
         
             EventPipeline processSubscriber  =  eventBus.lookup(pipelineName,pipelineType,pipelineFunctionType);
             
             if(processSubscriber != null) {
                 eventPublisher.subscribe(processSubscriber);
                 status = EventRegistrationStatus.REGISTERED;
             }
         }else {
             status = EventRegistrationStatus.REGISTERED;
         }
         
         return status;
     }
     
     public void process() throws ClientException {
         
         this.getServiceFramework().getCatalog().getNotificationService().notify(eventBusExecutor.process());
     }
    
    @Override
    public void run() {
        
        while(serviceConfig.getServiceStatus() != ServiceStatus.STOPPED) {
            
            try {
                process();
                Thread.sleep(FrameworkConstants.FRAMEWORK_PIPELINE_NOTIFICATION_TIME_UNIT);
            } catch (ClientException | InterruptedException e) {
                Logger.getRootLogger().error(e);
                Thread.currentThread().interrupt();
            }
        }
    }
    
 

    @Override
    public void addServiceListener(EventPublisher eventPublisher) {         
        this.eventPublisher = eventPublisher;
    }
    
    @Override
    public void removeServiceListener(EventPublisher serviceListener) {
        this.eventPublisher = null;
    }   
    
    @Override
    public EventPublisher getServiceListener() {
        
        return eventPublisher;
    }

    @Override
    public ServiceStatus startup(Optional<Properties> configProperties) {
        
        service.execute(this);
        return ServiceStatus.STARTED;
    }

    @Override
    public ServiceStatus shutdown(Optional<Properties> configProperties) {
        
        service.shutdown();
        return ServiceStatus.STOPPED;
    }
    
    @Override
    public String toString(){
        
        return "ConfigurationService{"+
                " | serviceConfig : "+serviceConfig+    
                " | \n eventPublisher : "+eventPublisher+
                super.toString()+  
                "}\n";
    }

}
