
/**
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

package org.flowr.framework.core.node.io.network;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.AsynchronousChannelGroup;
import java.util.AbstractMap.SimpleEntry;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.io.endpoint.NodeConfig;
import org.flowr.framework.core.node.io.flow.ByteEnumerableType;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler;
import org.flowr.framework.core.node.io.pipeline.NetworkPipeline;

public interface NetworkGroup {

    /**
     * 
     * Defines network group for classification & handling
     * @author Chandra Shekhar Pandey
     * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
     *
     */
    public enum NetworkGroupType implements ByteEnumerableType{
        NONE(0),
        LOCAL(1),
        REMOTE(2);

        private byte code;
        
        NetworkGroupType(int code){
            
            this.code = (byte)code;
        }

        @Override
        public byte getCode() {
            
            return code;
        }   
        
        public static NetworkGroupType getType(int code) {
            
            NetworkGroupType networkGroupType = null;
            
            switch((byte) code) {
                
                case 0:{
                    networkGroupType = NONE;
                    break;
                }case 1:{
                    networkGroupType = LOCAL;
                    break;
                }case 2:{
                    networkGroupType = REMOTE;
                    break;
                }default :{
                    networkGroupType = NONE;
                    break;
                }           
            }
            
            return networkGroupType;
        }
    }
    
    /**
     * 
     * Defines network group role for classification & handling
     * @author Chandra Shekhar Pandey
     * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
     *
     */
    public enum NetworkGroupRoleType implements ByteEnumerableType{
        NONE(0),
        CLIENT(1),
        SERVER(2);
        
        private byte code;
        
        NetworkGroupRoleType(int code){
            
            this.code = (byte)code;
        }

        @Override
        public byte getCode() {
            
            return code;
        }   
        
        public static NetworkGroupRoleType getType(int code) {
            
            NetworkGroupRoleType networkGroupRoleType = null;
            
            switch((byte) code) {
                
                case 0:{
                    networkGroupRoleType = NONE;
                    break;
                }case 1:{
                    networkGroupRoleType = CLIENT;
                    break;
                }case 2:{
                    networkGroupRoleType = SERVER;
                    break;
                }default :{
                    networkGroupRoleType = NONE;
                    break;
                }           
            }
            
            return networkGroupRoleType;
        }
    }
    
    /**
     * 
     * Defines network group status for operational handling
     * @author Chandra Shekhar Pandey
     * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
     *
     */
    public enum NetworkGroupStatus implements ByteEnumerableType{
        NONE(0),
        UNKNOWN(1),
        INVALID(2),
        IGNORED(3),     
        ASSOCIATED(4),
        DISASSOCIATED(5),
        ADDED(6),
        REMOVED(7);
        
        private byte code;
        
        NetworkGroupStatus(int code){
            
            this.code = (byte)code;
        }

        @Override
        public byte getCode() {
            
            return code;
        }   
        
        public static NetworkGroupStatus getType(int code) {
            
            NetworkGroupStatus networkGroupStatus = null;
            
            switch((byte) code) {
                
                case 0:{
                    networkGroupStatus = NONE;
                    break;
                }case 1:{
                    networkGroupStatus = UNKNOWN;
                    break;
                }case 2:{
                    networkGroupStatus = INVALID;
                    break;
                }case 3:{
                    networkGroupStatus = IGNORED;
                    break;
                }case 4:{
                    networkGroupStatus = ASSOCIATED;
                    break;
                }case 5:{
                    networkGroupStatus = DISASSOCIATED;
                    break;
                }case 6:{
                    networkGroupStatus = ADDED;
                    break;
                }case 7:{
                    networkGroupStatus = REMOVED;
                    break;
                }default :{
                    networkGroupStatus = NONE;
                    break;
                }           
            }
            
            return networkGroupStatus;
        }
    }       
    
    NetworkGroupStatus init(NetworkPipeline in,NetworkPipeline out, SimpleEntry<IntegrationPipelineHandler,
            IntegrationPipelineHandler> pipelineHandlerSet);
    
    NetworkGroupStatus close(SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> pipelineHandlerSet);
    
    NetworkPipeline getIn();
    
    NetworkPipeline getOut();
    
    SocketAddress getSocketAddress(String hostName, int portNumber) throws UnknownHostException;
    
    String getGroupName();
    
    AsynchronousChannelGroup getGroup();
    
    void setGroupName(String groupName);
    
    NetworkGroupStatus registerSocketAddress(NetworkGroupRoleType networkGroupRoleType,SocketAddress socketAddress);
    
    NetworkGroupStatus deregisterSocketAddress(NetworkGroupRoleType networkGroupRoleType,SocketAddress socketAddress);

    NetworkEntry getServerNetworkEntry();
    
    public final class NodeGroup{
        
        private static NodeGroup nodeGroup;
        private NetworkGroup localApplicationGroup ;
        private NetworkGroup remoteApplicationGroup;
        
        private NodeGroup() throws ConfigurationException{
            
            init();
        }
        
        public static NodeGroup geInstance() throws ConfigurationException {
            
            if(nodeGroup == null) {
                
                nodeGroup = new NodeGroup();
            }
            
            return nodeGroup;
        }
        
        public NetworkGroup createGroup(NodeConfig nodeConfig) throws ConfigurationException{
            
            NetworkGroup networkGroup = null;
             
            try {
                    
            
                if(nodeConfig != null && nodeConfig.isReuseNetworkGroup()) {
                
                    switch(nodeConfig.getNetworkGroupType()) {
                        
                        case LOCAL:{
                            
                            networkGroup = localApplicationGroup;       
                            break;
                        }case REMOTE:{
                                                
                            networkGroup = remoteApplicationGroup;    
                            break;
                        }default:{
                            networkGroup = new LocalApplicationGroup();
                            break;
                        }
                        
                    }
                    
                    if(networkGroup != null) {
                        networkGroup.setGroupName(nodeConfig.getNetworkGroupType().name());
                   }
                }else {
                    
                    throw new ConfigurationException(ErrorMap.ERR_CONFIG, "Mandatory NodeConfig not provided "
                            + "as input for NetworkGroup creation. nodeConfig : "+nodeConfig);
                }
                
               
            } catch (IOException e) {
                
                Logger.getRootLogger().error(e);
                Thread.currentThread().interrupt();
                throw new ConfigurationException(ErrorMap.ERR_CONFIG, e);
            }    
            
            return networkGroup;
        }

        private void init() throws ConfigurationException {
            
            try {
             
                localApplicationGroup  = new LocalApplicationGroup();
                remoteApplicationGroup = new RemoteApplicationGroup();              
            
            } catch (IOException e) {
                
                Logger.getRootLogger().error(e);
                Thread.currentThread().interrupt();
                throw new ConfigurationException(ErrorMap.ERR_CONFIG, e);
            }
        }
        
    }

    
}
