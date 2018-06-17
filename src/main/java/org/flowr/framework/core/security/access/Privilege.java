package org.flowr.framework.core.security.access;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.flowr.framework.core.flow.persistence.ProviderPersistence;
import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.security.Identity;
import org.flowr.framework.core.security.access.PrivilegeScope.AccessPrivilegeScope;
import org.flowr.framework.core.security.audit.Auditable;


/**
 * Marker interface for defining Privilege. Concrete implementation is context
 * specific. Access verifies Privilege for executable.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface Privilege extends Auditable{

	public enum PrivilegeType{
		VIEW,
		READ,
		WRITE,
		CONFIGURE,
		INSTALL,
		PLUGIN_INSTALL
	}
	
	public void setPersistence(ProviderPersistence<?> securePersistence);
	
	public void setAssociatedIdentity(Identity identity);
	
	public Identity getAssociatedIdentity();
	
	public void setPrivilegeScopes(Collection<AccessPrivilegeScope> privilegeList);
	
	public Collection<AccessPrivilegeScope> getPrivilegeScopes();
	
	public void addPrivilege(AccessPrivilegeScope accessPrivilegeScope);
	
	public void removePrivilege(AccessPrivilegeScope accessPrivilegeScope);
	
	/**
	 * Provides concrete implementation of Privilege & associated life cycle 
	 * management.
	 * @author Chandra Pandey
	 *
	 */

	public class AccessPrivilege implements Privilege {

		private Collection<AccessPrivilegeScope> privilegeScopes = 
				new ArrayList<AccessPrivilegeScope>();
		
		private ProviderPersistence<?> persistence;
		private Identity identity;
		private String parentTopologyIdentifier;
		
		@Override
		public boolean equals(Object otherPrivilege) {
			
			boolean isEqual = false;
			
			if( identity.equals(((AccessPrivilege)otherPrivilege).getAssociatedIdentity()) &&				
				privilegeScopes.size() == ((AccessPrivilege)otherPrivilege).getPrivilegeScopes().size() 
				){
				
				if(privilegeScopes != null && ! privilegeScopes.isEmpty()){
				
					Iterator<AccessPrivilegeScope> privilegeIterator = 
							privilegeScopes.iterator();
					
					while(privilegeIterator.hasNext()){
						
						AccessPrivilegeScope accessPrivilegeScope = 
								privilegeIterator.next();
						
						Collection<AccessPrivilegeScope> accessPrivilegeScopes = 
								((AccessPrivilege)otherPrivilege).getPrivilegeScopes();
						
						if(accessPrivilegeScopes != null && !
								accessPrivilegeScopes.isEmpty()){
							
							Iterator<AccessPrivilegeScope> accessPrivilegeIterator = 
									accessPrivilegeScopes.iterator();
							
							OTHER:
							while(accessPrivilegeIterator.hasNext()){
								
								AccessPrivilegeScope scope = 
										accessPrivilegeIterator.next();
								
								if(accessPrivilegeScope.compareTo(scope) == 0){
									isEqual = true;
									break OTHER;
								}
							}
							
						}
						
						
					}
				}
				
				
			}
			return isEqual;
		}
		
		@Override
		public void setPersistence(ProviderPersistence<?> securePersistence) {
			this.persistence=securePersistence;
		}
		
		@Override
		public void logChange(AuditType auditType, Model model) {
			//persistence.persistWithIdentity(auditType, model);
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
