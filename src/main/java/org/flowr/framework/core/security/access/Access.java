package org.flowr.framework.core.security.access;

import org.flowr.framework.core.context.AccessContext;
import org.flowr.framework.core.exception.AccessSecurityException;
import org.flowr.framework.core.security.token.Token;

/**
 * 
 *   
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Access {

	public enum AccessStatus{
		ACCEPTED,
		DENIED
	}
	
	public enum AccessMode{
		DIRECT,
		CLIENT,
		AGENT,
		PROXY,
		FEDERATED
	}
	
	public void setAccessMode(AccessMode accessMode);
	
	public AccessMode getAccessMode();

	
	/**
	 * Evaluates only static access as defined in source SecurityPerimeter ACL.
	 * Does not evaluates dynamic parameters.
	 * @return
	 */
	public AccessStatus hasAccessPrivilege();
	
	/**
	 * Returns expirable Token as access grant which is to be used for further 
	 * communication with destination identity.
	 * @param securityFilterContext
	 * @throws AccessSecurityException
	 * @return
	 */
	public Token getAccess(AccessContext 
			accessContext) throws org.flowr.framework.core.exception.AccessSecurityException;
		
	public void setPersistence(ProviderPersistence<?> persistence);
	
	
	public class SecureAccess implements Access{

		private AccessStatus accessStatus = AccessStatus.DENIED;
		private AccessMode accessMode = AccessMode.DIRECT;

		@Override
		public void setAccessMode(AccessMode accessMode) {
			this.accessMode = accessMode;
		}

		@Override
		public AccessMode getAccessMode() {
			return this.accessMode;
		}

		@Override
		public AccessStatus hasAccessPrivilege() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Token getAccess(AccessContext accessContext) throws AccessSecurityException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setPersistence(ProviderPersistence<?> persistence) {
			// TODO Auto-generated method stub
			
		}

		public String toString(){
			
			return "SecureAccess{"+
					"\n accessStatus : "+accessStatus+
					"\n accessMode : "+accessMode+
					"\n}\n";
		}
	}
}
