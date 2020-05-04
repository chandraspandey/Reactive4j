
/**
 * See if full fledged Cache can be pluggedin to handle high frequency updates
 * with large inflow/outflow. May have to define Mode to address the same as
 * variability.
 * Requires ProcessorConfig to control how many elements in memory storage 
 * should be good enough. After that there should be provision to offload to 
 * temporary cache/persistence if not consumed. Thereby reducing the memory 
 * requirement to run a processor. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.process;

import java.util.concurrent.TimeUnit;

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
    private TimeUnit compactionTimeUnit;
    
    // Defines interval which should be used for compaction
    private long compactionInterval;
    
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
    private long maxMemorySize;   
    
    // Marks the usage of expiring the elements of the queue.
    private long expiryLimit;
    
    private TimeUnit expiryTimeUnit;   
    
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

    public long getCompactionInterval() {
        return this.compactionInterval;
    }

    public void setCompactionInterval(long compactionInterval) {
        this.compactionInterval = compactionInterval;
    }

    public boolean isUsePersistence() {
        return this.usePersistence;
    }

    public void setUsePersistence(boolean usePersistence) {
        this.usePersistence = usePersistence;
    }

    public long getMaxMemorySize() {
        return this.maxMemorySize;
    }

    public void setMaxMemorySize(long maxMemorySize) {
        this.maxMemorySize = maxMemorySize;
    }

    public long getExpiryLimit() {
        return this.expiryLimit;
    }

    public void setExpiryLimit(long expiryLimit) {
        this.expiryLimit = expiryLimit;
    }

    public TimeUnit getExpiryTimeUnit() {
        return this.expiryTimeUnit;
    }

    public void setExpiryTimeUnit(TimeUnit expiryTimeUnit) {
        this.expiryTimeUnit = expiryTimeUnit;
    }
    
    public TimeUnit getCompactionTimeUnit() {
        return this.compactionTimeUnit;
    }
    
    public void setCompactionTimeUnit(TimeUnit compactionTimeUnit) {
        this.compactionTimeUnit = compactionTimeUnit;
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
                " | COMPACTION_TIMEUNIT : "+compactionTimeUnit+
                " | COMPACTION_INTERVAL : "+compactionInterval+
                " | maxQueueElements : "+maxQueueElements+
                " | usePersistence : "+usePersistence+
                " | persistenceConfigFile : "+persistenceConfigFile+
                " | MAX_MEMORY_SIZE : "+maxMemorySize+
                " | EXPIRY_LIMIT : "+expiryLimit+
                " | EXPIRY_TIMEUNIT : "+expiryTimeUnit+
                "}";
    }


}
