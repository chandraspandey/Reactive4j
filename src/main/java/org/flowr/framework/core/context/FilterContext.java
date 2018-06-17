package org.flowr.framework.core.context;

import java.util.Collection;

import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.security.Scope;
import org.flowr.framework.core.security.access.filter.AccessSecurityFilter;
import org.flowr.framework.core.security.access.filter.Filter.FilterRetentionType;
import org.flowr.framework.core.security.access.filter.Filter.FilterType;
import org.flowr.framework.core.security.policy.Policy.ViolationPolicy;
import org.flowr.framework.core.security.policy.Qualifier.QualifierOption;
import org.flowr.framework.core.service.Service.ServiceType;

/**
 * Defines a generic filtering context which can use AccessFilter as a type for
 * instrumenting filtering mechanism. Defines FilterType & FilterRetentionType 
 * for processing and a ViolationPolicy to provide a qualifying mechanism during
 * filtering process.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public class FilterContext implements Context {

	private Model model;
	private Scope scope;
	
	private Collection<AccessSecurityFilter>  accessFilters;
	private FilterType filterType;
	private FilterRetentionType filterRetentionType;
	private ViolationPolicy violationPolicy;
	private QualifierOption qualifierOption;
	
	
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public Scope getScope() {
		return scope;
	}
	public void setScope(Scope scope) {
		this.scope = scope;
	}
	public Collection<AccessSecurityFilter> getAccessSecurityFilters() {
		return accessFilters;
	}
	public void setAccessSecurityFilters(Collection<AccessSecurityFilter> accessFilters) {
		this.accessFilters = accessFilters;
	}
	public FilterType getFilterType() {
		return filterType;
	}
	public void setFilterType(FilterType filterType) {
		this.filterType = filterType;
	}
	public FilterRetentionType getFilterRetentionType() {
		return filterRetentionType;
	}
	public void setFilterRetentionType(FilterRetentionType filterRetentionType) {
		this.filterRetentionType = filterRetentionType;
	}
	public ViolationPolicy getViolationPolicy() {
		return violationPolicy;
	}
	public void setViolationPolicy(ViolationPolicy violationPolicy) {
		this.violationPolicy = violationPolicy;
	}
	
	public QualifierOption getQualifierOption() {
		return qualifierOption;
	}
	public void setQualifierOption(QualifierOption qualifierOption) {
		this.qualifierOption = qualifierOption;
	}	
	
	public String toString(){
		
		return "FilterContext{"+
				"\n model : "+model+
				"\n scope : "+scope+
				"\n accessFilters : "+accessFilters+
				"\n filterType : "+filterType+
				"\n filterRetentionType : "+filterRetentionType+
				"\n violationPolicy : "+violationPolicy+
				"\n qualifierOption : "+qualifierOption+
				"\n}\n";
	}
	@Override
	public ServiceType getServiceType() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
