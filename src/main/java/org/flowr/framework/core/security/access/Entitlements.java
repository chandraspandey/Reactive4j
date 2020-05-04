
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.security.access;

import org.flowr.framework.core.security.access.Access.AccessStatus;

public interface Entitlements {

    AccessStatus hasAccessPrivilege();
    
    public class DefaultEntitlements implements Entitlements{

        @Override
        public AccessStatus hasAccessPrivilege() {

            return AccessStatus.DENIED;
        }
        
    }
}
