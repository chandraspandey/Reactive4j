
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.exception;

import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.constants.Constant.FrameworkConstants;

public class ServerException extends Exception{

    private static final long serialVersionUID = FrameworkConstants.FRAMEWORK_VERSION_ID;

    private final int errorCode;
    private final String errorMessage;
    private final String contextMessage;
    
    public ServerException(ErrorMap errorMap,String contextMessage, Throwable cause){
        
        super(errorMap.getErrorMessage(),cause);
        this.errorCode      = errorMap.getErrorCode();
        this.errorMessage   = errorMap.getErrorMessage();
        this.contextMessage = contextMessage;
    }
    
    public ServerException(ErrorMap errorMap,String contextMessage){
        
        super(errorMap.getErrorMessage());
        this.errorCode      = errorMap.getErrorCode();
        this.errorMessage   = errorMap.getErrorMessage();
        this.contextMessage = contextMessage;
    }
    
    @Override
    public String getLocalizedMessage(){        
        
        return errorCode+" : "+errorMessage+" : "+contextMessage;       
    }
    

    public int getErrorCode() {
        return errorCode;
    }
    public String getErrorMessage() {
        return errorMessage;
    }

    public String getContextMessage() {
        return contextMessage;
    }

}
