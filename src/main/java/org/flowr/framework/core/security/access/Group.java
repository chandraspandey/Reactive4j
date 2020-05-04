
/**
 * Denies Aggregated Role for members with same role with same privilege. 
 * Access is controlled by ACL defined for the group by SecurityPerimeter. 
 * Group is not constrained by validity(start date & end date) by default and
 * access is controlled by addition & removal of members. Privilege definition
 * carries the validity in terms of time span for which the privilege exists.  
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.access;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.flowr.framework.core.security.Identity;
import org.flowr.framework.core.security.audit.AuditHandler;
import org.flowr.framework.core.security.audit.Auditable.AuditType;

public interface Group{
    
    /**
     * GroupType is defined for usecase driven access control. Privileges 
     * should be defined as applicable for the usecase of the role performed
     * by the identity in carrying out the function as applicable to the 
     * the access perimeter unit applicable. 
     * The Group Type is defined from usecase perspective with increasing 
     * scope where visibility & control is APP < SEGMENT< DOMAIN < PERIMETER.
     */
    public enum GroupType{  
        USER,
        APPLICATION,
        SERVICE,
        SEGMENT,
        DOMAIN,
        ZONE,
        PERIMETER
    }
    
    void setGroupType(GroupType groupType);
    
    GroupType getGroupType();
    
    void setGroupName(String groupName);
    
    String getGroupName();
    
    void addMember(Identity identity);
    
    void removeMember(Identity identity);
        
    void setGroupEntitlements(Entitlements groupEntitlements);
    
    Entitlements getExistingEntitlements(Identity identity);
    
    Entitlements getGroupEntitlements();
    
    void setAuditHandler(AuditHandler auditHandler);
    
    void setIdentifier(String identifier);
    
    String getIdentifier();
    
    /**
     * Defines SecurityGroup as a aggregate entity with life cycle methods for 
     * dynamic configuration. The role is associated with Role which in turn is 
     * asscoaited with Privilege. The privilege associated with Group is inheritable
     * to all members in terms of access & ACL as defined by SecurityPerimeter.
     * Concrete implementation provides role based access control specific to the 
     * usecase of perimeter segment.
     * @author Chandra Pandey
     *
     */

    public class SecurityGroup implements Group {

        private String identifier;  
        private String groupName;
        private GroupType groupType;
        private String parentTopologyIdentifier;
        private AuditHandler auditHandler;
        private Collection<Identity> members = new ArrayList<>();
        private Entitlements groupEntitlements;
        
        @Override
        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        @Override
        public String getIdentifier() {
            return this.identifier;
        }
        
        @Override
        public void setGroupEntitlements(Entitlements groupEntitlements) {
            this.groupEntitlements = groupEntitlements;
        }
        
        @Override
        public Entitlements getGroupEntitlements() {
            
            return this.groupEntitlements;
        }

        @Override
        public Entitlements getExistingEntitlements(Identity identity) {
            
            Entitlements entitlements = null;
            
            if(members != null && !members.isEmpty()){
                
                Iterator<Identity> identityIterator = members.iterator();
                
                while(identityIterator.hasNext()){
                    
                    Identity existingIdentity = identityIterator.next();
                            
                    if(existingIdentity.equals(identity)){
                        entitlements = existingIdentity.getEntitlements();
                    }
                }
            }
            
            return entitlements;
        }
        
        public void setAuditHandler(AuditHandler auditHandler) {
            this.auditHandler = auditHandler;
        }
        
        @Override
        public void addMember(Identity identity) {
            members.add(identity);
            this.auditHandler.logChange(AuditType.ADD, identity.getIdentityData());
        }

        public void removeMember(Identity identity){
            
            if(members != null && !members.isEmpty()){
                        
                Iterator<Identity> identityIterator = members.iterator();
                
                while(identityIterator.hasNext()){
                    
                    Identity existingIdentity = identityIterator.next();
                            
                    if(existingIdentity.equals(identity)){
                        identityIterator.remove();
                        this.auditHandler.logChange(AuditType.REMOVE, identity.getIdentityData());
                        break;  
                    }
                }

            }
        }


        public Collection<Identity> getMembers() {
            return members;
        }

        public void setMembers(Collection<Identity> members) {
            this.members = members;
        }
            
        @Override
        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        @Override
        public String getGroupName() {
            return this.groupName;
        }
        
         @Override
         public int hashCode() {
           
             int hashcode = 0;
             
             Iterator<Identity> iter = this.getMembers().iterator();
             
             while(iter.hasNext()) {
                 
                 hashcode += iter.next().hashCode();
             }
                         
             return hashcode;
         }

        
        @Override
        public boolean equals(Object securityGroup){
            
            boolean isEqual = false;
            
            if( 
                    securityGroup != null && 
                    securityGroup.getClass().isAssignableFrom(this.getClass()) &&
                    this.identifier.equals(((SecurityGroup)securityGroup).getIdentifier())
            ){
            
                isEqual = true;             
            }
            
            return isEqual;
        }
        
        @Override
        public void setGroupType(GroupType groupType) {
            this.groupType=groupType;
        }

        @Override
        public GroupType getGroupType() {
            return this.groupType;
        }
        
        
        public String toString(){
            
            return "SecurityGroup{"+
                    "\n parentTopologyIdentifier : "+parentTopologyIdentifier+
                    "\n groupName : "+groupName+
                    "\n groupType : "+groupType+
                    "\n members : "+members+
                    "\n}\n";
        }
    }

}
