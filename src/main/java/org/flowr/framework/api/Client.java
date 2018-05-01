package org.flowr.framework.api;

import javax.servlet.ServletContextEvent;

import org.flowr.framework.core.service.ServiceHook;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class Client implements Provider,ServiceHook{

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		// TODO Auto-generated method stub
		
	}

}
