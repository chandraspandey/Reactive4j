
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.AbstractMap.SimpleEntry;

import org.apache.log4j.Logger;
import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.node.Autonomic;
import org.flowr.framework.core.node.EndPoint;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.process.callback.Callback;
import org.flowr.framework.core.process.callback.RunnableCallback;

public class ServiceEndPoint implements EndPoint,RunnableCallback<SimpleEntry<ServiceEndPoint, EndPointStatus>>, 
    Autonomic<String,Boolean> {

    private EndPointStatus endPointStatus                                   = EndPointStatus.UNREACHABLE;
    private boolean isNegotiated;
    private ServiceConfiguration serviceConfiguration; 
    private boolean keepRunning;
    private Timestamp lastUpdated;
    private NotificationProtocolType notificationProtocolType;
    private PipelineFunctionType pipelineFunctionType;
    private Callback<SimpleEntry<ServiceEndPoint, EndPointStatus>> callback;
    
    public ServiceEndPoint(ServiceConfiguration serviceConfiguration){
        
        this.serviceConfiguration       = serviceConfiguration;
        this.notificationProtocolType   = serviceConfiguration.getNotificationProtocolType();
        this.pipelineFunctionType       = serviceConfiguration.getPipelineFunctionType();
    }
    
    @Override
    public EndPointStatus getEndPointStatus() {

        return endPointStatus;
    }
    
    public void setNotificationProtocolType(NotificationProtocolType notificationProtocolType){
        this.notificationProtocolType = notificationProtocolType;
    }
    
    public NotificationProtocolType getNotificationProtocolType(){
        return this.notificationProtocolType;
    }

    @Override
    public Boolean negotiate(String urlSpec) throws PromiseException {
        
        try {
            URL url = new URL(urlSpec);
            URLConnection con = url.openConnection();
            con.setReadTimeout((int)this.serviceConfiguration.getTimeout());
            con.connect();      
            isNegotiated = true;
        } catch (  IOException e) {
            Thread.currentThread().interrupt();
            Logger.getRootLogger().error(e);
        } 
        
        this.lastUpdated        =  Timestamp.from(Instant.now());
        
        if(!isNegotiated){
            throw new PromiseException(
                    ErrorMap.ERR_CONFIG, 
                    "Service EndPoint URL : "+urlSpec+" responded with endPointStatus : "+endPointStatus);
        }
        
        return isNegotiated;
    }
    
    @Override
    public Timestamp getLastUpdated(){
        
        return this.lastUpdated;
    }
    
    @Override
    public ServiceConfiguration getServiceConfiguration(){
        
        return this.serviceConfiguration;
    }


    @Override
    public boolean isNegotiated() {
        return isNegotiated;
    }

    @Override
    public void run() {

        while(keepRunning && !isNegotiated){
            
            try {
                
                if(heartbeat() ){
                    endPointStatus  = EndPointStatus.REACHABLE;
                    keepRunning = false;
                }else{
                    endPointStatus  = EndPointStatus.UNREACHABLE;
                }               
                
                Thread.sleep(1000);
            } catch (PromiseException | InterruptedException e) {
                Logger.getRootLogger().error(e);
                endPointStatus  = EndPointStatus.UNREACHABLE;
                keepRunning = false;
                Thread.currentThread().interrupt();
            } 
        }
        
        isNegotiated = true;
        doCallback(new SimpleEntry<ServiceEndPoint, EndPointStatus>(this, endPointStatus));
        Logger.getRootLogger().info("ServiceEndPoint : keepRunning : "+keepRunning);
    }
    
    public boolean heartbeat() throws PromiseException{
        
        return negotiate(this.serviceConfiguration.getNotificationEndPoint());
    }
    
    
    public void setKeepRunning(boolean keepRunning){
        this.keepRunning = keepRunning;
    }
    
    @Override
    public void register(Callback<SimpleEntry<ServiceEndPoint, EndPointStatus>> callback) {
        this.callback = callback;
    }

    @Override
    public SimpleEntry<ServiceEndPoint, EndPointStatus> doCallback(SimpleEntry<ServiceEndPoint, EndPointStatus> 
        status) {

        return this.callback.doCallback(status);
    }
    
    @Override
    public PipelineFunctionType getPipelineFunctionType() {
        return pipelineFunctionType;
    }

    @Override   
    public void setPipelineFunctionType(PipelineFunctionType pipelineFunctionType) {
        this.pipelineFunctionType = pipelineFunctionType;
    }
    
    public String toString(){
        
        return "ServiceEndPoint{"+
                " endPointStatus : "+endPointStatus+    
                " | notificationProtocolType : "+notificationProtocolType+  
                " | pipelineFunctionType : "+pipelineFunctionType+  
                " | isNegotiated : "+isNegotiated+  
                " | keepRunning : "+keepRunning+                
                " | lastUpdated : "+lastUpdated+
                " | serviceConfiguration : "+serviceConfiguration+
                "}\n";

    }
}
