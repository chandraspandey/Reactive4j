package org.flowr.framework.core.security.policy;

import java.util.ArrayList;

import org.flowr.framework.core.context.PolicyContext;
import org.flowr.framework.core.flow.persistence.ProviderPersistence;
import org.flowr.framework.core.model.Model;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public class EnterprisePolicy implements Policy{

	private PolicyType policyType;
	private Policy policy;
	private ProviderPersistence<?> securePersistence;
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
		
		policy = policyLoader.loadPolicy(policyContext);
		return policy;
	}

	@Override
	public void logChange(Model model) {
		//securePersistence.persistWithIdentity(AuditType.MODIFY, model);
	}

	@Override
	public void setPersistence(ProviderPersistence<?> securePersistence) {
		this.securePersistence=securePersistence;
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
	}

	@Override
	public ArrayList<PolicyConstraint> getPolicyConstraint() {
		return this.policyConstraintList;
	}

	@Override
	public void addPolicyConstraint(PolicyConstraint policyConstraint) {
		
		if(policyConstraintList != null){
			
			policyConstraintList = new ArrayList<PolicyConstraint>();
			policyConstraintList.add(policyConstraint);
		}
	}

	@Override
	public Exemption getExemption() {
		return this.exemption;
	}

	@Override
	public void setExemption(Exemption exemption) {
		this.exemption = exemption;
	}

	@Override
	public void setPolicyLoader(PolicyLoader policyLoader) {
		this.policyLoader = policyLoader;
	}

	@Override
	public PolicyLoader getPolicyLoader() {
		return this.policyLoader;
	}

}
