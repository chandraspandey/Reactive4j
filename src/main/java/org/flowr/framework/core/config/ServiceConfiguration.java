
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;


public class ServiceConfiguration implements Configuration{

    private NotificationProtocolType notificationProtocolType;
    
    // Value is assigned at run time for creation of pipeline
    private PipelineFunctionType pipelineFunctionType;  
    private String configName;
    private String filePath;
    private String hostName;
    private String notificationEndPoint;
    private int hostPort;
    private long timeout;
    private TimeUnit timeoutTimeUnit;
    
    private TimeUnit heartbeatTimeUnit;
    private long heartbeatInterval;
    private long defaultTimeout = 30000;
    private int minThreads;
    private int maxThreads; 
    private ConfigProperties configAsProperties;

    public boolean isValid(){
        
        boolean isValid = false;
        
        if(configName != null && isValidTimeout() ){
            
            isValid = true;
        }
        
        if(!isValid) {
            Logger.getRootLogger().info("isValid() : "+configName +","+ isValidTimeout()+this);
        }        
        
        return isValid;
    }

   private boolean isValidTimeout() {
        
        return ( (timeout >= defaultTimeout) && timeoutTimeUnit != null && heartbeatInterval > 0 && 
                heartbeatTimeUnit != null);
    }

    public String getHostName() {
        return hostName;
    }
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    
    public int getHostPort() {
        return hostPort;
    }
    public void setHostPort(int hostPort) {
        this.hostPort = hostPort;
    }
    public String getNotificationEndPoint() {
        return notificationEndPoint;
    }
    public void setNotificationEndPoint(String notificationEndPoint) {
        this.notificationEndPoint = notificationEndPoint;
    }
    public int getMinThreads() {
        return minThreads;
    }
    public void setMinThreads(int minThreads) {
        this.minThreads = minThreads;
    }
    public int getMaxThreads() {
        return maxThreads;
    }
    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }
    
    public ConfigProperties getConfigAsProperties() {
        return configAsProperties;
    }

    public void setConfigAsProperties(ConfigProperties configAsProperties) {
        this.configAsProperties = configAsProperties;
    }
    
    public long getTimeout() {
        return timeout;
    }
    public void setTimeout(long timeout) {
        
        this.timeout = timeout;
        if(timeoutTimeUnit != null){
            this.timeout = convert(timeout);
        }
    }
    public TimeUnit getTimeoutTimeUnit() {
        return timeoutTimeUnit;
    }

    public void setTimeoutTimeUnit(TimeUnit timeoutTimeUnit) {
        this.timeoutTimeUnit = timeoutTimeUnit;
        
        if(timeout > 0){
            this.timeout = convert(timeout);
        }
    }

    public String getConfigName() {
        return configName;
    }
    
    public void setConfigName(String configName) {
        this.configName = configName;
    }
    
    public String getFilePath() {
        return filePath;
    }   
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    private long convert(long v){
        
        long l = 0;
        
        switch(timeoutTimeUnit){
            case DAYS:
                l =  v*24*60*60*1000;
                break;
            case HOURS:
                l =  v*60*60*1000;
                break;
            case MICROSECONDS:
                l =  v/1000000;
                break;
            case MILLISECONDS:
                break;
            case MINUTES:
                l =  v*60*1000;
                break;
            case NANOSECONDS:
                l =  v/1000000000;
                break;
            case SECONDS:
                l =  v*1000;
                break;
            default:
                break;
    
        }
        
        return l;
    }
    
    public TimeUnit getHeartbeatTimeUnit() {
        return heartbeatTimeUnit;
    }
    public void setHeartbeatTimeUnit(TimeUnit heartbeatTimeUnit) {
        this.heartbeatTimeUnit = heartbeatTimeUnit;
        if(heartbeatTimeUnit != null){
            this.heartbeatInterval = convert(heartbeatInterval);
        }
    }
    public long getHeartbeatInterval() {
        return heartbeatInterval;
    }
    public void setHeartbeatInterval(long heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
        if(heartbeatInterval > 0){
            this.heartbeatInterval = convert(heartbeatInterval);
        }
    }
    
    public NotificationProtocolType getNotificationProtocolType() {
        return this.notificationProtocolType;
    }
    public void setNotificationProtocolType(NotificationProtocolType notificationProtocolType) {
        
        this.notificationProtocolType = notificationProtocolType;           
    }
    
    public PipelineFunctionType getPipelineFunctionType() {
        return pipelineFunctionType;
    }

    public void setPipelineFunctionType(PipelineFunctionType pipelineFunctionType) {
        this.pipelineFunctionType = pipelineFunctionType;
    }

    public String toString(){
        
        return "\n ServiceConfiguration{"+
                " configName : "+configName+    
                " | notificationProtocolType : "+notificationProtocolType+  
                " | pipelineFunctionType : "+pipelineFunctionType+  
                " | filePath : "+filePath+  
                " | hostName : "+hostName+  
                " | hostPort : "+hostPort+
                " | notificationEndPoint : "+notificationEndPoint+              
                " | timeout : "+timeout+
                " | timeoutTimeUnit : "+timeoutTimeUnit+
                " | heartbeatInterval : "+heartbeatInterval+
                " | heartbeatTimeUnit : "+heartbeatTimeUnit+
                " | minThreads : "+minThreads+
                " | maxThreads : "+maxThreads+
                "}\n";
    }
}
