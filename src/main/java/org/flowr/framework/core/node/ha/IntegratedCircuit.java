
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.ha;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.config.PipelineConfiguration;
import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.process.callback.Callback;
import org.flowr.framework.core.service.ServiceEndPoint;
import org.flowr.framework.core.service.ServiceFramework;
  
public class IntegratedCircuit implements Circuit, Callback<SimpleEntry<ServiceEndPoint, EndPointStatus>> {

    private ScheduledExecutorService service                    = Executors.newSingleThreadScheduledExecutor();
    private HashMap<ServiceEndPoint,EndPointStatus> circuitMap  = new HashMap<>();
    private CircuitStatus circuitStatus                         = CircuitStatus.UNAVAILABLE; 
    private String dependencyName                               = Circuit.class.getSimpleName();
    private DependencyType dependencyType                       = DependencyType.MANDATORY;
    
    @Override
    public Collection<ServiceEndPoint> getAllServiceEndPoints(){
                
        return circuitMap.keySet();
    }
    
    @Override
    public EndPointStatus addServiceEndpoint(ServiceEndPoint serviceEndPoint) {
        
        EndPointStatus status = EndPointStatus.UNREACHABLE;
        
        handleEndPoint(serviceEndPoint);
        
        boolean isNegotiated = serviceEndPoint.isNegotiated();
        
        while(!isNegotiated){
            
            try {
                Thread.sleep(2000);
                isNegotiated = serviceEndPoint.isNegotiated();
            } catch (InterruptedException e) {
                Logger.getRootLogger().error(e);
                Thread.currentThread().interrupt();
            }
        }
        
        if(circuitMap.get(serviceEndPoint) != null && circuitMap.get(serviceEndPoint) == EndPointStatus.REACHABLE) {
            status = EndPointStatus.ADDED;
        }
        
        return status;
    }
    
    @Override
    public EndPointStatus removeServiceEndpoint(ServiceEndPoint serviceEndpoint) {
        
        circuitMap.remove(serviceEndpoint);
                
        return EndPointStatus.REMOVED;
    }
    
    
    @Override
    public Collection<ServiceEndPoint> getAllAvailableServiceEndPoints(){
        
        Collection<ServiceEndPoint> endPointList = new ArrayList<>();
        
        Iterator<Entry<ServiceEndPoint, EndPointStatus>> endPointIterator = circuitMap.entrySet().iterator();
        
        while(endPointIterator.hasNext()){
            
            Entry<ServiceEndPoint, EndPointStatus> endPointEntry = endPointIterator.next();
            
            if( endPointEntry.getValue() == EndPointStatus.REACHABLE ){
                
                endPointList.add(endPointEntry.getKey());
            }           
            
        }
        
        return endPointList;
    }
    
    @Override
    public Collection<ServiceEndPoint> getAvailableServiceEndPoints(NotificationProtocolType notificationProtocolType){
        
        Collection<ServiceEndPoint> endPointList = new ArrayList<>();
        
        Iterator<Entry<ServiceEndPoint, EndPointStatus>> endPointIterator = circuitMap.entrySet().iterator();
        
        while(endPointIterator.hasNext()){
            
            Entry<ServiceEndPoint, EndPointStatus> endPointEntry = endPointIterator.next();
            
            if(
                endPointEntry.getValue() == EndPointStatus.REACHABLE && 
                endPointEntry.getKey().getNotificationProtocolType().equals(notificationProtocolType)){
                
                endPointList.add(endPointEntry.getKey());
            }           
            
        }
        
        return endPointList;
    }
    

    
    @Override
    public Collection<ServiceEndPoint> getAvailableServiceEndPoints(NotificationProtocolType notificationProtocolType,
        PipelineFunctionType pipelineFunctionType){
        
        Collection<ServiceEndPoint> endPointList = new ArrayList<>();
        
        Iterator<Entry<ServiceEndPoint, EndPointStatus>> endPointIterator = circuitMap.entrySet().iterator();
        
        while(endPointIterator.hasNext()){
            
            Entry<ServiceEndPoint, EndPointStatus> endPointEntry = endPointIterator.next();
            
            if(
                endPointEntry.getValue() == EndPointStatus.REACHABLE && 
                endPointEntry.getKey().getNotificationProtocolType().equals(notificationProtocolType) &&
                endPointEntry.getKey().getPipelineFunctionType() == pipelineFunctionType
            ){
                
                endPointList.add(endPointEntry.getKey());
            }           
            
        }
        
        return endPointList;
    }
    
