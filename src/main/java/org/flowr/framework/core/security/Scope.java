package org.flowr.framework.core.security;

import org.flowr.framework.core.model.AttributeSet;
import org.flowr.framework.core.model.Model;

/**
 * Defines the Scope of domain operation. Scope is modeled as AttributeSet which 
 * provides the scope as a collection of attributes. 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Scope extends Model{

	/**
	 * Returns the attributes which define the scope of operation 
	 * @return
	 */
	public AttributeSet getScopeAttributes();
	
	/**
	 * Returns the attributes which define the scope of operation 
	 * @return
	 */
	public void setScopeAttributes(AttributeSet scopeAttributes);
	
	
	/* 
	 * 
	 * IDENTITY  : Represents scope associated with the source    
	 * PERIMETER : Represents scope associated with the destination    
	 */
	public enum ScopeType{
		IDENTITY,
		PERIMETER
	}
	
	/*
	 * Defines Scope Types supported as part of Identity definition.
	 * NONE: is the default configuration & represents that secured access is
	 * stand alone & it can not be used for Remote Access. 
	 * Rest of the types are defined to associate role(privileges) with 
	 * granular access levels. 
	 */
	public enum IdentityScopeType{
		NONE,
		GUEST,
		USER,
		ADMIN
	}
	
	/*
	 * Defines Scope Types supported as part of Perimeter definition.
	 * NONE: is the default configuration & represents that secured access is
	 * stand alone & it can not be used for Remote Access.
	 * Rest of the Types are defined to appropriate granular & defined 
	 * privileges to different kind of use type associated with the assets.  
	 */
	public enum PerimeterScopeType{
		NONE,	
		DMZ,
		DATACENTER,
		INTRANET,
		INTERNET,
		
	}

	
	public void setScopeType(ScopeType scopeType);
	
	public ScopeType getScopeType();
}
