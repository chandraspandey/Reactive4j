package org.flowr.framework.core.security.access;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.flowr.framework.core.flow.persistence.ProviderPersistence;
import org.flowr.framework.core.model.Attribute;
import org.flowr.framework.core.model.AttributeSet;
import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.model.TextAttribute;
import org.flowr.framework.core.security.audit.Auditable;

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
	
	public HashMap<ACLType,AttributeSet> getAccessSet();

	public void setAccessSet(HashMap<ACLType,AttributeSet> accessSet);
	
	public void addAccess(ACLType aclType,Attribute textAttribute);	
	
	public void removeAccess(ACLType aclType,Attribute textAttribute);
	
	public void setPersistence(ProviderPersistence<?> securePersistence);

	
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

		private ProviderPersistence<?> persistence;
		private HashMap<ACLType,AttributeSet> accessSet = new HashMap<ACLType,AttributeSet>();

		
		@Override
		public HashMap<ACLType,AttributeSet> getAccessSet() {
			return accessSet;
		}

		@Override
		public void setAccessSet(HashMap<ACLType,AttributeSet> accessSet) {
			this.accessSet = accessSet;
		}
		
		@Override
		public void addAccess(ACLType aclType,Attribute textAttribute){
			
			AttributeSet aclAttributeSet = accessSet.get(aclType);
			
			aclAttributeSet.addAttribute(textAttribute);
			
			logChange(AuditType.CREATE,textAttribute);
		}
		
		@Override
		public void removeAccess(ACLType aclType,Attribute textAttribute){
			
			if(accessSet != null){
				
				AttributeSet aclAttributeSet = accessSet.get(aclType);
				
				Collection<Attribute> accessList = aclAttributeSet.getAttributeList();
				
				if(!accessList.isEmpty()){
					
					Iterator<Attribute> attributeIterator = accessList.iterator();
					
					while(attributeIterator.hasNext()){
						
						TextAttribute existingAttribute = (TextAttribute)attributeIterator.next();
						
						TextAttribute requestedAttribute = (TextAttribute)textAttribute;
						
						if(existingAttribute.getKey().equals(
								requestedAttribute.getKey())){						
							logChange(AuditType.DELETE,requestedAttribute);
							attributeIterator .remove();
						}
					}
				}
			}
			
		}

		
		@Override
		public void logChange(AuditType auditType,Model model) {
			/*securePersistence.setPersistenceType(
					this.getClass().getCanonicalName());
			securePersistence.persistWithIdentity(auditType, model);*/
		}

		@Override
		public void setPersistence(ProviderPersistence<?> persistence) {
			this.persistence=persistence;
		}


		
		public String toString(){
			
			return "ACL{"+
					"\n accessSet : "+accessSet+
					"\n securePersistence : "+persistence+
					"\n}\n";
		}
	}

}
