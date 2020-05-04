
/**
 * Marker interface for concrete policy definition, provides entry point for
 * linking of policy details which can be used by UI for customer display. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.policy;

import java.util.ArrayList;

import org.flowr.framework.core.context.Context.PolicyContext;
import org.flowr.framework.core.security.audit.AuditHandler;

public interface Policy {

    /**
     * High level classification Policy Types.
     */

    public enum PolicyType {
        DEFAULT,
        IDENTITY,
        ENTERPRISE,
        TAX,
        FINANCIAL,
        SECURITY,
        RISK
    }
    
    public enum ViolationPolicy {
        STOP_ON_VIOLATION,
        ENFORCE_SAFE_MODE
    }
    
    /**
     * Defines the valid states for policy segments, provides mechanism for 
     * differntial instrumentation in GUI   
     *
     */
    public enum PolicyState {
        ACTIVE,
        DEPRECATED,
        DISCONTINUED
    }
    
    public enum PolicyStatus {
        SUCCESS,
        FAILURE,
        ERROR
    }
    
    public enum PolicyError{
        INAPPLICABLE,
        TIMELAPSE_EXPIRED
    }
    
    void setPolicyType(PolicyType policyType);
    
    PolicyType getPolicyType();
    
    void setPolicyConstraint(ArrayList<PolicyConstraint> policyConstraintList);
    
    void addPolicyConstraint(PolicyConstraint policyConstraint);
    
    ArrayList<PolicyConstraint> getPolicyConstraint();
    
    Exemption getExemption();
    
    void setExemption(Exemption exemption);
    
    void setAuditHandler(AuditHandler auditHandler);
    
    void setPolicyLoader(PolicyLoader policyLoader);
    
    PolicyLoader getPolicyLoader();
    
    Policy loadPolicy(PolicyContext policyContext);
    
    void setPolicyVersion(Version version); 
    
    Version getPolicyVersion();
}
