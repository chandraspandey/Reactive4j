package org.flowr.framework.core.security.policy;

import org.flowr.framework.core.security.policy.Qualifier.QualifierOption;
import org.flowr.framework.core.security.policy.Qualifier.QualifierResult;
import org.flowr.framework.core.security.policy.Qualifier.QualifierType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface PolicyCompliance {

	public QualifierResult qualify();
	
	public void setQualifierType(QualifierType qualifierType);
	
	public QualifierType getQualifierType();
	
	public QualifierOption getQualifierOption();
	
	public void setQualifierOption(QualifierOption qualifierOption);

	
}
