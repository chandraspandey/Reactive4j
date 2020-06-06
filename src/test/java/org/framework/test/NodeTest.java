
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.framework.test;


import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.AbstractMap.SimpleEntry;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.io.NetworkByteBuffer;
import org.flowr.framework.core.node.io.endpoint.NodePipelineClient;
import org.flowr.framework.core.node.io.endpoint.NodePipelineServer;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelFlowType;
import org.junit.Test;


public class NodeTest extends FrameworkTest{

    @Test
    public void testConfig() throws ConfigurationException{
                    
        SimpleEntry<NodePipelineServer,NodePipelineClient> entry = app.configureApp();
        
        NodePipelineServer nodePipelineServer = entry.getKey();
        NodePipelineClient nodePipelineClient = entry.getValue();
         
        NetworkByteBuffer inBuffer = new NetworkByteBuffer(ByteBuffer.wrap(
                 FrameworkConstants.FRAMEWORK_NETWORK_IO_PING.getBytes(Charset.defaultCharset())));
        
        nodePipelineClient.getSocketChannel(ChannelFlowType.OUTBOUND).writeToPipeline(inBuffer);        
         
        Logger.getRootLogger().info("NodeTest : Client Sent : "+FrameworkConstants.FRAMEWORK_NETWORK_IO_PING);
        
        boolean isProcessing = true;
        
        while(isProcessing) {
        
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Logger.getRootLogger().error(e);
                Thread.currentThread().interrupt();
            }
            
            ByteBuffer serverBuffer = nodePipelineServer.getSocketChannel(ChannelFlowType.INBOUND).readFromPipeline();
            serverBuffer.flip();
            
            byte[] contentArray =  new byte[FrameworkConstants.FRAMEWORK_NETWORK_IO_PING.length()];
            
            serverBuffer.get(contentArray);
            
            String serverContent = new String(contentArray, Charset.defaultCharset());
            
            if( serverContent.equals(FrameworkConstants.FRAMEWORK_NETWORK_IO_PING)) {
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Logger.getRootLogger().error(e);
                    Thread.currentThread().interrupt();
                }
                
                Logger.getRootLogger().info("NodeTest : Server Recieved : "+
                        FrameworkConstants.FRAMEWORK_NETWORK_IO_PING);
                
                isProcessing = false;
            }            
        } 

     }
    
}