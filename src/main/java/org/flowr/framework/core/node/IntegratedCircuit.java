package org.flowr.framework.core.node;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.config.PipelineConfiguration;
import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.process.callback.Callback;
import org.flowr.framework.core.service.ServiceEndPoint;
import org.flowr.framework.core.service.ServiceFramework;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

  
public class IntegratedCircuit implements Circuit, Callback<SimpleEntry<ServiceEndPoint, EndPointStatus>> {

	private ScheduledExecutorService service 					= Executors.newSingleThreadScheduledExecutor();
	private HashMap<ServiceEndPoint,EndPointStatus> circuitMap 	= new HashMap<ServiceEndPoint,EndPointStatus>();
	private CircuitStatus circuitStatus							= CircuitStatus.UNAVAILABLE; 
	
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
				e.printStackTrace();
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
		
		Collection<ServiceEndPoint> endPointList = new ArrayList<ServiceEndPoint>();
		
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
		
		Collection<ServiceEndPoint> endPointList = new ArrayList<ServiceEndPoint>();
		
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
		
		Collection<ServiceEndPoint> endPointList = new ArrayList<ServiceEndPoint>();
		
		Iterator<Entry<ServiceEndPoint, EndPointStatus>> endPointIterator = circuitMap.entrySet().iterator();
		
		while(endPointIterator.hasNext()){
			
			Entry<ServiceEndPoint, EndPointStatus> endPointEntry = endPointIterator.next();
			
			if(
				endPointEntry.getValue() == EndPointStatus.REACHABLE && 
				endPointEntry.getKey().getNotificationProtocolType().equals(notificationProtocolType) &&
				endPointEntry.getKey().getPipelineFunctionType().equals(pipelineFunctionType)
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
					break;
				}			
				
			}
		}
		
		return circuitStatus;
	}
	
	@Override
	public void buildCircuit(ConfigurationType configurationType) throws ConfigurationException, 
		InterruptedException, ExecutionException{
				
		List<PipelineConfiguration> pipelineConfigurationList = ServiceFramework.getInstance().getConfigurationService().getPipelineConfiguration(configurationType);
		
		if(pipelineConfigurationList != null && !pipelineConfigurationList.isEmpty()){
		
			Iterator<PipelineConfiguration> pipelineConfigIterator = pipelineConfigurationList.iterator();
			
			while(pipelineConfigIterator.hasNext()){
				
				PipelineConfiguration pipelineConfiguration = pipelineConfigIterator.next();
			
				List<ServiceConfiguration> configurationList = pipelineConfiguration.getConfigurationList();
				
				if(configurationList != null && !configurationList.isEmpty()){
					
					Iterator<ServiceConfiguration> serviceConfigIterator = configurationList.iterator();
					
					while(serviceConfigIterator.hasNext()){
						
						ServiceConfiguration serviceConfiguration = serviceConfigIterator.next();
						
						if(serviceConfiguration.isValid()){
						
							ServiceEndPoint serviceEndPoint = new ServiceEndPoint(serviceConfiguration);
							
							handleEndPoint(serviceEndPoint);
			
						}else{
							
							throw new ConfigurationException(
									ERR_CONFIG,
									MSG_CONFIG, 
									"Service EndPoint Configurations not valid. configName "+serviceConfiguration);
		
						}
					}
		
				}else{
					
					throw new ConfigurationException(
							ERR_CONFIG,
							MSG_CONFIG, 
							"Service EndPoint Configurations not provided. configName "+configurationType);
				}	
				
				heartbeat();
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
	public SimpleEntry<ServiceEndPoint, EndPointStatus> doCallback(SimpleEntry<ServiceEndPoint, EndPointStatus> status) {

		
		circuitMap.put(status.getKey(),status.getValue());
		
		if(status.getValue() == EndPointStatus.UNREACHABLE) {

			System.out.println("IntegratedCircuit : heartbeat : status : "+status);
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
	
	public String toString(){
		
		return "IntegratedCircuit{"+
				" circuitMap : "+circuitMap+	
				"}\n";

	}


}
