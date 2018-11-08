package org.flowr.framework.core.constants;

import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class NodeConstants {
	
	public static final long NODE_PIPELINE_CLIENT_SLEEP_INTERVAL						= 6000;
	public static final long NODE_PIPELINE_CLIENT_SEEK_TIMEOUT							= 6000;
	public static final TimeUnit NODE_PIPELINE_CLIENT_SEEK_TIMEUNIT						= TimeUnit.MILLISECONDS;
	public static final long NODE_PIPELINE_SERVER_SLEEP_INTERVAL						= 6000;
	public static final int NODE_PIPELINE_RESPONSE_LIMIT_MIN							= 20;
	public static final int NODE_PIPELINE_RESPONSE_LIMIT_MAX							= 20;
	
	// supports up to 100 nodes
	public static final String CONFIG_NODE_PIPELINE_MIN									= "flowr.node.pipeline.min";
	public static final String CONFIG_NODE_PIPELINE_MAX									= "flowr.node.pipeline.max";
	public static final String CONFIG_NODE_PIPELINE_ENDPOINT_MIN						= "flowr.node.pipeline.endpoint.min";
	public static final String CONFIG_NODE_PIPELINE_ENDPOINT_MAX						= "flowr.node.pipeline.endpoint.max";

	public static final String CONFIG_NODE_PIPELINE_THREADS_MIN							= "flowr.node.pipeline.endpoint.thread.min";
	public static final String CONFIG_NODE_PIPELINE_THREADS_MAX							= "flowr.node.pipeline.endpoint.thread.max";
	public static final String CONFIG_NODE_PIPELINE_TIMEOUT								= "flowr.node.pipeline.endpoint.timeout";
	public static final String CONFIG_NODE_PIPELINE_TIMEOUT_UNIT						= "flowr.node.pipeline.endpoint.timeout.unit";
	public static final String CONFIG_NODE_PIPELINE_NOTIFICATION_ENDPOINT				= "flowr.node.pipeline.endpoint.notification.endpoint";	
	public static final String CONFIG_NODE_PIPELINE_ENDPOINT_HEARTBEAT		 			= "flowr.node.pipeline.endpoint.hearbeat.interval";
	public static final String CONFIG_NODE_PIPELINE_ENDPOINT_HEARTBEAT_UNIT				= "flowr.node.pipeline.endpoint.hearbeat.unit";	
	
	// In bound
	public static final String CONFIG_NODE_PIPELINE_INBOUND_SERVER_NAME					= "flowr.node.pipeline.inbound.server.name";
	public static final String CONFIG_NODE_PIPELINE_INBOUND_SERVER_CHANNEL_NAME			= "flowr.node.pipeline.inbound.server.channel.name";
	public static final String CONFIG_NODE_PIPELINE_INBOUND_SERVER_HOST_NAME			= "flowr.node.pipeline.inbound.server.host.name";
	public static final String CONFIG_NODE_PIPELINE_INBOUND_SERVER_HOST_PORT			= "flowr.node.pipeline.inbound.server.host.port";
	public static final String CONFIG_NODE_PIPELINE_INBOUND_POLICY_ELEMENT				= "flowr.node.pipeline.inbound.policy.element";
	public static final String CONFIG_NODE_PIPELINE_INBOUND_BATCH_MODE					= "flowr.node.pipeline.inbound.dispatch.batch.mode";
	public static final String CONFIG_NODE_PIPELINE_INBOUND_BATCH_SIZE					= "flowr.node.pipeline.inbound.dispatch.batch.size";
	public static final String CONFIG_NODE_PIPELINE_INBOUND_ELEMENT_MAX					= "flowr.node.pipeline.inbound.elements.max";
	public static final String CONFIG_NODE_PIPELINE_INBOUND_FUNCTION_TYPE				= "flowr.node.pipeline.inbound.endpoint.functiontype";
	public static final String CONFIG_NODE_PIPELINE_INBOUND_ENDPOINT_TYPE				= "flowr.node.pipeline.inbound.endpoint.type";
	public static final String CONFIG_NODE_PIPELINE_INBOUND_CLIENT_NAME					= "flowr.node.pipeline.inbound.client.name";
	public static final String CONFIG_NODE_PIPELINE_INBOUND_CLIENT_MAX					= "flowr.node.pipeline.inbound.client.max";
	public static final String CONFIG_NODE_PIPELINE_INBOUND_CLIENT_HOST_NAME			= "flowr.node.pipeline.inbound.client.host.name";
	public static final String CONFIG_NODE_PIPELINE_INBOUND_CLIENT_HOST_PORT			= "flowr.node.pipeline.inbound.client.host.port";
	
	
	// Out bound
	public static final String CONFIG_NODE_PIPELINE_OUTBOUND_SERVER_NAME				= "flowr.node.pipeline.outbound.server.name";
	public static final String CONFIG_NODE_PIPELINE_OUTBOUND_SERVER_CHANNEL_NAME		= "flowr.node.pipeline.outbound.server.channel.name";
	public static final String CONFIG_NODE_PIPELINE_OUTBOUND_SERVER_HOST_NAME			= "flowr.node.pipeline.outbound.server.host.name";
	public static final String CONFIG_NODE_PIPELINE_OUTBOUND_SERVER_HOST_PORT			= "flowr.node.pipeline.outbound.server.host.port";
	public static final String CONFIG_NODE_PIPELINE_OUTBOUND_POLICY_ELEMENT				= "flowr.node.pipeline.outbound.policy.element";
	public static final String CONFIG_NODE_PIPELINE_OUTBOUND_BATCH_MODE					= "flowr.node.pipeline.outbound.dispatch.batch.mode";
	public static final String CONFIG_NODE_PIPELINE_OUTBOUND_BATCH_SIZE					= "flowr.node.pipeline.outbound.dispatch.batch.size";
	public static final String CONFIG_NODE_PIPELINE_OUTBOUND_ELEMENT_MAX				= "flowr.node.pipeline.outbound.elements.max";
	public static final String CONFIG_NODE_PIPELINE_OUTBOUND_FUNCTION_TYPE				= "flowr.node.pipeline.outbound.endpoint.functiontype";
	public static final String CONFIG_NODE_PIPELINE_OUTBOUND_ENDPOINT_TYPE				= "flowr.node.pipeline.outbound.endpoint.type";
	public static final String CONFIG_NODE_PIPELINE_OUTBOUND_CLIENT_NAME				= "flowr.node.pipeline.outbound.client.name";
	public static final String CONFIG_NODE_PIPELINE_OUTBOUND_CLIENT_MAX					= "flowr.node.pipeline.outbound.client.max";
	public static final String CONFIG_NODE_PIPELINE_OUTBOUND_CLIENT_HOST_NAME			= "flowr.node.pipeline.outbound.client.host.name";
	public static final String CONFIG_NODE_PIPELINE_OUTBOUND_CLIENT_HOST_PORT			= "flowr.node.pipeline.outbound.client.host.port";

}
