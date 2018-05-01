package org.flowr.framework.core.event.pipeline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.flowr.framework.core.constants.ServerConstants;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class EventPipelineBus implements EventBus {

	private static List<EventPipeline> pipelineList 	= new ArrayList<EventPipeline>();
	private PipelineType pipelineType 					= PipelineType.BUS;	
	private String eventBusName							= ServerConstants.CONFIG_SERVER_EVENT_BUS_NAME;

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
		
		List<EventPipeline> eventPipelineList = new ArrayList<EventPipeline>();
				
		Iterator<EventPipeline> iter = pipelineList.iterator();
		
		while(iter.hasNext()) {
			
			EventPipeline ePipeline = iter.next();
			
			if(ePipeline.getPipelineType().equals(pipelineType)) {
				eventPipelineList.add(ePipeline);
			}
		}
		
		return eventPipelineList;
	}
	
	@Override
	public List<EventPipeline> lookup(PipelineFunctionType pipelineFunctionType){
		
		List<EventPipeline> eventPipelineList = new ArrayList<EventPipeline>();
				
		Iterator<EventPipeline> iter = pipelineList.iterator();
		
		while(iter.hasNext()) {
			
			EventPipeline ePipeline = iter.next();
			
			if(ePipeline.getPipelineFunctionType().equals(pipelineFunctionType)) {
				eventPipelineList.add(ePipeline);
			}
		}		
		return eventPipelineList;
	}
	
	@Override
	public EventPipeline lookup(String pipelineName,PipelineType pipelineType, PipelineFunctionType 
		pipelineFunctionType){
		
		EventPipeline eventPipeline =null;
				
		Iterator<EventPipeline> iter = pipelineList.iterator();
		
		while(iter.hasNext()) {
			
			EventPipeline ePipeline = iter.next();
			
			if(
					pipelineName != null && ePipeline.getPipelineName() != null &&
						ePipeline.getPipelineName().equals(pipelineName) &&
					pipelineType !=null && ePipeline.getPipelineType() != null &&
						ePipeline.getPipelineType().equals(pipelineType) &&
					pipelineFunctionType != null && ePipeline.getPipelineFunctionType() != null &&
						ePipeline.getPipelineFunctionType().equals(pipelineFunctionType)
			) {
				eventPipeline = ePipeline;
				break;
			}
		}		
		System.out.println("EventPipelineBus : eventPipeline : "+eventPipeline);
		
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
