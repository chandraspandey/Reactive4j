package org.flowr.framework.core.flow;

public interface EventFlow {

	/**
	 * Defines Listener type as differentiating type for end usage and determing
	 * capabilities to address listening behavior.  
	 * PROCESS	: Maps to normal State Change Machine occurrence in process flow  
	 * ENDPOINT : Maps to external,remote or asynchronously connected state change
	 * machine represented by any entity.  
	 */
	public enum FlowType{
		PROCESS,
		ENDPOINT		
	}
	
	/**
	 * EVENT	: Maps to normal State Change Machine occurrence in data change
	 * that can be assessed & qualified as Event for downstream consumption or
	 * process in a work flow.  
	 * GATEWAY	: Maps to Gateway functions which have specific state change
	 * machine related to switch, router, firewall and edge network end point.
	 * AVAILABILITY	: Maps to high availability configuration related to high 
	 * availability of Active-Passive pairing, Rack Pairing, Cluster health
	 * configuration.
	 * AGGREGATOR : Maps to Physical or virtual aggregator of traffic as hub or 
	 * data processing entities such as memory grid, spark, hadoop or kafka 
	 * DISSEMINATOR : Maps to Physical or virtual data disseminator and can
	 * represent audio, video, stream relay functions 
	 */
	public enum FlowFunctionType{
		EVENT,
		SERVER,
		GATEWAY,
		AVAILABILITY,
		AGGREGATOR,
		DISSEMINATOR
	}
	
	/**
	 * FLOW_SUBSCRIBER_CLIENT : Flow Subscription meant for client end processing as termination point
	 * FLOW_SUBSCRIBER_SERVER : Flow Subscription meant for server side processing as termination point
	 * FLOW_SUBSCRIBER_PROCESS: Flow Subscription meant for process governance such as monitoring, logging,
	 * health as termination point
	 */
	public enum FlowSubscriberType{
		FLOW_SUBSCRIBER_CLIENT,
		FLOW_SUBSCRIBER_SERVER,
		FLOW_SUBSCRIBER_PROCESS
	}
	
	/**
	 * FLOW_PUBLISHER_CLIENT : Flow publication as Origination point meant for client end processing
	 * FLOW_PUBLISHER_SERVER : Flow publication as Origination point meant for server side processing
	 * FLOW_PUBLISHER_PROCESS: Flow publication as Origination point meant for process governance such as monitoring, logging,
	 * health as termination point
	 */
	public enum FlowPublisherType{
		FLOW_PUBLISHER_CLIENT,
		FLOW_PUBLISHER_SERVER,
		FLOW_PUBLISHER_PROCESS
	}
}
