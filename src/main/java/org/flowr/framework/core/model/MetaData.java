package org.flowr.framework.core.model;

import java.util.Hashtable;

/**
 * Marker interface for representing MetaData. Augumentation details provided
 * as part of MetaData is context specific attributes which can be used for
 * actionable instrumentation or insight.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface MetaData extends Model{
	

	public Hashtable<String, String> getMetaDataAttributes();

	public void setMetaDataAttributes(Hashtable<String, String> metaDataAttributes) ;
}
