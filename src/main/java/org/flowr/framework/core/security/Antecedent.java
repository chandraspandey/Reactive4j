package org.flowr.framework.core.security;

import org.flowr.framework.core.security.Scope.IdentityScopeType;
import org.flowr.framework.core.security.access.Group.GroupType;
import org.flowr.framework.core.security.access.Privilege.PrivilegeType;
import org.flowr.framework.core.security.access.Role.RoleType;

/**
 * Defines high level antecedent for applicability of decision related to pre 
 * defined policy.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Antecedent {


	public class SecurityAntecedent {

		private IdentityScopeType identityScopeType;
		private GroupType groupType;
		private RoleType roleType;
		private PrivilegeType privilegeType;
		
		public IdentityScopeType getIdentityScopeType() {
			return identityScopeType;
		}
		public void setIdentityScopeType(IdentityScopeType identityScopeType) {
			this.identityScopeType = identityScopeType;
		}

		public GroupType getGroupType() {
			return groupType;
		}
		public void setGroupType(GroupType groupType) {
			this.groupType = groupType;
		}
		public RoleType getRoleType() {
			return roleType;
		}
		public void setRoleType(RoleType roleType) {
			this.roleType = roleType;
		}
		public PrivilegeType getPrivilegeType() {
			return privilegeType;
		}
		public void setPrivilegeType(PrivilegeType privilegeType) {
			this.privilegeType = privilegeType;
		}

		
		public String toString(){
			
			return "SecurityAntecedent{"+
					"\n identityScopeType : "+identityScopeType+
					"\n groupType : "+groupType+	
					"\n roleType : "+roleType+	
					"\n privilegeType : "+privilegeType+	
					"\n}\n";
		}
		
	}
	
}
