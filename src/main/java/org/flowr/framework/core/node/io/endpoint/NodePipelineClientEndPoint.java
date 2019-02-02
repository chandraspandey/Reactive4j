package org.flowr.framework.core.node.io.endpoint;

import java.io.IOException;
import java.sql.Timestamp;

import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.node.io.network.NodeChannel;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class NodePipelineClientEndPoint implements NodeEndPoint{
	
	private EndPointStatus endPointStatus 									= EndPointStatus.UNREACHABLE;
	private Timestamp lastUpdated											= null;
	private NotificationProtocolType notificationProtocolType				= null;
	private PipelineFunctionType pipelineFunctionType						= null;
	
	public NodeChannel configureAsPipeline(NodeEndPointConfiguration nodeEndPointConfiguration) 
			throws IOException{
					
		return new NodePipelineClient(nodeEndPointConfiguration);	
	}

	
	@Override
	public EndPointStatus getEndPointStatus() {

		return endPointStatus;
	}
	
	public void setNotificationProtocolType(NotificationProtocolType notificationProtocolType){
		this.notificationProtocolType = notificationProtocolType;
	}
	
	public NotificationProtocolType getNotificationProtocolType(){
		return this.notificationProtocolType;
	}
	
	@Override
	public Timestamp getLastUpdated(){
		
		return this.lastUpdated;
	}
	
	@Override
	public ServiceConfiguration getServiceConfiguration(){
		
		return null;
	}
	
	@Override
	public PipelineFunctionType getPipelineFunctionType() {
		return pipelineFunctionType;
	}

	@Override	
	public void setPipelineFunctionType(PipelineFunctionType pipelineFunctionType) {
		this.pipelineFunctionType = pipelineFunctionType;
	}
	
	public void setEndPointStatus(EndPointStatus endPointStatus) {
		this.endPointStatus = endPointStatus;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	public String toString(){
		
		return "NodePipelineClientEndPoint{"+
				" endPointStatus : "+endPointStatus+	
				" | notificationProtocolType : "+notificationProtocolType+	
				" | pipelineFunctionType : "+pipelineFunctionType+				
				" | lastUpdated : "+lastUpdated+
				"}\n";

	}
}