
/**
 * Provides Route Mapping capabilities associated with notification routing capabilities for different mapped route 
 * part of Notification associated with NotificationServiceAdapter.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.notification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.ServerException;
import org.flowr.framework.core.notification.Notification.ClientNotificationProtocolType;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter.NotificationServiceAdapterType;

public class NotificationAdapterMapping {

    
    private Set<NotificationRoute> notificationRouteSet = 
            new HashSet<>();
    
    public Set<NotificationRoute> getRouteList() {
        return notificationRouteSet;
    }
    
    public void add(NotificationServiceAdapter adapter, NotificationProtocolType  notificationProtocolType) 
        throws ConfigurationException{
                
        NotificationProtocolType existingMapping = lookupMapping(adapter);
        
        if(existingMapping == null){
            
            NotificationRoute notificationRoute = new NotificationRoute();
            
            notificationRoute.set(adapter, notificationProtocolType);
            
            notificationRouteSet.add(notificationRoute) ;
                
        }else if(existingMapping instanceof ServerNotificationProtocolType){
            Logger.getRootLogger().info(" IGNORED : NotificationProtocolType : "+notificationProtocolType+
                    " already configured NotificationServiceAdapter instance with name : "+
                    adapter.getAdapterNotificationConfig().getAdapterName());
        }else if(existingMapping instanceof ClientNotificationProtocolType){
            Logger.getRootLogger().info(" IGNORED : NotificationProtocolType : "+notificationProtocolType+
                    " already configured for NotificationServiceAdapter instance with name : "+
                    adapter.getAdapterNotificationConfig().getAdapterName());
        }else{
                                
            throw new ConfigurationException(           
                    ErrorMap.ERR_CONFIG,"NotificationProtocolType : "+notificationProtocolType+
                " already configured for NotificationServiceAdapter instance with name : "+
                        adapter.getAdapterNotificationConfig().getAdapterName());

        }
    }
    
    
    public NotificationProtocolType lookupMapping(NotificationServiceAdapter notificationServiceAdapterInstance){
        
        NotificationProtocolType existingMapping = null;
        
        if(!notificationRouteSet.isEmpty()){
            
            Iterator<NotificationRoute> routeIterator = notificationRouteSet.iterator();
            
            while(routeIterator.hasNext()){
                
                NotificationRoute route = routeIterator.next();
                
                if(route.isKeyPresent(notificationServiceAdapterInstance)){
                    
                    existingMapping = route.getValues();
                    break;
                }
            }
            
        }
        
        return existingMapping;
    }
    
    public List<NotificationServiceAdapter> getRoute(NotificationProtocolType notificationProtocolType, 
        EventType eventType) throws ServerException{
        
        List<NotificationServiceAdapter> adapterList = null;        
        
        if(!notificationRouteSet.isEmpty()){
            
            if(notificationProtocolType != null){
            
                adapterList =  buildNoticationServiceAdapter(notificationProtocolType, eventType);
            }else{
    
                throw new ServerException(
                        ErrorMap.ERR_NOTIFICATION_PROTOCOL,
                        "Notification Protocol not defined for processing : notificationProtocolTypeList : "+
                        notificationProtocolType
                );
            }
        }else{

            throw new ServerException(
                    ErrorMap.ERR_SERVER_PROCESSING,
                    "Notification Route Not defined for processing : routeSet : "+notificationRouteSet
            );
        }
                
        return adapterList;
    }

    private List<NotificationServiceAdapter>  buildNoticationServiceAdapter(NotificationProtocolType 
        notificationProtocolType, EventType eventType) throws ServerException {
        
        ArrayList<NotificationServiceAdapter> adapterList = new ArrayList<>();
        
        Iterator<NotificationRoute> routeIterator = notificationRouteSet.iterator();
        
        while(routeIterator.hasNext()){
            
            NotificationRoute notificationRoute = routeIterator.next();
            
            if(notificationRoute.getKey().getAdapterNotificationConfig().getAdapterType().equalsType(eventType)){
                                        

                if(
                    notificationProtocolType == ServerNotificationProtocolType.SERVER_INFORMATION &&
                    notificationRoute.getKey().getAdapterNotificationConfig().getAdapterType() == 
                    NotificationServiceAdapterType.SERVER   
                ){
                    
                    adapterList.add(notificationRoute.getKey());
                    break;
                    
                }else if(notificationRoute.isValuePresent(notificationProtocolType)){
                
                    adapterList.add(notificationRoute.getKey());
                }else{
                    
                    throw new ServerException(
                         ErrorMap.ERR_SERVER_PROCESSING,
                        "Notification Route Not defined for notificationProtocolType : "+notificationProtocolType
                    );
                }
            }                               
        }
        
        if(adapterList.isEmpty()){
                        
            throw new ServerException(
                    ErrorMap.ERR_SERVER_PROCESSING,
                "Notification Route Not defined for event type : "+eventType
            );
        }
        
        return adapterList;
    }
    
    public List<NotificationServiceAdapter> getRoute(EventType eventType) throws ServerException{
            
        ArrayList<NotificationServiceAdapter> adapterList = new ArrayList<>();
        
        
        if(!notificationRouteSet.isEmpty()){
            
            Iterator<NotificationRoute> routeIterator = notificationRouteSet.iterator();
            
            while(routeIterator.hasNext()){
                
                NotificationRoute notificationRoute = routeIterator.next();
                
                if(notificationRoute.getKey().getAdapterNotificationConfig().getAdapterType().equalsType(eventType)){
                    
                    adapterList.add(notificationRoute.getKey());
                }                               
            }

        }else{

            throw new ServerException(
                    ErrorMap.ERR_SERVER_PROCESSING,
                    "Notification Route Not defined for processing : routeSet : "+notificationRouteSet
            );
        }
        
        if(adapterList.isEmpty()){
            
            throw new ServerException(
                    ErrorMap.ERR_SERVER_PROCESSING,
                    "Notification Route Not defined for event type : "+eventType
            );
        }
        Logger.getRootLogger().info(" NotificationAdapterMapping : "+eventType+ " | adapterList : "+adapterList);
        return adapterList;
    }

    
    public String toString(){
        
        return "NotificationAdapterMapping{"+
                " routeSet : "+notificationRouteSet+    
                "}\n";
    }
}
