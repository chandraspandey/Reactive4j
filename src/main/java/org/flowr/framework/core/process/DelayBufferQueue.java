package org.flowr.framework.core.process;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

/**
 * Concrete implementaion & extension of DelayQueue for load balancing I/O.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

// Need mechanism for sorting on demand for multiple parameters supporting FIFO,LIFO

public class DelayBufferQueue extends DelayQueue<Delayed> {


}

