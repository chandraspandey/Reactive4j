package org.flowr.framework.core.process.management;

import java.util.Collection;
import java.util.HashMap;

import org.flowr.framework.core.process.ProcessRegistry;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class ManagedRegistry implements ProcessRegistry<String, ProcessHandler> {

	private static HashMap<String,ProcessHandler> handlerMap 	= new HashMap<String,ProcessHandler>();
	private RegistryType registryType							= RegistryType.LOCAL;
	
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
	public void persist() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		handlerMap.clear();
	}

	@Override
	public HashMap<String, ProcessHandler> restore() {
		// TODO Auto-generated method stub
		return null;
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
