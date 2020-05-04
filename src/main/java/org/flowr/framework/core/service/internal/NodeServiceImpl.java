
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.internal;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.flowr.framework.api.Node;
import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.event.pipeline.EventPipeline;
import org.flowr.framework.core.event.pipeline.ManagedEventPipelineBusExecutor;
import org.flowr.framework.core.exception.ClientException;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.service.AbstractService;
import org.flowr.framework.core.service.dependency.Dependency.DependencyStatus;
import org.flowr.framework.core.service.dependency.Dependency.DependencyType;
import org.flowr.framework.core.service.dependency.DependencyLoop;
import org.flowr.framework.core.service.internal.EventService.EventRegistrationStatus;

public class NodeServiceImpl extends AbstractService implements NodeService,DependencyLoop{

    private ManagedEventPipelineBusExecutor eventBusExecutor = ManagedService.getDefaultEventPipelineBusExecutor();
    private ExecutorService service                                 = Executors.newSingleThreadExecutor();
    private Node node;
    
    private ServiceConfig serviceConfig                             = new ServiceConfig(
                                                                        true,
                                                                        ServiceUnit.SINGELTON,
                                                                        FrameworkConstants.FRAMEWORK_SERVICE_NODE,
                                                                        ServiceType.NODE,
                                                                        ServiceStatus.UNUSED,
                                                                        this.getClass().getSimpleName(),
                                                                        DependencyType.MANDATORY
                                                                    );

    @Override
    public ServiceConfig getServiceConfig() {
    
        return this.serviceConfig;
    }
    
    public EventRegistrationStatus registerEventPipeline() {        
        
        EventRegistrationStatus status = EventRegistrationStatus.UNREGISTERED;
    
        if(!this.getManagedProcessHandler().isSubscribed()) {
        
            EventPipeline processSubscriber  =  ManagedService.getDefaultEventPipeline();
            
            if(processSubscriber != null) {
                this.getManagedProcessHandler().subscribe(processSubscriber);
                status = EventRegistrationStatus.REGISTERED;
            }
        }else {
            status = EventRegistrationStatus.REGISTERED;
        }       
        return status;
    }
    
    
    @Override
    public void run() {
        
        while(this.serviceConfig.getServiceStatus() != ServiceStatus.STOPPED) {
            
            try {
                process();
                Thread.sleep(FrameworkConstants.FRAMEWORK_PIPELINE_NOTIFICATION_TIME_UNIT);
            } catch (ClientException | InterruptedException e) {
                Logger.getRootLogger().error(e);
                Thread.currentThread().interrupt();
            }
        }
    }
    
   
    
    public void process() throws ClientException {
        
        this.getServiceFramework().getCatalog().getNotificationService().notify(eventBusExecutor.process());
    }
    
    @Override
    public void setNode(Node node) {        
        this.node = node;
    }
    
    @Override
    public Node getNode() {     
        return this.node;
    }

  
    @Override
    public DependencyStatus verify() {
    
        DependencyStatus status = DependencyStatus.UNSATISFIED;
        
        if(this.getSecurityHandler() != null && this.getServiceHandler() != null && 
                this.getPersistenceHandler() != null) {
            status = DependencyStatus.SATISFIED;
        }
        
        return status;
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
    public Process lookupProcess(String executable) throws ConfigurationException {

        return this.getNodeProcessHandler().lookupProcess(executable);
    }
    
    @Override
    public InputStream processIn(String executable) throws ConfigurationException {

        return this.getNodeProcessHandler().processIn(executable);
    }
    
    @Override
    public OutputStream processOut(String executable) throws ConfigurationException {
        return this.getNodeProcessHandler().processOut(executable);
    }

    @Override
    public InputStream processError(String executable) throws ConfigurationException {
        return this.getNodeProcessHandler().processError(executable);
    }

    @Override
    public String toString(){
        
        return "NodeService{"+
                " | serviceConfig : "+serviceConfig+    
                " | \n node : "+node+
                super.toString()+  
                "}\n";
    }


}
