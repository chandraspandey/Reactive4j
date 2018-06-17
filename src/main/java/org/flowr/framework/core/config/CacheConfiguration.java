package org.flowr.framework.core.config;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class CacheConfiguration implements Configuration{
	
	private String cacheName;
	private String cacheStrategy;
	private String cachePath;
	private String cachePolicy;
	
	private boolean cacheOverFlowToDisk;
	private boolean cacheStatistics;
	private boolean isEternal;
	
	private long cacheDiskSpool;
	private long cacheDiskMax;
	private long ELEMENT_MAX_MEMORY;
	private long ELEMENT_MAX_HEAP;
	private long ELEMENT_MAX_DISK;
	private long ELEMENT_TIME_TO_LIVE;
	private long ELEMENT_TIME_TO_IDLE;
	private long ELEMENT_EXPIRY_DISK;
	
	public boolean isValid(){
		
		boolean isValid = false;
		
		if(	ELEMENT_MAX_MEMORY > 0 && ELEMENT_TIME_TO_LIVE > 0 ){
			
			isValid = true;
		}
		
		return isValid;
	}

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public String getCacheStrategy() {
		return cacheStrategy;
	}

	public void setCacheStrategy(String cacheStrategy) {
		this.cacheStrategy = cacheStrategy;
	}

	public String getCachePath() {
		return cachePath;
	}

	public void setCachePath(String cachePath) {
		this.cachePath = cachePath;
	}

	public String getCachePolicy() {
		return cachePolicy;
	}

	public void setCachePolicy(String cachePolicy) {
		this.cachePolicy = cachePolicy;
	}
	
	public boolean isCacheOverFlowToDisk() {
		return cacheOverFlowToDisk;
	}

	public void setCacheOverFlowToDisk(boolean cacheOverFlowToDisk) {
		this.cacheOverFlowToDisk = cacheOverFlowToDisk;
	}

	public boolean isCacheStatistics() {
		return cacheStatistics;
	}

	public void setCacheStatistics(boolean cacheStatistics) {
		this.cacheStatistics = cacheStatistics;
	}

	public long getCacheDiskSpool() {
		return cacheDiskSpool;
	}

	public void setCacheDiskSpool(long cacheDiskSpool) {
		this.cacheDiskSpool = cacheDiskSpool;
	}

	public long getELEMENT_MAX_MEMORY() {
		return ELEMENT_MAX_MEMORY;
	}

	public void setELEMENT_MAX_MEMORY(long eLEMENT_MAX_MEMORY) {
		ELEMENT_MAX_MEMORY = eLEMENT_MAX_MEMORY;
	}

	public long getELEMENT_MAX_HEAP() {
		return ELEMENT_MAX_HEAP;
	}

	public void setELEMENT_MAX_HEAP(long eLEMENT_MAX_HEAP) {
		ELEMENT_MAX_HEAP = eLEMENT_MAX_HEAP;
	}

	public long getELEMENT_MAX_DISK() {
		return ELEMENT_MAX_DISK;
	}

	public void setELEMENT_MAX_DISK(long eLEMENT_MAX_DISK) {
		ELEMENT_MAX_DISK = eLEMENT_MAX_DISK;
	}

	public long getELEMENT_TIME_TO_LIVE() {
		return ELEMENT_TIME_TO_LIVE;
	}

	public void setELEMENT_TIME_TO_LIVE(long eLEMENT_TIME_TO_LIVE) {
		ELEMENT_TIME_TO_LIVE = eLEMENT_TIME_TO_LIVE;
	}

	public long getELEMENT_TIME_TO_IDLE() {
		return ELEMENT_TIME_TO_IDLE;
	}

	public void setELEMENT_TIME_TO_IDLE(long eLEMENT_TIME_TO_IDLE) {
		ELEMENT_TIME_TO_IDLE = eLEMENT_TIME_TO_IDLE;
	}

	public long getELEMENT_EXPIRY_DISK() {
		return ELEMENT_EXPIRY_DISK;
	}

	public void setELEMENT_EXPIRY_DISK(long eLEMENT_EXPIRY_DISK) {
		ELEMENT_EXPIRY_DISK = eLEMENT_EXPIRY_DISK;
	}
	
	public boolean isEternal() {
		return isEternal;
	}

	public void setEternal(boolean isEternal) {
		this.isEternal = isEternal;
	}

	public long getCacheDiskMax() {
		return cacheDiskMax;
	}

	public void setCacheDiskMax(long cacheDiskMax) {
		this.cacheDiskMax = cacheDiskMax;
	}
	
	public String toString(){
		
		return "CacheConfiguration{\n"+
				" cacheName : "+cacheName+
				" | cacheStrategy : "+cacheStrategy+	
				" | cachePath : "+cachePath+	
				" | cachePolicy : "+cachePolicy+	
				" | cacheOverFlowToDisk : "+cacheOverFlowToDisk+		
				" | cacheStatistics : "+cacheStatistics+	
				" | isEternal : "+isEternal+
				" | cacheDiskMax : "+cacheDiskMax+
				" | cacheDiskSpool : "+cacheDiskSpool+
				" | ELEMENT_MAX_MEMORY : "+ELEMENT_MAX_MEMORY+
				" | ELEMENT_MAX_HEAP : "+ELEMENT_MAX_HEAP+
				" | ELEMENT_MAX_DISK : "+ELEMENT_MAX_DISK+
				" | ELEMENT_TIME_TO_LIVE : "+ELEMENT_TIME_TO_LIVE+
				" | ELEMENT_TIME_TO_IDLE : "+ELEMENT_TIME_TO_IDLE+
				" | ELEMENT_TIME_TO_IDLE : "+ELEMENT_TIME_TO_IDLE+
				" | ELEMENT_EXPIRY_DISK : "+ELEMENT_EXPIRY_DISK+	
				"}\n";
	}


}
