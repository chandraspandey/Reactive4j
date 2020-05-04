
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.context;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import org.flowr.framework.core.event.ReactiveMetaData;
import org.flowr.framework.core.model.MetaData;
import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.model.PersistenceProvider.PeristenceType;
import org.flowr.framework.core.model.Tuple;
import org.flowr.framework.core.model.TupleSet;
import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
import org.flowr.framework.core.notification.dispatcher.NotificationBufferQueue;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.notification.subscription.NotificationSubscription.SubscriptionStatus;
import org.flowr.framework.core.notification.NotificationRoute;
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
import org.flowr.framework.core.service.Service.ServiceType;
import org.flowr.framework.core.service.extension.PromiseService.PromiseServerState;
import org.flowr.framework.core.service.extension.PromiseService.PromiseServerStatus;



public interface Context extends Model{

    ServiceType getServiceType();
    
    static ProcessContext getProcessLifecycleContext(
            String serverIdentifier, 
            PromiseServerState promiseServerState,
            PromiseServerStatus promiseServerStatus,
            String serviceIdentifier,
            String processIdentifier){
        
        ProcessContext processContext = new ProcessContext();
        processContext.setServerIdentifier(serverIdentifier);
        processContext.setServerState(promiseServerState);
        processContext.setServerStatus(promiseServerStatus);
        processContext.setServiceIdentifier(serviceIdentifier);
        processContext.setProcessIdentifier(processIdentifier);         
        processContext.setNotificationDeliveryType(NotificationDeliveryType.INTERNAL);
        processContext.setNotificationProtocolType(ServerNotificationProtocolType.SERVER_INFORMATION);
        
        return processContext;
        
    }
    
    static ServiceContext getServiceContext(
            String subscriptionIdentifier, 
            ServiceMode serviceMode,
            ServiceState serviceState,
            ServerNotificationProtocolType serverNotificationProtocolType) {
        
        ServiceContext serviceContext = new ServiceContext();
        serviceContext.setSubscriptionClientId(subscriptionIdentifier);
        serviceContext.setServiceMode(serviceMode);
        serviceContext.setServiceState(serviceState);
        serviceContext.setNotificationProtocolType(serverNotificationProtocolType);
        
        return serviceContext;
        
    }
    
    static ServerContext getServerContext(
            String subscriptionIdentifier, 
            ServiceMode serviceMode,
            ServiceState serviceState,
            ServerNotificationProtocolType serverNotificationProtocolType) {
        
        ServerContext serverContext = new ServerContext();
        serverContext.setSubscriptionClientId(subscriptionIdentifier);
        serverContext.setServiceMode(serviceMode);
        serverContext.setServiceState(serviceState);
        serverContext.setNotificationProtocolType(serverNotificationProtocolType);
        
        return serverContext;
    }
    
    static AccessContext getAccessContext(
            Identity identity,Optional<FilterRetentionType> filterRetentionTypeOption,
            Optional<FilterBy> filterByOption,  Optional<FilterType> filterTypeOption) {
        
        return new AccessContext(identity,filterRetentionTypeOption,filterByOption,filterTypeOption);
    }

    public class AccessContext implements Context{

        private Identity identity;
        private Optional<FilterRetentionType> filterRetentionTypeOption;
        private Optional<FilterBy> filterByOption;
        private Optional<FilterType> filterTypeOption;
        
        public AccessContext(Identity identity) {
            
            this.identity = identity;
        }
        
        public AccessContext(Identity identity,Optional<FilterRetentionType> filterRetentionTypeOption,
                Optional<FilterBy> filterByOption,  Optional<FilterType> filterTypeOption) {
            
            this.identity                   = identity;
            this.filterRetentionTypeOption  = filterRetentionTypeOption;
            this.filterByOption             = filterByOption;
            this.filterTypeOption           = filterTypeOption;
        }
        
        @Override
        public ServiceType getServiceType() {
            return ServiceType.SECURITY;
        }
        
        public Identity getIdentity() {
            
            return this.identity;
        }

        public Optional<FilterRetentionType> getFilterRetentionTypeOption() {
            return filterRetentionTypeOption;
        }

        public Optional<FilterBy> getFilterByOption() {
            return filterByOption;
        }

