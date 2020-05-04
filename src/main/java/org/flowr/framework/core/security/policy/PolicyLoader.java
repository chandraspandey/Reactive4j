
/**
 * Defines Policy loading & configuration capabilities.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.policy;

import org.flowr.framework.core.context.Context.PolicyContext;
import org.flowr.framework.core.model.Model;

public interface PolicyLoader {

    Policy loadPolicy(PolicyContext policyContext);
    
    Model getPolicyConfig(PolicyContext policyContext);

    void setPolicyConfig(PolicyContext policyContext);
}
