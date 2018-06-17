package org.flowr.framework.core.security.access;

import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import org.flowr.framework.core.model.AttributeSet;
import org.flowr.framework.core.security.Scope;
import org.flowr.framework.core.security.access.Privilege.PrivilegeType;

/**
 * Marker interface for defining scope attributes for privilege.
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface PrivilegeScope extends Scope{
	
	
	public String getIdentifier();

	public void setIdentifier(String identifier) ;	
	
	public void setPerimeterScopeType(PerimeterScopeType perimeterScopeType);
	
	public PerimeterScopeType getPerimeterScopeType();
	
	public void setIdentityScopeType(IdentityScopeType identityScopeType);
	
	public IdentityScopeType getIdentityScopeType();
	
	public void setPrivilegeStartDate(Calendar startDate);
	
	public void setPrivilegeEndDate(Calendar endDate);
	
	public Calendar getPrivilegeStartDate();
	
	public Calendar getPrivilegeEndDate();
	
	public void setPrivilegeType(Collection<PrivilegeType> privilegeTypeList);
	
	public Collection<PrivilegeType> getPrivilegeType();
	
	
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
			// TODO Auto-generated method stub
			
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
		public int compareTo(PrivilegeScope privilegeScope) {
			
			int status = -1;
			
			
			if(
					this.startDate == privilegeScope.getPrivilegeStartDate() &&
					this.endDate   == privilegeScope.getPrivilegeEndDate() &&				
					this.scopeType == privilegeScope.getScopeType() &&
					this.perimeterScopeType == privilegeScope.getPerimeterScopeType() &&
					this.identityScopeType == privilegeScope.getIdentityScopeType() &&
					this.scopeAttributes.equals(privilegeScope.getScopeAttributes())
					){
				
					
					
					if(this.privilegeTypeList.size() == 
							privilegeScope.getPrivilegeType().size()){
						
						Iterator<PrivilegeType> privilegeTypeIterator = 
								this.privilegeTypeList.iterator();
						
						while(privilegeTypeIterator.hasNext()){
							
							if(privilegeScope.getPrivilegeType().contains(
									privilegeTypeIterator.next())){
								status = 0;
							}else{
								break;
							}
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
