package org.flowr.framework.core.service.extension;

import java.util.Optional;
import java.util.Properties;

import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.service.ServiceFramework;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class StagePromiseServiceImpl<REQUEST,RESPONSE> implements StagePromiseService<REQUEST,RESPONSE>{

	private ServiceUnit serviceUnit 		= ServiceUnit.SINGELTON;
	private String serviceName				= FrameworkConstants.FRAMEWORK_SERVICE_PROMISE_STAGE;
	private ServiceType serviceType			= ServiceType.PROMISE_STAGE;
	@SuppressWarnings("unused")
	private ServiceFramework<REQUEST,RESPONSE> serviceFramework			= null;
	
	@SuppressWarnings("unchecked")
	@Override
	public void setServiceFramework(ServiceFramework<?,?> serviceFramework) {
		this.serviceFramework = (ServiceFramework<REQUEST, RESPONSE>) serviceFramework;
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
	public void addServiceListener(EventPublisher engineListener) {
		
	}

	@Override
	public ServiceStatus startup(Optional<Properties> configProperties) {
		return ServiceStatus.STARTED;
	}

	@Override
	public ServiceStatus shutdown(Optional<Properties> configProperties) {
		return ServiceStatus.STOPPED;
	}

}
