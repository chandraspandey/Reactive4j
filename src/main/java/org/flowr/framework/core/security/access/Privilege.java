
/**
 * Marker interface for defining Privilege. Concrete implementation is context
 * specific. Access verifies Privilege for executable.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.access;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.security.Identity;
import org.flowr.framework.core.security.access.PrivilegeScope.AccessPrivilegeScope;
import org.flowr.framework.core.security.audit.AuditHandler;
import org.flowr.framework.core.security.audit.Auditable;

public interface Privilege extends Auditable{

    public enum PrivilegeType{
        VIEW,
        READ,
        WRITE,
        CONFIGURE,
        INSTALL,
        PLUGIN_INSTALL
    }
    
    void setAssociatedIdentity(Identity identity);
    
    Identity getAssociatedIdentity();
    
    void setPrivilegeScopes(Collection<AccessPrivilegeScope> privilegeList);
    
    Collection<AccessPrivilegeScope> getPrivilegeScopes();
    
    void addPrivilege(AccessPrivilegeScope accessPrivilegeScope);
    
    void removePrivilege(AccessPrivilegeScope accessPrivilegeScope);
    
    void setAuditHandler(AuditHandler auditHandler);
    
    /**
     * Provides concrete implementation of Privilege & associated life cycle 
     * management.
     * @author Chandra Pandey
     *
     */

    public final class AccessPrivilege implements Privilege {

        private Collection<AccessPrivilegeScope> privilegeScopes    = new ArrayList<>();        
        private AuditHandler auditHandler;
        private Identity identity;
        private String parentTopologyIdentifier;
        
         @Override
         public int hashCode() {
             
             return parentTopologyIdentifier.hashCode();
         }

        
        @Override
        public boolean equals(Object otherPrivilege) {
            
            boolean isEqual = false;
                
            
            if( 
                otherPrivilege instanceof AccessPrivilege &&
                identity.equals(((AccessPrivilege)otherPrivilege).getAssociatedIdentity()) &&               
                privilegeScopes.size() == ((AccessPrivilege)otherPrivilege).getPrivilegeScopes().size() 
            ){
            
            
                Iterator<AccessPrivilegeScope> privilegeIterator = 
                        privilegeScopes.iterator();
                
                while(privilegeIterator.hasNext()){
                    
                    AccessPrivilegeScope accessPrivilegeScope = 
                            privilegeIterator.next();
                    
                    Collection<AccessPrivilegeScope> accessPrivilegeScopes = 
                            ((AccessPrivilege)otherPrivilege).getPrivilegeScopes();
                    
                    if(
                            accessPrivilegeScopes != null && ! accessPrivilegeScopes.isEmpty() &&
                            verifyAccessPrivilege(isEqual, accessPrivilegeScope, accessPrivilegeScopes)
                     ){
             
                        break;                       
                    }     
                }                   
                
            }
            return isEqual;
        }


        private static boolean verifyAccessPrivilege(boolean isEqual, AccessPrivilegeScope accessPrivilegeScope,
                Collection<AccessPrivilegeScope> accessPrivilegeScopes) {
                        
            Iterator<AccessPrivilegeScope> accessPrivilegeIterator = accessPrivilegeScopes.iterator();
                        
            while(accessPrivilegeIterator.hasNext()){
                
                AccessPrivilegeScope scope = 
                        accessPrivilegeIterator.next();
                
                if(accessPrivilegeScope.compareTo(scope) == 0){
                    isEqual = true;
                    break;
                }
            }
            return isEqual;
        }

        @Override
        public void logChange(AuditType auditType, Model model) {

            this.auditHandler.logChange(auditType, model);
        }
        
        @Override
        public void setPrivilegeScopes(Collection<AccessPrivilegeScope> privilegeScopes){
            this.privilegeScopes=privilegeScopes;
        }
        
        @Override
        public Collection<AccessPrivilegeScope> getPrivilegeScopes(){
            
            return this.privilegeScopes;
        }
        
        @Override
        public void addPrivilege(AccessPrivilegeScope accessPrivilegeScope){
            
            privilegeScopes.add(accessPrivilegeScope);
            logChange(AuditType.ADD,accessPrivilegeScope);
        }
        
        @Override
        public void removePrivilege(AccessPrivilegeScope accessPrivilegeScope){
            
            Iterator<AccessPrivilegeScope> privilegeIterator = 
                    privilegeScopes.iterator();
            
            while(privilegeIterator.hasNext()){
        
                if(privilegeIterator.next().compareTo(accessPrivilegeScope) == 0){
                    
                    privilegeIterator.remove();
                    logChange(AuditType.REMOVE,accessPrivilegeScope);
                    break;
                }
            }
            
        }
        
        
        @Override
        public void setAssociatedIdentity(Identity identity) {
            this.identity=identity;
        }

        @Override
        public Identity getAssociatedIdentity() {
            return this.identity;
        }

        @Override
        public void setAuditHandler(AuditHandler auditHandler) {
            
            this.auditHandler = auditHandler;
        }


        public String toString(){
            
            return "AccessPrivilege {"+
                    "\n identity : "+identity+
                    "\n privilegeScopes : "+privilegeScopes+    
                    "\n identity : "+identity+
                    "\n parentTopologyIdentifier : "+parentTopologyIdentifier+
                    "\n}\n";
        }
    }
    
}
