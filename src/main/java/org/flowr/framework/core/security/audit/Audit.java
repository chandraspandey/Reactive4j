
/**
 * Provides mechanism for recording rule based actions for Auditing 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.security.audit;

import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.security.Identity;

public interface Audit {

    void record(Identity identity, Model model);
}
