
/**
 * Defines SubscriptionRegistry as a Registry with 1:N association between Client & NotificationSubscription. 

 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.notification.subscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.context.Context.PersistenceContext;
import org.flowr.framework.core.context.Context.SubscriptionContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.model.PersistenceProvider.PeristenceType;
import org.flowr.framework.core.model.Tuple;
import org.flowr.framework.core.notification.Notification.ClientNotificationProtocolType;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
import org.flowr.framework.core.notification.subscription.NotificationSubscription.SubscriptionStatus;
import org.flowr.framework.core.process.ProcessRegistry;
import org.flowr.framework.core.service.ServiceFramework;

public class SubscriptionRegistry implements ProcessRegistry<String,NotificationSubscription>, 
    SubscriptionLifecycle{

    private static HashMap<NotificationProtocolType,ArrayList<NotificationSubscription>> subscriptionMap = 
                new HashMap<>();
    private RegistryType registryType                                       = RegistryType.LOCAL;
    
    
    public SubscriptionRegistry() {
        
        Arrays.asList(ClientNotificationProtocolType.values()).forEach(
            
            c -> subscriptionMap.put(c,new ArrayList<NotificationSubscription>())
            
        );
        
        Arrays.asList(ServerNotificationProtocolType.values()).forEach(
            
            c -> subscriptionMap.put(c,new ArrayList<NotificationSubscription>())
            
        );
    }

    @Override
    public SubscriptionContext register(SubscriptionContext subscriptionContext) throws ConfigurationException {
        
        // reuse previous subscription
        String subcriptionId = subscriptionContext.getSubcriptionId();
                
        if(subcriptionId == null  ){        
            subcriptionId = UUID.randomUUID().toString();
        }
        
        subscriptionContext.setSubscriptionStatus(SubscriptionStatus.REQUESTED);
        
        if(subscriptionContext.getNotificationSubscription() != null){
        
            NotificationProtocolType notificationProtocolType = 
                    subscriptionContext.getNotificationSubscription().getNotificationProtocolType();
            
            if(notificationProtocolType != null){
        
                bind(subcriptionId,subscriptionContext.getNotificationSubscription());
                        
                subscriptionContext.setSubcriptionId(subcriptionId);
                subscriptionContext.setSubscriptionStatus(SubscriptionStatus.SUBSCRIBED);
            }else{
                
                throw new ConfigurationException(ErrorMap.ERR_NOTIFICATION_PROTOCOL, 
                        "NotificationProtocolType : "+notificationProtocolType);
            }
        }else{
            throw new ConfigurationException(ErrorMap.ERR_NOTIFICATION_SUBSCRIPTION, 
                   subscriptionContext.getNotificationSubscription().toString());
        }
                
        return subscriptionContext;
    }

    @Override
    public SubscriptionContext deregister(SubscriptionContext subscriptionContext) throws ConfigurationException {
        
        subscriptionContext.setSubscriptionStatus(SubscriptionStatus.REQUESTED);
        
        if(subscriptionContext.getNotificationSubscription() != null){
            
            NotificationProtocolType notificationProtocolType = 
                    subscriptionContext.getNotificationSubscription().getNotificationProtocolType();
            
            if(notificationProtocolType != null ){
        
                unbind(subscriptionContext.getSubcriptionId(),subscriptionContext.getNotificationSubscription());
                subscriptionContext.setSubscriptionStatus(SubscriptionStatus.UNSUBSCRIBED);
            }else{
                
                throw new ConfigurationException(ErrorMap.ERR_NOTIFICATION_PROTOCOL,
                        "NotificationProtocolTypeList : "+notificationProtocolType);
            }
        }else{
            throw new ConfigurationException(ErrorMap.ERR_NOTIFICATION_SUBSCRIPTION,
                    "NotificationSubscription : "+subscriptionContext.getNotificationSubscription());
        }
        
        return subscriptionContext;
    }
    
    @Override
    public SubscriptionContext change(SubscriptionContext subscriptionContext) throws ConfigurationException {
        
        String subcriptionId = subscriptionContext.getSubcriptionId();
        
        subscriptionContext.setSubscriptionStatus(SubscriptionStatus.REQUESTED);
        
        if( subcriptionId != null && subscriptionContext.getNotificationSubscription() != null){
        
            subcriptionId = UUID.randomUUID().toString();
            
            NotificationProtocolType notificationProtocolType = 
                    subscriptionContext.getNotificationSubscription().getNotificationProtocolType();
            
            if(notificationProtocolType != null){
        
                rebind(subcriptionId,subscriptionContext.getNotificationSubscription());
                        
                subscriptionContext.setSubcriptionId(subcriptionId);
                subscriptionContext.setSubscriptionStatus(SubscriptionStatus.CHANGED);
            }else{
                
                throw new ConfigurationException(ErrorMap.ERR_NOTIFICATION_PROTOCOL,
                        "NotificationProtocolType : "+notificationProtocolType);
            }
        }else{
            throw new ConfigurationException(ErrorMap.ERR_NOTIFICATION_SUBSCRIPTION,
                    "NotificationSubscription : "+subscriptionContext.getNotificationSubscription());
        }
                
        return subscriptionContext;
    }
    
            
    @Override
    public void bind(String subscriptionId, NotificationSubscription notificationSubscription) {
        
        subscriptionMap.get(notificationSubscription.getNotificationProtocolType()).add(notificationSubscription);
    }

    @Override
    public void unbind(String subscriptionId,NotificationSubscription notificationSubscription) {
        
        subscriptionMap.get(notificationSubscription.getNotificationProtocolType()).remove(notificationSubscription);
    }

    @Override
    public void rebind(String subscriptionId, NotificationSubscription notificationSubscription) {
        
        unbind(subscriptionId, notificationSubscription);
        bind(subscriptionId, notificationSubscription);
    }

    @Override
    public Collection<NotificationSubscription> getNotificationProtocolTypeList(
            NotificationProtocolType notificationProtocolType) {
        
        return subscriptionMap.get(notificationProtocolType);
    }
    
    @Override
    public Collection<NotificationSubscription> list() {
        
        Collection<NotificationSubscription> susbcriptions = new ArrayList<>();
        
        subscriptionMap.values().forEach(
                
                v -> susbcriptions.addAll(v)
                
        );
        
        return susbcriptions;
    }

    @Override
    public NotificationSubscription lookup(String subscriptionId) {

        NotificationSubscription notificationSubscription = null;
        
        Logger.getRootLogger().info("SubscriptionRegistry : subscriptionMap : "+subscriptionMap);
        
        Iterator<NotificationSubscription> iter = list().iterator();

        while(iter.hasNext()) {         
            
            NotificationSubscription subscription = iter.next();
            
            if(subscription.getSubscriptionId().equals(subscriptionId)) {
                
                notificationSubscription = subscription;
                break;
            }

        }
        
        return notificationSubscription;

    }

    @Override
    public void persist() throws ConfigurationException{
        
        PersistenceContext<String,HashMap<NotificationProtocolType,ArrayList<NotificationSubscription>>> context 
            = new PersistenceContext<>(
                    this.getClass().getCanonicalName(),
                PeristenceType.TUPLE
            );
        
        Tuple<String,HashMap<NotificationProtocolType,ArrayList<NotificationSubscription>>> tuple = 
                new Tuple<>(this.getClass().getCanonicalName(),subscriptionMap);
        
        Optional<Tuple<String,HashMap<NotificationProtocolType,ArrayList<NotificationSubscription>>>> tupleOption = 
                Optional.of(tuple);
        context.setTupleOption(tupleOption);
        
        ServiceFramework.getInstance().getCatalog().getNodeService().getPersistenceHandler().persist(context);
    }
    
    @Override
    public void restore() throws ConfigurationException{

        PersistenceContext<String,HashMap<NotificationProtocolType,ArrayList<NotificationSubscription>>> context = 
                ServiceFramework.getInstance().getCatalog().getNodeService().getPersistenceHandler()
                .restore(this.getClass().getCanonicalName());
        
        Optional<Tuple<String,HashMap<NotificationProtocolType,ArrayList<NotificationSubscription>>>> tupleOption = 
                context.getTupleOption();
        
        if( tupleOption.isPresent() && tupleOption.get().getKey().equals(this.getClass().getCanonicalName())) {
                        
            setHandlerMap(tupleOption.get().getValues());
        }else {
            
            throw new ConfigurationException(
                ErrorMap.ERR_CONFIG,
                "Restore Operation failed for Persistence configuration in : "+this.getClass().getCanonicalName());
        }

    }
    
    private static void setHandlerMap(HashMap<NotificationProtocolType,ArrayList<NotificationSubscription>> map) {
        subscriptionMap = map;
    }
    
    @Override
    public void clear() {
        subscriptionMap.clear();
    }


    @Override
    public RegistryType getRegistryType() {
        return this.registryType;
    }

    @Override
    public void setRegistryType(RegistryType registryType) {
        this.registryType = registryType;
    }

    public String toString(){
        
        return "SubscriptionRegistry{  registryType : "+registryType+" | "+
                "\n subscriptionMap : "+subscriptionMap+
                "\n}\n";
    }

}
