
/**
 * Extends Handler for providing Token Handling capabilities.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.token;

import java.util.List;

import org.flowr.framework.core.context.Context.AccessContext;

public interface TokenHandler{

    public enum HandlerType{
        CERTIFICATE,
        TOKEN
    }
    
    String normalizeWithoutToken(String text, String delim);
    
    String annotateWithToken(String text, String delim,String append);
    
    List<String> tokenAsList(String text, String delim);
    
    Token getAccess(AccessContext accessContext);
    
}
