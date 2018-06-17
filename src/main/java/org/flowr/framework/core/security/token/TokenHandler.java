package org.flowr.framework.core.security.token;

import java.util.List;

/**
 * Extends Handler for providing Token Handling capabilities.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface TokenHandler{

	public enum HandlerType{
		CERTIFICATE,
		TOKEN
	}
	
	public String normalizeWithoutToken(String text, String delim);
	
	public String annotateWithToken(String text, String delim,String append);
	
	public List<String> tokenAsList(String text, String delim);
	
}
