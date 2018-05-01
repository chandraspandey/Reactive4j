package org.flowr.framework.cep;

import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.event.ReactiveMetaData;
import org.flowr.framework.core.model.EventModel;

/**
 * Defines high level capabilities of state and status change. To reduce the  
 * application interaction based on data change, modulates it to defined states 
 * & status.  The state here refers to intermediate changes between two Status.
 * There may be cases where only status changes would be required such as 
 * work flow where deterministic is inbuilt. Similarly for ROA based systems a
 * series of States between two Status is inevitable.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface StateChangeMachine<Context, CHANGE> {

	/**
	 * For every change type MetaData for State, generate and Event of type T.
	 * By providing generic Types opens the usecases which can be simple types
	 * or complex inbuilt model types.
	 * @param change
	 * @return
	 */
	public Event<EventModel> onStateChange(Context context,ReactiveMetaData change);
	
	/**
	 * For every change type MetaData for Status, generate and Event of type T.
	 * By providing generic Types opens the usecases which can be simple types
	 * or complex inbuilt model types.
	 * @param change
	 * @return
	 */
	public Event<EventModel> onStatusChange(Context  context,ReactiveMetaData change);
	
	/**
	 * Determines if there is a State Change
	 */
	public boolean isStateChange();
	
	
	/**
	 * Determines if there is status change
	 * @param change
	 */
	public boolean isStatusChange();
}
