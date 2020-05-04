
/**
 * Defines Federation in terms FederationPolicy, FederatedIdentifier & 
 * applicable FederatedTopologyIdentifier.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.identity;

public interface Federation {

    public enum FederationPolicy{
        IMPLICIT_SESSION,
        EXPLICIT_SESSION
    }
    
    public enum FederationMechanism{
        TOKEN,
        IDENTITY
    }
    
    
    void setFederationMechanism(FederationMechanism federationMechanism);
    
    FederationMechanism getFederationMechanism();
    
    void setFederationPolicy(FederationPolicy federationPolicy);
    
    FederationPolicy getFederationPolicy();
    
    String getFederatedIdentifier();

    void setFederatedIdentifier(String federatedIdentifier);

}
