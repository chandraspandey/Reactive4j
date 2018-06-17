package org.flowr.framework.core.security;

import org.flowr.framework.core.security.Scope.PerimeterScopeType;
import org.flowr.framework.core.security.Scope.ScopeType;
import org.flowr.framework.core.security.Security.SecurityDomainType;
import org.flowr.framework.core.security.access.ACL.ACLType;
import org.flowr.framework.core.security.access.ACL.DomainACLType;
import org.flowr.framework.core.security.access.filter.Filter.FilterType;

/**
 * Defines pre identified dependency related to decision. Based on applied 
 * context the implementation can provide run time discovery of dependency as 
 * deemed applicable for the scenario or use case. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Dependent {

	public class SecurityDependent{

		private ScopeType scopeType;
		private FilterType filterType;
		private PerimeterScopeType perimeterScopeType;
		private SecurityDomainType securityDomainType;
		private DomainACLType domainACLType;
		private ACLType aclType;
		
		public ScopeType getScopeType() {
			return scopeType;
		}
		public void setScopeType(ScopeType scopeType) {
			this.scopeType = scopeType;
		}
		public FilterType getFilterType() {
			return filterType;
		}
		public void setFilterType(FilterType filterType) {
			this.filterType = filterType;
		}
		public PerimeterScopeType getPerimeterScopeType() {
			return perimeterScopeType;
		}
		public void setPerimeterScopeType(PerimeterScopeType perimeterScopeType) {
			this.perimeterScopeType = perimeterScopeType;
		}
		public SecurityDomainType getSecurityDomainType() {
			return securityDomainType;
		}
		public void setSecurityDomainType(SecurityDomainType securityDomainType) {
			this.securityDomainType = securityDomainType;
		}
		public DomainACLType getDomainACLType() {
			return domainACLType;
		}
		public void setDomainACLType(DomainACLType domainACLType) {
			this.domainACLType = domainACLType;
		}

		public ACLType getAclType() {
			return aclType;
		}
		public void setAclType(ACLType aclType) {
			this.aclType = aclType;
		}


		public String toString(){
			
			return "SecurityDependent{"+
					"\n scopeType : "+scopeType+
					"\n filterType : "+filterType+
					"\n perimeterScopeType : "+perimeterScopeType+	
					"\n securityDomainType : "+securityDomainType+	
					"\n domainACLType : "+domainACLType+	
					"\n aclType : "+aclType+	
					"\n}\n";
		}	

	}
	
}
