package org.flowr.framework.core.node;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class BackPressureExecutors {


	public static BackPressureExecutorService newCachedBackPressureThreadPool(){
		
		BackPressureExecutorService backPressureExecutorService = new BackPressureExecutorService();
		
		return backPressureExecutorService;
	}

}
