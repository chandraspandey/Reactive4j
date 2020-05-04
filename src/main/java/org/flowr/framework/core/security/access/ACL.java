
/**
 * Defines AccessControlList as a list accessible to an identity. ACL Type is 
 * defined in three separate categories of NETWORK, COMPUTE and STORAGE.
 * Separate ACL list is maintained for all of three for tailored access for
 * range defined by the list. Aggregation of access for all the three defines
 * the granular & verifiable access control.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.access;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;

import org.flowr.framework.core.model.Attribute;
import org.flowr.framework.core.model.AttributeSet;
import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.model.PersistenceProvider;
import org.flowr.framework.core.model.TextAttribute;
import org.flowr.framework.core.security.audit.AuditHandler;
import org.flowr.framework.core.security.audit.Auditable;

public interface ACL extends Auditable{

    public enum DomainACLType{
        SEGMENT,
        PUBLIC,
        ENTERPRISE,
        CLOUD
    }
    
    public enum ACLType{
        APP,
        DNS,
        PROXY,
        NETWORK,
        COMPUTE,
        STORAGE,
        ZONE
    }
    
    EnumMap<ACLType,AttributeSet> getEnumMap();

    void setEnumMap(EnumMap<ACLType,AttributeSet> accessMap);
    
    void addAccess(ACLType aclType,Attribute textAttribute); 
    
    void removeAccess(ACLType aclType,Attribute textAttribute);
    
    void setAuditHandler(AuditHandler auditHandler);

    
    /**
     * Defines implementation ACL for secure access. Lifecycle methods provide 
     * dynamic configuration of specified assets for an identity. It also provides
     * a linking mechanism for access providers as a known reference type of 
     * SecurityProvider. SecurityProvider in turn provides a delegated management
     * of URL's as list of WhiteList or BlackList. 
     * @author Chandra Pandey
     *
     */

    public class SecureACL implements ACL{

        private PersistenceProvider persistence;
        private EnumMap<ACLType,AttributeSet> accessMap     = new EnumMap<>(ACLType.class);
        private AuditHandler auditHandler;

        
        @Override
        public EnumMap<ACLType,AttributeSet> getEnumMap() {
            return accessMap;
        }

        @Override
        public void setEnumMap(EnumMap<ACLType,AttributeSet> accessMap) {
            this.accessMap = accessMap;
        }
        
        @Override
        public void addAccess(ACLType aclType,Attribute textAttribute){
            
            AttributeSet aclAttributeSet = accessMap.get(aclType);
            
            aclAttributeSet.addAttribute(textAttribute);
            
            logChange(AuditType.CREATE,textAttribute);
        }
        
        @Override
        public void removeAccess(ACLType aclType,Attribute textAttribute){
            
            if(accessMap != null){
                
                AttributeSet aclAttributeSet = accessMap.get(aclType);
                
                Collection<Attribute> accessList = aclAttributeSet.getAttributeList();
                
                if(!accessList.isEmpty()){
                    
                    Iterator<Attribute> attributeIterator = accessList.iterator();
                    
                    while(attributeIterator.hasNext()){
                        
                        compareAndRemove(textAttribute, attributeIterator);
                    }
                }
            }
            
        }

        private void compareAndRemove(Attribute textAttribute, Iterator<Attribute> attributeIterator) {
            TextAttribute existingAttribute = (TextAttribute)attributeIterator.next();
            
            TextAttribute requestedAttribute = (TextAttribute)textAttribute;
            
            if(existingAttribute.getKey().equals(
                    requestedAttribute.getKey())){                      
                logChange(AuditType.DELETE,requestedAttribute);
                attributeIterator .remove();
            }
        }

        
        @Override
        public void logChange(AuditType auditType,Model model) {
            this.auditHandler.logChange(auditType, model);
        }

    
        @Override
        public void setAuditHandler(AuditHandler auditHandler) {
            
            this.auditHandler = auditHandler;
        }
        
        public String toString(){
            
            return "ACL{\\n accessMap : "+accessMap+"\n securePersistence : "+persistence+"\n}\n";
        }
    }

}
