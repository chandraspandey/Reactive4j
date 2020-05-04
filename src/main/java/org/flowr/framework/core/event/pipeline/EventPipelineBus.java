
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.event.pipeline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.Constant.ServerConstants;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;

public class EventPipelineBus implements EventBus {

    private static List<EventPipeline> pipelineList     = new ArrayList<>();
    private PipelineType pipelineType                   = PipelineType.BUS; 
    private String eventBusName                         = ServerConstants.SERVER_EVENT_BUS_NAME;

    @Override
    public void addEventPipeline(EventPipeline eventPipeline) {
        
        pipelineList.add(eventPipeline);
    }
    

    @Override
    public void removeEventPipeline(EventPipeline eventPipeline) {
        pipelineList.remove(eventPipeline);
    }
    

    @Override
    public EventPipeline lookup(String pipelineName){
        
        EventPipeline eventPipeline = null;
                
        Iterator<EventPipeline> iter = pipelineList.iterator();
        
        while(iter.hasNext()) {
            
            EventPipeline ePipeline = iter.next();
            
            if(ePipeline.getPipelineName().equals(pipelineName)) {
                eventPipeline = ePipeline;
                break;
            }
        }
        
        return eventPipeline;
    }
    

    @Override
    public List<EventPipeline> lookup(PipelineType pipelineType){
        
        List<EventPipeline> eventPipelineList = new ArrayList<>();
                
        Iterator<EventPipeline> iter = pipelineList.iterator();
        
        while(iter.hasNext()) {
            
            EventPipeline ePipeline = iter.next();
            
            if(ePipeline.getPipelineType() == pipelineType) {
                eventPipelineList.add(ePipeline);
            }
        }
        
        return eventPipelineList;
    }
    
    @Override
    public List<EventPipeline> lookup(PipelineFunctionType pipelineFunctionType){
        
        List<EventPipeline> eventPipelineList = new ArrayList<>();
                
        Iterator<EventPipeline> iter = pipelineList.iterator();
        
        while(iter.hasNext()) {
            
            EventPipeline ePipeline = iter.next();
            
            if(ePipeline.getPipelineFunctionType() == pipelineFunctionType) {
                eventPipelineList.add(ePipeline);
            }
        }       
        return eventPipelineList;
    }
    
    private static boolean isValidPipeline(String pipelineName,PipelineType pipelineType, PipelineFunctionType 
            pipelineFunctionType) {
        
        return (pipelineName != null && pipelineType !=null && pipelineFunctionType != null);
    }
    
    private static boolean isPipelineEqual(String pipelineName,PipelineType pipelineType, PipelineFunctionType 
            pipelineFunctionType, EventPipeline ePipeline) {
        
        return (
                ePipeline.getPipelineName().equals(pipelineName) &&
                ePipeline.getPipelineType() == pipelineType &&
                ePipeline.getPipelineFunctionType() == pipelineFunctionType
                );
    }
    
    @Override
    public EventPipeline lookup(String pipelineName,PipelineType pipelineType, PipelineFunctionType 
        pipelineFunctionType){
        
        EventPipeline eventPipeline =null;
                
        Iterator<EventPipeline> iter = pipelineList.iterator();
        
        while(iter.hasNext()) {
            
            EventPipeline ePipeline = iter.next();
            
            if(
                    isValidPipeline(pipelineName, pipelineType, pipelineFunctionType) && 
                    isValidPipeline(ePipeline.getPipelineName(), ePipeline.getPipelineType(), 
                            ePipeline.getPipelineFunctionType()) &&
                    isPipelineEqual(pipelineName, pipelineType, pipelineFunctionType, ePipeline)
            ) {
                eventPipeline = ePipeline;
                break;
            }
        }       
        
        if(eventPipeline == null) {
            
            Logger.getRootLogger().info("EventPipelineBus : lookup failed for : "+
                    pipelineName+","+ pipelineType+","+pipelineFunctionType);
        }
               
        return eventPipeline;
    }

    @Override
    public PipelineType getEventBusType() {

        return this.pipelineType;
    }


    @Override
    public String getEventBusName() {
        return this.eventBusName;
    }


    @Override
    public List<EventPipeline> getAllPipelines() {
        return pipelineList;
    }
    public String toString() {
        
        return "EventPipelineBus { pipelineType : "+pipelineType+" | "+pipelineList+" } ";
    }

}
