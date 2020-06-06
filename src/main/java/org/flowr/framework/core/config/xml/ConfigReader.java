
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config.xml;

import static org.flowr.framework.core.constants.Constant.FrameworkConstants.FRAMEWORK_CONFIG_PATH;

import java.io.File;
import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.exception.ConfigurationException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

public final class ConfigReader {
    
    private static Config config;
    
    private ConfigReader() {
        
    }
    
    private static Class<?>[] getClassBindings(){
                
        Class<?>[] classes = new Class[27];
        
        classes[0]  = Config.class;
        classes[1]  = ProgressUnit.class;
        classes[2]  = RequestScaleConfig.class;
        classes[3]  = ServiceConfig.class;
        classes[4]  = Retry.class;
        classes[5]  = PipelineConfig.class;
        classes[6]  = Pipeline.class;
        classes[7]  = EndPoint.class;
        classes[8]  = Dispatch.class;
        classes[9]  = Heartbeat.class;
        classes[10] = ProgressScaleConfig.class;
        classes[11] = ClientConfiguration.class;
        classes[12] = ServerConfiguration.class;
        classes[13] = NodeConfiguration.class;
        classes[14] = InboundConfig.class;
        classes[15] = OutboundConfig.class;
        classes[16] = Connection.class;
        classes[17] = Query.class;
        classes[18] = QueryCache.class;
        classes[19] = Mapping.class;
        classes[20] = Cache.class;
        classes[21] = Disk.class;
        classes[22] = CacheElements.class;
        classes[23] = DataSourceConfig.class;
        classes[24] = PolicyConfig.class;
        classes[25] = Policy.class;
        classes[26] = HA.class;
        
        return classes;
    }
          
    public static Config reload() throws ConfigurationException {
        
        config = null;
        
        return load();
    }

    public static Config load() throws ConfigurationException {
        
        if(config == null) {

            JAXBContext jaxbContext;
            
            try {
                
                jaxbContext = JAXBContext.newInstance(getClassBindings());
                
                File file = new File(System.getProperty(FRAMEWORK_CONFIG_PATH));
                
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                
                config = (Config) unmarshaller.unmarshal(file);               
            
            } catch (JAXBException e) {
                
                Logger.getRootLogger().error(e);
                
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG," Flowr.xml configuration file not configured correctly : "+
                                FRAMEWORK_CONFIG_PATH);
            }
        }
        
        return config;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProgressUnit", propOrder = {
    "unitvalue",
    "timeunit",
    "interval"
})
class ProgressUnit {

    @XmlElement(name = "unitValue", required = true)
    protected BigInteger unitvalue;
    @XmlElement(name = "timeUnit", required = true)
    protected String timeunit;
    @XmlElement(name = "interval", required = true)
    protected BigInteger interval;

    public BigInteger getUnitvalue() {
        return unitvalue;
    }
    
    public void setUnitvalue(BigInteger unitvalue) {
        this.unitvalue = unitvalue;
    }
    
    public String getTimeunit() {
        return timeunit;
    }

    public void setTimeunit(String timeunit) {
        this.timeunit = timeunit;
    }

    public BigInteger getInterval() {
        return interval;
    }

    public void setInterval(BigInteger interval) {
        this.interval = interval;
    }
    
    @Override
    public String toString() {
        return "\n   ProgressUnit[ unitvalue=" + unitvalue + ", timeunit=" + timeunit + ", interval=" +
                interval+ " ]\n";
    }

}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PipelineConfig", propOrder = {
    "pipeline"
})
class PipelineConfig {

    @XmlElement(name = "pipeline", required = true)
    protected List<Pipeline> pipeline;

    public List<Pipeline> getPipeline() {
        return pipeline;
    }

    public void setPipeline(List<Pipeline> value) {
        this.pipeline = value;
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        sb.append("\n PipelineConfig\n  {\n "); 
        
        pipeline.forEach(
            
                p -> sb.append(p) 
        );
        
        sb.append("\n   }\n "); 
        return sb.toString();
    }

}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pipeline", propOrder = {
    "name",
    "maxElements",
    "policy",
    "dispatch",
    "endPoint"
})
class Pipeline {

    @XmlElement(name = "name", required = true)
    protected String name;
    @XmlElement(name = "maxElements", required = true)
    protected BigInteger maxElements;
    @XmlElement(name = "pipelinePolicy")
    protected String policy;
    @XmlElement(name = "dispatch", required = true)
    protected Dispatch dispatch;
    @XmlElement(name = "endPoint", required = true)
    protected EndPoint endPoint;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public BigInteger getMaxElements() {
        return maxElements;
    }

    public void setMaxElements(BigInteger value) {
        this.maxElements = value;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String value) {
        this.policy = value;
    }

    public Dispatch getDispatch() {
        return dispatch;
    }

    public void setDispatch(Dispatch value) {
        this.dispatch = value;
    }

    public EndPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(EndPoint value) {
        this.endPoint = value;
    }

    @Override
    public String toString() {
        return "\n    Pipeline\n    [\n     "
                + "name=" + name + ",\n     maxElements=" + maxElements + ",\n     policy=" + policy + ",     "
                + dispatch + endPoint + "\n    ]\n";
    }

}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EndPoint", propOrder = {
    "name",
    "functionType",
    "notificationProtocolType",
    "url"
})
class EndPoint {

    @XmlElement(name = "name", required = true)
    protected String name;
    @XmlElement(name = "functionType")
    protected String functionType;
    @XmlElement(name = "notificationProtocolType", required = true)
    protected String notificationProtocolType;
    @XmlElement(name = "url", required = true)
    protected String url;


    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String value) {
        this.functionType = value;
    }

    public String getNotificationProtocolType() {
        return notificationProtocolType;
    }

    public void settNotificationProtocolType(String value) {
        this.notificationProtocolType = value;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String value) {
        this.url = value;
    }



    @Override
    public String toString() {
        return "\n     EndPoint\n     [\n     name=" + name + ",\n      functionType=" + functionType 
                + ",\n      notificationProtocolType=" + notificationProtocolType 
                + ",\n      url=" + url  + "\n     ]\n";
    }

}


