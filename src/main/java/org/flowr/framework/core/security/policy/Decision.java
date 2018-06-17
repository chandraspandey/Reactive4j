package org.flowr.framework.core.security.policy;

/**
 * Defines high level abstraction of Decision. Concrete implemntation may vary 
 * in applicability based on context of application which may include manual
 * intervention as required in case of security or it can be automated for 
 * algorithmic execution. Core implementation classes should provide the 
 * necessary instrumentation for contextual execution. 
 *  
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface Decision {

	public void setDecisionModel(DecisionModel decisionModel);
	
	public DecisionModel getDecisionModel();
	
}
