package org.flowr.framework.core.promise;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.promise.Promisable.PromisableType;
import org.flowr.framework.core.target.ReactiveTarget;


/**
 * Defines PromiseServer as end point for serving requests. Inherits ReactiveTarget for delegating requests for
 * response. 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface PromiseTypeServer<REQUEST,RESPONSE> extends ReactiveTarget<REQUEST,RESPONSE>{

	public Scale buildProgressScale(PromisableType promisableType,double now) throws ConfigurationException;	

	public PromisableType getPromisableType();
	
	public static String ServerIdentifier(){
		
		String hostname = "Unknown";

		try
		{
			hostname = InetAddress.getLocalHost().getHostName();
		}catch (UnknownHostException ex){
		    System.out.println("Hostname can not be resolved");
		}
		return hostname;
		
	}
	
	public static String ProcessIdentifier(){
		
		return ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
	}

}
