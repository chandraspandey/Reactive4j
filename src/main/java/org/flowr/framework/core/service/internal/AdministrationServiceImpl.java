package org.flowr.framework.core.service.internal;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.service.Service;
import org.flowr.framework.core.service.ServiceFramework;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class AdministrationServiceImpl implements AdministrationService{

	private ServiceUnit serviceUnit 		= ServiceUnit.SINGELTON;
	private String serviceName				= FrameworkConstants.FRAMEWORK_SERVICE_ADMINISTRATION;
	private ServiceType serviceType			= ServiceType.ADMINISTRATION;

	private ServiceFramework<?,?> serviceFramework			= null;
	
	@Override
	public void setServiceFramework(ServiceFramework<?,?> serviceFramework) {
		this.serviceFramework = serviceFramework;
	}
	
	public List<Service> getServiceList(){
		return this.serviceFramework.getServiceList();
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
	public ServiceStatus startup(Optional<Properties> configProperties) {
		return ServiceStatus.STARTED;
	}

	@Override
	public ServiceStatus shutdown(Optional<Properties> configProperties) {
		return ServiceStatus.STOPPED;
	}

	@Override
	public void addServiceListener(EventPublisher serviceListener) {
	}
}
