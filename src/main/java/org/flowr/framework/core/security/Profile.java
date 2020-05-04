
/**
 * Defines Profile as an Identity linked with Profile. It may have Persona 
 * & ProfileType.ProfileType is used for Policy linkage & enforcement. 
 * @author Chandra Pandey
 *
 */
package org.flowr.framework.core.security;

import org.flowr.framework.core.security.audit.Auditable;

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
    
    void setIdentifier(String identifier);
    
    String getIdentifier();
    
    void setAssociatedIdentity(Identity identity);
    
    Identity getAssociatedIdentity();
    
    void setProfileType(ProfileType profileType);
    
    ProfileType getProfileType();
        
}