        public Optional<FilterType> getFilterTypeOption() {
            return filterTypeOption;
        }

        public String toString(){
            
            return "AccessContext{"+
                    "\n identity : "+identity+
                    "\n filterRetentionTypeOption : "+filterRetentionTypeOption+
                    "\n filterByOption : "+filterByOption+
                    "\n filterTypeOption : "+filterTypeOption+
                    "\n}\n";
        }
    }
    
    public class PolicyContext implements Context{
    
        private Identity identity;
        private Model policyConfig;
        private PolicyType policyType;
        private PolicyActor policyActor;
        private MetaData resourceMetaData;
        
        public PolicyContext(Identity identity, Model policyConfig, PolicyType policyType, PolicyActor policyActor,
                MetaData resourceMetaData) {
            super();
            this.identity = identity;
            this.policyConfig = policyConfig;
            this.policyType = policyType;
            this.policyActor = policyActor;
            this.resourceMetaData = resourceMetaData;
        }
            
        public Identity getIdentity() {
            return identity;
        }
        public void setIdentity(Identity identity) {
            this.identity = identity;
        }
        public Model getPolicyConfig() {
            return policyConfig;
        }
        public void setPolicyConfig(Model policyConfig) {
            this.policyConfig = policyConfig;
        }
        public PolicyType getPolicyType() {
            return policyType;
        }
        public void setPolicyType(PolicyType policyType) {
            this.policyType = policyType;
        }
        public PolicyActor getPolicyActor() {
            return policyActor;
        }
        public void setPolicyActor(PolicyActor policyActor) {
            this.policyActor = policyActor;
        }
        public MetaData getResourceMetaData() {
            return resourceMetaData;
        }
        public void setResourceMetaData(MetaData resourceMetaData) {
            this.resourceMetaData = resourceMetaData;
        }
        
        @Override
        public ServiceType getServiceType() {
            return ServiceType.SECURITY;
        }
    
        public String toString(){
            
            return "PolicyContext{"+
                    "\n Identity : "+identity+
                    "\n PolicyType : "+policyType+
                    "\n PolicyActor : "+policyActor+                
                    "\n Model : "+policyConfig+
                    "\n MetaData : "+resourceMetaData+
                    "\n}\n";
        }
    
    }


    public class ProcessContext implements Context{
    
        private ServiceType serviceType = ServiceType.PROCESS;
        private String serverIdentifier;
        private PromiseServerState serverState ;
        private PromiseServerStatus serverStatus ;  
        private String processIdentifier;
        private String serviceIdentifier;
        private NotificationDeliveryType notificationDeliveryType;
        private NotificationProtocolType notificationProtocolType; 
            
        public String getServerIdentifier() {
            return serverIdentifier;
        }
        public void setServerIdentifier(String serverIdentifier) {
            this.serverIdentifier = serverIdentifier;
        }
        
        public PromiseServerState getServerState() {
            return serverState;
        }
        public void setServerState(PromiseServerState serverState) {
            this.serverState = serverState;
        }
        public PromiseServerStatus getServerStatus() {
            return serverStatus;
        }
        public void setServerStatus(PromiseServerStatus serverStatus) {
            this.serverStatus = serverStatus;
        }
    
        public String getProcessIdentifier() {
            return processIdentifier;
        }
        public void setProcessIdentifier(String processIdentifier) {
            this.processIdentifier = processIdentifier;
        }
        
        public String getServiceIdentifier() {
            return serviceIdentifier;
        }
        public void setServiceIdentifier(String serviceIdentifier) {
            this.serviceIdentifier = serviceIdentifier;
        }
        public NotificationDeliveryType getNotificationDeliveryType() {
            return notificationDeliveryType;
        }
        public void setNotificationDeliveryType(NotificationDeliveryType notificationDeliveryType) {
            this.notificationDeliveryType = notificationDeliveryType;
        }
        
        public NotificationProtocolType getNotificationProtocolType() {
            return notificationProtocolType;
        }
        public void setNotificationProtocolType(NotificationProtocolType notificationProtocolType) {
            this.notificationProtocolType = notificationProtocolType;
        }
        
        public ServiceType getServiceType(){
            return serviceType;
        }
        
