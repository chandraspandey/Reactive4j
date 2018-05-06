package org.flowr.framework.core.process;

import java.util.concurrent.TimeUnit;

/**
 * See if full fledged Cache can be pluggedin to handle high frequency updates
 * with large inflow/outflow. May have to define Mode to address the same as
 * variability.
 * Requires ProcessorConfig to control how many elements in memory storage 
 * should be good enough. After that there should be provision to offload to 
 * temporary cache/persistence if not consumed. Thereby reducing the memory 
 * requirement to run a processor. 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ProcessorConfig {

	/*
	 * Defines ProcessorMode which can be used for compacting the size of 
	 * output when used along with compaction functionality.
	 */
	public enum ProcessorMode{
		UNIT,
		BLOCK,
		COMPLETE
	}
	
	public enum ProcessorPersistenceMode{
		CACHE,
		DB
	}
	private ProcessorMode processorMode;
	
	// Marks if compaction should be used
	private boolean useCompaction;

	// Defines TimeUnit to be used for compaction 
	private TimeUnit COMPACTION_TIMEUNIT;
	
	// Defines interval which should be used for compaction
	private long COMPACTION_INTERVAL;
	
	/* Marks the maximum number of elements that should be considered to be 
	 * in queue. After that queue should be drained to Cache or DB. Should be
	 * used in conjugation with usePersistence.
	 */
	private long maxQueueElements; 
	
	// Marks the usage of Temporal/Permanent persistence 
	private boolean usePersistence;
	
	// Marks the configuration file to be used for Cache/DB persistence
	private String persistenceConfigFile;
	
	// Marks the maximum memory size that queue should occupy to use persistence
	private long MAX_MEMORY_SIZE;	
	
	// Marks the usage of expiring the elements of the queue.
	private long EXPIRY_LIMIT;
	
	private TimeUnit EXPIRY_TIMEUNIT;	
	
	public void setProcessorMode(ProcessorMode processorMode){		
		this.processorMode = processorMode;
	}
	
	public ProcessorMode getProcessorMode(){
		return this.processorMode;
	}

	public long getMaxQueueElements() {
		return maxQueueElements;
	}

	public void setMaxQueueElements(long maxQueueElements) {
		this.maxQueueElements = maxQueueElements;
	}

	public boolean isUseCompaction() {
		return useCompaction;
	}

	public void setUseCompaction(boolean useCompaction) {
		this.useCompaction = useCompaction;
	}

	public long getCOMPACTION_INTERVAL() {
		return this.COMPACTION_INTERVAL;
	}

	public void setCOMPACTION_INTERVAL(long COMPACTION_INTERVAL) {
		this.COMPACTION_INTERVAL = COMPACTION_INTERVAL;
	}

	public boolean isUsePersistence() {
		return this.usePersistence;
	}

	public void setUsePersistence(boolean usePersistence) {
		this.usePersistence = usePersistence;
	}

	public long getMAX_MEMORY_SIZE() {
		return this.MAX_MEMORY_SIZE;
	}

	public void setMAX_MEMORY_SIZE(long MAX_MEMORY_SIZE) {
		this.MAX_MEMORY_SIZE = MAX_MEMORY_SIZE;
	}

	public long getEXPIRY_LIMIT() {
		return this.EXPIRY_LIMIT;
	}

	public void setEXPIRY_LIMIT(long EXPIRY_LIMIT) {
		this.EXPIRY_LIMIT = EXPIRY_LIMIT;
	}

	public TimeUnit getEXPIRY_TIMEUNIT() {
		return this.EXPIRY_TIMEUNIT;
	}

	public void setEXPIRY_TIMEUNIT(TimeUnit EXPIRY_TIMEUNIT) {
		this.EXPIRY_TIMEUNIT = EXPIRY_TIMEUNIT;
	}
	
	public TimeUnit getCOMPACTION_TIMEUNIT() {
		return this.COMPACTION_TIMEUNIT;
	}
	
	public void setCOMPACTION_TIMEUNIT(TimeUnit COMPACTION_TIMEUNIT) {
		this.COMPACTION_TIMEUNIT = COMPACTION_TIMEUNIT;
	}
	
	public String getPersistenceConfigFile() {
		return persistenceConfigFile;
	}

	public void setPersistenceConfigFile(String persistenceConfigFile) {
		this.persistenceConfigFile = persistenceConfigFile;
	}
	
	public String toString(){
		
		return "\n ProcessorConfig{"+
				" | processorMode : "+processorMode+
				" | useCompaction : "+useCompaction+				
				" | COMPACTION_TIMEUNIT : "+COMPACTION_TIMEUNIT+
				" | COMPACTION_INTERVAL : "+COMPACTION_INTERVAL+
				" | maxQueueElements : "+maxQueueElements+
				" | usePersistence : "+usePersistence+
				" | persistenceConfigFile : "+persistenceConfigFile+
				" | MAX_MEMORY_SIZE : "+MAX_MEMORY_SIZE+
				" | EXPIRY_LIMIT : "+EXPIRY_LIMIT+
				" | EXPIRY_TIMEUNIT : "+EXPIRY_TIMEUNIT+
				"}";
	}


}
