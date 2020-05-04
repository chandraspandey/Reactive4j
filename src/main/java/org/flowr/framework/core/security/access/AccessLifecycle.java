
/**
 * Defines Lifecycle behavior for Access. Federation mechanism leverage the 
 * lifecycle entrypoints for session instrumentation. 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

package org.flowr.framework.core.security.access;

import org.flowr.framework.core.security.Identity;
import org.flowr.framework.core.security.token.Token;

public interface AccessLifecycle {

    void login(Identity identity);
    
    Token renewToken(Token token);
    
    void logout(Identity identity);  
    
}
