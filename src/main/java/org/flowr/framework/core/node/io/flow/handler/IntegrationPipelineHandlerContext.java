package org.flowr.framework.core.node.io.flow.handler;

import org.flowr.framework.core.context.Context;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler.HandlerType;
import org.flowr.framework.core.service.Service.ServiceType;

/**
 * Defines contextual processing model for selection of appropriate handler for Handling processing of different file 
 * types. Builtin types would involve builtin IO operation. Specific Types would involve external capabilities to
 * process the file type such as media, certificates. Based on the type the handler the IO, media operator is selected 
 * It acts as a reusable input  parameter for IO or media handling.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class IntegrationPipelineHandlerContext implements Context{

	private NetworkBufferQueue networkBufferQueue;
	private HandlerType handlerType;
		
	public IntegrationPipelineHandlerContext() {
		
	}
	
	public IntegrationPipelineHandlerContext(HandlerType handlerType, NetworkBufferQueue networkBufferQueue) {
		
		this.handlerType		= handlerType; 
		this.networkBufferQueue = networkBufferQueue;
	}	
	
	public HandlerType getHandlerType() {
		return handlerType;
	}
	public void setHandlerType(HandlerType handlerType) {
		this.handlerType = handlerType;
	}	

	public NetworkBufferQueue getNetworkBufferQueue() {
		return networkBufferQueue;
	}
	public void setNetworkBufferQueue(NetworkBufferQueue networkBufferQueue) {
		this.networkBufferQueue = networkBufferQueue;
	}
	
	@Override
	public ServiceType getServiceType() {
		return ServiceType.NETWORK;
	}
	
	public String toString(){
		
		return "HandlerContext { "+handlerType+" | "+networkBufferQueue+" }\n";
	}


}
