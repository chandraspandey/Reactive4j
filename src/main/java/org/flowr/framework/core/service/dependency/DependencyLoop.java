package org.flowr.framework.core.service.dependency;

import org.flowr.framework.core.service.dependency.Dependency.DependencyStatus;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface DependencyLoop {

	public DependencyStatus loopTest();
}
