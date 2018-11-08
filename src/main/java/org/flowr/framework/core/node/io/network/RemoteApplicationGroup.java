package org.flowr.framework.core.node.io.network;

import java.io.IOException;
import java.net.SocketAddress;

/**
 * 
 * Models 1:1 relationship between local Client & remote Server.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class RemoteApplicationGroup extends NodeApplicationGroup{

	private NetworkEntry serverNetworkEntry	= new NetworkEntry();
	private NetworkEntry clientNetworkEntry	= new NetworkEntry();
	
	public RemoteApplicationGroup() throws IOException {
		super();	
	}	

	@Override
	public NetworkGroupStatus registerSocketAddress(NetworkGroupRoleType networkGroupRoleType,SocketAddress socketAddress) {
		
		NetworkGroupStatus networkGroupStatus = NetworkGroupStatus.UNKNOWN;
		
		switch(networkGroupRoleType) {
			
			case CLIENT:{
				clientNetworkEntry.setNetworkRoleType(networkGroupRoleType);
				clientNetworkEntry.setSocketAddress(socketAddress);
				networkGroupStatus = NetworkGroupStatus.ADDED;
				break;
			}case SERVER:{
				serverNetworkEntry.setNetworkRoleType(networkGroupRoleType);
				serverNetworkEntry.setSocketAddress(socketAddress);
				networkGroupStatus = NetworkGroupStatus.ADDED;
				break;
			}default:{
				break;
			}		
		}
		
		return networkGroupStatus;
	}
	
	@Override
	public NetworkGroupStatus deregisterSocketAddress(NetworkGroupRoleType networkGroupRoleType,SocketAddress socketAddress) {
		
		NetworkGroupStatus networkGroupStatus = NetworkGroupStatus.UNKNOWN;
		
		switch(networkGroupRoleType) {
			
			case CLIENT:{				
				networkGroupStatus = NetworkGroupStatus.REMOVED;
				clientNetworkEntry = null;
				break;
			}case SERVER:{				
				networkGroupStatus = NetworkGroupStatus.REMOVED;
				serverNetworkEntry = null;
				break;
			}default:{
				break;
			}		
		}
		
		return networkGroupStatus;
	}
	
	@Override
	public NetworkEntry getServerNetworkEntry() {
		return serverNetworkEntry;
	}
	
	public NetworkEntry getClientNetworkEntry() {
		return clientNetworkEntry;
	}	

	
	public String toString(){
		
		return "RemoteApplicationGroup["+ clientNetworkEntry+" | "+serverNetworkEntry+"]\n";

	}


}
