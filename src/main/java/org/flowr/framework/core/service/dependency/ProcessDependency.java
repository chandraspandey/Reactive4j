package org.flowr.framework.core.service.dependency;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ProcessDependency implements Dependency{

	private static String dependencyName 					= ProcessDependency.class.getSimpleName();
	private static DependencyType dependencyType			=	DependencyType.MANDATORY;
	private static TreeSet<DependencyItem> dependencyChain 	= new TreeSet<DependencyItem>();
	
	public void addDependency(DependencyItem dependencyItem){
		
		dependencyChain.add(dependencyItem);
	}
	
	public DependencyStatus verify(){
		
		DependencyStatus status = DependencyStatus.UNSATISFIED;
		
		Iterator<DependencyItem>  dependencyIterator = dependencyChain.iterator();
		
		while(dependencyIterator.hasNext()){
			
			if(dependencyIterator.next().getDependency().verify() == DependencyStatus.SATISFIED){
				status = DependencyStatus.SATISFIED;
			}else{
				status = DependencyStatus.UNSATISFIED;
				break;
			}
		}		
		
		return status;
	}

	@Override
	public String getDependencyName() {
		return dependencyName;
	}

	@Override
	public DependencyType getDependencyType() {
		return dependencyType;
	}
	
	public String toString(){
		
		return "\n ProcessDependency { "+dependencyChain+" }";
	}
}
