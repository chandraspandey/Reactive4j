package org.flowr.framework.core.constants;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ServerConstants {

	public static final String CONFIG_SERVER_NAME								= "reactive.server.name";
	public static final String CONFIG_SERVER_PORT								= "reactive.server.port";
	public static final String CONFIG_SERVER_THREADS_MIN						= "reactive.server.thread.min";
	public static final String CONFIG_SERVER_THREADS_MAX						= "reactive.server.thread.max";
	public static final String CONFIG_SERVER_TIMEOUT							= "reactive.server.timeout";
	public static final String CONFIG_SERVER_TIMEOUT_UNIT						= "reactive.server.timeout.unit";
	public static final String CONFIG_SERVER_NOTIFICATION_ENDPOINT				= "reactive.server.notification.endpoint";
	public static final String CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_MIN		= "reactive.server.response.progress.scale.min";
	public static final String CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_MAX		= "reactive.server.response.progress.scale.max";
	public static final String CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_TIME_UNIT	= "reactive.server.response.progress.scale.time.unit";
	public static final String CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_UNIT		= "reactive.server.response.progress.scale.unit";
	public static final String CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_INTERVAL	= "reactive.server.response.progress.scale.interval";
	public static final String CONFIG_SERVER_RESPONSE_PROGRESS_NOTIFICATION_DELIVERY_TYPE
		= "reactive.server.response.progress.scale.notification.delivery.type";
	
	public static final String CONFIG_SERVER_EVENT_BUS_NAME						= "EventBus";

	// supports up to 100 nodes
	public static final int CONFIG_SERVER_SERVICE_ENDPOINT_MIN					= 0;
	public static final int CONFIG_SERVER_SERVICE_ENDPOINT_MAX					= 100;
	public static final String CONFIG_SERVER_SERVICE_ENDPOINT_NAME				= "reactive.server.service.endpoint.name";
	public static final String CONFIG_SERVER_SERVICE_ENDPOINT_URL				= "reactive.server.service.endpoint.url";
	public static final String CONFIG_SERVER_SERVICE_ENDPOINT_TYPE				= "reactive.server.service.endpoint.type";
	public static final String CONFIG_SERVER_SERVICE_ENDPOINT_TIMEOUT			= "reactive.server.service.endpoint.timeout.interval";
	public static final String CONFIG_SERVER_SERVICE_ENDPOINT_TIMEOUT_UNIT		= "reactive.server.service.endpoint.timeout.unit";
	public static final String CONFIG_SERVER_SERVICE_ENDPOINT_HEARTBEAT			= "reactive.server.service.endpoint.hearbeat.interval";
	public static final String CONFIG_SERVER_SERVICE_ENDPOINT_HEARTBEAT_UNIT	= "reactive.server.service.endpoint.hearbeat.unit";

	
	// supports up to 100 pipelines
	public static final int CONFIG_SERVER_PIPELINE_MIN							= 0;
	public static final int CONFIG_SERVER_PIPELINE_MAX							= 100;
	public static final String CONFIG_SERVER_PIPELINE_NAME						= "reactive.server.pipeline.name";
	public static final String CONFIG_SERVER_PIPELINE_POLICY_ELEMENT			= "reactive.server.pipeline.policy.element";
	public static final String CONFIG_SERVER_PIPELINE_BATCH_MODE				= "reactive.server.pipeline.dispatch.batch.mode";
	public static final String CONFIG_SERVER_PIPELINE_BATCH_SIZE				= "reactive.server.pipeline.dispatch.batch.size";
	public static final String CONFIG_SERVER_PIPELINE_ELEMENT_MAX				= "reactive.server.pipeline.elements.max";
	
}
