
/**
 * Defines PromiseServer as end point for serving requests. Inherits ReactiveTarget for delegating requests for
 * response. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.promise;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.promise.Promisable.PromisableType;
import org.flowr.framework.core.target.ReactiveTarget;


public interface PromiseTypeServer extends ReactiveTarget{

    Scale buildProgressScale(PromisableType promisableType,double now) throws PromiseException;    

    PromisableType getPromisableType();
    
    static String serverIdentifier(){
        
        String hostname = "Unknown";

        try
        {
            hostname = InetAddress.getLocalHost().getHostName();
        }catch (UnknownHostException ex){
            Logger.getRootLogger().error("Hostname can not be resolved");
            Logger.getRootLogger().error(ex);
        }
        return hostname;
        
    }
    
    static String processIdentifier(){
        
        return ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
    }

}
