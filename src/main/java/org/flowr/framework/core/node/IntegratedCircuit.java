package org.flowr.framework.core.node;

import java.util.AbstractMap.SimpleEntry;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG;

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

import org.flowr.framework.core.config.Configuration;
import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.process.callback.Callback;
import org.flowr.framework.core.service.ServiceEndPoint;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

  
public class IntegratedCircuit implements Circuit, Callback<SimpleEntry<ServiceEndPoint, EndPointStatus>> {

	private ScheduledExecutorService service 					= Executors.newSingleThreadScheduledExecutor();
	private HashMap<ServiceEndPoint,EndPointStatus> circuitMap 	= new HashMap<ServiceEndPoint,EndPointStatus>();
	private CircuitStatus circuitStatus							= CircuitStatus.UNAVAILABLE; 
	
	@Override
	public Collection<ServiceEndPoint> getAvailableServiceEndPoints(String endPointType){
		
		Collection<ServiceEndPoint> endPointList = new ArrayList<ServiceEndPoint>();
		
		Iterator<Entry<ServiceEndPoint, EndPointStatus>> endPointIterator = circuitMap.entrySet().iterator();
		
		while(endPointIterator.hasNext()){
			
			Entry<ServiceEndPoint, EndPointStatus> endPointEntry = endPointIterator.next();
			
			if(
				endPointEntry.getValue() == EndPointStatus.REACHABLE && 
				endPointEntry.getKey().getEndPointType().equals(endPointType)){
				
				endPointList.add(endPointEntry.getKey());
			}			
			
		}
		
		return endPointList;
	}
	
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
	public void buildCircuit(String configName, String filePath) throws ConfigurationException, 
		InterruptedException, ExecutionException{
		
		List<ServiceConfiguration> configurationList = Configuration.ClientEndPointConfiguration(configName,filePath);
		
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
							"Service EndPoint Configurations not provided. configName "+configName+" filePath : "+filePath);

				}
			}
			
			heartbeat();

		}else{
			
			throw new ConfigurationException(
					ERR_CONFIG,
					MSG_CONFIG, 
					"Service EndPoint Configurations not provided. configName "+configName+" filePath : "+filePath);
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

		System.out.println("IntegratedCircuit : heartbeat : status : "+status);
		circuitMap.put(status.getKey(),status.getValue());
		return null;
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
