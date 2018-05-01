package org.flowr.framework.core.service.extension;

import java.util.Properties;

import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.model.MetaData;
import org.flowr.framework.core.service.ServiceFramework;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class RegistryServiceImpl implements RegistryService{

	private ServiceUnit serviceUnit 		= ServiceUnit.REGISTRY;
	private String dependencyName			= RegistryService.class.getSimpleName();
	private DependencyType dependencyType 	= DependencyType.MANDATORY;
	private String serviceName				= FrameworkConstants.FRAMEWORK_SERVICE_REGISTRY;
	private ServiceType serviceType			= ServiceType.REGISTRY;
	@SuppressWarnings("unused")
	private ServiceFramework<?,?> serviceFramework			= null;
	
	@Override
	public void setServiceFramework(ServiceFramework<?,?> serviceFramework) {
		this.serviceFramework = serviceFramework;
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
		// TODO Auto-generated method stub
		return null;
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
	public void addServiceListener(EventPublisher<MetaData> serviceListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServiceStatus startup(Properties configProperties) {
		// TODO Auto-generated method stub
		return ServiceStatus.STARTED;
	}

	@Override
	public ServiceStatus shutdown(Properties configProperties) {
		// TODO Auto-generated method stub
		return ServiceStatus.STOPPED;
	}

}
