
/**
 * 
 * Models 1:N relationship between local Server & remote/local Clients.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io.network;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class LocalApplicationGroup extends AbstractNodeApplicationGroup{

    private NetworkEntry serverNetworkEntry                 = new NetworkEntry();
    private ArrayList<NetworkEntry> clientNetworkEntryList  = new ArrayList<>();
    
    public LocalApplicationGroup() throws IOException {
        super();
    }

    @Override
    public NetworkGroupStatus registerSocketAddress(NetworkGroupRoleType networkGroupRoleType,
            SocketAddress socketAddress) {
        
        NetworkGroupStatus networkGroupStatus = NetworkGroupStatus.UNKNOWN;
        
        switch(networkGroupRoleType) {
            
            case CLIENT:{
                NetworkEntry networkEntry = new NetworkEntry(this.getGroupName(),networkGroupRoleType,socketAddress);
                clientNetworkEntryList.add(networkEntry);
                networkGroupStatus = NetworkGroupStatus.ADDED;              
                break;
            }case SERVER:{
                serverNetworkEntry.setNetworkGroupName(this.getGroupName());
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
    public NetworkGroupStatus deregisterSocketAddress(NetworkGroupRoleType networkGroupRoleType,
            SocketAddress socketAddress) {
        
        NetworkGroupStatus networkGroupStatus = NetworkGroupStatus.UNKNOWN;
        
        switch(networkGroupRoleType) {
            
            case CLIENT:{
                
                if(clientNetworkEntryList.remove(new NetworkEntry(this.getGroupName(),networkGroupRoleType,
                        socketAddress))) {
                    
                    networkGroupStatus = NetworkGroupStatus.REMOVED;
                }else {
                    networkGroupStatus = NetworkGroupStatus.INVALID;
                }               
                break;
            }case SERVER:{
                
                if(clientNetworkEntryList.isEmpty()) {
                    
                    networkGroupStatus = NetworkGroupStatus.REMOVED;
                    serverNetworkEntry = null;
                }else {
                    networkGroupStatus = NetworkGroupStatus.ASSOCIATED;
                }   
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

    
    public List<NetworkEntry> getClientNetworkEntryList() {
        return clientNetworkEntryList;
    }
    
    @Override
    public String toString(){
        
        return "LocalApplicationGroup{"+
                " \n| serverNetworkEntry : "+serverNetworkEntry+    
                " \n| clientNetworkEntryList : "+clientNetworkEntryList+
                super.toString()+
                "\n}\n";
    }
}
