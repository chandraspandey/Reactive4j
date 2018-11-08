package org.flowr.framework.core.node.io.flow.handler;

import org.flowr.framework.core.node.io.flow.IOFlow.IOFlowType;

/**
 * Defines top level marker interface & behavior for handling capabilities.
 * Extension interfaces should provide specific capabilities to process different
 * types. 
 * @author Chandra Pandey
 *
 */

public interface IntegrationPipelineHandler {

	public enum HandlerType{
		IO,
		MEDIA,
		CERTIFICATE,
		TOKEN
	}
	
	public void setHandlerType(HandlerType handlerType);
	
	public HandlerType getHandlerType();
	
	public void setHandlerName(String handlerName);
	
	public String getHandlerName();
	
	public void setIOFlowType(IOFlowType ioFlowType);
	
	public IOFlowType getIOFlowType();
	
	public void handle(IntegrationPipelineHandlerContext handlerContext);
	
	class DefaultIntegrationPipelineHandler implements IntegrationPipelineHandler{
		
		private HandlerType handlerType = HandlerType.IO;
		private String handlerName;
		private IOFlowType ioFlowType;
		private IntegrationBridge integrationBridge;
		
		public DefaultIntegrationPipelineHandler(String handlerName,HandlerType handlerType,IOFlowType ioFlowType,
			IntegrationBridge integrationBridge) {
			
			this.handlerName 		= handlerName;
			this.handlerType 		= handlerType;
			this.ioFlowType  		= ioFlowType;
			this.integrationBridge  = integrationBridge;
		}
		
		@Override
		public void setHandlerType(HandlerType handlerType) {
			this.handlerType = handlerType;
		}

		@Override
		public HandlerType getHandlerType() {
			return this.handlerType;
		}

		@Override
		public void handle(IntegrationPipelineHandlerContext handlerContext) {
			this.integrationBridge.handle(handlerContext);
		}
		
		public void setHandlerName(String handlerName) {
			this.handlerName = handlerName;
		}
		
		public String getHandlerName() {
			return this.handlerName;
		}
		
		public void setIOFlowType(IOFlowType ioFlowType) {
			this.ioFlowType = ioFlowType;
		}
		
		public IOFlowType getIOFlowType() {
			return this.ioFlowType;
		}
		
		public IntegrationBridge getIntegrationBridge() {
			return integrationBridge;
		}

		public void setIntegrationBridge(IntegrationBridge integrationBridge) {
			this.integrationBridge = integrationBridge;
		}
		
		public String toString() {
			
			return "{ "+handlerName+" | "+ioFlowType+" | "+handlerType+" | "+integrationBridge+" }";
		}


	}
	
}
