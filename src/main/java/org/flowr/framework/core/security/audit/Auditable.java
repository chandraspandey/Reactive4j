package org.flowr.framework.core.security.audit;

import org.flowr.framework.core.model.Model;

/**
 * Defines characteristics for auditable transaction.  
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface Auditable{
	
	public enum AuditType{
		CREATE,
		MODIFY,
		DELETE,
		ADD,
		REMOVE,
		ASSOCIATE,
		DISASSOCIATE
	}
	
	public void logChange(AuditType auditType,Model model);
	
}
