
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.context;

import java.util.Collection;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.flowr.framework.api.Builder;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.context.Context.AccessContext;
import org.flowr.framework.core.context.Context.EventContext;
import org.flowr.framework.core.context.Context.FilterContext;
import org.flowr.framework.core.context.Context.PersistenceContext;
import org.flowr.framework.core.context.Context.PolicyContext;
import org.flowr.framework.core.context.Context.ProcessContext;
import org.flowr.framework.core.context.Context.PromiseContext;
import org.flowr.framework.core.context.Context.ServerContext;
import org.flowr.framework.core.context.Context.ServiceContext;
import org.flowr.framework.core.context.Context.SubscriptionContext;
import org.flowr.framework.core.event.ReactiveMetaData;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.model.MetaData;
import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.model.Model.RequestModel;
import org.flowr.framework.core.model.PersistenceProvider.PeristenceType;
import org.flowr.framework.core.model.Tuple;
import org.flowr.framework.core.model.TupleSet;
import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.notification.subscription.NotificationSubscription.SubscriptionStatus;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;
import org.flowr.framework.core.promise.Promise.ScheduleStatus;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.security.Identity;
import org.flowr.framework.core.security.Scope;
import org.flowr.framework.core.security.access.filter.AccessSecurityFilter;
import org.flowr.framework.core.security.access.filter.Filter.FilterBy;
import org.flowr.framework.core.security.access.filter.Filter.FilterRetentionType;
import org.flowr.framework.core.security.access.filter.Filter.FilterType;
import org.flowr.framework.core.security.policy.Actor.PolicyActor;
import org.flowr.framework.core.security.policy.Policy.PolicyType;
import org.flowr.framework.core.security.policy.Policy.ViolationPolicy;
import org.flowr.framework.core.security.policy.Qualifier.QualifierOption;
import org.flowr.framework.core.service.Service.ServiceMode;
import org.flowr.framework.core.service.Service.ServiceState;
import org.flowr.framework.core.service.Service.ServiceStatus;
import org.flowr.framework.core.service.extension.PromiseService.PromiseServerState;
import org.flowr.framework.core.service.extension.PromiseService.PromiseServerStatus;

public interface ContextBuilder extends Builder {

    
    public class AccessContextBuilder implements Builder{

        private Identity identity;
        private Optional<FilterRetentionType> filterRetentionTypeOption = Optional.empty();
        private Optional<FilterBy> filterByOption                       = Optional.empty();
        private Optional<FilterType> filterTypeOption                   = Optional.empty();
        
        public AccessContextBuilder withIdentityAs(Identity identity) {
             
             this.identity = identity;
             return this;
        }
        
        public AccessContextBuilder withFilterRetentionTypeAs(Optional<FilterRetentionType> filterRetentionTypeOption){
            
            this.filterRetentionTypeOption = filterRetentionTypeOption;
            return this;
        }    
        
        public AccessContextBuilder withFilterByAs(Optional<FilterBy> filterByOption){
            
            this.filterByOption = filterByOption;
            return this;
        }   
        
        public AccessContextBuilder withFilterTypeAs(Optional<FilterType> filterTypeOption){
            
            this.filterTypeOption = filterTypeOption;
            return this;
        }  
       
        public AccessContext build() throws ConfigurationException{        
                   
            AccessContext accessContext = null;
            
            if(identity != null) {
                
                accessContext = new AccessContext(identity,filterRetentionTypeOption, filterByOption,filterTypeOption);
            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create AccessContext with inputs as : ");
                sb.append("\n identity                  : "+identity);
                sb.append("\n filterRetentionTypeOption : "+filterRetentionTypeOption);
                sb.append("\n filterByOption            : "+filterByOption);
                sb.append("\n filterTypeOption          : "+filterTypeOption);
                
                Logger.getRootLogger().error(sb.toString());
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for AccessContextBuilder. "
                );
            }
           
