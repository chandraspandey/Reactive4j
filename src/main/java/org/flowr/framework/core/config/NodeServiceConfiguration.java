
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

public class NodeServiceConfiguration extends ServiceConfiguration{

    private List<SimpleEntry<String, Integer>> clientEndPointList = new ArrayList<>();
    private String nodePipelineName;
    private String nodeChannelName;

    @Override
    public boolean isValid(){
        
        boolean isValid = false;
        
        if(!clientEndPointList.isEmpty() && nodePipelineName != null &&
                nodeChannelName != null && super.isValid()){
            
            isValid = true;
        }
        
        return isValid;
    }
    
    public void addClientEndPoint(SimpleEntry<String, Integer> clientEndPoint) {
        
        this.clientEndPointList.add(clientEndPoint);
    }
    
    public String getNodePipelineName() {
        return nodePipelineName;
    }

    public void setNodePipelineName(String nodePipelineName) {
        this.nodePipelineName = nodePipelineName;
    }

    public String getNodeChannelName() {
        return nodeChannelName;
    }

    public void setNodeChannelName(String nodeChannelName) {
        this.nodeChannelName = nodeChannelName;
    }
    
    public List<SimpleEntry<String, Integer>> getClientEndPointList() {
        return clientEndPointList;
    }
    
    @Override
    public String toString(){
        
        return "\nServiceConfiguration{"+             
                " clientEndPointList : "+clientEndPointList+
                " | "+super.toString()+ 
                "}\n";
    }
}
