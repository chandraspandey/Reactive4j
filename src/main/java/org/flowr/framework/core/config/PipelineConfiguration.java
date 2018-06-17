package org.flowr.framework.core.config;

import java.util.List;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class PipelineConfiguration implements Configuration{

	private PipelinePolicy pipelinePolicy 		= PipelinePolicy.DISPOSE_AFTER_PROCESSING;
	private String pipelineName					= null;
	private long MAX_ELEMENTS 					= 10000;
	private boolean batchMode					= false;
	private int batchSize						= 1;	
	private List<ServiceConfiguration> configurationList = null;

	public PipelinePolicy getPipelinePolicy() {
		return pipelinePolicy;
	}

	public void setPipelinePolicy(PipelinePolicy pipelinePolicy) {
		this.pipelinePolicy = pipelinePolicy;
	}

	public long getMAX_ELEMENTS() {
		return MAX_ELEMENTS;
	}

	public void setMAX_ELEMENTS(long mAX_ELEMENTS) {
		MAX_ELEMENTS = mAX_ELEMENTS;
	}

	public boolean isBatchMode() {
		return batchMode;
	}

	public void setBatchMode(boolean batchMode) {
		this.batchMode = batchMode;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public String getPipelineName() {
		return pipelineName;
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}
	
	public List<ServiceConfiguration> getConfigurationList() {
		return configurationList;
	}

	public void setConfigurationList(List<ServiceConfiguration> configurationList) {
		this.configurationList = configurationList;
	}

	public String toString(){
		
		return "PipelineConfiguration{\n"+
				" pipelineName : "+pipelineName+
				" | pipelinePolicy : "+pipelinePolicy+	
				" | batchMode : "+batchMode+	
				" | batchSize : "+batchSize+	
				" | configurationList : "+configurationList+
				"}\n";
	}
}
