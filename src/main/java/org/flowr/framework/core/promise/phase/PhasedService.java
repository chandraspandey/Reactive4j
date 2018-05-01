package org.flowr.framework.core.promise.phase;

/**
 * Defines PhasedService where long running programs run in distinct phases and
 * in each of the phases can offer incremental capability to provide information
 * back to the consuming services/users. Some of the use cases are network,
 * infrastructure(physical, virtual, remote), data centers(physical, cloud etc.),
 * Big data, search etc. 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface PhasedService{

	/*
	 * DISCOVERY 	: The remote server is discovering the information which is not
	 * yet complete.
	 * AGGREGATION 	: The information is getting aggregated for delivery. 
	 * DISSEMINATION: The information is ready to be dissemniated.
	 * COMPLETE : Marks the competion of response
	 */
	public enum ServicePhase{
		DEFAULT,
		DISCOVERY,
		AGGREGATION,
		DISSEMINATION,
		COMPLETE
	}
	
	
	public void setServicePhase(ServicePhase currentPhase); 
	
	public ServicePhase getServicePhase();
}
