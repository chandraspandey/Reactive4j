
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */


package org.flowr.framework.core.config.xml;

import java.util.ArrayList;
import java.util.List;

import org.flowr.framework.core.config.Configuration.PipelinePolicy;
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
@XmlType(name = "ServerConfiguration", propOrder = {
    "progressScaleConfig",
    "serviceConfig",
    "pipelineConfig"
})
public class ServerConfiguration {

    @XmlElement(name = "progressScaleConfig", required = true)
    protected ProgressScaleConfig progressScaleConfig;
    
    @XmlElement(name = "serviceConfig", required = true)
    protected ServiceConfig serviceConfig;
    
    @XmlElement(name = "pipelineConfig", required = true)
    protected PipelineConfig pipelineConfig;
    
    
    public List<PipelineConfiguration> toPipelineConfiguration(){
        
        List<PipelineConfiguration> configurationList = new ArrayList<>();
        
        pipelineConfig.getPipeline().forEach(
                
             (Pipeline p) -> {
                 
                 PipelineConfiguration config = new PipelineConfiguration();
                 
                 populate(configurationList, p, config);
                 
                 if(ClientNotificationProtocolType.toCollection().contains(
                         p.getEndPoint().getNotificationProtocolType())) {
                     
                     config.setNotificationProtocolType(ClientNotificationProtocolType.valueOf(
                             p.getEndPoint().getNotificationProtocolType()));
                 }
                 
                 if(ServerNotificationProtocolType.toCollection().contains(
                         p.getEndPoint().getNotificationProtocolType())) {
                     
                     config.setNotificationProtocolType(ServerNotificationProtocolType.valueOf(
                             p.getEndPoint().getNotificationProtocolType()));
                 }
                 
                 
             }
        );
        
        return configurationList;
    }

    private void populate(List<PipelineConfiguration> configurationList, Pipeline p, PipelineConfiguration config) {
        
         config.setPipelineName(p.getName());
         config.setPipelinePolicy( PipelinePolicy.valueOf(p.getPolicy()));
         config.setBatchMode(Boolean.valueOf(p.getDispatch().getMode()));
         config.setBatchSize(p.getDispatch().getSize().intValue());
         config.setMaxElements(p.getMaxElements().longValue());
         config.setConfigName(p.getEndPoint().getName());  
         config.setPipelineFunctionType(PipelineFunctionType.valueOf(
                 p.getEndPoint().getFunctionType())); 
         
         ServiceConfiguration sConfig = serviceConfig.toServiceConfiguration();  
         
         ArrayList<ServiceConfiguration> list = new ArrayList<>();
         list.add(sConfig);
         
         config.setConfigurationList(list);
         
         configurationList.add(config);
    }

    public ProgressScaleConfig getProgressScaleConfig() {
        return progressScaleConfig;
    }

    public void setProgressScaleConfig(ProgressScaleConfig progressScaleConfig) {
        this.progressScaleConfig = progressScaleConfig;
    }
    
    public ServiceConfig getServiceConfig() {
        return serviceConfig;
    }

    public void setServiceConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }
    
    public PipelineConfig getPipelineConfig() {
        return pipelineConfig;
    }

    public void setPipelineConfig(PipelineConfig pipelineConfig) {
        this.pipelineConfig = pipelineConfig;
    }
    
    @Override
    public String toString() {
        return "\n ServerConfiguration\n [ " + progressScaleConfig+" , "+serviceConfig+" , "+pipelineConfig + " ] \n";
    }

}
