
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;
import org.flowr.framework.core.node.io.pipeline.NetworkPipeline;

public class NodeServiceConfiguration extends ServiceConfiguration{

    private List<SimpleEntry<String, Integer>> clientEndPointList = new ArrayList<>();
    private PipelineConfiguration clientPipelineConfiguration;
    private PipelineConfiguration serverPipelineConfiguration;
    private NetworkPipeline clientPipeline;
    private NetworkPipeline serverPipeline;
    private String nodeChannelName;

    @Override
    public boolean isValid(){
        
        boolean isValid = false;
        
        if(clientPipeline != null && serverPipeline != null && nodeChannelName != null && super.isValid()){
            
            isValid = true;
        }
        
        return isValid;
    }
    
    public void addClientEndPoint(SimpleEntry<String, Integer> clientEndPoint) {
        
        this.clientEndPointList.add(clientEndPoint);
    }
    
    public String getNodePipelineName() {
        return clientPipeline.getPipelineName();
    }

    public String getNodeChannelName() {
        return nodeChannelName;
    }

    public void setNodeChannelName(String nodeChannelName) {
        this.nodeChannelName = nodeChannelName;
    }
    
    public List<SimpleEntry<String, Integer>> getClientEndPointList() {
        return clientEndPointList;
    }
    
    public PipelineConfiguration getClientPipelineConfiguration() {
        return clientPipelineConfiguration;
    }

    public void setClientPipelineConfiguration(PipelineConfiguration clientPipelineConfiguration) {
        
        this.clientPipelineConfiguration = clientPipelineConfiguration;
        
        this.clientPipeline = new NetworkPipeline();
        this.clientPipeline.setEventType(EventType.NETWORK);
        this.clientPipeline.setPipelineConfiguration(clientPipelineConfiguration);
        this.clientPipeline.setPipelineName(clientPipelineConfiguration.getPipelineName());
        this.clientPipeline.setPipelineType(PipelineType.TRANSFER);
    }

    public PipelineConfiguration getServerPipelineConfiguration() {
        return serverPipelineConfiguration;
    }

    public void setServerPipelineConfiguration(PipelineConfiguration serverPipelineConfiguration) {
        
        this.serverPipelineConfiguration = serverPipelineConfiguration;
        
        this.serverPipeline = new NetworkPipeline();
        this.serverPipeline.setEventType(EventType.NETWORK);
        this.serverPipeline.setPipelineConfiguration(serverPipelineConfiguration);
        this.serverPipeline.setPipelineName(serverPipelineConfiguration.getPipelineName());
        this.serverPipeline.setPipelineType(PipelineType.TRANSFER);
    }
    
    @Override
    public String toString(){
        
        return "\n NodeServiceConfiguration{"+             
                " serverPipeline : "+serverPipeline+
                " clientPipeline : "+clientPipeline+
                " clientEndPointList : "+clientEndPointList+
                " | "+super.toString()+ 
                "}\n";
    }
}
