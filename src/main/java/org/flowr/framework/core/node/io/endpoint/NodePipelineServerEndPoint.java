
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io.endpoint;

import java.sql.Timestamp;
import java.time.Instant;

import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.io.network.NodeChannel;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;


public class NodePipelineServerEndPoint implements NodeEndPoint {

    private EndPointStatus endPointStatus                       = EndPointStatus.UNREACHABLE;
    private Timestamp lastUpdated;
    private NotificationProtocolType notificationProtocolType;
    private PipelineFunctionType pipelineFunctionType;
        
    public NodeChannel configureAsPipeline(NodeEndPointConfiguration nodeEndPointConfiguration) 
            throws ConfigurationException {
        
        this.lastUpdated = Timestamp.from(Instant.now());       
        return new NodePipelineServer(nodeEndPointConfiguration);
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
    public Timestamp getLastUpdated(){
        
        return this.lastUpdated;
    }
    
    @Override
    public PipelineFunctionType getPipelineFunctionType() {
        return pipelineFunctionType;
    }

    @Override   
    public void setPipelineFunctionType(PipelineFunctionType pipelineFunctionType) {
        this.pipelineFunctionType = pipelineFunctionType;
    }
    
    @Override
    public ServiceConfiguration getServiceConfiguration() {
        return getServiceConfiguration();
    }
    
    public void setEndPointStatus(EndPointStatus endPointStatus) {
        this.endPointStatus = endPointStatus;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    public String toString(){
        
        return "NodePipelineServerEndPoint{"+
                " endPointStatus : "+endPointStatus+    
                " | notificationProtocolType : "+notificationProtocolType+  
                " | pipelineFunctionType : "+pipelineFunctionType+  
                " | lastUpdated : "+lastUpdated+
                "}\n";

    }


}
