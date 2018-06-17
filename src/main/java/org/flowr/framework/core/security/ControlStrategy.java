package org.flowr.framework.core.security;

import org.flowr.action.Action;
import org.flowr.framework.core.model.AttributeSet;

/**
 * Defines ControlStrategy from the perspective of AdjustmentContext required 
 * for business continuity. This may require leveraging software defined API's
 * for seamless transition from one state to another in delivery ecosystem
 * which may include change of network, IP or acls. The change would be 
 * transparent in experience & not externally visible to end users.  
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ControlStrategy {

	/**
	 * Applies Decision to generate an Action as executable recourse
	 * @param decisionTree
	 * @return
	 */
	public Action applyDecision(AttributeSet attributeSet);
}
