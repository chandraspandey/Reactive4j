package org.flowr.framework.core.security.policy;

import org.flowr.framework.core.context.PolicyContext;
import org.flowr.framework.core.model.Model;

/**
 * Defines Policy loading & configuration capabilities.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface PolicyLoader {

	public Policy loadPolicy(PolicyContext policyContext);
	
	public Model getPolicyConfig(PolicyContext policyContext);

	public void setPolicyConfig(PolicyContext policyContext);
}
