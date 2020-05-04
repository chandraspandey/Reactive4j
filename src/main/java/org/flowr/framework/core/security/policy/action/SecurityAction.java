
/**
 * Extends Action Interface to provide Security specific instrumentation
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.policy.action;

import org.flowr.framework.action.Action;

public interface SecurityAction extends Action{
    
    public enum CauseType {
        OUTAGE,
        REMEDIATION,
        THREAT
    }
    
    public enum ActionType {    
        FIREWALL,
        MASKING,
        OBFUSCATION,
        REMOVAL
    }
    
    void setPolicySubject(PolicySubject policySubject);
    
    PolicySubject getPolicySubject();
    
    void setPolicyTarget(PolicyTarget policyTarget);
    
    PolicyTarget getPolicyTarget();
    
    void setCauseType(CauseType causeType);
    
    CauseType getCauseType();

    void setActionType(ActionType securityActionType);
    
    ActionType getActionType();

}
