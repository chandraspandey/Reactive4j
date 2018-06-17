package org.flowr.framework.core.config;

import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ServiceConfiguration implements Configuration{

	private NotificationProtocolType notificationProtocolType;
	
	// Value is assigned at run time for creation of pipeline
	private PipelineFunctionType pipelineFunctionType;	
	private String configName;
	private String filePath;
	private String serverName;
	private String notificationEndPoint;
	private int serverPort;
	private long timeout;
	private TimeUnit timeoutTimeUnit;
	
	private TimeUnit heartbeatTimeUnit;
	private long heartbeatInterval;
	private long defaultTimeout	= 3000;
	private int MIN_THREADS;
	private int MAX_THREADS;	
	private ConfigProperties configAsProperties;

	public boolean isValid(){
		
		boolean isValid = false;
		
		if(configName != null && notificationProtocolType != null &&
				(timeout >= (defaultTimeout) ) && timeoutTimeUnit != null && 
				heartbeatInterval > 0 &&	heartbeatTimeUnit != null){
			
			isValid = true;
		}
		
		return isValid;
	}

	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public String getNotificationEndPoint() {
		return notificationEndPoint;
	}
	public void setNotificationEndPoint(String notificationEndPoint) {
		this.notificationEndPoint = notificationEndPoint;
	}
	public int getMIN_THREADS() {
		return MIN_THREADS;
	}
	public void setMIN_THREADS(int MIN_THREADS) {
		this.MIN_THREADS = MIN_THREADS;
	}
	public int getMAX_THREADS() {
		return MAX_THREADS;
	}
	public void setMAX_THREADS(int MAX_THREADS) {
		this.MAX_THREADS = MAX_THREADS;
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
		
		return "ServiceConfiguration{"+
				" configName : "+configName+	
				" | notificationProtocolType : "+notificationProtocolType+	
				" | pipelineFunctionType : "+pipelineFunctionType+	
				" | filePath : "+filePath+	
				" | serverName : "+serverName+				
				" | notificationEndPoint : "+notificationEndPoint+
				" | serverPort : "+serverPort+
				" | timeout : "+timeout+
				" | timeoutTimeUnit : "+timeoutTimeUnit+
				" | heartbeatInterval : "+heartbeatInterval+
				" | heartbeatTimeUnit : "+heartbeatTimeUnit+
				" | MIN_THREADS : "+MIN_THREADS+
				" | MAX_THREADS : "+MAX_THREADS+
				"}";
	}
}
