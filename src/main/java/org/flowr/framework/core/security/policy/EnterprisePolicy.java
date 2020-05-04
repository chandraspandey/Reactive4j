
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.policy;

import java.util.ArrayList;

import org.flowr.framework.core.context.Context.PolicyContext;
import org.flowr.framework.core.security.audit.AuditHandler;
import org.flowr.framework.core.security.audit.Auditable.AuditType;

public class EnterprisePolicy implements Policy{

    private PolicyType policyType;
    private AuditHandler auditHandler;
    private Version policyVersion;
    private ArrayList<PolicyConstraint> policyConstraintList;
    private Exemption exemption;
    private PolicyLoader policyLoader;
    

    @Override
    public void setPolicyType(PolicyType policyType) {
        this.policyType=policyType;
    }

    @Override
    public PolicyType getPolicyType() {
        return this.policyType;
    }

    @Override
    public Policy loadPolicy(PolicyContext policyContext) {
        
        return policyLoader.loadPolicy(policyContext);
    }

    @Override
    public void setPolicyVersion(Version version) {
        this.policyVersion=version;
    }

    @Override
    public Version getPolicyVersion() {
        return this.policyVersion;
    }

    @Override
    public void setPolicyConstraint( ArrayList<PolicyConstraint> policyConstraintList) {
        
        this.policyConstraintList = policyConstraintList;
        
        policyConstraintList.forEach(
        
            c ->    this.auditHandler.logChange(AuditType.ADD, c)               
        );
    }

    @Override
    public ArrayList<PolicyConstraint> getPolicyConstraint() {
        return this.policyConstraintList;
    }

    @Override
    public void addPolicyConstraint(PolicyConstraint policyConstraint) {
        
        if(policyConstraintList != null){
            
            policyConstraintList = new ArrayList<>();
            policyConstraintList.add(policyConstraint);
            this.auditHandler.logChange(AuditType.ADD, policyConstraint);
        }
    }

    @Override
    public Exemption getExemption() {
        return this.exemption;
    }

    @Override
    public void setExemption(Exemption exemption) {
        this.exemption = exemption;
        
        this.auditHandler.logChange(AuditType.ADD, exemption);
    }

    @Override
    public void setPolicyLoader(PolicyLoader policyLoader) {
        this.policyLoader = policyLoader;
    }

    @Override
    public PolicyLoader getPolicyLoader() {
        return this.policyLoader;
    }

    @Override
    public void setAuditHandler(AuditHandler auditHandler) {
        this.auditHandler = auditHandler;
    }

}
