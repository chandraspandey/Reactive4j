
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io.network;

import java.net.SocketAddress;

import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupRoleType;

public class NetworkEntry {
    
    private String networkGroupName;
    private NetworkGroupRoleType networkRoleType;
    private SocketAddress socketAddress;
    
    public NetworkEntry() {     
    }
    
    public NetworkEntry(String networkGroupName,NetworkGroupRoleType networkRoleType, SocketAddress socketAddress){
        
        this.networkGroupName= networkGroupName;
        this.networkRoleType = networkRoleType;
        this.socketAddress   = socketAddress;
    }

    public NetworkGroupRoleType getNetworkRoleType() {
        return networkRoleType;
    }

    public void setNetworkRoleType(NetworkGroupRoleType networkRoleType) {
        this.networkRoleType = networkRoleType;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }
    
     @Override
     public int hashCode() {
                         
         int hashCode = 0;
         
         if(socketAddress != null) {
             
             hashCode = socketAddress.hashCode();
         }else {
             hashCode = this.getClass().getCanonicalName().hashCode();
         }
         
         return hashCode;
     }
    
    @Override
    public boolean equals(Object obj) {
        
        boolean isEqual = false;
        
        if(obj != null && obj.getClass() == this.getClass()) {
            
            NetworkEntry that = (NetworkEntry)obj;
            
            if(
                    this.networkGroupName.equals(that.networkGroupName)
                    && this.networkRoleType == that.networkRoleType 
                    && this.socketAddress.equals(that.socketAddress)
            ) {             
                isEqual = true;
            }           
        }
        
        return isEqual;
    }
    
    public String getNetworkGroupName() {
        return networkGroupName;
    }

    public void setNetworkGroupName(String networkGroupName) {
        this.networkGroupName = networkGroupName;
    }
    
    public String toString(){
        
        return "<"+networkGroupName+" : "+ networkRoleType+" : "+socketAddress+">\n";

    }



}
