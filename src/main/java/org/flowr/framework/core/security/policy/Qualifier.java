package org.flowr.framework.core.security.policy;

import org.flowr.framework.core.model.AttributeSet;
import org.flowr.framework.core.model.Model;

/**
 * Defines Qualifier as a type. The implementation classes provide concrete
 * behaviour for qualification based on use case.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface Qualifier {

	public QualifierResult qualify(Model model);
	
	public void setQualifierType(QualifierType qualifierType);
	
	public QualifierType getQualifierType();
	
	public AttributeSet getSatisfiedQualification();

	public void setSatisfiedQualification(
			AttributeSet satisfiedQualification);
	
	public AttributeSet getUnsatisfiedQualification();

	public void setUnsatisfiedQualification(
			AttributeSet unsatisfiedQualification);
	
	/*
	 * Defines qualification as distinct types
	 */
	public enum QualifierType {
		ELIGIBILITY,
		REMEDIATION
	}
	
	/*
	 * Defines attribution to be considered for evaluating the data as OPTIONAL
	 * or MANDATORY in qualification
	 */
	public enum QualifierOption {
		OPTIONAL,
		MANDATORY		
	}
	
	public enum QualifierResult {
		DEFAULT,
		QUALIFIED,
		QUALIFIED_TILL_LIMIT,
		NOT_QUALIFIED,
		EXPIRED_FOR_QUALIFICATION,
		UNQUALIFIED_FOR_GOLIVE,
		DELEGATED_FOR_QUALIFICATION,
		EXEMPTED
	}
	
	public void setQualifierResult(QualifierResult qualifierResult);
	
	public QualifierResult getQualifierResult();

}
