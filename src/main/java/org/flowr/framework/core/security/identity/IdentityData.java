package org.flowr.framework.core.security.identity;

import org.flowr.framework.core.model.AttributeSet;
import org.flowr.framework.core.security.Identity;
import org.flowr.framework.core.security.Security.SecurityStatus;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
public class IdentityData implements Identity{

	private String identifier;
	private AttributeSet attributeSet;
	private SecurityStatus securityStatus;


	public AttributeSet getAttributeSet() {
		return attributeSet;
	}

	public void setAttributeSet(AttributeSet attributeSet) {
		this.attributeSet = attributeSet;
	}


	//@Override
	public void setSecurityStatus(SecurityStatus securityStatus) {
		this.securityStatus=securityStatus;
	}

	//@Override
	public SecurityStatus getSecurityStatus() {
		return this.securityStatus;
	}

	//@Override
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	//@Override
	public String getIdentifier() {
		return this.identifier;
	}



}
