
/**
 * 
 *   
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.security.access;

import java.util.Optional;

import org.flowr.framework.core.context.Context.AccessContext;
import org.flowr.framework.core.exception.AccessSecurityException;
import org.flowr.framework.core.security.audit.AuditHandler;
import org.flowr.framework.core.security.token.Token;
import org.flowr.framework.core.security.token.TokenHandler;



public interface Access {

    public enum AccessStatus{
        ACCEPTED,
        DENIED
    }
    
    public enum AccessMode{
        DIRECT,
        CLIENT,
        AGENT,
        PROXY,
        FEDERATED
    }
    
    void setAccessMode(AccessMode accessMode);
    
    void setEntitlements(Entitlements entitlements);
    
    void setAuditHandler(AuditHandler auditHandler);
    
    AccessMode getAccessMode();

    
    /**
     * Evaluates only static access as defined in source SecurityPerimeter ACL.
     * Does not evaluates dynamic parameters.
     * @return
     */
    AccessStatus hasAccessPrivilege();
    
    /**
     * Returns expirable Token as access grant which is to be used for further 
     * communication with destination identity.
     * @param securityFilterContext
     * @throws AccessSecurityException
     * @return
     */
    Optional<Token> getAccess(AccessContext accessContext) throws AccessSecurityException;
        
    
    public class SecureAccess implements Access{

        private AccessStatus accessStatus                   = AccessStatus.DENIED;
        private AccessMode accessMode                       = AccessMode.DIRECT;
        private Entitlements entitlements;
        private Optional<TokenHandler> tokenHandlerOption;
        private AuditHandler auditHandler;

        @Override
        public void setAccessMode(AccessMode accessMode) {
            this.accessMode = accessMode;
        }

        @Override
        public AccessMode getAccessMode() {
            return this.accessMode;
        }

        @Override
        public AccessStatus hasAccessPrivilege() {
            return this.entitlements.hasAccessPrivilege();
        }
        
        @Override
        public void setEntitlements(Entitlements entitlements) {            
            this.entitlements = entitlements;
        }

        @Override
        public Optional<Token> getAccess(AccessContext accessContext) throws AccessSecurityException {
                        
            Optional<Token> tokenOption = Optional.empty();
            
            if(tokenHandlerOption.isPresent()) {
                
                tokenOption = Optional.of(tokenHandlerOption.get().getAccess(accessContext));
            }
            
            this.auditHandler.record(accessContext.getIdentity(), accessContext.getIdentity().getIdentityData());
            
            return tokenOption;
        }

        @Override
        public void setAuditHandler(AuditHandler auditHandler) {
            
            this.auditHandler = auditHandler;
        }

        public String toString(){
            
            return "SecureAccess{"+
                    "\n accessStatus : "+accessStatus+
                    "\n accessMode : "+accessMode+
                    "\n}\n";
        }
                
    }
}
