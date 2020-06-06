
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */


package org.flowr.framework.core.config.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.config.Configuration.PipelinePolicy;
import org.flowr.framework.core.config.PipelineConfiguration;
import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.notification.Notification.ClientNotificationProtocolType;
import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale.ScaleOption;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClientConfiguration", propOrder = {
    "requestScaleConfig",
    "serviceConfig",
    "pipelineConfig"
})
public class ClientConfiguration {

    @XmlElement(name = "requestScaleConfig", required = true)
    protected RequestScaleConfig requestScaleConfig;
    
    @XmlElement(name = "serviceConfig", required = true)
    protected ServiceConfig serviceConfig;
    
    @XmlElement(name = "pipelineConfig", required = true)
    protected PipelineConfig pipelineConfig;
    
    public List<PipelineConfiguration> toPipelineConfiguration(){
        
        List<PipelineConfiguration> configurationList = new ArrayList<>();
        
        pipelineConfig.getPipeline().forEach(
                
             (Pipeline p) -> {
                 
                 PipelineConfiguration config = new PipelineConfiguration();
                 
                 config.setPipelineName(p.getName());
                 config.setPipelinePolicy( PipelinePolicy.valueOf(p.getPolicy()));
                 config.setBatchMode(Boolean.valueOf(p.getDispatch().getMode()));
                 config.setBatchSize(p.getDispatch().getSize().intValue());
                 config.setMaxElements(p.getMaxElements().longValue());
                 config.setConfigName(p.getEndPoint().getName());  
                 config.setPipelineFunctionType(PipelineFunctionType.valueOf(
                         p.getEndPoint().getFunctionType())); 
                 config.setNotificationProtocolType(ClientNotificationProtocolType.valueOf(
                         p.getEndPoint().getNotificationProtocolType()));
                 
                 
                 ServiceConfiguration sConfig = serviceConfig.toServiceConfiguration();   
                 
                 ArrayList<ServiceConfiguration> list = new ArrayList<>();
                 list.add(sConfig);
                 
                 config.setConfigurationList(list);
                 
                 configurationList.add(config);
             }
        );
        
        return configurationList;
    }
    
    public RequestScale toRequestScale() {
        
        RequestScale requestScale = new RequestScale(); 
        
        ScaleOption scaleOption   = new ScaleOption();
        
        scaleOption.setMinScale(requestScaleConfig.getMin().doubleValue());
        scaleOption.setMaxScale(requestScaleConfig.getMax().doubleValue());
        scaleOption.setScaleUnit(requestScaleConfig.getProgressUnit().getUnitvalue().doubleValue());
        scaleOption.setProgressTimeUnit(TimeUnit.valueOf(requestScaleConfig.getProgressUnit().getTimeunit()));
        scaleOption.setInterval(requestScaleConfig.getProgressUnit().getInterval().longValue());
        
        requestScale.setScaleOption(scaleOption);        
        requestScale.setNotificationDeliveryType(NotificationDeliveryType.valueOf(
                requestScaleConfig.getNotificationDeliveryType()));  
        
        if(serviceConfig.getPolicy() !=null) {
            
            requestScale.setRetryCount(serviceConfig.getPolicy().getHa().getRetry().getCount().intValue());
            requestScale.setRetryInterval(serviceConfig.getPolicy().getHa().getRetry().getInterval().longValue()); 
            
            requestScale.setTimeout(serviceConfig.getPolicy().getHa().getTimeout().getPeriod().longValue());
            requestScale.setTimeoutTimeUnit(TimeUnit.valueOf(serviceConfig.getPolicy().getHa().getTimeout()
                    .getTimeUnit()));
        }
        return requestScale;
    }

    public RequestScaleConfig getRequestScaleConfig() {
        return requestScaleConfig;
    }

    public void setRequestScaleConfig(RequestScaleConfig requestScaleConfig) {
        this.requestScaleConfig = requestScaleConfig;
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
        return "\n ClientConfiguration\n [ " + requestScaleConfig+" , "+serviceConfig+" , "+pipelineConfig + " ] \n";
    }

}
