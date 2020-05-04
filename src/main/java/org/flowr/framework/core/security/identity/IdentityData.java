
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.security.identity;

import java.util.UUID;

import org.flowr.framework.core.model.AttributeSet;
import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.security.Security.SecurityStatus;

public class IdentityData implements Model{

    private String identifier;
    private AttributeSet attributeSet;
    private SecurityStatus securityStatus;
    
    public IdentityData() {
        
        this.identifier = UUID.randomUUID().toString();
    }

    public AttributeSet getAttributeSet() {
        return attributeSet;
    }

    public void setAttributeSet(AttributeSet attributeSet) {
        this.attributeSet = attributeSet;
    }

    public void setSecurityStatus(SecurityStatus securityStatus) {
        this.securityStatus=securityStatus;
    }

    public SecurityStatus getSecurityStatus() {
        return this.securityStatus;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

}
