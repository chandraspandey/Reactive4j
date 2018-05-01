package org.flowr.framework.api;

/**
 * Provides external interface for Provider implementation which may include Log provider, aggregator or collector.
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface FlowrLogger {

	public enum Level{
		OFF,
		FATAL,
		ERROR,
		WARN,
		INFO,
		DEBUG,
		TRACE,
		ALL
	}
	
	public void log(Level level, String message);

	public void log(Level level, String message, Object object);

	public void error(Level level, String message, Throwable throwable);
}
