package org.flowr.framework.core.process;

import java.util.Collection;
import java.util.HashMap;

/**
 * Defines Registry behaviour as a binding/unbinding mechanism to a Process.
 * Since a process type can be bound both as a subject & as a target, the Type
 * t is left open for the concrete implementation. 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ProcessRegistry<KEY,LIST> extends Registry{

	/**
	 * Binds a type to a process. 
	 * @param name
	 * @param list
	 */
	public void bind(KEY name, LIST list);
	
	/**
	 * Unbinds a type as identified by the name
	 * @param name
	 */
	public void unbind(KEY name, LIST list);
	
	/**
	 * Rebinds a type identified with a name by removing the earlier bound type
	 * from the process. If the binding key does not exists or is already 
	 * removed adds it to the client map
	 * @param name
	 * @param list
	 */
	public void rebind(KEY name, LIST list);
	
	/**
	 * Returns a collection of the  types that are bound with the process
	 * @return
	 */
	public Collection<LIST> list();
	
	/**
	 * Looks up the bound type & returns the concrete instance 
	 * @param name
	 * @return
	 */
	public LIST lookup(KEY name);
	
	/**
	 * Persists the existing map to recoverable storage in case of process
	 * failure or planned restart.
	 * @param name
	 * @param list
	 */
	public void persist();
	
	/**
	 * Clears the registry in case of deregistration of client & its associated
	 * type
	 */
	public void clear();
	
	/**
	 * Defines restore functionality from recoverable storage in case of process
	 * failure or planned restart. The collection of elements should be auto 
	 * populated to be invoked by Processor to rebuild the configuration
	 * return the restored map  
	 */
	public HashMap<KEY,LIST> restore();
}
