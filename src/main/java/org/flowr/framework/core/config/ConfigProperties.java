
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.exception.ConfigurationException;

public class ConfigProperties extends Properties{
    
    private static final long serialVersionUID = FrameworkConstants.FRAMEWORK_VERSION_ID;
    
    public enum PropertyType{
        INTEGER,
        LONG,
        FLOAT,
        DOUBLE,
        TIMEUNIT,
        STRING,
        ENUM,
        BOOLEAN
    }
    
    public ConfigProperties() {
        
    }
    
    public ConfigProperties(String fileName, String filePath) throws ConfigurationException{
        
        System.setProperty(fileName, filePath);
        
        String configPath = System.getProperty(fileName);
        
        try(
            Reader reader = new InputStreamReader(
                                new FileInputStream(configPath),
                                Charset.defaultCharset())){
            super.load(reader);
        } catch ( IOException ioException) {
            Logger.getRootLogger().error(ioException);
            throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,"Invalid configuration file : "+fileName+" or file path :"+filePath);
        }
    }
    
    public ConfigProperties(String filePath) throws ConfigurationException{
          
        try(
            Reader reader = new InputStreamReader(
                                new FileInputStream(filePath),
                                Charset.defaultCharset())){
            super.load(reader);
        } catch ( IOException ioException) {
            Logger.getRootLogger().error(ioException);
            throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,"Invalid configuration file path :"+filePath);
        }
    }
    
    @SuppressWarnings("unchecked")
    public <T> T get(PropertyType propertyType,String key){
        
        T t = null;
        
        switch(propertyType){
        case DOUBLE:
            t = (T) getDoubleProperty(key);
            break;
        case FLOAT:
            t = (T) getFloatProperty(key);
            break;
        case INTEGER:
            t = (T) getIntegerProperty(key);
            break;
        case LONG:
            t = (T) getLongProperty(key);
            break;
        case TIMEUNIT:
            t = (T) getTimeUnitProperty(key);
            break;
        case STRING:
            t = (T) getStringProperty(key);
            break;
        case BOOLEAN:
            t = (T) getBooleanProperty(key);
            break;
        default:
            t = (T) getStringProperty(key);
            break;
        
        }
        
        return t;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> T get(Class<?> enumTypeClass,PropertyType propertyType,String key){
        
        T t = null ;
        
        if(propertyType == PropertyType.ENUM) {
            
            t = (T) getEnumProperty((Class<Enum>) enumTypeClass,key);
        }else {
            t = (T) getStringProperty(key);
        }
                
        return t;
    }

    private Boolean getBooleanProperty(String key) {
        
        Boolean d = null;
        
        if(key != null && getProperty(key) != null && !getProperty(key).isEmpty()){
            d = Boolean.valueOf(getProperty(key,String.valueOf(Boolean.FALSE)));    
        }else{
            d = Boolean.FALSE;  
        }
        
        return d;   
    }

    private Double getDoubleProperty(String key){
        
        Double d = null;
        
        if(key != null && getProperty(key) != null && !getProperty(key).isEmpty()){
            d = Double.valueOf(getProperty(key,String.valueOf(Double.MIN_VALUE)));  
        }else{
            d = Double.valueOf(Double.MIN_VALUE);   
        }
        
        return d;   
    }
    
    private Integer getIntegerProperty(String key){
        
        Integer i = null;
        
        if(key != null && getProperty(key) != null && !getProperty(key).isEmpty()){
            i = Integer.valueOf(getProperty(key,String.valueOf(Integer.MIN_VALUE)));    
        }else{
            i = Integer.valueOf(Integer.MIN_VALUE); 
        }
        
        return i;   
    }
    
    private Long getLongProperty(String key){

        Long l = null;
        
        if(key != null && getProperty(key) != null && !getProperty(key).isEmpty()){
            l = Long.valueOf(getProperty(key,String.valueOf(Long.MIN_VALUE)));  
        }else{
            l = Long.valueOf(Long.MIN_VALUE);   
        }
        
        return l;       
    }
    
    private Float getFloatProperty(String key) {
        
        Float f = null;
        
        if(key != null && getProperty(key) != null && !getProperty(key).isEmpty()){
            f = Float.valueOf(getProperty(key,String.valueOf(Float.MIN_VALUE)));    
        }else{
            f = Float.valueOf(Float.MIN_VALUE); 
        }
        
        return f;       
    }
    
    private TimeUnit getTimeUnitProperty(String key) {
        
        TimeUnit t = null;
        
        if(key != null && getProperty(key) != null && !getProperty(key).isEmpty()){
            t = TimeUnit.valueOf(getProperty(key,TimeUnit.SECONDS.toString())); 
        }else{
            t = TimeUnit.SECONDS;   
        }
        
        return t;   
    }
    
    private String getStringProperty(String key){
        
        return getProperty(key);        
    }   

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <E extends Enum<E>> Enum<E> getEnumProperty(Class<Enum> enumTypeClass, String key){
        
        Enum<E> enumType = null;
        
        String val = getProperty(key);
    
        
        if(val != null) {
            
            enumType = Enum.valueOf(enumTypeClass, getProperty(key));
        }
    
        return enumType;
    }
    
    @Override
    public synchronized String toString(){
        
        return super.toString();
    }
    
}
