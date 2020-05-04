
/**
 * Extends Exception to provide reactive specific instrumentation. Provides additional instrumentation for linking the
 * input for which exception can happen which can be used exception chaining kind of scenarios.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.exception;

import java.io.Serializable;

import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.constants.Constant.FrameworkConstants;

public class ReactiveException extends Exception{

    private static final long serialVersionUID = FrameworkConstants.FRAMEWORK_VERSION_ID;

    private final int errorCode;
    private final String errorMessage;
    private final String contextMessage;
    private final Serializable input;
    
    public ReactiveException(ErrorMap errorMap,String contextMessage, Serializable input){
        
        super(errorMap.getErrorMessage());
        this.errorCode      = errorMap.getErrorCode();
        this.errorMessage   = errorMap.getErrorMessage();
        this.contextMessage = contextMessage;
        this.input          = input;
    }
    
    public Serializable getInput() {
        return input;
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

    @Override
    public String getLocalizedMessage(){        
        
        return errorCode+" : "+errorMessage+" : "+contextMessage;       
    }
}
