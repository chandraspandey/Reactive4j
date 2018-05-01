package org.flowr.framework.core.service.dependency;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Dependency {

	public enum DependencyType{
		MANDATORY,
		OPTIONAL
	}
	
	public enum DependencyStatus{
		SATISFIED,
		UNSATISFIED
	}
	
	public String getDependencyName();
	
	public DependencyType getDependencyType();
	
	public DependencyStatus verify();
}
