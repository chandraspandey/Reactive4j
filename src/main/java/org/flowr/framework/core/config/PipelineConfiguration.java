
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config;

import java.util.List;

import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;

public class PipelineConfiguration implements Configuration{

    
    private NotificationProtocolType notificationProtocolType;
    private PipelineFunctionType pipelineFunctionType;  
    private String configName;
    private PipelinePolicy pipelinePolicy       = PipelinePolicy.DISPOSE_AFTER_PROCESSING;
    private String pipelineName;
    private long maxElements                   = 10000;
    private boolean batchMode;
    private int batchSize                       = 1;    
    private List<ServiceConfiguration> configurationList;

    public PipelinePolicy getPipelinePolicy() {
        return pipelinePolicy;
    }

    public void setPipelinePolicy(PipelinePolicy pipelinePolicy) {
        this.pipelinePolicy = pipelinePolicy;
    }

    public long getMaxElements() {
        return maxElements;
    }

    public void setMaxElements(long maxElements) {
        this.maxElements = maxElements;
    }

    public boolean isBatchMode() {
        return batchMode;
    }

    public void setBatchMode(boolean batchMode) {
        this.batchMode = batchMode;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public String getPipelineName() {
        return pipelineName;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }
    
    public List<ServiceConfiguration> getConfigurationList() {
        return configurationList;
    }

    public void setConfigurationList(List<ServiceConfiguration> configurationList) {
        this.configurationList = configurationList;
    }
    
    public NotificationProtocolType getNotificationProtocolType() {
        return notificationProtocolType;
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

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }


    public String toString(){
        
        return "\n PipelineConfiguration{\n"+
                " pipelineName                  : "+pipelineName+
                " | configName                  : "+configName+  
                " | pipelineFunctionType        : "+pipelineFunctionType+  
                " | pipelineFunctionType        : "+pipelineFunctionType+  
                " | notificationProtocolType    : "+pipelinePolicy+  
                " | pipelinePolicy              : "+pipelinePolicy+  
                " | batchMode                   : "+batchMode+    
                " | batchSize                   : "+batchSize+    
                " | configurationList           : "+configurationList+
                "}\n";
    }


}
