package org.flowr.framework.core.security.access.filter;

import org.flowr.framework.core.security.policy.Qualifier.QualifierResult;


/**
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface AccessSecurityFilter extends Filter<QualifierResult>{
	
	public void setFilterName(String filterName);
	
	public String getFilterName();
	
	
}
