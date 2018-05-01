package org.flowr.framework.core.service.route;

import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.promise.ClientIdentity;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ServiceRoute<V> implements Model {
	
	private ClientIdentity  clientIdentity;
	private V value;
	
	public void set(ClientIdentity  clientIdentity,V value){
		this.clientIdentity = clientIdentity;
		this.value = value;
	}
	
	public ClientIdentity getKey() {
		return clientIdentity;
	}
	public void setKey(ClientIdentity  clientIdentity) {
		this.clientIdentity = clientIdentity;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}
	
	/**
	 * Provides concrete implementation  for equals.
	 * @param other
	 * @return
	 */
	public boolean equals(ServiceRoute<V> other){
		
		boolean isEqual = false;		
		
		if(	other != null && 
			this.clientIdentity.equals(
			other.getKey()) &&
			this.getValue().getClass().getName().equals(
			other.getValue().getClass().getName())
				){
			
			isEqual = true;			
		}
		
		return isEqual;
	}
	
	public boolean isKeyPresent(ClientIdentity  clientIdentity){
		
		boolean isPresent = false;
		
		if(this.clientIdentity.equals(clientIdentity)){
			
			isPresent =  true;
		}
		
		//System.out.println("Route : isKeyPresent : "+isPresent+" | "+clientIdentity+" | "+clientIdentity);
		
		return isPresent;
	}
	
	public boolean isValuePresent(V v){
		
		boolean isPresent = false;
		
		System.out.println("Route : isValuePresent : "+isPresent);
		
		if(value.getClass().getName().equals(v.getClass().getName())){
			
			isPresent =  true;
		}
		
		//System.out.println("Route : isValuePresent : "+isPresent+" | value : "+value);
		
		return isPresent;
	}
	
	
	public String toString(){
		
		return "Route{"+
			"\n ClientIdentity : "+clientIdentity+
			"\n V : "+value+
			"\n}\n";
	}
}