        public String toString(){
            
            return "\n ProcessContext{"+
                    " serviceType                : "+serviceType+
                    " | identifier               : "+serverIdentifier+
                    " | processorState           : "+serverState+
                    " | processorStatus          : "+serverStatus+   
                    " | notificationDeliveryType : "+notificationDeliveryType+
                    " | protocolType             : "+notificationProtocolType+
                    " | processIdentifier        : "+processIdentifier+
                    " | serviceIdentifier        : "+serviceIdentifier+
                    "}\n";
        }
    
    
    }

    public class PromiseContext implements Context{

        private ServiceType serviceType = ServiceType.PROMISE;
        private RequestScale requestScale;
        private boolean isTimelineRequired;
        private RequestModel deferableRequest;
        
        public PromiseContext() {
            
        }
        
        public PromiseContext(RequestScale requestScale, boolean isTimelineRequired, RequestModel deferableRequest) {
            this.requestScale = requestScale;
            this.isTimelineRequired = isTimelineRequired;
            this.deferableRequest = deferableRequest;
        }
        
        public RequestScale getRequestScale() {
            return requestScale;
        }
        public void setRequestScale(RequestScale requestScale) {
            this.requestScale = requestScale;
        }
        public RequestModel getDeferableRequest() {
            return deferableRequest;
        }
        public void setDeferableRequest(RequestModel deferableRequest) {
            this.deferableRequest = deferableRequest;
        }
        public boolean isTimelineRequired() {
            return isTimelineRequired;
        }
        public void setTimelineRequired(boolean isTimelineRequired) {
            this.isTimelineRequired = isTimelineRequired;
        }

        public ServiceType getServiceType(){
            return serviceType;
        }
        
        public String toString(){
            
            return "PromiseContext{ serviceType : "+serviceType+ " | requestScale: "+requestScale
                    +" | isTimelineRequired : "+isTimelineRequired+" | deferableRequest : "+deferableRequest;
        }
    }

    public class EventContext implements Context{

        private ServiceType serviceType = ServiceType.EVENT;
        private String subscriptionClientId;
        private PromiseState promiseState;
        private PromiseStatus promiseStatus;
        private ScheduleStatus scheduleStatus;
        private NotificationProtocolType notificationProtocolType; 
        private NotificationDeliveryType notificationDeliveryType;
        private Object change; 
        private ReactiveMetaData reactiveMetaData;
        
        public String getSubscriptionClientId() {
            return subscriptionClientId;
        }
        public void setSubscriptionClientId(String subscriptionClientId) {
            this.subscriptionClientId = subscriptionClientId;
        }
        public PromiseState getPromiseState() {
            return promiseState;
        }
        public void setPromiseState(PromiseState promiseState) {
            this.promiseState = promiseState;
        }
        public PromiseStatus getPromiseStatus() {
            return promiseStatus;
        }
        public void setPromiseStatus(PromiseStatus promiseStatus) {
            this.promiseStatus = promiseStatus;
        }
        public ScheduleStatus getScheduleStatus() {
            return scheduleStatus;
        }
        public void setScheduleStatus(ScheduleStatus scheduleStatus) {
            this.scheduleStatus = scheduleStatus;
        }

        public NotificationProtocolType getNotificationProtocolType() {
            return notificationProtocolType;
        }
        public void setNotificationProtocolType(NotificationProtocolType notificationProtocolType) {
            this.notificationProtocolType = notificationProtocolType;
        }

        public Object getChange() {
            return change;
        }
        public void setChange(Object change) {
            this.change = change;
        }
        public ReactiveMetaData getReactiveMetaData() {
            return reactiveMetaData;
        }
        public void setReactiveMetaData(ReactiveMetaData reactiveMetaData) {
            this.reactiveMetaData = reactiveMetaData;
        }
        public NotificationDeliveryType getNotificationDeliveryType() {
            return notificationDeliveryType;
        }
        public void setNotificationDeliveryType(NotificationDeliveryType notificationDeliveryType) {
            this.notificationDeliveryType = notificationDeliveryType;
        }
        
        public ServiceType getServiceType(){
            return serviceType;
        }
        
