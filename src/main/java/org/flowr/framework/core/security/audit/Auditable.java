
/**
 * Defines characteristics for auditable transaction.  
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.audit;

import org.flowr.framework.core.model.Model;

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
    
    void logChange(AuditType auditType,Model model);
    
}
