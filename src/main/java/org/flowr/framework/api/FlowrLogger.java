
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.api;

import java.util.logging.Logger;

public interface FlowrLogger {

    
    public enum Level{
        OFF,
        FATAL,
        ERROR,
        WARN,
        INFO,
        DEBUG,
        TRACE,
        ALL
    }
    
    void log(Level level, String message);

    void log(Level level, String message, Object object);

    void error(Level level, String message, Throwable throwable);
    
    
    static Logger getLogger() {
        
        return  Logger.getGlobal();
    }
}