        public String toString(){
            
            return "EventContext{"+
                    " serviceType : "+serviceType+
                    " | change : "+change+
                    " | promiseState : "+promiseState+
                    " | promiseStatus : "+promiseStatus+
                    " | scheduleStatus : "+scheduleStatus+
                    " | notificationDeliveryType : "+notificationDeliveryType+
                    " | notificationProtocolType : "+notificationProtocolType+              
                    " | reactiveMetaData : "+reactiveMetaData+              
                    "}\n";
        }
    }
    
    public class FilterContext implements Context {

        private Model model;
        private Scope scope;
        
        private Collection<AccessSecurityFilter>  accessFilters;
        private FilterType filterType;
        private FilterRetentionType filterRetentionType;
        private ViolationPolicy violationPolicy;
        private QualifierOption qualifierOption;
        
        
        public Model getModel() {
            return model;
        }
        public void setModel(Model model) {
            this.model = model;
        }
        public Scope getScope() {
            return scope;
        }
        public void setScope(Scope scope) {
            this.scope = scope;
        }
        public Collection<AccessSecurityFilter> getAccessSecurityFilters() {
            return accessFilters;
        }
        public void setAccessSecurityFilters(Collection<AccessSecurityFilter> accessFilters) {
            this.accessFilters = accessFilters;
        }
        public FilterType getFilterType() {
            return filterType;
        }
        public void setFilterType(FilterType filterType) {
            this.filterType = filterType;
        }
        public FilterRetentionType getFilterRetentionType() {
            return filterRetentionType;
        }
        public void setFilterRetentionType(FilterRetentionType filterRetentionType) {
            this.filterRetentionType = filterRetentionType;
        }
        public ViolationPolicy getViolationPolicy() {
            return violationPolicy;
        }
        public void setViolationPolicy(ViolationPolicy violationPolicy) {
            this.violationPolicy = violationPolicy;
        }
        
        public QualifierOption getQualifierOption() {
            return qualifierOption;
        }
        public void setQualifierOption(QualifierOption qualifierOption) {
            this.qualifierOption = qualifierOption;
        }   
        
        @Override
        public ServiceType getServiceType() {
            return ServiceType.SECURITY;
        }
        
        public String toString(){
            
            return "FilterContext{"+
                    "\n model : "+model+
                    "\n scope : "+scope+
                    "\n accessFilters : "+accessFilters+
                    "\n filterType : "+filterType+
                    "\n filterRetentionType : "+filterRetentionType+
                    "\n violationPolicy : "+violationPolicy+
                    "\n qualifierOption : "+qualifierOption+
                    "\n}\n";
        }
        
    }

    public class ServiceContext implements Context{

        private ServiceType serviceType = ServiceType.ADMINISTRATION;
        private String subscriptionClientId;
        private ServiceState serviceState;
        private ServiceMode serviceMode;
        private ServiceStatus serviceStatus;
        private NotificationProtocolType notificationProtocolType; 
        private String serverIdentifier;
        
        public ServiceContext() {
            
        }
        
        public ServiceContext(String subscriptionClientId, ServiceState serviceState, ServiceMode serviceMode,
                ServiceStatus serviceStatus, NotificationProtocolType notificationProtocolType,
                String serverIdentifier) {
            
            this.subscriptionClientId   = subscriptionClientId;
            this.serviceState           = serviceState;
            this.serviceMode            = serviceMode;
            this.serviceStatus          = serviceStatus;
            this.notificationProtocolType = notificationProtocolType;
            this.serverIdentifier       = serverIdentifier;
        }

        public String getSubscriptionClientId() {
            return subscriptionClientId;
        }
        public void setSubscriptionClientId(String subscriptionClientId) {
            this.subscriptionClientId = subscriptionClientId;
        }
        
        public ServiceStatus getServiceStatus() {
            return serviceStatus;
        }

        public void setServiceStatus(ServiceStatus serviceStatus) {
            this.serviceStatus = serviceStatus;
        }
        
        public String getServerIdentifier() {
            return serverIdentifier;
        }

        public void setServerIdentifier(String serverIdentifier) {
            this.serverIdentifier = serverIdentifier;
        }
        
        public NotificationProtocolType getNotificationProtocolType() {
            return notificationProtocolType;
        }

        public void setNotificationProtocolType(NotificationProtocolType notificationProtocolType) {
            this.notificationProtocolType = notificationProtocolType;
        }
        
        public ServiceState getServiceState() {
            return serviceState;
        }

