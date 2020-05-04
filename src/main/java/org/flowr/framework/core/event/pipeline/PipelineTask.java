
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.event.pipeline;

import java.util.concurrent.Callable;

import org.flowr.framework.core.notification.dispatcher.NotificationBufferQueue;

public interface PipelineTask extends Callable<NotificationBufferQueue> {

    NotificationBufferQueue process() throws InterruptedException;
}
