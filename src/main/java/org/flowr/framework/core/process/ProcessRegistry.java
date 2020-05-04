
/**
 * Defines Registry behaviour as a binding/unbinding mechanism to a Process.
 * Since a process type can be bound both as a subject & as a target, the Type
 * t is left open for the concrete implementation. 
 *  K : KEY
 *  L : LIST
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.process;

import java.util.Collection;

import org.flowr.framework.core.exception.ConfigurationException;

public interface ProcessRegistry<K,L> extends Registry{

    /**
     * Binds a type to a process. 
     * @param name
     * @param list
     */
    void bind(K name, L list);
    
    /**
     * Unbinds a type as identified by the name
     * @param name
     */
    void unbind(K name, L list);
    
    /**
     * Rebinds a type identified with a name by removing the earlier bound type
     * from the process. If the binding key does not exists or is already 
     * removed adds it to the client map
     * @param name
     * @param list
     */
    void rebind(K name, L list);
    
    /**
     * Returns a collection of the  types that are bound with the process
     * @return
     */
    Collection<L> list();
    
    /**
     * Looks up the bound type & returns the concrete instance 
     * @param name
     * @return
     */
    L lookup(K name);
    
    /**
     * Persists the existing map to recoverable storage in case of process
     * failure or planned restart.
     * @param name
     * @param list
     */
    void persist() throws ConfigurationException;
    
    /**
     * Clears the registry in case of deregistration of client & its associated
     * type
     */
    void clear() throws ConfigurationException;
    
    /**
     * Defines restore functionality from recoverable storage in case of process
     * failure or planned restart. The collection of elements should be auto 
     * populated to be invoked by Processor to rebuild the configuration
     * return the restored map  
     * @throws ConfigurationException 
     */
    void restore() throws ConfigurationException;
}