        public void setServiceState(ServiceState serviceState) {
            this.serviceState = serviceState;
        }

        public ServiceMode getServiceMode() {
            return serviceMode;
        }

        public void setServiceMode(ServiceMode serviceMode) {
            this.serviceMode = serviceMode;
        }
        
        public ServiceType getServiceType(){
            return serviceType;
        }   
        
        public String toString(){
            
            return "\n ServiceContext{"+
                    " serviceType : "+serviceType+
                    " | subscriptionClientId : "+subscriptionClientId+
                    " | serviceState : "+serviceState+
                    " | serviceMode : "+serviceMode+
                    " | serviceStatus : "+serviceStatus+
                    " | serverIdentifier : "+serverIdentifier+  
                    " | notificationProtocolType : "+notificationProtocolType+  
                    "}";
        }

    }
    
    public class ServerContext implements Context{

        private ServiceType serviceType = ServiceType.SERVER;
        private String subscriptionClientId;
        private ServiceState serviceState;
        private ServiceMode serviceMode;
        private ServiceStatus serviceStatus;
        private NotificationProtocolType notificationProtocolType; 
        private String serverIdentifier;
        
        public ServerContext() {
            
        }
        
        public ServerContext(String subscriptionClientId, ServiceState serviceState, ServiceMode serviceMode,
                ServiceStatus serviceStatus, NotificationProtocolType notificationProtocolType,
                String serverIdentifier) {
            
            this.subscriptionClientId   = subscriptionClientId;
            this.serviceState           = serviceState;
            this.serviceMode            = serviceMode;
            this.serviceStatus          = serviceStatus;
            this.notificationProtocolType = notificationProtocolType;
            this.serverIdentifier       = serverIdentifier;
        }


        public String getSubscriptionClientId() {
            return subscriptionClientId;
        }
        public void setSubscriptionClientId(String subscriptionClientId) {
            this.subscriptionClientId = subscriptionClientId;
        }
        
        public ServiceStatus getServiceStatus() {
            return serviceStatus;
        }

        public void setServiceStatus(ServiceStatus serviceStatus) {
            this.serviceStatus = serviceStatus;
        }
        
        public String getServerIdentifier() {
            return serverIdentifier;
        }

        public void setServerIdentifier(String serverIdentifier) {
            this.serverIdentifier = serverIdentifier;
        }
        
        public NotificationProtocolType getNotificationProtocolType() {
            return notificationProtocolType;
        }

        public void setNotificationProtocolType(NotificationProtocolType notificationProtocolType) {
            this.notificationProtocolType = notificationProtocolType;
        }
        
        public ServiceState getServiceState() {
            return serviceState;
        }

        public void setServiceState(ServiceState serviceState) {
            this.serviceState = serviceState;
        }

        public ServiceMode getServiceMode() {
            return serviceMode;
        }

        public void setServiceMode(ServiceMode serviceMode) {
            this.serviceMode = serviceMode;
        }
        
        public ServiceType getServiceType(){
            return serviceType;
        }   
        
        public String toString(){
            
            return "\n ServerContext{"+
                    " serviceType : "+serviceType+
                    " | subscriptionClientId : "+subscriptionClientId+
                    " | serviceState : "+serviceState+
                    " | serviceMode : "+serviceMode+
                    " | serviceStatus : "+serviceStatus+
                    " | serverIdentifier : "+serverIdentifier+  
                    " | notificationProtocolType : "+notificationProtocolType+  
                    "}";
        }


    }
    
    public class RouteContext implements Context{

        private ServiceType serviceType = ServiceType.EVENT;
        private Set<NotificationRoute> routeSet;
        
        public Set<NotificationRoute> getRouteSet() {
            return routeSet;
        }
        public void setRouteSet(Set<NotificationRoute> routeSet) {
            this.routeSet = routeSet;
        }
        
        public ServiceType getServiceType(){
            return serviceType;
        }
        
        public String toString(){
            
            return "RouteContext{"+
                " | serviceType : "+serviceType+
                " | routeSet : "+routeSet+
                " }\n";
        }
    }
    
    public class PersistenceContext<K,V>{
        
        private String canonicalClassName;
        private PeristenceType peristenceType;
        private Optional<Tuple<K,V>> tupleOption;
        private Optional<TupleSet<K,V>> tupleSetOption;
        
