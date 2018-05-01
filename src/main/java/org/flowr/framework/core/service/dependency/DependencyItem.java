package org.flowr.framework.core.service.dependency;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class DependencyItem implements Comparable<DependencyItem>{

	private Integer order;
	private Dependency dependency;
	
	public DependencyItem(Integer order,Dependency dependency){
		
		this.order		= order;
		this.dependency = dependency;	
	}

	@Override
	public int compareTo(DependencyItem other) {
		
		int status = -1;
		
		if(this.order == other.getOrder()){
			status = 0;
		}
		
		return status;
	}
	
	public Dependency getDependency(){
		return dependency;
	}

	public Integer getOrder() {
		return order;
	}
	
	public String toString(){
		
		return "\n | "+order+" | "+dependency.getDependencyType()+" | "+dependency.getDependencyName()+" |";
	}
}
