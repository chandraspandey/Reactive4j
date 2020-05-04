
/**
 * Marker interface for defining scope attributes for privilege.
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.access;

import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import org.flowr.framework.core.model.AttributeSet;
import org.flowr.framework.core.security.Scope;
import org.flowr.framework.core.security.access.Privilege.PrivilegeType;

public interface PrivilegeScope extends Scope{
        
    String getIdentifier();

    void setIdentifier(String identifier) ;  
    
    void setPerimeterScopeType(PerimeterScopeType perimeterScopeType);
    
    PerimeterScopeType getPerimeterScopeType();
    
    void setIdentityScopeType(IdentityScopeType identityScopeType);
    
    IdentityScopeType getIdentityScopeType();
    
    void setPrivilegeStartDate(Calendar startDate);
    
    void setPrivilegeEndDate(Calendar endDate);
    
    Calendar getPrivilegeStartDate();
    
    Calendar getPrivilegeEndDate();
    
    void setPrivilegeType(Collection<PrivilegeType> privilegeTypeList);
    
    Collection<PrivilegeType> getPrivilegeType();
    
    
    public class AccessPrivilegeScope implements PrivilegeScope, Comparable<PrivilegeScope> {

        private String identifier;
        private Calendar startDate;
        private Calendar endDate;
        private Collection<PrivilegeType> privilegeTypeList;
        private AttributeSet scopeAttributes;
        private ScopeType scopeType;
        private PerimeterScopeType perimeterScopeType;
        private IdentityScopeType identityScopeType;

        
        @Override
        public void setPrivilegeStartDate(Calendar startDate) {
            this.startDate=startDate;
        }

        @Override
        public void setPrivilegeEndDate(Calendar endDate) {
            this.endDate=endDate;
        }

        @Override
        public Calendar getPrivilegeStartDate() {
            return this.startDate;
        }

        @Override
        public Calendar getPrivilegeEndDate() {
            return this.endDate;
        }

        @Override
        public void setPrivilegeType(Collection<PrivilegeType> privilegeTypeList) {
            this.privilegeTypeList=privilegeTypeList;
        }

        @Override
        public Collection<PrivilegeType> getPrivilegeType() {
            return this.privilegeTypeList;
        }

        @Override
        public AttributeSet getScopeAttributes() {
            return this.scopeAttributes;
        }
        
        @Override
        public void setScopeAttributes(AttributeSet scopeAttributes) {
            this.scopeAttributes = scopeAttributes;
        }


        @Override
        public void setScopeType(ScopeType scopeType) {
            this.scopeType=scopeType;
        }

        @Override
        public ScopeType getScopeType() {
            return this.scopeType;
        }

        @Override
        public void setPerimeterScopeType(PerimeterScopeType perimeterScopeType) {
            this.perimeterScopeType=perimeterScopeType;
        }

        @Override
        public PerimeterScopeType getPerimeterScopeType() {
            return this.perimeterScopeType;
        }

        @Override
        public void setIdentityScopeType(IdentityScopeType identityScopeType) {
            this.identityScopeType=identityScopeType;
        }

        @Override
        public IdentityScopeType getIdentityScopeType() {
            return this.identityScopeType;
        }
        
         @Override
         public int hashCode() {
                             
             int hashCode = 0;
             
             if(identifier != null) {
                 
                 hashCode = this.identifier.hashCode();
             }else {
                 hashCode = this.getClass().getCanonicalName().hashCode();
             }
             
             return hashCode;
         }
        
        @Override
        public boolean equals(Object other) {
            
            return (
                    isEqualType(other) &&
                ((PrivilegeScope)other).getIdentifier().equals(this.identifier)
            );
        }
        
        private static boolean isEqualType(Object other) {
            
            return (
                    other != null && other.getClass().isAssignableFrom(PrivilegeScope.class)   &&
                            other.getClass() == PrivilegeScope.class
                );
        }
        
        private boolean hasEqualDates(PrivilegeScope privilegeScope) {
            
            return (
                    this.startDate.equals(privilegeScope.getPrivilegeStartDate()) &&
                    this.endDate.equals(privilegeScope.getPrivilegeEndDate())
                    );
        }
        
        private boolean hasEqualScopes(PrivilegeScope privilegeScope) {
            
            return (
                    this.scopeType == privilegeScope.getScopeType() &&
                    this.perimeterScopeType == privilegeScope.getPerimeterScopeType() &&
                    this.identityScopeType == privilegeScope.getIdentityScopeType() &&
                    this.scopeAttributes.equals(privilegeScope.getScopeAttributes())
                    );
        }

        @Override
        public int compareTo(PrivilegeScope privilegeScope) {
            
            int status = -1;
            
            
            if(
                this.privilegeTypeList.size() ==  privilegeScope.getPrivilegeType().size() &&
                hasEqualDates(privilegeScope) && hasEqualScopes(privilegeScope)
                
            ){
                                
                Iterator<PrivilegeType> privilegeTypeIterator = this.privilegeTypeList.iterator();
                
                while(privilegeTypeIterator.hasNext()){
                    
                    if(privilegeScope.getPrivilegeType().contains(
                            privilegeTypeIterator.next())){
                        status = 0;
                    }else{
                        break;
                    }
                }
                            
            }
            
            return status;
        }

        
        @Override
        public String getIdentifier() {
            return identifier;
        }

        @Override
        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }


        public String toString(){
            
            return "AccessPrivilegeScope {"+
                    "\n identifier : "+identifier+
                    "\n startDate : "+startDate+
                    "\n endDate : "+endDate+    
                    "\n privilegeTypeList : "+privilegeTypeList+
                    "\n scopeAttributes : "+scopeAttributes+
                    "\n scopeType : "+scopeType+
                    "\n perimeterScopeType : "+perimeterScopeType+
                    "\n identityScopeType : "+identityScopeType+

                    "\n}\n";
        }





    }

}
