package org.flowr.framework.core.security.access.filter;

import org.flowr.framework.core.context.Context;
import org.flowr.framework.core.exception.AccessSecurityException;



/**
 * Defines domain agnostic filtering mechanism. Extending interfaces provide 
 * domain specific instrumentation.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Filter<T>{

	public enum FilterRetentionType {
		PERSIST,
		NON_PERSISTENT,
		DISPOSE,
		PROPAGATED
	}
	
	/* RESTRICTED : Does not permits access
	 * READ_ONLY  : Read access
	 * DMZ		  : Privilege Access required
	 */
	public enum FilterType {
		RESTRICTED,
		READ_ONLY,
		DMZ
	}
	
	/**
	 * Performs filter operation in a provided context
	 * @param context
	 * @return
	 * @throws AccessSecurityException
	 */
	public T doFilter(Context context) throws AccessSecurityException;
	
	
	
	public void setFilterRetentionType(FilterRetentionType filterRetentionType);
	
	public FilterRetentionType getFilterRetentionType();
	
	public void setFilterName(String filterName);
	
	public String getFilterName();
	
	public void setFilterType(FilterType filterType);
	
	public FilterType getFilterType();
	
}
