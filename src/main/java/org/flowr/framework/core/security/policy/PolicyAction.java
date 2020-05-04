
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.policy;

import org.flowr.framework.action.Action;
import org.flowr.framework.core.security.Antecedent.SecurityAntecedent;
import org.flowr.framework.core.security.Dependent.SecurityDependent;

public interface PolicyAction {
    
    Action determinePolicyAction(SecurityAntecedent antecedent,
        SecurityDependent dependent);
}
