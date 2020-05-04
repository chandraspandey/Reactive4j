
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public interface ServiceHook extends ServletContextListener{

   void delegateService(ServletContextEvent event);
    
}
