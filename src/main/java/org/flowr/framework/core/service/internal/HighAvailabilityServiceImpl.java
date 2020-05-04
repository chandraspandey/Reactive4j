
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.internal;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.event.pipeline.EventPipeline;
import org.flowr.framework.core.event.pipeline.ManagedEventPipelineBusExecutor;
import org.flowr.framework.core.exception.ClientException;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.node.ha.Circuit;
import org.flowr.framework.core.node.ha.Circuit.CircuitStatus;
import org.flowr.framework.core.node.ha.IntegratedCircuit;
import org.flowr.framework.core.service.AbstractService;
import org.flowr.framework.core.service.ServiceEndPoint;
import org.flowr.framework.core.service.dependency.Dependency.DependencyStatus;
import org.flowr.framework.core.service.dependency.Dependency.DependencyType;
import org.flowr.framework.core.service.dependency.DependencyLoop;
import org.flowr.framework.core.service.internal.EventService.EventRegistrationStatus;

public class HighAvailabilityServiceImpl extends AbstractService implements HighAvailabilityService,DependencyLoop{

    private ManagedEventPipelineBusExecutor eventBusExecutor    = ManagedService.getDefaultEventPipelineBusExecutor();
    private Circuit clientCircuit;
    private Circuit serverCircuit; 
    private Circuit externalCircuit;
    private ExecutorService service                                 = Executors.newSingleThreadExecutor();
    
    private ServiceConfig serviceConfig                             = new ServiceConfig(
                                                                        true,
                                                                        ServiceUnit.SINGELTON,
                                                                        FrameworkConstants.FRAMEWORK_SERVICE_HEALTH,
                                                                        ServiceType.HEALTH,
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
    public Circuit getCircuit(ConfigurationType configurationType) {
        
        Circuit circuit = null;
        
        switch(configurationType) {
        
            case CLIENT:{
                circuit = clientCircuit;
                break;
            }case SERVER:{
                circuit = serverCircuit;
                break;
            }case EXTERNAL:{
                circuit = externalCircuit;
                break;
            }default:{
                break;
            }
        }
        
        return circuit;
    }
    
    public void addExternalCircuit(Circuit externalCircuit) {
        
        this.externalCircuit = externalCircuit;
    }
    
    
    @Override
    public void buildCircuit() throws ConfigurationException {
                
        Iterator<ConfigurationType> iter = Arrays.asList(ConfigurationType.values()).iterator();
                        
            while(iter.hasNext()) {
                
                ConfigurationType configurationType = iter.next();
                
                switch(configurationType) {
                    
                    case CLIENT:{
                        
                        clientCircuit = new IntegratedCircuit();
                        clientCircuit.buildCircuit(configurationType);   
                        break;
                    }case SERVER:{
                            
                        serverCircuit = new IntegratedCircuit();
                        serverCircuit.buildCircuit(configurationType);                       
                        break;
                    }default:{
                        break;
                    }               
                }               
            }
            
        
    }
    
    @Override
    public EndPointStatus addServiceEndpoint(ConfigurationType configurationType, ServiceEndPoint serviceEndPoint) 
            throws ConfigurationException {
        
        EndPointStatus status = EndPointStatus.UNREACHABLE;
        
        switch(configurationType) {
        
            case CLIENT:{
                status = clientCircuit.addServiceEndpoint(serviceEndPoint);
                break;
            }case SERVER:{  
                status = serverCircuit.addServiceEndpoint(serviceEndPoint);
                break;
            }default:{
                break;
            }   
        }
        
        return status;
    }
    
    @Override
    public EndPointStatus removeServiceEndpoint(ConfigurationType configurationType, ServiceEndPoint serviceEndPoint) 
            throws ConfigurationException {
        
        EndPointStatus status = EndPointStatus.UNREACHABLE;
        
        switch(configurationType) {
        
            case CLIENT:{
                status = clientCircuit.removeServiceEndpoint(serviceEndPoint);
                break;
            }case SERVER:{  
                status = serverCircuit.removeServiceEndpoint(serviceEndPoint);
                break;
            }default:{
                break;
            }   
        }
        
        return status;
    }
    
    private boolean isClientCircuitValid() {
        
        return ( clientCircuit != null && clientCircuit.getCircuitStatus() == CircuitStatus.AVAILABLE );
    }
    
    private boolean isServerCircuitValid() {
        
        return ( serverCircuit != null && serverCircuit.getCircuitStatus() == CircuitStatus.AVAILABLE);
    }
    
    private boolean isExternalCircuitValid() {
        
        return ( externalCircuit != null && externalCircuit.getCircuitStatus() == CircuitStatus.AVAILABLE);
    }

    @Override
    public DependencyStatus verify() {
            
        DependencyStatus status = DependencyStatus.UNSATISFIED;
        
        if( isClientCircuitValid() || isServerCircuitValid() || isExternalCircuitValid()) {
            status = DependencyStatus.SATISFIED;
        }        
        return status;
    }

    @Override
    public ServiceStatus startup(Optional<Properties> configProperties) {
        
        ServiceStatus serviceStatus = ServiceStatus.UNUSED;
        
        try {
            service.execute(this);
            buildCircuit();
            serviceStatus = ServiceStatus.STARTED;
        } catch (ConfigurationException e) {
            Logger.getRootLogger().error(e);    
            serviceStatus = ServiceStatus.ERROR;
        }
        return serviceStatus;
    }

    @Override
    public ServiceStatus shutdown(Optional<Properties> configProperties) {
    
        if(isClientCircuitValid()) {
            clientCircuit.shutdownCircuit();
        }
        
        if(isServerCircuitValid()) {
            serverCircuit.shutdownCircuit();
        }
        
        if(isExternalCircuitValid()) {
            externalCircuit.shutdownCircuit();
        }        
        
        service.shutdown();
        
        return ServiceStatus.STOPPED;
    }
    
    @Override
    public String toString(){
        
        return "HighAvailabilityService{"+
                " | serviceConfig : "+serviceConfig+    
                " | \n clientCircuit : "+clientCircuit+
                " | \n serverCircuit : "+serverCircuit+
                " | \n externalCircuit : "+externalCircuit+
                super.toString()+  
                "}\n";
    }

}
