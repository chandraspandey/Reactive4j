
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.exception;

import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.constants.Constant.FrameworkConstants;

public class DataAccessException extends FrameworkException{

    private static final long serialVersionUID = FrameworkConstants.FRAMEWORK_VERSION_ID;
    
    public DataAccessException(ErrorMap errorMap,String contextMessage){
        
        super(errorMap,contextMessage);
    }
}