           return accessContext;
        }
    }
    
    public class PolicyContextBuilder implements Builder{

        private Identity identity;
        private Model policyConfig;
        private PolicyType policyType;
        private PolicyActor policyActor;
        private MetaData resourceMetaData;
        
        public PolicyContextBuilder withIdentityAs(Identity identity) {
             
             this.identity = identity;
             return this;
        }
        
        public PolicyContextBuilder withPolicyConfigAs(Model policyConfig){
            
            this.policyConfig = policyConfig;
            return this;
        }    
        
        public PolicyContextBuilder withPolicyTypeAs(PolicyType policyType){
            
            this.policyType = policyType;
            return this;
        }   
        
        public PolicyContextBuilder withPolicyActorAs(PolicyActor policyActor){
            
            this.policyActor = policyActor;
            return this;
        }  
        
        public PolicyContextBuilder withResourceMetaDataAs(MetaData resourceMetaData){
            
            this.resourceMetaData = resourceMetaData;
            return this;
        }
        
        private boolean isValidPolicy() {
            
            return (policyConfig != null && policyType != null && policyActor != null);
        }
       
        public PolicyContext build() throws ConfigurationException{        
                   
            PolicyContext policyContext = null;
            
            if(identity != null && isValidPolicy() ) {
                
                policyContext = new PolicyContext(identity,policyConfig, policyType,policyActor,resourceMetaData);
            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create PolicyContext with inputs as : ");
                sb.append("\n identity          : "+identity);
                sb.append("\n policyConfig      : "+policyConfig);
                sb.append("\n policyType        : "+policyType);
                sb.append("\n policyActor       : "+policyActor);
                
                Logger.getRootLogger().error(sb.toString());
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for PolicyContextBuilder. "
                );
            }
           
           return policyContext;
        }
    }
    
    public class ProcessContextBuilder implements Builder{

        private String serverIdentifier;
        private PromiseServerState serverState ;
        private PromiseServerStatus serverStatus ;  
        private String processIdentifier;
        private String serviceIdentifier;
        private NotificationDeliveryType notificationDeliveryType;
        private NotificationProtocolType notificationProtocolType; 
 
        public ProcessContextBuilder withServerIdentifierAs(String serverIdentifier){
            
            this.serverIdentifier = serverIdentifier;
            return this;
        }    
        
        public ProcessContextBuilder withProcessIdentifierAs(String processIdentifier){
            
            this.processIdentifier = processIdentifier;
            return this;
        }  
        
        public ProcessContextBuilder withServiceIdentifierAs(String serviceIdentifier){
            
            this.serviceIdentifier = serviceIdentifier;
            return this;
        }  
        
        public ProcessContextBuilder withPromiseServerStateAs(PromiseServerState serverState){
            
            this.serverState = serverState;
            return this;
        }   
        
        public ProcessContextBuilder withPromiseServerStatusAs(PromiseServerStatus serverStatus){
            
            this.serverStatus = serverStatus;
            return this;
        }  
        
        public ProcessContextBuilder withNotificationDeliveryTypeAs(NotificationDeliveryType notificationDeliveryType){
            
            this.notificationDeliveryType = notificationDeliveryType;
            return this;
        }
        
        public ProcessContextBuilder withNotificationProtocolTypeAs(NotificationProtocolType notificationProtocolType){
            
            this.notificationProtocolType = notificationProtocolType;
            return this;
        }
        
        private boolean hasValidIdentifier() {
            
            return (serverIdentifier != null && serviceIdentifier != null );
        }
        
        private boolean hasValidNotification() {
            
            return (notificationProtocolType != null && notificationDeliveryType != null );
        }
        
        private boolean hasValidPromise() {
            
            return (serverState != null && serverStatus != null && processIdentifier != null);
        }
       
        public ProcessContext build() throws ConfigurationException{        
                   
            ProcessContext processContext = null;
            
            if(hasValidPromise() && hasValidNotification() && hasValidIdentifier() ) {
                
                processContext = new ProcessContext();
                processContext.setProcessIdentifier(processIdentifier);
                processContext.setServiceIdentifier(serviceIdentifier);
                processContext.setServerIdentifier(serverIdentifier);
                processContext.setServerState(serverState);
                processContext.setServerStatus(serverStatus);
                processContext.setNotificationDeliveryType(notificationDeliveryType);
                processContext.setNotificationProtocolType(notificationProtocolType);
                
            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create ProcessContext with inputs as : ");
                sb.append("\n processIdentifier         : "+processIdentifier);
                sb.append("\n serviceIdentifier         : "+serviceIdentifier);
                sb.append("\n serverIdentifier          : "+serverIdentifier);
                sb.append("\n serverState               : "+serverState);
                sb.append("\n serverStatus              : "+serverStatus);
                sb.append("\n notificationDeliveryType  : "+serverState);
                sb.append("\n notificationProtocolType  : "+serverState);
                
                Logger.getRootLogger().error(sb.toString());
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for ProcessContextBuilder. "
                );
            }
           
           return processContext;
        }
    }

    public class PromiseContextBuilder implements Builder{

        private RequestScale requestScale;
        private boolean isTimelineRequired;
        private RequestModel deferableRequest;
        
        public PromiseContextBuilder withRequestScaleAs(RequestScale requestScale) {
             
             this.requestScale = requestScale;
             return this;
        }
        
        public PromiseContextBuilder withTimelineRequiredAs(boolean isTimelineRequired){
            
            this.isTimelineRequired = isTimelineRequired;
            return this;
        }    
        
        public PromiseContextBuilder withRequestModelAs(RequestModel deferableRequest){
            
            this.deferableRequest = deferableRequest;
            return this;
        }   
  
       
        public PromiseContext build() throws ConfigurationException{        
                   
            PromiseContext promiseContext = null;
            
            if(requestScale != null && deferableRequest != null ) {
                
                promiseContext = new PromiseContext(requestScale,isTimelineRequired, deferableRequest);
            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create PromiseContext with inputs as : ");
                sb.append("\n requestScale            : "+requestScale);
                sb.append("\n isTimelineRequired      : "+isTimelineRequired);
                sb.append("\n deferableRequest        : "+deferableRequest);
                
                Logger.getRootLogger().error(sb.toString());
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for PromiseContextBuilder. "
                );
            }
           
           return promiseContext;
        }
    }
    
    public class EventContextBuilder implements Builder{

        private String subscriptionClientId;
        private PromiseState promiseState;
        private PromiseStatus promiseStatus;
        private ScheduleStatus scheduleStatus;
        private NotificationProtocolType notificationProtocolType; 
        private NotificationDeliveryType notificationDeliveryType;
        private Object change; 
        private ReactiveMetaData reactiveMetaData;
 
        public EventContextBuilder withIdentifierAs(String subscriptionClientId){
            
            this.subscriptionClientId = subscriptionClientId;
            return this;
        }    
        
        public EventContextBuilder withPromiseStateAs(PromiseState promiseState){
            
            this.promiseState = promiseState;
            return this;
        }  
        
        public EventContextBuilder withPromiseStatusAs(PromiseStatus promiseStatus){
            
            this.promiseStatus = promiseStatus;
            return this;
        }  
        
        public EventContextBuilder withScheduleStatusAs(ScheduleStatus scheduleStatus){
            
            this.scheduleStatus = scheduleStatus;
            return this;
        }   
        
        public EventContextBuilder withNotificationProtocolTypeAs(NotificationProtocolType notificationProtocolType){
            
            this.notificationProtocolType = notificationProtocolType;
            return this;
        }  
        
        public EventContextBuilder withNotificationDeliveryTypeAs(NotificationDeliveryType notificationDeliveryType){
            
            this.notificationDeliveryType = notificationDeliveryType;
            return this;
        }
        
        public EventContextBuilder withChangeAs(Object change){
            
            this.change = change;
            return this;
        }
        
        public EventContextBuilder withReactiveMetaDataAs(ReactiveMetaData reactiveMetaData){
            
            this.reactiveMetaData = reactiveMetaData;
            return this;
        }
        
        private boolean hasValidIdentifier() {
            
            return (subscriptionClientId != null && change != null && notificationDeliveryType != null);
        }
      
        private boolean hasValidPromise() {
            
            return (promiseState != null || promiseStatus != null );
        }
       
        public EventContext build() throws ConfigurationException{        
                   
            EventContext eventContext = null;
            
            if(hasValidPromise() && hasValidIdentifier() ) {
                
                eventContext = new EventContext();
                eventContext.setSubscriptionClientId(subscriptionClientId);
                eventContext.setScheduleStatus(scheduleStatus);
                eventContext.setPromiseState(promiseState);
                eventContext.setPromiseStatus(promiseStatus);
                eventContext.setScheduleStatus(scheduleStatus);
                eventContext.setReactiveMetaData(reactiveMetaData);
                eventContext.setChange(change);
                eventContext.setNotificationDeliveryType(notificationDeliveryType);
                eventContext.setNotificationProtocolType(notificationProtocolType);
                
            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create EventContext with inputs as : ");
                sb.append("\n subscriptionClientId      : "+subscriptionClientId);
                sb.append("\n scheduleStatus            : "+scheduleStatus);
                sb.append("\n promiseState              : "+promiseState);
                sb.append("\n promiseStatus             : "+promiseStatus);
                sb.append("\n reactiveMetaData          : "+reactiveMetaData);
                sb.append("\n change                    : "+change);
                sb.append("\n notificationProtocolType  : "+notificationProtocolType);
                sb.append("\n notificationDeliveryType  : "+notificationDeliveryType);
                
                Logger.getRootLogger().error(sb.toString());
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for EventContextBuilder. "
                );
            }
           
           return eventContext;
        }
    }
    
    public class FilterContextBuilder implements Builder{

        private Model model;
        private Scope scope;        
        private Collection<AccessSecurityFilter>  accessFilters;
        private FilterType filterType;
        private FilterRetentionType filterRetentionType;
        private ViolationPolicy violationPolicy;
        private QualifierOption qualifierOption;
 
        public FilterContextBuilder withModelAs(Model model){
            
            this.model = model;
            return this;
        }    
        
        public FilterContextBuilder withScopeAs(Scope scope){
            
            this.scope = scope;
            return this;
        }  
        
        public FilterContextBuilder withAccessSecurityFilterAs(Collection<AccessSecurityFilter>  accessFilters){
            
            this.accessFilters = accessFilters;
            return this;
        }  
        
        public FilterContextBuilder withFilterTypeAs(FilterType filterType){
            
            this.filterType = filterType;
            return this;
        }   
        
        public FilterContextBuilder withFilterRetentionTypeAs(FilterRetentionType filterRetentionType){
            
            this.filterRetentionType = filterRetentionType;
            return this;
        }  
        
        public FilterContextBuilder withViolationPolicyAs(ViolationPolicy violationPolicy){
            
            this.violationPolicy = violationPolicy;
            return this;
        }
        
        public FilterContextBuilder withQualifierOptionAs(QualifierOption qualifierOption){
            
            this.qualifierOption = qualifierOption;
            return this;
        }
        
        private boolean hasValidModel() {
            
            return (model != null && scope != null );
        }
        
        private boolean hasValidFilter() {
            
            return (accessFilters != null && filterType != null && filterRetentionType != null);
        }
        
        private boolean hasValidPolicy() {
            
            return (violationPolicy != null && qualifierOption != null );
        }
       
        public FilterContext build() throws ConfigurationException{        
                   
            FilterContext filterContext = null;
            
            if(hasValidPolicy() && hasValidFilter() && hasValidModel() ) {
                
                filterContext = new FilterContext();
                filterContext.setModel(model);
                filterContext.setScope(scope);
                filterContext.setFilterType(filterType);
                filterContext.setAccessSecurityFilters(accessFilters);
                filterContext.setFilterRetentionType(filterRetentionType);
                filterContext.setQualifierOption(qualifierOption);
                filterContext.setViolationPolicy(violationPolicy);
                
            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create FilterContext with inputs as : ");
                sb.append("\n model                 : "+model);
                sb.append("\n scope                 : "+scope);
                sb.append("\n accessFilters         : "+accessFilters);
                sb.append("\n filterType            : "+filterType);
                sb.append("\n filterRetentionType   : "+filterRetentionType);
                sb.append("\n violationPolicy       : "+violationPolicy);
                sb.append("\n qualifierOption       : "+qualifierOption);

                
                Logger.getRootLogger().error(sb.toString());
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for FilterContextBuilder. "
                );
            }
           
           return filterContext;
        }
    }
    
    public class ServiceContextBuilder implements Builder{

        private String subscriptionClientId;
        private ServiceState serviceState;
        private ServiceMode serviceMode;
        private ServiceStatus serviceStatus;
        private NotificationProtocolType notificationProtocolType; 
        private String serverIdentifier;
 
        public ServiceContextBuilder withIdentifierAs(String subscriptionClientId){
            
            this.subscriptionClientId = subscriptionClientId;
            return this;
        }    
        
        public ServiceContextBuilder withServerIdentifierAs(String serverIdentifier){
            
            this.serverIdentifier = serverIdentifier;
            return this;
        }  
        
        public ServiceContextBuilder withServiceStateAs(ServiceState serviceState){
            
            this.serviceState = serviceState;
            return this;
        }  
        
        public ServiceContextBuilder withServiceModeAs(ServiceMode serviceMode){
            
            this.serviceMode = serviceMode;
            return this;
        }  
        
        public ServiceContextBuilder withServiceStatusAs(ServiceStatus serviceStatus){
            
            this.serviceStatus = serviceStatus;
            return this;
        }   
        
        public ServiceContextBuilder withNotificationProtocolTypeAs(NotificationProtocolType notificationProtocolType){
            
            this.notificationProtocolType = notificationProtocolType;
            return this;
        }  
         
        private boolean hasValidServiceAttribute() {
            
            return (serviceState != null && serviceMode != null && serviceStatus != null);
        }
        
        private boolean hasValidSubscription() {
            
            return (subscriptionClientId != null && serverIdentifier != null && notificationProtocolType != null);
        }
       
        public ServiceContext build() throws ConfigurationException{        
                   
            ServiceContext serviceContext = null;
            
            if(hasValidServiceAttribute() && hasValidSubscription() ) {
                
                serviceContext = new ServiceContext(subscriptionClientId,serviceState,serviceMode,serviceStatus,
                                    notificationProtocolType, serverIdentifier);
                
                
            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create ServiceContext with inputs as : ");
                sb.append("\n subscriptionClientId       : "+subscriptionClientId);
                sb.append("\n serviceState               : "+serviceState);
                sb.append("\n serviceMode                : "+serviceMode);
                sb.append("\n serviceStatus              : "+serviceStatus);
                sb.append("\n notificationProtocolType   : "+notificationProtocolType);
                sb.append("\n serverIdentifier           : "+serverIdentifier);
         
                
                Logger.getRootLogger().error(sb.toString());
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for ServiceContextBuilder. "
                );
            }
           
           return serviceContext;
        }
    }
    
    public class ServerContextBuilder implements Builder{

        private String subscriptionClientId;
        private ServiceState serviceState;
        private ServiceMode serviceMode;
        private ServiceStatus serviceStatus;
        private NotificationProtocolType notificationProtocolType; 
        private String serverIdentifier;
 
        public ServerContextBuilder withIdentifierAs(String subscriptionClientId){
            
            this.subscriptionClientId = subscriptionClientId;
            return this;
        }    
        
        public ServerContextBuilder withServerIdentifierAs(String serverIdentifier){
            
            this.serverIdentifier = serverIdentifier;
            return this;
        }  
        
        public ServerContextBuilder withServiceStateAs(ServiceState serviceState){
            
            this.serviceState = serviceState;
            return this;
        }  
        
        public ServerContextBuilder withServiceModeAs(ServiceMode serviceMode){
            
            this.serviceMode = serviceMode;
            return this;
        }  
        
        public ServerContextBuilder withServiceStatusAs(ServiceStatus serviceStatus){
            
            this.serviceStatus = serviceStatus;
            return this;
        }   
        
        public ServerContextBuilder withNotificationProtocolTypeAs(NotificationProtocolType notificationProtocolType){
            
            this.notificationProtocolType = notificationProtocolType;
            return this;
        }  
         
        private boolean hasValidServiceAttribute() {
            
            return (serviceState != null && serviceMode != null && serviceStatus != null);
        }
        
        private boolean hasValidSubscription() {
            
            return (subscriptionClientId != null && serverIdentifier != null && notificationProtocolType != null);
        }
       
        public ServerContext build() throws ConfigurationException{        
                   
            ServerContext serverContext = null;
            
            if(hasValidServiceAttribute() && hasValidSubscription() ) {
                
                serverContext = new ServerContext(subscriptionClientId,serviceState,serviceMode,serviceStatus,
                                    notificationProtocolType, serverIdentifier);
                
                
            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create ServerContext with inputs as : ");
                sb.append("\n subscriptionClientId       : "+subscriptionClientId);
                sb.append("\n serviceState               : "+serviceState);
                sb.append("\n serviceMode                : "+serviceMode);
                sb.append("\n serviceStatus              : "+serviceStatus);
                sb.append("\n notificationProtocolType   : "+notificationProtocolType);
                sb.append("\n serverIdentifier           : "+serverIdentifier);
         
                
                Logger.getRootLogger().error(sb.toString());
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for ServerContextBuilder. "
                );
            }
           
           return serverContext;
        }
    }
    
    public class PersistenceContextBuilder<K,V> implements Builder{

        private String canonicalClassName;
        private PeristenceType peristenceType;
        private Optional<Tuple<K,V>> tupleOption;
        private Optional<TupleSet<K,V>> tupleSetOption;
 
        public PersistenceContextBuilder<K,V> withClassNameAs(String canonicalClassName){
            
            this.canonicalClassName = canonicalClassName;
            return this;
        }    
        
        public PersistenceContextBuilder<K,V> withPeristenceTypeAs(PeristenceType peristenceType){
            
            this.peristenceType = peristenceType;
            return this;
        }  
        
        public PersistenceContextBuilder<K,V> withTupleOptionAs(Optional<Tuple<K,V>> tupleOption){
            
            this.tupleOption = tupleOption;
            return this;
        }  
        
        public PersistenceContextBuilder<K,V> withTupleSetOptionAs(Optional<TupleSet<K,V>> tupleSetOption){
            
            this.tupleSetOption = tupleSetOption;
            return this;
        }  
       
        public PersistenceContext<K,V> build() throws ConfigurationException{        
                   
            PersistenceContext<K,V> persistenceContext = null;
            
            if(canonicalClassName != null && peristenceType != null ) {
                
                persistenceContext = new PersistenceContext<>(canonicalClassName,peristenceType);
                persistenceContext.setTupleOption(tupleOption);
                persistenceContext.setTupleSetOption(tupleSetOption);
                
            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create PersistenceContext with inputs as : ");
                sb.append("\n canonicalClassName       : "+canonicalClassName);
                sb.append("\n peristenceType           : "+peristenceType);
                sb.append("\n tupleOption                : "+tupleOption);
                sb.append("\n tupleSetOption              : "+tupleSetOption);
         
                
                Logger.getRootLogger().error(sb.toString());
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for PersistenceContextBuilder. "
                );
            }
           
           return persistenceContext;
        }
    }
    
    public class SubscriptionContextBuilder implements Builder{

        private String subcriptionId;
        private SubscriptionStatus subscriptionStatus;
        private NotificationSubscription notificationSubscription;
 
        public SubscriptionContextBuilder withIdentifierAs(String subcriptionId){
            
            this.subcriptionId = subcriptionId;
            return this;
        }    
        
        public SubscriptionContextBuilder withSubscriptionStatusAs(SubscriptionStatus subscriptionStatus){
            
            this.subscriptionStatus = subscriptionStatus;
            return this;
        }  
        
        public SubscriptionContextBuilder withNotificationSubscriptionAs(NotificationSubscription 
                notificationSubscription){
            
            this.notificationSubscription = notificationSubscription;
            return this;
        }  
 
       
        public SubscriptionContext build() throws ConfigurationException{        
                   
            SubscriptionContext subscriptionContext = null;
            
            if(subcriptionId != null && subscriptionStatus != null && notificationSubscription != null) {
                
                subscriptionContext = new SubscriptionContext(subcriptionId,subscriptionStatus,
                                        notificationSubscription);
                
            }else {
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n Failed to create SubscriptionContext with inputs as : ");
                sb.append("\n subcriptionId                 : "+subcriptionId);
                sb.append("\n subscriptionStatus            : "+subscriptionStatus);
                sb.append("\n notificationSubscription      : "+notificationSubscription);
         
                
                Logger.getRootLogger().error(sb.toString());
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Mandatory Configuration not provided for SubscriptionContextBuilder. "
                );
            }
           
           return subscriptionContext;
        }
    }
}
