package org.flowr.framework.core.security.policy;

import java.util.ArrayList;

import org.flowr.framework.core.context.PolicyContext;
import org.flowr.framework.core.flow.persistence.ProviderPersistence;
import org.flowr.framework.core.model.Model;

/**
 * Marker interface for concrete policy definition, provides entry point for
 * linking of policy details which can be used by UI for customer display. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface Policy {

	public void setPolicyType(PolicyType policyType);
	
	public PolicyType getPolicyType();
	
	public void setPolicyConstraint(ArrayList<PolicyConstraint> policyConstraintList);
	
	public void addPolicyConstraint(PolicyConstraint policyConstraint);
	
	public ArrayList<PolicyConstraint> getPolicyConstraint();
	
	public Exemption getExemption();
	
	public void setExemption(Exemption exemption);

	/**
	 * High level classification Policy Types.
	 */

	public enum PolicyType {
		DEFAULT,
		IDENTITY,
		ENTERPRISE,
		TAX,
		FINANCIAL,
		SECURITY,
		RISK
	}
	
	public enum ViolationPolicy {
		STOP_ON_VIOLATION,
		ENFORCE_SAFE_MODE
	}
	
	/**
	 * Defines the valid states for policy segments, provides mechanism for 
	 * differntial instrumentation in GUI   
	 *
	 */
	public enum PolicyState {
		ACTIVE,
		DEPRECATED,
		DISCONTINUED
	}
	
	public enum PolicyStatus {
		SUCCESS,
		FAILURE,
		ERROR
	}
	
	public enum PolicyError{
		INAPPLICABLE,
		TIMELAPSE_EXPIRED
	}
	
	public void setPolicyLoader(PolicyLoader policyLoader);
	
	public PolicyLoader getPolicyLoader();
	
	public Policy loadPolicy(PolicyContext policyContext);

	public void logChange(Model model);
	
	public void setPersistence(ProviderPersistence<?> securePersistence);
	
	public void setPolicyVersion(Version version); 
	
	public Version getPolicyVersion();
}
