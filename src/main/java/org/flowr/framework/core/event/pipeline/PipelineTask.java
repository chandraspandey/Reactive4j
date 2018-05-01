package org.flowr.framework.core.event.pipeline;

import java.util.concurrent.Callable;

import org.flowr.framework.core.notification.NotificationBufferQueue;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface PipelineTask extends Callable<NotificationBufferQueue> {

	public NotificationBufferQueue process() throws InterruptedException;
}
