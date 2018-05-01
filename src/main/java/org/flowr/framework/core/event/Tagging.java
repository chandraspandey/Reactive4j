package org.flowr.framework.core.event;

import org.flowr.framework.core.model.DataAttribute;


/**
 * Provides dynamic addition of attributes to the event as a change based on
 * the user interaction actions.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Tagging {
	
	/**
	 * Adds Data Attribute to the existing DataModel
	 * @param dataAttribute
	 */
	public void addAttribute(DataAttribute dataAttribute);

}
