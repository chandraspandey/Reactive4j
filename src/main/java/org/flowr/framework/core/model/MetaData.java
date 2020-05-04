
/**
 * Marker interface for representing MetaData. Augumentation details provided
 * as part of MetaData is context specific attributes which can be used for
 * actionable instrumentation or insight.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.model;

import java.util.HashMap;

public interface MetaData extends Model{
    

    HashMap<String, String> getMetaDataAttributes();

    void setMetaDataAttributes(HashMap<String, String> metaDataAttributes) ;
}
