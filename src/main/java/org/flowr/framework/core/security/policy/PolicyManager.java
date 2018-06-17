package org.flowr.framework.core.security.policy;

import org.flowr.framework.core.flow.persistence.ProviderPersistence;
import org.flowr.framework.core.security.policy.Policy.ViolationPolicy;

/**
 * Defines the behavior of PolicyManager which can be used Policy enforcement.
 * Domain specific interfaces should extend the behavior as applicable to the
 * domain specific use cases.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface PolicyManager {
	
	public ProviderPersistence<?> getSecurePersistence();
	
	public void setSecurePersistence(ProviderPersistence<?> securePersistence);
	
	public void setViolationPolicy(ViolationPolicy violationPolicy);
	
	public ViolationPolicy getViolationPolicy();
		
}
