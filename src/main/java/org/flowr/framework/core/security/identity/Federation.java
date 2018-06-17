package org.flowr.framework.core.security.identity;

/**
 * Defines Federation in terms FederationPolicy, FederatedIdentifier & 
 * applicable FederatedTopologyIdentifier.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface Federation {

	public enum FederationPolicy{
		IMPLICIT_SESSION,
		EXPLICIT_SESSION
	}
	
	public enum FederationMechanism{
		TOKEN,
		IDENTITY
	}
	
	
	public void setFederationMechanism(FederationMechanism federationMechanism);
	
	public FederationMechanism getFederationMechanism();
	
	public void setFederationPolicy(FederationPolicy federationPolicy);
	
	public FederationPolicy getFederationPolicy();
	
	public String getFederatedIdentifier();

	public void setFederatedIdentifier(String federatedIdentifier);

}
