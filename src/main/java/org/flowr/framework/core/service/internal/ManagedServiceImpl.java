package org.flowr.framework.core.service.internal;

import java.util.Optional;
import java.util.Properties;

import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.process.management.ManagedRegistry;
import org.flowr.framework.core.process.management.ProcessHandler;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.dependency.Dependency;
import org.flowr.framework.core.service.dependency.DependencyLoop;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ManagedServiceImpl implements ManagedService,Dependency, DependencyLoop{

	private ServiceUnit serviceUnit 				= ServiceUnit.POOL;
	private String dependencyName					= ManagedServiceImpl.class.getSimpleName();
	private DependencyType dependencyType 			= DependencyType.MANDATORY;
	private String serviceName						= FrameworkConstants.FRAMEWORK_SERVICE_MANAGEMENT;
	private ServiceType serviceType					= ServiceType.MANAGEMENT;
	private static ManagedRegistry managedRegistry 	= new ManagedRegistry();
	@SuppressWarnings("unused")
	private ServiceFramework<?,?> serviceFramework	= null;
	
	@Override
	public void setServiceFramework(ServiceFramework<?,?> serviceFramework) {
		this.serviceFramework = serviceFramework;
	}
	
	public ManagedServiceImpl() {
		
	}
	
	public void put(String name, ProcessHandler handler) {
		managedRegistry.bind(name, handler);		
	}
	
	public ProcessHandler get(String name) {
		
		return managedRegistry.lookup(name);
	}
	
	@Override
	public void setServiceType(ServiceType serviceType) {
		
		this.serviceType = serviceType;
	}
	
	@Override
	public ServiceType getServiceType() {
		
		return this.serviceType;
	}
	
	@Override
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	@Override
	public String getServiceName() {

		return this.serviceName;
	}	
	
	@Override
	public void setServiceUnit(ServiceUnit serviceUnit) {
		this.serviceUnit = serviceUnit;
	}

	@Override
	public ServiceUnit getServiceUnit() {
		return this.serviceUnit;
	}

	@Override
	public DependencyStatus loopTest() {
		DependencyStatus status = DependencyStatus.UNSATISFIED;
		
		return status;
	}

	@Override
	public String getDependencyName() {
		return this.dependencyName;
	}

	@Override
	public DependencyType getDependencyType() {
		return this.dependencyType;
	}

	@Override
	public DependencyStatus verify() {
		DependencyStatus status = DependencyStatus.UNSATISFIED;
		
		return status;
	}

	@Override
	public ServiceStatus startup(Optional<Properties> configProperties) {
		// TODO Auto-generated method stub
		return ServiceStatus.STARTED;
	}

	@Override
	public ServiceStatus shutdown(Optional<Properties> configProperties) {
		// TODO Auto-generated method stub
		return ServiceStatus.STOPPED;
	}

	@Override
	public void addServiceListener(EventPublisher serviceListener) {
		// TODO Auto-generated method stub
		
	}

}
