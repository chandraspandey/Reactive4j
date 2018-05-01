package org.flowr.framework.core.constants;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ClientConstants {

	public static final String CONFIG_CLIENT_SERVICE_SERVER_NAME				= "reactive.client.service.server.name";
	public static final String CONFIG_CLIENT_SERVICE_SERVER_PORT				= "reactive.client.service.server.port";
	public static final String CONFIG_CLIENT_THREADS_MIN						= "reactive.client.service.thread.min";
	public static final String CONFIG_CLIENT_THREADS_MAX						= "reactive.client.service.thread.max";
	public static final String CONFIG_CLIENT_TIMEOUT							= "reactive.client.service.timeout";
	public static final String CONFIG_CLIENT_TIMEOUT_UNIT						= "reactive.client.service.timeout.unit";
	public static final String CONFIG_CLIENT_NOTIFICATION_ENDPOINT				= "reactive.client.service.notification.endpoint";
	
	public static final String CONFIG_CLIENT_REQUEST_SCALE_MIN					= "reactive.client.request.scale.min";
	public static final String CONFIG_CLIENT_REQUEST_SCALE_MAX					= "reactive.client.request.scale.max";
	public static final String CONFIG_CLIENT_REQUEST_SCALE_RETRY				= "reactive.client.request.scale.retry";
	public static final String CONFIG_CLIENT_REQUEST_SCALE_TIMEOUT				= "reactive.client.request.scale.timeout";
	public static final String CONFIG_CLIENT_REQUEST_SCALE_TIMEOUT_UNIT			= "reactive.client.request.scale.timeout.unit";
	public static final String CONFIG_CLIENT_REQUEST_SCALE_UNIT					= "reactive.client.request.scale.unit";
	public static final String CONFIG_CLIENT_REQUEST_SCALE_PROGRESS_UNIT		= "reactive.client.request.scale.progress.unit";
	public static final String CONFIG_CLIENT_REQUEST_SCALE_INTERVAL				= "reactive.client.request.scale.interval";
	public static final String CONFIG_CLIENT_REQUEST_RETRY_INTERVAL				= "reactive.client.request.scale.retry.interval";
	public static final String CONFIG_CLIENT_REQUEST_NOTIFICATION_DELIVERY_TYPE = "reactive.client.request.scale.notification.delivery.type";	
	
	
	// supports up to 100 nodes
	public static final int CONFIG_CLIENT_SERVICE_ENDPOINT_MIN					= 0;
	public static final int CONFIG_CLIENT_SERVICE_ENDPOINT_MAX					= 100;
	public static final String CONFIG_CLIENT_SERVICE_ENDPOINT_NAME				= "reactive.client.service.endpoint.name";
	public static final String CONFIG_CLIENT_SERVICE_ENDPOINT_URL				= "reactive.client.service.endpoint.url";
	public static final String CONFIG_CLIENT_SERVICE_ENDPOINT_TYPE				= "reactive.client.service.endpoint.type";
	public static final String CONFIG_CLIENT_SERVICE_ENDPOINT_TIMEOUT			= "reactive.client.service.endpoint.timeout.interval";
	public static final String CONFIG_CLIENT_SERVICE_ENDPOINT_TIMEOUT_UNIT		= "reactive.client.service.endpoint.timeout.unit";
	public static final String CONFIG_CLIENT_SERVICE_ENDPOINT_HEARTBEAT			= "reactive.client.service.endpoint.hearbeat.interval";
	public static final String CONFIG_CLIENT_SERVICE_ENDPOINT_HEARTBEAT_UNIT	= "reactive.client.service.endpoint.hearbeat.unit";

	// supports up to 100 pipelines
	public static final int CONFIG_CLIENT_PIPELINE_MIN							= 0;
	public static final int CONFIG_CLIENT_PIPELINE_MAX							= 100;
	public static final String CONFIG_CLIENT_PIPELINE_NAME						= "reactive.client.pipeline.name";
	public static final String CONFIG_CLIENT_PIPELINE_POLICY_ELEMENT			= "reactive.client.pipeline.policy.element";
	public static final String CONFIG_CLIENT_PIPELINE_BATCH_MODE				= "reactive.client.pipeline.dispatch.batch.mode";
	public static final String CONFIG_CLIENT_PIPELINE_BATCH_SIZE				= "reactive.client.pipeline.dispatch.batch.size";
	public static final String CONFIG_CLIENT_PIPELINE_ELEMENT_MAX				= "reactive.client.pipeline.elements.max";

}
