package org.flowr.framework.core.context;

import org.flowr.framework.core.model.MetaData;
import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.security.Identity;
import org.flowr.framework.core.security.policy.Actor.PolicyActor;
import org.flowr.framework.core.security.policy.Policy.PolicyType;
import org.flowr.framework.core.service.Service.ServiceType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public class PolicyContext implements Context{

	private Identity identity;
	private Model policyConfig;
	private PolicyType policyType;
	private PolicyActor PolicyActor;
	private MetaData resourceMetaData;
		
	public Identity getIdentity() {
		return identity;
	}
	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
	public Model getPolicyConfig() {
		return policyConfig;
	}
	public void setPolicyConfig(Model policyConfig) {
		this.policyConfig = policyConfig;
	}
	public PolicyType getPolicyType() {
		return policyType;
	}
	public void setPolicyType(PolicyType policyType) {
		this.policyType = policyType;
	}
	public PolicyActor getPolicyActor() {
		return PolicyActor;
	}
	public void setPolicyActor(PolicyActor policyActor) {
		PolicyActor = policyActor;
	}
	public MetaData getResourceMetaData() {
		return resourceMetaData;
	}
	public void setResourceMetaData(MetaData resourceMetaData) {
		this.resourceMetaData = resourceMetaData;
	}
	

	public String toString(){
		
		return "PolicyContext{"+
				"\n Identity : "+identity+
				"\n PolicyType : "+policyType+
				"\n PolicyActor : "+PolicyActor+				
				"\n Model : "+policyConfig+
				"\n MetaData : "+resourceMetaData+
				"\n}\n";
	}
	@Override
	public ServiceType getServiceType() {
		// TODO Auto-generated method stub
		return null;
	}
}
