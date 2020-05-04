
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

package org.flowr.framework.core.security.policy;

public interface Decision {

    void setDecisionModel(DecisionModel decisionModel);
    
    DecisionModel getDecisionModel();
    
}
