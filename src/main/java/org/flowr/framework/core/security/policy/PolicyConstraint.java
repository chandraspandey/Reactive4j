package org.flowr.framework.core.security.policy;

import java.util.ArrayList;


/**
 * Abstract implementation constraint as PolicyConstraint. Maintains the list 
 * of constraints associated with the policy.
 * Leaf level concrete implementation provides the implementation of constraint
 * enforcement.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface PolicyConstraint{
	
	/*
	 * Defines Constraint associated with DomainType
	 */
	public enum ConstraintDomainType {
		DEFAULT,
		POLICY,
		IDENTITY,
		SECURITY,
		TECHNICAL
	}
	
	/*
	 * Defines Constraint for the DataType
	 */
	public enum DataConstraintType{
		CHAR,
		NUMERIC,
		NUMERIC_PATTERN,
		VARCHAR,
		VARCHAR_PATTERN
	}
	
	
	/**
	 * Sets the classification for constraint
	 * @param constraintType
	 */
	public void setConstraintDomainType(ConstraintDomainType constraintDomainType);	
	
	/**
	 * Returns constraint classification
	 * @return
	 */
	public ConstraintDomainType getConstraintDomainType();
	
	
	/**
	 * Returns list of PolicyConstraints
	 * @return
	 */
	public ArrayList<PolicyConstraint> getPolicyConstraint();
	
	/**
	 * Sets a list of PolicyConstraints
	 *
	 */
	public void setPolicyConstraint(ArrayList<PolicyConstraint>
			constraintList);
	
	/**
	 * Adds a PolicyConstraint to a Policy
	 * @param policyConstraint
	 */
	public void addPolicyConstraint(PolicyConstraint policyConstraint);
	
	

}
