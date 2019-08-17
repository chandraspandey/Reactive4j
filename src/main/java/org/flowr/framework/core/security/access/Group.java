package org.flowr.framework.core.security.access;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.flowr.framework.core.exception.AccessSecurityException;
import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.security.Identity;
import org.flowr.framework.core.security.audit.Auditable;

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

public interface Group extends Auditable{
	
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
	
	public void setGroupType(GroupType groupType);
	
	public GroupType getGroupType();
	
	public void setGroupName(String groupName);
	
	public String getGroupName();
	
	public void setAssociateRole(Role role) throws AccessSecurityException;
	
	public Role getAssociateRole();
	
	public void addMember(Identity identity);
	
	public void removeMember(Identity identity);
		
	public void setPersistence(ProviderPersistence<?> securePersistence);
	
	public void setIdentifier(String identifier);
	
	public String getIdentifier();
	
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

	public abstract class SecurityGroup implements Group {

		private String identifier;	
		private String groupName;
		private Collection<Identity> members = new ArrayList<Identity>();
		private ProviderPersistence<?> persistence;
		private GroupType groupType;
		private String parentTopologyIdentifier;
		
		@Override
		public void setIdentifier(String identifier) {
			this.identifier = identifier;
		}

		@Override
		public String getIdentifier() {
			return this.identifier;
		}

		@Override
		public void addMember(Identity identity) {
			//logChange(AuditType.ADD,identity);
			members.add(identity);
		}

		public void removeMember(Identity identity){
			
			if(members != null){
							
				if(!members.isEmpty()){
					
					Iterator<Identity> identityIterator = members.iterator();
					
					while(identityIterator.hasNext()){
						
						Identity existingIdentity = identityIterator.next();
								
						if(existingIdentity.equals(identity)){
							//logChange(AuditType.REMOVE,existingIdentity);
							identityIterator.remove();
						}
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
		public void logChange(AuditType auditType, Model model) {
			//persistence.setPersistenceType(this.getClass().getCanonicalName());
			//persistence.persistWithIdentity(auditType,model);
		}

		@Override
		public void setPersistence(ProviderPersistence<?> persistence) {
			this.persistence=persistence;
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
		public boolean equals(Object securityGroup){
			
			boolean isEqual = false;
			
			if(securityGroup.getClass().getCanonicalName()==this.getClass().getCanonicalName()){
				
				if(this.identifier.equals(((SecurityGroup)securityGroup).getIdentifier())){
					isEqual = true;
				}
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
