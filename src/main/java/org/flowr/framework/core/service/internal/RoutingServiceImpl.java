package org.flowr.framework.core.service.internal;

import java.util.Optional;
import java.util.Properties;

import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.promise.PromiseRequest;
import org.flowr.framework.core.security.ClientIdentity;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.ServiceResponse;
import org.flowr.framework.core.service.route.ServiceRouteMapping;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class RoutingServiceImpl implements RoutingService{

	private ServiceUnit serviceUnit 		= ServiceUnit.SINGELTON;
	private String serviceName				= FrameworkConstants.FRAMEWORK_SERVICE_ROUTING;
	private ServiceType serviceType			= ServiceType.ROUTING;
	private static ServiceRouteMapping<ClientIdentity,Class<? extends ServiceResponse>> routeMapping = 
			new ServiceRouteMapping<ClientIdentity,Class<? extends ServiceResponse>>();
	@SuppressWarnings("unused")
	private ServiceFramework<?,?> serviceFramework			= null;
	
	@Override
	public void setServiceFramework(ServiceFramework<?,?> serviceFramework) {
		this.serviceFramework = serviceFramework;
	}
	
	@Override
	public void bindServiceRoute(ClientIdentity clientIdentity,Class<? extends ServiceResponse>  responseClass) 
		throws ConfigurationException{
		
		routeMapping.add(clientIdentity,responseClass);
	}
	
	@Override
	public Class<? extends ServiceResponse> getServiceRoute(PromiseRequest<?> promiseRequest){
		
		ClientIdentity clientIdentity =  promiseRequest.getClientIdentity();
		
		return routeMapping.getRoute(clientIdentity);
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
