
/**
 * Defines characteristics for auditable transaction.  
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.audit;

import org.flowr.framework.core.model.PersistenceProvider;

public interface AuditHandler extends Audit, Auditable{

    void setPersistence(PersistenceProvider persistence);
}
