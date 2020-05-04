
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.node.io.flow.protocol;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

import org.flowr.framework.core.node.io.network.NetworkGroup;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelFlowType;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelState;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelStatus;

public interface FlowSocketChannel{

    ChannelStatus register();

    ChannelStatus close();

    ChannelStatus connect(SocketAddress serverSocketAddress) throws IOException;

    ByteBuffer readFromPipeline();

    ByteBuffer seek();

    SocketAddress getSocketAddress();

    ChannelStatus getChannelStatus();

    void setChannelStatus(ChannelStatus channelStatus);

    ChannelState getChannelState();

    void setChannelState(ChannelState channelState);

    ChannelFlowType getChannelFlowType();

    NetworkGroup getNetworkGroup();

    AsynchronousSocketChannel getSocketChannel();

    void cancel();

    void writeToPipeline();

    
}
