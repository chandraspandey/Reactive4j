package org.flowr.framework.core.security.policy;

import org.flowr.action.Action;
import org.flowr.framework.core.security.Antecedent.SecurityAntecedent;
import org.flowr.framework.core.security.Dependent.SecurityDependent;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface PolicyAction {
	
	public Action determinePolicyAction(SecurityAntecedent antecedent,
		SecurityDependent dependent);
}
