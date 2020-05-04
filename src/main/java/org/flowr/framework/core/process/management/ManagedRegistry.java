
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.process.management;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.context.Context.PersistenceContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.model.PersistenceProvider.PeristenceType;
import org.flowr.framework.core.model.Tuple;
import org.flowr.framework.core.process.ProcessRegistry;
import org.flowr.framework.core.service.ServiceFramework;

public class ManagedRegistry implements ProcessRegistry<String, ProcessHandler> {

    private static HashMap<String,ProcessHandler> handlerMap    = new HashMap<>();
    private RegistryType registryType                           = RegistryType.LOCAL;
    
    @Override
    public void bind(String name, ProcessHandler handler) {
        handlerMap.put(name, handler);
    }

    @Override
    public void unbind(String name, ProcessHandler handler) {
        handlerMap.remove(name);
    }

    @Override
    public void rebind(String name, ProcessHandler handler) {
        unbind(name, handler);
        bind(name, handler);
    }

    @Override
    public Collection<ProcessHandler> list() {
        return handlerMap.values();
    }

    @Override
    public ProcessHandler lookup(String name) {
        return handlerMap.get(name);
    }

    @Override
    public void persist() throws ConfigurationException{
        
        PersistenceContext<String,HashMap<String, ProcessHandler>> context 
            = new PersistenceContext<>(
                    this.getClass().getCanonicalName(),
                PeristenceType.TUPLE
            );
        
        Tuple<String,HashMap<String, ProcessHandler>> tuple = 
                new Tuple<>(this.getClass().getCanonicalName(),handlerMap);
        
        Optional<Tuple<String,HashMap<String, ProcessHandler>>> tupleOption = Optional.of(tuple);
        context.setTupleOption(tupleOption);
        
        ServiceFramework.getInstance().getCatalog().getNodeService().getPersistenceHandler().persist(context);
    }
    
    @Override
    public void restore() throws ConfigurationException{

        PersistenceContext<String,HashMap<String, ProcessHandler>> context = ServiceFramework.getInstance()
                .getCatalog().getNodeService().getPersistenceHandler().restore(this.getClass().getCanonicalName());
        
        Optional<Tuple<String,HashMap<String, ProcessHandler>>> tupleOption = context.getTupleOption();
        
        if( tupleOption.isPresent() && tupleOption.get().getKey().equals(this.getClass().getCanonicalName())) {
                        
            setHandlerMap(tupleOption.get().getValues());
        }else {
            
            throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,
                    "Restore Operation failed for Persistence configuration in : "+this.getClass().getCanonicalName());
        }

    }
    
    private static void setHandlerMap(HashMap<String,ProcessHandler> map) {
        handlerMap = map;
    }

    @Override
    public void clear() {
        handlerMap.clear();
    }



    @Override
    public RegistryType getRegistryType() {
        return this.registryType;
    }

    @Override
    public void setRegistryType(RegistryType registryType) {
        this.registryType = registryType;
    }

}
