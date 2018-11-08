package org.flowr.framework.core.security;

import org.flowr.framework.core.security.audit.Auditable;

/**
 * Defines Profile as an Identity linked with Profile. It may have Persona 
 * & ProfileType.ProfileType is used for Policy linkage & enforcement. 
 * @author Chandra Pandey
 *
 */


public interface Profile extends Auditable{

	public enum ProfileType{
		USER,
		DEVICE,
		SERVER,
		NETWORK,
		STORAGE,
		DESKTOP,
		VM,
		CLOUD
	}
	
	public enum ProfileAccountType{
		CONTRACTOR,
		GUEST,
		EMPLOYEE,
		VENDOR,
		ADMIN,
		PARTNER,
		CLIENT		
	}
	
	public enum ProfileLoginType{
		USER,
		ADMIN	
	}
	
	public void setIdentifier(String identifier);
	
	public String getIdentifier();
	
	public void setAssociatedIdentity(Identity identity);
	
	public Identity getAssociatedIdentity();
	
	public void setProfileType(ProfileType profileType);
	
	public ProfileType getProfileType();
		
}
