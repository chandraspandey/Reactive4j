package org.flowr.framework.core.notification;

import java.util.HashSet;

import org.flowr.framework.core.notification.Notification.NotificationProtocolType;

/**
 * Tuple represents a model of user defined type<K,V> where there is 1:N relationship between K & V. Notification & 
 * Service Configuration is instrumented so that concrete adapter classes can provide delegated implementation based
 * on Notification Characteristics. 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class NotificationRoute<K,V>{

	private K key;
	private V values;
	
	public void set(K key,V values){
		this.key = key;
		this.values = values;
	}
	
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	public V getValues() {
		return values;
	}
	public void setValues(V values) {
		this.values = values;
	}
	
	/**
	 * Provides concrete implementation  for equals.
	 * @param other
	 * @return
	 */
	public boolean equals(NotificationRoute<K,V> other){
		
		boolean isEqual = false;		
		
		if(	other != null && 
			this.getKey().getClass().getName().equals(
			other.getKey().getClass().getName()) &&
			this.getValues().getClass().getName().equals(
			other.getValues().getClass().getName())
				){
			
			if(key instanceof Class && values instanceof HashSet ){
				
				if(key.equals(other.getKey()) && values.equals(other.getValues())){
					isEqual = true;
				}
			} 
		}
		
		return isEqual;
	}
	
	public boolean isKeyPresent(NotificationServiceAdapter notificationServiceAdapterInstance){
		
		boolean isPresent = false;
		
		if(key == notificationServiceAdapterInstance){
			
			isPresent =  true;
		}
		
		return isPresent;
	}
	
	public boolean isValuePresent(NotificationProtocolType notificationProtocolType){
		
		boolean isPresent = false;
		
		if(values.equals(notificationProtocolType)){			
			isPresent =  true;
		}
		
		return isPresent;
	}
	
	public String toString(){
		
		return "NotificationRoute{"+
			" | K : "+key+
			" | V : "+values+
			" | }\n";
	}
}
