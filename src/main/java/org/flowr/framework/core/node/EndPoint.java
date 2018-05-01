package org.flowr.framework.core.node;

import java.sql.Timestamp;

import org.flowr.framework.core.config.ServiceConfiguration;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */


public interface EndPoint {

	public enum EndPointStatus{
		NEGOTIATE,
		REACHABLE,
		UNREACHABLE
	}
	
	public void setEndPointType(String endPointType);
	
	public String getEndPointType();
	
	public EndPointStatus getEndPointStatus();
	
	public ServiceConfiguration getServiceConfiguration();
	
	public Timestamp getLastUpdated();
}
