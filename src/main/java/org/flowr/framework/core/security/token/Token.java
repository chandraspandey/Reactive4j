package org.flowr.framework.core.security.token;

import org.flowr.framework.core.security.identity.IdentityData;

/**
 * Defines access in terms of tokenised access. Access is limited & time boxed
 * with self expiry. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface Token{

	/**
	 * Defines Types of Token as Self Signed for internal  mechanism. 
	 * Federated as SSO extension for deeemed access & Third Party as
	 * access granted from trusted integration environment 
	 */
	public enum TokenType{
		SELF_SIGNED,
		FEDERATED,
		THIRD_PARTY
	}
	
	/**
	 * Returns Access Grant as set of configued attributes represented by 
	 * IdentityData only when access is established. After token is invalidated
	 * the identity data is rendered ineffective.
	 * @return
	 */
	public IdentityData getAccessGranted();
	
	/**
	 * Defines access as set of attributes defined by IdentityData. Permission
	 * type(CRUD) may vary based on the Privilege associated with the role. 
	 * @param identityData
	 */
	public void grantAccess(IdentityData identityData);
	
	/**
	 * Sets the lifespan of Token in seconds  
	 * @param timeToLive
	 */
	public void setTimeToLive(long timeToLive);
	
	/**
	 * Returns the lifespan of the token
	 * @return
	 */
	public long getTimeToLive();
	
	/**
	 * Convinience method to verify if the token is valid. The concrete 
	 * implementation provides mechanism to validate the expiry of timeToLive 
	 * attribute. 
	 * @return
	 */
	public boolean isValid();	
	
	/**
	 * Sets the type of token for differntial treatment for Security Perimeter
	 * selection
	 * @param tokenType
	 */
	public void setTokenType(TokenType tokenType);
	
	
	/**
	 * Returns the Associated Token type.
	 * @return
	 */
	public TokenType getTokenType();
	
}
