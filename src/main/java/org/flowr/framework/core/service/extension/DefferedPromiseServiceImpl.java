
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.extension;

import java.util.Optional;
import java.util.Properties;

import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;
import org.flowr.framework.core.promise.deferred.DeferredPromiseHandler;
import org.flowr.framework.core.promise.deferred.DefferedPromise;
import org.flowr.framework.core.service.AbstractService;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.dependency.Dependency.DependencyType;

public class DefferedPromiseServiceImpl extends AbstractService implements DefferedPromiseService{

    private DeferredPromiseHandler promiseHandler       = new DeferredPromiseHandler();
    private ServiceConfig serviceConfig                 = new ServiceConfig(
                                                            true,
                                                            ServiceUnit.SINGELTON,
                                                            FrameworkConstants.FRAMEWORK_SERVICE_PROMISE_DEFFERED,
                                                            ServiceType.PROMISE_DEFFERED,
                                                            ServiceStatus.UNUSED,
                                                            this.getClass().getSimpleName(),
                                                            DependencyType.MANDATORY
                                                        );

    @Override
    public ServiceConfig getServiceConfig() {
    
        return this.serviceConfig;
    }

    @Override
    public void setServiceFramework(ServiceFramework serviceFramework) {
        
        super.setServiceFramework(serviceFramework);
        
        serviceFramework.getCatalog().getEventService().registerEventPipeline(
                FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_DEFFERED,
                PipelineType.TRANSFER, 
                PipelineFunctionType.PIPELINE_PROMISE_DEFFERED_EVENT 
                ,promiseHandler);
        
        serviceFramework.getCatalog().getEventService().registerEventPipeline(
                FrameworkConstants.FRAMEWORK_PIPELINE_MANAGEMENT,
                PipelineType.TRANSFER, 
                PipelineFunctionType.PIPELINE_MANAGEMENT_EVENT
                ,getManagedProcessHandler());
        
        promiseHandler.associateProcessHandler(getManagedProcessHandler());      

    }
    
 

    @Override
    public ServiceStatus startup(Optional<Properties> configProperties) {
        return ServiceStatus.STARTED;
    }

    @Override
    public ServiceStatus shutdown(Optional<Properties> configProperties) {
        return ServiceStatus.STOPPED;
    }


    @Override
    public DefferedPromise getPromise() {
        return this.promiseHandler;
    }
}
