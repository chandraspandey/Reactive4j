package org.flowr.framework.core.security.access;

/**
 * Defines Role as a type with associated Privilege.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface Role{

	/*
	 * The Role Type is defined from usecase perspective with increasing 
	 * scope where visibility & control is APP < CONTAINER < VM < HOST < 
	 * SEGMENT< DOMAIN < PERIMETER.
	 * The Privilege maps to the perimeter segment.
	 */
	public enum RoleType{
		GUEST,
		USER,
		OPERATOR,
		ADMIN	
	}
	
	public RoleType getRoleType();
	
	public void setRoleType(RoleType roleType);
	
	public void setPrivilege(Privilege privilege);
	
	public Privilege getPrivilege();
	
	public void setIdentifier(String identifier);
	
	public String getIdentifier();
	
	public class AccessRole implements Role {

		private String identifier;	
		private RoleType roleType;
		private Privilege privilege;
		
		@Override
		public void setIdentifier(String identifier) {
			this.identifier = identifier;
		}

		@Override
		public String getIdentifier() {
			return this.identifier;
		}	
		
		@Override
		public boolean equals(Object role){
			
			boolean isEqual = false;
			
			if(role.getClass().getCanonicalName()==this.getClass().getCanonicalName()){
				
				if(this.identifier.equals(((Role)role).getIdentifier())
					&&	roleType != null && ((Role)role).getRoleType() != null && 
						roleType == ((Role)role).getRoleType()	
						){
					isEqual = true;
				}
			}
			
			return isEqual;
		}
		
		
		@Override
		public RoleType getRoleType() {
			return this.roleType;
		}

		@Override
		public void setRoleType(RoleType roleType) {
			this.roleType=roleType;
		}

		@Override
		public void setPrivilege(Privilege privilege) {
			this.privilege=privilege;
		}

		@Override
		public Privilege getPrivilege() {
			return this.privilege;
		}

		public String toString(){
			
			return "AccessRole{"+
					"\n identifier : "+identifier+
					"\n roleType : "+roleType+
					"\n privilege : "+privilege+
					"\n}\n";
		}	
		
	}

}
