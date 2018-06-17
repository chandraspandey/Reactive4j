package org.flowr.framework.core.security.access;

import org.flowr.framework.core.security.Identity;
import org.flowr.framework.core.security.token.Token;

/**
 * Defines Lifecycle behavior for Access. Federation mechanism leverage the 
 * lifecycle entrypoints for session instrumentation. 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface AccessLifecycle {

	public void login(Identity identity);
	
	public Token renewToken(Token token);
	
	public void logout(Identity identity);	
	
}
