package org.flowr.framework.core.service.route;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG;

import java.util.HashSet;
import java.util.Iterator;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.promise.ClientIdentity;
import org.flowr.framework.core.service.ServiceResponse;

/**
 * Provides Route Mapping capabilities associated with service routing capabilities for different mapped route part of
 * ServiceRequest associated with ServiceResponse.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ServiceRouteMapping<K,V> {
	
	private HashSet<ServiceRoute<Class<? extends ServiceResponse>>> routeSet = 
			new HashSet<ServiceRoute<Class<? extends ServiceResponse>>>();
	
	public void add(ClientIdentity  clientIdentity,Class<? extends ServiceResponse> responseClass) throws 
		ConfigurationException{
		
		Class<? extends ServiceResponse> existingMapping = lookupMapping(clientIdentity);
		
		if(existingMapping == null){
						
			ServiceRoute<Class<? extends ServiceResponse>> route = 
					new ServiceRoute<Class<? extends ServiceResponse>>();
			
			route.set(clientIdentity,responseClass);
			
			routeSet.add(route) ;
			
		}else{
			
			throw new ConfigurationException(
					ERR_CONFIG,
					MSG_CONFIG, 
					"Concurrent processing requires unique client registration. ClientIdentity : "+clientIdentity
						+" is already registered for routing with with "+existingMapping.getSimpleName());
		}
		
		//System.out.println(" RouteMapping : add : "+clientIdentity+" | "+responseClass.getName() +" | "+routeSet);
	}
	
	
	public Class<? extends ServiceResponse> lookupMapping(ClientIdentity  clientIdentity){
		
		Class<? extends ServiceResponse> existingMapping = null;
		
		if(!routeSet.isEmpty()){
			
			Iterator<ServiceRoute<Class<? extends ServiceResponse>>> routeIterator = 
					routeSet.iterator();
			
			while(routeIterator.hasNext()){
				
				ServiceRoute<Class<? extends ServiceResponse>> route = routeIterator.next();
				
				// Fix here
				
				if(route.isKeyPresent(clientIdentity)){
					
					existingMapping = route.getValue();
					break;
				}
			}
			
		}
		
		//System.out.println(" RouteMapping : lookupMapping : "+existingMapping);
		
		return existingMapping;
	}
	
	public Class<? extends ServiceResponse> getRoute(ClientIdentity  clientIdentity){
		
		Class<? extends ServiceResponse> responseClass = null;
		
		if(!routeSet.isEmpty()){
			
			Iterator<ServiceRoute<Class<? extends ServiceResponse>>> routeIterator = routeSet.iterator();
			
			while(routeIterator.hasNext()){
				
				ServiceRoute<Class<? extends ServiceResponse>> route = routeIterator.next();
				
				if(route.isKeyPresent(clientIdentity)){
					
					responseClass = route.getValue();
					break;
				}
			}
			
		}
		
		//System.out.println(" RouteMapping :  getRoute : "+responseClass);
		
		//System.out.println(" RouteMapping : "+this);
		
		return responseClass;
	}
	
	
	public String toString(){
		
		return "RouteMapping{"+
				" routeList : "+routeSet+	
				"}\n";
	}

}
