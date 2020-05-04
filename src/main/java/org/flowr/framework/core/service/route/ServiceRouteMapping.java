
/**
 * Provides Route Mapping capabilities associated with service routing capabilities for different mapped route part of
 * ServiceRequest associated with ServiceResponse.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.service.route;

import java.util.HashSet;
import java.util.Iterator;

import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.security.ClientIdentity;
import org.flowr.framework.core.service.ServiceResponse;

public class ServiceRouteMapping {
    
    private HashSet<ServiceRoute> routeSet = new HashSet<>();
    
    public void add(ClientIdentity  clientIdentity,Class<? extends ServiceResponse> responseClass) throws 
        ConfigurationException{
        
        Class<? extends ServiceResponse> existingMapping = lookupMapping(clientIdentity);
        
        if(existingMapping == null){
                        
            ServiceRoute route = new ServiceRoute();
            
            route.set(clientIdentity,responseClass);
            
            routeSet.add(route) ;
            
        }else{
            
            throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,
                    "Concurrent processing requires unique client registration. ClientIdentity : "+clientIdentity
                        +" is already registered for routing with with "+existingMapping.getSimpleName());
        }
    }
    
    
    public Class<? extends ServiceResponse> lookupMapping(ClientIdentity  clientIdentity){
        
        Class<? extends ServiceResponse> existingMapping = null;
        
        if(!routeSet.isEmpty()){
            
            Iterator<ServiceRoute> routeIterator = routeSet.iterator();
            
            while(routeIterator.hasNext()){
                
                ServiceRoute route = routeIterator.next();
                
                // Fix here
                
                if(route.isKeyPresent(clientIdentity)){
                    
                    existingMapping = route.getValue();
                    break;
                }
            }
            
        }
        
        return existingMapping;
    }
    
    public Class<? extends ServiceResponse> getRoute(ClientIdentity  clientIdentity){
        
        Class<? extends ServiceResponse> responseClass = null;
        
        if(!routeSet.isEmpty()){
            
            Iterator<ServiceRoute> routeIterator = routeSet.iterator();
            
            while(routeIterator.hasNext()){
                
                ServiceRoute route = routeIterator.next();
                
                if(route.isKeyPresent(clientIdentity)){
                    
                    responseClass = route.getValue();
                    break;
                }
            }
        }       
        
        return responseClass;
    }
    
    
    public String toString(){
        
        return "RouteMapping{"+" routeList : "+routeSet+"}\n";
    }

}
