
/**
 * Marker Interface for Attributes specific data model. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.model;

import java.util.List;

public interface AttributeSet extends Model{

    /**
     * Adds attribute if the set exists, else ignores the call
     * @param attribute
     */
    void addAttribute(Attribute attribute);
    
    /**
     * Returns the list of Attributes
     * @return
     */
    List<Attribute> getAttributeList();

    /**
     * Sets the List of Attributes
     * @param dataPointList
     */
    void setAttributeList(List<Attribute> attributeList) ;
    
    /**
     * Shorthand method for checking if the set is empty.
     * @return
     */
    boolean isEmpty();
}