    @Override
    public CircuitStatus getCircuitStatus(){
        
        if(!circuitMap.isEmpty()){
            
            Iterator<Entry<ServiceEndPoint, EndPointStatus>> endPointIterator = circuitMap.entrySet().iterator();
            
            while(endPointIterator.hasNext()){
                
                Entry<ServiceEndPoint, EndPointStatus> endPointEntry = endPointIterator.next();
                
                if( endPointEntry.getValue() == EndPointStatus.REACHABLE ){
                    
                    circuitStatus = CircuitStatus.AVAILABLE;
                }else {
                    circuitStatus = CircuitStatus.UNAVAILABLE;
                    break;
                }
                
            }
        }
        
        return circuitStatus;
    }
    
    @Override
    public void buildCircuit(ConfigurationType configurationType) throws ConfigurationException{
                
        List<PipelineConfiguration> pipelineConfigurationList = ServiceFramework.getInstance().getCatalog()
                .getConfigurationService().getPipelineConfiguration(configurationType);
        
        if(pipelineConfigurationList != null && !pipelineConfigurationList.isEmpty()){
        
            Iterator<PipelineConfiguration> pipelineConfigIterator = pipelineConfigurationList.iterator();
            
            while(pipelineConfigIterator.hasNext()){
                
                PipelineConfiguration pipelineConfiguration     = pipelineConfigIterator.next();
            
                List<ServiceConfiguration> configurationList    = pipelineConfiguration.getConfigurationList();
                
                if(configurationList != null && !configurationList.isEmpty()){
                    
                    handleEndPoints(configurationList);
        
                }else{
                    
                    throw new ConfigurationException(
                            ErrorMap.ERR_CONFIG,
                            "Service EndPoint Configurations not provided. configName : "+configurationType
                    );
                }   
                
                heartbeat();
            }
        
        }
    }

    private void handleEndPoints(List<ServiceConfiguration> configurationList) throws ConfigurationException {
        
        Iterator<ServiceConfiguration> serviceConfigIterator = configurationList.iterator();
        
        while(serviceConfigIterator.hasNext()){
            
            ServiceConfiguration serviceConfiguration = serviceConfigIterator.next();
            
            if(serviceConfiguration.isValid()){
            
                ServiceEndPoint serviceEndPoint = new ServiceEndPoint(serviceConfiguration);
                
                handleEndPoint(serviceEndPoint);

            }else{
                
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Service EndPoint Configurations not valid. "+serviceConfiguration
                );

            }
        }
    }
    
    @Override
    public EndPointStatus handleEndPoint(ServiceEndPoint serviceEndPoint) {
        
        EndPointStatus endPointStatus = EndPointStatus.NEGOTIATE;

        register(serviceEndPoint);
        
        service.execute(serviceEndPoint);
            
        return endPointStatus;
    }
    
    @Override
    public void heartbeat() {
        
        if(circuitMap != null && ! circuitMap.isEmpty()){
            
            Iterator<ServiceEndPoint> serviceEndPointIterator = circuitMap.keySet().iterator();
            
            while(serviceEndPointIterator.hasNext()){
            
                ServiceEndPoint serviceEndPoint = serviceEndPointIterator.next();
    
                service.scheduleAtFixedRate(
                        serviceEndPoint, 
                        0, 
                        serviceEndPoint.getServiceConfiguration().getHeartbeatInterval(), 
                        TimeUnit.MILLISECONDS
                );

            }
        }
        
    }
    
    @Override
    public void register(Callback<SimpleEntry<ServiceEndPoint, EndPointStatus>> callback) {
        callback.register(this);
    }

    @Override
    public SimpleEntry<ServiceEndPoint, EndPointStatus> doCallback(
            SimpleEntry<ServiceEndPoint, EndPointStatus> status) {

        
        circuitMap.put(status.getKey(),status.getValue());
        
        if(status.getValue() == EndPointStatus.UNREACHABLE) {

            Logger.getRootLogger().info("IntegratedCircuit : heartbeat : status : "+status);
        }
        
        return status;
    }
    
    @Override
    public void shutdownCircuit(){
        
        service.shutdown();
    }
    
    @Override
    public void setCircuitStatus(CircuitStatus circuitStatus) {
        this.circuitStatus = circuitStatus;
    }
    


    @Override
    public String getDependencyName() {
        return this.dependencyName;
    }

    @Override
    public DependencyType getDependencyType() {
        return this.dependencyType;
    }

    @Override
    public DependencyStatus verify() {
        
        DependencyStatus dependencyStatus = DependencyStatus.UNSATISFIED;
        
        if( getCircuitStatus() == CircuitStatus.AVAILABLE  ) {
            dependencyStatus = DependencyStatus.SATISFIED;
        }
        
        return dependencyStatus;
    }

    public String toString(){
        
        return "IntegratedCircuit{"+
                " circuitMap : "+circuitMap+    
                "}\n";

    }
}
