
/**
 * Extends Exception to provide framework specific instrumentation. Provides additional instrumentation for linking the
 * input for which exception can happen which can be used exception chaining kind of scenarios.
 * @author Chandra Pandey
 *
 */

package org.flowr.framework.core.exception;

import java.io.Serializable;

import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.constants.Constant.FrameworkConstants;

public class FrameworkException extends Exception{

    private static final long serialVersionUID = FrameworkConstants.FRAMEWORK_VERSION_ID;

    private final int errorCode;
    private final String errorMessage;
    private final String contextMessage;
    private final Serializable input;
    
   public FrameworkException(ErrorMap errorMap,String contextMessage){
        
        super(errorMap.getErrorMessage());
        this.errorCode      = errorMap.getErrorCode();
        this.errorMessage   = errorMap.getErrorMessage();
        this.contextMessage = contextMessage;
        this.input          = null;
    }
    
    public FrameworkException(ErrorMap errorMap,String contextMessage, Throwable cause){
        
        super(errorMap.getErrorMessage(),cause);
        this.errorCode      = errorMap.getErrorCode();
        this.errorMessage   = errorMap.getErrorMessage();
        this.contextMessage = contextMessage;
        this.input          = null;
    }
    
    public FrameworkException(ErrorMap errorMap,String contextMessage, Serializable input){
        
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
