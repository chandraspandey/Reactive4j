package org.flowr.framework.core.service.internal;

import java.io.InputStream;
import java.io.OutputStream;

import org.flowr.framework.api.Node;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.process.management.NodeProcessHandler;
import org.flowr.framework.core.service.ServiceFrameworkComponent;

/**
 * Defines system level services as accessible for a node. Node here may refer to host, VM or container.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NodeService extends ServiceFrameworkComponent{
		
	public static NodeService getInstance() {
		
		return DefaultNodeService.getInstance();
	}
	
	public static NodeProcessHandler getDefaultNodeProcessHandler() {
		
		return DefaultNodeService.getDefaultNodeProcessHandler();
	}
	
	public Process lookupProcess(String executable) throws ConfigurationException ;
	
	public InputStream processIn(String executable) throws ConfigurationException;
	
	public OutputStream processOut(String executable) throws ConfigurationException ;
	
	public InputStream processError(String executable) throws ConfigurationException;
	
	public void setNode(Node node);
	
	public Node getNode();
	
	public void setNodeProcessHandler(NodeProcessHandler nodeProcessHandler);

	public NodeProcessHandler getNodeProcessHandler();
	
	public class DefaultNodeService{
		
		private static NodeService nodeService 						= null;
		private static NodeProcessHandler defaultNodeHandler 		= null;
		
		public static NodeService getInstance() {
			
			if(nodeService == null) {
				nodeService = new NodeServiceImpl();
			}
			
			return nodeService;
		}
		
		public static NodeProcessHandler getDefaultNodeProcessHandler() {
			
			if(defaultNodeHandler == null) {
				defaultNodeHandler 	= new DefaultNodeProcessHandler();
			}
			
			return defaultNodeHandler;
		}
		
	}
}
