
/**
 * Defines end user as Actor in the context of user interaction programming. 
 * Based on the context of real or workflow or virtual interaction the calling
 * program can can associate a Role for the identity for execution.
 * ResourceMetaData is instrumentation capability which can be used as part
 * of discovery where Identity can not be established or in virtual or Threat
 * actor is the representation. ResourceMetaData is optional attribution.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.policy;

import org.flowr.framework.core.exception.AccessSecurityException;
import org.flowr.framework.core.model.MetaData;
import org.flowr.framework.core.security.Identity;
import org.flowr.framework.core.security.access.Entitlements;
import org.flowr.framework.core.security.access.Role;
import org.flowr.framework.core.security.identity.IdentityData;

public interface Actor extends Identity{

    public enum ActorInRole{
        GUEST,
        UNKNOWN,
        USER,
        ADVISOR,
        ADMINISTRATOR
    }
    
    void setActorInRole(ActorInRole actorInRole);
    
    ActorInRole getActorInRole();
    
    void setResourceMetaData(MetaData resourceMetaData);
    
    MetaData getResourceMetaData();
    
    
    void setAssociateRole(Role role) throws AccessSecurityException;
    
    Role getAssociateRole();
    
    
    /**
     * Defines concrete representation of Actor in context of policy decision 
     * resolution.SecurityMetaData is integrated for cumulative identification for
     * traceability.
     * @author Chandra Pandey
     *
     */

    public class PolicyActor implements Actor {

        private ActorInRole actorInRole = ActorInRole.ADVISOR;
        private MetaData securityMetaData;
        private Identity identity;
        private Role role;
        
        public PolicyActor(Identity identity) {
            
            this.identity = identity;
        }
        
        @Override
        public void setActorInRole(ActorInRole actorInRole) {
            this.actorInRole = actorInRole;
        }

        @Override
        public ActorInRole getActorInRole() {
            return this.actorInRole;
        }

        @Override
        public void setResourceMetaData(MetaData resourceMetaData) {
            this.securityMetaData = resourceMetaData;
        }

        @Override
        public MetaData getResourceMetaData() {

            return this.securityMetaData;
        }
        
        @Override
        public IdentityData getIdentityData() {
            return this.identity.getIdentityData();
        }

        @Override
        public Entitlements getEntitlements() {
            return this.identity.getEntitlements();
        }

        @Override
        public void setAssociateRole(Role role) throws AccessSecurityException {
            this.role = role;
        }

        @Override
        public Role getAssociateRole() {
            return this.role;
        }

        public String toString(){
            
            return "PolicyActor{"+
                    "\n role : "+role+
                    "\n actorInRole : "+actorInRole+
                    "\n securityMetaData : "+securityMetaData+
                    "\n}\n";
        }




    }
}
