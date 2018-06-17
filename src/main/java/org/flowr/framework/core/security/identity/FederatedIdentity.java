package org.flowr.framework.core.security.identity;

import org.flowr.framework.core.model.AttributeSet;
import org.flowr.framework.core.security.Identity;
import org.flowr.framework.core.security.Security;
import org.flowr.framework.core.security.Security.SecurityStatus;

/**
 * Default implementation of Identity federation. After successfull federation 
 * the identity used in federation acquires federation characteristics. 
 * The calling program in identity broking should use FederatedIdentity for 
 * communication during the Federation session. 
 * The lifecycle of FederatedIdentity should exists as long as Federation 
 * Session.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public class FederatedIdentity implements Identity,Federation {

	private String sourceIdentifier;	
	private AttributeSet identitySet;
	private SecurityStatus securityStatus = SecurityStatus.UNIDENTIFIED;
	private String parentTopologyIdentifier;
	
	private String federatedIdentifier;
	private String federatedTopologyIdentifier;
	public FederationMechanism federationMechanism = FederationMechanism.TOKEN;
	private FederationPolicy federationPolicy = FederationPolicy.EXPLICIT_SESSION;
		
	
		

	//@Override
	public AttributeSet getAttributeSet() {

		return this.identitySet;
	}

	//@Override
	public void setAttributeSet(AttributeSet attributeSet) {
		this.identitySet=attributeSet;
	}


	//@Override
	public void setSecurityStatus(SecurityStatus securityStatus) {
		this.securityStatus=securityStatus;
	}

	//@Override
	public SecurityStatus getSecurityStatus() {
		return this.securityStatus;
	}
	


	@Override
	public String getFederatedIdentifier() {
		return federatedIdentifier;
	}

	@Override
	public void setFederatedIdentifier(String federatedIdentifier) {
		this.federatedIdentifier = federatedIdentifier;
	}

	
	@Override
	public void setFederationPolicy(FederationPolicy federationPolicy) {
		this.federationPolicy = federationPolicy;
	}

	@Override
	public FederationPolicy getFederationPolicy() {
		return this.federationPolicy;
	}
	
	@Override
	public void setFederationMechanism(FederationMechanism federationMechanism) {
		this.federationMechanism = federationMechanism;
	}

	@Override
	public FederationMechanism getFederationMechanism() {
		return this.federationMechanism;
	}
	
	public String toString(){
		
		return "FederatedIdentity {"+
				"\n federatedIdentifier : "+federatedIdentifier+
				"\n federatedTopologyIdentifier : "+federatedTopologyIdentifier+
				"\n federationPolicy : "+federationPolicy+
				"\n federationMechanism : "+federationMechanism+				
				
				"\n identifier : "+sourceIdentifier+
				"\n parentTopologyIdentifier : "+parentTopologyIdentifier+
				"\n identitySet : "+identitySet+
				"\n securityStatus : "+securityStatus+
				"\n}\n";
	}
}
