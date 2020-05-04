
/**
 * Defines system level services as accessible for a node. Node here may refer to host, VM or container.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service.internal;

import java.io.InputStream;
import java.io.OutputStream;

import org.flowr.framework.api.Node;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.process.management.NodeProcessHandler;
import org.flowr.framework.core.service.FrameworkService;

public interface NodeService extends FrameworkService, Runnable{
        
    static NodeService getInstance() {
        
        return DefaultNodeService.getInstance();
    }
    
    static NodeProcessHandler getDefaultNodeProcessHandler() {
        
        return DefaultNodeService.getDefaultNodeProcessHandler();
    }
    
    Process lookupProcess(String executable) throws ConfigurationException ;
    
    InputStream processIn(String executable) throws ConfigurationException;
    
    OutputStream processOut(String executable) throws ConfigurationException ;
    
    InputStream processError(String executable) throws ConfigurationException;
    
    void setNode(Node node);
    
    Node getNode();
    
    
    public final class DefaultNodeService{
        
        private static NodeService nodeService;
        private static NodeProcessHandler defaultNodeHandler;
        
        private DefaultNodeService() {
        }
        
        public static NodeService getInstance() {
            
            if(nodeService == null) {
                nodeService = new NodeServiceImpl();
            }
            
            return nodeService;
        }
        
        public static NodeProcessHandler getDefaultNodeProcessHandler() {
            
            if(defaultNodeHandler == null) {
                defaultNodeHandler  = new DefaultNodeProcessHandler();
            }
            
            return defaultNodeHandler;
        }
        
    }

    
}