        public PersistenceContext(String canonicalClassName,PeristenceType peristenceType){
            
            this.canonicalClassName = canonicalClassName;
            this.peristenceType     = peristenceType;
        }

        public Optional<Tuple<K, V>> getTupleOption() {
            return tupleOption;
        }

        public void setTupleOption(Optional<Tuple<K, V>> tupleOption) {
            this.tupleOption = tupleOption;
        }

        public Optional<TupleSet<K, V>> getTupleSetOption() {
            return tupleSetOption;
        }

        public void setTupleSetOption(Optional<TupleSet<K, V>> tupleSetOption) {
            this.tupleSetOption = tupleSetOption;
        }

        public String getCanonicalClassName() {
            return canonicalClassName;
        }

        public PeristenceType getPeristenceType() {
            return peristenceType;
        }
        
        public String toString(){
            
            return "PersistenceContext{"+
                    "\n peristenceType : "+peristenceType+
                    "\n tupleOption : "+tupleOption+
                    "\n tupleSetOption : "+tupleSetOption+
                    "\n}";
        }

    }

    public class SubscriptionContext implements Context {

        private ServiceType serviceType = ServiceType.SUBSCRIPTION;
        private String subcriptionId;
        private SubscriptionStatus subscriptionStatus;
        private NotificationSubscription notificationSubscription;
        
        public SubscriptionContext() {
            
        }
        
        public SubscriptionContext(String subcriptionId, SubscriptionStatus subscriptionStatus,
                NotificationSubscription notificationSubscription) {
            this.subcriptionId = subcriptionId;
            this.subscriptionStatus = subscriptionStatus;
            this.notificationSubscription = notificationSubscription;
        }
        
        public String getSubcriptionId() {
            return subcriptionId;
        }
        public void setSubcriptionId(String subcriptionId) {
            this.subcriptionId = subcriptionId;
        }
        public SubscriptionStatus getSubscriptionStatus() {
            return subscriptionStatus;
        }
        public void setSubscriptionStatus(SubscriptionStatus subscriptionStatus) {
            this.subscriptionStatus = subscriptionStatus;
        }
        public NotificationSubscription getNotificationSubscription() {
            return notificationSubscription;
        }
        public void setNotificationSubscription(NotificationSubscription notificationSubscription) {
            this.notificationSubscription = notificationSubscription;
        }
        
        public ServiceType getServiceType(){
            return serviceType;
        }
        
        public String toString(){
            
            return "SubscriptionContext{"+
                " serviceType : "+serviceType+
                " | subcriptionId : "+subcriptionId+
                " | subscriptionStatus : "+subscriptionStatus+
                " | notificationSubscription : "+notificationSubscription+
                " | }\n";
        }

    }

    public class NotificationContext implements Context {

        private ServiceType serviceType = ServiceType.NOTIFICATION;
        private NotificationBufferQueue notificationBufferQueue;
        private Properties notificationProperties;  
        private Map<NotificationProtocolType,NotificationSubscription> notificationSubscriptionMap;

        public Properties getNotificationProperties() {
            return notificationProperties;
        }
        public void setNotificationProperties(Properties notificationProperties) {
            this.notificationProperties = notificationProperties;
        }

        public Map<NotificationProtocolType,NotificationSubscription> getNotificationSubscriptionMap() {
            return notificationSubscriptionMap;
        }
        public void setNotificationSubscriptionMap(Map<NotificationProtocolType,NotificationSubscription> map) {
            this.notificationSubscriptionMap = map;
        }
        public ServiceType getServiceType(){
            return serviceType;
        }
        public NotificationBufferQueue getNotificationBufferQueue() {
            return notificationBufferQueue;
        }
        public void setNotificationBufferQueue(NotificationBufferQueue notificationBufferQueue) {
            this.notificationBufferQueue = notificationBufferQueue;
        }
        
        public String toString(){
            
            return "NotificationContext{"+
                " serviceType : "+serviceType+
                " | notificationBufferQueue : "+notificationBufferQueue+
                " | notificationProperties : "+notificationProperties+
                " | notificationSubscriptionList : "+notificationSubscriptionMap+
                " | }\n";
        }

    }


}
