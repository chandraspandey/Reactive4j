package org.flowr.framework.core.constants;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ClientConstants {

	public static final String CONFIG_CLIENT_SERVICE_SERVER_NAME				= "flowr.client.service.server.name";
	public static final String CONFIG_CLIENT_SERVICE_SERVER_PORT				= "flowr.client.service.server.port";
	public static final String CONFIG_CLIENT_THREADS_MIN						= "flowr.client.service.thread.min";
	public static final String CONFIG_CLIENT_THREADS_MAX						= "flowr.client.service.thread.max";
	public static final String CONFIG_CLIENT_TIMEOUT							= "flowr.client.service.timeout";
	public static final String CONFIG_CLIENT_TIMEOUT_UNIT						= "flowr.client.service.timeout.unit";
	public static final String CONFIG_CLIENT_NOTIFICATION_ENDPOINT				= "flowr.client.service.notification.endpoint";
	
	public static final String CONFIG_CLIENT_REQUEST_SCALE_MIN					= "flowr.client.request.scale.min";
	public static final String CONFIG_CLIENT_REQUEST_SCALE_MAX					= "flowr.client.request.scale.max";
	public static final String CONFIG_CLIENT_REQUEST_SCALE_RETRY				= "flowr.client.request.scale.retry";
	public static final String CONFIG_CLIENT_REQUEST_SCALE_TIMEOUT				= "flowr.client.request.scale.timeout";
	public static final String CONFIG_CLIENT_REQUEST_SCALE_TIMEOUT_UNIT			= "flowr.client.request.scale.timeout.unit";
	public static final String CONFIG_CLIENT_REQUEST_SCALE_UNIT					= "flowr.client.request.scale.unit";
	public static final String CONFIG_CLIENT_REQUEST_SCALE_PROGRESS_UNIT		= "flowr.client.request.scale.progress.unit";
	public static final String CONFIG_CLIENT_REQUEST_SCALE_INTERVAL				= "flowr.client.request.scale.interval";
	public static final String CONFIG_CLIENT_REQUEST_RETRY_INTERVAL				= "flowr.client.request.scale.retry.interval";
	public static final String CONFIG_CLIENT_REQUEST_NOTIFICATION_DELIVERY_TYPE = "flowr.client.request.scale.notification.delivery.type";	
	
	
	// supports up to 100 nodes
	public static final int CONFIG_CLIENT_PIPELINE_ENDPOINT_MIN					= 1;
	public static final int CONFIG_CLIENT_PIPELINE_ENDPOINT_MAX					= 100;
	public static final String CONFIG_CLIENT_PIPELINE_ENDPOINT_NAME				= "flowr.client.pipeline.endpoint.name";
	public static final String CONFIG_CLIENT_PIPELINE_ENDPOINT_URL				= "flowr.client.pipeline.endpoint.url";
	public static final String CONFIG_CLIENT_PIPELINE_FUNCTION_TYPE				= "flowr.client.pipeline.endpoint.functiontype";
	public static final String CONFIG_CLIENT_PIPELINE_ENDPOINT_TYPE				= "flowr.client.pipeline.endpoint.type";
	public static final String CONFIG_CLIENT_PIPELINE_ENDPOINT_TIMEOUT			= "flowr.client.pipeline.endpoint.timeout.interval";
	public static final String CONFIG_CLIENT_PIPELINE_ENDPOINT_TIMEOUT_UNIT		= "flowr.client.pipeline.endpoint.timeout.unit";
	public static final String CONFIG_CLIENT_PIPELINE_ENDPOINT_HEARTBEAT		= "flowr.client.pipeline.endpoint.hearbeat.interval";
	public static final String CONFIG_CLIENT_PIPELINE_ENDPOINT_HEARTBEAT_UNIT	= "flowr.client.pipeline.endpoint.hearbeat.unit";

	// supports up to 100 pipelines
	public static final int CONFIG_CLIENT_PIPELINE_MIN							= 1;
	public static final int CONFIG_CLIENT_PIPELINE_MAX							= 100;
	public static final String CONFIG_CLIENT_PIPELINE_NAME						= "flowr.client.pipeline.name";
	public static final String CONFIG_CLIENT_PIPELINE_POLICY_ELEMENT			= "flowr.client.pipeline.policy.element";
	public static final String CONFIG_CLIENT_PIPELINE_BATCH_MODE				= "flowr.client.pipeline.dispatch.batch.mode";
	public static final String CONFIG_CLIENT_PIPELINE_BATCH_SIZE				= "flowr.client.pipeline.dispatch.batch.size";
	public static final String CONFIG_CLIENT_PIPELINE_ELEMENT_MAX				= "flowr.client.pipeline.elements.max";

}
