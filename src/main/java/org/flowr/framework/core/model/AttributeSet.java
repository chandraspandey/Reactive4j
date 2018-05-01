package org.flowr.framework.core.model;

import java.util.List;

/**
 * Marker Interface for Attributes specific data model. 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface AttributeSet extends Model{

	/**
	 * Adds attribute if the set exists, else ignores the call
	 * @param attribute
	 */
	public void addAttribute(Attribute attribute);
	
	/**
	 * Returns the list of Attributes
	 * @return
	 */
	public List<Attribute> getAttributeList();

	/**
	 * Sets the List of Attributes
	 * @param dataPointList
	 */
	public void setAttributeList(List<Attribute> attributeList) ;
	
	/**
	 * Shorthand method for checking if the set is empty.
	 * @return
	 */
	public boolean isEmpty();
}
