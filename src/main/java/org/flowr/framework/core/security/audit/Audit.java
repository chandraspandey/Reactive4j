package org.flowr.framework.core.security.audit;

import org.flowr.framework.core.security.Identity;

/**
 * Provides mechanism for recording rule based actions for Auditing 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Audit<A> {

	
	public void record(Identity identity, A a);
}
