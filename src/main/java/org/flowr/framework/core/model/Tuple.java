package org.flowr.framework.core.model;


/**
 * Tuple represents a model of user defined type<K,V> where there is 1:N 
 * relationship between K & V.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class Tuple<K,V> implements Model {
	
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
	 * Provides concrete implementation  for equals for String & Number types. 
	 * For other types the implementation needs to be overriden.
	 * @param other
	 * @return
	 */
	public boolean equals(Tuple<K,V> other){
		
		boolean isEqual = false;
		
		
		if(	other != null && 
			this.getKey().getClass().getCanonicalName().equals(
			other.getKey().getClass().getCanonicalName()) &&
			this.getValues().getClass().getCanonicalName().equals(
			other.getValues().getClass().getCanonicalName())
				){
			
			if(key instanceof String && values instanceof String ){
				
				if(key.equals(other.getKey()) && values.equals(other.getValues())){
					isEqual = true;
				}
			} else if(key instanceof Number && values instanceof Number ){
				
				
				
				if(String.valueOf(key).equals(String.valueOf(other.getKey()))
						&& String.valueOf(values).equals(
								String.valueOf(other.getValues()))){
					isEqual = true;
				}
				
			}else{
				
				if(key.equals(other.getKey()) && values.equals(other.getValues())){
					isEqual = true;
				}
			}
			
		}
		
		return isEqual;
	}
	
	public String toString(){
		
		return "Tuple{"+
				"\n K : "+key+
				"\n V : "+values+
				"\n}\n";
	}
}
