package org.flowr.framework.core.service;

/**
 * Marker interface for service definition, concrete implementation provides the behavior for service operation.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Service extends ServiceLifecycle{
	
	public enum ServiceType{		
		ADMINISTRATION,
		BUS,
		CONFIGURATION,
		EVENT,
		FRAMEWORK,
		NOTIFICATION,
		PROMISE,
		HEALTH,
		PROMISE_PHASED,
		PROMISE_SCHEDULED,
		PROMISE_STAGE,
		PROMISE_MAP,
		PROMISE_STREAM,
		PROVIDER,
		PROCESS,
		REGISTRY,
		ROUTING,
		SERVER,
		NODE,
		MANAGEMENT,
		SUBSCRIPTION,
		SECURITY,
		API,
		NETWORK
	}
		
	public enum ServiceState{
		INITIALIZING,
		STARTING,
		STOPPING
	}
	
	public enum ServiceStatus{
		UNUSED,
		STARTED,
		SUSPENDED,
		RUNNING,
		RESUMED,
		STOPPED,
		ERROR
	}
	
	public enum ServiceMode{
		EMBEDDED,
		CONTAINER,
		SERVER
	}
	
	public enum ServiceUnit{
		SINGELTON,
		POOL,
		REGISTRY,
		PROVIDER,
		BUS
	}
	
	public void setServiceUnit(ServiceUnit serviceUnit);
	
	public ServiceUnit getServiceUnit();
	
	public void setServiceName(String serviceName);
	
	public String getServiceName();
	
	public void setServiceType(ServiceType serviceType);
	
	public ServiceType getServiceType();
}
