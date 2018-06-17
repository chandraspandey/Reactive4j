package org.flowr.framework.core.security.access.filter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * List implementation of filtering chain, based on the scope provided 
 * AnalyticFilter progressively filters as part of processing and the filtered 
 * data is finally returned as DataPointSet.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public class FilterChain {

	private Collection<Filter<?>> filterList = new ArrayList<
			Filter<?>>();
	
	/*@Override
	public void addFilter(Filter<?> filter) {
		filterList.add(filter);
	}

	@Override
	public DataPointSet doProcessing(DataSet dataSet,Scope scope) throws 
		AccessSecurityException {
		
		DataPointSet dataPointSet = new DataPointSet();
		
		Iterator<Filter<?>>  filterIterator = filterList.iterator();
		
		DataSet filterSet = dataSet;
		
		while(filterIterator.hasNext()){
			
			Filter<?> Filter = filterIterator.next();
			
			Pair<Model,Model> dataPointpair = new 
					Pair<Model, Model>();
			
			SecurityFilterContext filterContext = new SecurityFilterContext();
			filterContext.setModel(dataPointSet);
			filterContext.setScope(scope);
			
			filterSet = (DataSet) Filter.doFilter(filterContext);
		}
		
		dataPointSet.setDataSet(filterSet.getDataSet()); 
		
		return dataPointSet;
	}*/

}
