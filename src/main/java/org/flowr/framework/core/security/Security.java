package org.flowr.framework.core.security;

/**
 * Defines authentication mechanism applicable for validating identity of a 
 * transaction. 
 *
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 * @param <T>
 * @param <S>
 */

public interface Security<T, S> {
	
	/* SecurityStatus is part of network discovery protocol. 
	 * UNIDENTIFIED : Identification Status not yet established
	 * IDENTIFIED : Intermediate state where the identification of identity 
	 * existence in perimeter is done but full identification is not complete.
	 * Intermediate state is good for basic access till full identification is
	 * established. Movable IoT devices require this status for wireless networks 
	 * ESTABLISHED :  Full identification of identity is established.
	 * UNKNOWN : Error state where known identity is no longer accessible due
	 * to network outage. Helps in defining a policy around treatment of IoT
	 * network devices moving out of range.  
	 */
	public enum SecurityStatus{
		UNKNOWN,
		UNIDENTIFIED,
		IDENTIFIED,
		ESTABLISHED		
	}
	
	public enum SecurityDomainType{
		STANDALONE,
		GUEST,
		CLIENT,
		PARTNER,
		VENDOR,
		CONTRACTOR,
		ENTERPRISE,
		CLOUD_PRIVATE,
		CLOUD_PUBLIC,
		CLOUD_HYBRID
	}
	
	public void setIdentity(Identity identity);
	
	public Identity getIdentity();
	
	public void setMechanism(Mechanism mechanism);
	
	public Mechanism getMechanism();
		
}
