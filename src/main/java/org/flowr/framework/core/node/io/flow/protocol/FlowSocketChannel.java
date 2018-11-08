package org.flowr.framework.core.node.io.flow.protocol;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Set;

import org.flowr.framework.core.node.io.network.NetworkGroup;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelFlowType;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelState;
import org.flowr.framework.core.node.io.network.NodeChannel.ChannelStatus;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface FlowSocketChannel{

	public Set<SocketOption<?>> getDefaultSupportedOption();

	public ChannelStatus register();

	public ChannelStatus close();

	public ChannelStatus connect(SocketAddress serverSocketAddress) throws IOException;

	public ByteBuffer readFromPipeline();

	public ByteBuffer seek();

	public SocketAddress getSocketAddress();

	public ChannelStatus getChannelStatus();

	public void setChannelStatus(ChannelStatus channelStatus);

	public ChannelState getChannelState();

	public void setChannelState(ChannelState channelState);

	public ChannelFlowType getChannelFlowType();

	public NetworkGroup getNetworkGroup();

	public AsynchronousSocketChannel getSocketChannel();

	public void cancel();

	public void writeToPipeline();

	
}
