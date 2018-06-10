package org.flowr.framework.core.config;



import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.exception.ConfigurationException;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ConfigProperties extends Properties{
	
	private static final long serialVersionUID = 1L;
	
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
	
	public ConfigProperties(String fileName, String filePath) throws ConfigurationException{
		
		System.setProperty(fileName, filePath);
		
		String configPath = System.getProperty(fileName);
		
		//System.out.println("ConfigProperties : configPath : "+configPath);
		try {
			InputStream inputStream = new FileInputStream(configPath);
			Reader inputStreamReader = new InputStreamReader(inputStream);
			this.load(inputStreamReader);
			//System.out.println("configProperties : "+this);
		} catch ( IOException ioException) {
			System.err.println(ioException.getMessage());
			ioException.printStackTrace();
			throw new ConfigurationException(
					ERR_CONFIG,
					MSG_CONFIG, 
					"Invalid configuration file : "+fileName+" or file path :"+filePath);
		}
	}
	
	public ConfigProperties(String filePath) throws ConfigurationException{

		
		//System.out.println("ConfigProperties : configPath : "+configPath);
		try {
			InputStream inputStream = new FileInputStream(filePath);
			Reader inputStreamReader = new InputStreamReader(inputStream);
			this.load(inputStreamReader);
			//System.out.println("configProperties : "+this);
		} catch ( IOException ioException) {
			System.err.println(ioException.getMessage());
			ioException.printStackTrace();
			throw new ConfigurationException(
					ERR_CONFIG,
					MSG_CONFIG, 
					"Invalid configuration file path :"+filePath);
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
		
		switch(propertyType){
		
			case ENUM:
				t = (T) getEnumProperty((Class<Enum>) enumTypeClass,key);;
				break;
			default:
				t = (T) getStringProperty(key);
				break;
		}
		
		return t;
	}

	private Boolean getBooleanProperty(String key) throws NumberFormatException{
		
		Boolean d = null;
		
		if(key != null && getProperty(key) != null && !getProperty(key).isEmpty()){
			d = Boolean.valueOf(getProperty(key,String.valueOf(Boolean.FALSE)));	
		}else{
			d = Boolean.valueOf(Boolean.FALSE);	
		}
		
		return d;	
	}

	private Double getDoubleProperty(String key) throws NumberFormatException{
		
		Double d = null;
		
		if(key != null && getProperty(key) != null && !getProperty(key).isEmpty()){
			d = Double.valueOf(getProperty(key,String.valueOf(Double.MIN_VALUE)));	
		}else{
			d = Double.valueOf(Double.MIN_VALUE);	
		}
		
		return d;	
	}
	
	private Integer getIntegerProperty(String key) throws NumberFormatException{
		
		Integer i = null;
		
		if(key != null && getProperty(key) != null && !getProperty(key).isEmpty()){
			i = Integer.valueOf(getProperty(key,String.valueOf(Integer.MIN_VALUE)));	
		}else{
			i = Integer.valueOf(Integer.MIN_VALUE);	
		}
		
		return i;	
	}
	
	private Long getLongProperty(String key) throws NumberFormatException{

		Long l = null;
		
		if(key != null && getProperty(key) != null && !getProperty(key).isEmpty()){
			l = Long.valueOf(getProperty(key,String.valueOf(Long.MIN_VALUE)));	
		}else{
			l = Long.valueOf(Long.MIN_VALUE);	
		}
		
		return l;		
	}
	
	private Float getFloatProperty(String key) throws NumberFormatException{
		
		Float f = null;
		
		if(key != null && getProperty(key) != null && !getProperty(key).isEmpty()){
			f = Float.valueOf(getProperty(key,String.valueOf(Float.MIN_VALUE)));	
		}else{
			f = Float.valueOf(Float.MIN_VALUE);	
		}
		
		return f;		
	}
	
	private TimeUnit getTimeUnitProperty(String key) throws NumberFormatException{
		
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
	public Enum<?> getEnumProperty(Class<Enum> enumTypeClass, String key){
		
		Enum<?> enumType = null;
		
		String val = getProperty(key);
	
		
		if(val != null) {
			
			enumType = Enum.valueOf(enumTypeClass, getProperty(key));
		}
	
		return enumType;
	}
	
	public String toString(){
		
		return super.toString();
	}
	
}
