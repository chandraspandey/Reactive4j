package org.flowr.framework.core.constants;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ServerConstants {

	public static final String CONFIG_SERVER_NAME								= "flowr.server.name";
	public static final String CONFIG_SERVER_PORT								= "flowr.server.port";
	public static final String CONFIG_SERVER_THREADS_MIN						= "flowr.server.thread.min";
	public static final String CONFIG_SERVER_THREADS_MAX						= "flowr.server.thread.max";
	public static final String CONFIG_SERVER_TIMEOUT							= "flowr.server.timeout";
	public static final String CONFIG_SERVER_TIMEOUT_UNIT						= "flowr.server.timeout.unit";
	public static final String CONFIG_SERVER_NOTIFICATION_ENDPOINT				= "flowr.server.notification.endpoint";
	public static final String CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_MIN		= "flowr.server.response.progress.scale.min";
	public static final String CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_MAX		= "flowr.server.response.progress.scale.max";
	public static final String CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_TIME_UNIT	= "flowr.server.response.progress.scale.time.unit";
	public static final String CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_UNIT		= "flowr.server.response.progress.scale.unit";
	public static final String CONFIG_SERVER_RESPONSE_PROGRESS_SCALE_INTERVAL	= "flowr.server.response.progress.scale.interval";
	public static final String CONFIG_SERVER_RESPONSE_PROGRESS_NOTIFICATION_DELIVERY_TYPE
		= "flowr.server.response.progress.scale.notification.delivery.type";
	
	public static final String CONFIG_SERVER_EVENT_BUS_NAME						= "EventBus";

	// supports up to 100 nodes
	public static final int CONFIG_SERVER_PIPELINE_ENDPOINT_MIN					= 0;
	public static final int CONFIG_SERVER_PIPELINE_ENDPOINT_MAX					= 100;
	public static final String CONFIG_SERVER_PIPELINE_ENDPOINT_NAME				= "flowr.server.pipeline.endpoint.name";
	public static final String CONFIG_SERVER_PIPELINE_FUNCTION_TYPE				= "flowr.server.pipeline.endpoint.functiontype";
	public static final String CONFIG_SERVER_PIPELINE_ENDPOINT_URL				= "flowr.server.pipeline.endpoint.url";
	public static final String CONFIG_SERVER_PIPELINE_ENDPOINT_TYPE				= "flowr.server.pipeline.endpoint.type";
	public static final String CONFIG_SERVER_PIPELINE_ENDPOINT_TIMEOUT			= "flowr.server.pipeline.endpoint.timeout.interval";
	public static final String CONFIG_SERVER_PIPELINE_ENDPOINT_TIMEOUT_UNIT		= "flowr.server.pipeline.endpoint.timeout.unit";
	public static final String CONFIG_SERVER_PIPELINE_ENDPOINT_HEARTBEAT		= "flowr.server.pipeline.endpoint.hearbeat.interval";
	public static final String CONFIG_SERVER_PIPELINE_ENDPOINT_HEARTBEAT_UNIT	= "flowr.server.pipeline.endpoint.hearbeat.unit";

	
	// supports up to 100 pipelines
	public static final int CONFIG_SERVER_PIPELINE_MIN							= 0;
	public static final int CONFIG_SERVER_PIPELINE_MAX							= 100;
	public static final String CONFIG_SERVER_PIPELINE_NAME						= "flowr.server.pipeline.name";
	public static final String CONFIG_SERVER_PIPELINE_POLICY_ELEMENT			= "flowr.server.pipeline.policy.element";
	public static final String CONFIG_SERVER_PIPELINE_BATCH_MODE				= "flowr.server.pipeline.dispatch.batch.mode";
	public static final String CONFIG_SERVER_PIPELINE_BATCH_SIZE				= "flowr.server.pipeline.dispatch.batch.size";
	public static final String CONFIG_SERVER_PIPELINE_ELEMENT_MAX				= "flowr.server.pipeline.elements.max";
	
}
