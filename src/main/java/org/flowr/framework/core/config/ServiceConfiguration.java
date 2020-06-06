
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class ServiceConfiguration implements Configuration{

    private String serverHostName;
    private int serverHostPort;
    private String clientHostName;
    private int clientHostPort;
    
    private String notificationEndPoint;

    private long timeout;
    private TimeUnit timeoutTimeUnit;
    
    private TimeUnit heartbeatTimeUnit;
    private long heartbeatInterval;
    private long defaultTimeout = 30000;
    private int minThreads;
    private int maxThreads; 
    private ConfigProperties configAsProperties = new ConfigProperties();

    public boolean isValid(){
        
        boolean isValid = false;
        
        if(isValidTimeout() && isValidNotificationConfiguration()){
            
            isValid = true;
        }
        
        if(!isValid) {
            Logger.getRootLogger().info("isValid() : "+ isValidTimeout()+this);
        }        
        
        return isValid;
    }

   private boolean isValidTimeout() {
        
        return ( (timeout >= defaultTimeout) && timeoutTimeUnit != null && heartbeatInterval > 0 && 
                heartbeatTimeUnit != null);
    }

   public boolean isValidNotificationConfiguration() {

       return ( notificationEndPoint != null && (isValidServerConfiguration() || isValidClientConfiguration()));

   }

   public boolean isValidServerConfiguration() {
       
       return ( serverHostName != null  && serverHostPort > 0 );
   }

   public boolean isValidClientConfiguration() {
       
       return ( clientHostName != null && clientHostPort > 0 );
   }

   public boolean isValidThreadConfiguration() {
       
       return ( minThreads > 0 && maxThreads > 0 );
   }

    public String getServerHostName() {
        return serverHostName;
    }
    public void setServerHostName(String hostName) {
        this.serverHostName = hostName;
    }
    
    public int getServerHostPort() {
        return serverHostPort;
    }
    public void setServerHostPort(int hostPort) {
        this.serverHostPort = hostPort;
    }
    
    public String getClientHostName() {
        return clientHostName;
    }

    public void setClientHostName(String clientHostName) {
        this.clientHostName = clientHostName;
    }

    public int getClientHostPort() {
        return clientHostPort;
    }

    public void setClientHostPort(int clientHostPort) {
        this.clientHostPort = clientHostPort;
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
    
    public String toString(){
        
        return "\n ServiceConfiguration{"+
                " | serverHostName              : "+serverHostName+  
                " | serverHostPort              : "+serverHostPort+
                " | clientHostName              : "+clientHostName+  
                " | clientHostPort              : "+clientHostPort+                    
                " | notificationEndPoint        : "+notificationEndPoint+              
                " | timeout                     : "+timeout+
                " | timeoutTimeUnit             : "+timeoutTimeUnit+
                " | heartbeatInterval           : "+heartbeatInterval+
                " | heartbeatTimeUnit           : "+heartbeatTimeUnit+
                " | minThreads                  : "+minThreads+
                " | maxThreads                  : "+maxThreads+
                "}\n";
    }


}
