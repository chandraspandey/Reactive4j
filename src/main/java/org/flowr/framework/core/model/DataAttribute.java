package org.flowr.framework.core.model;

/**
 * DataAttribute represents a model of user defined type where there is 1:1 
 * relationship exists between key & value.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class DataAttribute implements Attribute{

	private String key;
	private Number value;
	
	public void set(String key, Number value){
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Number getValue() {
		return value;
	}
	public void setValue(Number value) {
		this.value = value;
	}
	
	public boolean isKeyPresent(DataAttribute dataAttribute){
		
		boolean isPresent = false;
		
		if(key.equals(dataAttribute.getKey())){
			
			isPresent =  true;
		}
		
		return isPresent;
	}
	
	public boolean isValuePresent(DataAttribute dataAttribute){
		
		boolean isPresent = false;
		
		if(value.equals(dataAttribute.getValue())){
			
			isPresent =  true;
		}
		
		return isPresent;
	}
	
	public String toString(){
		
		return "DataAttribute{"+
				"\n key : "+key+
				"\n value : "+value+
				"\n}\n";
	}
}
