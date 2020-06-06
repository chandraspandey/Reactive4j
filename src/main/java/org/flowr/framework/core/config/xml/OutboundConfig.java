
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config.xml;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.config.Configuration.PipelinePolicy;
import org.flowr.framework.core.config.NodeServiceConfiguration;
import org.flowr.framework.core.config.PipelineConfiguration;
import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.notification.Notification.ClientNotificationProtocolType;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutboundConfig", propOrder = {
    "serviceConfig",
    "channelName",
    "clientPipeline",
    "serverPipeline"
})
public class OutboundConfig {

    @XmlElement(name = "serviceConfig", required = true)
    protected ServiceConfig serviceConfig;
    
    @XmlElement(name = "channelName", required = true)
    protected String channelName;
    
    @XmlElement(name = "clientPipeline", required = true)
    protected ClientPipeline clientPipeline;
    
    @XmlElement(name = "serverPipeline", required = true)
    protected ServerPipeline serverPipeline;
    
    public NodeServiceConfiguration toNodeServiceConfiguration() {
        
        NodeServiceConfiguration config = new NodeServiceConfiguration();               
        
        config.setMinThreads(serviceConfig.getMinThreads().intValue());
        config.setMaxThreads(serviceConfig.getMaxThreads().intValue());
        config.setTimeout(serviceConfig.getPolicy().getHa().getTimeout().getPeriod().longValue());
        config.setTimeoutTimeUnit(TimeUnit.valueOf(serviceConfig.getPolicy().getHa().getTimeout().getTimeUnit()));
        config.setHeartbeatInterval(serviceConfig.getPolicy().getHa().getHeartbeat().getInterval().longValue());
        config.setHeartbeatTimeUnit(TimeUnit.valueOf(serviceConfig.getPolicy().getHa().getHeartbeat().getTimeUnit()));
        config.setNotificationEndPoint(serviceConfig.getNotificationEndpoint()); 
        
        config.setNodeChannelName(channelName);
        
        config.setServerHostName(serviceConfig.getServerHostName());
        config.setServerHostPort(Integer.parseInt(serviceConfig.getServerHostPort()));
        config.setClientHostName(serviceConfig.getClientHostName());
        config.setClientHostPort(Integer.parseInt(serviceConfig.getClientHostPort()));

        SimpleEntry<String, Integer> clientEndPoint = new SimpleEntry<>(
                                                        serviceConfig.getClientHostName(),
                                                        Integer.parseInt(serviceConfig.getClientHostPort())
                                                    );
        
        config.addClientEndPoint(clientEndPoint);       
        
        config.setClientPipelineConfiguration(toClientPipelineConfiguration());
        
        config.setServerPipelineConfiguration(toServerPipelineConfiguration());
        
        return config;
    }
    
    public PipelineConfiguration toClientPipelineConfiguration(){
                   
         PipelineConfiguration config = new PipelineConfiguration();
         
         config.setPipelineName(clientPipeline.getClientPipeline().getName());
         config.setPipelinePolicy( PipelinePolicy.valueOf(clientPipeline.getClientPipeline().getPolicy()));
         config.setBatchMode(Boolean.valueOf(clientPipeline.getClientPipeline().getDispatch().getMode()));
         config.setBatchSize(clientPipeline.getClientPipeline().getDispatch().getSize().intValue());
         config.setMaxElements(clientPipeline.getClientPipeline().getMaxElements().longValue());
         config.setConfigName(clientPipeline.getClientPipeline().getEndPoint().getName());  
         config.setPipelineFunctionType(PipelineFunctionType.valueOf(
                 clientPipeline.getClientPipeline().getEndPoint().getFunctionType())); 
         config.setNotificationProtocolType(ClientNotificationProtocolType.valueOf(
                 clientPipeline.getClientPipeline().getEndPoint().getNotificationProtocolType()));
         
         ServiceConfiguration sConfig = serviceConfig.toServiceConfiguration();                
         
         ArrayList<ServiceConfiguration> list = new ArrayList<>();
         list.add(sConfig);
         
         config.setConfigurationList(list);

        return config;
    }
    
    public PipelineConfiguration toServerPipelineConfiguration(){
        
        PipelineConfiguration config = new PipelineConfiguration();
        
        config.setPipelineName(serverPipeline.getServerPipeline().getName());
        config.setPipelinePolicy( PipelinePolicy.valueOf(serverPipeline.getServerPipeline().getPolicy()));
        config.setBatchMode(Boolean.valueOf(serverPipeline.getServerPipeline().getDispatch().getMode()));
        config.setBatchSize(serverPipeline.getServerPipeline().getDispatch().getSize().intValue());
        config.setMaxElements(serverPipeline.getServerPipeline().getMaxElements().longValue());
        config.setConfigName(serverPipeline.getServerPipeline().getEndPoint().getName());  
        config.setPipelineFunctionType(PipelineFunctionType.valueOf(
                serverPipeline.getServerPipeline().getEndPoint().getFunctionType())); 
        config.setNotificationProtocolType(ServerNotificationProtocolType.valueOf(
                serverPipeline.getServerPipeline().getEndPoint().getNotificationProtocolType()));
        
        ServiceConfiguration sConfig = serviceConfig.toServiceConfiguration();                
        
        ArrayList<ServiceConfiguration> list = new ArrayList<>();
        list.add(sConfig);
        
        config.setConfigurationList(list);

       return config;
   }

    
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    
    public ServiceConfig getServiceConfig() {
        return serviceConfig;
    }

    public void setServiceConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }
    
    public ClientPipeline getClientPipeline() {
        return clientPipeline;
    }

    public void setClientPipeline(ClientPipeline clientPipeline) {
        this.clientPipeline = clientPipeline;
    }

    public ServerPipeline getServerPipeline() {
        return serverPipeline;
    }

    public void setServerPipeline(ServerPipeline serverPipeline) {
        this.serverPipeline = serverPipeline;
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        sb.append("\n OutboundConfig\n  {\n "); 
        sb.append("\n   channelName : "+channelName); 
        sb.append(clientPipeline); 
        sb.append("\n   ,"); 
        sb.append(serverPipeline);      
        sb.append("\n   ,"); 
        sb.append(serviceConfig); 
        sb.append("\n   }\n "); 
        return sb.toString();
    }
}
