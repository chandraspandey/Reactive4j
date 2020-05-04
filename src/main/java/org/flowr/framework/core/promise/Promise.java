
/**
 * Provides a wrapper analogy of IF-THEN-WHAT for asynchronous call using 
 * Future<PromiseResponse>
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.promise;

import java.util.Map;
import java.util.Properties;

import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.model.Model.RequestModel;
import org.flowr.framework.core.model.Model.ResponseModel;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.NotificationTask;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.process.management.ProcessHandler;
import org.flowr.framework.core.target.ReactiveTarget;

public interface Promise {

    /** Marks the states that a promise can hold as a state change machine. 
     * DEFAULT : The default state of a promise without assignment.
     * NEGOTIATED : The Server has agreed for scale to be used.
     * ACKNOWLEDGED : The client has made a request for making a Promise.
     * However due to external dependencies involved it can move to ASSURED or
     * ERROR state based on resource/processing availability.
     * ASSURED      : The processing for fulfillment has started successfully to be
     * assured to be successfull in normal course, however can move to ERROR
     * state in case of any disruption or time out or run time error 
     * FULFILLED    : The fullfillment has completed successfully
     * TIMEOUT      : Marks a state where the promise can not be honoured as the
     * response generation has timed out. Relevant for Wide scope search,
     * parse or 1:N relationship processing on server side 
     * ERROR        : Marks a state where the promise can not be honoured. 
     * 
     */
    public enum PromiseState{
        DEFAULT,
        NEGOTIATED,
        ACKNOWLEDGED,
        ASSURED,
        FULFILLED,
        TIMEOUT,
        CANCEL,
        ERROR
    }
    
    /**
     * INITIATED    : When the processing has been started sucessfully & the state
     * has been changed to ASSURED.  *  
     * PROCESSING   : The client can make an assumption that promise would held 
     * good & the state as FULFILLED should happen in normal course.
     * COMPLETED    : The client can make definitive conclusion of processing
     * completion & no more state change can occur. Pairing with FULFILLED or
     * ERROR state is valid existence. 
     */
    public enum PromiseStatus{
        INITIATED,
        PROCESSING,
        COMPLETED
    }
    
    /*
     * REGISTERED   : Registration for Future occurence is acknowledged.
     * SCHEDULED    : Should happen in normal course unless run time error happens
     * DEFFERED     : The occurence is defered to new time
     * OVER         : The occurence is marked to be complete. 
     */
    public enum ScheduleStatus{
        REGISTERED,
        SCHEDULED,
        DEFFERED,
        OVER
    }
    
    /**
     * Default implementation for handling the flow for fullfillment of promise.
     * Subclasses can override this method to define alternate control flow. 
     * @param promiseRequest
     * @return
     * @throws PromiseException
     */
    PromiseResponse handle(PromiseRequest promiseRequest) throws PromiseException;
    
    /**
     * Validates if the PromiseRequest meets the preconditions for execution
     * that it provides ProgressScale as input and is of type Promisable that
     * can support generate PromiseResponse 
     * @param promiseRequest
     * @return boolean
     * @throws PromiseException
     */
    boolean ifValid(PromiseRequest promiseRequest) throws PromiseException;
    
    
    /**
     * Determines resources required for delegation to fulfill the request.
     * For non distributed processing, implementation can include What as an
     * offering 
     * @param promiseRequest
     * @return PromiseResponse
     * @throws PromiseException
     */
    PromiseResponse then(PromiseRequest promiseRequest) throws PromiseException;
    
    /**
     * Processes the Request for generating a valid response. Additional 
     * option for stateless distributed computing such as micro services, where
     * request is processing agnostic can be delegated to any of the backend 
     * systems  
     * @param promiseRequest
     * @return PromiseResponse
     * @throws PromiseException
     */
    PromiseResponse what(PromiseRequest promiseRequest) throws PromiseException;
    
    /**
     * Processes the progress and updates the Timeline accordingly 
     * @param timeline
     * @param snapshotScale
     * @return
     */
    Timeline processTimeline(Timeline timeline, Scale snapshotScale);
    
    
    /**
     * Returns the Reactive Target that services the request
     * @return
     */
    ReactiveTarget getReactiveTarget();
    
    /**
     * Sets the Reactive Target that services the request
     * @param reactiveTarget
     */
    void setReactiveTarget(ReactiveTarget reactiveTarget) ;
    
    /**
     * Associate manageability process handler.
     * @param processHandler
     */
    void associateProcessHandler(ProcessHandler processHandler);
    
    ProcessHandler getAssociatedProcessHandler();
    
    void startup();
    
    void shutdown();
    
    public class PromiseConfig {

        private PromiseTypeClient promiseTypeClient;
        private RequestModel requestModel;
        private ResponseModel responseModel;
        
        private NotificationServiceAdapter clientAdapter;
        private Properties clientAdapterProperties;    
        private NotificationTask  clientNotificationTask;
        private Properties clientTaskTopologyProperties;
        private Map<NotificationProtocolType,NotificationSubscription> clientSubscriptionMap;
        
        private NotificationServiceAdapter serverAdapter;
        private Properties serverAdapterProperties;    
        private NotificationTask  serverNotificationTask;
        private Properties serverTaskTopologyProperties;
        private Map<NotificationProtocolType,NotificationSubscription> serverSubscriptionMap;
            
        public RequestModel getRequestModel() {
            return requestModel;
        }
        public void setRequestModel(RequestModel requestModel) {
            this.requestModel = requestModel;
        }
        public ResponseModel getResponseModel() {
            return responseModel;
        }
        public void setResponseModel(ResponseModel responseModel) {
            this.responseModel = responseModel;
        }
        public NotificationServiceAdapter getClientAdapter() {
            return clientAdapter;
        }
        public void setClientAdapter(NotificationServiceAdapter clientAdapter) {
            this.clientAdapter = clientAdapter;
        }
        public Properties getClientAdapterProperties() {
            return clientAdapterProperties;
        }
        public void setClientAdapterProperties(Properties clientAdapterProperties) {
            this.clientAdapterProperties = clientAdapterProperties;
        }
        public NotificationTask getClientNotificationTask() {
            return clientNotificationTask;
        }
        public void setClientNotificationTask(NotificationTask clientNotificationTask) {
            this.clientNotificationTask = clientNotificationTask;
        }
        public Properties getClientTaskTopologyProperties() {
            return clientTaskTopologyProperties;
        }
        public void setClientTaskTopologyProperties(Properties clientTaskTopologyProperties) {
            this.clientTaskTopologyProperties = clientTaskTopologyProperties;
        }
        public NotificationServiceAdapter getServerAdapter() {
            return serverAdapter;
        }
        public void setServerAdapter(NotificationServiceAdapter serverAdapter) {
            this.serverAdapter = serverAdapter;
        }
        public Properties getServerAdapterProperties() {
            return serverAdapterProperties;
        }
        public void setServerAdapterProperties(Properties serverAdapterProperties) {
            this.serverAdapterProperties = serverAdapterProperties;
        }
        public NotificationTask getServerNotificationTask() {
            return serverNotificationTask;
        }
        public void setServerNotificationTask(NotificationTask serverNotificationTask) {
            this.serverNotificationTask = serverNotificationTask;
        }
        public Properties getServerTaskTopologyProperties() {
            return serverTaskTopologyProperties;
        }
        public void setServerTaskTopologyProperties(Properties serverTaskTopologyProperties) {
            this.serverTaskTopologyProperties = serverTaskTopologyProperties;
        }
        public PromiseTypeClient getPromiseTypeClient() {
            return promiseTypeClient;
        }
        public void setPromiseTypeClient(PromiseTypeClient promiseTypeClient) {
            this.promiseTypeClient = promiseTypeClient;
        }
        
        public Map<NotificationProtocolType, NotificationSubscription> getClientSubscriptionMap() {
            return clientSubscriptionMap;
        }
        public void setClientSubscriptionMap(Map<NotificationProtocolType, NotificationSubscription> 
            clientSubscriptionMap) {
            this.clientSubscriptionMap = clientSubscriptionMap;
        }
        public Map<NotificationProtocolType, NotificationSubscription> getServerSubscriptionMap() {
            return serverSubscriptionMap;
        }
        public void setServerSubscriptionMap(Map<NotificationProtocolType, NotificationSubscription> 
            serverSubscriptionMap) {
            this.serverSubscriptionMap = serverSubscriptionMap;
        }
        
        public String toString(){
            
            return "\n PromiseConfig{"+ 
                    "\n | promiseTypeClient             : "+promiseTypeClient+   
                    "\n | requestModel                  : "+requestModel+   
                    "\n | responseModel                 : "+responseModel+
                    "\n | clientAdapter                 : "+clientAdapter+ 
                    "\n | clientAdapterProperties       : "+clientAdapterProperties+   
                    "\n | clientNotificationTask        : "+clientNotificationTask+ 
                    "\n | clientTaskTopologyProperties  : "+clientTaskTopologyProperties+ 
                    "\n | clientSubscriptionMap         : "+clientSubscriptionMap+ 
                    "\n | serverAdapter                 : "+serverAdapter+ 
                    "\n | serverAdapterProperties       : "+serverAdapterProperties+   
                    "\n | serverNotificationTask        : "+serverNotificationTask+ 
                    "\n | serverTaskTopologyProperties  : "+serverTaskTopologyProperties+   
                    "\n | serverSubscriptionMap         : "+serverSubscriptionMap+ 
                    "\n}\n";
        }


    }

}
